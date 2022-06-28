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
import com.example.appmusicmp3.data.models.ChuDe;
import com.example.appmusicmp3.presentation.adapter.DanhsachAllChudeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachallchudeActivity extends AppCompatActivity {

    RecyclerView recyclerViewAllchude;
    Toolbar toolbarAllchude;
    DanhsachAllChudeAdapter danhsachAllChudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachallchude);
        init();
        getData();
    }

    private void getData() {
        APIService apiService = AppConstant.getService();
        Call<List<ChuDe>> callback = apiService.getAllChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> mangchude = (ArrayList<ChuDe>) response.body();
                danhsachAllChudeAdapter = new DanhsachAllChudeAdapter(DanhsachallchudeActivity.this, mangchude);
                recyclerViewAllchude.setLayoutManager(new GridLayoutManager(DanhsachallchudeActivity.this, 2));
                recyclerViewAllchude.setAdapter(danhsachAllChudeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewAllchude = findViewById(R.id.recyclerviewAllchude);
        toolbarAllchude = findViewById(R.id.toolbarAllchude);
        setSupportActionBar(toolbarAllchude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbarAllchude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
