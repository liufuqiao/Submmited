package com.giiso.submmited.utils;

import android.text.TextUtils;

import java.util.Map;

public class PayResult {
    private String resultStatus;
    private String result;
    private String memo;

    public static final String PAY_SUCCESS = "9000";//支付成功
    public static final String PAY_UNKONW = "8000";//正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
    public static final String PAY_CANCLE = "6001";//用户中途取消
    public static final String PAY_NETWORK_ERROR = "6002";//网络错误;
    public static final String PAY_UNKONW_EXCEPATION = "6004";//支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
    public static final String PAY_FAILED = "4000";//订单支付失败
    public static final String PAY_REPEAT = "5000";//重复请求

    public PayResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return;
        }

        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                resultStatus = rawResult.get(key);
            } else if (TextUtils.equals(key, "result")) {
                result = rawResult.get(key);
            } else if (TextUtils.equals(key, "memo")) {
                memo = rawResult.get(key);
            }
        }
    }

    @Override
    public String toString() {
        return "resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}";
    }

    /**
     * @return the resultStatus
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
}

