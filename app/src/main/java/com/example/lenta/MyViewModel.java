package com.example.lenta;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.paging.PagedList;


public class MyViewModel extends AndroidViewModel {
    private Repository repository;
    SharedPreferences sharedpreferences;
    private SavedStateHandle state;
    private SharedPreferences.Editor editor;
    public MyViewModel (@NonNull Application application, SavedStateHandle savedStateHandle)
    {
        super(application);
        sharedpreferences = getApplication().getSharedPreferences("preference_key", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        repository = new Repository(application, sharedpreferences.getInt("ScrollPos", 0));
        state = savedStateHandle;
    }

    public void update(FlickrPhoto flickrPhoto) {
        repository.update(flickrPhoto);
    }

    public void updateOrInsert(FlickrPhoto flickrPhoto)
    {
        repository.updateOrInsert(flickrPhoto);
    }

    public LiveData< PagedList<FlickrPhoto>> getPagedListLiveData() {
        return repository.getPagedListLiveData();
    }


    public void inser(FlickrPhoto flickrPhoto) {
        repository.insert(flickrPhoto);
    }

    public int loadScrollPos() {
        return sharedpreferences.getInt("ScrollPos", 0);
    }

    public void saveScrollPos(int position) {
        editor.putInt("ScrollPos", position);
        editor.apply();
    }
}