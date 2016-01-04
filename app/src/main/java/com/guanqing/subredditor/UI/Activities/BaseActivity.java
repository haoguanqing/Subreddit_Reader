package com.guanqing.subredditor.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guanqing.subredditor.App;

import de.greenrobot.event.EventBus;

/**
 * Created by Guanqing on 2015/11/24.
 */
public class BaseActivity extends AppCompatActivity{


    protected App mApp;
    protected Resources mResources;
    protected static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfigure();
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EventBus.getDefault().register(this);
    }

    private void initConfigure() {
        mContext = this;
        if (null == mApp) {
            mApp = App.getInstance();
        }
        mResources = getResources();
    }

    /**
     * start Activity
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void jumpToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param context
     * @param targetActivity
     * @param requestCode
     * @param bundle
     */
    public void jumpToActivityForResult(Context context, Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
