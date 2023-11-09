package hieunnph32561.fpoly.du_an_1_hieu.model;

public class HoaDon {
    private int maHD;
    private int maKH;
    private int tongTien;
    private String ngay;
    private int trangThai;
    private String diaChi;

    public HoaDon(int maHD, int maKH, int tongTien, String ngay, int trangThai, String diaChi) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.tongTien = tongTien;
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.diaChi = diaChi;
    }

    public HoaDon() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
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


    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}