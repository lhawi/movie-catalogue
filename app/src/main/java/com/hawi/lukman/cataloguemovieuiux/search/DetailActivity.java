package com.hawi.lukman.cataloguemovieuiux.search;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hawi.lukman.cataloguemovieuiux.R;
import com.hawi.lukman.cataloguemovieuiux.db.FavoriteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.CONTENT_URI;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.DATE;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.IMAGE;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.TITLE;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvJudul, tvOverview, tvReleaseDate;
    ImageView imgPoster, icon;

    public static String EXTRA_JUDUL = "Judulnya";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_POSTER = "extra_poster_jpg";

    FavoriteHelper favHelper = new FavoriteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        icon = findViewById(R.id.fav_icon);
        tvJudul = findViewById(R.id.tv_detail_judul);
        tvOverview = findViewById(R.id.tv_detail_overview);
        tvReleaseDate = findViewById(R.id.tv_detail_releasedate);
        imgPoster = findViewById(R.id.img_item_photo);

        EXTRA_JUDUL = getIntent().getStringExtra(EXTRA_JUDUL);
        EXTRA_OVERVIEW = getIntent().getStringExtra(EXTRA_OVERVIEW);
        EXTRA_POSTER = getIntent().getStringExtra(EXTRA_POSTER);
        EXTRA_RELEASE_DATE = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat new_format = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault());

        try {
            Date date = date_format.parse(EXTRA_RELEASE_DATE);
            tvReleaseDate.setText(new_format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide
                .with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w780/" + EXTRA_POSTER)
                .into(imgPoster);

        tvJudul.setText(EXTRA_JUDUL);
        tvOverview.setText(EXTRA_OVERVIEW);

        getSupportActionBar();
        setTitle("Catalogue Movie");

        //force close pakai ini
        // if (getSupportActionBar() != null) {
        //      getSupportActionBar().
        //        setTitle("Catalogue Movie");}

        favHelper.open();
        boolean checked = favHelper.check(EXTRA_JUDUL);
        favHelper.close();
        if (checked) {
            icon.setImageResource(R.drawable.ic_love_blue);
        } else {
            icon.setImageResource(R.drawable.ic_love_border);
        }

        icon.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fav_icon) {
            favHelper.open();
            try {

                boolean checked = favHelper.check(EXTRA_JUDUL);

                if (checked) {

                    favHelper.delete(EXTRA_JUDUL);
                    favHelper.deleteProvider(EXTRA_JUDUL);

                    Toast.makeText(this, getString(R.string.remove), Toast.LENGTH_SHORT).show();
                    icon.setImageResource(R.drawable.ic_love_border);
                } else {


                    ContentValues values = new ContentValues();

                    values.put(TITLE, EXTRA_JUDUL);
                    values.put(DESCRIPTION, EXTRA_OVERVIEW);
                    values.put(DATE, EXTRA_RELEASE_DATE);
                    values.put(IMAGE, EXTRA_POSTER);

                    getContentResolver().insert(CONTENT_URI, values);
                    setResult(110);


                    Toast.makeText(this, getString(R.string.add), Toast.LENGTH_SHORT).show();
                    icon.setImageResource(R.drawable.ic_love_blue);
                }
            } catch (Exception e) {

            }
            favHelper.close();
        }

    }
}
