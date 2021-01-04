package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.LogoutResponse;
import com.alllinkshare.auth.events.LogoutEvent;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutController {
    private static final String TAG = "API/Logout";

    public static void logout(final Listeners.AuthListener listener){
        Log.d(TAG, "Logout initiated...");

        String authorizationHeader = "Bearer "+SPM.getInstance().get(SPM.ACCESS_TOKEN, null);
        String acceptHeader = "application/json";

        RetrofitService.getClient().logout(acceptHeader, authorizationHeader).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().toString());
                    if (response.body().getSuccess()) {
                        SPM.getInstance().removeCredentials();

                        new LogoutEvent().fire();

                        listener.onSuccess(response.body().getMessage());

                        Log.d(TAG, "Logout completed...");
                    }
                    else {
                        listener.onFailure("Error, could not log out");
                    }
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
                else{
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}