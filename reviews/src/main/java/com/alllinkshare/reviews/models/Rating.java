package com.alllinkshare.reviews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rating implements Parcelable {
    @SerializedName("rating")
    private float rating;

    @SerializedName("total")
    private int total;

    @SerializedName("stars")
    private Stars stars;

    @SerializedName("percentage")
    private Percentage percentage;

    protected Rating(Parcel in) {
        rating = in.readFloat();
        total = in.readInt();
        stars = in.readParcelable(Stars.class.getClassLoader());
        percentage = in.readParcelable(Percentage.class.getClassLoader());
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public float getRating() {
        return rating;
    }

    public int getTotal() {
        return total;
    }

    public Stars getStars() {
        return stars;
    }

    public Percentage getPercentage() {
        return percentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(rating);
        dest.writeInt(total);
        dest.writeParcelable(stars, flags);
        dest.writeParcelable(percentage, flags);
    }
}