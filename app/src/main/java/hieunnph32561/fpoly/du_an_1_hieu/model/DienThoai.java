package hieunnph32561.fpoly.du_an_1_hieu.model;

public class DienThoai {
    private int maDT;
    private int maLoaiSeri;
    private String tenDT;
    private double giaTien;
    private String moTa;
    private int soLuong;

    public DienThoai(int maDT, int maLoaiSeri, String tenDT, double giaTien, String moTa,int soLuong) {
        this.maDT = maDT;
        this.maLoaiSeri = maLoaiSeri;
        this.tenDT = tenDT;
        this.giaTien = giaTien;
        this.moTa = moTa;
        this.soLuong=soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public void setMaLoaiSeri(int maLoaiSeri) {
        this.maLoaiSeri = maLoaiSeri;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public DienThoai() {
    }

    public int getMaDT() {
        return maDT;
    }

    public int getMaLoaiSeri() {
        return maLoaiSeri;
    }

    public String getTenDT() {
        return tenDT;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public String getMoTa() {
        return moTa;
    }
}

