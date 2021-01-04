package com.alllinkshare.catalog.models;

import com.google.gson.annotations.SerializedName;

public class ListingCategory {
    @SerializedName("id")
    private int id;

    @SerializedName("parent_id")
    private int parentId;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail")
    private String thumbnail;

    public ListingCategory(int id, int parentId, String name, String thumbnail) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}