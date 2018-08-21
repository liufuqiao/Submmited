package com.giiso.submmited.http.retrofit;

import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.bean.http.HttpLog;
import com.giiso.submmited.db.HttpLogDao;
import com.giiso.submmited.ui.LoginActivity;
import com.giiso.submmited.utils.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截请求和返回JSON做日志记录
 * Created by Administrator on 2018/7/17.
 */

public class LoggingInterceptor implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();

        HttpLog requestLog = new HttpLog();
        requestLog.setType(request.method());
        requestLog.setHead(request.headers().toString());
        requestLog.setBody(request.tag().toString());
        requestLog.setUrl(request.toString());
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            e.printStackTrace();
            requestLog.setResult(e.getMessage());
            if(e.getMessage().contains("9001")){
                LoginActivity.show(BaseApplication.getInstance());
            }
        }
        HttpLogDao.getInstance(BaseApplication.getInstance()).insert(requestLog);
        if(response != null){
            if (response.isSuccessful()) {
                HttpLog responseSuccess = new HttpLog();
                responseSuccess.setType(response.request().method());
                responseSuccess.setHead(response.request().headers().toString());
                responseSuccess.setBody(response.request().tag().toString());
                responseSuccess.setResult("protocol=" + response.protocol().toString() + "  ,code=" + response.code() + "  ,message=" + response.message());
                responseSuccess.setUrl(response.request().url().toString());
                HttpLogDao.getInstance(BaseApplication.getInstance()).insert(responseSuccess);
            } else {
                HttpLog responseError = new HttpLog();
                responseError.setType(response.request().method());
                responseError.setHead(response.request().headers().toString());
                responseError.setBody(response.request().tag().toString());
                responseError.setResult("protocol=" + response.protocol().toString() + "  ,code=" + response.code() + "  ,message=" + response.message());
                responseError.setUrl(response.request().url().toString());
                HttpLogDao.getInstance(BaseApplication.getInstance()).insert(responseError);
            }
        }
        return response;
    }
}
