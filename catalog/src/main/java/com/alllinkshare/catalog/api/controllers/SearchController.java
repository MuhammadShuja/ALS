package com.alllinkshare.catalog.api.controllers;

import android.util.Log;

import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.api.retrofit.RetrofitService;
import com.alllinkshare.catalog.api.retrofit.responses.CatalogListingsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchController {
    private static final String TAG = "API/Search";

    public static void search(int pageNumber, String query, final Listeners.ListingsListener listener){
        Log.d(TAG, "Search catalog initiated...");

        RetrofitService.getClient().searchCatalog(query, pageNumber).enqueue(new Callback<CatalogListingsResponse>() {
            @Override
            public void onResponse(Call<CatalogListingsResponse> call, Response<CatalogListingsResponse> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(
                            response.body().getData(),
                            response.body().getCurrentPage(),
                            response.body().getLastPage(),
                            response.body().getTotal());
                }

                Log.d(TAG, "Search catalog completed...");
            }

            @Override
            public void onFailure(Call<CatalogListingsResponse> call, Throwable t) {
                t.getCause().printStackTrace();
                listener.onFailure(t.getCause().getMessage());
            }
        });
    }
}