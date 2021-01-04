package com.alllinkshare.user.models;

public class Coupon {
    private int id;
    private String status;
    private String company;
    private String category;
    private String name;
    private String code;
    private String type;
    private String discount;
    private String amount;
    private String formDays;

    public Coupon(int id, String status, String company, String category, String name, String code, String type, String discount, String amount, String formDays) {
        this.id = id;
        this.status = status;
        this.company = company;
        this.category = category;
        this.name = name;
        this.code = code;
        this.type = type;
        this.discount = discount;
        this.amount = amount;
        this.formDays = formDays;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCompany() {
        return company;
    }

    public String getCategory() {
        return category;
    }

    public String getName(){
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getDiscount() {
        return discount;
    }

    public String getAmount() {
        return amount;
    }

    public String getFormDays() {
        return formDays;
    }
}