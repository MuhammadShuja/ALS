package com.alllinkshare.user.api.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponsResponseData {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("form_days")
        @Expose
        private Integer formDays;

    public CouponsResponseData(Integer id, String status, String company,
                               String category, String name, String code,
                               String type, Integer discount, Integer amount, Integer formDays) {
        this.id = id;
        this.status = status;
        this.company = company;
        this.category = category;
        this.name = name;
        this.code = code;
        this.type = type;
        this.discount = discount;
        this.amount = amount;
        this.formDays = formDays;
    }

    public Integer getId() {
        return id;
    }

        public void setId(Integer id) {
        this.id = id;
    }

        public String getStatus() {
        return status;
    }

        public void setStatus(String status) {
        this.status = status;
    }

        public String getCompany() {
        return company;
    }

        public void setCompany(String company) {
        this.company = company;
    }

        public String getCategory() {
        return category;
    }

        public void setCategory(String category) {
        this.category = category;
    }

        public String getName() {
        return name;
    }

        public void setName(String name) {
        this.name = name;
    }

        public String getCode() {
        return code;
    }

        public void setCode(String code) {
        this.code = code;
    }

        public String getType() {
        return type;
    }

        public void setType(String type) {
        this.type = type;
    }

        public Integer getDiscount() {
        return discount;
    }

        public void setDiscount(Integer discount) {
        this.discount = discount;
    }

        public Integer getAmount() {
        return amount;
    }

        public void setAmount(Integer amount) {
        this.amount = amount;
    }

        public Integer getFormDays() {
        return formDays;
    }

        public void setFormDays(Integer formDays) {
        this.formDays = formDays;
    }

    }