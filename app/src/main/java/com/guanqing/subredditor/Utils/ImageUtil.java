package com.guanqing.subredditor.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.guanqing.subredditor.App;
import com.guanqing.subredditor.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Guanqing on 2015/12/6.
 */
public class ImageUtil {

    /**
     * create a circular image from bitmap source
     * @param source
     * @return circularBitmap
     */
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


    /**
     * convert pixel to dip
     * @param pixels
     * @return dip
     */
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    /**
     * save user's avatar image
     * @return Uri
     */
    @Nullable
    public static Uri saveAvatarImage(Context context, String avatarUrl){
        try {
            URL url = new URL(avatarUrl);
            InputStream is = url.openStream();
            File file = new File(context.getExternalCacheDir()+"/avatar.png");
            FileOutputStream os = new FileOutputStream(file);
            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * get user's avatar from cache; default if not exist
     * @return drawable
     */
    public static Drawable getAvatarImage(Context context, Uri uri){
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            return Drawable.createFromStream(inputStream, uri.toString());
        } catch (FileNotFoundException e) {
            return context.getResources().getDrawable(R.drawable.avatar);
        }
    }

    /**
     * get drawable from url on background thread
     * @param url
     * @return drawable
     * @throws IOException
     */
    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    public static int getAppropriateDialogWidth(float ratio){
        int[] screenSize = Constants.getScreenSizeInPixels(App.getInstance());
        int width = screenSize[0] * 10/11;
        if(ratio>1.1){
            width = screenSize[0];
        }
        return width;
    }

    public static int getAppropriateGifDialogWidth(float ratio){
        int[] screenSize = Constants.getScreenSizeInPixels(App.getInstance());
        float screenRatio = (float) screenSize[0]/screenSize[1];
        int width = screenSize[0];
        if(ratio>1.1){
            return width;
        } else if(ratio<screenRatio * 1.15 && ratio>0) {
            return width * 8 / 11;
        } else {
            return width * 10 / 11;
        }
    }
}
