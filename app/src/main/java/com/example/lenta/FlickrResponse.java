package com.example.lenta;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FlickrResponse {
    @SerializedName("photos")
    FlickrPhotos photos;

    public List<FlickrPhoto> getPhotos() {
        return photos.getPhoto();
    }

    static class FlickrPhotos {
        public List<FlickrPhoto> getPhoto() {
            return photo;
        }

        @SerializedName("photo")
        List<FlickrPhoto> photo;
    }
}