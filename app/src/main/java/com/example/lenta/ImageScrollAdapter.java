package com.example.lenta;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ImageScrollAdapter extends PagedListAdapter<FlickrPhoto, ImageScrollAdapter.ImageViewHolder> {

    private LayoutInflater layoutInflater;
    private Activity activity;

    public ImageScrollAdapter(Activity activity, DiffUtil.ItemCallback<FlickrPhoto> diffUtilCallback) {
        super(diffUtilCallback);
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.image_flicker_view, parent, false);
        return new ImageViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        FlickrPhoto photo = getItem(position);
        if(photo == null)
            return;
        holder.setPhoto(photo);
        holder.setImage(photo.getImage());
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private ImageView likeView;
        private FlickrPhoto photo;
        private Activity activity;

        void setPhoto(FlickrPhoto photo){
            this.photo = photo;

            if(photo.hasLike())
                likeView.setImageResource(R.drawable.ic_like_enable);
            else
                likeView.setImageResource(R.drawable.ic_like_disable);
        }

        void setImage(Bitmap img)
        {
            imageView.setImageBitmap(img);
        }

        public ImageViewHolder(@NonNull View itemView, Activity activity) {
            super(itemView);
            this.activity = activity;

            likeView = itemView.findViewById(R.id.image_like);
            imageView = itemView.findViewById(R.id.image);

            imageView.setOnClickListener(this::imageClickListener);
            likeView.setOnClickListener(this::likeClickListener);
        }

        private void likeClickListener(View v)
        {
            if(photo.cklickLike())
                likeView.setImageResource(R.drawable.ic_like_enable);
            else
                likeView.setImageResource(R.drawable.ic_like_disable);
        }

        private void imageClickListener(View v)
        {
            ActivityOptions options
                    = ActivityOptions.makeSceneTransitionAnimation(activity,
                    new Pair<>(itemView.findViewById(R.id.image), activity.getString(R.string.image_transition)),
                    new Pair<>(itemView.findViewById(R.id.image_like), activity.getString(R.string.image_like_transition))
            );

            Bundle bundle = options.toBundle();
            Intent intent = new Intent(activity, ImageActivity.class);
            intent.putExtra("photo", photo);
            activity.startActivityForResult(intent, 10, bundle);
        }
    }
}
