package com.alllinkshare.catalog.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;
import java.util.List;

public class CategoryGridWithImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Category> items;
    private OnItemClickListener listener;

    public CategoryGridWithImagesAdapter(Context mContext, List<Category> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dataLayoutView = LayoutInflater.from(mContext).inflate(
                R.layout.card_category_grid_with_image, parent, false);
        return new ItemViewHolder(dataLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.bind((Category) items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    /*
    --------------------------------
    | HELPER FUNCTIONS
    --------------------------------
    */

    public void addData(List<Category> objects){
        if(items != null){
            items.addAll(objects);
            notifyDataSetChanged();
        }
    }

    public void setData(List<Category> objects){
        if(items != null){
            items.clear();
            items.addAll(objects);
            notifyDataSetChanged();
        }
    }

    public void clearData(){
        items.clear();
        notifyDataSetChanged();
    }

    /*
    --------------------------------
    | VIEW HOLDER CLASSES
    --------------------------------
    */

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout itemWrapper;
        private ImageView categoryImage;
        private TextView categoryName;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemWrapper = itemView.findViewById(R.id.categoryCardWrapper);
            categoryImage = (ImageView) itemView.findViewById(R.id.thumbnail);
            categoryName = itemView.findViewById(R.id.name);
        }

        void bind(final Category category, final OnItemClickListener listener){
            Glide
                    .with(mContext)
                    .load(category.getThumbnail())
                    .thumbnail(0.25f)
                    .centerCrop()
                    .apply(GlideOptions.getDefault())
                    .into(categoryImage);

            categoryName.setText(category.getName());

            itemWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(category);
                }
            });
        }
    }

    /*
    --------------------------------
    | CALLBACK LISTENER
    --------------------------------
    */

    public interface OnItemClickListener{
        void onItemClick(Category category);
    }
}