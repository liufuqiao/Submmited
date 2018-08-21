package com.giiso.submmited.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by lrz on 2018/5/10.
 */

public class PaymentUtil {

    private Context mContext;
    private static final int PAYMENT_ALI_PAY = 1;
    private static final int PAYMENT_WECHAT_PAY = 2;
    private OnPayResultListener mOnPayResultListener;
    public PaymentUtil(Context mContext){
        this.mContext = mContext;
    }
    private String order = "";
    /**
     * 调用支付宝
     * @param orderInfo
     */
    public void aliPay(final String orderInfo){
        order = orderInfo;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(orderInfo,true);
            Message msg = new Message();
            msg.what = PAYMENT_ALI_PAY;
            msg.obj = result;
            mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付
     * @param params
     */
    private IWXAPI api;
    public void wechatPay(Map<String, String> json){
        try {
            api = WXAPIFactory.createWXAPI(mContext, json.get("appid"), true);
            api.registerApp(json.get("appid"));
            if(!api.isWXAppInstalled()){
                ToastUtil.showToast( "当前手机未安装微信应用");
                return;
            }
            PayReq req = new PayReq();
            req.appId			= json.get("appid");
            req.partnerId		= json.get("partnerid");
            req.prepayId		= json.get("prepayid");
            req.nonceStr		= json.get("nonceStr");
            req.timeStamp		= json.get("timestamp");
            req.packageValue	= json.get("package");
            req.sign			= json.get("sign");
            api.sendReq(req);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PAYMENT_ALI_PAY: {
                    PayResult result = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = result.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    Log.d("code", order);
                    Log.d("code", result.toString());
                    if (TextUtils.equals(resultStatus, PayResult.PAY_SUCCESS)) {
                        if(mOnPayResultListener != null){
                            mOnPayResultListener.paySuccess();
                        }
                    } else if(TextUtils.equals(resultStatus, PayResult.PAY_CANCLE)){
                        Toast.makeText(mContext, "取消支付", Toast.LENGTH_SHORT).show();
                    } else {
                        if(mOnPayResultListener != null){
                            mOnPayResultListener.payFailed();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public interface OnPayResultListener{
        void paySuccess();
        void payFailed();
    }

    public void setmOnPayResultListener(OnPayResultListener mOnPayResultListener) {
        this.mOnPayResultListener = mOnPayResultListener;
    }
}
