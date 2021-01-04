package com.alllinkshare.play.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.play.R;
import com.alllinkshare.play.models.Category;
import com.alllinkshare.play.ui.adapters.PlayPagerAdapter;
import com.alllinkshare.play.repos.CategoriesRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class PlayFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ViewPager2 viewPager;
    private PlayPagerAdapter playPagerAdapter;

    private List<Category> categories = new ArrayList<>();

    private TabLayout tabLayout;

    private View rootView;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_play, container, false);

        initPosts();

        return rootView;
    }

    private void initPosts(){
        categories.clear();
        categories.addAll(CategoriesRepository.getFakeCategories(20));
        categories.add(0, new Category(-1, "All"));

        playPagerAdapter = new PlayPagerAdapter(this, categories);
        viewPager = rootView.findViewById(R.id.pager);
        viewPager.setAdapter(playPagerAdapter);

        tabLayout = rootView.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(categories.get(position).getName());
                    }
                }).attach();
    }
}