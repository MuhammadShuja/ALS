package com.alllinkshare.catalog.api.config;

import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.catalog.models.Listing;
import com.alllinkshare.catalog.models.ListingCategory;

import java.util.List;

public class Listeners {

    public interface CategoriesListener{
        void onSuccess(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> itemList);
        void onFailure(String error);
    }

    public interface ListingsListener{
        void onSuccess(List<Listing> listings, int currentPageNumber, int lastPageNumber, int totalListings);
        void onFailure(String error);
    }

    public interface ListingCategoriesListener{
        void onSuccess(List<ListingCategory> categories, int currentPageNumber, int lastPageNumber, int totalCategories);
        void onFailure(String error);
    }

    public interface ListingListener{
        void onSuccess(Listing listing);
        void onFailure(String error);
    }
}