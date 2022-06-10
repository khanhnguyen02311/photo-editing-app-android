package com.example.photoeditingapp_main._Classes;

import android.app.Application;
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
    //ContextWrapper contextWrapper = new ContextWrapper(this);
    //File privateDir = contextWrapper.getDir("studioImages", Context.MODE_PRIVATE);

    public ArrayList<GeneralPictureItem> listStudioItem = new ArrayList<>();
    public File studioLocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + "StewdioImages");
    private FirebaseFirestore firestoreDB;
    private _LocalDatabase localDB;

    private final int REQUEST_CODE_PERMISSION = 101; //Read, modify and delete storage
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
        //localDB.resetTables();  for test database

        firestoreDB = FirebaseFirestore.getInstance();
        if (!studioLocation.exists()) studioLocation.mkdirs();
        String[] listImages = new String[]{};
        try {
            listImages = getAssets().list("TestImages");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }
}

