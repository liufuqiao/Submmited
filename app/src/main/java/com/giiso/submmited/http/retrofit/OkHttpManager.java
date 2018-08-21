package com.giiso.submmited.http.retrofit;


import com.giiso.submmited.BuildConfig;
import com.giiso.submmited.utils.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2016/1/18.
 */

public class OkHttpManager {
    private static OkHttpClient okHttpClient;

    public static OkHttpClient newInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            //打印retrofit日志
                            Log.i("resultJson:", "retrofitBack =" + message);
                        }
                    });
                    if (BuildConfig.DEBUG) {
                        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    } else {
                        logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }
                    builder.addInterceptor(logInterceptor);
                    builder.addInterceptor(new LoggingInterceptor());
                    builder.addInterceptor(new HeaderInterceptor());
                    builder.addInterceptor(new ReceivedCookiesInterceptor());
                    //超时时间
                    builder.connectTimeout(15, TimeUnit.SECONDS);//15S连接超时
                    builder.readTimeout(15, TimeUnit.SECONDS);//20s读取超时
                    builder.writeTimeout(15, TimeUnit.SECONDS);//20s写入超时
                    //错误重连
                    builder.retryOnConnectionFailure(true);
                    okHttpClient = builder.build();
                }
            }
        }
        return okHttpClient;
    }
}
