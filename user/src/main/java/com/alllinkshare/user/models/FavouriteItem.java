package com.alllinkshare.user.models;

import com.google.gson.annotations.SerializedName;

public class FavouriteItem {
    @SerializedName("id")
    private int id;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("listing_id")
    private int listingId;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("type")
    private String type;

    public FavouriteItem(int id, int productId, int categoryId, int listingId, String name, String thumbnail, String type) {
        this.id = id;
        this.productId = productId;
        this.categoryId = categoryId;
        this.listingId = listingId;
        this.name = name;
        this.thumbnail = thumbnail;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getListingId() {
        return listingId;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getType() {
        return type;
    }
}