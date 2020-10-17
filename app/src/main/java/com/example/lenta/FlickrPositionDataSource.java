package com.example.lenta;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.List;

public class FlickrPositionDataSource extends PositionalDataSource<FlickrPhoto> {
    private FlickrDAO dao;

    FlickrPositionDataSource(FlickrDAO dao)
    {
        this.dao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<FlickrPhoto> callback) {
        Log.d("loadInitial", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);

        List<FlickrPhoto> data = dao.getDataByPage(params.requestedStartPosition/20, 20);
        callback.onResult(data, 0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<FlickrPhoto> callback) {
        Log.d("loadRange", "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        List<FlickrPhoto> data = dao.getDataByPage(params.startPosition/20, 20);
        callback.onResult(data);
    }
}
