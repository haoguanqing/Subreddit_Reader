package com.guanqing.subredditor.Retrofit;

import com.guanqing.subredditor.Retrofit.Models.GalleryModel;
import com.guanqing.subredditor.Retrofit.Models.ImageModel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by Guanqing on 2016/1/1.
 */
public interface ImgurService {
    @Headers("Authorization: Client-ID " + ImgurClient.IMGUR_CLIENT_ID)
    @GET("/3/image/{imageId}")
    Call<ImageModel> getImage(@Path("imageId") String imageId);

    @Headers("Authorization: Client-ID " + ImgurClient.IMGUR_CLIENT_ID)
    @GET("/3/gallery/{galleryId}")
    Call<GalleryModel> getGallery(@Path("galleryId") String galleryId);
}
