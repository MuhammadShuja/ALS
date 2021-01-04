package com.alllinkshare.catalogshopping.api;

import android.util.Log;

import com.alllinkshare.catalogshopping.api.config.Listeners;
import com.alllinkshare.catalogshopping.api.controllers.ProductsController;

public class API {
    private static final String TAG = "API/Shopping";

    public static void products(int listingId, int shoppingCategoryId,
                                int mainCategoryId, int pageNumber, Listeners.ProductsListener listener){
        Log.d(TAG, "Calling products endpoint...");
        ProductsController.products(listingId, shoppingCategoryId, mainCategoryId, pageNumber, listener);
    }

    public static void product(int productId, Listeners.ProductListener listener){
        Log.d(TAG, "Calling product endpoint...");
        ProductsController.product(productId, listener);
    }

    public static void dealers(int productId, Listeners.DealersListener listener){
        Log.d(TAG, "Calling dealers endpoint...");
        ProductsController.dealers(productId, listener);
    }
}