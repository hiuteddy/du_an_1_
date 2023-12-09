package hieunnph32561.fpoly.du_an_1_hieu.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "duan1";
    public static final int DB_VERSION = 75;

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
                "maLoaiSeries INTEGER," +
                "imageUrl BLOB," +
                "tenDT TEXT, " +
                "giaTien DOUBLE, " +
                "moTa TEXT," +
                "soLuong INTEGER," +
                "trangThai INTEGER, FOREIGN KEY (maLoaiSeries) REFERENCES LoaiSeriesDT(maLoaiSeries))";

        String createTaiKhoanTable = "CREATE TABLE TaiKhoan " +
                "(maTk INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenDN TEXT , " +
                "matKhau TEXT," +
                "hoTen TEXT," +
                "sdt TEXT," +
                "diaChi TEXT)";
        String createAdminTable = "CREATE TABLE Admin " +
                "(maTk INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenDN TEXT , " +
                "matKhau TEXT," +
                "hoTen TEXT," +
                "sdt TEXT," +
                "diaChi TEXT)";
        String createRatingTable = "CREATE TABLE DanhGia " +
                "(maDG INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maDT INTEGER, " +
                "maTk INTEGER, " +
                "diem INTEGER, " +
                "nhanXet TEXT," +
                "ngay DATE, " +
                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT), " +
                "FOREIGN KEY (maTk) REFERENCES TaiKhoan(maTk))";


        String createGioHangTable = "CREATE TABLE GioHang" +
                " (maGh INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTk INTEGER," +
                " maDT TEXT," +
                " giaTien DOUBLE," +
                " soLuong INTEGER," +
                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT), " +
                "FOREIGN KEY (maTk) REFERENCES TaiKhoan(maTk))";

        String createHoaDonTable = "CREATE TABLE HoaDon " +
                "(maHD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTk INTEGER, " +
                "tongTien DOUBLE, " +
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
        db.execSQL(createRatingTable);
        db.execSQL(createAdminTable);


        // Tạo các bảng

        // Thêm dữ liệu mẫu vào bảng LoaiSeriesDT


//        // Thêm dữ liệu mẫu vào bảng DienThoai
        db.execSQL("INSERT INTO DienThoai (maDT, maLoaiSeries, imageUrl, tenDT, giaTien, moTa, soLuong, trangThai) VALUES (1, 1, 'iphone15.png', 'Iphone 15 Pro', 30000000, 'Thời gian xem video lên đến 26 giờ chú thích ⁴\n" +
                "Hệ thống camera kép tiên tiến" +
                "\n" +
                "Chính 48MP | Ultra Wide" +
                "\n" +
                "Ảnh có độ phân giải siêu cao (24MP và 48MP)" +
                "\n" +
                "Ảnh chân dung thế hệ mới với Focus và Depth Control" +
                "Chip A16 Bionic với GPU 5 lõi\n" +
                "Màn hình Super Retina XDR chú thích " +
                "\n" +
                "Màn hình Luôn Bật" +
                "\n" +
                "Công nghệ ProMotionDynamic Island" +
                "\n" +
                "Một cách tuyệt diệu để tương tác với iPhone', 10,0)");

        db.execSQL("INSERT INTO DienThoai (maDT, maLoaiSeries, imageUrl, tenDT, giaTien, moTa,soLuong, trangThai) VALUES (2, 1, 'iphone14plus.png', 'Iphone 15 ', 22000000, '\"Hệ thống camera kép tiên tiến\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Chính 48MP | Ultra Wide\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Ảnh có độ phân giải siêu cao (24MP và 48MP)\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Ảnh chân dung thế hệ mới với Focus và Depth Control\" +\n" +
                "                \"Chip A16 Bionic với GPU 5 lõi\\n\" +\n" +
                "                \"Màn hình Super Retina XDR chú thích \" +\n" +
                "                \"\\n\" +\n" +
                "                \"Màn hình Luôn Bật\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Công nghệ ProMotionDynamic Island\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Một cách tuyệt diệu để tương tác với iPhone',10,0)");
        db.execSQL("INSERT INTO DienThoai (maDT, maLoaiSeries, imageUrl, tenDT, giaTien, moTa,soLuong, trangThai) VALUES (3, 1, 'iphone15promax.png', 'Iphone 15 Pro Max', 33000000, '\"Hệ thống camera kép tiên tiến\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Chính 48MP | Ultra Wide\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Ảnh có độ phân giải siêu cao (24MP và 48MP)\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Ảnh chân dung thế hệ mới với Focus và Depth Control\" +\n" +
                "                \"Chip A16 Bionic với GPU 5 lõi\\n\" +\n" +
                "                \"Màn hình Super Retina XDR chú thích \" +\n" +
                "                \"\\n\" +\n" +
                "                \"Màn hình Luôn Bật\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Công nghệ ProMotionDynamic Island\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Một cách tuyệt diệu để tương tác với iPhone',10,0)");

        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (1, 'Series 15')");
        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (2, 'Series 14')");
        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (3, 'Series 13')");
        // Thêm dữ liệu mẫu vào bảng TaiKhoan
        db.execSQL("INSERT INTO Admin (maTk, tenDN, matKhau,hoTen,sdt,diaChi) VALUES (1, 'admin', 'admin','Nguyễn Hai',0123456789 ,'hanoi')");
        db.execSQL("INSERT INTO TaiKhoan (maTk, tenDN, matKhau,hoTen,sdt,diaChi) VALUES (1, 'user', '123','Nguyễn Văn B', 0987654321,'C82-Ba-Đinh-Hanoi')");
        db.execSQL("INSERT INTO TaiKhoan (maTk, tenDN, matKhau,hoTen,sdt,diaChi) VALUES (2, 'hieu', '123','Nguyễn nhu hieu', 0987674321,'C74-Cầu Giấy-Hanoi')");


        db.execSQL("INSERT INTO DanhGia (maDT, maTk, diem,nhanXet,ngay) VALUES (1, 1, 5, 'Sản phẩm rất tuyệt vời!','2023-9-07')");
        db.execSQL("INSERT INTO DanhGia (maDT, maTk, diem, nhanXet, ngay) VALUES (2, 2, 4, 'Có chút vấn đề về pin', '2023-10-05')");
        db.execSQL("INSERT INTO DanhGia (maDT, maTk, diem,nhanXet,ngay) VALUES (3, 2, 3, 'Không đáng giá hơn giá tiền','2023-11-01')");


        // Thêm dữ liệu mẫu vào bảng HoaDon
        db.execSQL("INSERT INTO HoaDon (maTk, tongTien, ngay, trangThai, phuongThuc) VALUES (1, 66000000, '2023-11-01', 1, '1')");
        db.execSQL("INSERT INTO HoaDon (maTk, tongTien, ngay, trangThai, phuongThuc) VALUES (1, 90000000, '2023-11-02', 1, '2')");

        // Thêm dữ liệu mẫu vào bảng GioHang
//        db.execSQL("INSERT INTO GioHang (maDT, giaTien, soLuong) VALUES (1, 1000, 2)");
//        db.execSQL("INSERT INTO GioHang (maDT, giaTien, soLuong) VALUES (2, 2000, 3)");

        // Thêm dữ liệu mẫu vào bảng ChiTietDonHang
        db.execSQL("INSERT INTO ChiTietDonHang (maHD, maDT, soLuong, giaTien) VALUES (1, 1, 2, 66000000)");
        db.execSQL("INSERT INTO ChiTietDonHang (maHD, maDT, soLuong, giaTien) VALUES (2, 2, 3, 90000000)");
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
            db.execSQL("drop table if exists Admin");
            db.execSQL("drop table if exists DanhGia");

            onCreate(db);
        }
    }

}
