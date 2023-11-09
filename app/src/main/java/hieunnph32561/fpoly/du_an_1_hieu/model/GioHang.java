package hieunnph32561.fpoly.du_an_1_hieu.model;

public class GioHang {
    private int maGh;
    private int madt;
    private Double gia;
    private int soLuong;

    public GioHang(int maGh,int madt, Double gia, int soLuong) {
        this.maGh = maGh;
        this.madt = madt;
        this.gia = gia;
        this.soLuong = soLuong ;
    }

    public GioHang(int madt, Double gia, int soLuong) {
        this.madt = madt;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public int getMadt() {
        return madt;
    }

    public void setMadt(int madt) {
        this.madt = madt;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public int getMaGh() {
        return maGh;
    }

    public void setMaGh(int maGh) {
        this.maGh = maGh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
