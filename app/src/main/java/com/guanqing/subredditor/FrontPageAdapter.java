package com.guanqing.subredditor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guanqing.subredditor.util.ListenerUtil;

import java.util.ArrayList;
import java.util.List;


public class FrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FrontPageModel> dataSet;

    private Context mContext;

    private FrontPageFeedViewHolder holder;

    public FrontPageAdapter(Context context) {
        mContext = context;
        dataSet = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.frontpage_list_item, parent, false);
        return new FrontPageFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        holder = (FrontPageFeedViewHolder) viewHolder;
        try{
            bindFeedItem(position, holder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void bindFeedItem(int position, final FrontPageFeedViewHolder holder) throws Exception{

        final FrontPageModel frontpageModel = dataSet.get(position);
        // configure a proper onClickListener for each case
        // imgur image, gif, gallery, not imgur, etc.
        View.OnClickListener onClickListener = ListenerUtil.getProperOnClickListener(mContext, frontpageModel);
        boolean isGif = false;
        //detect whether the content is animated gif
        if (frontpageModel.link.endsWith(".gif") || frontpageModel.link.endsWith(".gifv")){
            isGif = true;
        }

        if (frontpageModel.thumbnailUrl == null) {
            //if there is no pic, do not inflate imageview & increase the maxLines of the title to 5
            holder.ivThumbnail.setVisibility(View.GONE);
            holder.tvFeedTitle.setMaxLines(5);
        } else {
            //set image res and onClickListener
            holder.ivThumbnail.setVisibility(View.VISIBLE);
            holder.tvFeedTitle.setMaxLines(3);
            Glide.with(mContext).load(frontpageModel.thumbnailUrl)
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error_gray)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(holder.ivThumbnail);
        }
        //set thumbnail's on click listener
        holder.ivThumbnail.setOnClickListener(onClickListener);

        //set gif icon visibility
        holder.ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);

        //set karma number
        holder.tvKarma.setText(frontpageModel.karma + "");

        //set title text and onClickListener
        holder.tvFeedTitle.setText(frontpageModel.title);
        holder.tvFeedTitle.setOnClickListener(onClickListener);

        //set comment number
        holder.tvCommentsNum.setText(frontpageModel.commentCount+"");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * get the current data in adapter
     * @return dataSet
     */
    public List<FrontPageModel> getData() {
        return dataSet;
    }

    /**
     * add data to the top of the data list
     * @param data
     */
    public void addNewData(List<FrontPageModel> data) {
        if (data != null) {
            dataSet.addAll(0, data);
            notifyItemRangeInserted(0, data.size());
        }
    }

    /**
     * add data to the end of the data list
     * @param data
     */
    public void addMoreData(List<FrontPageModel> data) {
        if (data != null) {
            dataSet.addAll(dataSet.size(), data);
            notifyItemRangeInserted(dataSet.size(), data.size());
        }
    }

    /**
     * set new data; if null, clear the data list
     * @param data
     */
    public void setData(List<FrontPageModel> data) {
        if (data != null) {
            dataSet = data;
        } else {
            dataSet.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * clear the data list
     */
    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }



    public static class FrontPageFeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvFeedTitle;
        ImageButton btnComments;
        TextView tvCommentsNum;
        TextView tvKarma;
        ImageView ivGifIcon;

        public FrontPageFeedViewHolder(View view) {
            super(view);

            ivThumbnail = (ImageView) view.findViewById(R.id.ivFeedThumbnail);
            tvFeedTitle = (TextView) view.findViewById(R.id.tvFeedTitle);
            btnComments = (ImageButton) view.findViewById(R.id.btnComments);
            tvCommentsNum = (TextView) view.findViewById(R.id.tvCommentCount);
            tvKarma = (TextView)view.findViewById(R.id.tvKarma);
            ivGifIcon = (ImageView)view.findViewById(R.id.ivGifIcon);
        }
    }
}
