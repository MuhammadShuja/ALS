package com.alllinkshare.cardealing.api;

import android.util.Log;

import com.alllinkshare.cardealing.api.config.Listeners;
import com.alllinkshare.cardealing.api.controllers.CarsController;

public class API {
    private static final String TAG = "API/CarDealing";

    public static boolean isAuthenticated(){
        return com.alllinkshare.auth.api.API.isAuthenticated();
    }

    public static void featured(Listeners.FeaturedCarsListener listener){
        Log.d(TAG, "Calling featured cars endpoint...");
        CarsController.featured(listener);
    }

    public static void cars(int pageNumber, Listeners.CarsListener listener){
        Log.d(TAG, "Calling cars endpoint...");
        CarsController.cars(pageNumber, listener);
    }

    public static void car(int carId, Listeners.CarListener listener){
        Log.d(TAG, "Calling car endpoint...");
        CarsController.car(carId, listener);
    }
}