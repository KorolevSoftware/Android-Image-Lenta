package com.example.lenta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Room;

import java.util.concurrent.Executors;

public class Repository {

    private final LocalDataBase dataBase;
    private final LiveData< PagedList<FlickrPhoto>> pagedListLiveData;
    private final FlickrPhotoDSFactory dataSourceFactory;

    public Repository(@NonNull Application application, int startPosition)
    {
        dataBase = Room.databaseBuilder(application,
                LocalDataBase.class, "database")
                .fallbackToDestructiveMigrationFrom(1) // BD version
                .build();


        IPhotoDataSource dao = new FlickrDataSourceRetrofit(application);
        dao.setSearchTile("cat"); // Search cat image

        dataSourceFactory = new FlickrPhotoDSFactory(dao, dataBase);

        PagedList.Config config = new PagedList.Config
                .Builder()
                .setMaxSize(500)              // Element count for dropped old page //Memory optimization
                .setEnablePlaceholders(false) // Use infinity scroll you dont know element count
                .setPageSize(50)              // Element count in page
                .setInitialLoadSizeHint(50)
                .build();

        pagedListLiveData = new LivePagedListBuilder<>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor()) //Load data from another thread
                .setInitialLoadKey(startPosition)
                .build();
    }

    public LiveData< PagedList<FlickrPhoto>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public void updatePageList() {
        dataSourceFactory.invalidate();
    }

    public void insert(FlickrPhoto flickrPhoto) {
        Runnable task = () -> {
            dataBase.flickrPhotoDAO().insert(flickrPhoto);
            updatePageList();
        };
        startTread(task);
    }

    public void update(FlickrPhoto flickrPhoto)  {
        Runnable task = () -> {
            dataBase.flickrPhotoDAO().update(flickrPhoto);
            updatePageList();
        };
        startTread(task);
    }


    public void updateOrInsert(FlickrPhoto flickrPhoto)
    {
        Runnable task = () -> {
            if(dataBase.flickrPhotoDAO().getByID(flickrPhoto.getId()) == null)
                dataBase.flickrPhotoDAO().insert(flickrPhoto);
            else
                dataBase.flickrPhotoDAO().update(flickrPhoto);
            updatePageList();
        };
        startTread(task);
    }

    private void startTread(Runnable run)
    {
        new Thread(run).start();
    }
}
