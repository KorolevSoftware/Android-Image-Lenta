package com.example.lenta;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Extras;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.flickr4java.flickr.photos.SearchParameters.RELEVANCE;

public class FlickrDAO implements DAO<FlickrPhoto> {

    private Flickr flickr;
    private String searchTile;
    private FlickrLSSetter flickrLSSetter;
    private SQLiteDatabase db;
    FlickrDAO(SQLiteDatabase db)
    {
        this.db = db;
        flickrLSSetter = new FlickrLSSetter(db);
        String apiKey = "f52542e77c35e91f4a50303ed505ac14";
        String sharedSecret = "003e9f1c91657bdd";
        flickr = new Flickr(apiKey, sharedSecret, new REST());
    }

    public void setSearchTile(String tile) {
        searchTile = tile;
    }


    @Override
    public List<FlickrPhoto> getDataByPage(int page, int itemInPage) {
        try {
            PhotosInterface photos = flickr.getPhotosInterface();
            SearchParameters params = new SearchParameters();
            params.setMedia("photos"); // One of "photos", "videos" or "all"
            params.setText(searchTile);
            params.setSort(RELEVANCE);
            params.setExtras(Extras.ALL_EXTRAS);

            PhotoList<Photo> resultsPhoto = photos.search(params, itemInPage, page + 1);

            List<FlickrPhoto> flickrPhotos = new ArrayList<>();

            for(Photo photo:resultsPhoto)
            {
                InputStream in = new URL(photo.getSmallUrl()).openStream();
                Bitmap mIcon11 = BitmapFactory.decodeStream(in);

                int isLike = 0;
                Cursor c = db.rawQuery("select islike from photo where id="+photo.getId(), null);
                if( c != null) {
                    if (c.moveToFirst()) {
                        String isLikeRaw = c.getString(c.getColumnIndex("islike"));
                        if (isLikeRaw.equals("yes"))
                            isLike = 1;
                    } else {
                        ContentValues cv = new ContentValues();
                        cv.put("id", photo.getId());
                        cv.put("islike", "no");
                        db.insert("photo", null, cv);
                    }
                }
                    flickrPhotos.add(new FlickrPhoto(photo.getSmallUrl(), photo.getMediumUrl(), mIcon11, photo.getId(), isLike, flickrLSSetter ));
            }
            return flickrPhotos;


        } catch (FlickrException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
