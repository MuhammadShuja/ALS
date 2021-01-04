package com.alllinkshare.user.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.user.R;
import com.alllinkshare.user.ui.activities.ProfileActivity;

public class DashboardFragment extends Fragment {

    private View rootView;

    public DashboardFragment() {
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initWidgets();

        return rootView;
    }

    private void initWidgets(){
        rootView.findViewById(R.id.btn_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), ProfileFragment.newInstance())
                        .commit();

                ((ProfileActivity) getActivity()).setTitle(getString(R.string.user_fragment_dashboard_my_profile));
            }
        });

        rootView.findViewById(R.id.btn_credentials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), CredentialsFragment.newInstance())
                        .commit();

                ((ProfileActivity) getActivity()).setTitle(getString(R.string.user_fragment_dashboard_credentials));
            }
        });

        rootView.findViewById(R.id.btn_favourites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), FavouritesFragment.newInstance())
                        .commit();

                ((ProfileActivity) getActivity()).setTitle(getString(R.string.user_fragment_dashboard_my_favourites));
            }
        });

        rootView.findViewById(R.id.btn_coupons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), CouponFragment.newInstance())
                        .commit();

                ((ProfileActivity) getActivity()).setTitle(getString(R.string.user_fragment_dashboard_my_coupons));
            }
        });

        rootView.findViewById(R.id.btn_chat_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), ChatFragment.newInstance(null, null))
                        .commit();

                ((ProfileActivity) getActivity()).setTitle(getString(R.string.user_fragment_dashboard_chat_box));
            }
        });

        rootView.findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), UploadFragment.newInstance(null, null))
                        .commit();

                ((ProfileActivity) getActivity()).setTitle(getString(R.string.user_fragment_dashboard_image_video_upload));
            }
        });
    }
}