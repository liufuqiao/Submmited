package com.giiso.submmited.utils;

import android.app.Dialog;

/**
 * Created by Administrator on 2017/10/12.
 * 用于管理Dialog弹窗唯一
 */

public class SingleDialogUtil {

    private Dialog customDialog = null;
    private static SingleDialogUtil customDialogUtil = null;

    public static synchronized SingleDialogUtil getInstance() {
        if (customDialogUtil == null) {
            customDialogUtil = new SingleDialogUtil();
        }
        return customDialogUtil;
    }

    /**
     * 取消所有弹出的对话框
     */
    public void show(Dialog customDialog) {
        try {
            if (this.customDialog != null && this.customDialog.isShowing()) {
                this.customDialog.dismiss();
            }
            this.customDialog = customDialog;
            if (this.customDialog.getContext() != null) {
                this.customDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消所有弹出的对话框
     */
    public void dismiss() {
        try {
            if (this.customDialog != null && this.customDialog.isShowing()) {
                this.customDialog.dismiss();
                this.customDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
