package com.example.photoeditingapp_main._Classes;

import android.net.Uri;

public class GeneralPictureItem {
    private int id;
    private Uri imageUri;
    private String imageName;
    private ConfigParameters imageConfigs;

    public int getId() {return id;}

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public Uri getImageUri() {return imageUri;}
    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public ConfigParameters getImageConfigs() { return imageConfigs; }

    public GeneralPictureItem() {
        id = -1;
        imageName = null;
        imageUri = null;
        imageConfigs = null;
    }

    public GeneralPictureItem(int i, String n, Uri u, ConfigParameters cfg) {
        id = i;
        imageName = n;
        imageUri = u;
        imageConfigs = cfg;
    }
}
