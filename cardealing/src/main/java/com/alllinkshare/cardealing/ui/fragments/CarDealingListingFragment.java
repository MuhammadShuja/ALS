package com.alllinkshare.cardealing.ui.fragments;

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

import com.alllinkshare.cardealing.R;
import com.alllinkshare.cardealing.api.API;
import com.alllinkshare.cardealing.api.config.Listeners;
import com.alllinkshare.cardealing.models.Car;
import com.alllinkshare.cardealing.ui.adapters.CarsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarDealingListingFragment extends Fragment {

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private CarsAdapter carsAdapter;
    private LinearLayout loadingWrapper;

    private View rootView;

    public CarDealingListingFragment() {
        // Required empty public constructor
    }

    public static CarDealingListingFragment newInstance() {
        CarDealingListingFragment fragment = new CarDealingListingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_car_dealing_listing, container, false);

        initListings();
        loadListings();

        return rootView;
    }

    private void initListings(){
        carsAdapter = new CarsAdapter(getActivity(), new ArrayList<Car>(), new CarsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Car car) {
//                Coordinator.getShoppingNavigator().navigateToProduct(car.getId());
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
        rvItems.setAdapter(carsAdapter);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    loadListings();
                }
            }
        });

        loadingWrapper = rootView.findViewById(R.id.loading_wrapper);
    }

    private void loadListings(){
        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;
        loadingWrapper.setVisibility(View.VISIBLE);

        API.cars(currentPage, new Listeners.CarsListener() {
            @Override
            public void onSuccess(List<Car> cars, int currentPageNumber, int lastPageNumber, int totalCars) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalCars;

                carsAdapter.addData(cars);

                loadingWrapper.setVisibility(View.GONE);
                isLoading = false;
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