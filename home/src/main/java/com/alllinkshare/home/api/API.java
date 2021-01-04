package com.alllinkshare.home.api;

import com.alllinkshare.home.api.config.Listeners;

public class API {
    private static final String TAG = "API/Home";

    public static boolean isAuthenticated(){
        return com.alllinkshare.auth.api.API.isAuthenticated();
    }

    public static void logout(final Listeners.LogoutListener listener){
        com.alllinkshare.auth.api.API.logout(new com.alllinkshare.auth.api.config.Listeners.AuthListener() {
            @Override
            public void onSuccess(String message) {
                listener.onSuccess(true);
            }

            @Override
            public void onFailure(String error) {
                listener.onFailure(error);
            }
        });
    }
}