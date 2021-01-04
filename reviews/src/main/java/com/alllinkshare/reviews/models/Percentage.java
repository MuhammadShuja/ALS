package com.alllinkshare.reviews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Percentage implements Parcelable {
    @SerializedName("1")
    private double oneStars;

    @SerializedName("2")
    private double twoStars;

    @SerializedName("3")
    private double threeStars;

    @SerializedName("4")
    private double fourStars;

    @SerializedName("5")
    private double fiveStars;

    protected Percentage(Parcel in) {
        oneStars = in.readDouble();
        twoStars = in.readDouble();
        threeStars = in.readDouble();
        fourStars = in.readDouble();
        fiveStars = in.readDouble();
    }

    public static final Creator<Percentage> CREATOR = new Creator<Percentage>() {
        @Override
        public Percentage createFromParcel(Parcel in) {
            return new Percentage(in);
        }

        @Override
        public Percentage[] newArray(int size) {
            return new Percentage[size];
        }
    };

    public int getOneStars() {
        return (int) Math.round(oneStars);
    }

    public int getTwoStars() {
        return (int) Math.round(twoStars);
    }

    public int getThreeStars() {
        return (int) Math.round(threeStars);
    }

    public int getFourStars() {
        return (int) Math.round(fourStars);
    }

    public int getFiveStars() {
        return (int) Math.round(fiveStars);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(oneStars);
        dest.writeDouble(twoStars);
        dest.writeDouble(threeStars);
        dest.writeDouble(fourStars);
        dest.writeDouble(fiveStars);
    }
}