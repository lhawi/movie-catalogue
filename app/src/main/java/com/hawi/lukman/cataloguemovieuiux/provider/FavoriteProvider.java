package com.hawi.lukman.cataloguemovieuiux.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;


import com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract;
import com.hawi.lukman.cataloguemovieuiux.db.FavoriteHelper;

import java.util.Objects;

import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.AUTHORITY;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.CONTENT_URI;

public class FavoriteProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        // content://com.hawi.lukman.cataloguemovieuiux/favorite
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAV, NOTE);

        // content://com.hawi.lukman.cataloguemovieuiux/favorite/id
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_FAV + "/#",
                NOTE_ID);
    }

    private FavoriteHelper favHelper;

    @Override
    public boolean onCreate() {
        favHelper = new FavoriteHelper(getContext());
        favHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = favHelper.queryProvider();
                break;
            case NOTE_ID:
                cursor = favHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added;

        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = favHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = favHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted = favHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

}

