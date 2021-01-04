package com.alllinkshare.shopping.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alllinkshare.shopping.R;
import com.alllinkshare.shopping.models.Product;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context mContext;
    private List<Product> items;
    private ProductsAdapter.OnItemClickListener listener;

    public ProductsAdapter(Context mContext, List<Product> items, ProductsAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_category_horizontal, viewGroup, false);

        return new ProductsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumbnail;
        private TextView name;
        private TextView labelOne, labelTwo;
        private TextView rating, ratingCount;
        private TextView percentageOff;
        private Button btnOrder, btnShop, btnBooking, btnDeal;
        private RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(final Product product, final ProductsAdapter.OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(mContext.getResources().getIdentifier(product.getThumbnail(), "drawable", mContext.getPackageName()))
                    .into(thumbnail);

            name.setText(product.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(product);
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Product> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Product> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Product product);
    }
}