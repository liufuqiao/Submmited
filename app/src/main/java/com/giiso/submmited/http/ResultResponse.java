package com.giiso.submmited.http;

import com.giiso.submmited.base.Constants;
import com.giiso.submmited.utils.gson.AppOperator;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Create by Arthur liurizhao
 * Comment ://TODO
 * Date : 2018/7/09 16:02
 */
public class ResultResponse<T>{
    private String code;
    private String msg;
    private T data;
    private boolean isShowToast;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }

    public boolean getIsShowToast()
    {
        return isShowToast;
    }

    public void setIsShowToast(boolean showToast)
    {
        isShowToast = showToast;
    }

    public boolean isSuccess() {
        return this.code.equals(Constants.RESPONSE_SUCCESS_CODE);
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
