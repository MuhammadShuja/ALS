package com.alllinkshare.user.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.retrofit.RetrofitService;
import com.alllinkshare.user.models.City;
import com.alllinkshare.user.models.Country;
import com.alllinkshare.user.models.State;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationController {
    private static final String TAG = "API/Location";

    public static void countries(final Listeners.CountriesListener listener){
        Log.d(TAG, "Get countries initiated...");

        RetrofitService.getClient().getCountries().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                Country country = null;
                List<Country> items = new ArrayList<>();
                if (response.isSuccessful()) {
                    List<Country> result = response.body();
                    try {
                        JSONArray jsonarray = new JSONArray(response.body());
                        for (int i = 0; i < result.size(); i++) {
                            Integer id = result.get(i).getId();
                            String name = result.get(i).getName();
                            String name_korean = result.get(i).getNameKorean();
                            items.add(new Country(id, name, name_korean));
                        }
                        listener.onSuccess(items);

                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure(e.getMessage());
                    }
                } else {
                    Log.d(TAG, "Could not load data");
                }

                Log.d(TAG, "Get countries completed...");
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
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

    public static void states(int countryId, final Listeners.StatesListener listener){
        Log.d(TAG, "Get states initiated...");

        RetrofitService.getClient().getStates(countryId).enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {
                List<State> items = new ArrayList<>();
                if (response.isSuccessful()) {
                    List<State> result = response.body();
                    try {
                        for (int i = 0; i < result.size(); i++) {
                            int id = result.get(i).getId();
                            String name = result.get(i).getName();
                            String name_korean = result.get(i).getNameKorean();
                            items.add(new State(id, name, name_korean));
                        }
                        listener.onSuccess(items);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure(e.getMessage());
                    }
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Get states completed...");
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {
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

    public static void cities(int stateId, final Listeners.CitiesListener listener){
        Log.d(TAG, "Get cities initiated...");

        RetrofitService.getClient().getCities(stateId).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                List<City> items = new ArrayList<>();
                if (response.isSuccessful()) {
                    List<City> result = response.body();
                    try {
                        for (int i = 0; i < result.size(); i++) {
                            int id = result.get(i).getId();
                            String name = result.get(i).getName();
                            String name_korean = result.get(i).getNameKorean();

                            items.add(new City(id, name, name_korean));

                        }
                        listener.onSuccess(items);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Get cities completed...");
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
            }
        });
    }
}