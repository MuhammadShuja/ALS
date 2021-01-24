package com.alllinkshare.liveTrack.tracking.Api;

import com.alllinkshare.liveTrack.tracking.Model.CurrentLocation;
import com.alllinkshare.liveTrack.tracking.Model.Order;
import com.alllinkshare.liveTrack.tracking.Model.OrderHistoryDetails;
import com.alllinkshare.liveTrack.tracking.Model.OrdersModel;
import com.alllinkshare.liveTrack.tracking.Model.Rides;
import com.alllinkshare.liveTrack.tracking.Model.SecretModel;
import com.alllinkshare.liveTrack.tracking.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @Headers({"Accept: application/json"})
    @POST("/oauth/token")
    Call<Object> createAuth(@Body SecretModel secretAuth);

    @GET("/api/v1/profile")
    Call<User> getProfileData(@Header("Accept") String value, @Header("Authorization") String authorized);

    @GET("/api/v1/rides")
    Call<OrderHistoryDetails> getHistoryData(@Header("Accept") String value, @Header("Authorization") String authorized);

    @POST("/api/v1/rides/start")
    Call<Object> startRides(@Body Order body, @Header("Accept") String value, @Header("Authorization") String authorized);

    @POST("/api/v1/rides/move")
    Call<Object> sendCurrentLocation(@Body CurrentLocation body, @Header("Accept") String value, @Header("Authorization") String authorized);

    @POST("/api/v1/rides/complete")
    Call<Object> completeRide(@Body Rides body, @Header("Accept") String value, @Header("Authorization") String authorized);

    @POST("/api/v1/rides/cancel")
    Call<Object> cancelRide(@Body Rides body, @Header("Accept") String value, @Header("Authorization") String authorized);

    @GET("/api/v1/sales/track-order")
    Call<Object> showOrderTrack(@Query("order_id") String order_id, @Header("Accept") String value, @Header("Authorization") String authorized);

    @GET("/api/v1/sales/track-order/live")
    Call<Object> LiveOrderTrack(@Query("order_id") String order_id, @Header("Accept") String value, @Header("Authorization") String authorized);

    @GET("/api/v1/orders")
    Call<OrdersModel> getOrderModel(@Header("Accept") String value, @Header("Authorization") String authorized);

    @POST("/api/v1/oauth/revoke")
    Call<Object> logout(@Header("Content-Type") String value, @Header("Authorization") String authorized);

}
