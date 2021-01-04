package com.alllinkshare.catalogshopping.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alllinkshare.catalogshopping.R;
import com.alllinkshare.catalogshopping.api.API;
import com.alllinkshare.catalogshopping.api.config.Listeners;
import com.alllinkshare.catalogshopping.models.Product;
import com.alllinkshare.catalogshopping.ui.adapters.ProductsAdapter;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GifImageView;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.ui.fragments.CartBarFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingProductsFragment extends Fragment {

    private int listingId;
    private int shoppingCategoryId;
    private int mainCategoryId;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private ProductsAdapter productsAdapter;

    private LinearLayout loadingWrapper, emptyWrapper, contentWrapper;

    private View rootView;

    public ShoppingProductsFragment() {
        // Required empty public constructor
    }

    public static ShoppingProductsFragment newInstance(int listingId, int shoppingCategoryId, int mainCategoryId) {
        ShoppingProductsFragment fragment = new ShoppingProductsFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.MAIN_ID, mainCategoryId);
        args.putInt(Keys.PARENT_ID, shoppingCategoryId);
        args.putInt(Keys.ITEM_ID, listingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mainCategoryId = getArguments().getInt(Keys.MAIN_ID);
            shoppingCategoryId = getArguments().getInt(Keys.PARENT_ID);
            listingId = getArguments().getInt(Keys.ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shopping_products, container, false);

        initCartBar();
        initProducts();
        loadProducts();

        return rootView;
    }

    private void initCartBar(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.cart_bar_host, CartBarFragment.newInstance(Cart.TYPE_SHOPPING))
                .commit();
    }

    private void initProducts(){
        productsAdapter = new ProductsAdapter(getActivity(), new ArrayList<Product>(), new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Coordinator.getShoppingNavigator().navigateToProduct(product.getId());
            }

            @Override
            public void onFavouriteClick(Product product, boolean isFavourite) {

            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(productsAdapter);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    loadProducts();
                }
            }
        });

        loadingWrapper = rootView.findViewById(R.id.loading_wrapper);
        emptyWrapper = rootView.findViewById(R.id.empty_wrapper);
        contentWrapper = rootView.findViewById(R.id.content_wrapper);

        GifImageView gifImageView = rootView.findViewById(R.id.gif_image_view);
        gifImageView.setGifImageResource(R.drawable.empty);
    }

    private void loadProducts(){
        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;
        loadingWrapper.setVisibility(View.VISIBLE);

        API.products(listingId, shoppingCategoryId, mainCategoryId, currentPage, new Listeners.ProductsListener() {
            @Override
            public void onSuccess(List<Product> products, int currentPageNumber, int lastPageNumber, int totalProducts) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalProducts;

                productsAdapter.addData(products);

                loadingWrapper.setVisibility(View.GONE);
                isLoading = false;

                if(totalItems < 1){
                    contentWrapper.setVisibility(View.GONE);
                    emptyWrapper.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String error) {
                loadingWrapper.setVisibility(View.GONE);
                isLoading = false;
                Toast.makeText(getContext(), "Error: "+error, Toast.LENGTH_LONG).show();
            }
        });
    }
}