package com.alllinkshare.sales.cart.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.sales.cart.api.config.Listeners;
import com.alllinkshare.sales.cart.api.retrofit.RetrofitService;
import com.alllinkshare.sales.cart.api.retrofit.responses.CheckoutResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutController {
    private static final String TAG = "API/Checkout";

    public static void checkout(String orderJson, final Listeners.CheckoutListener listener){
        Log.d(TAG, "Checkout initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().checkout(
                acceptHeader, authorizationHeader,
                orderJson
        ).enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().isSuccess())
                        listener.onSuccess(response.body().getMessage());
                    else
                        listener.onFailure(response.body().getMessage());
                }
                else{
                    listener.onFailure(response.message());
                }
                Log.d(TAG, "Checkout completed...");
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
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