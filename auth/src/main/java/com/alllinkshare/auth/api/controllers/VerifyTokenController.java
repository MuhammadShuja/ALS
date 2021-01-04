package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.VerifyTokenResponse;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyTokenController {
    private static final String TAG = "API/VerifyToken";

    public static void verify(final String token, final Listeners.AuthListener listener) {
        Log.d(TAG, "Token verification initiated...");

        RetrofitService.getClient().verifyToken(token).enqueue(new Callback<VerifyTokenResponse>() {
            @Override
            public void onResponse(Call<VerifyTokenResponse> call, Response<VerifyTokenResponse> response) {
                if (response.isSuccessful()) {
                    SPM.getInstance().save(SPM.USER_ID, response.body().getUserId());

                    listener.onSuccess("success");
                    Log.d(TAG, "Token verification completed...");
                } else {
                    listener.onSuccess("Error, could not verify token");
                }
            }

            @Override
            public void onFailure(Call<VerifyTokenResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String error = "No internet connection!";
                    listener.onFailure(error);
                }
                else{
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}