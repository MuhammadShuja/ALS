package com.alllinkshare.catalog.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.catalog.repos.CategoryRepository;
import com.alllinkshare.catalog.ui.adapters.CategoryGridWithIconsAdapter;
import com.alllinkshare.catalog.ui.constants.ActionType;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryGridWithIconsFragment extends Fragment {

    private static final String PARENT_ID = "parent_id";

    private int parentId;

    private CategoryGridWithIconsAdapter categoryGridWithIconsAdapter;

    private View rootView;

    public CategoryGridWithIconsFragment() {
        // Required empty public constructor
    }

    public static CategoryGridWithIconsFragment newInstance(int parentId) {
        CategoryGridWithIconsFragment fragment = new CategoryGridWithIconsFragment();
        Bundle args = new Bundle();
        args.putInt(PARENT_ID, parentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parentId = getArguments().getInt(PARENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category_grid_with_icons, container, false);

        initRecyclerView();
        loadData();

        return rootView;
    }

    private void initRecyclerView(){
        categoryGridWithIconsAdapter = new CategoryGridWithIconsAdapter(getContext(), new ArrayList<Category>(), new CategoryGridWithIconsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Category category) {
                switch (category.getAction().getType()){
                    case ActionType.LISTINGS:
                        Coordinator.getCatalogNavigator()
                                .navigateToListings(Integer.parseInt(category.getAction().getHandle()));
                        break;
                    case ActionType.CATEGORIES:
                        Coordinator.getCatalogNavigator()
                                .navigateToCategory(Integer.parseInt(category.getAction().getHandle()), false);
                        break;
                    case ActionType.FORM:
                        break;
                    case ActionType.REDIRECT:
                        break;
                }
            }
        });

        RecyclerView rvCategories = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        DividerItemDecoration horizontalDivider = new DividerItemDecoration(rvCategories.getContext(), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(rvCategories.getContext(), DividerItemDecoration.VERTICAL);

        Drawable divider = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider);
        horizontalDivider.setDrawable(Objects.requireNonNull(divider));
        verticalDivider.setDrawable(Objects.requireNonNull(divider));
        rvCategories.addItemDecoration(horizontalDivider);
        rvCategories.addItemDecoration(verticalDivider);
        rvCategories.setNestedScrollingEnabled(false);
        rvCategories.setLayoutManager(layoutManager);
        rvCategories.setAdapter(categoryGridWithIconsAdapter);
    }

    private void loadData(){
        CategoryRepository.getInstance().getCategories(parentId, false, new CategoryRepository.DataReadyListener() {
            @Override
            public void onDataReady(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> categoryList) {
                categoryGridWithIconsAdapter.setData(categoryList);

                Glide
                        .with(Objects.requireNonNull(getContext()))
                        .load(coverImage)
                        .apply(GlideOptions.getDefault())
                        .into((ImageView) rootView.findViewById(R.id.cover));

                rootView.findViewById(R.id.content_wrapper).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.loading_wrapper).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }
}