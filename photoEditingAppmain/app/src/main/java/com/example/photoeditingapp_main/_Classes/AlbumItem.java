package com.example.photoeditingapp_main._Classes;

import java.io.Serializable;
import java.util.List;

public class AlbumItem implements Serializable {
    private String albumName;
    private List<SliderItem> albumImages;

    public AlbumItem(String albumName, List<SliderItem> albumImages) {
        this.albumName = albumName;
        this.albumImages = albumImages;
    }

    public String getAlbumName() {
        return albumName;
    }

    public List<SliderItem> getAlbumImages() {
        return albumImages;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAlbumImages(List<SliderItem> albumImages) {
        this.albumImages = albumImages;
    }
}
