package com.guanqing.subredditor.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.guanqing.subredditor.UI.DynamicHeightNetworkImageView;

/**
 * Created by Guanqing on 2015/12/2.
 */
public class ImageRatioUtil {
    private float ratio;

    public static float getRatioFromDynamicHeightIV(DynamicHeightNetworkImageView imageView){

        imageView.setDrawingCacheEnabled(true);
        Bitmap b = imageView.getDrawingCache();
        if(b==null) {
            return (float) 1.5;
        }
        else {
            int w = b.getWidth();
            int h = b.getHeight();
            return (float) w / h;
        }
    }

    public static float getRatioFromUrl(Context context, String url){
        ImageView imageView = new ImageView(context);
        Bitmap b = imageView.getDrawingCache();
        int w = b.getWidth();
        int h = b.getHeight();
        imageView = null;
        b = null;
        return (float) h/w;
    }
}
