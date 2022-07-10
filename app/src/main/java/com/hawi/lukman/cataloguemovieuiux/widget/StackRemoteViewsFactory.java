package com.hawi.lukman.cataloguemovieuiux.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hawi.lukman.cataloguemovieuiux.MovieItems;
import com.hawi.lukman.cataloguemovieuiux.R;
import com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.DATE;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.IMAGE;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.NoteColumns.TITLE;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<MovieItems> mMovies = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        String[] projection = new String[]{BaseColumns._ID, TITLE, DESCRIPTION, DATE, IMAGE};

        cursor = mContext.getContentResolver().query(
                DatabaseContract.CONTENT_URI,
                projection,
                null,
                null,
                null);

        Binder.restoreCallingIdentity(identityToken);

        if (cursor.moveToFirst()) {
            do {
                MovieItems item = new MovieItems();

                item.setId(cursor.getInt(0));
                item.setJudul(cursor.getString(1));
                item.setDeskripsi(cursor.getString(2));
                item.setRelease(cursor.getString(3));
                item.setGambar(cursor.getString(4));

                mMovies.add(item);
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w185" + mMovies.get(position).getGambar())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Load Error", "error");
        }

        rv.setImageViewBitmap(R.id.img_widget_poster, bmp);

        Bundle extras = new Bundle();
        extras.putInt(MovieStackWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
