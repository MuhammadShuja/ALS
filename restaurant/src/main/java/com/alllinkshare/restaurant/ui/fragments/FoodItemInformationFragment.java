package com.alllinkshare.restaurant.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alllinkshare.restaurant.R;

public class FoodItemInformationFragment extends Fragment {

    private static final String CATEGORY = "category";
    private static final String NAME = "name";

    private String category;
    private String name;

    private View rootView;

    public FoodItemInformationFragment() {
        // Required empty public constructor
    }

    public static FoodItemInformationFragment newInstance(String category, String name) {
        FoodItemInformationFragment fragment = new FoodItemInformationFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        args.putString(NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
            name = getArguments().getString(NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food_item_information, container, false);

        initInfo();

        return rootView;
    }

    private void initInfo(){
        ((TextView) rootView.findViewById(R.id.category)).setText(category);
        ((TextView) rootView.findViewById(R.id.food_name)).setText(name);
    }
}