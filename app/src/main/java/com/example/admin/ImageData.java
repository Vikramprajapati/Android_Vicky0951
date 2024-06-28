package com.example.admin;

public class ImageData {

    private String imageUrl;

    public ImageData() {
        // Default constructor required for calls to DataSnapshot.getValue(ImageInfo.class)
    }

    public ImageData( String imageUrl) {

        this.imageUrl = imageUrl;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

