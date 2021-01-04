package com.alllinkshare.reviews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReviewImage implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("source")
    private String source;

    @SerializedName("caption")
    private String caption;

    protected ReviewImage(Parcel in) {
        id = in.readInt();
        source = in.readString();
        caption = in.readString();
    }

    public static final Creator<ReviewImage> CREATOR = new Creator<ReviewImage>() {
        @Override
        public ReviewImage createFromParcel(Parcel in) {
            return new ReviewImage(in);
        }

        @Override
        public ReviewImage[] newArray(int size) {
            return new ReviewImage[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getCaption() {
        return caption;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(source);
        dest.writeString(caption);
    }
}