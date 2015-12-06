package com.guanqing.subredditor.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.guanqing.subredditor.R;
import com.guanqing.subredditor.UI.GifView;
import com.guanqing.subredditor.Util.Constants;

/**
 * Created by Guanqing on 2015/12/4.
 */
public class ZoomGifDialogFragment extends android.app.DialogFragment {

    static int[] screenSize;

    public static ZoomGifDialogFragment newInstance(){
        //TODO: new instance
        ZoomGifDialogFragment fragment = new ZoomGifDialogFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenSize = Constants.getScreenSizeInPixels(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_gif_dialog, container, false);
        GifView gifView = (GifView) rootView.findViewById(R.id.ivGifThumbnail_detail);
        gifView.setMovieResource(R.drawable.imgur_example);
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
        int width = screenSize[0] * 10/11;
        int height = screenSize[1] * 10/11;

        if (getDialog() == null)
            return;
        if (getResources().getConfiguration().orientation==1){
            getDialog().getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        }else{
            getDialog().getWindow().setLayout(height, width);
        }
    }
}
