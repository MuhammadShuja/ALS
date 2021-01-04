package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_korean")
    @Expose
    private String nameKorean;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("phone_code")
    @Expose
    private Integer phoneCode;

    public CountryResponse(Integer id, String name, String nameKorean, String countryCode, Integer phoneCode) {
        this.id = id;
        this.name = name;
        this.nameKorean = nameKorean;
        this.countryCode = countryCode;
        this.phoneCode = phoneCode;
    }

    public CountryResponse(Integer id, String name, String nameKorean) {
        this.id = id;
        this.name = name;
        this.nameKorean = nameKorean;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKorean() {
        return nameKorean;
    }

    public void setNameKorean(String nameKorean) {
        this.nameKorean = nameKorean;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

}