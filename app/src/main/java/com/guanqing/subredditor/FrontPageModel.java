package com.guanqing.subredditor;

import android.os.Parcel;
import android.os.Parcelable;

import com.guanqing.subredditor.Retrofit.Imgur.Models.ImageData;

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
    float aspectRatio;
    int commentCount;
    int karma;
    String submissionId;
    String subredditName;
    List<ImageData> imageDataList;

    /**
     *
     * @param id
     * @param title
     * @param thumbnailUrl
     * @param link
     * @param commentCount
     * @param karma
     * @param submissionId
     * @param subredditName
     */
    public FrontPageModel(String id, String title, String thumbnailUrl, String link, int commentCount, int karma, String submissionId, String subredditName){
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.link = link;
        this.commentCount = commentCount;
        this.karma = karma;
        this.subredditName = subredditName;
        this.submissionId = submissionId;
        aspectRatio = -1f;
        this.imageDataList = null;
    }

    protected FrontPageModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        thumbnailUrl = in.readString();
        link = in.readString();
        aspectRatio = in.readFloat();
        commentCount = in.readInt();
        karma = in.readInt();
        submissionId = in.readString();
        subredditName = in.readString();
        if (in.readByte() == 0x01) {
            imageDataList = new ArrayList<ImageData>();
            in.readList(imageDataList, ImageData.class.getClassLoader());
        } else {
            imageDataList = null;
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
        dest.writeFloat(aspectRatio);
        dest.writeInt(commentCount);
        dest.writeInt(karma);
        dest.writeString(submissionId);
        dest.writeString(subredditName);
        if (imageDataList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imageDataList);
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
    public List<ImageData> getImages() {
        return imageDataList;
    }

    /**
     * set gallery links data
     * @param imageDataList
     */
    public void setImages(List<ImageData> imageDataList) {
        this.imageDataList = imageDataList;
    }

    /**
     * set aspect ratio (width/height)
     * @param aspectRatio
     */
    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public String getSubredditName() {
        return subredditName;
    }
}
