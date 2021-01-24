package com.alllinkshare.catalog.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Listing;
import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.core.utils.MaterialColorGenerator;
import com.bumptech.glide.Glide;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.ViewHolder> {

    private Context mContext;
    private List<Listing> items;
    private ListingsAdapter.OnItemClickListener listener;

    public ListingsAdapter(Context mContext, List<Listing> items, ListingsAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_listing_horizontal, viewGroup, false);

        return new ListingsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListingsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumbnail, iconOpen, iconVerified, iconFranchise;
        private TextView name;
        private TextView rating, ratingCount;
        private TagView freeServices;
        private LinearLayout couponWrapper, priceLevelWrapper;
        private TextView coupon;
        private ImageView btnReview, btnPhone, btnVoiceCall, btnVideoCall, btnChat;
        private RatingBar ratingBar;
        private ImageView priceLevelLow, priceLevelMedium, priceLevelHigh;
        private RecyclerView rvItems;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            iconOpen = itemView.findViewById(R.id.icon_open);
            iconVerified = itemView.findViewById(R.id.icon_verified);
            iconFranchise = itemView.findViewById(R.id.icon_franchise);
            btnReview = itemView.findViewById(R.id.btn_review);
            name = itemView.findViewById(R.id.name);
            freeServices = (TagView) itemView.findViewById(R.id.free_services);
            rating = itemView.findViewById(R.id.rating);
            ratingCount = itemView.findViewById(R.id.rating_count);
            couponWrapper = itemView.findViewById(R.id.coupon_wrapper);
            coupon = itemView.findViewById(R.id.coupon);
            btnReview = itemView.findViewById(R.id.btn_review);
            btnPhone = itemView.findViewById(R.id.btn_phone);
            btnVoiceCall = itemView.findViewById(R.id.btn_voice_call);
            btnVideoCall = itemView.findViewById(R.id.btn_video_call);
            btnChat = itemView.findViewById(R.id.btn_chat);

            ratingBar = itemView.findViewById(R.id.rating_bar);

            priceLevelWrapper = itemView.findViewById(R.id.price_level_wrapper);
            priceLevelLow = itemView.findViewById(R.id.price_level_low);
            priceLevelMedium = itemView.findViewById(R.id.price_level_medium);
            priceLevelHigh = itemView.findViewById(R.id.price_level_high);

            rvItems = itemView.findViewById(R.id.rv_items);
        }

        public void bind(final Listing listing, final ListingsAdapter.OnItemClickListener listener) {

//            BLOCK 1
            Glide
                    .with(mContext)
                    .load(listing.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getDefault())
                    .into(thumbnail);

            if ((listing.isOpen())) {
                iconOpen.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.emerald),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                iconOpen.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.gold),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            }

            if ((listing.isFranchise())) {
                iconFranchise.setVisibility(View.VISIBLE);
            } else {
                iconFranchise.setVisibility(View.GONE);
            }

            if ((listing.isVerified())) {
                iconVerified.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.emerald),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                iconVerified.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.grey),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            }

//            BLOCK 2

            name.setText(listing.getName());

            ratingBar.setRating(listing.getRating().getRating());
            rating.setText(String.valueOf(listing.getRating().getRating()));
            if(listing.getRating().getTotal() < 1){
                ratingCount.setVisibility(View.GONE);
            }
            else{
                ratingCount.setText("("+listing.getRating().getTotal()+")");
                ratingCount.setVisibility(View.VISIBLE);
            }

            ArrayList<Tag> tags = new ArrayList<>();
            for(String service : listing.getFreeServices()){
                if(service.trim().isEmpty()) continue;
                Tag tag = new Tag(service);
                tag.tagTextColor = mContext.getResources().getColor(R.color.white);
                tag.layoutColor = MaterialColorGenerator.MATERIAL.getRandomColor();
                tag.tagTextSize = 8.0f;
                tags.add(tag);
                if(tags.size() == 2) break;
            }
            freeServices.addTags(tags);

            if(listing.getCoupon().isEmpty()){
                couponWrapper.setVisibility(View.GONE);
            } else{
                couponWrapper.setVisibility(View.VISIBLE);
                coupon.setText(listing.getCoupon());
            }

            if(listing.getPriceLevel() != null){
                priceLevelWrapper.setVisibility(View.VISIBLE);
                switch (listing.getPriceLevel()){
                    case "low":
                        priceLevelLow.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.emerald),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        break;
                    case "medium":
                        priceLevelLow.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.emerald),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        priceLevelMedium.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.emerald),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        break;
                    case "high":
                        priceLevelLow.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.emerald),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        priceLevelMedium.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.emerald),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        priceLevelHigh.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.emerald),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        break;
                }
            } else{
                priceLevelWrapper.setVisibility(View.GONE);
            }

            btnReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onWriteReview(listing);
                }
            });

            if(listing.getContactOptions().getMobile().isEmpty()){
                btnPhone.setVisibility(View.GONE);
            }
            else{
                btnPhone.setVisibility(View.VISIBLE);
            }
            btnPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStartPhoneCall(listing);
                }
            });

            if(listing.getContactOptions().getPhone().isEmpty()){
                btnVoiceCall.setVisibility(View.GONE);
            }
            else{
                btnVoiceCall.setVisibility(View.VISIBLE);
            }
            btnVoiceCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStartVoiceCall(listing);
                }
            });

            if(listing.getContactOptions().isVideoAllowed()){
                btnVideoCall.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.emerald),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            } else{
                btnVideoCall.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.red),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            }
            btnVideoCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStartVideoCall(listing);
                }
            });

            if(listing.getContactOptions().isTextAllowed()){
                btnChat.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.emerald),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            } else{
                btnChat.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.red),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            }
            btnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStartChat(listing);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(listing);
                }
            });

//            BLOCK 3
            List<Listing.FormAction> formActions = new ArrayList<>(listing.getActions());
            formActions.add(new Listing.FormAction(-1, "Best Deal", "BEST_DEAL"));
            final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
            DividerItemDecoration verticalDivider = new DividerItemDecoration(
                    mContext, DividerItemDecoration.VERTICAL);

            verticalDivider.setDrawable(
                    Objects.requireNonNull(
                            ContextCompat.getDrawable(mContext, R.drawable.divider_white)));
            rvItems.addItemDecoration(verticalDivider);
            rvItems.setLayoutManager(layoutManager);
            rvItems.setAdapter(new FormActionsAdapter(mContext, formActions, new FormActionsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Listing.FormAction formAction) {
                    listener.onFormAction(listing, formAction);
                }
            }));
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Listing> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Listing> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Listing listing);
        void onWriteReview(Listing listing);
        void onStartPhoneCall(Listing listing);
        void onStartVoiceCall(Listing listing);
        void onStartVideoCall(Listing listing);
        void onStartChat(Listing listing);
        void onFormAction(Listing listing, Listing.FormAction formAction);
    }
}