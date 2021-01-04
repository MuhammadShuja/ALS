package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponUpdate {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private CouponUpdateError errors;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CouponUpdateError getErrors() {
        return errors;
    }

    public void setErrors(CouponUpdateError errors) {
        this.errors = errors;
    }

}