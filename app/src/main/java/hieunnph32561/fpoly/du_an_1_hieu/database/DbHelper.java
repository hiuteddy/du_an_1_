package hieunnph32561.fpoly.du_an_1_hieu.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "duan1";
    public static final int DB_VERSION = 30;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoaiSeriTable = "CREATE TABLE LoaiSeriesDT " +
                "(maLoaiSeries INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " tenLoaiSeries TEXT)";

        String createDienThoaiTable = "CREATE TABLE DienThoai" +
                " (maDT INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " maLoaiSeries INTEGER," +
                " tenDT TEXT, " +
                "giaTien DOUBLE, " +
                "moTa TEXT, FOREIGN KEY (maLoaiSeries) REFERENCES LoaiSeriesDT(maLoaiSeries))";


        String createTaiKhoanTable = "CREATE TABLE TaiKhoan " +
                "(maTk INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenDN TEXT , " +
                "matKhau TEXT," +
                "hoTen TEXT," +
                "sdt TEXT," +
                "diaChi TEXT)";


        String createGioHangTable = "CREATE TABLE GioHang" +
                " (maGh INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maDT TEXT," +
                " giaTien DOUBLE," +
                " soLuong INTEGER," +
                " FOREIGN KEY (maDT) REFERENCES DienThoai(maDT))";

        String createHoaDonTable = "CREATE TABLE HoaDon " +
                "(maHD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTk INTEGER, " +
                "tongTien INTEGER, " +
                "ngay DATE, " +
                "trangThai INTEGER, " +
                "phuongThuc TEXT, " +
                "FOREIGN KEY (maTk) REFERENCES TaiKhoan(maTk))";

        String createChiTietDonHangTable = "CREATE TABLE ChiTietDonHang " +
                "(maCTDH INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maHD INTEGER, " +
                "maDT INTEGER, " +
                "soLuong INTEGER, " +
                "giaTien DOUBLE, " +
                "FOREIGN KEY (maHD) REFERENCES HoaDon(maHD), " +
                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT))";


        db.execSQL(createLoaiSeriTable);
        db.execSQL(createDienThoaiTable);
        db.execSQL(createTaiKhoanTable);
        db.execSQL(createHoaDonTable);
        db.execSQL(createGioHangTable);
        db.execSQL(createChiTietDonHangTable);


        // Tạo các bảng

        // Thêm dữ liệu mẫu vào bảng LoaiSeriesDT
        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (1, 'Series 15')");
        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (2, 'Series 14')");
        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (3, 'Series 13')");

        // Thêm dữ liệu mẫu vào bảng DienThoai
        db.execSQL("INSERT INTO DienThoai (maDT, maLoaiSeries, tenDT, giaTien, moTa) VALUES (1, 1, 'Điện thoại 1', 1000, 'Mô tả 1')");
        db.execSQL("INSERT INTO DienThoai (maDT, maLoaiSeries, tenDT, giaTien, moTa) VALUES (2, 2, 'Điện thoại 2', 2000, 'Mô tả 2')");
        db.execSQL("INSERT INTO DienThoai (maDT, maLoaiSeries, tenDT, giaTien, moTa) VALUES (3, 3, 'Điện thoại 3', 3000, 'Mô tả 3')");

        // Thêm dữ liệu mẫu vào bảng TaiKhoan
        db.execSQL("INSERT INTO TaiKhoan (maTk, tenDN, matKhau,sdt,hoTen,diaChi) VALUES (1, 'admin', 'admin','Nguyễn Văn A', 123456789,'hanoi')");
        db.execSQL("INSERT INTO TaiKhoan (maTk, tenDN, matKhau,sdt,hoTen,diaChi) VALUES (2, 'user', '123',987654321,'Nguyễn Văn B' ,'hcm')");
        db.execSQL("INSERT INTO TaiKhoan (maTk, tenDN, matKhau,sdt,hoTen,diaChi) VALUES (3, 'name', '234',987654321, 'Nguyễn Văn C','Nb')");


        // Thêm dữ liệu mẫu vào bảng HoaDon
        db.execSQL("INSERT INTO HoaDon (maTk, tongTien, ngay, trangThai, phuongThuc) VALUES (1, 5000, '2023/11/01', 1, '1')");
        db.execSQL("INSERT INTO HoaDon (maTk, tongTien, ngay, trangThai, phuongThuc) VALUES (2, 7000, '2023/11/02', 1, '2')");
        db.execSQL("INSERT INTO HoaDon (maTk, tongTien, ngay, trangThai, phuongThuc) VALUES (3, 8000, '2023/11/03', 1, '1')");

        // Thêm dữ liệu mẫu vào bảng GioHang
        db.execSQL("INSERT INTO GioHang (maDT, giaTien, soLuong) VALUES (1, 1000, 2)");
        db.execSQL("INSERT INTO GioHang (maDT, giaTien, soLuong) VALUES (2, 2000, 3)");

        // Thêm dữ liệu mẫu vào bảng ChiTietDonHang
        db.execSQL("INSERT INTO ChiTietDonHang (maHD, maDT, soLuong, giaTien) VALUES (1, 1, 2, 2000)");
        db.execSQL("INSERT INTO ChiTietDonHang (maHD, maDT, soLuong, giaTien) VALUES (2, 2, 3, 6000)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("drop table if exists TaiKhoan");
            db.execSQL("drop table if exists DienThoai");
            db.execSQL("drop table if exists LoaiSeriesDT");
            db.execSQL("drop table if exists HoaDon");
            db.execSQL("drop table if exists GioHang");
            db.execSQL("drop table if exists ChiTietDonHang");


            onCreate(db);
        }
    }

}
