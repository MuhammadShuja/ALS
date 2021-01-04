package com.alllinkshare.catalog.api.controllers;

import android.util.Log;

import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.api.retrofit.RetrofitService;
import com.alllinkshare.catalog.api.retrofit.responses.CatalogListingsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingsController {
    private static final String TAG = "API/Listings";

    public static void listings(int categoryId, int pageNumber, final Listeners.ListingsListener listener){
        Log.d(TAG, "Get listing initiated...");

        RetrofitService.getClient().getListings(categoryId, pageNumber).enqueue(new Callback<CatalogListingsResponse>() {
            @Override
            public void onResponse(Call<CatalogListingsResponse> call, Response<CatalogListingsResponse> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(
                            response.body().getData(),
                            response.body().getCurrentPage(),
                            response.body().getLastPage(),
                            response.body().getTotal());
                }

                Log.d(TAG, "Get listing completed...");
            }

            @Override
            public void onFailure(Call<CatalogListingsResponse> call, Throwable t) {
                t.getCause().printStackTrace();
                listener.onFailure(t.getCause().getMessage());
            }
        });
    }
}