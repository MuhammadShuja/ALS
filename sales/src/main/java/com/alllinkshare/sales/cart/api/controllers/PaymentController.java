package com.alllinkshare.sales.cart.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.sales.cart.api.config.Listeners;
import com.alllinkshare.sales.cart.api.retrofit.RetrofitService;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentResponse;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentTokenResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentController {
    private static final String TAG = "API/Payment";

    public static void token(final Listeners.TokenListener listener){
        Log.d(TAG, "Get token initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().token(
                acceptHeader, authorizationHeader
        ).enqueue(new Callback<PaymentTokenResponse>() {
            @Override
            public void onResponse(Call<PaymentTokenResponse> call, Response<PaymentTokenResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                }
                else{
                    listener.onFailure(response.message());
                }
                Log.d(TAG, "Get token completed...");
            }

            @Override
            public void onFailure(Call<PaymentTokenResponse> call, Throwable t) {
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

    public static void pay(String amount, String nonce, final Listeners.PayListener listener){
        Log.d(TAG, "Payment initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().pay(
                acceptHeader, authorizationHeader,
                amount, nonce
        ).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful()) {
                    if(Objects.requireNonNull(response.body()).isSuccess())
                        listener.onSuccess(response.body().getMessage());
                    else
                        listener.onFailure(response.body().getMessage());
                }
                else{
                    listener.onFailure(response.message());
                }
                Log.d(TAG, "Payment completed...");
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
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