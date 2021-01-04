package com.alllinkshare.user.api.retrofit.responses;

import com.alllinkshare.user.models.Coupon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponEdit {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("coupon")
    @Expose
    private Coupon coupon;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}