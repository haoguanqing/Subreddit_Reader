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
import com.guanqing.subredditor.Fragments.ZoomDialog;
import com.guanqing.subredditor.Fragments.ZoomGifDialog;

import java.util.ArrayList;
import java.util.List;


public class FrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<StaggeredModel> dataSet;

    private Context context;

    private FragmentManager fm;
    private FrontPageFeedViewHolder holder;

    public FrontPageAdapter(Context context) {
        this.context = context;
        dataSet = new ArrayList<>();
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
            bindFeedItem(position, holder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void bindFeedItem(int position, final FrontPageFeedViewHolder holder) throws Exception{
        final StaggeredModel model = dataSet.get(position);
        View.OnClickListener clickListener;
        boolean isGif = false;

        if (model.thumbnailUrl !=null){
            if (model.thumbnailUrl.endsWith(".gif") || model.thumbnailUrl.endsWith(".gifv")) {
                isGif = true;
                if (model.thumbnailUrl.endsWith(".gifv")) {
                    model.thumbnailUrl = model.thumbnailUrl.substring(0, model.thumbnailUrl.length() - 1);
                }
            }
        }

        //set gif icon visibility
        holder.ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);

        holder.tvUpvotesCounter.setText(model.karma+"");

        if (isGif){
            //if image is gif
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show new detailed gif dialog
                    ZoomGifDialog fragment = ZoomGifDialog.newInstance();
                    fragment.show(fm, "zoom dialog");
                }
            };

            //set image res and onClickListener
            Glide.with(context).load(model.thumbnailUrl)
                    .asBitmap()
                    .placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error)
                    .thumbnail(0.1f)
                    .into(holder.ivThumbnail);

            holder.ivThumbnail.setOnClickListener(clickListener);

            //set gif icon visible
            holder.ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);

        }else {
            //if image is not gif or is null
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show new detailed dialog
                    ZoomDialog fragment = ZoomDialog.newInstance(model);
                    fragment.show(fm, "zoom dialog");
                }
            };

            if (model.thumbnailUrl == null) {
                //if there is no pic, do not inflate imageview & increase the maxLines of the title to 4
                holder.tvFeedTitle.setMaxLines(5);
            } else {
                //set image res and onClickListener
                Glide.with(context).load(model.thumbnailUrl)
                        //.placeholder(R.drawable.avatar_loading)
                        .error(R.drawable.error)
                        .thumbnail(0.1f)
                        .crossFade()
                        .into(holder.ivThumbnail);

                holder.ivThumbnail.setOnClickListener(clickListener);
            }

        }

        //set title text and onClickListener
        holder.tvFeedTitle.setText(model.title);
        holder.tvFeedTitle.setOnClickListener(clickListener);
        //set comment number
        holder.tvCommentsNum.setText(model.commentCount+"");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * get the current data in adapter
     * @return dataSet
     */
    public List<StaggeredModel> getData() {
        return dataSet;
    }

    /**
     * add data to the top of the data list
     * @param data
     */
    public void addNewData(List<StaggeredModel> data) {
        if (data != null) {
            dataSet.addAll(0, data);
            notifyItemRangeInserted(0, data.size());
        }
    }

    /**
     * add data to the end of the data list
     * @param data
     */
    public void addMoreData(List<StaggeredModel> data) {
        if (data != null) {
            dataSet.addAll(dataSet.size(), data);
            notifyItemRangeInserted(dataSet.size(), data.size());
        }
    }

    /**
     * set new data; if null, clear the data list
     * @param data
     */
    public void setData(List<StaggeredModel> data) {
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
        TextView tvUpvotesCounter;
        ImageView ivGifIcon;

        public FrontPageFeedViewHolder(View view) {
            super(view);

            ivThumbnail = (ImageView) view.findViewById(R.id.ivFeedThumbnail);
            tvFeedTitle = (TextView) view.findViewById(R.id.tvFeedTitle);
            btnComments = (ImageButton) view.findViewById(R.id.btnComments);
            tvCommentsNum = (TextView) view.findViewById(R.id.tvCommentCount);
            tvUpvotesCounter = (TextView)view.findViewById(R.id.tvUpvotesCounter);
            ivGifIcon = (ImageView)view.findViewById(R.id.ivGifIcon);
        }
    }
}
