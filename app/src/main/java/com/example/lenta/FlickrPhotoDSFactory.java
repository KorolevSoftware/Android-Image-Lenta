package com.example.lenta;

import androidx.paging.DataSource;

public class FlickrPhotoDSFactory extends DataSource.Factory<Integer, FlickrPhoto> {

    private final IPhotoDataSource photoDataSource;
    private LocalDataBase dataBase;
    private FlickrPositionDataSource flickrPositionDataSource;

    public FlickrPhotoDSFactory(IPhotoDataSource photoDataSource, LocalDataBase dataBase) {
        this.photoDataSource = photoDataSource;
        this.dataBase = dataBase;
        this.flickrPositionDataSource = new FlickrPositionDataSource(photoDataSource, dataBase);
    }

    public void invalidate()
    {
        flickrPositionDataSource.invalidate();
    }

    @Override
    public DataSource create() {
        flickrPositionDataSource =  new FlickrPositionDataSource(photoDataSource, dataBase);
        return flickrPositionDataSource;
    }
}