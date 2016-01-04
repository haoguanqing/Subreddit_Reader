package com.guanqing.subredditor.Retrofit.Imgur.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class GalleryData {
    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("vote")
    @Expose
    private boolean favorite;
    @SerializedName("is_album")
    @Expose
    private boolean isAlbum;
    @SerializedName("nsfw")
    @Expose
    private boolean nsfw;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("topic_id")
    @Expose
    private int topicId;
    @SerializedName("images_count")
    @Expose
    private int imagesCount;
    @SerializedName("comment_count")
    @Expose
    private int commentCount;
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
    @SerializedName("images")
    @Expose
    private List<ImageData> images = new ArrayList<ImageData>();

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
     * @return
     * The title
     */
    public String getTitle() {
        return title;
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
     * @return
     * The datetime
     */
    public int getDatetime() {
        return datetime;
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
     * @return
     * The animated
     */
    public boolean isAnimated() {
        return animated;
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
     * @return
     * The height
     */
    public int getHeight() {
        return height;
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
     * @return
     * The views
     */
    public int getViews() {
        return views;
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
     * @return
     * The favorite
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     *
     * @return
     * The isAlbum
     */
    public boolean isAlbum() {
        return isAlbum;
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
     * @return
     * The topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     *
     * @return
     * The topicId
     */
    public int getTopicId() {
        return topicId;
    }

    /**
     *
     * @return
     * The imagesCount
     */
    public int getImagesCount() {
        return imagesCount;
    }

    /**
     *
     * @return
     * The commentCount
     */
    public int getCommentCount() {
        return commentCount;
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
     * @return
     * The webm
     */
    public String getWebm() {
        return webm;
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
     * @return
     * The link
     */
    public String getLink() {
        return link;
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
     * @return
     * The images
     */
    public List<ImageData> getImages() {
        return images;
    }
}
