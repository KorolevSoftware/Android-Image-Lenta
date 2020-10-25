package com.example.lenta;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.List;

public class FlickrPositionDataSource extends PositionalDataSource<FlickrPhoto> {
    private FlickrDataSource dao;

    FlickrPositionDataSource(FlickrDataSource dao)
    {
        this.dao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<FlickrPhoto> callback) {
        Log.d("loadData", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        List<FlickrPhoto> data = dao.getDataByPage(params.requestedStartPosition,  params.requestedLoadSize);
        callback.onResult(data, params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<FlickrPhoto> callback) {
        Log.d("loadData", "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        List<FlickrPhoto> data = dao.getDataByPage(params.startPosition, params.loadSize);
        callback.onResult(data);
    }
}
