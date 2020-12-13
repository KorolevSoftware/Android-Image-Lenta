package com.example.lenta;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity
public class FlickrPhoto extends BaseObservable implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private long id;
    private int isLike;

    @SerializedName("secret")
    private String secret;


    public FlickrPhoto(long id, int isLike, String secret) {
        this.isLike = isLike;
        this.id = id;
        this.secret = secret;
    }

    protected FlickrPhoto(Parcel in) {
        id = in.readLong();
        isLike = in.readInt();
        secret = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(isLike);
        dest.writeString(secret);
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

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    @SuppressLint("DefaultLocale")
    @Bindable
    public String getQuadImageUrl() {
        return String.format("https://live.staticflickr.com/7023/%d_%s_q.jpg", id, secret);
       
    }

    @SuppressLint("DefaultLocale")
    @Bindable
    public String getMediumImageUrl() {
        return String.format("https://live.staticflickr.com/7023/%d_%s_m.jpg", id, secret);

    }
    public String getSecret() {
        return secret;
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
        this(other.getId(), other.getIsLike(), other.getSecret());
    }

    public boolean hasLike() {
        return isLike > 0;
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
