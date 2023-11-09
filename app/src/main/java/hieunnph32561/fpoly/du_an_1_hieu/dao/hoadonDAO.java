package hieunnph32561.fpoly.du_an_1_hieu.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;

public class hoadonDAO {
    private DbHelper dbHelper;

    public hoadonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<HoaDon> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") HoaDon s = new HoaDon(
                    cursor.getInt(cursor.getColumnIndex("maHD")),
                    cursor.getInt(cursor.getColumnIndex("maKH")),
                    (int) cursor.getDouble(cursor.getColumnIndex("tongTien")),
                    cursor.getString(cursor.getColumnIndex("ngay")),
                    cursor.getInt(cursor.getColumnIndex("trangThai")),
                    cursor.getString(cursor.getColumnIndex("diaChi"))
            );
            list.add(s);
        }
        cursor.close();
        return list;
    }

    public long insert(HoaDon s) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKH", s.getMaKH());
        values.put("tongTien", s.getTongTien());
        values.put("ngay", s.getNgay());
        values.put("trangThai", s.getTrangThai());
        values.put("diaChi", s.getDiaChi());

        return database.insert("HoaDon", null, values);
    }

    public ArrayList<HoaDon> getAll() {
        String sql = "SELECT * FROM HoaDon";
        return getALLSACH(sql);
    }
    @SuppressLint("Range")
    public int getMaHoaDonMoiNhat() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String sql = "SELECT MAX(maHd) FROM HoaDon";

        Cursor cursor = db.rawQuery(sql, null);
        int maGioHang = 0; // Giá trị mặc định hoặc xử lý tùy ý khi không có kết quả

        if (cursor.moveToFirst()) {
            maGioHang = cursor.getInt(0);
        }

        cursor.close();
        return maGioHang;
    }
    public HoaDon getID(String id) {
        String sql = "select * from HoaDon where maHD=?";
        ArrayList<HoaDon> list = getALLSACH(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new HoaDon();
        }
    }
    public List<ChiTiet> getChiTietByMaHoaDon(int maHoaDon) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ChiTiet> chiTietList = new ArrayList<>();

        // Câu truy vấn SQL lấy danh sách chi tiết đơn hàng thuộc mã hóa đơn
        String query = "SELECT * FROM ChiTietDonHang WHERE maHd = ?";
        String[] selectionArgs = {String.valueOf(maHoaDon)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                // Tạo đối tượng ChiTiet từ dữ liệu trong Cursor
                @SuppressLint("Range") int maChiTiet = cursor.getInt(cursor.getColumnIndex("maCTDH"));
                @SuppressLint("Range") int mahd = cursor.getInt(cursor.getColumnIndex("maHD"));
                @SuppressLint("Range") int madt = cursor.getInt(cursor.getColumnIndex("maDT"));
                @SuppressLint("Range") int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
                @SuppressLint("Range") Double giatien = cursor.getDouble(cursor.getColumnIndex("giaTien"));

                // Thêm đối tượng ChiTiet vào danh sách
                ChiTiet chiTiet = new ChiTiet(maChiTiet, mahd, madt,soLuong,giatien);
                chiTietList.add(chiTiet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return chiTietList;
    }
}