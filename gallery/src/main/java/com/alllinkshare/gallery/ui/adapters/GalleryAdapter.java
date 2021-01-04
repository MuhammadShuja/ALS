package com.alllinkshare.gallery.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.gallery.R;
import com.alllinkshare.gallery.models.Image;
import com.bumptech.glide.Glide;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context mContext;
    private List<Image> items;
    private OnItemClickListener listener;
    private int resource;

    public GalleryAdapter(Context mContext, List<Image> items, int resource, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.resource = resource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(resource, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }

        public void bind(final Image image, final OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(image.getSource())
                    .apply(GlideOptions.getDefault())
                    .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
//                    listener.onItemClick(product);
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Image> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Image> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(String image);
    }
}