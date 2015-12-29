package com.guanqing.subredditor;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guanqing on 2015/12/25.
 */
public class StaggeredModel implements Parcelable {
    String id;
    String imageUrl;
    String title;
    int commentCount;
    int karma;

    public StaggeredModel(String id, String imageUrl, String title, int commentCount, int karma){
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.commentCount = commentCount;
        this.karma = karma;
    }

    protected StaggeredModel(Parcel in) {
        id = in.readString();
        imageUrl = in.readString();
        title = in.readString();
        commentCount = in.readInt();
        karma = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeInt(commentCount);
        dest.writeInt(karma);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StaggeredModel> CREATOR = new Parcelable.Creator<StaggeredModel>() {
        @Override
        public StaggeredModel createFromParcel(Parcel in) {
            return new StaggeredModel(in);
        }

        @Override
        public StaggeredModel[] newArray(int size) {
            return new StaggeredModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
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
}
