package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.UserResendCodeResponse;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResendCodeController {
    private static final String TAG = "API/ResendCode";

    public static void resend(final Listeners.AuthListener listener) {
        Log.d(TAG, "Resend code initiated...");

        String userEmail = SPM.getInstance().get(SPM.USER_EMAIL, null);

        RetrofitService.getClient().resendCode(userEmail).enqueue(new Callback<UserResendCodeResponse>() {
            @Override
            public void onResponse(Call<UserResendCodeResponse> call, Response<UserResendCodeResponse> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body().getSuccess();
                    Integer userID = response.body().getUserId();

                    SPM.getInstance().save(SPM.USER_ID, userID);

                    if (success) {
                        listener.onSuccess("Code has been resent successfully");
                    } else {
                        listener.onFailure("Error, could not resend code");
                    }
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }
                Log.d(TAG, "Resend code completed...");
            }

            @Override
            public void onFailure(Call<UserResendCodeResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String error = "No Internet Connection!";
                    listener.onFailure(error);
                }
                else{
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}
