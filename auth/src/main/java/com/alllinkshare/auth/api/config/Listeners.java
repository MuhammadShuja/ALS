package com.alllinkshare.auth.api.config;

public class Listeners {

    public interface AuthListener{
        void onSuccess(String message);
        void onFailure(String error);
    }
}