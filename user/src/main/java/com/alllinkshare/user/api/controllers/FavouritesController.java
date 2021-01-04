package com.alllinkshare.user.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.retrofit.RetrofitService;
import com.alllinkshare.user.api.retrofit.responses.FavouriteRemove;
import com.alllinkshare.user.api.retrofit.responses.FavouriteResponse;
import com.alllinkshare.user.models.FavouriteItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesController {
    private static final String TAG = "API/Favourites";

    public static void favourites(int pageNumber, final Listeners.FavouritesListener listener){
        Log.d(TAG, "Get favourites initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().getFavouritesData(
                acceptHeader, authorizationHeader,
                pageNumber
        ).enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                List<FavouriteItem> items = new ArrayList<>();
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().toString());
                    if (response.body().getCurrentPage() != null) {
                        try {
                            List<FavouriteItem> favouriteItem = new ArrayList<>();
                            int from = response.body().getFrom();
                            int to = response.body().getTo();
                            int res= to-from;

                            for (int i = 0; i <= res; i++) {
                                int id = response.body().getData().get(i).getId();
                                int productId = response.body().getData().get(i).getProductId();
                                int categoryId = response.body().getData().get(i).getCategoryId();
                                int listingId = response.body().getData().get(i).getListingId();
                                String name = response.body().getData().get(i).getName();
                                String thumbnail = response.body().getData().get(i).getThumbnail();
                                String type = response.body().getData().get(i).getType();

                                items.add(new FavouriteItem(id, productId, categoryId, listingId, name,
                                        thumbnail, type));

                            }
                            int lastPageNumber = response.body().getLastPage();
                            int totalItemCount = response.body().getTotal();
                            int currentPageNumber = response.body().getCurrentPage();

                            listener.onSuccess(items, currentPageNumber, lastPageNumber, totalItemCount);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Get favourites completed...");
            }

            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {
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

    public static void remove(int itemId, final Listeners.RemoveFavouriteListener listener){
        Log.d(TAG, "Remove favourite initiated...");
        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().removeFavouritesData(
                acceptHeader, authorizationHeader,
                itemId
        ).enqueue(new Callback<FavouriteRemove>() {
            @Override
            public void onResponse(Call<FavouriteRemove> call, Response<FavouriteRemove> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        listener.onSuccess("Item has been removed from favourites successfully");
                    } else {
                        listener.onFailure("Error, could not get favourite items");
                    }
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Remove favourite completed...");
            }

            @Override
            public void onFailure(Call<FavouriteRemove> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
                else {
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}