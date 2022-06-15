package com.example.photoeditingapp_main.Activity_Login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._GlobalVariables;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout activityLayout;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0) {
                for (int result: grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) finishAndRemoveTask();
                }
            }
        }
        else Log.i("WEIRD REQUEST CODE", Integer.toString(requestCode));
    }

    private final int REQUEST_CODE_PERMISSION = 101; //Read, modify and delete storage
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.CAMERA",
            "android.permission.INTERNET"
    };

    public boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(LoginActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION);
        }

        activityLayout = findViewById(R.id.activity_login_layout);

        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION);
            if (!allPermissionGranted()) finishAndRemoveTask();
        }*/
    }
}