package com.alllinkshare.catalog.repos;

import android.util.Log;

import com.alllinkshare.catalog.api.API;
import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.models.Listing;

import java.util.List;

public class ListingRepository {
    private static final String TAG = "Repo/Listing";

    private int categoryId = -1;
    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private static ListingRepository instance = null;

    public static synchronized ListingRepository getInstance(){
        if(instance == null){
            instance = new ListingRepository();
        }
        return instance;
    }

    private ListingRepository(){
        Log.d(TAG, "New instance created...");
    }

    public void getListings(int categoryId, int pageNumber, final ListingRepository.DataListReadyListener listener){
        Log.d(TAG, "Get listings for category_id: "+categoryId);
        currentPage = pageNumber;

        if(this.categoryId != categoryId){
            this.categoryId = categoryId;
            currentPage = 0;
            lastPage = 1;
            totalItems = 0;
            isLoading = false;
        }

        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;

        API.listings(categoryId, currentPage,new Listeners.ListingsListener() {
            @Override
            public void onSuccess(List<Listing> listings, int currentPageNumber, int lastPageNumber, int totalListings) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalListings;

                isLoading = false;
                listener.onDataReady(totalListings, currentPageNumber,listings);
            }

            @Override
            public void onFailure(String error) {
                isLoading = false;
                listener.onFailure(error);
            }
        });
    }

    public void getListing(int listingId, int categoryId, final ListingRepository.DataReadyListener listener){
        Log.d(TAG, "Get listing details for listing_id: "+listingId);

        API.listing(listingId, categoryId, new Listeners.ListingListener() {
            @Override
            public void onSuccess(Listing listing) {
                listener.onDataReady(listing);
            }

            @Override
                public void onFailure(String error) {
                    listener.onFailure(error);
                }
            });
    }

    public interface DataReadyListener{
        void onDataReady(Listing listing);
        void onFailure(String error);
    }

    public interface DataListReadyListener{
        void onDataReady(int total, int currentPageNumber, List<Listing> listings);
        void onFailure(String error);
    }
}