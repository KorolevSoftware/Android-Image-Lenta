package com.example.lenta;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class MyViewModel extends AndroidViewModel {
    private Repository repository;
    public MyViewModel (@NonNull Application application)
    {
        super(application);
        repository = new Repository(application);
    }

    public void update(FlickrPhoto flickrPhoto) {
        repository.update(flickrPhoto);
    }

    public void updateOrInsert(FlickrPhoto flickrPhoto)
    {
        repository.updateOrInsert(flickrPhoto);
    }

    public LiveData<PagedList<FlickrPhoto>> getPagedListLiveData() {
        return repository.getPagedListLiveData();
    }

    public void inser(FlickrPhoto flickrPhoto) {
        repository.insert(flickrPhoto);
    }
}