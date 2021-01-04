package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.ResetPasswordResponse;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordResetController {
    private static final String TAG = "API/Reset";

    public static void reset(final String password, final String passwordConfirmation, final Listeners.AuthListener listener) {
        Log.d(TAG, "Password reset initiated...");

        int userId = SPM.getInstance().get(SPM.USER_ID, 0);

        RetrofitService.getClient().resetPassword(userId, password, passwordConfirmation).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.isSuccessful()) {
                    boolean success = response.body().getSuccess();
                    if (success) {
                        listener.onSuccess("success");
                    }
                } else {
                    listener.onFailure("Please enter a valid password");
                }

                Log.d(TAG, "Password reset completed...");
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String error = "No internet connection!";
                    listener.onFailure(error);
                }
                else{
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}
