package com.guanqing.subredditor.Retrofit.Gfycat;

import java.util.HashMap;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Guanqing on 2016/1/3.
 */
public class GfycatClient {
    private static GfycatClient instance;

    public static final String BASE_URL = "https://upload.gfycat.com/transcode?";

    private Retrofit mRestAdapter;
    private Map<String, Object> mClients = new HashMap<>();

    public static GfycatClient getInstance() {
        if (null == instance) {
            instance = new GfycatClient();
        }
        return instance;
    }

    // Configure our Retrofit object.
    public void configureRestAdapter() {
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @SuppressWarnings("unchecked")
    public <T> T getClient(Class<T> clazz) {
        if (mRestAdapter == null) {
            return null;
        }
        T client;
        if ((client = (T) mClients.get(clazz.getCanonicalName())) != null) {
            return client;
        }
        client = mRestAdapter.create(clazz);

        mClients.put(clazz.getCanonicalName(), client);
        return client;
    }
}
