package com.giiso.submmited.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.giiso.submmited.R;
import com.giiso.submmited.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * <p>
 * Title: 微信支付结果处理页面
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @作者 LiuRiZhao
 * @创建时间 2018年05月20日
 * @版本 1.0
 * @修改记录 <pre>
 * 版本   修改人    修改时间    修改内容描述
 * ----------------------------------------
 * <p>
 * </pre>
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private final static String TAG = WXPayEntryActivity.class.getSimpleName();
    private IWXAPI wxApi;
    public static String APP_ID = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_checkstand_wxpay_result);

        wxApi = WXAPIFactory.createWXAPI(this, APP_ID);
        wxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0: // 支付成功
                    ToastUtil.showToast("打赏成功");
                    break;
                case -1: // 支付失败
                    ToastUtil.showToast("打赏失败");
                    break;
                case -2: // 用户取消
                    ToastUtil.showToast("取消支付");
                    break;
                default:
                    break;
            }
            finish();
        }
    }
}