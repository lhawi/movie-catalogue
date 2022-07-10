package com.hawi.lukman.cataloguemovieuiux.search;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.RecyclerView;

import com.hawi.lukman.cataloguemovieuiux.BuildConfig;
import com.hawi.lukman.cataloguemovieuiux.MovieItems;
import com.hawi.lukman.cataloguemovieuiux.adapter.MovieAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;

    private String mKumpulanMovie;

    public SearchAsyncTaskLoader(final Context context, String kumpulanMovie) {
        super(context);

        onContentChanged();
        this.mKumpulanMovie = kumpulanMovie;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<MovieItems> data) {
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemses = new ArrayList<>();

        final ArrayList<MovieItems> movieItems_ = new ArrayList<>();
        String url = BuildConfig.MOVIE_URL_SEARCH+"?api_key=" +
                BuildConfig.MOVIE_API_KEY+"&language=en-US&query="+mKumpulanMovie;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode,Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        JSONObject movie = array.getJSONObject(i);
                        MovieItems movies = new MovieItems();
                        movies.setJudul(movie.getString("title"));
                        movies.setDeskripsi(movie.getString("overview"));
                        movies.setRelease(movie.getString("release_date"));
                        movies.setGambar(movie.getString("poster_path"));
                        movieItemses.add(movies);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        });
        return movieItemses;
    }


}
