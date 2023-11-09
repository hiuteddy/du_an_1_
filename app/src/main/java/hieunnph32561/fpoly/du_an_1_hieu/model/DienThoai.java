package hieunnph32561.fpoly.du_an_1_hieu.model;

public class DienThoai {
    private int maDT;
    private int maLoaiSeri;
    private String tenDT;
    private double giaTien;
    private String moTa;

    public DienThoai(int maDT, int maLoaiSeri, String tenDT, double giaTien, String moTa) {
        this.maDT = maDT;
        this.maLoaiSeri = maLoaiSeri;
        this.tenDT = tenDT;
        this.giaTien = giaTien;
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

