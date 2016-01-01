package com.guanqing.subredditor.Retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class BaseModel {

    @SerializedName("imageData")
    @Expose
    private ImageData imageData;

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status")
    @Expose
    private int status;

    /**
     *
     * @return
     * The imageData
     */
    public ImageData getImageData() {
        return imageData;
    }

    /**
     *
     * @param imageData
     * The imageData
     */
    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    /**
     *
     * @return
     * The success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @return
     * The status
     */
    public int getStatus() {
        return status;
    }
}
