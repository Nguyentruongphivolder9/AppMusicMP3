package com.example.appmusicmp3.data.datasources.remote;

import com.example.appmusicmp3.data.models.QuangCao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("songbanner.php")
    Call<List<QuangCao>> getDataBanner();
}
