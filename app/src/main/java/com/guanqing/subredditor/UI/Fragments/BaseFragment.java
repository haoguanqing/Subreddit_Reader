package com.guanqing.subredditor.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanqing.subredditor.ui.activities.BaseActivity;
import com.guanqing.subredditor.App;
import com.guanqing.subredditor.util.NetworkUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by Guanqing on 2015/12/8.
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG;
    protected BaseActivity mActivity;
    protected View mContentView;
    protected App mApp;
    protected boolean mIsNetworkEnabled;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mIsNetworkEnabled = NetworkUtil.isInternetConnected(getActivity());
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TAG = this.getClass().getSimpleName();
        mActivity = (BaseActivity) activity;
        mApp = App.getInstance();
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

    protected void setContentView(@LayoutRes int layoutResID) {
        mContentView = LayoutInflater.from(mApp).inflate(layoutResID, null);
    }

    /**
     * find and return view
     *
     * @param id   view id
     * @param <VT> View class
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mContentView.findViewById(id);
    }

    //
    protected boolean isNetworkEnabled(){
        return mIsNetworkEnabled;
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
