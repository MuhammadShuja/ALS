package com.alllinkshare.home.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.cardealing.ui.activities.CarDealingActivity;
import com.alllinkshare.home.ui.activities.HomeActivity;
import com.alllinkshare.maps.ui.fragments.MapFragment;
import com.alllinkshare.news.ui.fragments.NewsFragment;
import com.alllinkshare.play.ui.fragments.PlayFragment;
import com.alllinkshare.shopping.ui.activities.ShoppingActivity;
import com.als.home.R;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private static final String FRAGMENT_TO_LOAD = "fragment";

    private String fragmentToLoad;

    private Fragment fragment = null;

    private TabLayout topTabLayout, bottomTabLayout;

    private View rootView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String fragmentToLoad) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TO_LOAD, fragmentToLoad);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentToLoad = getArguments().getString(FRAGMENT_TO_LOAD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        initHeader();
        initTopTabs();
        initBottomTabs();
        loadFragment();

        return rootView;
    }

    private void initHeader(){
        HomeActivity activity = (HomeActivity) getActivity();
        activity.updateHeader(activity.HEADER_HOME);
    }

    private void initTopTabs(){
        topTabLayout = rootView.findViewById(R.id.tabs);

        topTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        fragmentToLoad = "ALSFragment";
                        break;

                    case 1:
                        fragmentToLoad = "PlayFragment";
                        break;

                    case 2:
                        fragmentToLoad = "NewsFragment";
                        break;

                    case 3:
                        fragmentToLoad = "MapFragment";
                        break;
                }

                loadFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initBottomTabs(){
        bottomTabLayout = rootView.findViewById(R.id.bottom_tabs);

        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        fragmentToLoad = "OfficeShareFragment";
                        break;

                    case 1:
                        fragmentToLoad = "CarSharingFragment";
                        break;

                    case 2:
                        fragmentToLoad = "GolfFragment";
                        break;

                    case 3:
                        startActivity(new Intent(getActivity(), ShoppingActivity.class));
//                        getActivity().finish();
                        break;

                    case 4:
                        fragmentToLoad = "Freelancer";
                        break;

                    case 9:
                        startActivity(new Intent(getActivity(), CarDealingActivity.class));
                }

                loadFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadFragment(){
        switch (fragmentToLoad){
            case "PlayFragment":
                fragment = PlayFragment.newInstance(null, null);
                break;

            case "NewsFragment":
                fragment = NewsFragment.newInstance("DailyNewsFragment");
                break;

            case "MapFragment":
                fragment = MapFragment.newInstance(null, null);
                break;

            default:
                fragment = ALSFragment.newInstance();
        }

        try {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, fragment)
                    .addToBackStack(null)
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}