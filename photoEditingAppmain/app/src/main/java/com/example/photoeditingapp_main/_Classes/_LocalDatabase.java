package com.example.photoeditingapp_main._Classes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class _LocalDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DATABASE_Stewdio";
    public static final String TABLE_STUDIO = "TABLE_ImageStudio";
    public static final String TABLE_CONFIG = "TABLE_ImageConfig";
    public static final String TABLE_ACCOUNT = "TABLE_Account";

    public static final String COLUMN_Studio_ID = "ID";
    public static final String COLUMN_Studio_ImageName = "NAME";
    public static final String COLUMN_Studio_ImageUri = "URI";
    public static final String COLUMN_Studio_ImageConfig = "CONFIG_REFERENCE";

    public static final String COLUMN_Config_ID = "ID";
    public static final String[] COLUMN_Configs;

    public static final String COLUMN_Account_ID = "ID";
    public static final String COLUMN_Account_Password = "PASSWORD";

    static { COLUMN_Configs = new String[] {
                "EXPOSURE",
                "TEMPERATURE",
                "TINT",
                "CONTRAST",
                "SATURATION",
                "VIBRANCE",
                "HIGHLIGHT",
                "SHADOW",
                "HUE",
                "RGB_R",
                "RGB_G",
                "RGB_B",
                "SHARPNESS",
                "VIGNETTE_START",
                "VIGNETTE_END"
    };}

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";
    public static final String SQL_SELECT_TABLE = "SELECT * FROM ";
    public static final String SQL_CREATE_TABLE_CONFIG =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CONFIG + " (" +
            COLUMN_Config_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_Configs[0] + " FLOAT, " +
            COLUMN_Configs[1] + " FLOAT, " +
            COLUMN_Configs[2] + " FLOAT, " +
            COLUMN_Configs[3] + " FLOAT, " +
            COLUMN_Configs[4] + " FLOAT, " +
            COLUMN_Configs[5] + " FLOAT, " +
            COLUMN_Configs[6] + " FLOAT, " +
            COLUMN_Configs[7] + " FLOAT, " +
            COLUMN_Configs[8] + " FLOAT, " +
            COLUMN_Configs[9] + " FLOAT, " +
            COLUMN_Configs[10] + " FLOAT, " +
            COLUMN_Configs[11] + " FLOAT, " +
            COLUMN_Configs[12] + " FLOAT, " +
            COLUMN_Configs[13] + " FLOAT, " +
            COLUMN_Configs[14] + " FLOAT)";
    public static final String SQL_CREATE_TABLE_STUDIO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STUDIO + " (" +
            COLUMN_Studio_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_Studio_ImageName + " NVARCHAR(255), " +
            COLUMN_Studio_ImageUri + " NVARCHAR(255), " +
            COLUMN_Studio_ImageConfig + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_Studio_ImageConfig + ") REFERENCES " + TABLE_CONFIG + "(" + COLUMN_Config_ID + "))";
    public static final String SQL_CREATE_TABLE_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + " (" +
            COLUMN_Account_ID + " NVARCHAR(255), " +
            COLUMN_Account_Password + " NVARCHAR(255))";


    public _LocalDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONFIG);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_STUDIO);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLE + TABLE_STUDIO);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE + TABLE_CONFIG);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE + TABLE_ACCOUNT);
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
        onQueryData(SQL_CREATE_TABLE_CONFIG);
        onQueryData(SQL_CREATE_TABLE_STUDIO);
        onQueryData(SQL_CREATE_TABLE_ACCOUNT);
    }

    public void resetTables() {
        onQueryData(SQL_DROP_TABLE + TABLE_STUDIO);
        onQueryData(SQL_DROP_TABLE + TABLE_CONFIG);
        onQueryData(SQL_DROP_TABLE + TABLE_ACCOUNT);
        createTables();
    }

    public GeneralPictureItem getLastAddedImage() {
        Cursor cursor = onGetData(SQL_SELECT_TABLE + TABLE_STUDIO + " ORDER BY " + COLUMN_Studio_ID + " DESC LIMIT 1");
        cursor.moveToFirst();
        GeneralPictureItem item = getImageItemFromCursor(cursor);
        cursor.close();
        return item;
    }

    public ConfigParameters getLastAddedConfig() {
        Cursor cursor = onGetData(SQL_SELECT_TABLE + TABLE_CONFIG + " ORDER BY " + COLUMN_Config_ID + " DESC LIMIT 1");
        cursor.moveToFirst();
        ConfigParameters config = getConfigItemFromCursor(cursor);
        cursor.close();
        return config;
    }

    public GeneralPictureItem getImageFromTable(int id) {
        Cursor cursor = onGetData(SQL_SELECT_TABLE + TABLE_STUDIO + " WHERE " + COLUMN_Studio_ID + " = " + id);
        cursor.moveToFirst();
        GeneralPictureItem item = getImageItemFromCursor(cursor);
        cursor.close();
        return item;
    }

    public ConfigParameters getConfigFromTable(int id) {
        Cursor cursor = onGetData(SQL_SELECT_TABLE + TABLE_CONFIG + " WHERE " + COLUMN_Config_ID + " = " + id);
        cursor.moveToFirst();
        ConfigParameters config = getConfigItemFromCursor(cursor);
        cursor.close();
        return config;
    }

    public ArrayList<String> getActiveUser() {
        Cursor cursor = onGetData(SQL_SELECT_TABLE + TABLE_ACCOUNT);
        ArrayList<String> account = new ArrayList<>();
        if (cursor.moveToFirst()) {
            account.add(cursor.getString(0));
            account.add(cursor.getString(1));
        }
        return account;
    }

    public void clearActiveUser() {
        onQueryData(SQL_DROP_TABLE + TABLE_ACCOUNT);
        onQueryData(SQL_CREATE_TABLE_ACCOUNT);
    }

    public int getImageStudioSize() {
        Cursor cursor = onGetData("SELECT COUNT(*) FROM " + TABLE_STUDIO);
        cursor.moveToFirst();
        int temp = cursor.getInt(0);
        cursor.close();
        return temp;
    }

    public int getConfigStudioSize() {
        Cursor cursor = onGetData("SELECT COUNT(*) FROM " + TABLE_CONFIG);
        cursor.moveToFirst();
        int temp = cursor.getInt(0);
        cursor.close();
        return temp;
    }

    public boolean setActiveUser(String usr, String psw) {
        SQLiteDatabase dtb = getWritableDatabase();
        onQueryData(SQL_DROP_TABLE + TABLE_ACCOUNT);
        onQueryData(SQL_CREATE_TABLE_ACCOUNT);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Account_ID, usr);
        cv.put(COLUMN_Account_Password, psw);
        long insert = dtb.insert(TABLE_ACCOUNT, null, cv);
        return insert != -1;
    }

    public boolean addImageToStudio(String name, Uri _uri) {
        String uri = _uri.toString();
        SQLiteDatabase dtb = getWritableDatabase();
        long insert = dtb.insert(TABLE_STUDIO, null, createContentImage(name, uri));
        return insert != -1;
        //onQueryData("INSERT INTO " + STUDIO_TABLE + " (" + COLUMN_Studio_ImageName + ", " + COLUMN_Studio_ImageUri + ") VALUES ('" + name + "', '" + uri + "')");
    }

    public ArrayList<GeneralPictureItem> getImagesFromStudio(boolean isFlipped) {
        ArrayList<GeneralPictureItem> list = new ArrayList<>();
        Cursor cursor = onGetData(SQL_SELECT_TABLE + TABLE_STUDIO);
        if (cursor.moveToFirst()) { //move to first item, if have return true, else return false
            do {
                GeneralPictureItem item = getImageItemFromCursor(cursor);
                Log.i(Integer.toString(cursor.getPosition()), Objects.requireNonNull(item).getImageUri().toString());
                if (isFlipped) list.add(0, item); else list.add(item);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public boolean updateConfigToImage(int studioImageID, ConfigParameters cfg) {
        SQLiteDatabase dtb = getWritableDatabase();
        long insert = dtb.insert(TABLE_CONFIG, null, createConfigImage(cfg));
        if (insert != -1) {
            Cursor cursor = onGetData(SQL_SELECT_TABLE + TABLE_CONFIG + " ORDER BY " + COLUMN_Config_ID + " DESC LIMIT 1");
            if (cursor.moveToFirst()) {
                ContentValues foreignKey = new ContentValues();
                foreignKey.put(COLUMN_Studio_ImageConfig, cursor.getInt(0));
                long update = dtb.update(TABLE_STUDIO, foreignKey, COLUMN_Studio_ID+"=?", new String[]{Integer.toString(studioImageID)});
                cursor.close();
                return update != -1;
            } else {
                cursor.close();
                return false;
            }
        } else return false;
    }

    public boolean deleteImagesFromStudio(ArrayList<Integer> ids) {
        SQLiteDatabase dtb = getWritableDatabase();
        long deleteConfig = 0, deleteStudio = 0;
        for (int id: ids) {
            Cursor cursor = onGetData("SELECT "+TABLE_CONFIG+"."+COLUMN_Config_ID+" FROM "+TABLE_CONFIG+" WHERE "+TABLE_CONFIG+"."+COLUMN_Config_ID+" IN (SELECT "+TABLE_STUDIO+"."+COLUMN_Studio_ImageConfig+" FROM "+TABLE_STUDIO+" WHERE "+TABLE_STUDIO+"."+COLUMN_Studio_ID+" = "+id+")");
            if (cursor.moveToFirst()) {
                deleteConfig = dtb.delete(TABLE_CONFIG, COLUMN_Config_ID+"=?", new String[]{Integer.toString(cursor.getInt(0))});
            }
            deleteStudio = dtb.delete(TABLE_STUDIO, COLUMN_Studio_ID+"=?", new String[]{Integer.toString(id)});
            if (deleteConfig == -1 || deleteStudio == -1) return false;
        }
        return true;
    }


    private ContentValues createContentImage(String name, String uriString) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Studio_ImageName, name);
        cv.put(COLUMN_Studio_ImageUri, uriString);
        return cv;
    }

    private ContentValues createConfigImage(ConfigParameters cfg) {
        ContentValues cv = new ContentValues();
        for (int i=0; i<cfg.getConfigs().length; ++i)
            cv.put(COLUMN_Configs[i], cfg.getConfig(i));
        return cv;
    }

    private GeneralPictureItem getImageItemFromCursor(Cursor cursor) {
        int ID = cursor.getInt(0);
        String name = cursor.getString(1);
        Uri uri = Uri.parse(cursor.getString(2));
        if (cursor.isNull(3)) return new GeneralPictureItem(ID, name, uri, null);
        else {
            int configID = cursor.getInt(3);
            ConfigParameters configParameters = getConfigFromTable(configID);
            return new GeneralPictureItem(ID, name, uri, configParameters);
        }
    }

    private ConfigParameters getConfigItemFromCursor(Cursor cursor) {
        float[] listCfg = new float[15];
        int ID = cursor.getInt(0);
        for (int i=1; i<16; i++) listCfg[i - 1] = cursor.getFloat(i);
        return new ConfigParameters(ID, listCfg);
    }
}
