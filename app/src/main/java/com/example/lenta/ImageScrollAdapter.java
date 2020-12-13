package com.example.lenta;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lenta.databinding.ImageFlickerViewBinding;

public class ImageScrollAdapter extends PagedListAdapter<FlickrPhoto, ImageScrollAdapter.ImageViewHolder> {

    private final MyViewModel model;

    public ImageScrollAdapter(MyViewModel model, DiffUtil.ItemCallback<FlickrPhoto> diffUtilCallback) {
        super(diffUtilCallback);
        this.model = model;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageFlickerViewBinding imageFlickerViewBinding = ImageFlickerViewBinding.inflate(layoutInflater, parent, false);
        return new ImageViewHolder(imageFlickerViewBinding, model);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {


        FlickrPhoto photo = new FlickrPhoto(getItem(position));
        Log.w("itemCount", String.valueOf(getItemCount()));

        holder.bind(photo);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageFlickerViewBinding imageFlickerViewBinding;
        private final MyViewModel model;
        private FlickrPhoto photo;

        private void bind(FlickrPhoto photo){
            this.photo = photo;
            imageFlickerViewBinding.setFlickrPhoto(photo);
        }

        public ImageViewHolder(ImageFlickerViewBinding imageFlickerViewBinding, MyViewModel model) {
            super(imageFlickerViewBinding.getRoot());
            this.model = model;

            this.imageFlickerViewBinding = imageFlickerViewBinding;
            imageFlickerViewBinding.setLikeClick(this::likeClickListener);
            imageFlickerViewBinding.setImageClick(this::imageClickListener);
        }

        private void likeClickListener(View v)
        {
            photo.cklickLike();
            model.updateOrInsert(photo);
        }

        private void imageClickListener(View v)
        {
            Context context = v.getContext();

            ActivityOptions options
                    = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                    new Pair<>(itemView.findViewById(imageFlickerViewBinding.image.getId()), context.getString(R.string.image_transition)),
                    new Pair<>(itemView.findViewById(imageFlickerViewBinding.imageLike.getId()), context.getString(R.string.image_like_transition))
            );

            Bundle bundle = options.toBundle();
            Intent intent = new Intent(context, ImageActivity.class);
            intent.putExtra("photo", photo);
            ((Activity) context).startActivityForResult(intent, 10, bundle);
        }
    }
}