package com.alllinkshare.catalog.api.retrofit.responses;

import com.alllinkshare.catalog.models.Category;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CatalogCategoriesResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("parent_id")
    private int parentId;

    @SerializedName("parent_name")
    private String parentName;

    @SerializedName("cover_image")
    private String coverImage;

    @SerializedName("children_count")
    private int childrenCount;

    @SerializedName("layout_style")
    private String layoutStyle;

    @SerializedName("categories")
    private List<Category> categories = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public int getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public String getLayoutStyle() {
        return layoutStyle;
    }

    public List<Category> getCategories() {
        return categories;
    }
}