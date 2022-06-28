package com.example.appmusicmp3.data.datasources.remote;

import com.example.appmusicmp3.data.models.Album;
import com.example.appmusicmp3.data.models.BaiHat;
import com.example.appmusicmp3.data.models.ChuDe;
import com.example.appmusicmp3.data.models.Playlist;
import com.example.appmusicmp3.data.models.QuangCao;
import com.example.appmusicmp3.data.models.TheLoai;
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

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("playlistforcurrent.php")
    Call<List<Playlist>> getDanhsachcacplaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDanhsachbaihattheotheloai(@Field("idtheloai") String idtheloai);

    @GET("allchude.php")
    Call<List<ChuDe>> getAllChuDe();

    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> getTheloaitheochude(@Field("idchude") String idchude);

    @GET("allalum.php")
    Call<List<Album>> getAllAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDanhsachbaihattheoAlbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> updateLuotThich(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<BaiHat>> getSearchBaihat(@Field("tukhoa") String tukhoa);
}
