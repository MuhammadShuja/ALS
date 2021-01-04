package com.alllinkshare.catalogshopping.api;

public class EndPoints {
    public static final String API_HOST = "https://alllinkshare.com/";
    public static final String API_VERSION = "api/v1/";
    public static final String EP_API = API_HOST+API_VERSION;

    public static final String SHOPPING_PRODUCTS = EP_API+"catalog/shopping/products";
    public static final String SHOPPING_PRODUCT = EP_API+"catalog/shopping/products/{product}";
    public static final String SHOPPING_PRODUCT_DEALERS = EP_API+"catalog/shopping/products/{product}/dealers";
}