package com.alllinkshare.news.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.news.R;
import com.google.android.material.tabs.TabLayout;

public class NewsFragment extends Fragment {

    private static final String FRAGMENT_TO_LOAD = "fragment";

    private String fragmentToLoad;

    private Fragment fragment = null;

    private TabLayout topTabLayout, bottomTablayout;

    private View rootView;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String fragmentToLoad) {
        NewsFragment fragment = new NewsFragment();
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
        rootView =  inflater.inflate(R.layout.fragment_news, container, false);

        initTopTabs();

        loadFragment();

        return rootView;
    }

    private void initTopTabs(){
        topTabLayout = rootView.findViewById(R.id.tabs);

        topTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        fragmentToLoad = "DailyNewsFragment";
                        break;

                    case 1:
                        fragmentToLoad = "EventFragment";
                        break;

                    case 2:
                        fragmentToLoad = "JobFragment";
                        break;

                    case 3:
                        fragmentToLoad = "MovieFragment";
                        break;

                    case 4:
                        fragmentToLoad = "CurrencyFragment";
                        break;

                    case 5:
                        fragmentToLoad = "WeatherFragment";
                        break;

                    default:
                        fragmentToLoad = "DailyNewsFragment";
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
            case "DailyNewsFragment":
                fragment = DailyNewsFragment.newInstance(null, null);
                break;

            case "EventFragment":
                fragment = EventFragment.newInstance(null, null);
                break;

            case "JobFragment":
                fragment = JobFragment.newInstance(null, null);
                break;

            case "MovieFragment":
                fragment = MovieFragment.newInstance(null, null);
                break;

            case "CurrencyFragment":
                fragment = CurrencyFragment.newInstance(null, null);
                break;

            case "WeatherFragment":
                fragment = WeatherFragment.newInstance(null, null);
                break;

            default:
                fragment = DailyNewsFragment.newInstance(null, null);
        }

        try {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_host_tab, fragment)
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}