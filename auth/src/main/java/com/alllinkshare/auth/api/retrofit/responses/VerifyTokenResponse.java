package com.alllinkshare.auth.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyTokenResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
