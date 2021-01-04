package com.alllinkshare.sales.cart.api.retrofit;

import android.util.Log;

import com.alllinkshare.core.retrofit.RetrofitServiceGenerator;
import com.alllinkshare.sales.cart.api.EndPoints;
import com.alllinkshare.sales.cart.api.retrofit.responses.CheckoutResponse;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentResponse;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentTokenResponse;
import com.alllinkshare.sales.cart.models.Coupon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
        @GET(EndPoints.SALES_COUPONS)
        Call<List<Coupon>> coupons(@Header("Accept") String acceptHeader,
                                   @Header("Authorization") String authorizationHeader,
                                   @Query("listing_ids") String listingIds);

        @POST(EndPoints.SALES_CHECKOUT)
        Call<CheckoutResponse> checkout(@Header("Accept") String acceptHeader,
                                        @Header("Authorization") String authorizationHeader,
                                        @Query("order_json") String orderJson);

        @POST(EndPoints.SALES_TOKEN)
        Call<PaymentTokenResponse> token(@Header("Accept") String acceptHeader,
                                         @Header("Authorization") String authorizationHeader);

        @POST(EndPoints.SALES_PAY)
        Call<PaymentResponse> pay(@Header("Accept") String acceptHeader,
                                  @Header("Authorization") String authorizationHeader,
                                  @Query("amount") String amount,
                                  @Query("nonce") String nonce);
    }
}