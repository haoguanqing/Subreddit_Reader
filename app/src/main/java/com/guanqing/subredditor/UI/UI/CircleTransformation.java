package com.guanqing.subredditor.UI.ui;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.guanqing.subredditor.Utils.ImageUtil;

/**
 * Created by Guanqing on 2015/12/6.
 */
public class CircleTransformation extends BitmapTransformation {

    public CircleTransformation(Context context) {
        super(context);
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return ImageUtil.getCircularBitmapImage(toTransform);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
