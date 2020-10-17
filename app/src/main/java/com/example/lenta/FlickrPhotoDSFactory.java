package com.example.lenta;

import androidx.paging.DataSource;

public class FlickrPhotoDSFactory extends DataSource.Factory<Integer, FlickrPhoto> {

    private final FlickrDAO flickrDAO;
    private final FlickrPositionDataSource flickrPositionDataSource;
    FlickrPhotoDSFactory(FlickrDAO flickrDAO) {
        this.flickrDAO = flickrDAO;
        this.flickrPositionDataSource = new FlickrPositionDataSource(flickrDAO);
    }

    public void invalidate()
    {
        flickrPositionDataSource.invalidate();
    }


    @Override
    public DataSource create() {
        return flickrPositionDataSource;
    }
}