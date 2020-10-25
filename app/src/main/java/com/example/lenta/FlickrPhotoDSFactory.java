package com.example.lenta;

import androidx.paging.DataSource;

public class FlickrPhotoDSFactory extends DataSource.Factory<Integer, FlickrPhoto> {

    private final FlickrDataSource flickrDataSource;
    private FlickrPositionDataSource flickrPositionDataSource;
    public FlickrPhotoDSFactory(FlickrDataSource flickrDataSource) {
        this.flickrDataSource = flickrDataSource;
        this.flickrPositionDataSource = new FlickrPositionDataSource(flickrDataSource);
    }

    public void invalidate()
    {
        flickrPositionDataSource.invalidate();
    }

    @Override
    public DataSource create() {
        flickrPositionDataSource =  new FlickrPositionDataSource(flickrDataSource);
        return flickrPositionDataSource;
    }
}