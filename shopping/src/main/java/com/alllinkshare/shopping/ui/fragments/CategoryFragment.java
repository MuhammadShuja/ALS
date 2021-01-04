package com.alllinkshare.shopping.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.shopping.R;
import com.alllinkshare.shopping.models.Category;
import com.alllinkshare.shopping.repos.CategoriesRepository;
import com.alllinkshare.shopping.ui.adapters.CategoriesAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryFragment extends Fragment {

        private static final String CATEGORY_ID = "category_id";
        private static final String FULL_DEMO = "full_demo";
        private int categoryID;
        private boolean fullDemo;

        private View rootView;
        private CategoriesAdapter categoriesAdapter;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance(int categoryID, boolean fullDemo) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryID);
        args.putBoolean(FULL_DEMO, fullDemo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getInt(CATEGORY_ID);
            fullDemo = getArguments().getBoolean(FULL_DEMO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_category_shopping, container, false);

        initCategories();
        loadCategories();
        return rootView;
    }

    private void initCategories(){
        categoriesAdapter = new CategoriesAdapter(getActivity(), new ArrayList<Category>(), new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                if(fullDemo) {
                    try {
//                        CategoryChildrenFragment fragment = CategoryChildrenFragment.newInstance(1, true);
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .add(((ViewGroup) getView().getParent()).getId(), fragment)
//                                .commit();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(categoriesAdapter);
    }

    private void loadCategories(){
        categoriesAdapter.setData(CategoriesRepository.getFakeCategoriesWithImages(10));
    }
}