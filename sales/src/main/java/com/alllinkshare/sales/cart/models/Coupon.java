package com.alllinkshare.sales.cart.models;

import com.google.gson.annotations.SerializedName;

public class Coupon {
    @SerializedName("listing_id")
    private int listingId;

    @SerializedName("seller_name")
    private String sellerName;

    @SerializedName("type")
    private String type;

    @SerializedName("discount")
    private int discount;

    @SerializedName("description")
    private String description;

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}