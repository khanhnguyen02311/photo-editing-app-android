package com.example.photoeditingapp_main._Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocalDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "PhotoEditingApp_SQLite";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME;
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_SELECT_TABLE = "SELECT * FROM " + TABLE_NAME;

    public LocalDatabase(@Nullable Context context) {
        super(context, "PhotoEditingApp.sqlite", null, 1);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    //CREATE, INSERT, UPDATE, DELETE, ...
    public void onQueryData(String sql) {
        android.database.sqlite.SQLiteDatabase dtb = getWritableDatabase();
        dtb.execSQL(sql);
    }

    //SELECT, ...
    public Cursor onGetData(String sql) {
        android.database.sqlite.SQLiteDatabase dtb = getReadableDatabase();
        return dtb.rawQuery(sql, null);
    }

    public void createTable() { onQueryData(SQL_CREATE_TABLE); }
    public void resetTable() { onQueryData(SQL_DROP_TABLE); onQueryData(SQL_CREATE_TABLE); }
}
