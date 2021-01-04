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
import com.alllinkshare.catalog.models.Listing;
import com.alllinkshare.catalog.repos.ListingRepository;
import com.alllinkshare.catalog.ui.adapters.ListingsAdapter;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.FormType;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GifImageView;

import java.util.ArrayList;
import java.util.List;

public class ListingsFragment extends Fragment {

    private int categoryId;
    private int pageNumber = 0;

    private View rootView;
    private ListingsAdapter listingsAdapter;

    private LinearLayout loadingWrapper, emptyWrapper, contentWrapper;

    private boolean isLoading = false;

    public ListingsFragment() {
        // Required empty public constructor
    }

    public static ListingsFragment newInstance(int categoryID) {
        ListingsFragment fragment = new ListingsFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.PARENT_ID, categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(Keys.PARENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listings, container, false);

        initListings();
        loadListings();

        return rootView;
    }

    private void initListings(){
        listingsAdapter = new ListingsAdapter(getActivity(), new ArrayList<Listing>(), new ListingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Listing listing) {
                Coordinator.getCatalogNavigator().navigateToListing(listing.getId(), categoryId);
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
            public void onFormAction(int listingId, Listing.FormAction formAction) {
                switch (formAction.getType()){
                    case FormType.SHOPPING:
                    case FormType.FOOD_ORDER:
                    case FormType.HOSPITAL_APPOINTMENT:
                        Coordinator.getCatalogNavigator()
                                .navigateToListingCategories(
                                        formAction.getType(), listingId, categoryId);
                        break;

                    default:
                        Toast.makeText(getContext(), "Form #"+formAction.getId()+" "+formAction.getType(), Toast.LENGTH_LONG).show();
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

        isLoading = true;
        loadingWrapper.setVisibility(View.VISIBLE);

        ListingRepository.getInstance().getListings(categoryId, pageNumber, new ListingRepository.DataListReadyListener() {
            @Override
            public void onDataReady(int total, int currentPageNumber, List<Listing> listings) {
                pageNumber = currentPageNumber;

                listingsAdapter.addData(listings);

                loadingWrapper.setVisibility(View.GONE);
                isLoading = false;

                if(total < 1){
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