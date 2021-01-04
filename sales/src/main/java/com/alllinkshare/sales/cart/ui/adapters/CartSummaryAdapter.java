package com.alllinkshare.sales.cart.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.sales.R;
import com.alllinkshare.sales.cart.models.CartItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class CartSummaryAdapter extends RecyclerView.Adapter<CartSummaryAdapter.ViewHolder> {

    private Context mContext;
    private List<CartItem> items;
    private OnItemClickListener listener;

    public CartSummaryAdapter(Context mContext, List<CartItem> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_order_item, viewGroup, false);

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

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumbnail;
        private TextView name;
        private TextView quantity;
        private TextView price;
        private TextView subTotal;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.item_price);
            subTotal = itemView.findViewById(R.id.item_sub_total);
        }

        public void bind(final CartItem item, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(item.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getDefault())
                    .into(thumbnail);

            name.setText(item.getName());
            quantity.setText(String.valueOf(item.getQuantity()));
            price.setText("₩" + item.getPrice());
            subTotal.setText("₩" + item.getSubTotal());

        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<CartItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<CartItem> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(CartItem cartItem);
    }
}