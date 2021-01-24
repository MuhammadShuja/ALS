package com.alllinkshare.user.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private String name;
    private String status;
    private int quantity;
    private String price;
    private String date;

    public Order(int id, String name, String status, int quantity, String price, String date) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}