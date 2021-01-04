package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {

    @SerializedName("current_email")
    @Expose
    private List<String> currentEmail = null;
    @SerializedName("new_email")
    @Expose
    private List<String> newEmail = null;

    public List<String> getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(List<String> currentEmail) {
        this.currentEmail = currentEmail;
    }

    public List<String> getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(List<String> newEmail) {
        this.newEmail = newEmail;
    }














    @SerializedName("current_mobile_number")
    @Expose
    private List<String> currentMobileNumber = null;
    @SerializedName("new_mobile_number")
    @Expose
    private List<String> newMobileNumber = null;

    public List<String> getCurrentMobileNumber() {
        return currentMobileNumber;
    }

    public void setCurrentMobileNumber(List<String> currentMobileNumber) {
        this.currentMobileNumber = currentMobileNumber;
    }

    public List<String> getNewMobileNumber() {
        return newMobileNumber;
    }

    public void setNewMobileNumber(List<String> newMobileNumber) {
        this.newMobileNumber = newMobileNumber;
    }





    @SerializedName("verification_code")
    @Expose
    private List<String> verificationCode = null;

    public List<String> getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(List<String> verificationCode) {
        this.verificationCode = verificationCode;
    }


// Update Profile only two parameters required

    @SerializedName("first_name")
    @Expose
    private List<String> firstName = null;
    @SerializedName("last_name")
    @Expose
    private List<String> lastName = null;

    public List<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(List<String> firstName) {
        this.firstName = firstName;
    }

    public List<String> getLastName() {
        return lastName;
    }

    public void setLastName(List<String> lastName) {
        this.lastName = lastName;
    }



}