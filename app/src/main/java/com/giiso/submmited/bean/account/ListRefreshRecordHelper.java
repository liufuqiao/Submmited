package com.giiso.submmited.bean.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

/**
 * 列表刷新记录
 * Created by Administrator on 2018/2/7.
 */

public class ListRefreshRecordHelper {
    private static final String PREF_NAME = "listRefresh.pref";
    public static final String INFO_NAME = "info_name_";
    public static ListRefreshRecordHelper listRefreshRecordHelper = null;
    private Context context;

    public static synchronized ListRefreshRecordHelper getInstance(Context context) {
        if (listRefreshRecordHelper == null) {
            listRefreshRecordHelper = new ListRefreshRecordHelper(context);
        }
        return listRefreshRecordHelper;
    }

    public void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public void clear() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }


    public ListRefreshRecordHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public SharedPreferences getPreferences() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
