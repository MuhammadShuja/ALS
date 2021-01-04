package com.alllinkshare.user.models;

public class OrderItem {
    private int id;
    private String status;
    private int productId;
    private String name;
    private String thumbnail;
    private int quantity;
    private double itemPrice;
    private double totalPrice;

    public OrderItem(int id, int productId, String name, String thumbnail, int quantity, double itemPrice, double totalPrice) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}