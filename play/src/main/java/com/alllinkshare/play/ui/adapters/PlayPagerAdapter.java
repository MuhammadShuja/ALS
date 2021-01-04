package com.alllinkshare.play.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alllinkshare.play.models.Category;
import com.alllinkshare.play.ui.fragments.PostsFragment;

import java.util.List;

public class PlayPagerAdapter extends FragmentStateAdapter {

    private List<Category> items;

    public PlayPagerAdapter(Fragment fragment, List<Category> items) {
        super(fragment);
        this.items = items;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return PostsFragment.newInstance(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Category> categories){
        this.items.clear();
        this.items.addAll(categories);
        notifyDataSetChanged();
    }
}