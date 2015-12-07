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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.guanqing.subredditor.R;
import com.guanqing.subredditor.UI.UpvoteTextSwitcher;
import com.guanqing.subredditor.Util.Constants;

/**
 * Created by Guanqing on 2015/12/3.
 * Pop out and show a boarderless image/gif view
 */
public class ZoomViewDialogFragment extends android.app.DialogFragment {
    static int[] screenSize;
    ImageView ivThumbnail;
    UpvoteTextSwitcher tsUpvote;
    ImageView ivUpvotes;

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
        final View view = inflater.inflate(R.layout.fragment_zoom_dialog, container, false);
        ivThumbnail = (ImageView) view.findViewById(R.id.ivFeedThumbnail_detail);
        tsUpvote = (UpvoteTextSwitcher) view.findViewById(R.id.tsUpvotesCounter_detail);
        ivUpvotes = (ImageView) view.findViewById(R.id.ivUpvotes);

        tsUpvote.setListener(1989);
        ivUpvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUpvotes.setImageDrawable(
                        ivUpvotes.getDrawable()==getResources().getDrawable(R.drawable.ic_arrow_up) ?
                                getResources().getDrawable(R.drawable.ic_arrow_up_blue) : getResources().getDrawable(R.drawable.ic_arrow_up));
                tsUpvote.performClick();
            }
        });

        Glide.with(getActivity()).load("http://i.imgur.com/46Vi6an.jpg")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.error)
                .crossFade()
                .thumbnail(0.1f)
                .into(ivThumbnail);
        return view;
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
