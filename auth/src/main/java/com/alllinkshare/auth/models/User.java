package com.alllinkshare.auth.models;

public class User {
    private String userType;
    private String firstName;
    private String lastName;
    private String countryCode;
    private String email;
    private String mobileNumber;
    private String password;
    private String passwordConfirmation;

    public User(String userType, String firstName, String lastName, String countryCode, String email, String mobileNumber, String password, String passwordConfirmation) {
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryCode = countryCode;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUserType() {
        return userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}