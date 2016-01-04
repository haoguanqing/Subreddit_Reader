package com.guanqing.subredditor.Retrofit.Gfycat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Guanqing on 2016/1/3.
 */
public class GfycatModel {
    @SerializedName("gfyname")
    @Expose
    private String gfyname;
    @SerializedName("gfyName")
    @Expose
    private String gfyName;
    @SerializedName("gfysize")
    @Expose
    private long gfysize;
    @SerializedName("gifSize")
    @Expose
    private long gifSize;
    @SerializedName("gifWidth")
    @Expose
    private int gifWidth;
    @SerializedName("mp4Url")
    @Expose
    private String mp4Url;
    @SerializedName("frameRate")
    @Expose
    private int frameRate;
    @SerializedName("webmUrl")
    @Expose
    private String webmUrl;
    @SerializedName("gifUrl")
    @Expose
    private String gifUrl;

    /**
     *
     * @return
     * The gfyname
     */
    public String getGfyname() {
        return gfyname;
    }

    /**
     *
     * @param gfyname
     * The gfyname
     */
    public void setGfyname(String gfyname) {
        this.gfyname = gfyname;
    }

    /**
     *
     * @return
     * The gfyName
     */
    public String getGfyName() {
        return gfyName;
    }

    /**
     *
     * @param gfyName
     * The gfyName
     */
    public void setGfyName(String gfyName) {
        this.gfyName = gfyName;
    }

    /**
     *
     * @return
     * The gfysize
     */
    public long getGfysize() {
        return gfysize;
    }

    /**
     *
     * @param gfysize
     * The gfysize
     */
    public void setGfysize(long gfysize) {
        this.gfysize = gfysize;
    }

    /**
     *
     * @return
     * The gifSize
     */
    public long getGifSize() {
        return gifSize;
    }

    /**
     *
     * @param gifSize
     * The gifSize
     */
    public void setGifSize(long gifSize) {
        this.gifSize = gifSize;
    }

    /**
     *
     * @return
     * The gifWidth
     */
    public int getGifWidth() {
        return gifWidth;
    }

    /**
     *
     * @param gifWidth
     * The gifWidth
     */
    public void setGifWidth(int gifWidth) {
        this.gifWidth = gifWidth;
    }

    /**
     *
     * @return
     * The mp4Url
     */
    public String getMp4Url() {
        return mp4Url;
    }

    /**
     *
     * @param mp4Url
     * The mp4Url
     */
    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }

    /**
     *
     * @return
     * The frameRate
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     *
     * @param frameRate
     * The frameRate
     */
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    /**
     *
     * @return
     * The webmUrl
     */
    public String getWebmUrl() {
        return webmUrl;
    }

    /**
     *
     * @param webmUrl
     * The webmUrl
     */
    public void setWebmUrl(String webmUrl) {
        this.webmUrl = webmUrl;
    }

    /**
     *
     * @return
     * The gifUrl
     */
    public String getGifUrl() {
        return gifUrl;
    }

    /**
     *
     * @param gifUrl
     * The gifUrl
     */
    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
