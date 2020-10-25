package com.example.lenta;

import android.util.Log;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Extras;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.flickr4java.flickr.photos.SearchParameters.RELEVANCE;

public class FlickrDataSource {

    private final FlickrPhotoDAO db;
    private final PhotosInterface photos;
    private final SearchParameters params;
    private List<Photo> pageLoaded;
    private int loadPage;
    private int itemCountLoad = 0;

    public FlickrDataSource(LocalDataBase db) {
        this.db = db.flickrPhotoDAO();
        String apiKey = "f52542e77c35e91f4a50303ed505ac14";
        String sharedSecret = "003e9f1c91657bdd";
        Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());
        loadPage = -1;
        photos = flickr.getPhotosInterface();
        params = new SearchParameters();

        try {
            params.setMedia("photos"); // One of "photos", "videos" or "all"
        } catch (FlickrException e) {
            e.printStackTrace();
        }

        params.setSort(RELEVANCE);
        params.setExtras(Extras.MIN_EXTRAS);
        pageLoaded = new ArrayList<>();
    }

    public void setSearchTile(String tile) {
        params.setText(tile);
    }

    private Photo getPhotoByFlicker(int index) {
        try {
            int pageSize = 100;
            int needPage = index / pageSize;
            if (needPage != loadPage) {
                loadPage = needPage;
                pageLoaded = photos.search(params, pageSize, loadPage + 1);
                itemCountLoad = pageLoaded.size();
            }

            Log.w("FlData", "Data size: " + String.valueOf(pageLoaded.size()));
            Log.w("FlData", "page : " + String.valueOf(loadPage + 1));
            Log.w("FlData", "index:" + String.valueOf(index - needPage * pageSize));

            int needIndex = index - needPage * pageSize;

            if (needIndex < itemCountLoad)
                return pageLoaded.get(needIndex);
            else
                return pageLoaded.get(itemCountLoad - 1);

        } catch (FlickrException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FlickrPhoto> getDataByPage(int startPosition, int itemCount) {
        List<FlickrPhoto> flickrPhotos = new ArrayList<>();

        for (int i = 0; i < itemCount; i++) {
            Photo photo = getPhotoByFlicker(startPosition + i);
            assert photo != null;
            long id = Long.parseLong(photo.getId());

            FlickrPhoto flickrPhoto = Optional.ofNullable(db.getByID(id)).orElse(new FlickrPhoto(photo.getSmallUrl(), id, 0));

            flickrPhoto.setSmallUrl(photo.getSmallUrl());
            flickrPhotos.add(flickrPhoto);
        }
        return flickrPhotos;
    }
}