package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_korean")
    @Expose
    private String nameKorean;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;

    public StateResponse(Integer id, String name, String nameKorean, Integer countryId) {
        this.id = id;
        this.name = name;
        this.nameKorean = nameKorean;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

}