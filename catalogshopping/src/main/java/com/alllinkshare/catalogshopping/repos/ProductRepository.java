package com.alllinkshare.catalogshopping.repos;

import android.util.Log;

import com.alllinkshare.catalogshopping.api.API;
import com.alllinkshare.catalogshopping.api.config.Listeners;
import com.alllinkshare.catalogshopping.models.Product;

import java.util.List;

public class ProductRepository {
    private static final String TAG = "Repo/Listing";

    private int listingId = -1;
    private int shoppingCategoryId = -1;
    private int mainCategoryId = -1;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private static ProductRepository instance = null;

    public static synchronized ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    private ProductRepository(){
        Log.d(TAG, "New instance created...");
    }

    public void getProducts(int listingId, int shoppingCategoryId, int mainCategoryId, int pageNumber,
                            final ProductRepository.DataListReadyListener listener){
        Log.d(TAG, "Get products for listing_id: "+listingId+
                ", shopping_category_id: "+shoppingCategoryId+
                ", main_category_id: "+mainCategoryId);

        currentPage = pageNumber;

        if(this.listingId != listingId
            && this.shoppingCategoryId != shoppingCategoryId
            && this.mainCategoryId != mainCategoryId){
            this.listingId = listingId;
            this.shoppingCategoryId = shoppingCategoryId;
            this.mainCategoryId = mainCategoryId;
            currentPage = 0;
            lastPage = 1;
            totalItems = 0;
            isLoading = false;
        }

        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;

        API.products(listingId, shoppingCategoryId, mainCategoryId, currentPage,new Listeners.ProductsListener() {
            @Override
            public void onSuccess(List<Product> products, int currentPageNumber, int lastPageNumber, int totalProducts) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalProducts;

                isLoading = false;
                listener.onDataReady(totalProducts, currentPageNumber, products);
            }

            @Override
            public void onFailure(String error) {
                isLoading = false;
                listener.onFailure(error);
            }
        });
    }

    public void getListing(int productId, int categoryId, final ProductRepository.DataReadyListener listener){
        Log.d(TAG, "Get product details for product_id: "+productId);
//
//        API.listing(listingId, categoryId, new Listeners.ListingListener() {
//            @Override
//            public void onSuccess(Listing listing) {
//                listener.onDataReady(listing);
//            }
//
//            @Override
//                public void onFailure(String error) {
//                    listener.onFailure(error);
//                }
//            });
    }

    public interface DataReadyListener{
        void onDataReady(Product product);
        void onFailure(String error);
    }

    public interface DataListReadyListener{
        void onDataReady(int total, int currentPageNumber, List<Product> products);
        void onFailure(String error);
    }
}