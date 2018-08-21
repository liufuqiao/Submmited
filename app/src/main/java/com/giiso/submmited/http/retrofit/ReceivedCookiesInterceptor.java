package com.giiso.submmited.http.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.utils.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Session会话拦截器查看是否过期
 * Created by LiuRiZhao on 2018/8/10.
 */

public class ReceivedCookiesInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        Log.d("http", "originalResponse" + originalResponse.toString());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(originalResponse.headers("set-cookie"))
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            cookieBuffer.append(cookie).append(";");
                        }
                    });
            Log.d("http", "ReceivedCookiesInterceptor" + cookieBuffer.toString());
            BaseApplication.set(Constants.COOKIE, cookieBuffer.toString());
        }

        return originalResponse;
    }
}
