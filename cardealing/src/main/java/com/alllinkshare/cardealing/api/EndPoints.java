package com.alllinkshare.cardealing.api;

public class EndPoints {
    public static final String API_HOST = "https://alllinkshare.com/";
    public static final String API_VERSION = "api/v1/";
    public static final String EP_API = API_HOST+API_VERSION;

    public static final String CARS_INDEX = EP_API+"car-dealer";
    public static final String CARS_FEATURED = EP_API+"car-dealer/featured";
    public static final String CARS_SHOW = EP_API+"car-dealer/{car}";
}