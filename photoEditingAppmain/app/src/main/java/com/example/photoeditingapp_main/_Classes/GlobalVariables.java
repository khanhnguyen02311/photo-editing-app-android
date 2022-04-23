package com.example.photoeditingapp_main._Classes;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class GlobalVariables extends Application {
    FirebaseDatabase firebaseDTB = FirebaseDatabase.getInstance();
    //LocalDatabase sqliteDTB = new LocalDatabase(this);
}
