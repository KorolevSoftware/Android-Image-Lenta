package com.example.lenta;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class FlickrPhotoDiffCallback extends DiffUtil.ItemCallback<FlickrPhoto> {

    @Override
    public boolean areItemsTheSame(@NonNull FlickrPhoto oldItem, @NonNull FlickrPhoto newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull FlickrPhoto oldItem, @NonNull FlickrPhoto newItem) {
        Log.d("areContentsTheSame", String.valueOf(oldItem.hasLike() == newItem.hasLike()));
        return oldItem.getIsLike() == newItem.getIsLike();
    }
}
