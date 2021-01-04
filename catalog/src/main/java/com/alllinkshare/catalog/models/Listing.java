package com.alllinkshare.catalog.models;

import com.alllinkshare.gallery.models.Image;
import com.alllinkshare.reviews.models.Rating;
import com.alllinkshare.reviews.models.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Listing {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("business_name")
    private String businessName;

    @SerializedName("business_address")
    private String businessAddress;

    @SerializedName("business_registration_number")
    private String businessRegistrationNumber;

    @SerializedName("price_level")
    private String priceLevel;

    @SerializedName("address")
    private String address;

    @SerializedName("is_open")
    private boolean isOpen;

    @SerializedName("is_franchise")
    private boolean isFranchise;

    @SerializedName("is_verified")
    private boolean isVerified;

    @SerializedName("keywords")
    private List<String> keywords;

    @SerializedName("free_services")
    private List<String> freeServices;

    @SerializedName("images")
    private ArrayList<Image> images;

    @SerializedName("description")
    private String businessInformation;

    @SerializedName("history")
    private String history;

    @SerializedName("crew_experience")
    private String crewExperience;

    @SerializedName("greetings")
    private String greetings;

    @SerializedName("event_news")
    private String eventNews;

    @SerializedName("contact_options")
    private ContactOptions contactOptions;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("reviews")
    private ArrayList<Review> reviews;

    @SerializedName("coupon")
    private String coupon;

    @SerializedName("actions")
    private List<FormAction> actions;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public String getAddress() {
        return address;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isFranchise() {
        return isFranchise;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public List<String> getFreeServices() {
        return freeServices;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public String getBusinessInformation() {
        return businessInformation;
    }

    public String getHistory() {
        return history;
    }

    public String getCrewExperience() {
        return crewExperience;
    }

    public String getGreetings() {
        return greetings;
    }

    public String getEventNews() {
        return eventNews;
    }

    public ContactOptions getContactOptions() {
        return contactOptions;
    }

    public Rating getRating() {
        return rating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public String getCoupon() {
        return coupon;
    }

    public List<FormAction> getActions() {
        return actions;
    }

    public static class ContactOptions{
        @SerializedName("mobile")
        private String mobile;

        @SerializedName("phone")
        private String phone;

        @SerializedName("video")
        private boolean videoAllowed;

        @SerializedName("text")
        private boolean textAllowed;

        public String getMobile() {
            return mobile;
        }

        public String getPhone() {
            return phone;
        }

        public boolean isVideoAllowed() {
            return videoAllowed;
        }

        public boolean isTextAllowed() {
            return textAllowed;
        }
    }

    public static class FormAction{
        @SerializedName("form_id")
        private int id;

        @SerializedName("form_name")
        private String name;

        @SerializedName("form_type")
        private String type;

        public FormAction(int id, String name, String type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }
}