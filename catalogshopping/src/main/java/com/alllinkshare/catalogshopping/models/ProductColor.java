package com.alllinkshare.catalogshopping.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductColor {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("sizes")
    private List<ProductSize> sizes;

    public ProductColor(int id, String name, String image, List<ProductSize> sizes) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.sizes = sizes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<ProductSize> getSizes() {
        return sizes;
    }
}