package com.alllinkshare.restaurant.api.config;

import com.alllinkshare.restaurant.models.FoodMenuItem;

import java.util.List;

public class Listeners {

    public interface MenuListener{
        void onSuccess(List<FoodMenuItem> menuItems, int currentPageNumber, int lastPageNumber, int totalItems);
        void onFailure(String error);
    }

    public interface ItemListener{
        void onSuccess(FoodMenuItem foodMenuItem);
        void onFailure(String error);
    }
}