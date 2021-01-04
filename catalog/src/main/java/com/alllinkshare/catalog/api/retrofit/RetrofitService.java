package com.alllinkshare.catalog.api.retrofit;

import android.util.Log;

import com.alllinkshare.catalog.api.EndPoints;
import com.alllinkshare.catalog.api.retrofit.responses.CatalogCategoriesResponse;
import com.alllinkshare.catalog.api.retrofit.responses.CatalogListingCategoriesResponse;
import com.alllinkshare.catalog.api.retrofit.responses.CatalogListingsResponse;
import com.alllinkshare.catalog.models.Listing;
import com.alllinkshare.core.retrofit.RetrofitServiceGenerator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitService {
    private static final String TAG = "Retrofit/Catalog";

    private static Client client = null;

    public static synchronized Client getClient(){
        if(client == null){
            client = RetrofitServiceGenerator.generate(Client.class, EndPoints.API_HOST);
            Log.d(TAG, "Service generated...");
        }
        return client;
    }

    public interface Client {
        @GET(EndPoints.CATALOG_CATEGORIES)
        Call<CatalogCategoriesResponse> getCategories(@Query("parent_id") int parentId,
                                                      @Query("load") String load);


        @GET(EndPoints.CATALOG_LISTINGS)
        Call<CatalogListingsResponse> getListings(@Query("category_id") int categoryId,
                                                  @Query("page") int pageNumber);

        @GET(EndPoints.CATALOG_LISTING)
        Call<Listing> getListing(@Path("listing") int listingId,
                                 @Query("category_id") int categoryId);

        @GET(EndPoints.CATALOG_LISTING_CATEGORIES)
        Call<CatalogListingCategoriesResponse> getListingCategories(@Path("listing") int listingId,
                                                                    @Query("type") String type,
                                                                    @Query("page") int pageNumber);
    }
}