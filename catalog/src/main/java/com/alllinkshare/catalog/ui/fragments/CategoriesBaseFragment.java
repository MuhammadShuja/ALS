package com.alllinkshare.catalog.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Category;
import com.alllinkshare.catalog.repos.CategoryRepository;
import com.alllinkshare.catalog.ui.constants.LayoutStyle;
import com.alllinkshare.core.navigator.Keys;

import java.util.List;

public class CategoriesBaseFragment extends Fragment {
    private static final String TAG = "Category/Base";

    private static final String FRAGMENT_HOST = "fragment_host";

    private int fragmentHost;
    private int parentId;
    private boolean loadAll;

    private View rootView;

    public CategoriesBaseFragment() {
        // Required empty public constructor
    }

    public static CategoriesBaseFragment newInstance(int fragmentHost, int parentId, boolean loadAll) {
        CategoriesBaseFragment fragment = new CategoriesBaseFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_HOST, fragmentHost);
        args.putInt(Keys.PARENT_ID, parentId);
        args.putBoolean(Keys.BOOL, loadAll);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentHost = getArguments().getInt(FRAGMENT_HOST);
            parentId = getArguments().getInt(Keys.PARENT_ID);
            loadAll = getArguments().getBoolean(Keys.BOOL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_categories_base, container, false);

        loadCategoryData();

        return rootView;
    }

    private void loadCategoryData(){
        CategoryRepository.getInstance().getCategories(parentId, loadAll, new CategoryRepository.DataReadyListener() {
            @Override
            public void onDataReady(int parentId, String parentName, String coverImage, int childCount, String layoutStyle, List<Category> categoryList) {
                initCategoryView(parentId, layoutStyle);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initCategoryView(int categoryParentId, String layoutStyle){
        Fragment fragment;

        switch (layoutStyle){
            case LayoutStyle.GRID_WITH_THUMBNAILS:
                fragment = CategoryGridWithImagesFragment.newInstance(categoryParentId);
                break;

            case LayoutStyle.GRID_WITH_ICONS:
                fragment = CategoryGridWithIconsFragment.newInstance(categoryParentId);
                break;

            case LayoutStyle.LIST_WITH_THUMBNAILS:
                fragment = CategoryListWithImagesFragment.newInstance(categoryParentId);
                break;

            case LayoutStyle.LIST_WITH_ICONS:
            default:
                fragment = CategoryListWithIconsFragment.newInstance(categoryParentId);
        }

        try {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(fragmentHost,
                            fragment)
                    .commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}