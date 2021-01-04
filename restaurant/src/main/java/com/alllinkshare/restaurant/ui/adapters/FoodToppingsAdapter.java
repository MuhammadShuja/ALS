package com.alllinkshare.restaurant.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.restaurant.R;
import com.alllinkshare.restaurant.models.FoodTopping;
import com.alllinkshare.sales.Sales;
import com.alllinkshare.sales.cart.models.CartItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class FoodToppingsAdapter extends RecyclerView.Adapter<FoodToppingsAdapter.ViewHolder> {

    private Context mContext;
    private List<FoodTopping> items;
    private OnItemClickListener listener;

    private String uuid;

    public FoodToppingsAdapter(Context mContext, List<FoodTopping> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_extra_topping_horizontal, viewGroup, false);

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

        private ImageView thumbnail, indicator, btnPlus, btnMinus;
        private TextView name, price, counter;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            indicator = itemView.findViewById(R.id.indicator);
            btnPlus = itemView.findViewById(R.id.btn_topping_plus);
            btnMinus = itemView.findViewById(R.id.btn_topping_minus);
            indicator = itemView.findViewById(R.id.indicator);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            counter = itemView.findViewById(R.id.topping_counter);
        }

        public void bind(final FoodTopping topping, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(topping.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getProductOptions())
                    .into(thumbnail);

            name.setText(topping.getName());
            price.setText("₩"+topping.getPrice());

            if(topping.isAvailable()){
                indicator.setBackgroundResource(R.drawable.indicator_emerald);
            }
            else{
                indicator.setBackgroundResource(R.drawable.indicator_red);
            }

            btnPlus.setOnClickListener(v -> {
                if(Sales.getFoodCart().findItem(uuid) == null){
                    Toast.makeText(mContext, "제품수량을 선택해주세요", Toast.LENGTH_LONG).show();
                }
                else{
                    listener.onCounterPlus(topping);

                    int count = Integer.valueOf(counter.getText().toString()) + 1;
                    counter.setText(String.valueOf(count));
                    if(count > 0){
                        counter.setVisibility(View.VISIBLE);
                        btnMinus.setVisibility(View.VISIBLE);
                    }
                }
            });

            btnMinus.setOnClickListener(v -> {
                listener.onCounterMinus(topping);

                int count = Integer.valueOf(counter.getText().toString()) - 1;
                counter.setText(String.valueOf(count));
                if(count == 0){
                    counter.setVisibility(View.GONE);
                    btnMinus.setVisibility(View.GONE);
                }
            });

            CartItem cartItem = Sales.getFoodCart().findItem(topping.getUuid());
            if(cartItem != null){
                counter.setText(String.valueOf(cartItem.getQuantity()));
                counter.setVisibility(View.VISIBLE);
                btnMinus.setVisibility(View.VISIBLE);
            }
            else{
                counter.setText("0");
                counter.setVisibility(View.GONE);
                btnMinus.setVisibility(View.GONE);
            }
        }

        public void clearCart(){
            counter.setText("0");
            counter.setVisibility(View.GONE);
            btnMinus.setVisibility(View.GONE);
        }
    }

    public void clearCartSelections(ViewHolder viewHolder){
        viewHolder.clearCart();
    }

    public void setItemUuid(String uuid){
        this.uuid = uuid;
    }

    public void clearData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<FoodTopping> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<FoodTopping> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onCounterPlus(FoodTopping topping);
        void onCounterMinus(FoodTopping topping);
    }
}