package com.alllinkshare.catalog.api;

public class EndPoints {
    public static final String API_HOST = "https://alllinkshare.com/";
    public static final String API_VERSION = "api/v1/";
    public static final String EP_API = API_HOST+API_VERSION;

    public static final String CATALOG_CATEGORIES = EP_API+"catalog/categories/";
    public static final String CATALOG_LISTINGS = EP_API+"catalog/listings/";
    public static final String CATALOG_LISTING = EP_API+"catalog/listings/{listing}";
    public static final String CATALOG_LISTING_CATEGORIES = EP_API+"catalog/listing-categories/{listing}";
}