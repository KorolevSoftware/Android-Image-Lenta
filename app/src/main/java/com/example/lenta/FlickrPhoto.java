package com.example.lenta;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class FlickrPhoto implements Parcelable {
    private String urlSmallImage;
    private String urlMediumImage;
    private Bitmap image;
    private String id;
    private FlickrLSSetter likeSetter;

    public FlickrPhoto(String urlSmallImage, String urlMediumImage, Bitmap image, String id, int isLike, FlickrLSSetter likeSetter) {
        this.urlSmallImage = urlSmallImage;
        this.urlMediumImage = urlMediumImage;
        this.image = image;
        this.id = id;
        this.likeSetter = likeSetter;
    }

    protected FlickrPhoto(Parcel in) {
        urlSmallImage = in.readString();
        urlMediumImage = in.readString();
        id = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(urlSmallImage);
        dest.writeString(urlMediumImage);
        dest.writeString(id);
        dest.writeParcelable(image, flags);
    }

    public static final Creator<FlickrPhoto> CREATOR = new Creator<FlickrPhoto>() {
        @Override
        public FlickrPhoto createFromParcel(Parcel in) {
            return new FlickrPhoto(in);
        }

        @Override
        public FlickrPhoto[] newArray(int size) {
            return new FlickrPhoto[size];
        }
    };

    public String getUrlSmallImage() {
        return urlSmallImage;
    }

    public String getUrlMediumImage() {
        return urlMediumImage;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public boolean hasLike()
    {
        return likeSetter.hasLike(getId());
    }
    public boolean cklickLike()
    {

        if(hasLike())
            likeSetter.likeDisable(getId());
        else
            likeSetter.likeEnable(getId());

        return hasLike();
    }

    public void setLikeSetter(FlickrLSSetter likeSetter) {
        this.likeSetter = likeSetter;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
