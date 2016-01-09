package com.guanqing.subredditor.UI.Fragments;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.guanqing.subredditor.FrontPageModel;
import com.guanqing.subredditor.R;
import com.guanqing.subredditor.UI.Widgets.LoadingIndicatorView;
import com.guanqing.subredditor.UI.Widgets.UpvoteTextSwitcher;
import com.guanqing.subredditor.Utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guanqing on 2015/12/4.
 * Pop out and show a boarderless gif dialog view
 */
public class ZoomGifDialog extends DialogFragment {
    public static final String DIALOG_FLAG = "ZoomDialog.DIALOG_FLAG";
    public static final String SUBMISSION_MODEL_KEY = "ZomGifDialog.SUBMISSION_MODEL_KEY";

    // an array storing the width and height of current screen
    static int[] screenSize;
    // model containing all data of this submission
    protected FrontPageModel model;
    //view holder
    private static ViewHolder holder;

    public static ZoomGifDialog newInstance(FrontPageModel model){
        ZoomGifDialog fragment = new ZoomGifDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUBMISSION_MODEL_KEY, model);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get screen size in pixels
        screenSize = Constants.getScreenSizeInPixels(getActivity());

        if(getArguments()!=null) {
            //get the data passed in
            model = getArguments().getParcelable(SUBMISSION_MODEL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_gif_zoom, container, false);
        holder = new ViewHolder(view);

        holder.ivUpvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivUpvotes.setImageDrawable(
                        holder.ivUpvotes.getDrawable() == getResources().getDrawable(R.drawable.ic_arrow_up) ?
                                getResources().getDrawable(R.drawable.ic_arrow_up_blue) : getResources().getDrawable(R.drawable.ic_arrow_up));
                holder.tsUpvote.performClick();
            }
        });

        //set title of the submission
        holder.tvTitle.setText(model.getTitle());

        //set comments count
        holder.tvCommentCount.setText(model.getCommentCount() + "");

        //change text and pic after user upvotes a submission
        holder.tsUpvote.setCurrentText(model.getKarma() + "");
        holder.tsUpvote.setListener(model.getKarma());
        holder.ivUpvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivUpvotes.setImageDrawable(
                        holder.ivUpvotes.getDrawable() == getResources().getDrawable(R.drawable.ic_arrow_up) ?
                                getResources().getDrawable(R.drawable.ic_arrow_up_blue) : getResources().getDrawable(R.drawable.ic_arrow_up));
                holder.tsUpvote.performClick();
            }
        });

        loadGif(model.getLink());

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation_zoom;
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


    private void loadGif(String url){
        Uri uri = Uri.parse(url);
        holder.gifView.setVideoURI(uri);
        holder.gifView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                holder.loadingIndicatorView.setVisibility(View.GONE);
                holder.ivBackground.setVisibility(View.GONE);
            }
        });
        //on error:
        holder.gifView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                holder.gifView.setVisibility(View.GONE);
                holder.loadingIndicatorView.setVisibility(View.VISIBLE);
                holder.ivBackground.setVisibility(View.GONE);
                holder.ivBackground.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                return false;
            }
        });
        holder.gifView.start();
    }


    //custom view holder for gif zoom layout
    protected class ViewHolder{
        protected View view;
        // UI reference
        @Bind(R.id.vvGifView_zoomgif) protected VideoView gifView;
        @Bind(R.id.ivBackground_zoomgif) protected ImageView ivBackground;
        @Bind(R.id.btnSave_zoomgif) protected ImageButton btnSave;
        @Bind(R.id.btnShare_zoomgif) protected ImageButton btnShare;
        @Bind(R.id.btnComments_zoomgif) protected ImageButton btnComments;
        @Bind(R.id.tvCommentCount_zoomgif) protected TextView tvCommentCount;
        @Bind(R.id.tvFeedTitle_zoomgif) protected TextView tvTitle;
        @Bind(R.id.tsUpvotesCounter_zoomgif) protected UpvoteTextSwitcher tsUpvote;
        @Bind(R.id.ivUpvotes_zoomgif) protected ImageView ivUpvotes;
        @Bind(R.id.loadingIndicator_zoomgif) protected LoadingIndicatorView loadingIndicatorView;

        public ViewHolder(View view){
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
