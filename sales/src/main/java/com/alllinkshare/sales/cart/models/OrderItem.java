package com.alllinkshare.sales.cart.models;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("listing_id")
    private int listingId;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("product_image")
    private String productImage;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private double price;

    @SerializedName("sub_total")
    private double subTotal;

    @SerializedName("color")
    private String color;

    @SerializedName("size")
    private String size;

    @SerializedName("free_gift")
    private String freeGift;

    public OrderItem(int listingId, int productId, String productName, String productImage, int quantity, double price, double subTotal, String color, String size, String freeGift) {
        this.listingId = listingId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
        this.color = color;
        this.size = size;
        this.freeGift = freeGift;
    }

    public int getListingId() {
        return listingId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getFreeGift() {
        return freeGift;
    }
}