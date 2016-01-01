package com.guanqing.subredditor;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class ZoomModel {
    String id;
    String title;
    String thumbnailUrl;
    String link;
    int commentCount;
    int karma;

    public String getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getKarma() {
        return karma;
    }

    public String getLink() {
        return link;
    }
}
