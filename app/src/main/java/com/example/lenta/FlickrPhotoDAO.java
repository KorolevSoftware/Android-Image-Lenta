package com.example.lenta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FlickrPhotoDAO {
    @Query("select * from FlickrPhoto")
    List<FlickrPhoto> getAll();

    @Query("select * from FlickrPhoto where id =:id")
    FlickrPhoto getByID(long id);

    @Insert
    void insert(FlickrPhoto flickrPhoto);

    @Update
    void update(FlickrPhoto flickrPhoto);

    @Delete
    void delete(FlickrPhoto flickrPhoto);
}
