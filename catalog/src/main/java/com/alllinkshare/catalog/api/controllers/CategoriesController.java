package com.alllinkshare.catalog.api.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.api.retrofit.RetrofitService;
import com.alllinkshare.catalog.api.retrofit.responses.CatalogCategoriesResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesController {
    private static final String TAG = "API/Categories";

    public static void categories(int parentId, boolean loadAll, final Listeners.CategoriesListener listener){
        Log.d(TAG, "Get categories initiated...");

        String load = (loadAll) ? "all" : "";

        RetrofitService.getClient().getCategories(parentId, load).enqueue(new Callback<CatalogCategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CatalogCategoriesResponse> call, @NonNull Response<CatalogCategoriesResponse> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(
                            Objects.requireNonNull(response.body()).getParentId(),
                            response.body().getParentName(),
                            response.body().getCoverImage(),
                            response.body().getChildrenCount(),
                            response.body().getLayoutStyle(),
                            response.body().getCategories()
                    );
                }

                Log.d(TAG, "Get categories completed...");
            }

            @Override
            public void onFailure(@NonNull Call<CatalogCategoriesResponse> call, @NonNull Throwable t) {

            }
        });
    }
}