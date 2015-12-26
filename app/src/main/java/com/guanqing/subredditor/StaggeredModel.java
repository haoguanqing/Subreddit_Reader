package com.guanqing.subredditor;

/**
 * Created by Guanqing on 2015/12/25.
 */
public class StaggeredModel {
    String imageUrl;
    String title;
    int karma;

    public StaggeredModel(String imageUrl, String title, int karma){
        this.imageUrl = imageUrl;
        this.title = title;
        this.karma = karma;
    }
}
