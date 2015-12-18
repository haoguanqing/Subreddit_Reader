package com.guanqing.subredditor;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Guanqing on 2015/11/24.
 */
public class App extends Application {

    private static App app;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        requestQueue = Volley.newRequestQueue(this);
    }

    public static App getInstance() {
        return app;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
