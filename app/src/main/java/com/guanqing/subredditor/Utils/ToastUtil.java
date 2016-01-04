package com.guanqing.subredditor.Utils;

import android.widget.Toast;

import com.guanqing.subredditor.App;

/**
 * Created by Guanqing on 2015/11/25.
 */
public class ToastUtil {
    private static Toast toast;

    public static void show(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
