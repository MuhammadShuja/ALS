package com.alllinkshare.catalog.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.alllinkshare.catalog.ui.adapters.CategoryListWithImagesAdapter;
import com.alllinkshare.catalog.ui.constants.ActionType;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryListWithImagesFragment extends Fragment {

    private static final String CATEGORY_ID = "category_id";
    private static int categoryID;

    private View rootView;
    private CategoryListWithImagesAdapter categoryListWithImagesAdapter;

    public CategoryListWithImagesFragment() {
    }

    public static CategoryListWithImagesFragment newInstance(int categoryID) {
        CategoryListWithImagesFragment fragment = new CategoryListWithImagesFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getInt(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_category_list_with_images, container, false);

        initCategories();
        loadCategories();
        return rootView;
    }

    private void initCategories(){

        categoryListWithImagesAdapter = new CategoryListWithImagesAdapter(getContext(), new ArrayList<Category>(), new CategoryListWithImagesAdapter.OnItemClickListener() {
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

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(categoryListWithImagesAdapter);

        rootView.findViewById(R.id.btn_load_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coordinator.getCatalogNavigator()
                        .navigateToCategory(categoryID, true);
            }
        });
    }

    private void loadCategories(){
        CategoryRepository.getInstance().getCategories(categoryID, false, new CategoryRepository.DataReadyListener() {
            @Override
            public void onDataReady(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> categoryList) {
                categoryListWithImagesAdapter.setData(categoryList);
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