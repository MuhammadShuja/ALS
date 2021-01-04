package com.alllinkshare.user.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.alllinkshare.core.utils.Swal;
import com.alllinkshare.user.R;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.FavouriteItem;
import com.alllinkshare.user.ui.adapters.FavouritesAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private FavouritesAdapter favouritesAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private ProgressBar progressBar;
    private LinearLayout sectionEmpty, sectionData;

    private Swal swal;

    private View rootView;

    public FavouritesFragment() {
    }

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        swal = new Swal(getContext());

        initSwipeRefreshLayout();
        initFavourites();
        loadData();

        return rootView;
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
                totalItems = 0;
                isLoading = false;
                favouritesAdapter.clearData();
                loadData();
            }
        });
    }

    private void initFavourites(){
        favouritesAdapter = new FavouritesAdapter(getContext(), new ArrayList<FavouriteItem>(), new FavouritesAdapter.OnItemClickListener() {
            @Override
            public void onViewItem(FavouriteItem item) {
                Toast.makeText(getContext(), "View item", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRemoveItem(FavouriteItem item) {
                swal.progress("Removing item");
                API.removeFavourite(item.getId(), new Listeners.RemoveFavouriteListener() {
                    @Override
                    public void onSuccess(String message) {
                        swal.success(message);
                        currentPage = 0;
                        lastPage = 1;
                        totalItems = 0;
                        isLoading = false;
                        favouritesAdapter.clearData();
                        loadData();
                    }

                    @Override
                    public void onFailure(String error) {
                        swal.error(error);
                    }
                });
            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(favouritesAdapter);

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

        API.favourites(currentPage, new Listeners.FavouritesListener() {
            @Override
            public void onSuccess(List<FavouriteItem> itemList, int currentPageNumber, int lastPageNumber, int totalItemCount) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalItemCount;

                favouritesAdapter.addData(itemList);

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