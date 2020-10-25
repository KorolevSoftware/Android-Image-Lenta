package com.example.lenta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.lenta.databinding.ActivityImageBinding;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

public class ImageActivity extends AppCompatActivity implements SlidrListener {
    private FlickrPhoto photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Setup swipe back
        SlidrConfig.Builder slidrConfig = new SlidrConfig.Builder();
        slidrConfig.position(SlidrPosition.VERTICAL);
        slidrConfig.scrimColor(getResources().getColor(R.color.transparent));
        slidrConfig.scrimStartAlpha(0);
        slidrConfig.listener(this);
        Slidr.attach(this, slidrConfig.build());

        Bundle arguments = getIntent().getExtras();
        if(arguments == null)
            return;

        photo = arguments.getParcelable("photo");
        ActivityImageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        binding.setFlickrPhoto(photo); // Data in layout
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("photo", photo);
        setResult(MainActivity.RESULT_CANCELED, intent);
    }

    @Override
    public void onSlideClosed()
    {
        onBackPressed();
    }

    @Override
    public void onSlideChange(float percent) {
        if(percent < 0.7f)
            onBackPressed();
        Log.w("percent", String.valueOf(percent));
    }

    @Override
    public void onSlideOpened() {}

    @Override
    public void onSlideStateChanged(int state) {}
}