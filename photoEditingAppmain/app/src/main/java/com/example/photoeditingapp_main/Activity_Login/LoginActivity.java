package com.example.photoeditingapp_main.Activity_Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.GlobalVariables;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout activityLayout;

    private final int REQUEST_CODE_PERMISSION = 101; //Read, modify and delete storage
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
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

        activityLayout = findViewById(R.id.activity_login_layout);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION);
            if (!allPermissionGranted()) finishAndRemoveTask();
        }
    }
}