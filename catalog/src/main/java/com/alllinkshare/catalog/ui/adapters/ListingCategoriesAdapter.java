package com.alllinkshare.catalog.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.ListingCategory;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;

import java.util.List;

public class ListingCategoriesAdapter extends RecyclerView.Adapter<ListingCategoriesAdapter.ViewHolder> {

    private Context mContext;
    private List<ListingCategory> items;
    private OnItemClickListener listener;

    public ListingCategoriesAdapter(Context mContext, List<ListingCategory> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_listing_category, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(final ListingCategory listingCategory, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(listingCategory.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getDefault())
                    .into(thumbnail);

            name.setText(listingCategory.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(listingCategory);
                }
            });
        }
    }

    public void clearData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<ListingCategory> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<ListingCategory> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ListingCategory listingCategory);
    }
}