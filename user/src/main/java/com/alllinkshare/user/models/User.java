package com.alllinkshare.user.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("gender")
    private String gender;

    @SerializedName("address")
    private String address;

    @SerializedName("street_address")
    private String streetAddress;

    @SerializedName("pincode")
    private String pinCode;

    @SerializedName("city")
    private City city;

    @SerializedName("state")
    private State state;

    @SerializedName("country")
    private Country country;

    @SerializedName("fcm_token")
    private String fcmToken;

    public User(String profilePicture, String firstName, String lastName, String fullName, String email, String mobileNumber, String gender, String address, String streetAddress, String pinCode, City city, State state, Country country) {
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.address = address;
        this.streetAddress = streetAddress;
        this.pinCode = pinCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPinCode() {
        return pinCode;
    }

    public City getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public Country getCountry() {
        return country;
    }

    public void setFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}