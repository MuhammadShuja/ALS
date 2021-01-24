package com.alllinkshare.user.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alllinkshare.user.R;
import com.alllinkshare.user.models.Order;

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

        private TextView reference;
        private TextView statusComplete;
        private TextView statusPending;
        private TextView statusActive;
        private TextView name;
        private TextView quantity;
        private TextView price;
        private TextView date;
        private Button btnTrackOrder;

        public ViewHolder(View itemView) {
            super(itemView);

            reference = itemView.findViewById(R.id.id);
            statusComplete = itemView.findViewById(R.id.status_complete);
            statusPending = itemView.findViewById(R.id.status_pending);
            statusActive = itemView.findViewById(R.id.status_active);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            date = itemView.findViewById(R.id.date);
            btnTrackOrder = itemView.findViewById(R.id.btn_track_order);
        }

        public void bind(final Order order, final OnItemClickListener listener) {
            reference.setText(String.valueOf(order.getId()));
            name.setText(order.getName());
            if(order.getStatus().equals("Pending")){
                statusPending.setVisibility(View.VISIBLE);
                statusComplete.setVisibility(View.GONE);
                statusActive.setVisibility(View.GONE);
            }
            else if(order.getStatus().equals("Active")){
                statusActive.setVisibility(View.VISIBLE);
                statusPending.setVisibility(View.GONE);
                statusComplete.setVisibility(View.GONE);
            }
            else{
                statusComplete.setVisibility(View.VISIBLE);
                statusPending.setVisibility(View.GONE);
                statusActive.setVisibility(View.GONE);
            }
            quantity.setText(String.valueOf(order.getQuantity()));
            price.setText(mContext.getResources().getString(R.string.currency_symbol)+order.getPrice());
            date.setText(order.getDate());

            if(order.getStatus().equals("Active"))
                btnTrackOrder.setVisibility(View.VISIBLE);
            else
                btnTrackOrder.setVisibility(View.GONE);

            btnTrackOrder.setOnClickListener(new View.OnClickListener() {
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