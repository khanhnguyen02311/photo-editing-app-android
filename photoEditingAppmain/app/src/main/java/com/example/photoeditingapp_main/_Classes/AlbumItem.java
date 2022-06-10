package com.example.photoeditingapp_main._Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumItem implements Serializable {
    private String albumName;
    private ArrayList<GeneralPictureItem> albumImages;

    public AlbumItem(String albumName, ArrayList<GeneralPictureItem> albumImages) {
        this.albumName = albumName;
        this.albumImages = albumImages;
    }

    public String getAlbumName() {
        return albumName;
    }

    public ArrayList<GeneralPictureItem> getAlbumImages() {
        return albumImages;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public void setAlbumImages(ArrayList<GeneralPictureItem> albumImages) {this.albumImages = albumImages;}
}
