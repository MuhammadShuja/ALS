package com.alllinkshare.sales.cart.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.sales.R;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.models.CartItem;
import com.bumptech.glide.Glide;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private List<CartItem> items;
    private OnItemClickListener listener;

    public CartAdapter(Context mContext, List<CartItem> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_cart_item, viewGroup, false);

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

        private ImageView thumbnail, btnMinus, btnPlus, btnRemove;
        private TextView name;
        private TextView quantity;
        private TextView price;
        private TextView subTotal;
        private TextView freeGifts;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            btnMinus = itemView.findViewById(R.id.btn_quantity_minus);
            btnPlus = itemView.findViewById(R.id.btn_quantity_plus);
            btnRemove = itemView.findViewById(R.id.btn_remove);
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
            price.setText("₩" + item.getSubTotal());

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCounterPlus(item);
                    item.increaseQuantity(1);
                    quantity.setText(String.valueOf(item.getQuantity()));
                    price.setText("₩" + item.getSubTotal());
                }
            });
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getQuantity() > 1){
                        listener.onCounterMinus(item);
                        item.decreaseQuantity(1);
                        quantity.setText(String.valueOf(item.getQuantity()));
                        price.setText("₩" + item.getSubTotal());
                    }
                }
            });
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemRemove(item);
                }
            });

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
        void onCounterPlus(CartItem cartItem);
        void onCounterMinus(CartItem cartItem);
        void onItemRemove(CartItem cartItem);
    }
}