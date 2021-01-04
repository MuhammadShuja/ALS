package com.alllinkshare.shopping.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.shopping.R;
import com.alllinkshare.shopping.models.Product;
import com.alllinkshare.shopping.repos.ProductsRepository;
import com.alllinkshare.shopping.ui.adapters.ProductsAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsFragment extends Fragment {

    private static final String CATEGORY_ID = "category_id";
    private static final String STYLE = "category_id";
    private int categoryID;
    private int style;

    private View rootView;
    private ProductsAdapter productsAdapter;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance(int categoryID, int style) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryID);
        args.putInt(STYLE, style);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getInt(CATEGORY_ID);
            style = getArguments().getInt(STYLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_products, container, false);

        initProducts();
        loadProducts();

        return rootView;
    }

    private void initProducts(){
        productsAdapter = new ProductsAdapter(getActivity(), new ArrayList<Product>(), new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(productsAdapter);
    }

    private void loadProducts(){
        productsAdapter.setData(ProductsRepository.getFakeProducts(20));
    }
}