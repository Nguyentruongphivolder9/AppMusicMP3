package com.example.appmusicmp3.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuangCao {

    @SerializedName("IdQuangCao")
    @Expose
    private String idQuangCao;
    @SerializedName("HinhAnh")
    @Expose
    private String hinhAnh;
    @SerializedName("IdBaiHat")
    @Expose
    private String idBaiHat;

    public String getIdQuangCao() {
        return idQuangCao;
    }

    public void setIdQuangCao(String idQuangCao) {
        this.idQuangCao = idQuangCao;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(String idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

}
