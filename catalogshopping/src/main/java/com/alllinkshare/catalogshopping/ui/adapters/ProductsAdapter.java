package com.alllinkshare.catalogshopping.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.catalogshopping.R;
import com.alllinkshare.catalogshopping.models.Product;
import com.alllinkshare.catalogshopping.models.ProductColor;
import com.alllinkshare.catalogshopping.models.ProductSize;
import com.alllinkshare.catalogshopping.models.SpinnerItem;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context mContext;
    private List<Product> items;
    private OnItemClickListener listener;

    public ProductsAdapter(Context mContext, List<Product> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_product_horizontal, viewGroup, false);

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

        private ImageView thumbnail, btnFavourite;
        private TextView name, price, rating, ratingCount;
        private AppCompatSpinner colorSpinner, sizeSpinner;
        private RatingBar ratingBar;
        private TextView btnAction;
        private TagView deliveryMethods;
        private LinearLayout deliveryMethodsWrapper;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            rating = itemView.findViewById(R.id.rating);
            ratingCount = itemView.findViewById(R.id.rating_count);

            colorSpinner = itemView.findViewById(R.id.color);
            sizeSpinner = itemView.findViewById(R.id.size);

            ratingBar = itemView.findViewById(R.id.rating_bar);

            btnFavourite = itemView.findViewById(R.id.btn_favourite);
            btnAction = itemView.findViewById(R.id.btn_action);

            deliveryMethods = itemView.findViewById(R.id.delivery_methods);
            deliveryMethodsWrapper = itemView.findViewById(R.id.delivery_methods_wrapper);
        }

        public void bind(final Product product, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(product.getThumbnail())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getProductOptions())
                    .into(thumbnail);

            name.setText(product.getName());
            price.setText("₩"+product.getPrice());
            rating.setText(String.valueOf(product.getRating().getRating()));
            ratingBar.setRating(product.getRating().getRating());
            if(product.getRating().getTotal() > 0){
                ratingCount.setText(String.valueOf(product.getRating().getTotal()));
                ratingCount.setVisibility(View.VISIBLE);
            }
            else{
                ratingCount.setVisibility(View.GONE);
            }

            if(product.getAction().equals("SEE_DETAILS")){
                btnAction.setText("See details");
            }
            else{
                btnAction.setText("See more");
            }

            List<String> methods = new ArrayList<>();
            if(product.getDeliveryMethods().isALSAvailable()) methods.add("ALS");
            if(product.getDeliveryMethods().isO2OAvailable()) methods.add("O2O");
            if(product.getDeliveryMethods().isD2DAvailable()) methods.add("D2D");
            if(product.getDeliveryMethods().isCODAvailable()) methods.add("COD");

            if(methods.size() > 0) {
                deliveryMethodsWrapper.setVisibility(View.VISIBLE);
                ArrayList<Tag> tags = new ArrayList<>();
                for (String method : methods) {
                    Tag tag = new Tag(method);
                    tag.tagTextColor = mContext.getResources().getColor(R.color.black);
                    tag.layoutColor = mContext.getResources().getColor(R.color.bg);
                    tag.tagTextSize = 10.0f;
                    tag.radius = 4.0f;
                    tags.add(tag);
                }
                deliveryMethods.addTags(tags);
            }
            else{
                deliveryMethodsWrapper.setVisibility(View.GONE);
            }

            if(product.getColors().size() > 0){
                List<SpinnerItem> colors = new ArrayList<>();
                for(ProductColor color : product.getColors()){
                    colors.add(new SpinnerItem(color.getId(), color.getName(), color.getImage()));
                }

                final SpinnerAdapter colorSpinnerAdapter = new SpinnerAdapter(mContext, colors);
                colorSpinner.setAdapter(colorSpinnerAdapter);
                colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ProductColor color = product.getColors().get(i);
                        if(color.getSizes().size() > 0){
                            List<SpinnerItem> sizes = new ArrayList<>();
                            for(ProductSize size : color.getSizes()){
                                sizes.add(new SpinnerItem(size.getId(), size.getName(), null));
                            }

                            final SpinnerAdapter sizeSpinnerAdapter = new SpinnerAdapter(mContext, sizes);
                            sizeSpinner.setAdapter(sizeSpinnerAdapter);
                            sizeSpinner.setVisibility(View.VISIBLE);
                        }
                        else{
                            sizeSpinner.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                colorSpinner.setVisibility(View.VISIBLE);
            }
            else{
                colorSpinner.setVisibility(View.GONE);
            }

            if(product.isFavourite()){
                btnFavourite.setColorFilter(
                        ContextCompat.getColor(mContext, R.color.red),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            }

            btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(product.isFavourite()){
                        btnFavourite.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.grey),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        product.setFavourite(false);
                        listener.onFavouriteClick(product, false);
                    }
                    else{
                        btnFavourite.setColorFilter(
                                ContextCompat.getColor(mContext, R.color.red),
                                android.graphics.PorterDuff.Mode.SRC_IN);
                        product.setFavourite(true);
                        listener.onFavouriteClick(product, true);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(product);
                }
            });
        }
    }

    public void clearData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Product> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Product> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
        void onFavouriteClick(Product product, boolean isFavourite);
    }
}