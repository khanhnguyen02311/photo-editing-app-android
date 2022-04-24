package com.example.photoeditingapp_main._Classes;

import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GlobalVariables extends Application {
    public FirebaseDatabase firebaseDTB;
    public ArrayList<PictureItem> listDiscoverItem = new ArrayList<>();
    public File tempImageStorage;

    private final int REQUEST_CODE_PERMISSION = 101; //Read, modify and delete storage
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        firebaseDTB = FirebaseDatabase.getInstance();

        tempImageStorage = new File(getExternalFilesDir(null) + "/" + "tempImages");
        if (!tempImageStorage.exists()) tempImageStorage.mkdir();

        String[] listImages = new String[]{};

        try {
            listImages = getAssets().list("TestImages");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Test adapter, discard later
        for (int i = 0; i < listImages.length; i++) {
            listDiscoverItem.add(new PictureItem(Integer.toString(i), randomName(), listImages[i], false));
        }
    }

    //Test adapter, discard later
    public String randomName() {
        ArrayList<String> firstName = new ArrayList<String>() {{ add("Nguyen"); add("Tran"); add("Trinh"); add("Hoang"); add("Huynh"); add("Mai"); add("Pham"); add("Le"); }};
        ArrayList<String> lastName = new ArrayList<String>() {{ add("Hoang"); add("Kiet"); add("Khoa"); add("Minh"); add("Tien"); add("Trong"); add("Khai"); add("Vy"); }};
        Random generator = new Random();
        return firstName.get(generator.nextInt(8)) + " " + lastName.get(generator.nextInt(8));
    }

    public boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }
}

