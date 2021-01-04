package com.alllinkshare.cardealing.api.retrofit;

import android.util.Log;

import com.alllinkshare.cardealing.api.EndPoints;
import com.alllinkshare.cardealing.api.retrofit.responses.CarDealingCarsResponse;
import com.alllinkshare.cardealing.models.Car;
import com.alllinkshare.core.retrofit.RetrofitServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitService {
    private static final String TAG = "Retrofit/Shopping";

    private static Client client = null;

    public static synchronized Client getClient(){
        if(client == null){
            client = RetrofitServiceGenerator.generate(Client.class, EndPoints.API_HOST);
            Log.d(TAG, "Service generated...");
        }
        return client;
    }

    public interface Client {
        @GET(EndPoints.CARS_FEATURED)
        Call<List<Car>> getFeaturedCars();

        @GET(EndPoints.CARS_INDEX)
        Call<CarDealingCarsResponse> getCars(@Query("page") int pageNumber);

        @GET(EndPoints.CARS_SHOW)
        Call<Car> getCar(@Path("car") int carId);
    }
}