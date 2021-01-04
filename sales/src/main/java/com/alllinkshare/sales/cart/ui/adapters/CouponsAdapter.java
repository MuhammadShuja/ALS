package com.alllinkshare.sales.cart.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.sales.R;
import com.alllinkshare.sales.cart.models.Coupon;
import com.bumptech.glide.Glide;

import java.util.List;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.ViewHolder> {

    private Context mContext;
    private List<Coupon> items;
    private OnItemClickListener listener;

    private CheckBox lastSelection = null;

    public CouponsAdapter(Context mContext, List<Coupon> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_coupon, viewGroup, false);

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

        private TextView description;
        private TextView seller;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description);
            seller = itemView.findViewById(R.id.seller);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        public void bind(final Coupon coupon, final OnItemClickListener listener) {
            description.setText(coupon.getDescription());
            seller.setText(coupon.getSellerName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                    }
                    else{
                        checkBox.setChecked(true);
                        listener.onItemClick(coupon);
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

    public void setData(List<Coupon> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Coupon> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Coupon coupon);
    }
}