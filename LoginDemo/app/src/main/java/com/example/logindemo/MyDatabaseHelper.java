package com.example.logindemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_USER = "user";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PWD = "password";

    // 创建用户表的SQL
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
            COLUMN_PWD + " TEXT NOT NULL)";

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        // 预埋测试账号：email=test@123.com，password=123456
        db.execSQL("INSERT INTO " + TABLE_USER + " (" + COLUMN_EMAIL + ", " + COLUMN_PWD + ") VALUES ('test@123.com', '123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}