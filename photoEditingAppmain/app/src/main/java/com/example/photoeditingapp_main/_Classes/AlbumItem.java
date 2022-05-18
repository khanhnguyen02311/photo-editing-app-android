package com.example.photoeditingapp_main._Classes;

import java.io.Serializable;
import java.util.List;

public class AlbumItem implements Serializable {
    private String albumName;
    private List<ImageItem> albumImages;

    public AlbumItem(String albumName, List<ImageItem> albumImages) {
        this.albumName = albumName;
        this.albumImages = albumImages;
    }

    public String getAlbumName() {
        return albumName;
    }

    public List<ImageItem> getAlbumImages() {
        return albumImages;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAlbumImages(List<ImageItem> albumImages) {
        this.albumImages = albumImages;
    }
}
