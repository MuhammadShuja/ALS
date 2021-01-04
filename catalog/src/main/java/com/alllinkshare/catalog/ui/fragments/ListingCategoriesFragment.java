package com.alllinkshare.catalog.ui.fragments;

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

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.api.API;
import com.alllinkshare.catalog.api.config.Listeners;
import com.alllinkshare.catalog.models.ListingCategory;
import com.alllinkshare.catalog.ui.adapters.ListingCategoriesAdapter;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.FormType;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GifImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListingCategoriesFragment extends Fragment {

    private String type;
    private int listingId;
    private int categoryId;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private ListingCategoriesAdapter listingCategoriesAdapter;

    private LinearLayout loadingWrapper, emptyWrapper, contentWrapper;

    private View rootView;

    public ListingCategoriesFragment() {
        // Required empty public constructor
    }

    public static ListingCategoriesFragment newInstance(String type, int listingId, int categoryId) {
        ListingCategoriesFragment fragment = new ListingCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(Keys.TYPE, type);
        args.putInt(Keys.ITEM_ID, listingId);
        args.putInt(Keys.PARENT_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(Keys.TYPE);
            listingId = getArguments().getInt(Keys.ITEM_ID);
            categoryId = getArguments().getInt(Keys.PARENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listing_categories, container, false);

        initCategories();
        loadCategories();

        return rootView;
    }

    private void initCategories(){
        listingCategoriesAdapter = new ListingCategoriesAdapter(getActivity(), new ArrayList<ListingCategory>(), new ListingCategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListingCategory listingCategory) {
                switch (type){
                    case FormType.SHOPPING:
                        Coordinator.getShoppingNavigator().navigateToProducts(listingId, listingCategory.getId(), listingCategory.getParentId());
                        break;

                    case FormType.FOOD_ORDER:
                        Coordinator.getRestaurantNavigator().navigateToMenu(listingId);
                        break;
                }
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
        rvItems.setAdapter(listingCategoriesAdapter);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    loadCategories();
                }
            }
        });

        loadingWrapper = rootView.findViewById(R.id.loading_wrapper);
        emptyWrapper = rootView.findViewById(R.id.empty_wrapper);
        contentWrapper = rootView.findViewById(R.id.content_wrapper);

        GifImageView gifImageView = rootView.findViewById(R.id.gif_image_view);
        gifImageView.setGifImageResource(R.drawable.empty);
    }

    private void loadCategories(){
        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;
        loadingWrapper.setVisibility(View.VISIBLE);

        API.listingCategories(type, listingId, currentPage, new Listeners.ListingCategoriesListener() {
            @Override
            public void onSuccess(List<ListingCategory> categories, int currentPageNumber, int lastPageNumber, int totalCategories) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalCategories;

                listingCategoriesAdapter.addData(categories);

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