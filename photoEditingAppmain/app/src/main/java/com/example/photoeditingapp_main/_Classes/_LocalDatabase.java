package com.example.photoeditingapp_main._Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class _LocalDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DATABASE_Stewdio";
    public static final String STUDIO_TABLE = "TABLE_Studio";

    public static final String COLUMN_Studio_ID = "ID";
    public static final String COLUMN_Studio_ImageName = "NAME";
    public static final String COLUMN_Studio_ImageUri = "URI";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";
    public static final String SQL_SELECT_TABLE = "SELECT * FROM ";

    public _LocalDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE + STUDIO_TABLE + " (" +
                COLUMN_Studio_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_Studio_ImageName + " NVARCHAR(255), " +
                COLUMN_Studio_ImageUri + " NVARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLE + STUDIO_TABLE);
        onCreate(sqLiteDatabase);
    }

    //CREATE, INSERT, UPDATE, DELETE, ...
    public void onQueryData(String sql) {
        SQLiteDatabase dtb = getWritableDatabase();
        dtb.execSQL(sql);
    }

    //SELECT, ...
    public Cursor onGetData(String sql) {
        SQLiteDatabase dtb = getReadableDatabase();
        return dtb.rawQuery(sql, null);
    }

    public void createTables() {
        onQueryData(SQL_CREATE_TABLE + STUDIO_TABLE + " (" +
                COLUMN_Studio_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_Studio_ImageName + " NVARCHAR(255), " +
                COLUMN_Studio_ImageUri + " NVARCHAR(255))");
    }

    public void resetTables() {
        onQueryData(SQL_DROP_TABLE + STUDIO_TABLE);
        createTables();
    }

    public boolean addImageToStudio(String name, Uri _uri) {
        String uri = _uri.toString();
        SQLiteDatabase dtb = getWritableDatabase();
        long insert = dtb.insert(STUDIO_TABLE, null, createContentPicture(name, uri));
        return insert != -1;
        //onQueryData("INSERT INTO " + STUDIO_TABLE + " (" + COLUMN_Studio_ImageName + ", " + COLUMN_Studio_ImageUri + ") VALUES ('" + name + "', '" + uri + "')");
    }

    public ArrayList<GeneralPictureItem> getImagesFromStudio() {
        ArrayList<GeneralPictureItem> list = new ArrayList<>();
        Cursor cursor = onGetData(SQL_SELECT_TABLE + STUDIO_TABLE /*+ " ORDER BY " + COLUMN_Studio_ID + " DESC"*/);
        if (cursor.moveToFirst()) { //move to first item, if have return true, else return false
            do {
                GeneralPictureItem item = getItemFromCursor(cursor);
                list.add(item);
            } while (cursor.moveToNext());
        }
        return list;
    }


    public ContentValues createContentPicture(String name, String uriString) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Studio_ImageName, name);
        cv.put(COLUMN_Studio_ImageUri, uriString);
        return cv;
    }

    public GeneralPictureItem getItemFromCursor(Cursor cursor) {
        int ID = cursor.getInt(0);
        String name = cursor.getString(1);
        String _uri = cursor.getString(2);
        return new GeneralPictureItem(ID, name, Uri.parse(_uri));
    }
}
