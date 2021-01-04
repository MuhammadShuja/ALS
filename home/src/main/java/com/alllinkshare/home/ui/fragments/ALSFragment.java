package com.alllinkshare.home.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alllinkshare.catalog.ui.fragments.CategoryGridWithImagesFragment;
import com.alllinkshare.catalog.ui.fragments.CategoriesRecentFragment;
import com.als.home.R;

import java.util.Objects;

public class ALSFragment extends Fragment{

    private View rootView;

    public ALSFragment() {
    }

    public static ALSFragment newInstance() {
        ALSFragment fragment = new ALSFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_als, container, false);

        initRecentCategories();
        initPopularCategories();

        return rootView;
    }

    private void initRecentCategories(){
        try {
            CategoriesRecentFragment recentCategories = CategoriesRecentFragment.newInstance();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_host_recent_categories, recentCategories)
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initPopularCategories(){
        try {
            CategoryGridWithImagesFragment popularCategories = CategoryGridWithImagesFragment.newInstance(0);
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_host_popular_categories, popularCategories)
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        rootView.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button) v).getText().toString().toLowerCase().equals("close")) {
                    ((Button) v).setText("open");
                    rootView.findViewById(R.id.fragment_host_recent_categories).setVisibility(View.GONE);
                }
                else{
                    ((Button) v).setText("close");
                    rootView.findViewById(R.id.fragment_host_recent_categories).setVisibility(View.VISIBLE);
                }
            }
        });
    }
}