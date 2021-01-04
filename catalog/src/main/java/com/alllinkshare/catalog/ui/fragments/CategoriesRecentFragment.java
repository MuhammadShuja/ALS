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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.catalog.repos.CategoryRepository;
import com.alllinkshare.catalog.ui.adapters.CategoryGridWithImagesAdapter;
import com.alllinkshare.catalog.ui.constants.ActionType;
import com.alllinkshare.core.navigator.Coordinator;

import java.util.ArrayList;
import java.util.List;

public class CategoriesRecentFragment extends Fragment {
    private CategoryGridWithImagesAdapter categoryGridWithImagesAdapter;
    private ProgressBar progressBar;

    private View rootView;

    public CategoriesRecentFragment() {
        // Required empty public constructor
    }

    public static CategoriesRecentFragment newInstance() {
        return new CategoriesRecentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_categories_recent, container, false);

        initRecyclerView();
        loadData();

        return rootView;
    }

    private void initRecyclerView(){
        categoryGridWithImagesAdapter = new CategoryGridWithImagesAdapter(getActivity(), new ArrayList<Category>(), new CategoryGridWithImagesAdapter.OnItemClickListener(){
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

        Drawable divider = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        horizontalDivider.setDrawable(divider);
        verticalDivider.setDrawable(divider);
        rvCategories.addItemDecoration(horizontalDivider);
        rvCategories.addItemDecoration(verticalDivider);
        rvCategories.setLayoutManager(layoutManager);
        rvCategories.setAdapter(categoryGridWithImagesAdapter);
        progressBar = rootView.findViewById(R.id.progress_bar);
    }

    private void loadData(){
        if(progressBar.getVisibility() != View.VISIBLE) progressBar.setVisibility(View.VISIBLE);

        CategoryRepository.getInstance().getRecentCategories(new CategoryRepository.DataReadyListener() {
            @Override
            public void onDataReady(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> categoryList) {
                categoryGridWithImagesAdapter.setData(categoryList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }
}