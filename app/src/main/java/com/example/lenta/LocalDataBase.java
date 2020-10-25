package com.example.lenta;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {FlickrPhoto.class}, version = 1)
public abstract class LocalDataBase extends RoomDatabase {
    public abstract FlickrPhotoDAO flickrPhotoDAO();

}
