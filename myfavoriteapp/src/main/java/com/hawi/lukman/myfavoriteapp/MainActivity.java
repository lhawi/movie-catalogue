package com.hawi.lukman.myfavoriteapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hawi.lukman.myfavoriteapp.adapter.FavAdapter;
import com.hawi.lukman.myfavoriteapp.db.DatabaseContract;

import static com.hawi.lukman.myfavoriteapp.db.DatabaseContract.CONTENT_URI;
import static com.hawi.lukman.myfavoriteapp.db.DatabaseContract.getColumnString;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private FavAdapter favAdapter;
    ListView lvFav;
    private final int LOAD_FAV = 110;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().
                    setTitle("Favorite Movie");
        }

        lvFav = findViewById(R.id.LV);
        favAdapter = new FavAdapter(this, null, true);
        lvFav.setAdapter(favAdapter);
        lvFav.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_FAV, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAV, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("", "onCreateLoader: ");
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
        // return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favAdapter.swapCursor(data);
        Log.d("", "onLoadFinished: ");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) favAdapter.getItem(i);

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_JUDUL, (getColumnString(cursor, DatabaseContract.NoteColumns.TITLE)));
        intent.putExtra(DetailActivity.EXTRA_OVERVIEW, (getColumnString(cursor, DatabaseContract.NoteColumns.DESCRIPTION)));
        intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, (getColumnString(cursor, DatabaseContract.NoteColumns.DATE)));
        intent.putExtra(DetailActivity.EXTRA_POSTER, (getColumnString(cursor, DatabaseContract.NoteColumns.IMAGE)));

        startActivity(intent);
    }
}
