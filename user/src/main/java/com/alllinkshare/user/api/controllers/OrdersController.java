package com.alllinkshare.user.api.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.retrofit.RetrofitService;
import com.alllinkshare.user.api.retrofit.responses.OrdersResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersController {
    private static final String TAG = "API/Orders";

    public static void orders(int pageNumber, String filter, final Listeners.OrdersListener listener){
        Log.d(TAG, "Get orders initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().getOrders(
                acceptHeader, authorizationHeader,
                filter, pageNumber
        ).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(
                            Objects.requireNonNull(response.body()).getData(),
                            response.body().getCurrentPage(),
                            response.body().getLastPage(),
                            response.body().getTotal());
                }

                Log.d(TAG, "Get products completed...");
            }

            @Override
            public void onFailure(@NonNull Call<OrdersResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }
}