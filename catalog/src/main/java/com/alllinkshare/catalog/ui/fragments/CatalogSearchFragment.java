package com.alllinkshare.catalog.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.alllinkshare.catalog.models.Listing;
import com.alllinkshare.catalog.repos.ListingRepository;
import com.alllinkshare.catalog.ui.adapters.ListingsAdapter;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.FormType;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GifImageView;

import java.util.ArrayList;
import java.util.List;

public class CatalogSearchFragment extends Fragment {

    private String query;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private View rootView;
    private ListingsAdapter listingsAdapter;

    private LinearLayout loadingWrapper, emptyWrapper, contentWrapper;

    public CatalogSearchFragment() {
        // Required empty public constructor
    }

    public static CatalogSearchFragment newInstance(String query) {
        CatalogSearchFragment fragment = new CatalogSearchFragment();
        Bundle args = new Bundle();
        args.putString(Keys.STRING, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(Keys.STRING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_catalog_search, container, false);

        initListings();
        loadListings();

        return rootView;
    }

    private void initListings(){
        listingsAdapter = new ListingsAdapter(getActivity(), new ArrayList<Listing>(), new ListingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Listing listing) {
                Coordinator.getCatalogNavigator().navigateToListing(listing.getId(), -1);
            }

            @Override
            public void onWriteReview(Listing listing) {
                Toast.makeText(getContext(), "Write review clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartPhoneCall(Listing listing) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+listing.getContactOptions().getMobile()));
                getActivity().startActivity(intent);
            }

            @Override
            public void onStartVoiceCall(Listing listing) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+listing.getContactOptions().getPhone()));
                getActivity().startActivity(intent);
            }

            @Override
            public void onStartVideoCall(Listing listing) {
                Toast.makeText(getContext(), "Start video call clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartChat(Listing listing) {
                Toast.makeText(getContext(), "Start chat clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFormAction(Listing listing, Listing.FormAction formAction) {
                switch (formAction.getType()){
                    case FormType.SHOPPING:
                    case FormType.FOOD_ORDER:
                    case FormType.HOSPITAL_APPOINTMENT:
                        Coordinator.getCatalogNavigator()
                                .navigateToListingCategories(
                                        formAction.getType(), listing.getId(), -1);
                        break;

                    default:
                        BookingFormFragment.newInstance(listing.getName(), -1)
                                .show(getParentFragmentManager(), "dialog");
                }
            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(listingsAdapter);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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

        loadingWrapper = rootView.findViewById(R.id.loading_wrapper_listings);
        emptyWrapper = rootView.findViewById(R.id.empty_wrapper);
        contentWrapper = rootView.findViewById(R.id.content_wrapper);

        GifImageView gifImageView = rootView.findViewById(R.id.gif_image_view);
        gifImageView.setGifImageResource(R.drawable.empty);
    }

    private void loadListings(){
        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;
        loadingWrapper.setVisibility(View.VISIBLE);

        API.search(currentPage, query, new Listeners.ListingsListener(){

            @Override
            public void onSuccess(List<Listing> listings, int currentPageNumber, int lastPageNumber, int totalListings) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalListings;

                listingsAdapter.addData(listings);

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

    private void reloadListings() {
        currentPage = 0;
        isLoading = false;
        listingsAdapter.clearData();
        loadListings();
    }
}