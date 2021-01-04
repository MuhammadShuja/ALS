package com.alllinkshare.user.repos;

import android.util.Log;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.Country;
import com.alllinkshare.user.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountryRepository {
    private static final String TAG = "Repo/Country";

    private static CountryRepository instance = null;

    private static List<Country> items = new ArrayList<>();

    public static synchronized CountryRepository getInstance(){
        if(instance == null){
            instance = new CountryRepository();
        }
        return instance;
    }

    private CountryRepository(){
        Log.d(TAG, "New instance created...");
    }

    public void getCountries(final DataReadyListener listener){
        if(items.size() > 0){
            listener.onDataReady(items);
        }
        else{
            API.countries(new Listeners.CountriesListener() {
                @Override
                public void onSuccess(List<Country> countries) {
                    items = countries;
                    listener.onDataReady(countries);
                }

                @Override
                public void onFailure(String error) {
                    listener.onFailure(error);
                }
            });
        }

        Log.d(TAG, "Get countries completed...");
    }

    public Country getCountryById(int id){
        for(Country country : items){
            if(country.getId() == id){
                return country;
            }
        }
        return null;
    }

    public interface DataReadyListener{
        void onDataReady(List<Country> countryList);
        void onFailure(String error);
    }
}