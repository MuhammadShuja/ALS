package com.alllinkshare.sales.cart.api;

public class EndPoints {
    public static final String API_HOST = "https://alllinkshare.com/";
    public static final String API_VERSION = "api/v1/";
    public static final String EP_API = API_HOST+API_VERSION;

    public static final String SALES_COUPONS = EP_API+"sales/coupons";
    public static final String SALES_CHECKOUT = EP_API+"sales/checkout";
    public static final String SALES_TOKEN = EP_API+"sales/payment/token";
    public static final String SALES_PAY = EP_API+"sales/payment/pay";
}