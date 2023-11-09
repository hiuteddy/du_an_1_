package hieunnph32561.fpoly.du_an_1_hieu.model;

public class ChiTiet {
    private int mact;
    private int mahd;
    private int madt;
    private int soluong;
    private Double giatien;

    public ChiTiet() {
    }


    public ChiTiet(int mact, int mahd, int madt, int soluong, Double giatien) {
        this.mact = mact;
        this.mahd = mahd;
        this.madt = madt;
        this.soluong = soluong;
        this.giatien = giatien;
    }

    public int getMact() {
        return mact;
    }

    public void setMact(int mact) {
        this.mact = mact;
    }

    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }

    public int getMadt() {
        return madt;
    }

    public void setMadt(int madt) {
        this.madt = madt;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Double getGiatien() {
        return giatien;
    }

    public void setGiatien(Double giatien) {
        this.giatien = giatien;
    }
    //        "(maCTDH INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "maHD INTEGER, " +
//                "maDT INTEGER, " +
//                "soLuong INTEGER, " +
//                "giaTien DOUBLE, " +
//                "FOREIGN KEY (maHD) REFERENCES HoaDon(maHD), " +
//                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT))";
}
