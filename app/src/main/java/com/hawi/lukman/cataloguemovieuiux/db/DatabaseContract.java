package com.hawi.lukman.cataloguemovieuiux.db;


import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAV = "Favorite";

    public static final class NoteColumns implements BaseColumns {
        //title
        public static String TITLE = "title";
        //description
        public static String DESCRIPTION = "description";
        //date
        public static String DATE = "date";
        //image
        public static String IMAGE = "image";

    }

    ////////////////////////////////////////////////////
    public static final String AUTHORITY = "com.hawi.lukman.cataloguemovieuiux";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAV)
            .build();

    public final static String PREF_NAME = "reminderPreferences";
    public final static String KEY_REMINDER_Daily = "DailyTime";
    public final static String KEY_REMINDER_MESSAGE_Release = "reminderMessageRelease";
    public final static String KEY_REMINDER_MESSAGE_Daily = "reminderMessageDaily";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE= "type";
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}

