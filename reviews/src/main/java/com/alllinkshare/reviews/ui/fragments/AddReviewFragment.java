package com.alllinkshare.reviews.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.reviews.R;

public class AddReviewFragment extends Fragment {

    private View rootView;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    public static AddReviewFragment newInstance() {
        return new AddReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_review, container, false);

        return rootView;
    }
}