package com.hawi.lukman.cataloguemovieuiux;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.getColumnInt;
import static com.hawi.lukman.cataloguemovieuiux.db.DatabaseContract.getColumnString;

public class MovieItems implements Parcelable {
    private int id;
    private String judul;
    private String deskripsi;
    private String gambar;
    private String release;

    private static String ID = "id";
    private static String JUDUL = "judul";
    private static String DESKRIPSI = "deskripsi";
    private static String GAMBAR = "gambar";
    private static String RELEASE = "rilis";

    @Override
    public String toString() {
        return "MovieItems{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", gambar='" + gambar + '\'' +
                ", release='" + release + '\'' +
                '}';
    }

    public MovieItems() {
    }

    public MovieItems(JSONObject object) throws JSONException {

        int id = object.getInt("id");
        String judul = object.getString("title");
        String deskripsi = object.getString("overview");
        String gambar = object.getString("poster_path");
        String release = object.getString("release_date");

        gambar = "http://image.tmdb.org/t/p/w185/" + gambar;
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.release = release;
    }

    public MovieItems(Cursor cursor) {
//        this.id = getColumnInt(cursor, ID);
//        this.title = getColumnString(cursor, DatabaseContract.NoteColumns.TITLE);
//        this.description = getColumnString(cursor, DatabaseContract.NoteColumns.DESCRIPTION);
//        this.date = getColumnString(cursor, DatabaseContract.NoteColumns.DATE);

        this.id = getColumnInt(cursor, ID);
        this.judul = getColumnString(cursor, JUDUL);
        this.deskripsi = getColumnString(cursor, DESKRIPSI);
        this.gambar = getColumnString(cursor, GAMBAR);
        this.release = getColumnString(cursor, RELEASE);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public String getRelease() {
        return release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.deskripsi);
        dest.writeString(this.gambar);
        dest.writeString(this.release);
    }

    protected MovieItems(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.deskripsi = in.readString();
        this.gambar = in.readString();
        this.release = in.readString();
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}

