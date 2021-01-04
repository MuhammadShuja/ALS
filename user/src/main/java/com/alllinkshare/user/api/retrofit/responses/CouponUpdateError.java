package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponUpdateError {

    @SerializedName("coupon_id")
    @Expose
    private List<String> couponId = null;
    @SerializedName("coupon_name")
    @Expose
    private List<String> couponName = null;
    @SerializedName("coupon_code")
    @Expose
    private List<String> couponCode = null;
    @SerializedName("discount_type")
    @Expose
    private List<String> discountType = null;
    @SerializedName("percent_off")
    @Expose
    private List<String> percentOff = null;
    @SerializedName("discount")
    @Expose
    private List<String> discount = null;

    public List<String> getCouponId() {
        return couponId;
    }

    public void setCouponId(List<String> couponId) {
        this.couponId = couponId;
    }

    public List<String> getCouponName() {
        return couponName;
    }

    public void setCouponName(List<String> couponName) {
        this.couponName = couponName;
    }

    public List<String> getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(List<String> couponCode) {
        this.couponCode = couponCode;
    }

    public List<String> getDiscountType() {
        return discountType;
    }

    public void setDiscountType(List<String> discountType) {
        this.discountType = discountType;
    }

    public List<String> getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(List<String> percentOff) {
        this.percentOff = percentOff;
    }

    public List<String> getDiscount() {
        return discount;
    }

    public void setDiscount(List<String> discount) {
        this.discount = discount;
    }

}
