package com.guanqing.subredditor;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guanqing.subredditor.Retrofit.ImgurClient;
import com.guanqing.subredditor.Retrofit.ImgurService;
import com.guanqing.subredditor.Retrofit.Models.GalleryModel;
import com.guanqing.subredditor.Retrofit.Models.ImageData;
import com.guanqing.subredditor.Retrofit.Models.ImageModel;
import com.guanqing.subredditor.UI.Fragments.ZoomDialog;
import com.guanqing.subredditor.Util.ImgurUtil;
import com.guanqing.subredditor.Util.ToastUtil;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class FrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FrontPageModel> dataSet;

    private Context context;

    private FragmentManager fm;
    private FrontPageFeedViewHolder holder;

    public FrontPageAdapter(Context context) {
        this.context = context;
        dataSet = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.frontpage_list_item, parent, false);
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

        final FrontPageModel frontpageModel = dataSet.get(position);
        // configure a proper onClickListener for each case
        // imgur image, gif, gallery, not imgur, etc.
        View.OnClickListener onClickListener = getProperOnClickListener(frontpageModel);
        boolean isGif = false;
        //detect whether the content is animated gif
        if (frontpageModel.link.endsWith(".gif") || frontpageModel.link.endsWith(".gifv")){
            isGif = true;
        }
        //set gif icon visibility
        holder.ivGifIcon.setVisibility(isGif ? View.VISIBLE : View.GONE);

        if (frontpageModel.thumbnailUrl == null) {
            //if there is no pic, do not inflate imageview & increase the maxLines of the title to 5
            holder.ivThumbnail.setImageResource(android.R.color.transparent);
            holder.ivThumbnail.setMaxHeight(0);
            holder.tvFeedTitle.setMaxLines(5);
        } else {
            //set image res and onClickListener
            Glide.with(context).load(frontpageModel.thumbnailUrl)
                    //.placeholder(R.drawable.avatar_loading)
                    .error(R.drawable.error_gray)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(holder.ivThumbnail);
        }

        holder.ivThumbnail.setOnClickListener(onClickListener);

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


    /**
     * =====================================================================================
     * ============================== HELPER FUNCTIONS =====================================
     * =====================================================================================
     */
    private View.OnClickListener getProperOnClickListener(final FrontPageModel frontpageModel){
        //dismiss any dialog which is currently showing on top
        Fragment prev = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag(ZoomDialog.DIALOG_FLAG);
        if (prev != null){
            ZoomDialog df = (ZoomDialog) prev;
            df.dismiss();
            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().remove(prev);
        }

        View.OnClickListener onClickListener;
        ImgurClient client = ImgurClient.getInstance();
        client.configureRestAdapter();
        ImgurService service;
        final String link = frontpageModel.link;

        //detect link type
        ImgurUtil.LinkType linkType = ImgurUtil.getLinkType(link);
        switch (linkType){
            case NOT_IMGUR:
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: new webview
                        ToastUtil.show(link);
                    }
                };
                break;

            case IMGUR_GALLERY:
                //get the links of the images in the gallery using Imgur API
                service = client.getClient(ImgurService.class);
                Call<GalleryModel> galleryCall = service.getGallery(ImgurUtil.getId(link));
                galleryCall.enqueue(new Callback<GalleryModel>() {
                    @Override
                    public void onResponse(Response<GalleryModel> response, Retrofit retrofit) {
                        GalleryModel gallery = response.body();

                        if (gallery == null) {
                            //404 or the response cannot be converted to ImageModel.
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody != null) {
                                Log.e("HGQ", "responseBody = " + responseBody.toString());
                            } else {
                                Log.e("HGQ", "responseBody = null");
                            }
                        } else {
                            //200 - success
                            //set the correct links in the model
                            if(gallery.getData().isAlbum()){
                                List<String> list = new ArrayList<>();
                                for(ImageData imageData: gallery.getData().getImages()){
                                    if (imageData.isAnimated()){
                                        list.add(imageData.getMp4());
                                    }else{
                                        list.add(imageData.getLink());
                                    }
                                }
                                frontpageModel.setLinks(list);
                            }else{
                                if (gallery.getData().isAnimated()){
                                    frontpageModel.setLink(gallery.getData().getMp4());
                                }else{
                                    frontpageModel.setLink(gallery.getData().getLink());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("HGQ", "t = " + t.getMessage());
                    }
                });
                //set on click listener
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: new gallery view
                        ZoomDialog fragment = ZoomDialog.newInstance(frontpageModel);
                        fragment.show(fm, ZoomDialog.DIALOG_FLAG);
                    }
                };
                break;

            case IMGUR_LINK:
                //get the link of the image using Imgur API
                service = client.getClient(ImgurService.class);
                Call<ImageModel> imageCall = service.getImage(ImgurUtil.getId(link));
                imageCall.enqueue(new Callback<ImageModel>() {
                    @Override
                    public void onResponse(Response<ImageModel> response, Retrofit retrofit) {
                        ImageModel image = response.body();
                        if (image == null) {
                            //404 or the response cannot be converted to ImageModel.
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody != null) {
                                Log.e("HGQ","responseBody = " + responseBody.toString());
                            } else {
                                Log.e("HGQ","responseBody = null");
                            }
                        } else {
                            //200 - success
                            //set the correct link in the model
                            if (image.getData().isAnimated()){
                                frontpageModel.setLink(image.getData().getMp4());
                            }else{
                                frontpageModel.setLink(image.getData().getLink());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("HGQ", "t = " + t.getMessage());
                    }
                });

                //set on click listener
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ZoomDialog fragment = ZoomDialog.newInstance(frontpageModel);
                        fragment.show(fm, ZoomDialog.DIALOG_FLAG);
                    }
                };
                break;

            case GIF:
                if(link.contains("imgur")) {
                    //change the gif link to mp4 if the GIF is from imgur.com
                    if (link.endsWith(".gifv")) {
                        frontpageModel.setLink(link.substring(0, link.length()-4) + "gif");
                    }
                    if (link.endsWith(".gif")) {
                        frontpageModel.setLink(link.substring(0, link.length()-3) + "gif");
                    }
                }
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show new detailed dialog
                        ZoomDialog fragment = ZoomDialog.newInstance(frontpageModel);
                        fragment.show(fm, ZoomDialog.DIALOG_FLAG);
                    }
                };
                break;

            case IMAGE:
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show new detailed dialog
                        ZoomDialog fragment = ZoomDialog.newInstance(frontpageModel);
                        fragment.show(fm, ZoomDialog.DIALOG_FLAG);
                    }
                };
                break;

            default:
                onClickListener = null;
                break;

        }
        return onClickListener;
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
