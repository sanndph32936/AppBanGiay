package com.example.vergencyshop.models;

public class HoaDonChiTiet {
    String idHD, idSP,soLuong,tongTien,anhSP,sizeSP;

    public HoaDonChiTiet(String idHD, String idSP, String soLuong, String tongTien, String anhSP, String sizeSP) {
        this.idHD = idHD;
        this.idSP = idSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.anhSP = anhSP;
        this.sizeSP = sizeSP;
    }

    public HoaDonChiTiet() {
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public String getSizeSP() {
        return sizeSP;
    }

    public void setSizeSP(String sizeSP) {
        this.sizeSP = sizeSP;
    }

    public String getIdHD() {
        return idHD;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
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

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }
}
