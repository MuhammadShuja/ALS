package com.alllinkshare.reviews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Stars implements Parcelable {
    @SerializedName("1")
    private int oneStars;

    @SerializedName("2")
    private int twoStars;

    @SerializedName("3")
    private int threeStars;

    @SerializedName("4")
    private int fourStars;

    @SerializedName("5")
    private int fiveStars;

    protected Stars(Parcel in) {
        oneStars = in.readInt();
        twoStars = in.readInt();
        threeStars = in.readInt();
        fourStars = in.readInt();
        fiveStars = in.readInt();
    }

    public static final Creator<Stars> CREATOR = new Creator<Stars>() {
        @Override
        public Stars createFromParcel(Parcel in) {
            return new Stars(in);
        }

        @Override
        public Stars[] newArray(int size) {
            return new Stars[size];
        }
    };

    public int getOneStars() {
        return oneStars;
    }

    public int getTwoStars() {
        return twoStars;
    }

    public int getThreeStars() {
        return threeStars;
    }

    public int getFourStars() {
        return fourStars;
    }

    public int getFiveStars() {
        return fiveStars;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(oneStars);
        dest.writeInt(twoStars);
        dest.writeInt(threeStars);
        dest.writeInt(fourStars);
        dest.writeInt(fiveStars);
    }
}