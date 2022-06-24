package com.example.appmusicmp3.data.datasources.remote;

import com.example.appmusicmp3.data.models.Album;
import com.example.appmusicmp3.data.models.BaiHat;
import com.example.appmusicmp3.data.models.Playlist;
import com.example.appmusicmp3.data.models.QuangCao;
import com.example.appmusicmp3.data.models.TheLoaiToDay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("songbanner.php")
    Call<List<QuangCao>> getDataBanner();

    @GET("playlistforcurrent.php")
    Call<List<Playlist>> getPlaylistCurrentDay();

    @GET("chudevatheloai.php")
    Call<TheLoaiToDay> getCategoryMusic();

    @GET("albumhot.php")
    Call<List<Album>> getAlbumHot();

    @GET("baihatduocthich.php")
    Call<List<BaiHat>> getBaiHatHot();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getdanhsachbaihattheoquangcao(@Field("idquangcao") String idqiangcao);
}
