package com.giiso.submmited.utils;

import android.text.TextUtils;

import com.giiso.submmited.BuildConfig;

public class Log {
    private static final String LOG_TAG = "GiisoVentureLog";
    private static boolean DEBUG = BuildConfig.DEBUG;

    private Log() {
    }

    public static void error(String log) {
        if (DEBUG && !TextUtils.isEmpty(log)) android.util.Log.e(LOG_TAG, "" + log);
    }

    public static void log(String log) {
        if (DEBUG && !TextUtils.isEmpty(log)) android.util.Log.i(LOG_TAG, log);
    }

    public static void log(String tag, String log) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(log)) android.util.Log.i(tag, log);
    }

    public static void d(String tag, String log) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(log)) android.util.Log.d(tag, log);
    }

    public static void e(String tag, String log) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(log)) android.util.Log.e(tag, log);
    }

    public static void i(String tag, String log) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(log)) android.util.Log.i(tag, log);
    }
}
