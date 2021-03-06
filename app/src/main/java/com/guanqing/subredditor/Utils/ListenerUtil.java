package com.guanqing.subredditor.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.guanqing.subredditor.App;
import com.guanqing.subredditor.FrontPageModel;
import com.guanqing.subredditor.UI.Activities.WebViewActivity;
import com.guanqing.subredditor.UI.Fragments.ZoomDialog;
import com.guanqing.subredditor.Retrofit.Gfycat.GfycatClient;
import com.guanqing.subredditor.Retrofit.Gfycat.GfycatModel;
import com.guanqing.subredditor.Retrofit.Gfycat.GfycatService;
import com.guanqing.subredditor.Retrofit.Imgur.ImgurClient;
import com.guanqing.subredditor.Retrofit.Imgur.ImgurService;
import com.guanqing.subredditor.Retrofit.Imgur.Models.GalleryModel;
import com.guanqing.subredditor.Retrofit.Imgur.Models.ImageModel;
import com.guanqing.subredditor.UI.Fragments.ZoomGifDialog;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Guanqing on 2016/1/1.
 */
public class ListenerUtil {
/*    private static Context context = App.getInstance();
    private static FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
    private static String url;
    private static String title;

    static ImgurClient imgurClient;
    static GfycatClient gfycatClient;
    private static FrontPageModel frontpageModel;*/

    public static void frontPageOnClick(final FrontPageModel frontPageModel){
        Context context = App.getInstance();
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();

        //dismiss any dialog currently showing on top (if any)
        Fragment prev = fm.findFragmentByTag(ZoomDialog.DIALOG_FLAG);
        if (prev != null){
            ZoomDialog df = (ZoomDialog) prev;
            df.dismiss();
            fm.beginTransaction().remove(prev);
        }

        // initialize Imgur client & service
        ImgurClient imgurClient = ImgurClient.getInstance();
        imgurClient.configureRestAdapter();

        // initialize Gfycat client & service
        GfycatClient gfycatClient = GfycatClient.getInstance();
        gfycatClient.configureRestAdapter();

        String url = frontPageModel.getLink();
        String title = frontPageModel.getTitle();

        //detect link type
        ImgurUtil.LinkType linkType = ImgurUtil.getLinkType(url);

        switch (linkType){
            case OTHER:
                //other();
                break;
            case GFYCAT:
                //gfycat();
                break;
            case IMGUR_GALLERY:
                //get the links of the images in the gallery using Imgur API
                //imgur_gallery();
                break;
            case IMGUR_LINK:
                //get the link of the image using Imgur API
                //imgur_link();
                break;
            case IMGUR_GIF:
                //imgur_gif();
                break;
            case IMGUR_IMAGE:
                //image();
                break;
            default:
                break;
        }
    }

    private static void other(Context context, FrontPageModel model){
        // open link in webview
        Intent intent = new Intent(context, WebViewActivity.class)
                .putExtra(WebViewActivity.INTENT_URL_KEY, model.getLink())
                .putExtra(WebViewActivity.INTENT_TITLE_KEY, model.getTitle());
        context.startActivity(intent);
    }

    private static void gfycat(final FrontPageModel frontPageModel, final FragmentManager fm){
        //get the url of the gif with Gfycat REST client
        GfycatClient gfycatClient = GfycatClient.getInstance();
        gfycatClient.configureRestAdapter();
        GfycatService gfycatService = gfycatClient.getClient(GfycatService.class);
        Call<GfycatModel> imageCall = gfycatService.getGifData(ImgurUtil.getLinkId(frontPageModel.getLink()));
        imageCall.enqueue(new Callback<GfycatModel>() {
            @Override
            public void onResponse(Response<GfycatModel> response, Retrofit retrofit) {
                GfycatModel image = response.body();
                if (image == null) {
                    //404 or the response cannot be converted to ImageModel.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        Log.e("HGQ", "responseBody = " + responseBody.toString());
                    } else {
                        Log.e("HGQ", "responseBody = null");
                    }
                } else {
                    //200 - success
                    //set the correct link in the model
                    frontPageModel.setLink(image.getMp4Url());
                    showZoomFragment(frontPageModel, fm);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("HGQ", "t = " + t.getMessage());
            }
        });
    }

    private static void image(final FrontPageModel frontPageModel, FragmentManager fm){
        //show new zoom dialog
        showZoomFragment(frontPageModel, fm);
    }

    private static void imgur_gif(FrontPageModel frontPageModel){
        String url = frontPageModel.getLink();
        //change the gif link to mp4 if the GIF is from imgur.com
        if (url.endsWith(".gifv")) {
            frontPageModel.setLink(url.substring(0, url.length()-4) + "mp4");
        }else if (url.endsWith(".gif")) {
            frontPageModel.setLink(url.substring(0, url.length()-3) + "mp4");
        }
        url = frontPageModel.getLink();
        if(url.startsWith("http://imgur.com/")){
            frontPageModel.setLink(url.replace("//imgur", "//i.imgur"));
        }
    }

    private static void imgur_link(final FrontPageModel frontPageModel, ImgurClient imgurClient){
        //get the url of the image using Imgur API
        ImgurService imgurService = imgurClient.getClient(ImgurService.class);
        //make asynchronous REST calls
        Call<ImageModel> imageCall = imgurService.getImage(ImgurUtil.getLinkId(frontPageModel.getLink()));
        imageCall.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Response<ImageModel> response, Retrofit retrofit) {
                ImageModel image = response.body();
                if (image == null) {
                    //404 or the response cannot be converted to ImageModel.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        Log.e("HGQ", "responseBody = " + responseBody.toString());
                    } else {
                        Log.e("HGQ", "responseBody = null");
                    }
                } else {
                    //200 - success
                    //set the correct link in the model
                    if (image.getData().isAnimated()) {
                        frontPageModel.setLink(image.getData().getMp4());
                    } else {
                        frontPageModel.setLink(image.getData().getLink());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("HGQ", "t = " + t.getMessage());
            }
        });
    }



    private static void imgur_gallery(final FrontPageModel frontPageModel, ImgurClient imgurClient, final FragmentManager fm){
        //get the links of the images in the gallery using Imgur API
        ImgurService imgurService = imgurClient.getClient(ImgurService.class);
        Call<GalleryModel> galleryCall = imgurService.getGallery(ImgurUtil.getLinkId(frontPageModel.getLink()));
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
                    if (gallery.getData().isAlbum()) {
                        frontPageModel.setImages(gallery.getData().getImages());
                    } else {
                        if (gallery.getData().isAnimated()) {
                            frontPageModel.setLink(gallery.getData().getMp4());
                        } else {
                            frontPageModel.setLink(gallery.getData().getLink());
                        }
                        //TODO: new gallery view
                        showZoomFragment(frontPageModel, fm);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("HGQ", "t = " + t.getMessage());
            }
        });
    }






    /**
     * get a proper onClickListener for the FrontPageAdapter based on the link type
     * @param context
     * @param frontpageModel
     * @return onClickListener
     */
    public static View.OnClickListener getProperOnClickListener(final Context context, final FrontPageModel frontpageModel){
        //dismiss any dialog which is currently showing on top
        final FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        Fragment prev = fm.findFragmentByTag(ZoomDialog.DIALOG_FLAG);
        if (prev != null){
            ZoomDialog df = (ZoomDialog) prev;
            df.dismiss();
            fm.beginTransaction().remove(prev).commit();
        }

        View.OnClickListener onClickListener;
        final ImgurClient client = ImgurClient.getInstance();
        client.configureRestAdapter();
        ImgurService service;
        final String url = frontpageModel.getLink();
        final String title = frontpageModel.getTitle();

        //detect link type
        ImgurUtil.LinkType linkType = ImgurUtil.getLinkType(url);
        switch (linkType){
            case OTHER:
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // open link in webview
                        Intent intent = new Intent(context, WebViewActivity.class)
                                .putExtra(WebViewActivity.INTENT_URL_KEY, url)
                                .putExtra(WebViewActivity.INTENT_TITLE_KEY, title)
                                .putExtra(WebViewActivity.INTENT_SUBREDDIT_NAME_KEY, frontpageModel.getSubredditName());
                        context.startActivity(intent);
                    }
                };
                break;

            case IMGUR_ALBUM:
                frontpageModel.setLink(url.replace("/a/", "/gallery/"));
                ToastUtil.show("Imgur Album");

            case IMGUR_GALLERY:
                //get the data of the images in the gallery with Imgur API
                service = client.getClient(ImgurService.class);
                //make asynchronous REST calls
                Call<GalleryModel> galleryCall = service.getGallery(ImgurUtil.getLinkId(url));
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
                                frontpageModel.setImages(gallery.getData().getImages());
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
                imgur_link(frontpageModel, client);
                //set on click listener
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (frontpageModel.getLink().endsWith(".mp4")){
                            ZoomGifDialog fragment = ZoomGifDialog.newInstance(frontpageModel);
                            fragment.show(fm, ZoomGifDialog.DIALOG_FLAG);
                        } else{
                            ZoomDialog fragment = ZoomDialog.newInstance(frontpageModel);
                            fragment.show(fm, ZoomDialog.DIALOG_FLAG);
                        }
                    }
                };
                break;

            case IMGUR_GIF:
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgur_gif(frontpageModel);
                        //show new detailed dialog
                        ZoomGifDialog fragment = ZoomGifDialog.newInstance(frontpageModel);
                        fragment.show(fm, ZoomGifDialog.DIALOG_FLAG);
                    }
                };
                break;

            case IMGUR_IMAGE:
                //get the link of the image using Imgur API
                service = client.getClient(ImgurService.class);
                if(ImgurUtil.getImageId(url)!=null) {
                    Call<ImageModel> call = service.getImage(ImgurUtil.getImageId(url));
                    call.enqueue(new Callback<ImageModel>() {
                        @Override
                        public void onResponse(Response<ImageModel> response, Retrofit retrofit) {
                            ImageModel image = response.body();
                            if (image == null) {
                                //404 or the response cannot be converted to ImageModel.
                                ResponseBody responseBody = response.errorBody();
                                if (responseBody != null) {
                                    Log.e("HGQ", "responseBody = " + responseBody.toString());
                                } else {
                                    Log.e("HGQ", "responseBody = null");
                                }
                            } else {
                                //200 - success
                                float aspectRatio = (float) image.getData().getWidth() / image.getData().getHeight();
                                frontpageModel.setAspectRatio(aspectRatio);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("HGQ", "t = " + t.getMessage());
                        }
                    });
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

            case GFYCAT:
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gfycat(frontpageModel, fm);
                        ZoomGifDialog fragment = ZoomGifDialog.newInstance(frontpageModel);
                        fragment.show(fm, ZoomGifDialog.DIALOG_FLAG);
                    }
                };
                break;

            case GIF:
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



    private static void showZoomFragment(final FrontPageModel frontPageModel, FragmentManager fm){
        ZoomDialog fragment = ZoomDialog.newInstance(frontPageModel);
        fragment.show(fm, ZoomDialog.DIALOG_FLAG);
    }
}
