package com.example.lenta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    private FlickrPhotoDSFactory dataSourceFactory;
    private ImageScrollAdapter imageScrollAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Flickr web loader
        LocalStorage localStorage =new LocalStorage(this);
        FlickrDAO dao = new FlickrDAO(localStorage.getWritableDatabase());
        dao.setSearchTile("cat");


        dataSourceFactory = new FlickrPhotoDSFactory(dao);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();

        LiveData< PagedList<FlickrPhoto> > pagedListLiveData = new LivePagedListBuilder<>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();

        imageScrollAdapter = new ImageScrollAdapter(this, new FlickrPhotoDiffCallback());
        pagedListLiveData.observe(this, flickrPhotos -> {
                Log.d("onChanged", "submit PagedList");
                imageScrollAdapter.submitList(flickrPhotos);
            }
        );
        RecyclerView recyclerView = findViewById(R.id.image_scroll_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(imageScrollAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageScrollAdapter.notifyDataSetChanged();
    }
}