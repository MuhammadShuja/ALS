package com.alllinkshare.catalog.api.controllers;

import android.util.Log;

import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.api.retrofit.RetrofitService;
import com.alllinkshare.catalog.api.retrofit.responses.CatalogListingCategoriesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingCategoriesController {
    private static final String TAG = "API/ListingCategories";

    public static void categories(String type, int listingId, int pageNumber, final Listeners.ListingCategoriesListener listener){
        Log.d(TAG, "Get listing categories initiated...");

        RetrofitService.getClient().getListingCategories(listingId, type, pageNumber).enqueue(new Callback<CatalogListingCategoriesResponse>() {
            @Override
            public void onResponse(Call<CatalogListingCategoriesResponse> call, Response<CatalogListingCategoriesResponse> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(
                            response.body().getData(),
                            response.body().getCurrentPage(),
                            response.body().getLastPage(),
                            response.body().getTotal());
                }

                Log.d(TAG, "Get listing categories completed...");
            }

            @Override
            public void onFailure(Call<CatalogListingCategoriesResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }
}