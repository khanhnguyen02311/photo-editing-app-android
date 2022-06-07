package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;

import com.canhub.cropper.CropImageView;
import java.util.ArrayList;
import java.util.Arrays;

public class TransformFilter {
    Uri original_uri;
    CropImageView cropImageView;
    Bitmap image_bitmap;

    public TransformFilter(Uri uri, CropImageView imageView, Bitmap image) {
        original_uri = uri;
        cropImageView = imageView;
        image_bitmap = image;
        cropImageView.setAspectRatio(image_bitmap.getWidth(), image_bitmap.getHeight());
        cropImageView.setImageBitmap(image_bitmap);
    }

    public CropImageView getCropImageView() { return cropImageView; }

    public void setCropRatio(int ratioX, int ratioY) {
        if (image_bitmap.getWidth() < image_bitmap.getHeight())
        if (ratioX == 0) {
            cropImageView.setFixedAspectRatio(false);
        }
        else cropImageView.setAspectRatio(ratioX, ratioY);
    }
}
