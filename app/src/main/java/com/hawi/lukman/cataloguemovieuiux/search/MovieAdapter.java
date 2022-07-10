package com.hawi.lukman.cataloguemovieuiux.search;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hawi.lukman.cataloguemovieuiux.MovieItems;
import com.hawi.lukman.cataloguemovieuiux.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    private String final_overview;

    private final static String URL_POSTER = "http://image.tmdb.org/t/p/w780";


    public MovieAdapter(FragmentActivity context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.movie_items, null);
            holder.textViewJudul = convertView.findViewById(R.id.tvTitle);
            holder.textViewTanggal = convertView.findViewById(R.id.tvDate);
            holder.textViewDescription = convertView.findViewById(R.id.tvDescription);
            holder.imgPoster = convertView.findViewById(R.id.imgMovie);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewJudul.setText(mData.get(position).getJudul());

        String overview = mData.get(position).getDeskripsi();
        if (TextUtils.isEmpty(overview)) {
            final_overview = "No data";
        } else {
            final_overview = overview;
        }
        holder.textViewDescription.setText(final_overview);

        String retrievedDate = mData.get(position).getRelease();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrievedDate);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String date_of_release = new_date_format.format(date);
            holder.textViewTanggal.setText(date_of_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide
                .with(context)
                .load("http://image.tmdb.org/t/p/w780/" + mData.get(position).getGambar())
                .into(holder.imgPoster);
        return convertView;
    }

    private static class ViewHolder {
        TextView textViewJudul;
        TextView textViewTanggal;
        TextView textViewDescription;
        ImageView imgPoster;
    }
}