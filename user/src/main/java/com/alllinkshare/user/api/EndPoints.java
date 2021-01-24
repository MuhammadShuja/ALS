package com.alllinkshare.user.api;

public class EndPoints {
    public static final String API_HOST = "https://alllinkshare.com";
    public static final String API_VERSION = "/api/v1";
    public static final String EP_API = API_HOST+API_VERSION;

    public static final String LOCATION_COUNTRIES = EP_API+"/location/countries";
    public static final String LOCATION_STATES = EP_API+"/location/states";
    public static final String LOCATION_CITIES = EP_API+"/location/cities";

    public static final String USER_PROFILE_GET = EP_API+"/profile";
    public static final String USER_PROFILE_UPDATE = EP_API+"/profile/update";
    public static final String USER_PROFILE_UPDATE_PICTURE = EP_API+"/profile/update/picture";
    public static final String USER_PROFILE_UPDATE_FCM_TOKEN = EP_API+"/profile/update/fcm-token";

    public static final String USER_CREDENTIALS_UPDATE_MOBILE_NUMBER_REQUEST = EP_API+"/credentials/update/request/mobile";
    public static final String USER_CREDENTIALS_UPDATE_MOBILE_NUMBER = EP_API+"/credentials/update/mobile";
    public static final String USER_CREDENTIALS_UPDATE_EMAIL_REQUEST = EP_API+"/credentials/update/request/email";
    public static final String USER_CREDENTIALS_UPDATE_EMAIL = EP_API+"/credentials/update/email";
    public static final String USER_CREDENTIALS_UPDATE_PASSWORD = EP_API+"/credentials/update/password";

    public static final String USER_FAVOURITES_INDEX = EP_API+"/favourites";
    public static final String USER_FAVOURITES_REMOVE = EP_API+"/favourites/remove";

    public static final String USER_COUPONS_INDEX = EP_API+"/coupons";
    public static final String USER_COUPONS_UPDATE = EP_API+"/coupons/{coupon}";

    public static final String USER_ORDERS_INDEX = EP_API+"/sales/orders";
}