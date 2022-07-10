package com.hawi.lukman.cataloguemovieuiux.adapter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hawi.lukman.cataloguemovieuiux.R;
import com.hawi.lukman.cataloguemovieuiux.entity.Favorite;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavHolder> {
    private ArrayList<Favorite> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public FavoriteAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FavHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items, parent, false);
        return new FavHolder(view);
    }

    public void addItem(ArrayList<Favorite> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(FavHolder holder, int position) {
        holder.Judul.setText(mData.get(position).getTitle());
        holder.Deskripsi.setText(mData.get(position).getDescription());
        holder.Rilis.setText(mData.get(position).getDate());

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/" + mData.get(position).getImage())
                .into(holder.Gambar);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FavHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView Judul;
        @BindView(R.id.tvDescription)
        TextView Deskripsi;
        @BindView(R.id.tvDate)
        TextView Rilis;
        @BindView(R.id.imgMovie)
        ImageView Gambar;

        FavHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}