package com.example.photoeditingapp_main._Classes;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.photoeditingapp_main.Activity_Mainpage.MainpageActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public boolean enableBackPressed = true;

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
    }

    public String hashingAlgorithm(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) hexString.append(Integer.toHexString(0xFF & b));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}