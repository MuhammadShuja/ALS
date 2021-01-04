package com.alllinkshare.reviews.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.reviews.R;
import com.alllinkshare.reviews.models.Rating;
import com.alllinkshare.reviews.models.Review;

import java.util.ArrayList;
import java.util.Objects;

public class ReviewsFragment extends Fragment {

    private static final String RATING = "rating";
    private static final String REVIEWS = "reviews";

    private Rating rating;
    private ArrayList<Review> reviews;

    private View rootView;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    public static ReviewsFragment newInstance(Rating rating, ArrayList<Review> reviews) {
        ReviewsFragment fragment = new ReviewsFragment();
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
        rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        initViewReviews();
        initAddReview();

        return rootView;
    }

    private void initViewReviews(){
        try {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.view_reviews_fragment,
                            ViewReviewsFragment.newInstance(rating, reviews))
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initAddReview(){
        try {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.add_review_fragment,
                            AddReviewFragment.newInstance())
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}