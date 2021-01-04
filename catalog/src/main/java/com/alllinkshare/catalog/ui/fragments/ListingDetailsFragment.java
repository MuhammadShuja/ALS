package com.alllinkshare.catalog.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Listing;
import com.alllinkshare.catalog.repos.ListingRepository;
import com.alllinkshare.catalog.ui.adapters.FormActionsAdapter;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.FormType;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.core.utils.MaterialColorGenerator;
import com.alllinkshare.core.utils.Swal;
import com.alllinkshare.gallery.models.Image;
import com.alllinkshare.gallery.ui.fragments.GalleryGridFragment;
import com.alllinkshare.reviews.models.Rating;
import com.alllinkshare.reviews.models.Review;
import com.alllinkshare.reviews.ui.fragments.ReviewsFragment;
import com.bumptech.glide.Glide;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListingDetailsFragment extends Fragment {
    private int listingId;
    private int categoryId;

    private ImageView thumbnail;
    private TextView name, companyName, phoneNumber, businessAddress, address,
            businessInformation, history, crewExperience, greetings, eventNews;
    private RecyclerView rvActions;

    private View rootView;
    private Swal swal;
    private Context context;

    public ListingDetailsFragment() {
        // Required empty public constructor
    }

    public static ListingDetailsFragment newInstance(int listingId, int categoryId) {
        ListingDetailsFragment fragment = new ListingDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.ITEM_ID, listingId);
        args.putInt(Keys.PARENT_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listingId = getArguments().getInt(Keys.ITEM_ID);
            categoryId = getArguments().getInt(Keys.PARENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listing_details, container, false);

        initDialog();
        initListing();
        loadData();

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initDialog(){
        swal = new Swal(getContext());
    }

    private void initListing(){
        thumbnail = rootView.findViewById(R.id.thumbnail);
        name = rootView.findViewById(R.id.name);
        companyName = rootView.findViewById(R.id.company_name);
        businessAddress = rootView.findViewById(R.id.business_address);
        address = rootView.findViewById(R.id.address);

        businessInformation = rootView.findViewById(R.id.business_information);
        history = rootView.findViewById(R.id.history);
        crewExperience = rootView.findViewById(R.id.crew_experience);
        greetings = rootView.findViewById(R.id.greetings);
        eventNews = rootView.findViewById(R.id.event_news);

        rvActions = rootView.findViewById(R.id.rv_actions);
    }

    private void loadData(){
        ListingRepository.getInstance().getListing(listingId, categoryId, new ListingRepository.DataReadyListener() {
            @Override
            public void onDataReady(Listing listing) {
                initCoupon(listing.getCoupon());
                initGallery(listing.getImages());
                initReviews(listing.getRating(), listing.getReviews());

                Glide
                        .with(context)
                        .load(listing.getThumbnail())
                        .apply(GlideOptions.getDefault())
                        .into(thumbnail);

                name.setText(listing.getName());
                companyName.setText(listing.getCompanyName());
                businessAddress.setText(listing.getBusinessAddress());
                address.setText(listing.getAddress());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    businessInformation.setText(Html.fromHtml(listing.getBusinessInformation(), Html.FROM_HTML_MODE_COMPACT));
                    history.setText(Html.fromHtml(listing.getHistory(), Html.FROM_HTML_MODE_COMPACT));
                    crewExperience.setText(Html.fromHtml(listing.getCrewExperience(), Html.FROM_HTML_MODE_COMPACT));
                    greetings.setText(Html.fromHtml(listing.getGreetings(), Html.FROM_HTML_MODE_COMPACT));
                    eventNews.setText(Html.fromHtml(listing.getEventNews(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    businessInformation.setText(Html.fromHtml(listing.getBusinessInformation()));
                    history.setText(Html.fromHtml(listing.getHistory()));
                    crewExperience.setText(Html.fromHtml(listing.getCrewExperience()));
                    greetings.setText(Html.fromHtml(listing.getGreetings()));
                    eventNews.setText(Html.fromHtml(listing.getEventNews()));
                }

                initActions(listing.getActions());
                initContactOptions(listing.getContactOptions());
                initFreeServices(listing.getFreeServices());
                initKeywords(listing.getKeywords());

                rootView.findViewById(R.id.loading_wrapper).setVisibility(View.GONE);
                rootView.findViewById(R.id.content_wrapper).setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String error) {
                swal.error(error);
            }
        });
    }

    private void initCoupon(String coupon){
        if(!TextUtils.isEmpty(coupon)){
            rootView.findViewById(R.id.qr_wrapper).setVisibility(View.VISIBLE);

            ((TextView) rootView.findViewById(R.id.qr_offer)).setText(coupon);
        }
    }

    private void initGallery(ArrayList<Image> images){
        try {
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.gallery_fragment, GalleryGridFragment.newInstance(images, 3))
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initActions(final List<Listing.FormAction> actions){
        List<Listing.FormAction> formActions = new ArrayList<>(actions);
        formActions.add(new Listing.FormAction(-1, "Best Deal", "BEST_DEAL"));
        int spanCount = 2;
        if(formActions.size() > 8) spanCount = 3;
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        DividerItemDecoration horizontalDivider = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);

        horizontalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(getContext(), R.drawable.divider_white)));
        verticalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(getContext(), R.drawable.divider_white)));
        rvActions.addItemDecoration(horizontalDivider);
        rvActions.addItemDecoration(verticalDivider);

        rvActions.setLayoutManager(layoutManager);
        rvActions.setAdapter(new FormActionsAdapter(getContext(), formActions, new FormActionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Listing.FormAction formAction) {
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
        }));
    }

    private void initContactOptions(final Listing.ContactOptions contactOptions){
        phoneNumber = rootView.findViewById(R.id.phone_number);
        phoneNumber.setText(contactOptions.getPhone());

        rootView.findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contactOptions.getPhone()));
                ((AppCompatActivity) context).startActivity(intent);
            }
        });
    }

    private void initFreeServices(List<String> services){
        if(services.size() < 1){
            rootView.findViewById(R.id.services_wrapper).setVisibility(View.GONE);
            return;
        }

        TagView freeServices = (TagView) rootView.findViewById(R.id.free_services);
        ArrayList<Tag> tags = new ArrayList<>();
        for(String service : services){
            if(service.trim().isEmpty()) continue;
            Tag tag = new Tag(service);
            tag.tagTextColor = context.getResources().getColor(R.color.white);
            tag.layoutColor = MaterialColorGenerator.MATERIAL.getRandomColor();
            tags.add(tag);
        }
        freeServices.addTags(tags);
    }

    private void initKeywords(List<String> keywords){
        if(keywords.size() < 1){
            rootView.findViewById(R.id.keywords_wrapper).setVisibility(View.GONE);
            return;
        }

        TagView keywordsGroup = (TagView) rootView.findViewById(R.id.keywords);
        ArrayList<Tag> tags = new ArrayList<>();
        for(String keyword : keywords){
            if(keyword.trim().isEmpty()) continue;
            Tag tag = new Tag(keyword);
            tag.tagTextColor = context.getResources().getColor(R.color.white);
            tag.layoutColor = MaterialColorGenerator.MATERIAL.getRandomColor();
            tags.add(tag);
        }
        keywordsGroup.addTags(tags);
    }

    private void initReviews(Rating rating, ArrayList<Review> reviews){
        ((ProgressBar) rootView.findViewById(R.id.rating_progress)).setProgress((int) rating.getRating());
        ((TextView) rootView.findViewById(R.id.rating)).setText(rating.getRating()+"");
        ((TextView) rootView.findViewById(R.id.reviews_count)).setText(rating.getTotal()+"");

        ReviewsFragment fragment = ReviewsFragment.newInstance(rating, reviews);
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                .add(R.id.reviews_fragment, fragment)
                .commit();
    }
}