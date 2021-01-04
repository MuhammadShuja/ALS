package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouriteRemoveErrors {

    @SerializedName("item_id")
    @Expose
    private List<String> itemId = null;

    public List<String> getItemId() {
        return itemId;
    }

    public void setItemId(List<String> itemId) {
        this.itemId = itemId;
    }

}
