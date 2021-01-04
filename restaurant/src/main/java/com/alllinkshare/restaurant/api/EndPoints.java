package com.alllinkshare.restaurant.api;

public class EndPoints {
    public static final String API_HOST = "https://alllinkshare.com/";
    public static final String API_VERSION = "api/v1/";
    public static final String EP_API = API_HOST+API_VERSION;

    public static final String FOOD_MENU = EP_API+"catalog/restaurant/menu";
    public static final String FOOD_MENU_ITEM = EP_API+"catalog/restaurant/menu/{item}";
}