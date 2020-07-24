package com.example.loginapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.loginapp.Model.Images;
import com.example.loginapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Images> mRegion;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private Context context;
    public View v2;

    public DataAdapter(Context context) {
        this.context = context;
        mRegion = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                v2 = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.no_results, parent, false);
                viewHolder = new NoResultVH(v3);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item, parent, false);
        viewHolder = new ImagesViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Images result = mRegion.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ImagesViewHolder imagesViewHolder = (ImagesViewHolder) holder;
                imagesViewHolder.id.setText("" + result.getId());
                imagesViewHolder.title.setText("Title-: " + result.getTitle());
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_autorenew_24)
                        .error(R.drawable.ic_baseline_error_outline_24);
                Log.e("WW", mRegion.get(position).getUrl());


//                Glide.with(context).load(result.getUrl()).apply(options).into(imagesViewHolder.img);


                Picasso.get().load(result.getUrl()).into(imagesViewHolder.img);
                Log.e("", mRegion.get(position).getUrl());
                break;
            case LOADING:
        }
    }

    @Override
    public int getItemCount() {
        return mRegion.size();
    }


    public void add(Images g) {
        mRegion.add(g);
        notifyItemInserted(mRegion.size() - 1);
    }

    public void addAll(List<Images> listRegion) {
        for (Images result : listRegion) {
            add(result);
        }
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView id;
        TextView title;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            id = itemView.findViewById(R.id.id);
            title = itemView.findViewById(R.id.title);
        }
    }

    public class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
            v2.setVisibility(View.GONE);
        }
    }

    protected static class NoResultVH extends RecyclerView.ViewHolder {
        public NoResultVH(View itemView) {
            super(itemView);
        }
    }
}