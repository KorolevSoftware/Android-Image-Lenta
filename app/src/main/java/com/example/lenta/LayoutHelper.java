package com.example.lenta;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.squareup.picasso.Picasso;

public class LayoutHelper {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder( R.drawable.ic_cat) // Static placeholder
                .into(view);
    }
}
