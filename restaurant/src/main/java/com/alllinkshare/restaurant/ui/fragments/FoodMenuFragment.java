package com.alllinkshare.restaurant.ui.fragments;

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

import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GifImageView;
import com.alllinkshare.restaurant.R;
import com.alllinkshare.restaurant.api.API;
import com.alllinkshare.restaurant.api.config.Listeners;
import com.alllinkshare.restaurant.models.FoodMenuItem;
import com.alllinkshare.restaurant.ui.adapters.FoodMenuAdapter;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.ui.fragments.CartBarFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodMenuFragment extends Fragment {

    private int listingId;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private FoodMenuAdapter foodMenuAdapter;

    private LinearLayout loadingWrapper, emptyWrapper, contentWrapper;

    private View rootView;

    public FoodMenuFragment() {
        // Required empty public constructor
    }

    public static FoodMenuFragment newInstance(int listingId) {
        FoodMenuFragment fragment = new FoodMenuFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.ITEM_ID, listingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listingId = getArguments().getInt(Keys.ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food_menu, container, false);

        initCartBar();
        initMenu();
        loadMenu();

        return rootView;
    }

    private void initCartBar(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.food_menu_cart_bar_host, CartBarFragment.newInstance(Cart.TYPE_FOOD))
                .commit();
    }

    private void initMenu(){
        foodMenuAdapter = new FoodMenuAdapter(getActivity(), new ArrayList<FoodMenuItem>(), new FoodMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodMenuItem foodMenuItem) {
                Coordinator.getRestaurantNavigator().navigateToItem(foodMenuItem.getId());
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
        rvItems.setAdapter(foodMenuAdapter);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMenu();
                }
            }
        });

        loadingWrapper = rootView.findViewById(R.id.loading_wrapper);
        emptyWrapper = rootView.findViewById(R.id.empty_wrapper);
        contentWrapper = rootView.findViewById(R.id.content_wrapper);

        GifImageView gifImageView = rootView.findViewById(R.id.gif_image_view);
        gifImageView.setGifImageResource(R.drawable.empty);
    }

    private void loadMenu(){
        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;
        loadingWrapper.setVisibility(View.VISIBLE);

        API.menu(listingId, currentPage, new Listeners.MenuListener() {
            @Override
            public void onSuccess(List<FoodMenuItem> menuItems, int currentPageNumber, int lastPageNumber, int totalMenuItems) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalMenuItems;

                foodMenuAdapter.addData(menuItems);

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