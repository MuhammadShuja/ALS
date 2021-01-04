package com.alllinkshare.restaurant.api;

import android.util.Log;

import com.alllinkshare.restaurant.api.config.Listeners;
import com.alllinkshare.restaurant.api.controllers.FoodMenuController;

public class API {
    private static final String TAG = "API/Restaurant";

    public static void menu(int listingId, int pageNumber, Listeners.MenuListener listener){
        Log.d(TAG, "Calling food menu endpoint...");
        FoodMenuController.menu(listingId, pageNumber, listener);
    }

    public static void item(int itemId, Listeners.ItemListener listener){
        Log.d(TAG, "Calling food item endpoint...");
        FoodMenuController.item(itemId, listener);
    }
}