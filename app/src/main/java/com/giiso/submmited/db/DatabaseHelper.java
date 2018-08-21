package com.giiso.submmited.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.giiso.submmited.utils.TDevice;

/**
 * 数据库
 *
 * @author kymjs
 *         <p>
 *         update:2014-01-12 updateor: fireant 内容：修改为全应用数据库
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mInstance;
    private static final int VERSION = TDevice.getVersionCode();
    public static final String DATABASE_NAME = "dailysunshine";
    public static final String LOG_TABLE_NAME = "log";
    public static final String HTTP_LOG_TABLE_NAME = "http_log";

    public static final String CREATE_LOG_TABLE = "create table "
            + LOG_TABLE_NAME
            + " (_id integer primary key autoincrement,"
            + " time varchar(20), message text)";
    public static final String CREATE_HTTP_LOG_TABLE = "create table "
            + HTTP_LOG_TABLE_NAME
            + " (_id integer primary key autoincrement,"
            + " time varchar(20), type varchar(10) ,url text,head text ,body text ,result text )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public synchronized static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOG_TABLE);
        db.execSQL(CREATE_HTTP_LOG_TABLE);
        // db.execSQL(CREATE_NEWS_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+LOG_TABLE_NAME);
        db.execSQL("drop table if exists "+HTTP_LOG_TABLE_NAME);
        onCreate(db);
    }

}