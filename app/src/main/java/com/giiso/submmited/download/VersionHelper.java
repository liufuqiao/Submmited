package com.giiso.submmited.download;

import android.content.Context;

import com.giiso.submmited.cache.SharedPreferencesHelper;

/**
 * Created by Administrator on 2017/12/11.
 */

public class VersionHelper {
    private Version version;
    private static VersionHelper versionHelper;

    public static synchronized VersionHelper getInstance() {
        if (versionHelper == null) {
            versionHelper = new VersionHelper();
        }
        return versionHelper;
    }

    public synchronized  Version getVersion(Context context) {
        if (versionHelper == null) {
            return null;
        }
        if (versionHelper.version == null) {
            versionHelper.version = SharedPreferencesHelper.loadFormSource(context, Version.class);
        }
        if (versionHelper.version == null) {
            versionHelper.version = null;
        }
        return versionHelper.version;
    }

    public  boolean updateVersionCache(Context context, Version version) {
        versionHelper.version = version;
        return SharedPreferencesHelper.save(context, version);
    }

    public  void clearVersionCache(Context context) {
        versionHelper.version = null;
        SharedPreferencesHelper.remove(context, Version.class);
    }
}
