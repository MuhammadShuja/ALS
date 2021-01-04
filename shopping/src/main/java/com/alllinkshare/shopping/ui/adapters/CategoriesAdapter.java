package com.alllinkshare.shopping.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alllinkshare.shopping.R;
import com.alllinkshare.shopping.models.Category;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> items;
    private CategoriesAdapter.OnItemClickListener listener;

    public CategoriesAdapter(Context mContext, List<Category> items, CategoriesAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_category_horizontal, viewGroup, false);

        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.ViewHolder viewHolder, int i) {
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

        public void bind(final Category category, final CategoriesAdapter.OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(mContext.getResources().getIdentifier(category.getThumbnail(), "drawable", mContext.getPackageName()))
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