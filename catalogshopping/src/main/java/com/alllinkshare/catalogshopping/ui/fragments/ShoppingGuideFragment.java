package com.alllinkshare.catalogshopping.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.catalogshopping.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ShoppingGuideFragment extends Fragment {

    private TabLayout tabLayout;

    private View rootView;

    public ShoppingGuideFragment() {
        // Required empty public constructor
    }

    public static ShoppingGuideFragment newInstance() {
        ShoppingGuideFragment fragment = new ShoppingGuideFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shopping_guide, container, false);

        initGuide();

        return rootView;
    }

    private void initGuide(){
        TabLayout tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Caution"));
        tabLayout.addTab(tabLayout.newTab().setText("Size chart"));
        tabLayout.addTab(tabLayout.newTab().setText("Customs guide"));
        tabLayout.addTab(tabLayout.newTab().setText("Currency rates"));
        tabLayout.addTab(tabLayout.newTab().setText("Unit conversion"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.shopping_guide_host,
                                        ShoppingGuideCautionFragment.newInstance())
                                .commit();
                        break;

                    case 1:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.shopping_guide_host,
                                        ShoppingGuideSizeChartFragment.newInstance())
                                .commit();
                        break;

                    case 2:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.shopping_guide_host,
                                        ShoppingGuideCustomsFragment.newInstance())
                                .commit();
                        break;

                    case 3:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.shopping_guide_host,
                                        ShoppingGuideCurrencyRatesFragment.newInstance())
                                .commit();
                        break;

                    case 4:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.shopping_guide_host,
                                        ShoppingGuideUnitConversionFragment.newInstance())
                                .commit();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.shopping_guide_host,
                        ShoppingGuideCautionFragment.newInstance())
                .commit();
    }
}