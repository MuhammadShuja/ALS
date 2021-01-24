package com.alllinkshare.catalog.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alllinkshare.catalog.R;
import com.alllinkshare.catalog.models.Listing;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FormActionsAdapter extends RecyclerView.Adapter<FormActionsAdapter.ViewHolder> {

    private Context mContext;
    private List<Listing.FormAction> items;
    private OnItemClickListener listener;

    public FormActionsAdapter(Context mContext, List<Listing.FormAction> items, OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_form_action, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.button_form_action);
        }

        public void bind(final Listing.FormAction formAction, final OnItemClickListener listener) {

            button.setText(formAction.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(formAction);
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Listing.FormAction> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Listing.FormAction> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Listing.FormAction formAction);
    }
}