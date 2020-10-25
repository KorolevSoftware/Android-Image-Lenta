package com.example.lenta;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.RawRes;
import androidx.databinding.BindingAdapter;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.squareup.picasso.Picasso;

public class LayoutHelper {

    private static LottieDrawable createLottieDrawable(Context context, @RawRes int filename) {
        final LottieDrawable lottieDrawable = new LottieDrawable();
        LottieComposition.Factory.fromRawFile(context, filename,
                composition -> {
                    lottieDrawable.setComposition(composition);
                    lottieDrawable.loop(true);
                    lottieDrawable.playAnimation();
                });

        return lottieDrawable;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                //.placeholder(createLottieDrawable(view.getContext(), R.raw.catloader))// <-- Animate placeholder
                .placeholder( R.drawable.ic_cat) // Static placeholder
                .into(view);
    }
}
