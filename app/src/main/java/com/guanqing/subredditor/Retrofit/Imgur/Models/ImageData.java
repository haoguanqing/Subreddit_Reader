package com.guanqing.subredditor.Retrofit.Imgur.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class ImageData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("deletehash")
    @Expose
    private String deletehash;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("datetime")
    @Expose
    private int datetime;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("animated")
    @Expose
    private boolean animated;
    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("bandwidth")
    @Expose
    private long bandwidth;
    @SerializedName("favorite")
    @Expose
    private boolean favorite;
    @SerializedName("nsfw")
    @Expose
    private boolean nsfw;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("gifv")
    @Expose
    private String gifv;
    @SerializedName("webm")
    @Expose
    private String webm;
    @SerializedName("mp4")
    @Expose
    private String mp4;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("looping")
    @Expose
    private boolean looping;
    @SerializedName("vote")
    @Expose
    private String vote;
    @SerializedName("account_url")
    @Expose
    private String accountUrl;
    @SerializedName("account_id")
    @Expose
    private String accountId;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The deletehash
     */
    public String getDeletehash() {
        return deletehash;
    }

    /**
     *
     * @param deletehash
     * The deletehash
     */
    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The datetime
     */
    public int getDatetime() {
        return datetime;
    }

    /**
     *
     * @param datetime
     * The datetime
     */
    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The animated
     */
    public boolean isAnimated() {
        return animated;
    }

    /**
     *
     * @param animated
     * The animated
     */
    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    /**
     *
     * @return
     * The width
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return
     * The height
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The size
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The views
     */
    public int getViews() {
        return views;
    }

    /**
     *
     * @param views
     * The views
     */
    public void setViews(int views) {
        this.views = views;
    }

    /**
     *
     * @return
     * The bandwidth
     */
    public long getBandwidth() {
        return bandwidth;
    }

    /**
     *
     * @param bandwidth
     * The bandwidth
     */
    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    /**
     *
     * @return
     * The favorite
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     *
     * @param favorite
     * The favorite
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     *
     * @return
     * The nsfw
     */
    public boolean isNsfw() {
        return nsfw;
    }

    /**
     *
     * @param nsfw
     * The nsfw
     */
    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    /**
     *
     * @return
     * The section
     */
    public String getSection() {
        return section;
    }

    /**
     *
     * @param section
     * The section
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     *
     * @return
     * The gifv
     */
    public String getGifv() {
        return gifv;
    }

    /**
     *
     * @param gifv
     * The gifv
     */
    public void setGifv(String gifv) {
        this.gifv = gifv;
    }

    /**
     *
     * @return
     * The webm
     */
    public String getWebm() {
        return webm;
    }

    /**
     *
     * @param webm
     * The webm
     */
    public void setWebm(String webm) {
        this.webm = webm;
    }

    /**
     *
     * @return
     * The mp4
     */
    public String getMp4() {
        return mp4;
    }

    /**
     *
     * @param mp4
     * The mp4
     */
    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The looping
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     *
     * @param looping
     * The looping
     */
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    /**
     *
     * @return
     * The vote
     */
    public String getVote() {
        return vote;
    }

    /**
     *
     * @param vote
     * The vote
     */
    public void setVote(String vote) {
        this.vote = vote;
    }

    /**
     *
     * @return
     * The accountUrl
     */
    public String getAccountUrl() {
        return accountUrl;
    }

    /**
     *
     * @param accountUrl
     * The account_url
     */
    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    /**
     *
     * @return
     * The accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     *
     * @param accountId
     * The account_id
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}