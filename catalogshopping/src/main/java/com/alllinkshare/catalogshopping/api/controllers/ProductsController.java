package com.alllinkshare.catalogshopping.api.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alllinkshare.catalogshopping.api.config.Listeners;
import com.alllinkshare.catalogshopping.api.retrofit.RetrofitService;
import com.alllinkshare.catalogshopping.api.retrofit.responses.CatalogProductsResponse;
import com.alllinkshare.catalogshopping.models.Product;
import com.alllinkshare.catalogshopping.models.ProductDealer;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsController {
    private static final String TAG = "API/Listings";

    public static void products(int listingId, int shoppingCategoryId, int mainCategoryId, int pageNumber, final Listeners.ProductsListener listener){
        Log.d(TAG, "Get products initiated...");

        RetrofitService.getClient().getProducts(listingId, shoppingCategoryId, mainCategoryId, pageNumber).enqueue(new Callback<CatalogProductsResponse>() {
            @Override
            public void onResponse(@NonNull Call<CatalogProductsResponse> call, @NonNull Response<CatalogProductsResponse> response) {
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
            public void onFailure(@NonNull Call<CatalogProductsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }

    public static void product(int productId, final Listeners.ProductListener listener){
        Log.d(TAG, "Get product initiated...");

        RetrofitService.getClient().getProduct(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(response.body());
                }

                Log.d(TAG, "Get product completed...");
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }

    public static void dealers(int productId, final Listeners.DealersListener listener){
        Log.d(TAG, "Get dealers initiated...");

        RetrofitService.getClient().getProductDealers(productId).enqueue(new Callback<List<ProductDealer>>() {
            @Override
            public void onResponse(Call<List<ProductDealer>> call, Response<List<ProductDealer>> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(response.body());
                }

                Log.d(TAG, "Get dealers completed...");
            }

            @Override
            public void onFailure(Call<List<ProductDealer>> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }
}