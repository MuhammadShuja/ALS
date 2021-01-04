package com.alllinkshare.user.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alllinkshare.user.R;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.Coupon;
import com.alllinkshare.user.ui.activities.EditCouponActivity;
import com.alllinkshare.user.ui.adapters.CouponsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouponFragment extends Fragment {

    private final int EDIT_COUPON_REQUEST = 111;

    private CouponsAdapter couponsAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int currentPage = 0;
    private int lastPage = 1;
    private boolean isLoading = false;

    private ProgressBar progressBar;
    private LinearLayout sectionEmpty, sectionData;

    private View rootView;
    public CouponFragment() {
        // Required empty public constructor
    }

    public static CouponFragment newInstance() {
        return new CouponFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_coupon, container, false);

        initSwipeRefreshLayout();
        initCoupons();
        loadData();

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_COUPON_REQUEST){
            currentPage = 0;
            lastPage = 1;
            isLoading = false;
            couponsAdapter.clearData();
            loadData();
        }
    }

    private void initSwipeRefreshLayout(){
        mSwipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        int[] indicatorColorArray = {R.color.emerald, R.color.gold};
        mSwipeRefreshLayout.setColorSchemeResources(indicatorColorArray);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                lastPage = 1;
                isLoading = false;
                couponsAdapter.clearData();
                loadData();
            }
        });
    }

    private void initCoupons(){
        couponsAdapter = new CouponsAdapter(getContext(), new ArrayList<Coupon>(), new CouponsAdapter.OnItemClickListener() {
            @Override
            public void onClick(Coupon coupon) {
                Intent intent = new Intent(getActivity(), EditCouponActivity.class);
                intent.putExtra("coupon_id", coupon.getId());
                startActivityForResult(intent, EDIT_COUPON_REQUEST);
            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(couponsAdapter);

        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    loadData();
                }
            }
        });

        progressBar = rootView.findViewById(R.id.progress_bar);
        sectionEmpty = rootView.findViewById(R.id.empty_wrapper);
        sectionData = rootView.findViewById(R.id.data_wrapper);
    }

    private void loadData() {
        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        API.coupons(currentPage, new Listeners.CouponsListener() {
            @Override
            public void onSuccess(List<Coupon> itemList, int currentPageNumber, int lastPageNumber, int totalItemCount) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;

                couponsAdapter.addData(itemList);

                progressBar.setVisibility(View.GONE);
                isLoading = false;
                if(mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);

                if(totalItemCount < 1){
                    sectionEmpty.setVisibility(View.VISIBLE);
                    sectionData.setVisibility(View.GONE);
                }
                else{
                    sectionEmpty.setVisibility(View.GONE);
                    sectionData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }
}