package com.giiso.submmited.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.giiso.submmited.base.AppConfig;
import com.giiso.submmited.bean.http.HttpLog;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2018/6/25.
 */

public class HttpLogDao {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private static HttpLogDao mDao = null;
    public static final String HTTP_LOG_TABLE_NAME = "http_log";
    public static final String TIME = "time";
    public static final String ID = "_id";
    public static final String TYPE = "type";
    public static final String HEAD = "head";
    public static final String BODY = "body";
    public static final String RESULT = "result";
    public static final String URL = "url";
    public static final int NUM = 100;

    private HttpLogDao(Context context) {
        super();
        dbHelper = DatabaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public static synchronized HttpLogDao getInstance(Context context) {
        if (mDao == null) {
            mDao = new HttpLogDao(context);
        }
        return mDao;
    }

    public long insert(HttpLog httpLog) {
        long id = -1;
        ContentValues values = new ContentValues();
        long time = Calendar.getInstance().getTimeInMillis();
        values.put(TIME, "" + time);
        values.put(TYPE, httpLog.getType());
        values.put(URL, httpLog.getUrl());
        values.put(HEAD, httpLog.getHead());
        values.put(BODY, httpLog.getBody());
        values.put(RESULT, httpLog.getResult());
        if (AppConfig.HTTP_LOG_OPEN) {
            id = db.insert(HTTP_LOG_TABLE_NAME, null, values);
        }
        long num = allCaseNum();
        for (int i = 0; i < num - NUM; i++) {
            delectMin();
        }
        return id;
    }

    public void delect(long id) {
        if (AppConfig.HTTP_LOG_OPEN) {
            db.delete(HTTP_LOG_TABLE_NAME, ID + "= ?", new String[]{String.valueOf(id)});
        }
    }

    public void delectTime(long time) {
        if (AppConfig.HTTP_LOG_OPEN) {
            db.delete(HTTP_LOG_TABLE_NAME, ID + "= ?", new String[]{String.valueOf(time)});
        }
    }

    //num 删除id最小的一条
    public void delectMin() {
        if (AppConfig.HTTP_LOG_OPEN) {
            db.delete(HTTP_LOG_TABLE_NAME, ID + "=(select min(" + ID + ") from " + HTTP_LOG_TABLE_NAME + ")", null);
        }
    }

    /**
     * 查询数据库中的总条数.
     *
     * @return
     */
    public long allCaseNum() {
        long count = 0;
        if (AppConfig.HTTP_LOG_OPEN) {
            String sql = "select count(*) from " + HTTP_LOG_TABLE_NAME;
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            count = cursor.getLong(0);
            cursor.close();
        }
        return count;
    }

    public ArrayList<HttpLog> queryAll() {
        Cursor cursor = db.query(HTTP_LOG_TABLE_NAME, null, null, null, null, null, ID + " desc");
        ArrayList<HttpLog> list = new ArrayList<HttpLog>();
        if (AppConfig.HTTP_LOG_OPEN) {
            while (cursor.moveToNext()) {
                HttpLog errorLog = new HttpLog();
                String type = cursor.getString(cursor.getColumnIndex(TYPE));
                String head = cursor.getString(cursor.getColumnIndex(HEAD));
                String body = cursor.getString(cursor.getColumnIndex(BODY));
                String result = cursor.getString(cursor.getColumnIndex(RESULT));
                String time = cursor.getString(cursor.getColumnIndex(TIME));
                String id = cursor.getString(cursor.getColumnIndex(ID));
                String url = cursor.getString(cursor.getColumnIndex(URL));
                errorLog.setTime(Long.parseLong(time));
                errorLog.setId(Long.parseLong(id));
                errorLog.setType(type);
                errorLog.setBody(body);
                errorLog.setResult(result);
                errorLog.setHead(head);
                errorLog.setUrl(url);
                list.add(errorLog);
            }
            cursor.close();
        }
        return list;
    }

    public void deleteAll() {
        if (AppConfig.HTTP_LOG_OPEN) {
            db.delete(HTTP_LOG_TABLE_NAME, null, null);
        }
    }
}
