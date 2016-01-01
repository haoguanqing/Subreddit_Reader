package com.guanqing.subredditor.Retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class BaseModel {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status")
    @Expose
    private int status;

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
