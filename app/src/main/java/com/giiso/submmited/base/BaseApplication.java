package com.giiso.submmited.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.giiso.submmited.BuildConfig;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.utils.ActivityUtil;
import com.giiso.submmited.utils.ActivityUtils;
import com.mob.MobSDK;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by jayce on 2017/11/8.
 */

public class BaseApplication extends Application {
    private static final String PREF_NAME = "creativelocker.pref";

    private static BaseApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }

    protected String getAppkey() {
        return null;
    }
    protected String getAppSecret() {
        return null;
    }

    private void init() {
        //初始化activity 管理工具
        ActivityUtil.getInstance();
        //初始化夜间模式切换类
        SkinCompatManager.withoutActivity(this)                       // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
        //初始化友盟统计 禁止默认的页面统计方式
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);

        //share 分享 第三方登录
        MobSDK.init(this, this.getAppkey(), this.getAppSecret());
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:友盟 app key
         * 参数3:友盟 channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this, "58edcfeb310c93091c000be2", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
    }

    public synchronized static BaseApplication getInstance() {
        return application;
    }

    public BaseActivity getCurrentActivity() {
        BaseActivity currentActivity = null;
        if ( ActivityUtils.getsActivityList() != null && ActivityUtils.getsActivityList().size()>0) {
            currentActivity = (BaseActivity) ActivityUtils.getsActivityList().get(ActivityUtils.getsActivityList().size()-1);
        }
        return currentActivity;
    }


    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
        //SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void set(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
        //SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }
    public static void set(String key, float value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
        //SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
        //SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public static SharedPreferences getPreferences() {
        return getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
