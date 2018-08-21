package com.giiso.submmited.utils;

import android.text.TextUtils;

import com.giiso.submmited.base.BaseApplication;


/**
 * Created by Administrator on 2017/9/18.
 */

public class AppInfoTidUtil {
    public static AppInfoTidUtil appInfoTidUtil;
    //tid
    public static final String TID = "tid";
    private String tid;

    public static synchronized AppInfoTidUtil getInstance() {
        if (appInfoTidUtil == null)
            appInfoTidUtil = new AppInfoTidUtil();
        return appInfoTidUtil;
    }

    public String getTid() {
        if (TextUtils.isEmpty(tid)) {
            tid = BaseApplication.get(TID, "");
        }
        return tid;
    }

    public void setTid(String tid) {
        BaseApplication.set(TID, tid);
        this.tid = tid;
    }
}
