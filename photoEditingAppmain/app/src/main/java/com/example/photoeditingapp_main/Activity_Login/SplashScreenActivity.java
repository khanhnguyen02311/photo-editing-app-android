package com.example.photoeditingapp_main.Activity_Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.photoeditingapp_main.Activity_Login.LoginActivity;
import com.example.photoeditingapp_main.Activity_Mainpage.MainpageActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.google.firebase.database.Transaction;
import com.squareup.okhttp.internal.framed.FrameReader;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    private _GlobalVariables gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        gv = (_GlobalVariables) getApplication();

        /*final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gv.checkAccount()) {
                    Intent intent = new Intent(getApplication(), MainpageActivity.class);
                    startActivity(intent);
                }
            }
        },700);*/
    }
}