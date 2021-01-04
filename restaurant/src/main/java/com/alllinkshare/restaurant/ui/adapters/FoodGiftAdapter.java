package com.alllinkshare.restaurant.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.restaurant.R;
import com.alllinkshare.restaurant.models.FoodFreeGift;
import com.bumptech.glide.Glide;

import java.util.List;

public class FoodGiftAdapter extends RecyclerView.Adapter<FoodGiftAdapter.ViewHolder> {

    private Context mContext;
    private List<FoodFreeGift> items;
    private OnItemClickListener listener;

    private CheckBox lastSelection = null;

    public FoodGiftAdapter(Context mContext, List<FoodFreeGift> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_free_gift_horizontal, viewGroup, false);

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
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        public void bind(final FoodFreeGift gift, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(gift.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getProductOptions())
                    .into(thumbnail);

            name.setText(gift.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                    }
                    else{
                        checkBox.setChecked(true);
                        listener.onItemClick(gift);
                    }
                    if(lastSelection != null
                        && lastSelection != checkBox){
                        lastSelection.setChecked(false);
                    }
                    lastSelection = checkBox;
                }
            });
        }
    }

    public void clearData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<FoodFreeGift> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<FoodFreeGift> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(FoodFreeGift gift);
    }
}