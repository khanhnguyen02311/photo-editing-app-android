package com.example.photoeditingapp_main._Classes;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalVariables extends Application {
    FirebaseDatabase firebaseDTB;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        firebaseDTB = FirebaseDatabase.getInstance();
    }
}
