package com.guanqing.subredditor;

/**
 * Created by Guanqing on 2015/12/25.
 */
public class StaggeredModel {
    String imageUrl;
    String title;
    int commentCount;
    int karma;

    public StaggeredModel(String imageUrl, String title, int commentCount, int karma){
        this.imageUrl = imageUrl;
        this.title = title;
        this.commentCount = commentCount;
        this.karma = karma;
    }
}
