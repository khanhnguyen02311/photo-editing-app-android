package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;

import com.canhub.cropper.CropImageView;
import com.example.photoeditingapp_main.Activity_Design.DesignActivity;

public class TransformFilter {
    CropImageView cropImageView;
    Bitmap image_bitmap;
    Rect previousCrop;


    public TransformFilter(CropImageView imageView, Bitmap image) {
        cropImageView = imageView;
        image_bitmap = image;

        cropImageView.setFlippedHorizontally(false);
        cropImageView.setFlippedVertically(false);

        cropImageView.setAspectRatio(image_bitmap.getWidth(), image_bitmap.getHeight());
        cropImageView.setImageBitmap(image_bitmap);
        cropImageView.resetCropRect();
        cropImageView.setCropRect(previousCrop);
        cropImageView.setScaleType(CropImageView.ScaleType.CENTER_INSIDE);
    }

    public CropImageView getCropImageView() { return cropImageView; }

    public void setCropRatio(int index) {
        cropImageView.setFixedAspectRatio(index == 0);
    }

    public void flipImage(int index) {
        if (index == 0) cropImageView.flipImageHorizontally();
        else cropImageView.flipImageVertically();
    }

    public void rotateImage() {
        cropImageView.rotateImage(90);
    }

    public Bitmap getCroppedImage() {
        previousCrop = cropImageView.getCropRect();
        return cropImageView.getCroppedImage();
    }
}
