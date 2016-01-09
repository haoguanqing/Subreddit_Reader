package com.guanqing.subredditor.Utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Guanqing on 2015/12/29.
 */
public class ImgurUtil {
    public enum LinkType {OTHER, GFYCAT, IMGUR_IMAGE, IMGUR_GIF, IMGUR_LINK, IMGUR_GALLERY, IMGUR_ALBUM, IMAGE, GIF, ERROR}

    public static LinkType getLinkType(String url){
        try {
            URL parseUrl = new URL(url);
            Uri.parse(url);
        } catch (MalformedURLException e){
            return LinkType.ERROR;
        }

        if (url.contains("imgur.com/")){
            if (url.endsWith(".jpg") || url.endsWith(".png")) {
                return LinkType.IMGUR_IMAGE;
            } else if (url.endsWith(".gifv") || url.endsWith(".gif")){
                return LinkType.IMGUR_GIF;
            } else if (url.contains("imgur.com/gallery/")){
                return LinkType.IMGUR_GALLERY;
            } else if (url.contains("imgur.com/a/")){
                return LinkType.IMGUR_ALBUM;
            } else{
                return LinkType.IMGUR_LINK;
            }
        } else if (url.contains("gfycat.com/")){
            return LinkType.GFYCAT;
        } else if (url.endsWith(".jpg") || url.endsWith(".png")) {
            return LinkType.IMAGE;
        } else if (url.endsWith(".gifv") || url.endsWith(".gif")){
            return LinkType.GIF;
        } else{
            return LinkType.OTHER;
        }
    }

    /**
     *
     * @param link
     * @return
     */
    public static String getLinkId(String link){
        return link.substring(link.lastIndexOf("/"), link.length());
    }

    /**
     *
     * @param link
     * @return
     */
    public static String getImageId(String link){
        if(link.contains("i.imgur.com")) {
            return link.substring(link.lastIndexOf("/"), link.length() - 4);
        }
        return null;
    }
}
