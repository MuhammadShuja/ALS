package com.alllinkshare.sales.cart.models;

import com.alllinkshare.sales.cart.listeners.CartItemListener;

public class CartItem {
//    COMMON PROPERTIES
    private String uuid;
    private int listingId;
    private int id;
    private String name;
    private String thumbnail;
    private int quantity;
    private double price;
    private double subTotal;
    private double shippingPrice;

//    FOOD PROPERTIES
    private String freeGift;

//    SHOPPING PROPERTIES
    private String color;
    private String size;

    private CartItemListener listener;

    public CartItem(String uuid, int listingId, int id, String name, String thumbnail, double price, int quantity, CartItemListener listener) {
        this.uuid = uuid;
        this.listingId = listingId;
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
        this.quantity = quantity;
        this.listener = listener;

        updateItem();
    }


    public void setQuantity(int quantity){
        this.quantity = quantity;
        updateItem();
    }

    public void increaseQuantity(int quantity){
        this.quantity += quantity;
        updateItem();
    }

    public void decreaseQuantity(int quantity){
        this.quantity -= quantity;
        updateItem();
    }

    public String getUuid() {
        return uuid;
    }

    public int getListingId() {
        return listingId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setFreeGift(String freeGift) {
        this.freeGift = freeGift;
    }

    public String getFreeGift() {
        return freeGift;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void updateItem() {
        this.subTotal = quantity * price;
        listener.onItemUpdate(this);
    }
}