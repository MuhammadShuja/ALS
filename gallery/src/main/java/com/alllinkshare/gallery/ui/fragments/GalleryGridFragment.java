package com.alllinkshare.gallery.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alllinkshare.gallery.R;
import com.alllinkshare.gallery.models.Image;
import com.alllinkshare.gallery.ui.adapters.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

public class GalleryGridFragment extends Fragment {

    private static final String IMAGE_DATA = "image_data";
    private static final String GRID_COLUMNS = "columns";

    private List<Image> images;
    private int columnCount;
    private GalleryAdapter galleryAdapter;
    private ProgressBar progressBar;

    private View rootView;

    public GalleryGridFragment() {
        // Required empty public constructor
    }

    public static GalleryGridFragment newInstance(ArrayList<Image> imageData, int gridColumns) {
        GalleryGridFragment fragment = new GalleryGridFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(IMAGE_DATA, imageData);
        args.putInt(GRID_COLUMNS, gridColumns);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            images = getArguments().getParcelableArrayList(IMAGE_DATA);
            columnCount = getArguments().getInt(GRID_COLUMNS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_gallery_grid, container, false);

        initGallery();

        return rootView;
    }

    private void initGallery(){
        int resource = R.layout.card_gallery_image;
        if(columnCount == 1){
            resource = R.layout.card_gallery_image_full_height;
        }
        galleryAdapter = new GalleryAdapter(getActivity(), images, resource, new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String image) {

            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columnCount);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(galleryAdapter);
    }
}