package com.example.lenta;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    private FlickrPhoto photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView image = findViewById(R.id.image);
        ImageView imageLike = findViewById(R.id.image_like);

        Bundle arguments = getIntent().getExtras();
        if(arguments == null)
            return;
        photo = (FlickrPhoto) arguments.getParcelable("photo");
        image.setImageBitmap(photo.getImage());
        LocalStorage localStorage = new LocalStorage(this);
        photo.setLikeSetter(new FlickrLSSetter(localStorage.getWritableDatabase()));

        imageLike.setOnClickListener(this::onClick);

        if(photo.hasLike())
            imageLike.setImageResource(R.drawable.ic_like_enable);
        else
            imageLike.setImageResource(R.drawable.ic_like_disable);
    }

    private void onClick(View v) {
        ImageView imageLike = (ImageView)v;
        if(photo.cklickLike())
            imageLike.setImageResource(R.drawable.ic_like_enable);
        else
            imageLike.setImageResource(R.drawable.ic_like_disable);
    }
}