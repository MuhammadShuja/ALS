package com.alllinkshare.reviews.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.reviews.R;
import com.alllinkshare.reviews.models.Review;
import com.alllinkshare.reviews.models.ReviewImage;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewImagesAdapter extends RecyclerView.Adapter<ReviewImagesAdapter.ViewHolder> {

    private Context mContext;
    private List<ReviewImage> items;
    private ReviewImagesAdapter.OnItemClickListener listener;

    public ReviewImagesAdapter(Context mContext, List<ReviewImage> items, ReviewImagesAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_review_image, parent, false);

        return new ReviewImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView caption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            caption = itemView.findViewById(R.id.caption);
        }

        public void bind(ReviewImage reviewImage, OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(reviewImage.getSource())
                    .placeholder(R.drawable.image_placeholder)
                    .into(imageView);

            caption.setText(reviewImage.getCaption());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<ReviewImage> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<ReviewImage> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(ReviewImage reviewImage);
    }
}