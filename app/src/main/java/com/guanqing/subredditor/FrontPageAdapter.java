package com.guanqing.subredditor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guanqing.subredditor.UI.UpvoteTextSwitcher;


public class FrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;
    private boolean isUpvoted = false;

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

    private void bindDefaultFeedItem(int position, final CellFeedViewHolder holder) {
        if (position % 2 == 0) {
            holder.ivThumbnail.setImageUrl("http://i.imgur.com/6c0N9I3.jpg",
                    ImageLoaderHelper.getInstance(context).getImageLoader());
        } else {
            holder.ivThumbnail.loadGifUrl("http://i.imgur.com/vvThMVa.gif");
        }



        holder.btnComments.setTag(position);
        holder.ivThumbnail.setTag(holder);
        holder.tsUpvotesCounter.setTag(holder);
        holder.tsUpvotesCounter.setListener(978);
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
        com.guanqing.subredditor.UI.DynamicHeightNetworkImageView ivThumbnail;
        TextView ivFeedBottom;
        ImageButton btnComments;
        ImageButton btnSave;
        UpvoteTextSwitcher tsUpvotesCounter;

        public CellFeedViewHolder(View view) {
            super(view);

            ivThumbnail = (com.guanqing.subredditor.UI.DynamicHeightNetworkImageView) view.findViewById(R.id.ivFeedThumbnail);
            ivFeedBottom = (TextView) view.findViewById(R.id.tvFeedTitle);
            btnComments = (ImageButton) view.findViewById(R.id.btnComments);
            btnSave = (ImageButton) view.findViewById(R.id.btnSave);
            tsUpvotesCounter = (UpvoteTextSwitcher) view.findViewById(R.id.tsUpvotesCounter);
        }
    }
}
