package com.guanqing.subredditor.Retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class ImageModel extends BaseModel{

    @SerializedName("data")
    @Expose
    private ImageData data;

    /**
     *
     * @return
     * The imageData
     */
    public ImageData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The imageData
     */
    public void setData(ImageData data) {
        this.data = data;
    }

}