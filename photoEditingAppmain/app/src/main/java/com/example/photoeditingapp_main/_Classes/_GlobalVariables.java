package com.example.photoeditingapp_main._Classes;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

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
    private FirebaseStorage storageDB;
    private _LocalDatabase localDB;

    public FirebaseFirestore getFirestoreDB() {return firestoreDB;}
    public FirebaseStorage getStorageDB() {return storageDB;}
    public _LocalDatabase getLocalDB() {return localDB;}

    @Override
    public void onCreate() {
        super.onCreate();
        localDB = new _LocalDatabase(this);
        firestoreDB = FirebaseFirestore.getInstance();
        storageDB = FirebaseStorage.getInstance();
        //localDB.resetTables();

        contextWrapper = new ContextWrapper(this);
        privateLocation = contextWrapper.getDir("tempImages", Context.MODE_PRIVATE);
        publicLocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());

        if (!publicLocation.exists()) publicLocation.mkdirs();
        if (!privateLocation.exists()) privateLocation.mkdirs();
        /*String[] listImages = new String[]{};
        try {
            listImages = getAssets().list("TestImages");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}

