package com.guanqing.subredditor;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanqing on 2015/12/25.
 */
public class FrontPageModel implements Parcelable {
    String id;
    String title;
    String thumbnailUrl;
    String link;
    int commentCount;
    int karma;
    List<String> links = new ArrayList<>();

    /**
     *
     * @param id
     * @param title
     * @param thumbnailUrl
     * @param link
     * @param commentCount
     * @param karma
     */
    public FrontPageModel(String id, String title, String thumbnailUrl, String link, int commentCount, int karma){
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.link = link;
        this.commentCount = commentCount;
        this.karma = karma;
    }

    protected FrontPageModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        thumbnailUrl = in.readString();
        link = in.readString();
        commentCount = in.readInt();
        karma = in.readInt();
        if (in.readByte() == 0x01) {
            links = new ArrayList<String>();
            in.readList(links, String.class.getClassLoader());
        } else {
            links = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(thumbnailUrl);
        dest.writeString(link);
        dest.writeInt(commentCount);
        dest.writeInt(karma);
        if (links == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(links);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FrontPageModel> CREATOR = new Parcelable.Creator<FrontPageModel>() {
        @Override
        public FrontPageModel createFromParcel(Parcel in) {
            return new FrontPageModel(in);
        }

        @Override
        public FrontPageModel[] newArray(int size) {
            return new FrontPageModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getKarma() {
        return karma;
    }

    public String getLink() {
        return link;
    }

    /**
     * reset the image link in the model
     * @param link
     */
    public void setLink(String link){
        this.link = link;
    }

    /**
     * get the image links in the gallery
     * @return galleryLinks
     */
    public List<String> getLinks() {
        return links;
    }

    /**
     * set gallery links data
     * @param links
     */
    public void setLinks(List<String> links) {
        this.links = links;
    }
}
