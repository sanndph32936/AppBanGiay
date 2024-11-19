package com.example.vergencyshop.models;

public class TopSanPham{
    private String idSP;
    private String soLuong ;

    public TopSanPham(String idSP, String soLuong) {
        this.idSP = idSP;
        this.soLuong = soLuong;
    }

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "TopSanPham{" +
                "idSP='" + idSP + '\'' +
                ", soLuong='" + soLuong + '\'' +
                '}';
    }
}
