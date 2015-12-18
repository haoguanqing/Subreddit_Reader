package com.guanqing.subredditor.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanqing.subredditor.Activities.BaseActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by Guanqing on 2015/12/8.
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG;
    protected BaseActivity mActivity;
    protected View mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TAG = this.getClass().getSimpleName();
        mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // avoid repeatedly loading xml
        if (mContentView == null) {
            initView(savedInstanceState);
            setListener();
            processLogic(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * initialize view
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * set listeners to the views
     */
    protected abstract void setListener();

    /**
     * process logic, recover status, etc.
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

}
