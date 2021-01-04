package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.R;
import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.UserLoginResponse;
import com.alllinkshare.auth.events.LoginEvent;
import com.alllinkshare.auth.events.LogoutEvent;
import com.alllinkshare.core.ALS;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {
    private static final String TAG = "API/Login";

    public static void login(final String userType, final String username, final String password, final Listeners.AuthListener listener){
        Log.d(TAG, "Login initiated...");

        RetrofitService.getClient().login(
                userType, username, password,
                ALS.getAppContext().getString(R.string.api_grant_type),
                ALS.getAppContext().getString(R.string.api_client_id),
                ALS.getAppContext().getString(R.string.api_client_secret),
                ALS.getAppContext().getString(R.string.api_scope)
        ).enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    String tokenType = response.body().getTokenType();
                    Integer expireIn = response.body().getExpiresIn();
                    String accessToken = response.body().getAccessToken();
                    String refreshToken = response.body().getRefreshToken();

                    SPM.getInstance().save(SPM.TOKEN_TYPE, tokenType);
                    SPM.getInstance().save(SPM.EXPIRES_IN, expireIn);
                    SPM.getInstance().save(SPM.ACCESS_TOKEN, accessToken);
                    SPM.getInstance().save(SPM.REFRESH_TOKEN, refreshToken);
                    SPM.getInstance().save(SPM.USER_STATUS, 1);

                    new LoginEvent().fire();

                    listener.onSuccess("Login successful");
                } else {
                    SPM.getInstance().save(SPM.USER_STATUS, 0);
                    listener.onFailure("Please check your email and password");
                }
            }
            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String error = "No internet connection!";
                    listener.onFailure(error);
                }
            }
        });
    }
}