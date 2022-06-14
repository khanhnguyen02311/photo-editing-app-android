package com.example.photoeditingapp_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.photoeditingapp_main.Activity_Login.LoginActivity;
import com.google.firebase.database.Transaction;
import com.squareup.okhttp.internal.framed.FrameReader;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        },700);
    }
}