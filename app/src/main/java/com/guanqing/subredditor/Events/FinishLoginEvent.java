package com.guanqing.subredditor.Events;

import net.dean.jraw.RedditClient;

/**
 * Created by Guanqing on 2015/12/1.
 * Event to post when user logged in and reddit client authenticated
 */
public class FinishLoginEvent {
    private RedditClient redditClient;
    public FinishLoginEvent(RedditClient client){
        redditClient = client;
    }

    public RedditClient getRedditClient(){
        return redditClient;
    }
}