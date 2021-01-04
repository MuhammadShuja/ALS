package com.alllinkshare.catalogshopping.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alllinkshare.catalogshopping.R;
import com.google.android.material.tabs.TabLayout;

public class ShoppingGuideSizeChartFragment extends Fragment {

    private View rootView;

    public ShoppingGuideSizeChartFragment() {
        // Required empty public constructor
    }
public static ShoppingGuideSizeChartFragment newInstance() {
        ShoppingGuideSizeChartFragment fragment = new ShoppingGuideSizeChartFragment();
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
        rootView = inflater.inflate(R.layout.fragment_shopping_guide_size_chart, container, false);

        initSizeChart();

        return rootView;
    }

    private void initSizeChart(){
        TabLayout tabLayout = rootView.findViewById(R.id.size_tabs);
        final LinearLayout sizeTabContainer = rootView.findViewById(R.id.size_tab_container);
        for(String tab : getTabs()){
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_0, null));
                        break;
                    case 1:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_1, null));
                        break;
                    case 2:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_2, null));
                        break;
                    case 3:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_3, null));
                        break;
                    case 4:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_4, null));
                        break;
                    case 5:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_5, null));
                        break;
                    case 6:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_6, null));
                        break;
                    case 7:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_7, null));
                        break;
                    case 8:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_8, null));
                        break;
                    case 9:
                        sizeTabContainer.removeAllViews();
                        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_9, null));
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

        sizeTabContainer.removeAllViews();
        sizeTabContainer.addView(getLayoutInflater().inflate(R.layout.size_chart_0, null));
    }

    private String[] getTabs(){
        return new String[]{
                "Casual clothing",
                "Women's Shoes",
                "Maternity Wear",
                "Women's underwear",
                "Men's Clothing",
                "Men's shoes",
                "Accessory",
                "Baby/Kids Cloth",
                "Baby/Kids Shoes",
                "Size measuring"
        };
    }

}