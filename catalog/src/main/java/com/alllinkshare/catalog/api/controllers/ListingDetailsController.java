package com.alllinkshare.catalog.api.controllers;

import android.util.Log;

import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.api.retrofit.RetrofitService;
import com.alllinkshare.catalog.models.Listing;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingDetailsController {
    private static final String TAG = "API/ListingDetails";

    public static void listing(int listingId, int categoryId, final Listeners.ListingListener listener){
        Log.d(TAG, "Get listing initiated...");

        RetrofitService.getClient().getListing(listingId, categoryId).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(Call<Listing> call, Response<Listing> response) {
                if(response.isSuccessful())
                    listener.onSuccess(response.body());

                Log.d(TAG, "Get listing completed...");
            }

            @Override
            public void onFailure(Call<Listing> call, Throwable t) {

            }
        });
    }
}