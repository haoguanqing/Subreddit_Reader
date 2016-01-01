package com.guanqing.subredditor.Util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.guanqing.subredditor.FrontPageModel;
import com.guanqing.subredditor.Retrofit.ImgurClient;
import com.guanqing.subredditor.Retrofit.ImgurService;
import com.guanqing.subredditor.Retrofit.Models.GalleryModel;
import com.guanqing.subredditor.Retrofit.Models.ImageData;
import com.guanqing.subredditor.Retrofit.Models.ImageModel;
import com.guanqing.subredditor.UI.Fragments.ZoomDialog;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class ListenerUtil {

    /**
     * get a proper onClickListener for the FrontPageAdapter based on the link type
     * @param context
     * @param frontpageModel
     * @return
     */
    public static View.OnClickListener getProperOnClickListener(Context context, final FrontPageModel frontpageModel){
        //dismiss any dialog which is currently showing on top
        final FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        Fragment prev = fm.findFragmentByTag(ZoomDialog.DIALOG_FLAG);
        if (prev != null){
            ZoomDialog df = (ZoomDialog) prev;
            df.dismiss();
            fm.beginTransaction().remove(prev);
        }

        View.OnClickListener onClickListener;
        ImgurClient client = ImgurClient.getInstance();
        client.configureRestAdapter();
        ImgurService service;
        final String link = frontpageModel.getLink();

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
                        frontpageModel.setLink(link.substring(0, link.length()-4) + "mp4");
                    }
                    if (link.endsWith(".gif")) {
                        frontpageModel.setLink(link.substring(0, link.length()-3) + "mp4");
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
}
