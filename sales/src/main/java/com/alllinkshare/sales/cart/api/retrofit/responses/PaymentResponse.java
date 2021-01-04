package com.alllinkshare.sales.cart.api.retrofit.responses;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}