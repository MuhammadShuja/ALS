package com.alllinkshare.user.api.controllers;

import android.content.Context;
import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.retrofit.RetrofitService;
import com.alllinkshare.user.api.retrofit.responses.UpdateEmailReq;
import com.alllinkshare.user.api.retrofit.responses.UpdateMobNumResponse;
import com.alllinkshare.user.api.retrofit.responses.UpdateMobileNumber;
import com.alllinkshare.user.api.retrofit.responses.UpdatePassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CredentialsController {
    private static final String TAG = "API/Credentials";

    public static void updateMobileNumberRequest(final String currentMobileNumber, final String newMobileNumber, final Listeners.CredentialsListener listener){
        Log.d(TAG, "Update mobile number request initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updateMobileNumberRequest(
                acceptHeader, authorizationHeader,
                currentMobileNumber, newMobileNumber
        ).enqueue(new Callback<UpdateMobNumResponse>() {
            @Override
            public void onResponse(Call<UpdateMobNumResponse> call, Response<UpdateMobNumResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        listener.onSuccess("Verification code has been sent to your mobile number");
                    } else {
                        listener.onFailure(response.body().getErrors());
                    }
                } else {
                    String error = response.message();
                    listener.onFailure("Please enter a valid mobile number");
                }

                Log.d(TAG, "Update mobile number request completed...");
            }

            @Override
            public void onFailure(Call<UpdateMobNumResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
                else {
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }

    public static void updateMobileNumber(final String currentMobileNumber, final String newMobileNumber, final String verificationCode, final Listeners.CredentialsListener listener){
        Log.d(TAG, "Update mobile number initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updateMobileNumber(
                acceptHeader, authorizationHeader,
                currentMobileNumber, newMobileNumber, verificationCode
        ).enqueue(new Callback<UpdateMobileNumber>() {
            @Override
            public void onResponse(Call<UpdateMobileNumber> call, Response<UpdateMobileNumber> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                       listener.onSuccess("Your credentials has been updated successfully");
                    } else {
                        listener.onFailure(response.body().getErrors());
                    }
                } else {
                    String error = response.message();
                    listener.onFailure("Invalid verification code");
                }

                Log.d(TAG, "Update mobile number completed...");
            }

            @Override
            public void onFailure(Call<UpdateMobileNumber> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
                else {
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }

    public static void updateEmailRequest(final String currentEmail, final String newEmail, final Listeners.CredentialsListener listener){
        Log.d(TAG, "Update email request initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updateEmailRequest(
                acceptHeader, authorizationHeader,
                currentEmail, newEmail
        ).enqueue(new Callback<UpdateEmailReq>() {
            @Override
            public void onResponse(Call<UpdateEmailReq> call, Response<UpdateEmailReq> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        listener.onSuccess("Verification code has been sent to your mobile number");
                    } else {
                        listener.onFailure("Please enter a valid email address");
                    }
                } else {
                    String error = response.errorBody().toString();
                    listener.onFailure("Please enter a valid email address");
                }
                Log.d(TAG, "Update email request completed...");
            }

            @Override
            public void onFailure(Call<UpdateEmailReq> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
                else{
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }

    public static void updateEmail(final String currentEmail, final String newEmail, final String verificationCode, final Listeners.CredentialsListener listener){
        Log.d(TAG, "Update email initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updateEmail(
                acceptHeader, authorizationHeader,
                currentEmail, newEmail, verificationCode
        ).enqueue(new Callback<UpdateEmailReq>() {
            @Override
            public void onResponse(Call<UpdateEmailReq> call, Response<UpdateEmailReq> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        listener.onSuccess("Your credentials has been updated successfully");
                    } else {
                        listener.onFailure(response.body().getErrors());
                    }
                } else {
                    String error = response.message();
                    listener.onFailure("Invalid verification code");
                }
                Log.d(TAG, "Update email completed...");
            }

            @Override
            public void onFailure(Call<UpdateEmailReq> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
                else{
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }

    public static void updatePassword(final String currentPassword, final String newPassword, final Listeners.CredentialsListener listener){
        Log.d(TAG, "Update password initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updatePassword(
                acceptHeader, authorizationHeader,
                currentPassword, newPassword
        ).enqueue(new Callback<UpdatePassResponse>() {
            @Override
            public void onResponse(Call<UpdatePassResponse> call, Response<UpdatePassResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        listener.onSuccess("Your credentials has been updated successfully");
                    } else {
                        listener.onFailure(response.body().getError());
                    }
                } else{
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Update password completed...");
            }

            @Override
            public void onFailure(Call<UpdatePassResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
                else{
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}