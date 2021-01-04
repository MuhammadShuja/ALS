package com.alllinkshare.user.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.retrofit.RetrofitService;
import com.alllinkshare.user.api.retrofit.responses.UpdateMobNumResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FcmController {
    private static final String TAG = "API/FcmToken";

    public static void updateFcmToken(final String token, final Listeners.FcmTokenListener listener) {
        Log.d(TAG, "Update fcm token request initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updateFcmToken(
                acceptHeader, authorizationHeader,
                token
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.body());
                }

                Log.d(TAG, "Update fcm token request completed...");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                } else {
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}