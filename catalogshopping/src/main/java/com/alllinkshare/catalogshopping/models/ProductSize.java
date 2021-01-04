package com.alllinkshare.catalogshopping.models;

import com.google.gson.annotations.SerializedName;

public class ProductSize {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public ProductSize(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}