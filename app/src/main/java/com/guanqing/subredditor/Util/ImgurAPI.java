package com.guanqing.subredditor.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Guanqing on 2015/12/29.
 */
public class ImgurAPI {
    public static final String NOT_IMGUR = "ImgurAPI.NOT_IMGUR";
    public static final String IS_IMAGE = "ImgurAPI.IS_IMAGE";
    public static final String IS_GIF = "ImgurAPI.IS_GIF";
    public static final String IS_URL = "ImgurAPI.IS_URL";
    public static final String IS_GALLERY = "ImgurAPI.IS_GALLERY";
    public static final String ERROR = "ImgurAPI.ERROR";

    public static String isImgur(String url){
        if (!url.contains("imgur")){
            return NOT_IMGUR;
        }else if(url.startsWith("http://i.imgur.com/")){
            if(url.endsWith(".gifv") || url.endsWith(".gif")){
                return IS_GIF;
            }else{
                return IS_IMAGE;
            }
        }else if (url.startsWith("http://imgur.com/gallery/")){
            return IS_GALLERY;
        }else if (url.startsWith("http://imgur.com/")){
            return IS_URL;
        }else{
            return ERROR;
        }
    }

    public static String getImage(String url) throws Exception{
        url = convertImgurUrl(url);
        String json = readUrl(url);
        JSONObject data = new JSONObject(json).getJSONObject("data");
        if(data.getString("type").equals("image/gif")){
            return data.getString("mp4");
        }
        return data.getString("link");
    }

    public static String[] getImagesFromGallery(String url) throws Exception{
        url = convertGalleryUrl(url);
        String json = readUrl(url);
        JSONObject data = new JSONObject(json).getJSONObject("data");
        int imagesCount = data.getInt("images_count");
        JSONArray images = data.getJSONArray("images");

        String[] links = new String[imagesCount];
        for (int i=0;i<imagesCount;i++){
            JSONObject o = images.getJSONObject(i);
            links[i] = o.getString("link");
        }
        return links;
    }

    private static String convertImgurUrl(String url){
        url = url.replace("http://imgur.com/", "");
        url = "https://api.imgur.com/3/image/" + url;
        return url;
    }

    private static String convertGalleryUrl(String url){
        url = url.replace("http://imgur.com/", "");
        url = "https://api.imgur.com/3/gallery/" + url;
        return url;
    }

    //read json string from url
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
