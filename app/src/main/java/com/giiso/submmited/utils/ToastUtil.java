package com.giiso.submmited.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;
import com.giiso.submmited.base.BaseApplication;

/**
 * Created by jayce on 2017/12/1.
 */

public class ToastUtil
{
    // 定义成全局变量，避免使用Toast提示信息时持续显示
    private static Toast mToast;

    /**
     * function :  显示提示信息
     * author :  Arthur
     * createTime :  2017/5/24 15:56
     *
     * @param resourceId:资源文件id,duration:持续时间
     */
    public static void showToast(final int resourceId, final int duration) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(BaseApplication.getInstance(), resourceId, duration);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
            }
        });
    }

    public static void showToast(final String content, final int duration) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(BaseApplication.getInstance(), content, duration);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
            }
        });
    }

    public static void showToast(int resourceId) {
        showToast(resourceId, Toast.LENGTH_SHORT);
    }

    public static void showToast(String content) {
        showToast(content, Toast.LENGTH_SHORT);
    }
}
