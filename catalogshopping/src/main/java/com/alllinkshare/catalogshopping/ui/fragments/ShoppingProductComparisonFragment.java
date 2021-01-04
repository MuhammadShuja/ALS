package com.alllinkshare.catalogshopping.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.catalogshopping.R;
import com.alllinkshare.catalogshopping.api.API;
import com.alllinkshare.catalogshopping.api.config.Listeners;
import com.alllinkshare.catalogshopping.models.ProductDealer;
import com.alllinkshare.catalogshopping.ui.adapters.ComparisonAdapter;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GifImageView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingProductComparisonFragment extends BottomSheetDialogFragment {

    private int productId;

    private ComparisonAdapter comparisonAdapter;

    private LinearLayout loadingWrapper;
    private HorizontalScrollView contentWrapper;

    private View rootView;

    public ShoppingProductComparisonFragment() {
        // Required empty public constructor
    }

    public static ShoppingProductComparisonFragment newInstance(int productId) {
        ShoppingProductComparisonFragment fragment = new ShoppingProductComparisonFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.ITEM_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getInt(Keys.ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_shopping_product_comparison, container, false);

        initDealers();
        loadDealers();

        return rootView;
    }

    private void initDealers(){
        comparisonAdapter = new ComparisonAdapter(getActivity(), new ArrayList<ProductDealer>(), new ComparisonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductDealer dealer) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(dealer.getAction()));
                Objects.requireNonNull(getActivity()).startActivity(i);
            }

        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);

        verticalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(getContext(), R.drawable.divider)));
        rvItems.addItemDecoration(verticalDivider);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(comparisonAdapter);

        loadingWrapper = rootView.findViewById(R.id.loading_wrapper);
        contentWrapper = rootView.findViewById(R.id.content_wrapper);
    }

    private void loadDealers(){
        API.dealers(productId, new Listeners.DealersListener() {
            @Override
            public void onSuccess(List<ProductDealer> dealers) {
                comparisonAdapter.setData(dealers);

                contentWrapper.setVisibility(View.VISIBLE);
                loadingWrapper.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String error) {
                contentWrapper.setVisibility(View.VISIBLE);
                loadingWrapper.setVisibility(View.GONE);
            }
        });
    }
}