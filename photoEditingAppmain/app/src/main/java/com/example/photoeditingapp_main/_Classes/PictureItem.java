package com.example.photoeditingapp_main._Classes;

public class PictureItem {
    private String id, author, imageLink;
    private boolean isLiked;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getImageLink() { return imageLink; }

    public void setImageLink(String imageLink) { this.imageLink = imageLink; }

    public boolean isLiked() { return isLiked; }

    public void setLiked(boolean liked) { isLiked = liked; }

    public PictureItem() {
        id = "";
        author = "no-name";
        imageLink = "";
        isLiked = false;
    }

    public PictureItem(String i, String aut, String image, boolean liked) {
        id = i;
        author = aut;
        imageLink = image;
        isLiked = liked;
    }
}
