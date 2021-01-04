package com.alllinkshare.sales.cart.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.sales.cart.api.config.Listeners;
import com.alllinkshare.sales.cart.api.retrofit.RetrofitService;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentResponse;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentTokenResponse;
import com.alllinkshare.sales.cart.models.Coupon;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsController {
    private static final String TAG = "API/Coupons";

    public static void coupons(String listingIds, final Listeners.CouponsListener listener){
        Log.d(TAG, "Get coupons initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().coupons(
                acceptHeader, authorizationHeader,
                listingIds
        ).enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                }
                else{
                    listener.onFailure(response.message());
                }
                Log.d(TAG, "Get Coupons completed...");
            }

            @Override
            public void onFailure(Call<List<Coupon>> call, Throwable t) {
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