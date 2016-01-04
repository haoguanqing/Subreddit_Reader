package com.guanqing.subredditor.Retrofit.Gfycat;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Guanqing on 2016/1/3.
 */
public interface GfycatService {
    @GET("fetchUrl={url}")
    Call<GfycatModel> getGifData(@Path("url") String url);
}
