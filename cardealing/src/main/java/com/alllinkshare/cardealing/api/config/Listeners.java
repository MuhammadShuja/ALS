package com.alllinkshare.cardealing.api.config;

import com.alllinkshare.cardealing.models.Car;

import java.util.List;

public class Listeners {

    public interface CarsListener{
        void onSuccess(List<Car> cars, int currentPageNumber, int lastPageNumber, int totalCars);
        void onFailure(String error);
    }

    public interface FeaturedCarsListener{
        void onSuccess(List<Car> cars);
        void onFailure(String error);
    }

    public interface CarListener{
        void onSuccess(Car product);
        void onFailure(String error);
    }
}