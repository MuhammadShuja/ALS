package com.alllinkshare.liveTrack.tracking.Model;

public class CurrentLocation {
    public String ride_id;
    public String longitude;
    public String latitude;

    public CurrentLocation(String ride_id, String longitude, String latitude) {
        this.ride_id = ride_id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
