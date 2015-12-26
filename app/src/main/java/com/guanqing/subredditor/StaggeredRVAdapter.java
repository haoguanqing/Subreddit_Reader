package com.guanqing.subredditor;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guanqing.subredditor.Fragments.ZoomDialog;
import com.guanqing.subredditor.Fragments.ZoomGifDialog;
import com.guanqing.subredditor.Util.Constants;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Guanqing on 2015/12/25.
 */
public class StaggeredRVAdapter extends BGARecyclerViewAdapter<StaggeredModel>  {
    boolean isGif;
    FragmentManager fm;

    public StaggeredRVAdapter(RecyclerView recyclerView, FragmentManager fragmentManager){
        super(recyclerView, R.layout.feed_list_item);
        isGif = false;
        fm = fragmentManager;
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int i, final StaggeredModel model) {

        ImageView ivGifIcon = viewHolderHelper.getImageView(R.id.ivGifIcon);
        ImageView ivThumbnail = viewHolderHelper.getImageView(R.id.ivFeedThumbnail);
        TextView tvFeedTitle = viewHolderHelper.getTextView(R.id.tvFeedTitle);
        ImageButton btnComments = viewHolderHelper.getView(R.id.btnComments);
        TextView tvUpvotesCounter = viewHolderHelper.getTextView(R.id.tvUpvotesCounter);

        View.OnClickListener clickListener;

        if (model.imageUrl==null){
            isGif = false;
        }else if (model.imageUrl.endsWith(".gif") || model.imageUrl.endsWith(".gifv")) {
            isGif = true;
            if (model.imageUrl.endsWith(".gifv")) {
                model.imageUrl = model.imageUrl.substring(0, model.imageUrl.length() - 1);
            }
        }

        //set gif icon visibility
        ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);

        if (isGif){
            //---- if image is gif ----
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show new detailed gif dialog
                    ZoomGifDialog fragment = ZoomGifDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            };

            //set image res and onClickListener
            int halfWidth = Constants.getScreenSizeInPixels(mContext)[0] / 2;
            Glide.with(mContext).load(model.imageUrl)
                    .asBitmap()
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error)
                    .override(halfWidth, halfWidth)
                    .thumbnail(0.1f)
                    .into(ivThumbnail);

            ivThumbnail.setOnClickListener(clickListener);

            //set gif icon visible
            ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);

            //set title text
            tvFeedTitle.setText(model.title);
            if (model.imageUrl==null){
                tvFeedTitle.setMaxLines(4);
            }
            //set onClickListener for title
            tvFeedTitle.setOnClickListener(clickListener);
        }else{
            //---- if image is not gif or is null ----
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show new detailed dialog
                    ZoomDialog fragment = ZoomDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            };

            if(model.imageUrl==null){
                //if there is no pic, increase the maxLines of the title to 4
                tvFeedTitle.setMaxLines(4);
            }else {
                //set image res and onClickListener
                int halfWidth = Constants.getScreenSizeInPixels(mContext)[0] / 2;
                Glide.with(mContext).load(model.imageUrl)
                        .placeholder(R.drawable.avatar_loading)
                        .error(R.drawable.error)
                        .override(halfWidth, halfWidth)
                        .thumbnail(0.1f)
                        .into(ivThumbnail);

                ivThumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isGif) {
                            ZoomGifDialog fragment = ZoomGifDialog.newInstance();
                            fragment.show(fm, "zoom dialog");
                        } else {
                            ZoomDialog fragment = ZoomDialog.newInstance();
                            fragment.show(fm, "zoom dialog");
                        }
                    }
                });
            }

            //set title text and onClickListener
            tvFeedTitle.setText(model.title);
            tvFeedTitle.setOnClickListener(clickListener);
        }
    }
}
