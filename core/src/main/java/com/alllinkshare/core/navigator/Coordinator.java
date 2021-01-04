package com.alllinkshare.core.navigator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class Coordinator {
    private static CoreNavigator coreNavigator;
    private static CatalogNavigator catalogNavigator;
    private static ShoppingNavigator shoppingNavigator;
    private static RestaurantNavigator restaurantNavigator;
    private static SalesNavigator salesNavigator;

    public static CoreNavigator getCoreNavigator() {
        return coreNavigator;
    }

    public static void setCoreNavigator(CoreNavigator coreNavigator) {
        Coordinator.coreNavigator = coreNavigator;
    }

    public static CatalogNavigator getCatalogNavigator() {
        return catalogNavigator;
    }

    public static void setCatalogNavigator(CatalogNavigator catalogNavigator) {
        Coordinator.catalogNavigator = catalogNavigator;
    }

    public static ShoppingNavigator getShoppingNavigator() {
        return shoppingNavigator;
    }

    public static void setShoppingNavigator(ShoppingNavigator shoppingNavigator) {
        Coordinator.shoppingNavigator = shoppingNavigator;
    }

    public static RestaurantNavigator getRestaurantNavigator() {
        return restaurantNavigator;
    }

    public static void setRestaurantNavigator(RestaurantNavigator restaurantNavigator) {
        Coordinator.restaurantNavigator = restaurantNavigator;
    }

    public static SalesNavigator getSalesNavigator() {
        return salesNavigator;
    }

    public static void setSalesNavigator(SalesNavigator salesNavigator) {
        Coordinator.salesNavigator = salesNavigator;
    }
}