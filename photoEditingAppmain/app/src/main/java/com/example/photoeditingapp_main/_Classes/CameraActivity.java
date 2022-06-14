package com.example.photoeditingapp_main._Classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.photoeditingapp_main.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {

    _GlobalVariables gv;

    PreviewView viewFinder;
    FloatingActionButton captureBtn;
    ImageButton cancelBtn, flipBtn;

    ListenableFuture<ProcessCameraProvider> cameraProvider;

    private ImageCapture imageCapture;
    private int cameraFacing = CameraSelector.LENS_FACING_BACK;

    private final String[] REQUIRED_PERMISSIONS = new String[] {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        gv = (_GlobalVariables) getApplication();

        viewFinder = findViewById(R.id.viewFinder);
        captureBtn = findViewById(R.id.captureBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        flipBtn = findViewById(R.id.flipCameraBtn);

        if (allPermissionGranted()) {
            cameraProvider = ProcessCameraProvider.getInstance(this);
            cameraProvider.addListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        ProcessCameraProvider cameraProvider1 = cameraProvider.get();
                        startCameraX(cameraProvider1, viewFinder);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, ContextCompat.getMainExecutor(this));

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            captureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takeImage();
                }
            });

            flipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cameraFacing == CameraSelector.LENS_FACING_BACK) cameraFacing = CameraSelector.LENS_FACING_FRONT;
                    else if (cameraFacing == CameraSelector.LENS_FACING_FRONT) cameraFacing = CameraSelector.LENS_FACING_BACK;
                    try {
                        ProcessCameraProvider cameraProvider1 = cameraProvider.get();
                        startCameraX(cameraProvider1, viewFinder);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private boolean allPermissionGranted() {
        for (String permission : REQUIRED_PERMISSIONS)
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider, PreviewView viewFinder) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(cameraFacing)
                .build();

        imageCapture = new ImageCapture.Builder().build();

        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    private void takeImage() {
        long timestamp = System.currentTimeMillis();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        cv.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        imageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(this.getContentResolver(), uri, cv).build(),
                ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        if (gv.getLocalDB().addImageToStudio(cv.get(MediaStore.MediaColumns.DISPLAY_NAME).toString(), Objects.requireNonNull(outputFileResults.getSavedUri()))) {
                            Log.i("IMAGE", uri.toString());
                            /*Snackbar snackbar = Snackbar.make(..., "Image saved to Studio", 1000);
                            snackbar.show();*/
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("IMAGE", uri.toString());
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Error", 1000);
                        snackbar.show();
                    }
                }
        );
    }
}