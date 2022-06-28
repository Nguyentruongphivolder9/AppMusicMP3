package com.example.appmusicmp3.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.Album;
import com.example.appmusicmp3.presentation.adapter.DanhsachAllAlbumAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachallAlbumActivity extends AppCompatActivity {

    RecyclerView recyclerViewAllAlbum;
    Toolbar toolbarAllAlbum;
    DanhsachAllAlbumAdapter danhsachAllAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachall_album);
        init();
        getData();
    }

    private void getData() {
        APIService apiService = AppConstant.getService();
        Call<List<Album>> callback = apiService.getAllAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> mangAlbum = (ArrayList<Album>) response.body();
                danhsachAllAlbumAdapter = new DanhsachAllAlbumAdapter(DanhsachallAlbumActivity.this, mangAlbum);
                recyclerViewAllAlbum.setLayoutManager(new GridLayoutManager(DanhsachallAlbumActivity.this,2));
                recyclerViewAllAlbum.setAdapter(danhsachAllAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void init(){
        recyclerViewAllAlbum = findViewById(R.id.rcvAllAlbum);
        toolbarAllAlbum = findViewById(R.id.toolbarAllAlbum);
        setSupportActionBar(toolbarAllAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Album");
        toolbarAllAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
