package com.alllinkshare.cardealing.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.cardealing.R;
import com.alllinkshare.cardealing.models.Car;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {

    private Context mContext;
    private List<Car> items;
    private CarsAdapter.OnItemClickListener listener;

    public CarsAdapter(Context mContext, List<Car> items, CarsAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_car_vertical, viewGroup, false);

        return new CarsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView name, price, model, transmission, mileage;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            model = itemView.findViewById(R.id.model);
            transmission = itemView.findViewById(R.id.transmission);
            mileage = itemView.findViewById(R.id.mileage);
        }

        public void bind(final Car car, final CarsAdapter.OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(car.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getDefault())
                    .into(thumbnail);

            name.setText(car.getName());
            price.setText(car.getPrice());
            model.setText(car.getModel());
            transmission.setText(car.getTransmission());
            mileage.setText(car.getMileage());

            itemView.setOnClickListener(v -> listener.onItemClick(car));
        }
    }

    public void clearData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Car> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Car> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Car car);
    }
}