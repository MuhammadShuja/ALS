package com.alllinkshare.catalog.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryListWithImagesAdapter extends RecyclerView.Adapter<CategoryListWithImagesAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> items;
    private CategoryListWithImagesAdapter.OnItemClickListener listener;

    public CategoryListWithImagesAdapter(Context mContext, List<Category> items, CategoryListWithImagesAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public CategoryListWithImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_category_list_with_image, viewGroup, false);

        return new CategoryListWithImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListWithImagesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumbnail;
        private TextView name;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
        }

        public void bind(final Category category, final CategoryListWithImagesAdapter.OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(category.getThumbnail())
                    .apply(GlideOptions.getDefault())
                    .into(thumbnail);

            name.setText(category.getName());
            description.setText(category.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(category);
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Category> objects){
        Log.d("Check", "--------------------------"+ objects.size());
        this.items.clear();
        this.items.addAll(objects);
        notifyDataSetChanged();
    }

    public void addData(List<Category> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Category category);
    }
}