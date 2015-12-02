package com.guanqing.subredditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Guanqing on 2015/12/1.
 */
public class ImageLoaderHelper {
    private static ImageLoaderHelper sInstance;

    public static ImageLoaderHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ImageLoaderHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    private final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(20);
    private ImageLoader mImageLoader;

    private ImageLoaderHelper(Context applicationContext) {
        RequestQueue queue = Volley.newRequestQueue(applicationContext);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                mImageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return mImageCache.get(key);
            }
        };
        mImageLoader = new ImageLoader(queue, imageCache);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
