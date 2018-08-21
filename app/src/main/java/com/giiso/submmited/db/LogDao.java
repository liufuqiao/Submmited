package com.giiso.submmited.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.giiso.submmited.bean.log.ErrorLog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/10/27.
 */

public class LogDao {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private static LogDao mDao = null;
    public static final String LOG_TABLE_NAME = "log";
    public static final String TIME = "time";
    public static final String ID = "_id";
    public static final String MESSGE = "message";

    private LogDao(Context context) {
        super();
        dbHelper = DatabaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }
    public static synchronized LogDao getInstance(Context context) {
        if (mDao == null) {
            mDao = new LogDao(context);
        }
        return mDao;
    }
    public long insert(String message) {
        long id = -1;
        ContentValues values = new ContentValues();
        values.put(MESSGE, message);
        long time = Calendar.getInstance().getTimeInMillis();
        values.put(TIME, "" + time);
        id = db.insert(LOG_TABLE_NAME, null, values);
        return id;
    }

    public void delect(long id) {
        db.delete(LOG_TABLE_NAME, ID+"= ?", new String[]{String.valueOf(id)});
    }
    public void delectTime(long time) {
        db.delete(LOG_TABLE_NAME, ID+"= ?", new String[]{String.valueOf(time)});
    }
    //num 删除id最小的一条
    public void delectMin() {
        db.delete(LOG_TABLE_NAME, ID+"=(select min("+ID+") from "+LOG_TABLE_NAME+")", null);
    }
    /**
     * 查询数据库中的总条数.
     * @return
     */
    public long allCaseNum( ){
        String sql = "select count(*) from "+LOG_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }
    public ArrayList<ErrorLog> queryAll() {
        Cursor cursor = db.query(LOG_TABLE_NAME, null, null, null, null, null, ID+" desc");
        ArrayList<ErrorLog> list = new ArrayList<ErrorLog>();
        while (cursor.moveToNext()) {
            ErrorLog errorLog = new ErrorLog();
            String message = cursor.getString(cursor.getColumnIndex(MESSGE));
            String time = cursor.getString(cursor.getColumnIndex(TIME));
            String id = cursor.getString(cursor.getColumnIndex(ID));
            errorLog.setTime(Long.parseLong(time));
            errorLog.setId(Long.parseLong(id));
            errorLog.setMeaagse(message);
            list.add(errorLog);
        }
        cursor.close();
        return list;
    }

    public void deleteAll() {
        db.delete(LOG_TABLE_NAME, null, null);
    }
}
