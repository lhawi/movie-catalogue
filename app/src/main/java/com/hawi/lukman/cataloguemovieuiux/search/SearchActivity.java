package com.hawi.lukman.cataloguemovieuiux.search;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hawi.lukman.cataloguemovieuiux.MovieItems;
import com.hawi.lukman.cataloguemovieuiux.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {


    ListView listView;
    MovieAdapter adapter;
    EditText editMovie;
    Button buttonCari;

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private static final String position = "positiion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        getSupportActionBar();
        setTitle("Catalogue Movie");

        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItems item = (MovieItems) parent.getItemAtPosition(position);

                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_JUDUL, item.getJudul());
                intent.putExtra(DetailActivity.EXTRA_OVERVIEW, item.getDeskripsi());
                intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, item.getRelease());
                intent.putExtra(DetailActivity.EXTRA_POSTER, item.getGambar());

                startActivity(intent);
            }
        });

        editMovie = findViewById(R.id.edt_judul);
        buttonCari = findViewById(R.id.btn_cari);

        buttonCari.setOnClickListener(myListener);

        String movie = editMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE, movie);

        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {

        String kumpulanMovie = "";
        if (args != null) {
            kumpulanMovie = args.getString(EXTRA_MOVIE);
        }

        return new SearchAsyncTaskLoader(this, kumpulanMovie);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String movie = editMovie.getText().toString();

            if (TextUtils.isEmpty(movie)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_MOVIE, movie);
            getSupportLoaderManager().restartLoader(0, bundle, SearchActivity.this);
        }
    };
}