package com.hawi.lukman.myfavoriteapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hawi.lukman.myfavoriteapp.R;
import com.hawi.lukman.myfavoriteapp.entity.Favorite;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hawi.lukman.myfavoriteapp.db.DatabaseContract.NoteColumns.DATE;
import static com.hawi.lukman.myfavoriteapp.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.hawi.lukman.myfavoriteapp.db.DatabaseContract.NoteColumns.IMAGE;
import static com.hawi.lukman.myfavoriteapp.db.DatabaseContract.NoteColumns.TITLE;
import static com.hawi.lukman.myfavoriteapp.db.DatabaseContract.getColumnString;

public class FavAdapter extends CursorAdapter {
    private ArrayList<Favorite> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    @BindView(R.id.tvTitle)
    TextView Judul;
    @BindView(R.id.tvDescription)
    TextView Deskripsi;
    @BindView(R.id.tvDate)
    TextView Rilis;
    @BindView(R.id.imgMovie)
    ImageView Gambar;

    public FavAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_movie, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ButterKnife.bind(this, view);
        Judul.setText(getColumnString(cursor, TITLE));
        Deskripsi.setText(getColumnString(cursor, DESCRIPTION));
        Rilis.setText(getColumnString(cursor, DATE));

        final String loadPoster = "http://image.tmdb.org/t/p/w500" + getColumnString(cursor, IMAGE);

        String image = (getColumnString(cursor, IMAGE));
        Log.d("LINK: ", image);
        Glide.with(context)
                .load(loadPoster)
                .into(Gambar);
    }

}