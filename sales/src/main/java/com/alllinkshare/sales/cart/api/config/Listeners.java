package com.alllinkshare.sales.cart.api.config;

import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentResponse;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentTokenResponse;
import com.alllinkshare.sales.cart.models.Coupon;

import java.util.List;

public class Listeners {

    public interface CouponsListener{
        void onSuccess(List<Coupon> couponList);
        void onFailure(String error);
    }

    public interface CheckoutListener{
        void onSuccess(String message);
        void onFailure(String error);
    }

    public interface TokenListener{
        void onSuccess(PaymentTokenResponse response);
        void onFailure(String error);
    }

    public interface PayListener{
        void onSuccess(String message);
        void onFailure(String error);
    }
}