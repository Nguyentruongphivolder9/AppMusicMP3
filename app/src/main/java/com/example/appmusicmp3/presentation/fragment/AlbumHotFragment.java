package com.example.appmusicmp3.presentation.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.Album;
import com.example.appmusicmp3.presentation.adapter.AlbumHotAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AlbumHotFragment extends Fragment {

   View view;
   RecyclerView recyclerViewAlbum;
   TextView tvXemthemAlbum;
   AlbumHotAdapter albumAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_album, container, false) ;
        recyclerViewAlbum = view.findViewById(R.id.recyclerviewAlbum);
        tvXemthemAlbum = view.findViewById(R.id.textviewXemthemAlbum);
        init();
        return view;
    }

    private void init() {
        APIService apiService = AppConstant.getService();
        Call<List<Album>> callAlbum = apiService.getAlbumHot();
        callAlbum.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumsArraylist = (ArrayList<Album>) response.body();
                albumAdapter = new AlbumHotAdapter(getActivity(), albumsArraylist);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewAlbum.setLayoutManager(linearLayoutManager);
                recyclerViewAlbum.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}
