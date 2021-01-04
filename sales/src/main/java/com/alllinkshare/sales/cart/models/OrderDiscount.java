package com.alllinkshare.sales.cart.models;

import com.google.gson.annotations.SerializedName;

public class OrderDiscount {
    @SerializedName("listing_id")
    private int listingId;

    @SerializedName("order_total")
    private double total;

    @SerializedName("order_discount")
    private double discount;

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}