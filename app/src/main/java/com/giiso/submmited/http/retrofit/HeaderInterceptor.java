package com.giiso.submmited.http.retrofit;

import android.content.SharedPreferences;

import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.account.AccountHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Create by LiuRiZhao
 * Comment ://TODO
 * Date : 2017/6/9 17:27
 */
public class HeaderInterceptor implements Interceptor
{
    private Request.Builder builder;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String cookie = BaseApplication.get(Constants.COOKIE,"");
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", "add cookies here")
                .addHeader("cookie", cookie);
        return chain.proceed(builder.build());
    }
}
