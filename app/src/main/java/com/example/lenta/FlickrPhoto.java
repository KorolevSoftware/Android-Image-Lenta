package com.example.lenta;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class FlickrPhoto extends BaseObservable implements Parcelable {

    @PrimaryKey
    private long id;
    private int isLike;

    @Ignore
    private String smallUrl;

    public FlickrPhoto(long id, int isLike) {
        this.id = id;
        this.isLike = isLike;
    }

    public FlickrPhoto(String smallUrl, long id, int isLike) {
        this.smallUrl = smallUrl;
        this.isLike = isLike;
        this.id = id;
    }

    protected FlickrPhoto(Parcel in) {
        id = in.readLong();
        isLike = in.readInt();
        smallUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(isLike);
        dest.writeString(smallUrl);
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

    @Bindable
    public String getSmallUrl() {
        return smallUrl;
    }

    @Bindable
    public long getId() {
        return id;
    }

    @Bindable
    public int getIsLike() {
        return isLike;
    }
    
    public FlickrPhoto( FlickrPhoto other)
    {
        this(other.getSmallUrl(), other.getId(), other.getIsLike());
    }
    
    public boolean hasLike() {
        return isLike > 0;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public boolean cklickLike() {
        if (isLike == 1)
            isLike = 0;
        else if (isLike == 0)
            isLike = 1;
        notifyPropertyChanged(BR._all);

        return isLike > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
