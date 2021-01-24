package com.alllinkshare.liveTrack.tracking.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("mobile_number")
    public String phoneNo;
    @SerializedName("profile_picture")
    public String profile_picture;
    @SerializedName("address")
    public Address address;

    public User(String name, String email, String phoneNo, String profile_picture) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.profile_picture = profile_picture;
    }

    public class Address {
        @SerializedName("location")
        public String location;
        @SerializedName("latitude")
        public String latitude;
        @SerializedName("longitude")
        public String longitude;

        public Address(String location, String latitude, String longitude) {
            this.location = location;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
