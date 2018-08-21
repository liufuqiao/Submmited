package com.giiso.submmited.http.presenter;

import android.net.ParseException;

import com.giiso.submmited.BuildConfig;
import com.giiso.submmited.R;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.ui.LoginActivity;
import com.giiso.submmited.utils.Log;
import com.giiso.submmited.utils.ToastUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/8/2.
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {
    protected BaseView view;
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 1002;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 1003;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1004;

    /**
     * 请求异常
     */
    public static final int EXCEPTION_ERROR = 1005;

    public BaseObserver() {

    }

    public BaseObserver(BaseView view) {
        this.view = view;
    }

    @Override
    protected void onStart() {
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        try {
            ResultResponse model = (ResultResponse) o;
            if (model.getCode().equals(Constants.RESPONSE_SUCCESS_CODE)) {
                onSuccess(o);
            } else {
                if (view != null) {
                    view.onErrorCode(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(EXCEPTION_ERROR, e.toString());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (view != null) {
            view.closeLoading();
        }
        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(PARSE_ERROR);
        } else {
            if (e != null) {
                onError(EXCEPTION_ERROR, e.toString());
            } else {
                onError(EXCEPTION_ERROR, "未知错误");
            }
        }

    }

    private void onException(int unknownError) {
        if(BAD_NETWORK == unknownError){
            ToastUtil.showToast(R.string.operate_error_hint_network);
        } else if(CONNECT_ERROR == unknownError){
            ToastUtil.showToast(R.string.operate_error_hint_server);
        } else if(CONNECT_TIMEOUT == unknownError){
            ToastUtil.showToast(R.string.operate_error_hint_server);
        }
        switch (unknownError) {
            case CONNECT_ERROR:
                onError(CONNECT_ERROR, "连接错误");
                break;

            case CONNECT_TIMEOUT:
                onError(CONNECT_TIMEOUT, "连接超时");
                break;

            case BAD_NETWORK:
                onError(BAD_NETWORK, "网络问题");
                break;

            case PARSE_ERROR:
                onError(PARSE_ERROR,"解析数据失败");
                break;

            default:
                break;
        }
    }

    @Override
    public void onComplete() {
        if (view != null) {
            view.closeLoading();
        }

    }
    public abstract void onSuccess(T response);

    public abstract void onError(int code, String msg);

}