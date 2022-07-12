package com.example.appmusicmp3.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appmusicmp3.R;
import com.example.appmusicmp3.data.datasources.remote.APIService;
import com.example.appmusicmp3.data.datasources.remote.AppConstant;
import com.example.appmusicmp3.data.models.Album;
import com.example.appmusicmp3.data.models.BaiHat;
import com.example.appmusicmp3.data.models.Playlist;
import com.example.appmusicmp3.data.models.QuangCao;
import com.example.appmusicmp3.data.models.TheLoai;
import com.example.appmusicmp3.presentation.adapter.DanhsachbaihatAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDanhsachbaihat;
    ExtendedFloatingActionButton extendedFloatingActionButtonRandom;
    ImageView imgDanhsachcakhuc;
    QuangCao quangCao;
    ArrayList<BaiHat> mangbaihat;
    DanhsachbaihatAdapter danhsachbaihatAdapter;
    Playlist playlist;
    TheLoai theLoai;
    Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dataIntent();
        event();
        init();
        if (quangCao != null && !quangCao.getTenbaihat().equals("")) {
            setValueInview(quangCao.getTenbaihat(), quangCao.getHinhbaihat());
            getDataQuangcao(quangCao.getIdQuangCao());
        }
        if (playlist != null && !playlist.getTen().equals("")) {
            setValueInview(playlist.getTen(), playlist.getIcon());
            getDataplaylist(playlist.getIdPlayList());
        }
        if (theLoai != null && !theLoai.getTenTheLoai().equals("")) {
            setValueInview(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            getDataTheLoai(theLoai.getIdTheLoai());
        }
        if (album != null && !album.getTenAlbum().equals("")) {
            setValueInview(album.getTenAlbum(), album.getHinhAnhAlbum());
            getDataAlbum(album.getIdAlbum());
        }
    }

    private void getDataAlbum(String idAlbum) {
        APIService apiService = AppConstant.getService();
        Call<List<BaiHat>> callback = apiService.getDanhsachbaihattheoAlbum(idAlbum);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this, mangbaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataTheLoai(String idtheloai){
        APIService apiService = AppConstant.getService();
        Call<List<BaiHat>> callback = apiService.getDanhsachbaihattheotheloai(idtheloai);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this, mangbaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataplaylist(String idplaylist) {
        APIService apiService = AppConstant.getService();
        Call<List<BaiHat>> callback = apiService.getDanhsachbaihattheoplaylist(idplaylist);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this, mangbaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void setValueInview(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Glide.with(this).load(hinh)
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15)))
                .into(imgDanhsachcakhuc);
    }

    private void getDataQuangcao(String idquangcao) {
        APIService apiService = AppConstant.getService();
        Call<List<BaiHat>> callback = apiService.getdanhsachbaihattheoquangcao(idquangcao);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this, mangbaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        extendedFloatingActionButtonRandom.setEnabled(false);
    }

    private void event() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        toolbar = findViewById(R.id.toolbarDanhsach);
        recyclerViewDanhsachbaihat = findViewById(R.id.recyclerviewdanhsachbaihat);
        extendedFloatingActionButtonRandom = findViewById(R.id.extendedFloatingActionButtonRandom);
        imgDanhsachcakhuc = findViewById(R.id.imageviewDanhsachcakhuc);
    }

    private void dataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("banner")) {
                quangCao = (QuangCao) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")) {
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")) {
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("album")) {
                album = (Album) intent.getSerializableExtra("album");
            }
        }
    }
    private void evenClick() {
        extendedFloatingActionButtonRandom.setEnabled(true);
        extendedFloatingActionButtonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhsachbaihatActivity.this, PlayNhacActivity.class);
                intent.putExtra("cacbaihat",mangbaihat);
                startActivity(intent);
            }
        });
    }
}
