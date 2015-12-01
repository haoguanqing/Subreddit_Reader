package com.guanqing.subredditor;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Guanqing on 2015/11/24.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    private static RequestQueue requestQueue;

    public static MyApplication getInstance() {
        return myApplication;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        requestQueue = Volley.newRequestQueue(this);
        //initImageLoader();
    }

/*    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(30 * 1024 * 1024)
                .diskCacheFileCount(100)
                .build();
        ImageLoader.getInstance().init(config);
    }

    //默认的
    public DisplayImageOptions getOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_app_logo)
                .showImageForEmptyUri(R.drawable.ic_app_logo)
                .showImageOnFail(R.drawable.ic_app_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    //自定义默认图的
    public DisplayImageOptions getOptions(int defaultImgResourceId) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImgResourceId)
                .showImageForEmptyUri(defaultImgResourceId)
                .showImageOnFail(defaultImgResourceId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    //圆角图片
    public DisplayImageOptions getOptionsWithRoundedCorner() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_app_logo)
                .showImageForEmptyUri(R.drawable.ic_app_logo)
                .showImageOnFail(R.drawable.ic_app_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new FadeInBitmapDisplayer(300))
                .displayer(new RoundedBitmapDisplayer(8))
                .build();
    }*/
}
