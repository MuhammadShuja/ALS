package com.alllinkshare.user.api.controllers;

import android.util.Log;

import com.alllinkshare.core.retrofit.NetworkConnectionInterceptor;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.retrofit.RetrofitService;
import com.alllinkshare.user.api.retrofit.responses.CouponEdit;
import com.alllinkshare.user.api.retrofit.responses.CouponUpdate;
import com.alllinkshare.user.api.retrofit.responses.CouponsResponse;
import com.alllinkshare.user.models.Coupon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsController {
    private static final String TAG = "API/Coupons";

    public static void coupons(int pageNumber, final Listeners.CouponsListener listener){
        Log.d(TAG, "Get coupons initiated...");

        final List<Coupon> items = new ArrayList<>();

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().getCoupons(acceptHeader, authorizationHeader).enqueue(new Callback<CouponsResponse>() {
            @Override
            public void onResponse(Call<CouponsResponse> call, Response<CouponsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCurrentPage() != null) {
                        try {
                            int lastPageNumber = response.body().getLastPage();
                            int totalItemCount = response.body().getTotal();
                            int currentPageNumber = response.body().getCurrentPage();

                            int firstpagevalue = response.body().getTo();
                            for (int i = 0; i < totalItemCount; i++) {

                                int id = response.body().getData().get(i).getId();
                                String status = response.body().getData().get(i).getStatus();
                                String company = response.body().getData().get(i).getCompany();
                                String category = response.body().getData().get(i).getCategory();
                                String name = response.body().getData().get(i).getName();
                                String code = response.body().getData().get(i).getCode();
                                String type = response.body().getData().get(i).getType();
                                String discount = response.body().getData().get(i).getDiscount().toString();
                                String amount = response.body().getData().get(i).getAmount().toString();
                                String formDays = response.body().getData().get(i).getFormDays().toString();

                                items.add(new Coupon(id, status,
                                        company, category,
                                        name, code, type,
                                        discount, amount, formDays));
                            }

                            listener.onSuccess(items, currentPageNumber, lastPageNumber, totalItemCount);

                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.onFailure(e.getMessage());
                        }

                    }
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Get coupons completed...");
            }

            @Override
            public void onFailure(Call<CouponsResponse> call, Throwable t) {
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

    public static void coupon(int couponId, final Listeners.CouponListener listener){
        Log.d(TAG, "Edit coupon initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().editCoupon(acceptHeader, authorizationHeader, couponId).enqueue(new Callback<CouponEdit>() {
            @Override
            public void onResponse(Call<CouponEdit> call, Response<CouponEdit> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().toString());
                    if (response.body().getSuccess()) {
                        int id = response.body().getCoupon().getId();
                        String status = response.body().getCoupon().getStatus();

                        String company = response.body().getCoupon().getCompany();
                        String category = response.body().getCoupon().getCategory();
                        String name = response.body().getCoupon().getName();
                        String code = response.body().getCoupon().getCode();
                        String type = response.body().getCoupon().getType();
                        String discount = response.body().getCoupon().getDiscount();
                        String amount = response.body().getCoupon().getAmount();
                        String formDays = response.body().getCoupon().getFormDays();
                        Coupon coupon = new Coupon(id, status,
                                company, category,
                                name, code, type,
                                discount, amount, formDays);

                        listener.onSuccess(coupon);
                    } else {
                        String error = "Coupon Does not belong to this user!";
                        listener.onFailure(error);
                    }

                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }

                Log.d(TAG, "Edit coupon completed...");
            }

            @Override
            public void onFailure(Call<CouponEdit> call, Throwable t) {
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

    public static void update(final Coupon coupon, final Listeners.CouponListener listener){
        Log.d(TAG, "Update coupon initiated...");

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        RetrofitService.getClient().updateCoupon(
                acceptHeader, authorizationHeader,
                coupon.getId(), coupon.getName(), coupon.getCode(),
                coupon.getDiscount(), coupon.getDiscount(), coupon.getAmount()
        ).enqueue(new Callback<CouponUpdate>() {
            @Override
            public void onResponse(Call<CouponUpdate> call, Response<CouponUpdate> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equals(true)) {
                        listener.onSuccess(coupon);
                    } else {
                        listener.onFailure("Error, could not update coupon");
                    }
                } else {
                    String error = response.message();
                    listener.onFailure(error);
                }
                Log.d(TAG, "Update coupon completed...");
            }

            @Override
            public void onFailure(Call<CouponUpdate> call, Throwable t) {
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