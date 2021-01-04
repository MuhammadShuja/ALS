package com.alllinkshare.play.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.play.R;
import com.alllinkshare.play.models.Post;
import com.alllinkshare.play.ui.adapters.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    private static final String POSTS_FILTER = "filter";
    private String postsFilter;

    private PostsAdapter postsAdapter;

    private View rootView;

    public PostsFragment() {
        // Required empty public constructor
    }

    public static PostsFragment newInstance(String postsFilter) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putString(POSTS_FILTER, postsFilter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postsFilter = getArguments().getString(POSTS_FILTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_posts, container, false);

        initPosts();

        return rootView;
    }

    private void initPosts(){
        List<Post> posts = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            posts.add(new Post());
        }

        postsAdapter = new PostsAdapter(getContext(), posts, new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {

            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postsAdapter);

    }
}