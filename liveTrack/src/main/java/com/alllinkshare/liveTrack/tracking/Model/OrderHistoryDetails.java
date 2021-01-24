package com.alllinkshare.liveTrack.tracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDetails {
    @SerializedName("data")
    public List<History> historyList = new ArrayList<>();

    public class History {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("order_id")
        @Expose
        public String orderId;
        @SerializedName("updated_at")
        @Expose
        public String updated_at;
        @SerializedName("pickup_latitude")
        @Expose
        public String pickup_latitude;
        @SerializedName("pickup_longitude")
        @Expose
        public String pickup_longitude;
        @SerializedName("current_position_latitude")
        @Expose
        public String current_position_latitude;
        @SerializedName("current_position_longitude")
        @Expose
        public String current_position_longitude;
        @SerializedName("destination_latitude")
        @Expose
        public String destination_latitude;
        @SerializedName("destination_longitude")
        @Expose
        public String destination_longitude;
        @SerializedName("status")
        @Expose
        public String status;

        public History(String id, String orderId, String updated_at, String pickup_latitude, String pickup_longitude, String current_position_latitude, String current_position_longitude, String destination_latitude, String destination_longitude, String status) {
            this.id = id;
            this.orderId = orderId;
            this.updated_at = updated_at;
            this.pickup_latitude = pickup_latitude;
            this.pickup_longitude = pickup_longitude;
            this.current_position_latitude = current_position_latitude;
            this.current_position_longitude = current_position_longitude;
            this.destination_latitude = destination_latitude;
            this.destination_longitude = destination_longitude;
            this.status = status;
        }
    }


}
