package com.alllinkshare.restaurant.models;

import android.text.TextUtils;
import android.util.Log;

import com.alllinkshare.reviews.models.Rating;
import com.alllinkshare.reviews.models.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodMenuItem {

    @Expose(serialize = false)
    private String uuid = null;

    public String getUuid() {
        if(uuid == null){
            uuid = getId()+getName();
        }
        return uuid;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("listing_id")
    private int listingId;

    @SerializedName("main_category_id")
    private int mainCategoryId;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("price")
    private String price;

    @SerializedName("dish_type")
    private String dishType;

    @SerializedName("extra_topping")
    private boolean extraTopping;

    @SerializedName("is_open")
    private boolean isOpen;

    @SerializedName("timings")
    private String timings;

    @SerializedName("delivery_fee")
    private String deliveryFee;

    @SerializedName("options")
    private String options;

    @SerializedName("description")
    private String description;

    @SerializedName("toppings")
    private ArrayList<FoodTopping> toppings;

    @SerializedName("gifts")
    private ArrayList<FoodFreeGift> freeGifts;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("reviews")
    private ArrayList<Review> reviews;

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getListingId() {
        return listingId;
    }

    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public String getDishType() {
        return dishType;
    }

    public boolean isExtraTopping() {
        return extraTopping;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getTimings() {
        return timings;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public String getOptions() {
        return options;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<FoodTopping> getToppings() {
        return toppings;
    }

    public ArrayList<FoodFreeGift> getFreeGifts() {
        return freeGifts;
    }

    public Rating getRating() {
        return rating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}