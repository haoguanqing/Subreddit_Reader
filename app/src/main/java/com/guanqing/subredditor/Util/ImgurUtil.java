package com.guanqing.subredditor.util;

/**
 * Created by Guanqing on 2015/12/29.
 */
public class ImgurUtil {
    public enum LinkType {OTHER, GFYCAT, IMAGE, IMGUR_GIF, IMGUR_LINK, IMGUR_GALLERY, ERROR};

    public static LinkType getLinkType(String url){
        if (url.contains("gfycat.com")){
            return LinkType.GFYCAT;
        } else if(url.endsWith(".jpg") || url.endsWith(".png")) {
            return LinkType.IMAGE;
        } else if (!url.contains("imgur.com")){
            return LinkType.OTHER;
        } else if(url.endsWith(".gifv") || url.endsWith(".gif")){
            return LinkType.IMGUR_GIF;
        } else if (url.startsWith("http://imgur.com/gallery/")){
            return LinkType.IMGUR_GALLERY;
        } else if (url.startsWith("http://imgur.com/")){
            return LinkType.IMGUR_LINK;
        } else{
            return LinkType.ERROR;
        }
    }

    public static String getId(String link){
        return link.substring(link.lastIndexOf("/"), link.length());
    }
}
