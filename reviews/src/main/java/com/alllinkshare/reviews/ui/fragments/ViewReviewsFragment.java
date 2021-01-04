package com.alllinkshare.reviews.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alllinkshare.core.utils.MaterialColorGenerator;
import com.alllinkshare.reviews.R;
import com.alllinkshare.reviews.models.Rating;
import com.alllinkshare.reviews.models.Review;
import com.alllinkshare.reviews.ui.adapters.ReviewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewReviewsFragment extends Fragment {

    private static final String RATING = "rating";
    private static final String REVIEWS = "reviews";

    private Rating rating;
    private List<Review> reviews;

    private View rootView;

    public ViewReviewsFragment() {
        // Required empty public constructor
    }

    public static ViewReviewsFragment newInstance(Rating rating, ArrayList<Review> reviews) {
        ViewReviewsFragment fragment = new ViewReviewsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RATING, rating);
        args.putParcelableArrayList(REVIEWS, reviews);
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rating = getArguments().getParcelable(RATING);
            reviews = getArguments().getParcelableArrayList(REVIEWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_reviews, container, false);

        initRatings();
        initReviews();

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void initRatings(){
        ((TextView) rootView.findViewById(R.id.rating_count)).setText("("+rating.getTotal()+")");
        ((RatingBar) rootView.findViewById(R.id.rating_bar)).setRating(rating.getRating());

        ((ProgressBar) rootView.findViewById(R.id.progress_five)).setProgress((int) Math.round(rating.getPercentage().getFiveStars()));
        ((ProgressBar) rootView.findViewById(R.id.progress_four)).setProgress((int) Math.round(rating.getPercentage().getFourStars()));
        ((ProgressBar) rootView.findViewById(R.id.progress_three)).setProgress((int) Math.round(rating.getPercentage().getThreeStars()));
        ((ProgressBar) rootView.findViewById(R.id.progress_two)).setProgress((int) Math.round(rating.getPercentage().getTwoStars()));
        ((ProgressBar) rootView.findViewById(R.id.progress_one)).setProgress((int) Math.round(rating.getPercentage().getOneStars()));

        ((TextView) rootView.findViewById(R.id.percentage_five)).setText(rating.getPercentage().getFiveStars()+"%");
        ((TextView) rootView.findViewById(R.id.percentage_four)).setText(rating.getPercentage().getFourStars()+"%");
        ((TextView) rootView.findViewById(R.id.percentage_three)).setText(rating.getPercentage().getThreeStars()+"%");
        ((TextView) rootView.findViewById(R.id.percentage_two)).setText(rating.getPercentage().getTwoStars()+"%");
        ((TextView) rootView.findViewById(R.id.percentage_one)).setText(rating.getPercentage().getOneStars()+"%");
    }

    private void initReviews(){
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getContext(), reviews, new ReviewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Review review) {

            }
        });

        RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(reviewsAdapter);
    }
}