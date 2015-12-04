package com.guanqing.subredditor.Events;

import net.dean.jraw.RedditClient;

/**
 * Created by Guanqing on 2015/12/4.
 */
public class AutoLoginEvent {
    private RedditClient redditClient;
    public AutoLoginEvent(RedditClient client){
        redditClient = client;
    }

    public RedditClient getRedditClient(){
        return redditClient;
    }
}
