package com.alllinkshare.user.repos;

import android.util.Log;

import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.City;

import java.util.ArrayList;
import java.util.List;

public class CityRepository {
    private static final String TAG = "Repo/City";

    private static CityRepository instance = null;

    private static List<City> items = new ArrayList<>();

    public static synchronized CityRepository getInstance(){
        if(instance == null){
            instance = new CityRepository();
        }
        return instance;
    }

    private CityRepository(){
        Log.d(TAG, "New instance created...");
    }

    public void getCities(final int stateId, final CityRepository.DataReadyListener listener){
        API.cities(stateId, new Listeners.CitiesListener() {
            @Override
            public void onSuccess(List<City> cities) {
                items = cities;
                listener.onDataReady(cities);
            }

            @Override
            public void onFailure(String error) {
                listener.onFailure(error);
            }
        });

        Log.d(TAG, "Get Cities completed...");
    }

    public City getCityById(int id){
        for(City city : items){
            if(city.getId() == id){
                return city;
            }
        }
        return null;
    }

    public interface DataReadyListener{
        void onDataReady(List<City> cityList);
        void onFailure(String error);
    }
}