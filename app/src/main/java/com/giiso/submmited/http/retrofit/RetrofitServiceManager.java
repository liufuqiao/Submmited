package com.giiso.submmited.http.retrofit;

import com.giiso.submmited.base.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Arthur
 * Email: m18152768805@163.com
 * Comment :利用Retrofit设置网络请求
 * Date : 2017/5/31 17:30
 */
public class RetrofitServiceManager {
    private static RetrofitServiceManager mRetrofitServiceManager;
    private static Retrofit mRetrofit;

    private RetrofitServiceManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpManager.newInstance())
                .build();
    }

    public static RetrofitServiceManager getInstance() {
        if (mRetrofitServiceManager == null) {
            synchronized (RetrofitServiceManager.class) {
                if (mRetrofitServiceManager == null) {
                    mRetrofitServiceManager = new RetrofitServiceManager();
                }
            }
        }
        return mRetrofitServiceManager;
    }

    public <T> T create(Class<T> cls) {
        return mRetrofit.create(cls);
    }
}
