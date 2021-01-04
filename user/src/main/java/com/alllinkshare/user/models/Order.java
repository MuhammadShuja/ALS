package com.alllinkshare.user.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private String reference;
    private String status;
    private int totalItems;
    private double totalPrice;
    private int userAddressId;
    private String paymentMethod;
    private String date;

    private List<OrderItem> items = new ArrayList<>();

    public Order(int id, String reference, String status, double totalPrice, String date) {
        this.id = id;
        this.reference = reference;
        this.status = status;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public Order(int id, String reference, String status, int totalItems, double totalPrice, int userAddressId, String paymentMethod, String date) {
        this.id = id;
        this.reference = reference;
        this.status = status;
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
        this.userAddressId = userAddressId;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getUserAddressId() {
        return userAddressId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDate() {
        return date;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatusIcon(){
        switch(getStatus()){
            case "Pending":
                return "status_pending";

            case "Delivered":
                return "status_succeeded";

            case "Delivery failed":
            case "Cancelled":
            case "Rejected":
            case "Returned":
                return "status_failed";

            default:
                return "status_processing";
        }
    }

    public boolean canViewContactDetails(){
        switch (getStatus()){
            case "Pending":
            case "Delivery failed":
            case "Cancelled":
            case "Rejected":
            case "Returned":
                return false;

            default:
                return true;
        }
    }

    public String getPaymentStatusIcon(){
        switch(getStatus()){
            case "Pending":
                return "status_pending";

            case "Succeeded":
                return "status_succeeded";

            case "Failed":
            case "Canceled":
                return "status_failed";

            default:
                return "status_processing";
        }
    }
}
