package com.mountisome.aquareminder.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLHelper extends SQLiteOpenHelper {

    private Context context;

    public MySQLHelper(Context context) {
        super(context, SQLCon.DB_NAME, null, SQLCon.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCon.CREATE_TABLE); // 创建表
        db.execSQL(SQLCon.INSERT); // 插入数据
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
