package com.guanqing.subredditor.Retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class GalleryModel extends BaseModel{

    @SerializedName("data")
    @Expose
    private GalleryData data;

    /**
     *
     * @return
     * The data
     */
    public GalleryData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(GalleryData data) {
        this.data = data;
    }
}
