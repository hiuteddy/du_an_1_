package hieunnph32561.fpoly.du_an_1_hieu.model;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private int maHD;
    private int maTk;
    private int tongTien;
    private String ngay;
    private int trangThai;
    private String phuongthuc;

    public HoaDon(int maHD, int maTk, int tongTien, String ngay, int trangThai, String phuongthuc) {
        this.maHD = maHD;
        this.maTk = maTk;
        this.tongTien = tongTien;
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.phuongthuc = phuongthuc;
    }

    public HoaDon() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaTk() {
        return maTk;
    }

    public void setMaTk(int maTk) {
        this.maTk = maTk;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }


    public String getPhuongthuc() {
        return phuongthuc;
    }

    public void setPhuongthuc(String phuongthuc) {
        this.phuongthuc = phuongthuc;
    }
}