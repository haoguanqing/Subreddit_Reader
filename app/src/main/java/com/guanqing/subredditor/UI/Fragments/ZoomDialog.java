package com.guanqing.subredditor.ui.fragments;

import android.app.Dialog;
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

import com.bumptech.glide.Glide;
import com.guanqing.subredditor.FrontPageModel;
import com.guanqing.subredditor.R;
import com.guanqing.subredditor.retrofit.imgur.ImgurClient;
import com.guanqing.subredditor.retrofit.imgur.ImgurService;
import com.guanqing.subredditor.ui.ui.UpvoteTextSwitcher;
import com.guanqing.subredditor.util.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guanqing on 2015/12/3.
 * Pop out and show a boarderless image view
 */
public class ZoomDialog extends DialogFragment {

    public static final String DIALOG_FLAG = "ZoomDialog.DIALOG_FLAG";
    public static final String SUBMISSION_MODEL_KEY = "ZoomDialog.SUBMISSION_MODEL_KEY";

    // an array storing the width and height of current screen
    static int[] screenSize;
    // model containing all data of this submission
    protected FrontPageModel model;

    public static ZoomDialog newInstance(FrontPageModel model){
        ZoomDialog fragment = new ZoomDialog();
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
        //set up imgur REST client
        ImgurClient.getInstance().configureRestAdapter();

        if(getArguments()!=null) {
            //get the data passed in
            model = getArguments().getParcelable(SUBMISSION_MODEL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the views and find views by id
        final View view = inflater.inflate(R.layout.dialog_zoom, container, false);
        final ViewHolder holder = new ViewHolder(view);

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

        //dismiss the dialog when user clicks the image
        holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        ImgurService service = ImgurClient.getInstance().getClient(ImgurService.class);
        //inflate the image
        Glide.with(getActivity()).load(model.getLink())
                .placeholder(R.drawable.avatar_loading)
                .error(R.drawable.error)
                .crossFade()
                .thumbnail(0.1f)
                .into(holder.ivThumbnail);
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
        //calculate suitable width and height for the detailed view
        int width = screenSize[0] *10/11;
        int height = screenSize[1] *10/11;
        //error check
        if (getDialog() == null)
            return;
        //set view size to fit the screen
        if (getResources().getConfiguration().orientation==1){
            getDialog().getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        }else{
            getDialog().getWindow().setLayout(height, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }



    protected class ViewHolder{
        protected View view;

        @Bind(R.id.ivFeedThumbnail_detail) protected ImageView ivThumbnail;
        @Bind(R.id.btnSave_detail) protected ImageButton btnSave;
        @Bind(R.id.btnShare_detail) protected ImageButton btnShare;
        @Bind(R.id.btnComments_detail) protected ImageButton btnComments;
        @Bind(R.id.tvCommentCount_detail) protected TextView tvCommentCount;
        @Bind(R.id.tvFeedTitle_detail) protected TextView tvTitle;
        @Bind(R.id.tsUpvotesCounter_detail) protected UpvoteTextSwitcher tsUpvote;
        @Bind(R.id.ivUpvotes_detail) protected ImageView ivUpvotes;

        public ViewHolder(View view){
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
