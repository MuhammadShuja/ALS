package com.alllinkshare.user.models;

public class City {
    private int id;
    private String name;
    private String nameKorean;

    public City(int id, String name, String nameKorean) {
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
