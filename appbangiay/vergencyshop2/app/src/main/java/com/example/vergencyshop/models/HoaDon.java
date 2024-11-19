package com.example.vergencyshop.models;

import java.io.Serializable;
import java.util.ArrayList;

public class HoaDon implements Serializable {
    String idHD, idND, thanhTien, ngayMua, phuongThuc, trangThai;
    ArrayList<HoaDonChiTiet> list ;

    public HoaDon(String idHD, String idND, String thanhTien, String ngayMua, String phuongThuc, String trangThai) {
        this.idHD = idHD;
        this.idND = idND;
        this.thanhTien = thanhTien;
        this.ngayMua = ngayMua;
        this.phuongThuc = phuongThuc;
        this.trangThai = trangThai;

    }

    public HoaDon() {
    }

    public String getIdHD() {
        return idHD;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
    }

    public String getIdND() {
        return idND;
    }

    public void setIdND(String idND) {
        this.idND = idND;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }
}
