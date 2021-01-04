package com.alllinkshare.user.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alllinkshare.user.R;
import com.alllinkshare.user.models.Order;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context mContext;
    private List<Order> orders;
    private OnItemClickListener listener;

    public OrdersAdapter(Context mContext, List<Order> orders, OnItemClickListener listener) {
        this.mContext = mContext;
        this.orders = orders;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_order_horizontal, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(orders.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView statusIcon;
        private TextView reference;
        private TextView date;
        private TextView status;
        private TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);

            statusIcon = itemView.findViewById(R.id.coupon_icon);
            reference = itemView.findViewById(R.id.reference);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.status);
        }

        public void bind(final Order order, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(mContext.getResources().getIdentifier(order.getStatusIcon(), "drawable", mContext.getPackageName()))
                    .into(statusIcon);

            reference.setText(order.getReference());
            date.setText(order.getDate());
            status.setText(order.getStatus());
            amount.setText(String.valueOf(order.getTotalPrice()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(order);
                }
            });
        }
    }

    public void clearData(){
        this.orders.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }

    public void addData(List<Order> orders){
        this.orders.addAll(orders);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Order order);
    }
}