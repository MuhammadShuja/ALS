package com.alllinkshare.user.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.user.R;
import com.alllinkshare.user.models.Coupon;

import java.util.List;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.ViewHolder> {

    private Context mContext;
    private List<Coupon> items;
    private OnItemClickListener listener;

    public CouponsAdapter(Context mContext, List<Coupon> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_coupon_horizontal, viewGroup, false);

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

        private TextView status;
        private TextView company;
        private TextView category;
        private TextView code;
        private TextView type;
        private TextView discount;
        private TextView amount;
        private TextView formDays;
        private Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            company = itemView.findViewById(R.id.company);
            category = itemView.findViewById(R.id.category);
            code = itemView.findViewById(R.id.code);
            type = itemView.findViewById(R.id.outlined_exposed_dropdown_editable);
            discount = itemView.findViewById(R.id.discount);
            amount = itemView.findViewById(R.id.amount);
            formDays = itemView.findViewById(R.id.days);
            btnEdit = itemView.findViewById(R.id.btn_edit_coupon);
        }

        public void bind(final Coupon item, final OnItemClickListener listener) {
            status.setText(item.getStatus());
            company.setText(item.getCompany());
            category.setText(item.getCategory());
            code.setText(item.getCode());
            type.setText(item.getType());
            discount.setText(item.getDiscount());
            amount.setText(item.getAmount());
            formDays.setText(item.getFormDays());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Coupon> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Coupon> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onClick(Coupon item);
    }
}