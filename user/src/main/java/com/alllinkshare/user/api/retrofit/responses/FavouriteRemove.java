package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteRemove {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("favouriteRemoveErrors")
    @Expose

    private FavouriteRemoveErrors favouriteRemoveErrors;


    public FavouriteRemoveErrors getFavouriteRemoveErrors() {
        return favouriteRemoveErrors;
    }

    public void setFavouriteRemoveErrors(FavouriteRemoveErrors favouriteRemoveErrors) {
        this.favouriteRemoveErrors = favouriteRemoveErrors;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


}