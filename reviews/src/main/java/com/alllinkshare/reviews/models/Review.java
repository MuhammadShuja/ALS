package com.alllinkshare.reviews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Review implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_image")
    private String userImage;

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("rating")
    private String rating;

    @SerializedName("service_rating")
    private String serviceRating;

    @SerializedName("food_rating")
    private String foodRating;

    @SerializedName("money_value_name")
    private String moneyValueRating;

    @SerializedName("ambiance_rating")
    private String ambianceRating;

    @SerializedName("comment")
    private String comment;

    @SerializedName("month")
    private String month;

    @SerializedName("year")
    private String year;

    @SerializedName("date")
    private String date;

    @SerializedName("images")
    private List<ReviewImage> images = new ArrayList<>();

    @SerializedName("reactions")
    private ReviewReaction reactions;

    protected Review(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        userName = in.readString();
        userImage = in.readString();
        userEmail = in.readString();
        rating = in.readString();
        serviceRating = in.readString();
        foodRating = in.readString();
        moneyValueRating = in.readString();
        ambianceRating = in.readString();
        comment = in.readString();
        month = in.readString();
        year = in.readString();
        date = in.readString();
        images = in.createTypedArrayList(ReviewImage.CREATOR);
        reactions = in.readParcelable(ReviewReaction.class.getClassLoader());
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getRating() {
        return rating;
    }

    public String getServiceRating() {
        return serviceRating;
    }

    public String getFoodRating() {
        return foodRating;
    }

    public String getMoneyValueRating() {
        return moneyValueRating;
    }

    public String getAmbianceRating() {
        return ambianceRating;
    }

    public String getComment() {
        return comment;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public List<ReviewImage> getImages() {
        return images;
    }

    public ReviewReaction getReactions() {
        return reactions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(userImage);
        dest.writeString(userEmail);
        dest.writeString(rating);
        dest.writeString(serviceRating);
        dest.writeString(foodRating);
        dest.writeString(moneyValueRating);
        dest.writeString(ambianceRating);
        dest.writeString(comment);
        dest.writeString(month);
        dest.writeString(year);
        dest.writeString(date);
        dest.writeTypedList(images);
        dest.writeParcelable(reactions, flags);
    }
}