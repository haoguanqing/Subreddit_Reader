package com.guanqing.subredditor.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Guanqing on 2015/12/6.
 */
public class ImageUtil {

    //create a circular image
    public static Bitmap getCircularBitmapImage(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    // Convert pixel to dip
    public int getDipsFromPixel(Context context, float pixels) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    /**
     * save user's avatar image
     * @return Uri
     */
    @Nullable
    private Uri saveAvatarImage(Context context, Bitmap bm){
        try {
            File file = new File(context.getExternalCacheDir()+"/avatar.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //define the size of the view to perfectly fit the screen
    public static int[] getSuitableViewSize(int screenWidth, int screenHeight, int imageWidth, int imageHeight){
        int adjustW = screenWidth * 10 / 11;
        int adjustH = screenHeight * 8 / 11;
        float screenRatio = (float) adjustW / adjustH;
        float imageRatio = (float) imageWidth / imageHeight;

        if(imageWidth > adjustW && imageRatio>screenRatio){
            return new int[]{adjustW, WindowManager.LayoutParams.WRAP_CONTENT};
        }else if (imageWidth > adjustW && imageRatio<screenRatio){
            return new int[]{WindowManager.LayoutParams.WRAP_CONTENT, adjustH};
        }else if (imageWidth < adjustW && imageHeight > adjustH){
            return new int[]{WindowManager.LayoutParams.WRAP_CONTENT, adjustH};
        }
        return new int[]{adjustW,adjustH};

    }

    //define the view size to perfectly fit the horizontal screen
    public static int[] getSuitableViewSizeHorizontal(int screenWidth, int screenHeight, int imageWidth, int imageHeight){
        int adjustW = screenWidth * 10 / 11;
        int adjustH = screenHeight * 8 / 11;
        float screenRatio = (float) adjustW / adjustH;
        float imageRatio = (float) imageWidth / imageHeight;

        if(imageWidth > adjustW && imageRatio<screenRatio){
            return new int[]{adjustW, WindowManager.LayoutParams.WRAP_CONTENT};
        }else if (imageWidth > adjustW && imageRatio>screenRatio){
            return new int[]{WindowManager.LayoutParams.WRAP_CONTENT, adjustH};
        }else if (imageWidth < adjustW && imageHeight > adjustH){
            return new int[]{WindowManager.LayoutParams.WRAP_CONTENT, adjustH};
        }
        return new int[]{adjustW,adjustH};
    }
}
