package com.giiso.submmited.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by jayce on 2017/11/16.
 */

public class AppUtil
{
    /**
     *
     * getVersionCode 功能描述: 获取当前AndroidManifest.xml下 android:versionCode 逻辑描述:
     * @param context 上下文对象
     * @return int 版本号
     * @since Version 2.0
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取应用版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * getVersionName 功能描述: 获取当前AndroidManifest.xml下 android:versionName 逻辑描述:
     *
     * @param context 上下文对象
     * @return String 版本名称
     * @since Version 2.0
     * */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取应用版本名称，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
