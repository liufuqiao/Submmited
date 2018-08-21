package com.giiso.submmited.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Stack;

/**
 * Create by Arthur
 * Comment :Activity管理类
 * Date : 2017/6/8 10:02
 */
public class ActivityUtil
{
    private static volatile ActivityUtil mActivityUtil;
    private static Stack<Activity> mActivityStack;

    public static ActivityUtil getInstance() {
        if (mActivityUtil == null) {
            synchronized (ActivityUtil.class) {
                if (mActivityUtil == null) {
                    mActivityUtil = new ActivityUtil();
                }
            }
        }
        return mActivityUtil;
    }

    /**
     * function : 向mActivityStack栈内添加Activity
     * createTime : 2017/6/8 10:20
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * function : 从mActivityStack栈内移除Activity
     * createTime : 2017/6/8 10:23
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * function : 获取mActivityStack栈的最顶部Activity
     * createTime : 2017/6/8 10:26
     */
    public static Activity getTopActivity() {
        Activity activity = null;
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            activity = mActivityStack.get(mActivityStack.size() - 1);
        }
        return activity;
    }

    /**
     * function : 关闭mActivityStack栈内所有的Activity(打开过的)
     * createTime : 2017/6/8 10:29
     */
    public static void removeAll() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity activity : mActivityStack) {
                activity.finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * function : 跳转到下一个Activity
     * createTime : 2017/6/11 10:09
     *
     * @param cls         指定Activity界面
     * @param bundle      传递的参数
     * @param requestCode 请求码
     */

    public static void goNextActivity(Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getTopActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getTopActivity().startActivityForResult(intent, requestCode);
    }


    public static void goNextActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(getTopActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getTopActivity().startActivity(intent);
    }

    public static void goNextActivity(Class cls) {
        goNextActivity(cls, null);
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            removeAll();
            ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) { }
    }

}
