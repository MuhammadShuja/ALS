package com.alllinkshare.catalogshopping.api.config;

import com.alllinkshare.catalogshopping.models.Product;
import com.alllinkshare.catalogshopping.models.ProductDealer;

import java.util.List;

public class Listeners {

    public interface ProductsListener{
        void onSuccess(List<Product> products, int currentPageNumber, int lastPageNumber, int totalProducts);
        void onFailure(String error);
    }

    public interface ProductListener{
        void onSuccess(Product product);
        void onFailure(String error);
    }

    public interface DealersListener{
        void onSuccess(List<ProductDealer> dealers);
        void onFailure(String error);
    }
}