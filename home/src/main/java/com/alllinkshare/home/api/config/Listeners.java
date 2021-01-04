package com.alllinkshare.home.api.config;

public class Listeners {

    public interface LogoutListener{
        void onSuccess(boolean success);
        void onFailure(String error);
    }
}