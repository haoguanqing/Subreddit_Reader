package com.guanqing.subredditor.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guanqing.subredditor.R;
import com.guanqing.subredditor.Util.Constants;

/**
 * Created by Guanqing on 2015/12/3.
 * Pop out and show a boarderless image/gif view
 */
public class ZoomViewDialogFragment extends android.app.DialogFragment {
    static int[] screenSize;
    ImageView ivThumbnail;

    public static ZoomViewDialogFragment newInstance(){
        //TODO: new instance
        ZoomViewDialogFragment fragment = new ZoomViewDialogFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenSize = Constants.getScreenSizeInPixels(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_zoom_dialog, container, false);
        ivThumbnail = (ImageView) rootView.findViewById(R.id.ivFeedThumbnail_detail);
        Glide.with(getActivity()).load("http://i.imgur.com/6c0N9I3.jpg").thumbnail(0.1f).crossFade().into(ivThumbnail);

        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

        int width = screenSize[0] *10/11;
        int height = screenSize[1] *10/11;

        /*if (getDialog() == null)
            return;*/
        if (getResources().getConfiguration().orientation==1){
            getDialog().getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        }else{
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, height);
        }
    }
}
