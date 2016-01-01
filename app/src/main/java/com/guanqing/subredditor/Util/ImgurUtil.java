package com.guanqing.subredditor.Util;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Guanqing on 2015/12/29.
 */
public class ImgurUtil {
    public enum LinkType {NOT_IMGUR, IMAGE, GIF, IMGUR_LINK, IMGUR_GALLERY, ERROR};

    public static LinkType getLinkType(String url){
        if (!url.contains("imgur")){
            return LinkType.NOT_IMGUR;
        }else if(url.startsWith("http://i.imgur.com/")){
            if(url.endsWith(".gifv") || url.endsWith(".gif")){
                return LinkType.GIF;
            }else{
                return LinkType.IMAGE;
            }
        }else if (url.startsWith("http://imgur.com/gallery/")){
            return LinkType.IMGUR_GALLERY;
        }else if (url.startsWith("http://imgur.com/")){
            return LinkType.IMGUR_LINK;
        }else{
            return LinkType.ERROR;
        }
    }

    public static String getId(String link){
        return link.substring(link.lastIndexOf("/"), link.length());
    }

    /**
     * get image link via Imgur API
     * @param webpageUrl
     * @return imageUrl
     */
    public static String getLink(String webpageUrl) throws Exception{
        webpageUrl = convertImgurUrl(webpageUrl);
        String json = readUrl(webpageUrl);
        JSONObject data = new JSONObject(json).getJSONObject("data");
        if(data.getString("type").equals("image/gif")){
            return data.getString("mp4");
        }
        return data.getString("link");
    }


    /**
     * get the image link in another way
     * when caught FileNotFoundException connecting to the Imgur API
     * @param url
     * @return suedoImageUrl
     */
    @Nullable
    public static String getLinkWhenError(String url){
        if(url==null) return null;
        //download the file as MP4 first since we don't know what format it is
        //if Glide failed to decode the file, rename the file to JPG
        return url.replace("imgur.com", "i.imgur.com") + ".mp4";
    }

    /**
     * get the links from an imgur gallery via Imgur API
     * @param webpageUrl
     * @return imageLinks
     * @throws Exception
     */
    public static String[] getLinksFromGallery(String webpageUrl) throws Exception{
        webpageUrl = convertGalleryUrl(webpageUrl);
        String json = readUrl(webpageUrl);
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
        return url.replace("http://imgur.com/", "https://api.imgur.com/3/image/");
    }

    private static String convertGalleryUrl(String url){
        return url.replace("http://imgur.com/", "https://api.imgur.com/3/gallery/");
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
