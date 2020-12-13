package com.example.lenta;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.LinkedList;
import java.util.List;

public class FlickrPositionDataSource extends PositionalDataSource<FlickrPhoto> {
    private IPhotoDataSource nDataSource;

    private FlickrPhotoDAO db;
    private int pageSize;
    FlickrPositionDataSource(IPhotoDataSource nDataSource, LocalDataBase lDataSource)
    {
        this.nDataSource = nDataSource;
        this.db = lDataSource.flickrPhotoDAO();
    }

    private List<FlickrPhoto> fromLiveData(List<FlickrPhoto> data)
    {
        for(FlickrPhoto fPhoto: data) {
            FlickrPhoto fbyId = db.getByID(fPhoto.getId());

            if(fbyId != null) {
                fPhoto.setIsLike(fbyId.getIsLike());
            }
        }

        return data;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<FlickrPhoto> callback) {
        Log.d("loadData", "loadInitial," +
                " requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        pageSize = params.pageSize;

        int pageCountNeed = params.requestedLoadSize/params.pageSize;
        int pageStart = params.requestedStartPosition == 0 ? 0 :params.requestedStartPosition/params.pageSize;

        List<FlickrPhoto> loadDataFromServer = new LinkedList<>();
        for(int pageIndex = 0; pageIndex < pageCountNeed; pageIndex++ )
        {
            List<FlickrPhoto> data = nDataSource.getDataByPage(
                    pageStart + pageIndex,
                    pageSize);

            loadDataFromServer.addAll(data);
        }
        callback.onResult(fromLiveData(loadDataFromServer), params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<FlickrPhoto> callback) {
        Log.d("loadData", "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        List<FlickrPhoto> data = nDataSource.getDataByPage(params.startPosition == 0 ? 0: params.startPosition/pageSize, pageSize);
        callback.onResult(fromLiveData(data));
    }
}
