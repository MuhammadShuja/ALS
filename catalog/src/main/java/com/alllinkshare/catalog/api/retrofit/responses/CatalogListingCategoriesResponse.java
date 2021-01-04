package com.alllinkshare.catalog.api.retrofit.responses;

import com.alllinkshare.catalog.models.ListingCategory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatalogListingCategoriesResponse {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("first_page_url")
    private String firstPageUrl;

    @SerializedName("from")
    private int from;

    @SerializedName("last_page")
    private int lastPage;

    @SerializedName("last_page_url")
    private String lastPageUrl;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    @SerializedName("path")
    private String path;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("prev_page_url")
    private String prevPageUrl;

    @SerializedName("to")
    private int to;

    @SerializedName("total")
    private int total;

    @SerializedName("data")
    private List<ListingCategory> data;

    public int getCurrentPage() {
        return currentPage;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public int getFrom() {
        return from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String getPath() {
        return path;
    }

    public int getPerPage() {
        return perPage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public int getTo() {
        return to;
    }

    public int getTotal() {
        return total;
    }

    public List<ListingCategory> getData() {
        return data;
    }
}