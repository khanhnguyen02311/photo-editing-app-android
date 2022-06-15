package com.example.photoeditingapp_main.Activity_Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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
                //Log.i("ACTIVEUSER", Integer.toString(deviceAccount.size()));
                if (deviceAccount.size() == 0) {

                    final String PREFS_NAME = "MyPrefsFile";

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                    if (settings.getBoolean("my_first_time", true)) {
                        //the app is being launched for first time, do something
                        Log.d("Comments", "First time");

                        // first time task
                        Intent intent = new Intent(getApplication(), WelcomeActivity.class);
                        startActivity(intent);
                        // record the fact that the app has been started at least once
                        settings.edit().putBoolean("my_first_time", false).commit();
                    }
                    else {
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        startActivity(intent);
                    }
                        /*Intent intent = new Intent(getApplication(), LoginActivity.class);
                        startActivity(intent);*/
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
                                        startActivity(intent);
                                }
                            });
                }
            }
        },700);
    }
}