package com.alllinkshare.sales.cart.api.retrofit.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaymentTokenResponse implements Parcelable {
    @SerializedName("token")
    private String token;

    protected PaymentTokenResponse(Parcel in) {
        token = in.readString();
    }

    public static final Creator<PaymentTokenResponse> CREATOR = new Creator<PaymentTokenResponse>() {
        @Override
        public PaymentTokenResponse createFromParcel(Parcel in) {
            return new PaymentTokenResponse(in);
        }

        @Override
        public PaymentTokenResponse[] newArray(int size) {
            return new PaymentTokenResponse[size];
        }
    };

    public String getToken() {
        return token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
    }
}