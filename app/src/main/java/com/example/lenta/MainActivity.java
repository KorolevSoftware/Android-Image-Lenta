package com.example.lenta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.lenta.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MyViewModel.class);

        Log.d("start activity", "onCreate: -------------------");
        ImageScrollAdapter imageScrollAdapter = new ImageScrollAdapter(model, new FlickrPhotoDiffCallback());
        model.getPagedListLiveData().observe(this, flickrPhotos -> {
                Log.d("onChanged", "submit PagedList");
                imageScrollAdapter.submitList(flickrPhotos);
            }
        );

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.imageScrollView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        binding.imageScrollView.setAdapter(imageScrollAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle arguments = data.getExtras();

        if(arguments != null)
            model.updateOrInsert(arguments.getParcelable("photo"));
    }
}