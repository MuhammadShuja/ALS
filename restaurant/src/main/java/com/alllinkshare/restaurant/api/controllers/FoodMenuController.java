package com.alllinkshare.restaurant.api.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alllinkshare.restaurant.api.config.Listeners;
import com.alllinkshare.restaurant.api.retrofit.RetrofitService;
import com.alllinkshare.restaurant.api.retrofit.responses.CatalogFoodMenuResponse;
import com.alllinkshare.restaurant.models.FoodMenuItem;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodMenuController {
    private static final String TAG = "API/Restaurant";

    public static void menu(int listingId, int pageNumber, final Listeners.MenuListener listener){
        Log.d(TAG, "Get menu items initiated...");

        RetrofitService.getClient().getMenuItems(listingId, pageNumber).enqueue(new Callback<CatalogFoodMenuResponse>() {
            @Override
            public void onResponse(Call<CatalogFoodMenuResponse> call, Response<CatalogFoodMenuResponse> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(
                            Objects.requireNonNull(response.body()).getData(),
                            response.body().getCurrentPage(),
                            response.body().getLastPage(),
                            response.body().getTotal());
                }

                Log.d(TAG, "Get menu items completed...");
            }

            @Override
            public void onFailure(Call<CatalogFoodMenuResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }

    public static void item(int itemId, final Listeners.ItemListener listener){
        Log.d(TAG, "Get item initiated...");

        RetrofitService.getClient().getMenuItem(itemId).enqueue(new Callback<FoodMenuItem>() {
            @Override
            public void onResponse(Call<FoodMenuItem> call, Response<FoodMenuItem> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(response.body());
                }

                Log.d(TAG, "Get item completed...");
            }

            @Override
            public void onFailure(Call<FoodMenuItem> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }
}