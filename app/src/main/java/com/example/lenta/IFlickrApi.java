package com.example.lenta;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFlickrApi {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&media=photos&sort=relevance")
    Call<FlickrResponse> getPhotoPages(
            @Query("api_key") String apiKey,
            @Query("per_page") Integer perPage,
            @Query("text") String text,
            @Query("page") Integer page );
}
