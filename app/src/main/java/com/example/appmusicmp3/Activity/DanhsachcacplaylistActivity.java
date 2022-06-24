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
import com.example.appmusicmp3.data.models.Playlist;
import com.example.appmusicmp3.presentation.adapter.DanhsachcacplaylistAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachcacplaylistActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewDanhsachcacplaylist;
    DanhsachcacplaylistAdapter danhsachcacplaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachcacplaylist);
        event();
        init();
        getData();
    }

    private void getData() {
        APIService apiService = AppConstant.getService();
        Call<List<Playlist>> callback = apiService.getDanhsachcacplaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> mangplaylist = (ArrayList<Playlist>) response.body();
                danhsachcacplaylistAdapter = new DanhsachcacplaylistAdapter(DanhsachcacplaylistActivity.this, mangplaylist);
                recyclerViewDanhsachcacplaylist.setLayoutManager(new GridLayoutManager(DanhsachcacplaylistActivity.this, 2));
                recyclerViewDanhsachcacplaylist.setAdapter(danhsachcacplaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void init() {
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setTitle("Play Lists");
       toolbar.setTitleTextColor(getResources().getColor(R.color.black));
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }

    private void event() {
        toolbar = findViewById(R.id.toolbarDanhsachcacplaylist);
        recyclerViewDanhsachcacplaylist = findViewById(R.id.recyclerviewDanhsachcacplaylist);
    }
}
