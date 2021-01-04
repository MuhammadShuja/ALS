package com.alllinkshare.catalogshopping.api.retrofit;

import android.util.Log;

import com.alllinkshare.catalogshopping.api.EndPoints;
import com.alllinkshare.catalogshopping.api.retrofit.responses.CatalogProductsResponse;
import com.alllinkshare.catalogshopping.models.Product;
import com.alllinkshare.catalogshopping.models.ProductDealer;
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
        @GET(EndPoints.SHOPPING_PRODUCTS)
        Call<CatalogProductsResponse> getProducts(@Query("listing_id") int listingId,
                                                  @Query("shopping_category_id") int shoppingCategoryId,
                                                  @Query("main_category_id") int mainCategoryId,
                                                  @Query("page") int pageNumber);

        @GET(EndPoints.SHOPPING_PRODUCT)
        Call<Product> getProduct(@Path("product") int productId);

        @GET(EndPoints.SHOPPING_PRODUCT_DEALERS)
        Call<List<ProductDealer>> getProductDealers(@Path("product") int productId);
    }
}