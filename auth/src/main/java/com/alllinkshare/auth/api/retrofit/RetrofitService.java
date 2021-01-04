package com.alllinkshare.auth.api.retrofit;

import android.util.Log;

import com.alllinkshare.auth.api.EndPoints;
import com.alllinkshare.auth.api.retrofit.responses.LogoutResponse;
import com.alllinkshare.auth.api.retrofit.responses.ResetPasswordResponse;
import com.alllinkshare.auth.api.retrofit.responses.UserLoginResponse;
import com.alllinkshare.auth.api.retrofit.responses.UserResponse;
import com.alllinkshare.auth.api.retrofit.responses.VerifyEmailResponse;
import com.alllinkshare.auth.api.retrofit.responses.VerifyTokenResponse;
import com.alllinkshare.auth.api.retrofit.responses.VerifyUserCodeResponse;
import com.alllinkshare.auth.api.retrofit.responses.UserResendCodeResponse;
import com.alllinkshare.core.retrofit.RetrofitServiceGenerator;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class RetrofitService {
    private static final String TAG = "Retrofit/Auth";

    private static Client client = null;

    public static synchronized Client getClient(){
        if(client == null){
            client = RetrofitServiceGenerator.generate(Client.class, EndPoints.API_HOST);
            Log.d(TAG, "Service generated...");
        }
        return client;
    }

    public interface Client {
        @FormUrlEncoded
        @POST(EndPoints.REGISTER)
        Call<UserResponse> register(@Field("user_type") String userType,
                                    @Field("first_name") String firstName,
                                    @Field("last_name") String lastName,
                                    @Field("email") String email,
                                    @Field("country_code") String countryCode,
                                    @Field("mobile_number") String mobileNumber,
                                    @Field("password") String password,
                                    @Field("password_confirmation") String passwordConfirmation,
                                    @Field("grant_type") String grantType,
                                    @Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("scope") String scope);

        @FormUrlEncoded
        @POST(EndPoints.LOGIN)
        Call<UserLoginResponse> login(@Field("user_type") String userType,
                                      @Field("username") String userName,
                                      @Field("password") String password,
                                      @Field("grant_type") String grantType,
                                      @Field("client_id") String clientId,
                                      @Field("client_secret") String clientSecret,
                                      @Field("scope") String scope);

        @POST(EndPoints.LOGOUT)
        Call<LogoutResponse> logout(@Header("Accept") String acceptHeader,
                                    @Header("Authorization") String authorizationHeader);

        @FormUrlEncoded
        @POST(EndPoints.VERIFY_EMAIL)
        Call<VerifyEmailResponse> verifyEmail(@Field("email") String email);

        @FormUrlEncoded
        @POST(EndPoints.VERIFY_TOKEN)
        Call<VerifyTokenResponse> verifyToken(@Field("_token") String token);

        @FormUrlEncoded
        @POST(EndPoints.PASSWORD_RESET)
        Call<ResetPasswordResponse> resetPassword(@Field("user_id") int userId,
                                                  @Field("password") String password,
                                                  @Field("password_confirmation") String passConfirmation);

        @FormUrlEncoded
        @POST(EndPoints.VERIFY_CODE)
        Call<VerifyUserCodeResponse> verifyUserCode(@Field("user_code") String userCode);

        @GET(EndPoints.RESEND_CODE)
        Call<UserResendCodeResponse> resendCode(@Query("email") String email);
    }
}