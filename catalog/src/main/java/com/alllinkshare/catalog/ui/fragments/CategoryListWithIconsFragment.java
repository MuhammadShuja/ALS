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
import com.alllinkshare.catalog.ui.adapters.CategoryListWithIconsAdapter;
import com.alllinkshare.catalog.ui.constants.ActionType;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GlideOptions;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryListWithIconsFragment extends Fragment {

    private int parentID;

    private View rootView;
    private CategoryListWithIconsAdapter categoryListWithIconsAdapter;

    public CategoryListWithIconsFragment() {
        // Required empty public constructor
    }

    public static CategoryListWithIconsFragment newInstance(int parentID) {
        CategoryListWithIconsFragment fragment = new CategoryListWithIconsFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.PARENT_ID, parentID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parentID = getArguments().getInt(Keys.PARENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category_list_with_icons, container, false);

        initCategories();
        loadCategories();
        return rootView;
    }

    private void initCategories(){
        categoryListWithIconsAdapter = new CategoryListWithIconsAdapter(getActivity(), new ArrayList<Category>(), new CategoryListWithIconsAdapter.OnItemClickListener() {
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
        rvItems.setAdapter(categoryListWithIconsAdapter);
    }

    private void loadCategories(){
        CategoryRepository.getInstance().getCategories(parentID, false, new CategoryRepository.DataReadyListener() {
            @Override
            public void onDataReady(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> categoryList) {
                Category category = new Category(
                        -1,
                        parentId,
                        "All options",
                        "All options",
                        "https://alllinkshare.com/frontend-assets/favicon/34D.png",
                        "https://alllinkshare.com/frontend-assets/favicon/34D.png",
                        "",
                        "",
                        false,
                        0,
                        new Category.Action(ActionType.LISTINGS, String.valueOf(parentId))
                );
                List<Category> categories = new ArrayList<>();
                categories.add(0, category);
                categories.addAll(categoryList);

                categoryListWithIconsAdapter.setData(categories);
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