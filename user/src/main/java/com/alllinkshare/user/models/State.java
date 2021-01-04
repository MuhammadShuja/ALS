package com.alllinkshare.user.models;

import com.google.gson.annotations.SerializedName;

public class State {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("name_korean")
    private String nameKorean;

    public State(int id, String name, String nameKorean) {
        this.id = id;
        this.name = name;
        this.nameKorean = nameKorean;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameKorean() {
        return nameKorean;
    }
}