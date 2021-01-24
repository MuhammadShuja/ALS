package com.alllinkshare.liveTrack.tracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersModel
{
    @SerializedName("data")
    @Expose
    public List<AllOrder> dataList;

    public class AllOrder {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("status")
        @Expose
        public String status;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        @SerializedName("error")
        @Expose
        private String error;


        @SerializedName("product")
        @Expose
        public Product product;
        @SerializedName("payment")
        @Expose
        public Payment payment;
        @SerializedName("customer")
        @Expose
        public Customer customer;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public class Product {
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("quantity")
            @Expose
            public String quantity;
            @SerializedName("price")
            @Expose
            public String price;

            public Product(String name, String quantity, String price) {
                this.name = name;
                this.quantity = quantity;
                this.price = price;
            }
        }

        public class Payment {
            @SerializedName("amount")
            @Expose
            public String amount;
            @SerializedName("status")
            @Expose
            public String status;
            @SerializedName("type")
            @Expose
            public String type;

            public Payment(String amount, String status, String type) {
                this.amount = amount;
                this.status = status;
                this.type = type;
            }
        }

        public class Customer {
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("mobile")
            @Expose
            public String mobile;
            @SerializedName("street_address")
            @Expose
            public String street_address;
            @SerializedName("address")
            @Expose
            public String address;
            @SerializedName("longitude")
            @Expose
            public String longitude;
            @SerializedName("latitude")
            @Expose
            public String latitude;

            public Customer(String name, String mobile, String street_address, String address, String longitude, String latitude) {
                this.name = name;
                this.mobile = mobile;
                this.street_address = street_address;
                this.address = address;
                this.longitude = longitude;
                this.latitude = latitude;
            }
        }

        public AllOrder(String orderId, String status, Product product, Payment payment, Customer customer) {
            this.id = orderId;
            this.status = status;
            this.product = product;
            this.payment = payment;
            this.customer = customer;
        }
    }
}
