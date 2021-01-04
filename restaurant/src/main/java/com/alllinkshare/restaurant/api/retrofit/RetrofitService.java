package com.alllinkshare.restaurant.api.retrofit;

import android.util.Log;

import com.alllinkshare.core.retrofit.RetrofitServiceGenerator;
import com.alllinkshare.restaurant.api.EndPoints;
import com.alllinkshare.restaurant.api.retrofit.responses.CatalogFoodMenuResponse;
import com.alllinkshare.restaurant.models.FoodMenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitService {
    private static final String TAG = "Retrofit/Restaurant";

    private static Client client = null;

    public static synchronized Client getClient(){
        if(client == null){
            client = RetrofitServiceGenerator.generate(Client.class, EndPoints.API_HOST);
            Log.d(TAG, "Service generated...");
        }
        return client;
    }

    public interface Client {
        @GET(EndPoints.FOOD_MENU)
        Call<CatalogFoodMenuResponse> getMenuItems(@Query("listing_id") int listingId,
                                                  @Query("page") int pageNumber);

        @GET(EndPoints.FOOD_MENU_ITEM)
        Call<FoodMenuItem> getMenuItem(@Path("item") int menuItemId);
    }
}