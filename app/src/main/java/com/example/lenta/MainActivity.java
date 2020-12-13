package com.example.lenta;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.lenta.databinding.ActivityMainBinding;

import java.security.PrivateKey;


public class MainActivity extends AppCompatActivity {
    private MyViewModel model;
    enum INTERNET {
        OFFLINEMOD,
        ONLINEMOD
    }

    private INTERNET internet;
    private GridLayoutManager grid;
    private ProgressBar progressBar;
    private boolean firstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        internet = INTERNET.ONLINEMOD;

        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(MyViewModel.class);
        Log.d("start activity", "onCreate: -------------------");
        ImageScrollAdapter imageScrollAdapter = new ImageScrollAdapter(model, new FlickrPhotoDiffCallback());
        model.getPagedListLiveData().observe(this, flickrPhotos -> {
            Log.d("onChanged", "submit PagedList");
            imageScrollAdapter.submitList(flickrPhotos);

            if(firstLoad) {
                grid.scrollToPosition(model.loadScrollPos());
                firstLoad = false;
            }
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            if(internet == INTERNET.OFFLINEMOD && isConnected()) {
                    Toast toast = Toast.makeText(this, "Соединение восстановлено", Toast.LENGTH_LONG);
                    toast.show();
                    internet = INTERNET.ONLINEMOD;
                }

                if(internet == INTERNET.ONLINEMOD && !isConnected()) {
                    Toast toast = Toast.makeText(this, "Соединение отсутствует, автономный режим", Toast.LENGTH_LONG);
                    toast.show();
                    internet = INTERNET.OFFLINEMOD;
                }
            }
        );

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        grid = new GridLayoutManager(this, 2);
        progressBar = binding.progressBar;
        binding.imageScrollView.setLayoutManager(grid);
        binding.imageScrollView.setAdapter(imageScrollAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle arguments = data.getExtras();

        if(arguments != null)
            model.updateOrInsert(arguments.getParcelable("photo"));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        model.saveScrollPos(grid.findFirstCompletelyVisibleItemPosition());
    }

    public boolean isConnected() {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager)getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("TAG", e.toString());
        }
        return false;
    }
}