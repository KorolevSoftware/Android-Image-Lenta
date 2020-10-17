package com.example.lenta;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class FlickrPhotoDiffCallback extends DiffUtil.ItemCallback<FlickrPhoto> {

    @Override
    public boolean areItemsTheSame(@NonNull FlickrPhoto oldItem, @NonNull FlickrPhoto newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull FlickrPhoto oldItem, @NonNull FlickrPhoto newItem) {
        return oldItem.hasLike() == newItem.hasLike();
    }
}
