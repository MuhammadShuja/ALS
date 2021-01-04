package com.alllinkshare.restaurant.models;

import com.google.gson.annotations.SerializedName;

public class FoodFreeGift {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail")
    private String thumbnail;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}