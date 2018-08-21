package com.giiso.submmited.ui.base.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.giiso.submmited.utils.TDevice;

/**
 * Created by Administrator on 2017/10/11.
 */

public abstract class NetBroadCastReciver extends BroadcastReceiver {

    /**
     * 只有当网络改变的时候才会 经过广播。
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (TDevice.hasInternet()) {
            onSuccess();
        } else {
            onFail();
        }
    }

    public void onSuccess() {
    }

    public void onFail() {
    }
}