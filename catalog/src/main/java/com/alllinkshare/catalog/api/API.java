package com.alllinkshare.catalog.api;

import android.util.Log;

import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.api.controllers.CategoriesController;
import com.alllinkshare.catalog.api.controllers.ListingCategoriesController;
import com.alllinkshare.catalog.api.controllers.ListingDetailsController;
import com.alllinkshare.catalog.api.controllers.ListingsController;
import com.alllinkshare.catalog.api.controllers.SearchController;

public class API {
    private static final String TAG = "API/Catalog";

    public static void categories(int parentId, boolean loadAll, Listeners.CategoriesListener listener){
        Log.d(TAG, "Calling categories endpoint...");
        CategoriesController.categories(parentId, loadAll, listener);
    }

    public static void listings(int categoryId, int pageNumber, String filter, String value, Listeners.ListingsListener listener){
        Log.d(TAG, "Calling listings endpoint...");
        ListingsController.listings(categoryId, pageNumber, filter, value, listener);
    }

    public static void search(int pageNumber, String query, Listeners.ListingsListener listener){
        Log.d(TAG, "Calling search endpoint...");
        SearchController.search(pageNumber, query, listener);
    }

    public static void listingCategories(String type, int listingId, int pageNumber, Listeners.ListingCategoriesListener listener){
        Log.d(TAG, "Calling listing categories endpoint...");
        ListingCategoriesController.categories(type, listingId, pageNumber, listener);
    }

    public static void listing(int listingId, int categoryId, Listeners.ListingListener listener){
        Log.d(TAG, "Calling listing endpoint...");
        ListingDetailsController.listing(listingId, categoryId, listener);
    }
}