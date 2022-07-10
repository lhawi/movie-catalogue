package com.hawi.lukman.cataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hawi.lukman.cataloguemovieuiux.CustomOnItemClickListener;
import com.hawi.lukman.cataloguemovieuiux.MovieItems;
import com.hawi.lukman.cataloguemovieuiux.R;
import com.hawi.lukman.cataloguemovieuiux.search.DetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieItems> movieLists;
    private Context context;

    public MovieAdapter(List<MovieItems> movieLists, Context context) {
        // generate constructors to initialise the List and Context objects
        this.movieLists = movieLists;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_now_playing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // this method will bind the data to the ViewHolder from hence it'll be shown to other Views
        final MovieItems movList = movieLists.get(position);
        holder.title.setText(movList.getJudul());
        holder.overview.setText(movList.getDeskripsi());

        String release_date = movList.getRelease();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            holder.date.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/" + movList.getGambar())
                .into(holder.poster);

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {

            @Override
            public void onItemClicked(View view, int position) {
                System.err.println();
                MovieItems movieList = movieLists.get(position);
                Context context = view.getContext();
                Intent Intent = new Intent(context, DetailActivity.class);
                Intent.putExtra(DetailActivity.EXTRA_JUDUL, movieList.getJudul());
                Intent.putExtra(DetailActivity.EXTRA_OVERVIEW, movieList.getDeskripsi());
                Intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, movieList.getRelease());
                Intent.putExtra(DetailActivity.EXTRA_POSTER, movieList.getGambar());
                context.startActivity(Intent);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share: " + movList.getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItems movieList = movieLists.get(position);
                Intent Intent = new Intent(context, DetailActivity.class);
                Intent.putExtra(DetailActivity.EXTRA_JUDUL, movieList.getJudul());
                Intent.putExtra(DetailActivity.EXTRA_OVERVIEW, movieList.getDeskripsi());
                Intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, movieList.getRelease());
                Intent.putExtra(DetailActivity.EXTRA_POSTER, movieList.getGambar());
                context.startActivity(Intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // deklarasi View objects
        public TextView title, overview, date;
        ImageView poster;
        public Button btnDetail, btnShare;
        public LinearLayout cvDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            // init the View objects
            title = itemView.findViewById(R.id.tv_item_name);
            poster = itemView.findViewById(R.id.img_item_photo);
            overview = itemView.findViewById(R.id.tv_item_overview);
            date = itemView.findViewById(R.id.tv_item_date);
            btnDetail = itemView.findViewById(R.id.btn_set_detail);
            btnShare = itemView.findViewById(R.id.btn_set_share);
            cvDetail = itemView.findViewById(R.id.cv_movie);
        }

    }
}
