package com.alllinkshare.auth.api;

public class EndPoints {
    public static final String API_HOST = "https://alllinkshare.com";
    public static final String API_VERSION = "/api/v1";
    public static final String EP_API = API_HOST+API_VERSION;

    public static final String REGISTER = "api/v1/register";
    public static final String LOGIN = "oauth/token";
    public static final String LOGOUT = "api/v1/oauth/revoke";
    public static final String VERIFY_EMAIL = "api/v1/forgot-password";
    public static final String VERIFY_CODE = "api/v1/verify";
    public static final String PASSWORD_RESET = "api/v1/reset-password";
    public static final String RESEND_CODE = "api/v1/resend";
    public static final String VERIFY_TOKEN = "api/v1/confirm-token";
}