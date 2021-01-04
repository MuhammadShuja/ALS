package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.VerifyUserCodeResponse;
import com.alllinkshare.auth.events.LoginEvent;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCodeController {
    private static final String TAG = "API/VerifyCode";

    public static void verify(final String code,  final Listeners.AuthListener listener) {
        Log.d(TAG, "Code verification initiated...");

        RetrofitService.getClient().verifyUserCode(code).enqueue(new Callback<VerifyUserCodeResponse>() {
            @Override
            public void onResponse(Call<VerifyUserCodeResponse> call, Response<VerifyUserCodeResponse> response) {
                if (response.isSuccessful()) {
                    boolean success = response.body().getSuccess();
                    if (success) {
                        SPM.getInstance().save(SPM.USER_STATUS, 1);

                        new LoginEvent().fire();

                        listener.onSuccess("success");
                    } else {
                        SPM.getInstance().save(SPM.USER_STATUS, 0);
                        listener.onSuccess("failed");
                    }

                } else {
                    SPM.getInstance().save(SPM.USER_STATUS, 0);
                    listener.onSuccess(response.body().getError());
                }

                Log.d(TAG, "Code verification completed...");
            }

            @Override
            public void onFailure(Call<VerifyUserCodeResponse> call, Throwable t) {

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