package com.alllinkshare.user.api.retrofit;

import android.util.Log;

import com.alllinkshare.user.api.EndPoints;
import com.alllinkshare.core.retrofit.RetrofitServiceGenerator;
import com.alllinkshare.user.api.retrofit.responses.CouponEdit;
import com.alllinkshare.user.api.retrofit.responses.CouponUpdate;
import com.alllinkshare.user.api.retrofit.responses.CouponsResponse;
import com.alllinkshare.user.api.retrofit.responses.FavouriteRemove;
import com.alllinkshare.user.api.retrofit.responses.FavouriteResponse;
import com.alllinkshare.user.api.retrofit.responses.OrdersResponse;
import com.alllinkshare.user.api.retrofit.responses.UpdateEmailReq;
import com.alllinkshare.user.api.retrofit.responses.UpdateMobNumResponse;
import com.alllinkshare.user.api.retrofit.responses.UpdateMobileNumber;
import com.alllinkshare.user.api.retrofit.responses.UpdatePassResponse;
import com.alllinkshare.user.api.retrofit.responses.UpdateProfile;
import com.alllinkshare.user.models.City;
import com.alllinkshare.user.models.Country;
import com.alllinkshare.user.models.State;
import com.alllinkshare.user.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitService {
    private static final String TAG = "Retrofit/User";

    private static Client client = null;

    public static synchronized Client getClient(){
        if(client == null){
            client = RetrofitServiceGenerator.generate(Client.class, EndPoints.API_HOST);
            Log.d(TAG, "Service generated...");
        }
        return client;
    }

    public interface Client {
        @GET(EndPoints.LOCATION_COUNTRIES)
        Call<List<Country>> getCountries();

        @GET(EndPoints.LOCATION_STATES)
        Call<List<State>> getStates(@Query("country_id") int countryId);

        @GET(EndPoints.LOCATION_CITIES)
        Call<List<City>> getCities(@Query("state_id") int stateId);

        @GET(EndPoints.USER_PROFILE_GET)
        Call<User> getProfileData(@Header("Accept") String acceptHeader,
                                  @Header("Authorization") String authorizationHeader);

        @POST(EndPoints.USER_PROFILE_UPDATE)
        Call<User> updateProfileData(@Header("Accept") String acceptHeader,
                                     @Header("Authorization") String authorizationHeader,
                                     @Query("first_name") String firstName,
                                     @Query("last_name") String lastName,
                                     @Query("address") String address,
                                     @Query("street_address") String streetAddress,
                                     @Query("country_id") int countryId,
                                     @Query("state_id") int stateId,
                                     @Query("city_id") int cityId,
                                     @Query("pincode") String pinCode);

        @Multipart
        @POST(EndPoints.USER_PROFILE_UPDATE_PICTURE)
        Call<UpdateProfile> updateProfileImage(@Header("Accept") String acceptHeader,
                                               @Header("Authorization") String authorizationHeader,
                                               @Part MultipartBody.Part profilePicture);

        @GET(EndPoints.USER_FAVOURITES_INDEX)
        Call<FavouriteResponse> getFavouritesData(@Header("Accept") String acceptHeader,
                                                  @Header("Authorization") String authorizationHeader,
                                                  @Query("page") int pageNumber);

        @POST(EndPoints.USER_FAVOURITES_REMOVE)
        Call<FavouriteRemove> removeFavouritesData(@Header("Accept") String acceptHeader,
                                                   @Header("Authorization") String authorizationHeader,
                                                   @Query("item_id") int itemId);

        @GET(EndPoints.USER_ORDERS_INDEX)
        Call<OrdersResponse> getOrders(@Header("Accept") String acceptHeader,
                                       @Header("Authorization") String authorizationHeader,
                                       @Query("filter") String filter,
                                       @Query("page") int pageNumber);

        @GET(EndPoints.USER_COUPONS_INDEX)
        Call<CouponsResponse> getCoupons(@Header("Accept") String acceptHeader,
                                         @Header("Authorization") String authorizationHeader);

        @GET(EndPoints.USER_COUPONS_UPDATE)
        Call<CouponEdit> editCoupon(@Header("Accept") String acceptHeader,
                                             @Header("Authorization") String authorizationHeader,
                                             @Path("coupon") Integer couponId);

        @POST(EndPoints.USER_COUPONS_UPDATE)
        Call<CouponUpdate> updateCoupon(@Header("Accept") String acceptHeader,
                                        @Header("Authorization") String authorizationHeader,
                                        @Query("coupon_id") int couponId,
                                        @Query("coupon_name") String couponName,
                                        @Query("coupon_code") String couponCode,
                                        @Query("discount_type") String discountType,
                                        @Query("percent_off") String percentOff,
                                        @Query("discount") String discount);

        @POST(EndPoints.USER_CREDENTIALS_UPDATE_MOBILE_NUMBER_REQUEST)
        Call<UpdateMobNumResponse> updateMobileNumberRequest(@Header("Accept") String acceptHeader,
                                                             @Header("Authorization") String authorizationHeader,
                                                             @Query("current_mobile_number") String currentMobileNumber,
                                                             @Query("new_mobile_number") String newMobileNumber);

        @POST(EndPoints.USER_CREDENTIALS_UPDATE_MOBILE_NUMBER)
        Call<UpdateMobileNumber> updateMobileNumber(@Header("Accept") String acceptHeader,
                                                    @Header("Authorization") String authorizationHeader,
                                                    @Query("current_mobile_number") String currentMobileNumber,
                                                    @Query("new_mobile_number") String newMobileNumber,
                                                    @Query("verification_code") String verificationCode);

        @POST(EndPoints.USER_CREDENTIALS_UPDATE_EMAIL_REQUEST)
        Call<UpdateEmailReq> updateEmailRequest(@Header("Accept") String acceptHeader,
                                                @Header("Authorization") String authorizationHeader,
                                                @Query("current_email") String currentEmail,
                                                @Query("new_email") String newEmail);

        @POST(EndPoints.USER_CREDENTIALS_UPDATE_EMAIL)
        Call<UpdateEmailReq> updateEmail(@Header("Accept") String acceptHeader,
                                         @Header("Authorization") String authorizationHeader,
                                         @Query("current_email") String currentEmail,
                                         @Query("new_email") String newEmail,
                                         @Query("verification_code") String verificationCode);

        @POST(EndPoints.USER_CREDENTIALS_UPDATE_PASSWORD)
        Call<UpdatePassResponse> updatePassword(@Header("Accept") String acceptHeader,
                                                @Header("Authorization") String authorizationHeader,
                                                @Query("current_password") String currentPassword,
                                                @Query("new_password") String newPassword);

        @POST(EndPoints.USER_PROFILE_UPDATE_FCM_TOKEN)
        Call<String> updateFcmToken(@Header("Accept") String acceptHeader,
                                                @Header("Authorization") String authorizationHeader,
                                                @Query("fcm_token") String fcmToken);
    }
}