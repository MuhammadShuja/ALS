package com.alllinkshare.user.api.retrofit.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("street_address")
    @Expose
    private String streetAddress;
    @SerializedName("city")
    @Expose
    private CityResponse city;
    @SerializedName("state")
    @Expose
    private StateResponse state;
    @SerializedName("country")
    @Expose
    private CountryResponse country;
    @SerializedName("pincode")
    @Expose
    private String pincode;

/*

    public User(String profilePicture, String firstName, String lastName,
                String fullName, String email, String mobileNumber,
                String gender, String address, String streetAddress, String pinCode, City city, State state, Country country) {
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.address = address;
        this.streetAddress = streetAddress;
        this.pincode = pinCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }
*/


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public CityResponse getCity() {
        return city;
    }

    public void setCity(CityResponse city) {
        this.city = city;
    }

    public StateResponse getState() {
        return state;
    }

    public void setState(StateResponse state) {
        this.state = state;
    }

    public CountryResponse getCountry() {
        return country;
    }

    public void setCountry(CountryResponse country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}

