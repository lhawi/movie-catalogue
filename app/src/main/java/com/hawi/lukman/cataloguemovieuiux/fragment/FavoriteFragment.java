package com.hawi.lukman.cataloguemovieuiux.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.hawi.lukman.cataloguemovieuiux.ItemClickSupport;
import com.hawi.lukman.cataloguemovieuiux.MainActivity;
import com.hawi.lukman.cataloguemovieuiux.R;
import com.hawi.lukman.cataloguemovieuiux.adapter.FavoriteAdapter;
import com.hawi.lukman.cataloguemovieuiux.db.FavoriteHelper;
import com.hawi.lukman.cataloguemovieuiux.entity.Favorite;
import com.hawi.lukman.cataloguemovieuiux.search.DetailActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    @BindView(R.id.recyclerview_fav)
    RecyclerView recyclerView;
    FavoriteAdapter favAdapter;
    FavoriteHelper favHelper;
    Context con;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity())
                .setTitle(getString(R.string.favorite));
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        con = getActivity();
        favHelper = new FavoriteHelper(con);
        favAdapter = new FavoriteAdapter(con);


        recyclerView.setLayoutManager(new LinearLayoutManager(con));

        recyclerView.setAdapter(favAdapter);

        favHelper.open();
        final ArrayList<Favorite> fav = favHelper.query();
        for (int i = 0; i < fav.size(); i++) {
            Log.d("->JUDUL", fav.get(i).getTitle());
            Log.d("->DESKRIPSI", fav.get(i).getDescription());
            Log.d("->DATE", fav.get(i).getDate());
//            Log.d("->LINK", fav.get(i).getImage());
            Log.d("->", "");
        }
        favHelper.close();
        favAdapter.addItem(fav);

        showRecyclerList(fav);

        return view;
    }

    private void showRecyclerList(final ArrayList<Favorite> fav) {
        recyclerView.setLayoutManager(new LinearLayoutManager(con));

        favAdapter.addItem(fav);
        recyclerView.setAdapter(favAdapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_JUDUL, fav.get(position).getTitle());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_OVERVIEW, fav.get(position).getDescription());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, fav.get(position).getDate());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_POSTER, fav.get(position).getImage());
                Toast.makeText(getActivity(), fav.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                getActivity().startActivity(moveWithDataIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        favHelper.open();
        final ArrayList<Favorite> fav = favHelper.query();
        for (int i = 0; i < fav.size(); i++) {
            Log.d("->JUDUL", fav.get(i).getTitle());
            Log.d("->DESKRIPSI", fav.get(i).getDescription());
            Log.d("->DATE", fav.get(i).getDate());
//            Log.d("->LINK", fav.get(i).getImage());
            Log.d("->", "");
        }
        favHelper.close();

        favAdapter.addItem(fav);
    }
}