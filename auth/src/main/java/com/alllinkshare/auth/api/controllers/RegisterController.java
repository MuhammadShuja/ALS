package com.alllinkshare.auth.api.controllers;

import android.util.Log;

import com.alllinkshare.auth.R;
import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.retrofit.RetrofitService;
import com.alllinkshare.auth.api.retrofit.responses.UserResponse;
import com.alllinkshare.auth.models.User;
import com.alllinkshare.core.ALS;
import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterController {
    private static final String TAG = "API/Register";

    public static void register(final User user, final Listeners.AuthListener listener) {
        Log.d(TAG, "Register initiated...");

        RetrofitService.getClient().register(
                user.getUserType(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getCountryCode(), user.getMobileNumber(),
                user.getPassword(), user.getPasswordConfirmation(),
                ALS.getAppContext().getString(R.string.api_grant_type),
                ALS.getAppContext().getString(R.string.api_client_id),
                ALS.getAppContext().getString(R.string.api_client_secret),
                ALS.getAppContext().getString(R.string.api_scope)
        ).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    String tokenType = response.body().getTokenType();
                    Integer expireIn = response.body().getExpiresIn();
                    String accessToken = response.body().getAccessToken();
                    String refreshToken = response.body().getRefreshToken();

                    SPM.getInstance().save(SPM.TOKEN_TYPE, tokenType);
                    SPM.getInstance().save(SPM.EXPIRES_IN, expireIn);
                    SPM.getInstance().save(SPM.ACCESS_TOKEN, accessToken);
                    SPM.getInstance().save(SPM.REFRESH_TOKEN, refreshToken);
                    SPM.getInstance().save(SPM.USER_EMAIL, user.getEmail());

                    listener.onSuccess("Your account has been successfully registered");
                } else {
                    String error = response.message();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        JSONObject errors = jsonObject.getJSONObject("errors");

                        Iterator<String> iterator = errors.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            JSONArray value;
                            switch (key) {
                                case "user_type":
                                    value = (JSONArray) errors.get(key);
                                    listener.onFailure("Please enter userType");
                                    break;
                                case "first_name":
                                    value = (JSONArray) errors.get(key);
                                    listener.onFailure("Please enter first name");
                                    break;
                                case "last_name":
                                    value = (JSONArray) errors.get(key);
                                    listener.onFailure("Please enter last name");
                                    break;
                                case "email":
                                    value = (JSONArray) errors.get(key);
                                    String emailResponse = String.valueOf(value.get(0));

                                    if (emailResponse.equals("validation.unique")) {
                                        listener.onFailure("Email already exist, please enter new email");
                                    } else if (emailResponse.equals("validation.email")) {
                                        listener.onFailure("Please enter valid email");
                                    }
                                    break;
                                case "mobile_number":
                                    value = (JSONArray) errors.get(key);
                                    listener.onFailure("Please enter valid mobile number");
                                    break;
                                case "mobile_number_with_code":
                                    value = (JSONArray) errors.get(key);
                                    listener.onFailure("Mobile number already exist, please enter a different mobile number");
                                    break;
                                case "password":
                                    value = (JSONArray) errors.get(key);
                                    listener.onFailure("Please enter valid password");
                                    break;
                            }
                        }

                    } catch (Exception e) {
                        //    Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
                        listener.onSuccess(e.getMessage());
                    }


                }

                Log.d(TAG, "Register completed...");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                if (t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    String error = "No Internet Connection!";
                    listener.onFailure(error);
                } else {
                    listener.onFailure(t.getMessage());
                }
            }
        });
    }
}
