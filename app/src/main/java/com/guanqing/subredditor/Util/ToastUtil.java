package com.guanqing.subredditor.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Guanqing on 2015/11/25.
 */
public class ToastUtil {
    private static Toast toast;

    public static void show(Context context, String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
