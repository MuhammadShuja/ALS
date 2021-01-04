package com.alllinkshare.cardealing.api.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alllinkshare.cardealing.api.config.Listeners;
import com.alllinkshare.cardealing.api.retrofit.RetrofitService;
import com.alllinkshare.cardealing.api.retrofit.responses.CarDealingCarsResponse;
import com.alllinkshare.cardealing.models.Car;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsController {
    private static final String TAG = "API/CarDealing";

    public static void featured(final Listeners.FeaturedCarsListener listener){
        Log.d(TAG, "Get featured cars initiated...");

        RetrofitService.getClient().getFeaturedCars().enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(response.body());
                }

                Log.d(TAG, "Get featured cars completed...");
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }

    public static void cars(int pageNumber, final Listeners.CarsListener listener){
        Log.d(TAG, "Get cars initiated...");

        RetrofitService.getClient().getCars(pageNumber).enqueue(new Callback<CarDealingCarsResponse>() {
            @Override
            public void onResponse(Call<CarDealingCarsResponse> call, Response<CarDealingCarsResponse> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(
                            response.body().getData(),
                            response.body().getCurrentPage(),
                            response.body().getLastPage(),
                            response.body().getTotal()
                    );
                }

                Log.d(TAG, "Get cars completed...");
            }

            @Override
            public void onFailure(Call<CarDealingCarsResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }

    public static void car(int carId, final Listeners.CarListener listener){
        Log.d(TAG, "Get car initiated...");

        RetrofitService.getClient().getCar(carId).enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if(response.isSuccessful()){
                    listener.onSuccess(response.body());
                }

                Log.d(TAG, "Get car completed...");
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getMessage());
            }
        });
    }
}