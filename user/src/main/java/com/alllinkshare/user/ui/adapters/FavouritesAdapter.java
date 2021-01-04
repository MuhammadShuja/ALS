package com.alllinkshare.user.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.user.R;
import com.alllinkshare.user.models.FavouriteItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    private Context mContext;
    private List<FavouriteItem> items;
    private OnItemClickListener listener;

    public FavouritesAdapter(Context mContext, List<FavouriteItem> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_favourite_item, viewGroup, false);

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
        private Button btnView;
        private ImageView btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            btnView = itemView.findViewById(R.id.btn_view_item);
            btnRemove = itemView.findViewById(R.id.btn_remove_item);
        }

        public void bind(final FavouriteItem item, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(item.getThumbnail())
                    .placeholder(mContext.getResources().getIdentifier("image_placeholder", "drawable", mContext.getPackageName()))
                    .into(thumbnail);

            name.setText(item.getName());

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onViewItem(item);
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onRemoveItem(item);
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<FavouriteItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<FavouriteItem> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onViewItem(FavouriteItem item);
        void onRemoveItem(FavouriteItem item);
    }
}