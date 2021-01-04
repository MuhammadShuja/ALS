package com.alllinkshare.catalogshopping.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.catalogshopping.R;
import com.alllinkshare.catalogshopping.models.Product;
import com.alllinkshare.catalogshopping.models.ProductColor;
import com.alllinkshare.catalogshopping.models.ProductDealer;
import com.alllinkshare.catalogshopping.models.ProductSize;
import com.alllinkshare.catalogshopping.models.SpinnerItem;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;
import com.cunoraz.tagview.Tag;

import java.util.ArrayList;
import java.util.List;

public class ComparisonAdapter  extends RecyclerView.Adapter<ComparisonAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductDealer> items;
    private ComparisonAdapter.OnItemClickListener listener;

    public ComparisonAdapter(Context mContext, List<ProductDealer> items, ComparisonAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ComparisonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_dealer_horizontal, viewGroup, false);

        return new ComparisonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComparisonAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView logo;
        private TextView price, shipping, info;
        private Button btnBuy;

        public ViewHolder(View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.thumbnail);
            price = itemView.findViewById(R.id.price);
            shipping = itemView.findViewById(R.id.shipping);
            info = itemView.findViewById(R.id.info);
            btnBuy = itemView.findViewById(R.id.btn_buy);
        }

        public void bind(final ProductDealer dealer, final ComparisonAdapter.OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(dealer.getLogo())
                    .thumbnail(0.25f)
                    .apply(GlideOptions.getDefault())
                    .into(logo);

            price.setText(dealer.getPrice());
            shipping.setText(dealer.getShippingFee());
            info.setText(dealer.getInfo());

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dealer);
                }
            });
        }
    }

    public void clearData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<ProductDealer> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<ProductDealer> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ProductDealer dealer);
    }
}