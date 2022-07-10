package com.hawi.lukman.myfavoriteapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    TextView tvJudul, tvOverview, tvReleaseDate;
    ImageView imgPoster, icon;

    public static String EXTRA_JUDUL = "judulnya";
    public static String EXTRA_OVERVIEW = "deskripsinya";
    public static String EXTRA_RELEASE_DATE = "tanggalnya";
    public static String EXTRA_POSTER = "Gambarnya";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().
                    setTitle("Favorite Movie");
        }

        tvJudul = (TextView)findViewById(R.id.tv_detail_judul);
        tvOverview = (TextView)findViewById(R.id.tv_detail_overview);
        tvReleaseDate = (TextView)findViewById(R.id.tv_detail_releasedate);
        imgPoster = (ImageView)findViewById(R.id.img_item_photo);

        String title = getIntent().getStringExtra(EXTRA_JUDUL);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String poster_jpg = getIntent().getStringExtra(EXTRA_POSTER);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MM dd, yyyy" , Locale.getDefault());
            String date_of_release = new_date_format.format(date);
            tvReleaseDate.setText(date_of_release);
        }catch (ParseException e){
            e.printStackTrace();
        }
        tvJudul.setText(title);
        tvOverview.setText(overview);

        Glide
                .with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w780/"+poster_jpg)
                .into(imgPoster);

    }

}

