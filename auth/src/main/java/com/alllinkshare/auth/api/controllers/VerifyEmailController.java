package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.VerifyEmailResponse;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailController {
    private static final String TAG = "API/VerifyEmail";

    public static void verify(final String email,  final Listeners.AuthListener listener) {
        Log.d(TAG, "Email verification initiated...");

        RetrofitService.getClient().verifyEmail(email).enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.isSuccessful()){
                    boolean success = response.body().getSuccess();
                    if(success) {
                        listener.onSuccess("Email has been verified successfully");
                    }
                } else {
                    listener.onFailure("Error, could not verify email");
                }

                Log.d(TAG, "Email verification completed...");
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {
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