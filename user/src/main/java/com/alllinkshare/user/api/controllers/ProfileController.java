package com.alllinkshare.user.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.retrofit.RetrofitService;
import com.alllinkshare.user.api.retrofit.responses.UpdateProfile;
import com.alllinkshare.user.models.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileController {
    private static final String TAG = "API/Profile";

    public static void get(final Listeners.ProfileListener listener){
        Log.d(TAG, "Get profile initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().getProfileData(
                acceptHeader, authorizationHeader
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = null;
                if (response.isSuccessful()) {
                    user = new User(response.body().getProfilePicture(), response.body().getFirstName()
                            , response.body().getLastName(), response.body().getFullName(), response.body().getEmail()
                            , response.body().getMobileNumber(), response.body().getGender(), response.body().getAddress(), response.body().getStreetAddress()
                            , response.body().getPinCode(), response.body().getCity(), response.body().getState()
                            , response.body().getCountry());
                    listener.onSuccess(user);
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }
                Log.d(TAG, "Get profile completed...");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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

    public static void update(final User user, final Listeners.ProfileListener listener){
        Log.d(TAG, "Update profile initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updateProfileData(
                acceptHeader, authorizationHeader,
                user.getFirstName(), user.getLastName(),
                user.getAddress(), user.getStreetAddress(),
                user.getCountry().getId(), user.getState().getId(),
                user.getCity().getId(), user.getPinCode()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(user);
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Update profile completed...");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String netError = "No Internet Connection!";
                    listener.onFailure(netError);
                }
            }
        });
    }

    public static void profilePicture(final File file, final Listeners.ProfilePictureListener listener){
        Log.d(TAG, "Update profile picture initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);


        RequestBody reqFile = RequestBody.create(file,MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_picture", file.getName(), reqFile);

        RetrofitService.getClient().updateProfileImage(
                acceptHeader, authorizationHeader,
                body
        ).enqueue(new Callback<UpdateProfile>() {
            @Override
            public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        listener.onSuccess(file.getPath());
                    } else {
                        listener.onFailure("Response...........Error....");
                    }
                } else {
                    String error = response.errorBody().toString();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Update profile picture completed...");
            }

            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {
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
}