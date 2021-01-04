package com.alllinkshare.home.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.auth.ui.activities.LoginActivity;
import com.alllinkshare.catalog.ui.activities.CategoriesActivity;
import com.alllinkshare.home.ui.activities.HomeActivity;
import com.als.home.R;

public class LandingFragment extends Fragment {

    private View rootView;

    public LandingFragment() {
        // Required empty public constructor
    }

    public static LandingFragment newInstance() {
        return new LandingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_landing, container, false);

        initHeader();
        initCategories();
        initHome();
        initLogin();

        return rootView;
    }

    private void initHeader(){
        HomeActivity activity = (HomeActivity) getActivity();
        activity.updateHeader(activity.HEADER_LANDING);
    }

    private void initCategories(){
        rootView.findViewById(R.id.categories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CategoriesActivity.class));
            }
        });
    }

    private void initHome(){
        rootView.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(((ViewGroup) getView().getParent()).getId(), HomeFragment.newInstance("CategoriesGridFragment"))
                        .commit();
            }
        });

    }

    private void initLogin(){
        rootView.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}