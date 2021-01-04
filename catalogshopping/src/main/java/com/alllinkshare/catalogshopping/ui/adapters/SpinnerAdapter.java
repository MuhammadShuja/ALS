package com.alllinkshare.catalogshopping.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alllinkshare.catalogshopping.R;
import com.alllinkshare.catalogshopping.models.SpinnerItem;

import java.util.List;
import java.util.Objects;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {
    private LayoutInflater layoutInflater;

    public SpinnerAdapter(@NonNull Context context, @NonNull List<SpinnerItem> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
//        if(position == 0)
//            return initView(position, view, parent, R.layout.spinner_default);

        return initView(position, view, parent, R.layout.spinner_normal);
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
//        if(position == 0)
//            return initView(position, view, parent, R.layout.spinner_default_dropdown);

        return initView(position, view, parent, R.layout.spinner_normal_dropdown);
    }

    private View initView(int position, View view, ViewGroup parent, int resourceId) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(resourceId, null);

            holder.name = view.findViewById(R.id.name);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(Objects.requireNonNull(getItem(position)).getName());
        return view;
    }

    public void setData(List<SpinnerItem> list){
        super.clear();
        super.addAll(list);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView name;
    }

}