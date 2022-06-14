package com.example.photoeditingapp_main.Activity_Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.photoeditingapp_main.Activity_Login.LoginActivity;
import com.example.photoeditingapp_main.Activity_Mainpage.MainpageActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Transaction;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.framed.FrameReader;

import java.util.ArrayList;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    private _GlobalVariables gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        gv = (_GlobalVariables) getApplication();

        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> deviceAccount = gv.getLocalDB().getActiveUser();
                //Log.i(deviceAccount.get(0), deviceAccount.get(1));
                if (deviceAccount.size() == 0) {
                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); clear backstack
                    startActivity(intent);
                } else {
                    gv.getFirestoreDB().collection("users")
                            .whereEqualTo("usr", deviceAccount.get(0)).whereEqualTo("psw", deviceAccount.get(1)).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot snapshot) {
                                    if (!snapshot.isEmpty()) {
                                        Intent intent = new Intent(getApplication(), MainpageActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("DB_ERROR", "Error getting data: ", e);
                                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                }
            }
        },700);
    }
}