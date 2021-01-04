package com.alllinkshare.restaurant.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodTopping {
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

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private String price;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("available")
    private boolean isAvailable;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}