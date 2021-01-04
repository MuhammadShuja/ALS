package com.alllinkshare.reviews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReviewReaction implements Parcelable {

    @SerializedName("like")
    private int likeCount;

    @SerializedName("love")
    private int loveCount;

    @SerializedName("haha")
    private int happyCount;

    @SerializedName("wow")
    private int supriseCount;

    @SerializedName("angry")
    private int angryCount;

    @SerializedName("comments")
    private int commentsCount;

    protected ReviewReaction(Parcel in) {
        likeCount = in.readInt();
        loveCount = in.readInt();
        happyCount = in.readInt();
        supriseCount = in.readInt();
        angryCount = in.readInt();
        commentsCount = in.readInt();
    }

    public static final Creator<ReviewReaction> CREATOR = new Creator<ReviewReaction>() {
        @Override
        public ReviewReaction createFromParcel(Parcel in) {
            return new ReviewReaction(in);
        }

        @Override
        public ReviewReaction[] newArray(int size) {
            return new ReviewReaction[size];
        }
    };

    public int getLikeCount() {
        return likeCount;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public int getHappyCount() {
        return happyCount;
    }

    public int getSupriseCount() {
        return supriseCount;
    }

    public int getAngryCount() {
        return angryCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(likeCount);
        dest.writeInt(loveCount);
        dest.writeInt(happyCount);
        dest.writeInt(supriseCount);
        dest.writeInt(angryCount);
        dest.writeInt(commentsCount);
    }
}