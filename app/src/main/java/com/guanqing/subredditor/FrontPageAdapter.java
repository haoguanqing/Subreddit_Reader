package com.guanqing.subredditor;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guanqing.subredditor.Fragments.ZoomGifDialogFragment;
import com.guanqing.subredditor.Fragments.ZoomViewDialogFragment;
import com.guanqing.subredditor.Util.Constants;


public class FrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;
    private FragmentManager fm;
    private FrontPageFeedViewHolder holder;

    public FrontPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.list_item_feed, parent, false);
        fm = ((FragmentActivity) context).getSupportFragmentManager();
        return new FrontPageFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        holder = (FrontPageFeedViewHolder) viewHolder;
        try{
            bindDefaultFeedItem(position, holder);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void bindDefaultFeedItem(int position, final FrontPageFeedViewHolder holder) throws Exception{
        if (position % 2 == 0) {
            setVIewVisibility(true);
            int halfWidth = Constants.getScreenSizeInPixels(context)[0]/2;

            Glide.with(context).load("http://i.imgur.com/58aBBpZ.gif")
                    .asBitmap()
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error)
                    .override(halfWidth, halfWidth)
                    .thumbnail(0.1f)
                    .into(holder.ivThumbnail);
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoomViewDialogFragment fragment = ZoomViewDialogFragment.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            });
        } else if (position % 2 == 1) {
            setVIewVisibility(false);
            Glide.with(context).load("http://i.imgur.com/33GXeGQ.jpg")
                    .error(R.drawable.error)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(holder.ivThumbnail);
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoomGifDialogFragment fragment = ZoomGifDialogFragment.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            });
        }
    }

    private void setVIewVisibility(boolean isGif){
        holder.ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);
    }

    public void updateItems() {
        itemsCount += 10;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    public static class FrontPageFeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvFeedTitle;
        ImageButton btnComments;
        TextView tvUpvotesCounter;
        ImageView ivGifIcon;

        public FrontPageFeedViewHolder(View view) {
            super(view);

            ivThumbnail = (ImageView) view.findViewById(R.id.ivFeedThumbnail);
            tvFeedTitle = (TextView) view.findViewById(R.id.tvFeedTitle);
            btnComments = (ImageButton) view.findViewById(R.id.btnComments);
            tvUpvotesCounter = (TextView)view.findViewById(R.id.tvUpvotesCounter);
            ivGifIcon = (ImageView)view.findViewById(R.id.ivGifIcon);
        }
    }
}
