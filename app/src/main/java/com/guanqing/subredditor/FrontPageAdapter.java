package com.guanqing.subredditor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.guanqing.subredditor.UI.DynamicHeightNetworkImageView;
import com.squareup.picasso.Picasso;


public class FrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;

    public FrontPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.list_item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {
        if (position % 2 == 0) {
            //holder.ivThumbnail.setImageUrl(R.drawable.img_feed_center_1);
            Picasso.with(context).load("https://i.imgur.com/azJG8TL.gif").into(holder.ivThumbnail);
        } else {
            //holder.ivThumbnail.setImageResource(R.drawable.img_feed_center_2);
            Picasso.with(context).load("http://i.imgur.com/6c0N9I3.jpg").into(holder.ivThumbnail);
        }

        holder.btnComments.setTag(position);
        holder.ivThumbnail.setTag(holder);
        holder.btnSave.setTag(holder);

    }

    public void updateItems() {
        itemsCount = 10;
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

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        DynamicHeightNetworkImageView ivThumbnail;
        TextView ivFeedBottom;
        ImageButton btnComments;
        ImageButton btnSave;
        TextSwitcher tsUpvotesCounter;

        public CellFeedViewHolder(View view) {
            super(view);

            ivThumbnail = (DynamicHeightNetworkImageView) view.findViewById(R.id.ivFeedThumbnail);
            ivFeedBottom = (TextView) view.findViewById(R.id.tvFeedTitle);
            btnComments = (ImageButton) view.findViewById(R.id.btnComments);
            btnSave = (ImageButton) view.findViewById(R.id.btnSave);
            tsUpvotesCounter = (TextSwitcher) view.findViewById(R.id.tsUpvotesCounter);
        }
    }
}
