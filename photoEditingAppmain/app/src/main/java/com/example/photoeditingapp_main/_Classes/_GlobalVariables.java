package com.example.photoeditingapp_main._Classes;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class _GlobalVariables extends Application {
    //public ArrayList<GeneralPictureItem> listDiscoverItem = new ArrayList<>();

    ContextWrapper contextWrapper;
    public File publicLocation;
    public File privateLocation;
    private FirebaseFirestore firestoreDB;
    private _LocalDatabase localDB;

    private final String[] REQUIRED_PERMISSIONS = new String[] {
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    public FirebaseFirestore getFirestoreDB() {return firestoreDB;}
    public _LocalDatabase getLocalDB() {return localDB;}

    @Override
    public void onCreate() {
        super.onCreate();
        localDB = new _LocalDatabase(this);
        //localDB.resetTables();

        contextWrapper = new ContextWrapper(this);
        privateLocation = contextWrapper.getDir("tempImages", Context.MODE_PRIVATE);
        publicLocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + "StewdioImages");

        firestoreDB = FirebaseFirestore.getInstance();
        if (!publicLocation.exists()) publicLocation.mkdirs();
        if (!privateLocation.exists()) privateLocation.mkdirs();
        /*String[] listImages = new String[]{};
        try {
            listImages = getAssets().list("TestImages");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }
}

