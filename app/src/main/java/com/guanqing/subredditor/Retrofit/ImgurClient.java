package com.guanqing.subredditor.Retrofit;

import java.util.HashMap;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Guanqing on 2015/12/31.
 */
public class ImgurClient {
    private static ImgurClient instance;

    public static final String BASE_URL = "https://api.imgur.com/3/";
    public static final String IMGUR_CLIENT_ID = "e7a200f58cc6e9c";

    private Retrofit mRestAdapter;
    private Map<String, Object> mClients = new HashMap<>();

    public static ImgurClient getInstance() {
        if (null == instance) {
            instance = new ImgurClient();
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
