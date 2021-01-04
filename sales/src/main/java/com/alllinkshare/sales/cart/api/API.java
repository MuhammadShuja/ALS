package com.alllinkshare.sales.cart.api;

import android.util.Log;

import com.alllinkshare.sales.cart.api.config.Listeners;
import com.alllinkshare.sales.cart.api.controllers.CheckoutController;
import com.alllinkshare.sales.cart.api.controllers.CouponsController;
import com.alllinkshare.sales.cart.api.controllers.PaymentController;

public class API {
    private static final String TAG = "API/Sales";

    public static void coupons(String listingIds, Listeners.CouponsListener listener){
        Log.d(TAG, "Calling coupons endpoint...");
        CouponsController.coupons(listingIds, listener);
    }

    public static void checkout(String orderJson, Listeners.CheckoutListener listener){
        Log.d(TAG, "Calling checkout endpoint...");
        CheckoutController.checkout(orderJson, listener);
    }

    public static void token(Listeners.TokenListener listener){
        Log.d(TAG, "Calling token endpoint...");
        PaymentController.token(listener);
    }

    public static void pay(String amount, String nonce, Listeners.PayListener listener){
        Log.d(TAG, "Calling checkout endpoint...");
        PaymentController.pay(amount, nonce, listener);
    }
}