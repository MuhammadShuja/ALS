package com.alllinkshare.reviews.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alllinkshare.reviews.R;
import com.alllinkshare.reviews.models.Review;
import com.alllinkshare.reviews.models.ReviewImage;
import com.alllinkshare.reviews.ui.fragments.AddReviewFragment;
import com.alllinkshare.reviews.ui.fragments.ViewReviewsFragment;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private Context mContext;
    private List<Review> items;
    private OnItemClickListener listener;

    public ReviewsAdapter(Context mContext, List<Review> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_review, parent, false);

        return new ViewHolder(view);
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
        private CircleImageView avatar;
        private TextView name;
        private RatingBar ratingBar;
        private TextView comment;
        private RecyclerView images;
        private TextView likeCount, loveCount, happyCount, surpriseCount, angryCount, commentsCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            comment = itemView.findViewById(R.id.comment);
            images = itemView.findViewById(R.id.rv_items);

            likeCount = itemView.findViewById(R.id.like_count);
            loveCount = itemView.findViewById(R.id.love_count);
            happyCount = itemView.findViewById(R.id.happy_count);
            surpriseCount = itemView.findViewById(R.id.surprise_count);
            angryCount = itemView.findViewById(R.id.angry_count);
            commentsCount = itemView.findViewById(R.id.comments_count);
        }

        public void bind(Review review, OnItemClickListener listener) {
            Glide
                    .with(mContext)
                    .load(review.getUserImage())
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .into(avatar);

            name.setText(review.getUserName());
            comment.setText(review.getComment());

            if(!TextUtils.isEmpty(review.getRating()))
                ratingBar.setRating(Float.parseFloat(review.getRating()));

            likeCount.setText(String.valueOf(review.getReactions().getLikeCount()));
            loveCount.setText(String.valueOf(review.getReactions().getLoveCount()));
            happyCount.setText(String.valueOf(review.getReactions().getHappyCount()));
            surpriseCount.setText(String.valueOf(review.getReactions().getSupriseCount()));
            angryCount.setText(String.valueOf(review.getReactions().getAngryCount()));
            commentsCount.setText(review.getReactions().getCommentsCount() + " Comment");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                }
            });

            List<ReviewImage> items = new ArrayList<>();

            if(review.getImages() != null){
                items.addAll(review.getImages());
            }

            ReviewImagesAdapter reviewImagesAdapter = new ReviewImagesAdapter(mContext, items, new ReviewImagesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ReviewImage reviewImage) {

                }
            });
            images.setLayoutManager(new GridLayoutManager(mContext, 1));
            images.setAdapter(reviewImagesAdapter);
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Review> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Review> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Review review);
    }
}