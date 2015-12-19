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
import com.guanqing.subredditor.Fragments.ZoomGifDialog;
import com.guanqing.subredditor.Fragments.ZoomDialog;
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
        final View view = LayoutInflater.from(context).inflate(R.layout.feed_list_item, parent, false);
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
        if (position % 5 == 0) {
            setGifIconVisible(true);
            int halfWidth = Constants.getScreenSizeInPixels(context)[0]/2;

            Glide.with(context).load("http://i.imgur.com/58aBBpZ.gif")
                    .asBitmap()
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error)
                    .override(halfWidth, halfWidth)
                    .thumbnail(0.1f)
                    .into(holder.ivThumbnail);
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoomDialog fragment = ZoomDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            });
        } else if (position % 5 == 1) {
            setGifIconVisible(false);
            Glide.with(context).load("http://i.imgur.com/33GXeGQ.jpg")
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(holder.ivThumbnail);
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoomGifDialog fragment = ZoomGifDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            });
        }else if (position % 5 == 2) {
            setGifIconVisible(false);
            Glide.with(context).load("http://i.imgur.com/by0E4RK.jpg")
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(holder.ivThumbnail);
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoomGifDialog fragment = ZoomGifDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            });
        }else if (position % 5 == 3) {
            setGifIconVisible(false);
            Glide.with(context).load("http://i.imgur.com/ae3Nc5M.jpg")
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(holder.ivThumbnail);
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoomGifDialog fragment = ZoomGifDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            });
        }
        else if (position % 5 == 4) {
            setGifIconVisible(false);
            Glide.with(context).load("http://i.imgur.com/7LabzAX.jpg")
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(holder.ivThumbnail);
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoomGifDialog fragment = ZoomGifDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            });
        }
    }

    private void setGifIconVisible(boolean isGif){
        holder.ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);
    }

    public void updateItems() {
        itemsCount += 20;
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
