package com.alllinkshare.restaurant.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.restaurant.R;
import com.alllinkshare.restaurant.models.FoodMenuItem;
import com.bumptech.glide.Glide;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.ViewHolder> {

    private Context mContext;
    private List<FoodMenuItem> items;
    private OnItemClickListener listener;

    public FoodMenuAdapter(Context mContext, List<FoodMenuItem> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_menu_item_horizontal, viewGroup, false);

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

        private ImageView thumbnail, indicator;
        private TextView name, price, timing, dishType, extraTopping;
        private LinearLayout dishTypeWrapper;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            indicator = itemView.findViewById(R.id.indicator);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            timing = itemView.findViewById(R.id.timing);
            dishType = itemView.findViewById(R.id.dish_type);
            extraTopping = itemView.findViewById(R.id.extra_topping);

            dishTypeWrapper = itemView.findViewById(R.id.dish_type_wrapper);
        }

        public void bind(final FoodMenuItem menuItem, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(menuItem.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getProductOptions())
                    .into(thumbnail);

            name.setText(menuItem.getName());
            price.setText("₩"+menuItem.getPrice());

            if(menuItem.isExtraTopping()) extraTopping.setVisibility(View.VISIBLE);
            else extraTopping.setVisibility(View.GONE);

            if(menuItem.isOpen()){
                indicator.setBackgroundResource(R.drawable.indicator_emerald);
                timing.setVisibility(View.GONE);
            }
            else{
                indicator.setBackgroundResource(R.drawable.indicator_red);
                timing.setText(menuItem.getTimings());
                timing.setVisibility(View.VISIBLE);
            }
            if(TextUtils.isEmpty(menuItem.getDishType())){
                dishTypeWrapper.setVisibility(View.GONE);
            }
            else{
                dishTypeWrapper.setVisibility(View.VISIBLE);
                dishType.setText(menuItem.getDishType());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(menuItem);
                }
            });
        }
    }

    public void clearData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<FoodMenuItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<FoodMenuItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(FoodMenuItem foodMenuItem);
    }
}