package com.example.appmusicmp3.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.ChuDe;
import com.example.appmusicmp3.data.models.TheLoai;
import com.example.appmusicmp3.presentation.adapter.DanhsachtheloaithepchudeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachtheloaithechudeActivity extends AppCompatActivity {

    ChuDe chuDe;
    RecyclerView recyclerViewTheloaitheochuede;
    Toolbar toolbarTheloaitheochude;
    DanhsachtheloaithepchudeAdapter danhsachtheloaithepchudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtheloaithechude);
        GetInten();
        init();
        getData();
    }

    private void getData() {
        APIService apiService = AppConstant.getService();
        Call<List<TheLoai>> callback = apiService.getTheloaitheochude(chuDe.getIdChuDe());
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> mangTheloai = (ArrayList<TheLoai>) response.body();
                danhsachtheloaithepchudeAdapter = new DanhsachtheloaithepchudeAdapter(DanhsachtheloaithechudeActivity.this, mangTheloai);
                recyclerViewTheloaitheochuede.setLayoutManager(new GridLayoutManager(DanhsachtheloaithechudeActivity.this,2));
                recyclerViewTheloaitheochuede.setAdapter(danhsachtheloaithepchudeAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewTheloaitheochuede = findViewById(R.id.rcvTheloaitheochude);
        toolbarTheloaitheochude = findViewById(R.id.toolbarTheloaitheochude);
        setSupportActionBar(toolbarTheloaitheochude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbarTheloaitheochude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetInten() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")) {
            chuDe = (ChuDe) intent.getSerializableExtra("chude");

        }
    }
}
