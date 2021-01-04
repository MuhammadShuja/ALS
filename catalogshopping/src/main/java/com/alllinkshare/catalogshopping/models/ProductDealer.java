package com.alllinkshare.catalogshopping.models;

import com.google.gson.annotations.SerializedName;

public class ProductDealer {
    @SerializedName("logo")
    private String logo;

    @SerializedName("price")
    private String price;

    @SerializedName("shipping_fee")
    private String shippingFee;

    @SerializedName("info")
    private String info;

    @SerializedName("action")
    private String action;

    public String getLogo() {
        return logo;
    }

    public String getPrice() {
        return price;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public String getInfo() {
        return info;
    }

    public String getAction() {
        return action;
    }
}