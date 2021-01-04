package com.alllinkshare.catalog.ui.adapters;

import android.content.Context;
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

public class CategoryListWithIconsAdapter extends RecyclerView.Adapter<CategoryListWithIconsAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> items;
    private OnItemClickListener listener;

    public CategoryListWithIconsAdapter(Context mContext, List<Category> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_category_list_with_icon, viewGroup, false);

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

        private ImageView thumbnail;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(final Category category, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(category.getIcon())
                    .thumbnail(0.25f)
                    .centerCrop()
                    .apply(GlideOptions.getDefault())
                    .into(thumbnail);

            name.setText(category.getName());

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

    public void setData(List<Category> items){
        this.items = items;
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