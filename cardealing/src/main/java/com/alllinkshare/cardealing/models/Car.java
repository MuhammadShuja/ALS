package com.alllinkshare.cardealing.models;

import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("price")
    private String price;

    @SerializedName("model")
    private String model;

    @SerializedName("transmission")
    private String transmission;

    @SerializedName("mileage")
    private String mileage;

    public int getId() {
        return id;
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

    public String getModel() {
        return model;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getMileage() {
        return mileage;
    }
}