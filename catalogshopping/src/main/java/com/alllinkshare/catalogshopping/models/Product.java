package com.alllinkshare.catalogshopping.models;

import com.alllinkshare.reviews.models.Rating;
import com.alllinkshare.reviews.models.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product {
    @Expose(serialize = false)
    private String uuid = null;

    public String getUuid() {
        if(uuid == null){
            uuid = getId()+getName();
        }
        return uuid;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("listing_id")
    private int listingId;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("main_category_id")
    private int mainCategoryId;

    @SerializedName("name")
    private String name;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("price")
    private float price;

    @SerializedName("site_name")
    private String siteName;

    @SerializedName("click_url")
    private String clickUrl;

    @SerializedName("discount")
    private boolean isDiscountAvailable;

    @SerializedName("discount_percentage")
    private int discountPercentage;

    @SerializedName("action")
    private String action;

    @SerializedName("is_favourite")
    private boolean isFavourite;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("reviews")
    private ArrayList<Review> reviews;

    @SerializedName("colors")
    private List<ProductColor> colors;

    @SerializedName("delivery_methods")
    private DeliveryMethods deliveryMethods;

    @SerializedName("description")
    private String description;

    @SerializedName("manufacturer")
    private String manufacturer;

    @SerializedName("brand_name")
    private String brandName;

    @SerializedName("model_number")
    private String modelNumber;

    @SerializedName("service_type")
    private String serviceType;

    @SerializedName("country_origin")
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(int mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public boolean isDiscountAvailable() {
        return isDiscountAvailable;
    }

    public void setDiscountAvailable(boolean discountAvailable) {
        isDiscountAvailable = discountAvailable;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public List<ProductColor> getColors() {
        return colors;
    }

    public void setColors(List<ProductColor> colors) {
        this.colors = colors;
    }

    public DeliveryMethods getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(DeliveryMethods deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}