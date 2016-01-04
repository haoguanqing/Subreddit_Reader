package com.guanqing.subredditor;

import android.app.Application;

/**
 * Created by Guanqing on 2015/11/24.
 */
public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getInstance() {
        return app;
    }
}
