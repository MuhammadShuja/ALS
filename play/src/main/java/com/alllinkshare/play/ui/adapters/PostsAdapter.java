package com.alllinkshare.play.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.play.R;
import com.alllinkshare.play.models.Post;
import com.bumptech.glide.Glide;
import java.util.List;

public class PostsAdapter  extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> items;
    private PostsAdapter.OnItemClickListener listener;

    public PostsAdapter(Context mContext, List<Post> items, PostsAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_post, viewGroup, false);

        return new PostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumbnail;
        private TextView name;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(final Post post, final PostsAdapter.OnItemClickListener listener) {
//            Glide
//                    .with(mContext)
//                    .load(mContext.getResources().getIdentifier(category.getThumbnail(), "drawable", mContext.getPackageName()))
//                    .into(thumbnail);
//
//            name.setText(category.getName());
//            description.setText(category.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(post);
                }
            });
        }
    }

    public void clearData(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Post> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addData(List<Post> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Post post);
    }
}