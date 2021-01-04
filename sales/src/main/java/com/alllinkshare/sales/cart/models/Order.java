package com.alllinkshare.sales.cart.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("address")
    private String address;

    @SerializedName("detailed_address")
    private String detailedAddress;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("delivery_instructions")
    private String instructions;

    @SerializedName("delivery_date")
    private String deliveryDate;

    @SerializedName("delivery_time")
    private String deliveryTime;

    @SerializedName("delivery_type")
    private String deliveryType;

    @SerializedName("order_type")
    private String orderType;

    @SerializedName("payment_status")
    private String paymentStatus;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private double price;

    @SerializedName("items")
    private List<OrderItem> items;

    @SerializedName("discount")
    private OrderDiscount discount;

    public Order(String orderType, int quantity, double price, List<OrderItem> items) {
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
        this.items = items;
    }

    public void setCustomerDetails(String customerName,
                                   String address, String detailedAddress, String latitude, String longitude,
                                   String email, String mobile) {
        this.customerName = customerName;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.mobile = mobile;
    }

    public void setDeliveryDetails(String instructions, String deliveryDate, String deliveryTime, String deliveryType) {
        this.instructions = instructions;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setDiscount(OrderDiscount discount){
        this.discount = discount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderDiscount getDiscount() {
        return discount;
    }
}