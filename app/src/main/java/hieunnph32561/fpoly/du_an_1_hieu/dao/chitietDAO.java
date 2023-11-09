package hieunnph32561.fpoly.du_an_1_hieu.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;

public class chitietDAO {
    private DbHelper dbHelper;

    public chitietDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public long insert(ChiTiet chiTietSanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHD", chiTietSanPham.getMahd());
        values.put("maDT", chiTietSanPham.getMadt());
        values.put("soLuong", chiTietSanPham.getSoluong());
        values.put("giaTien", chiTietSanPham.getGiatien());
        return db.insert("ChiTietDonHang", null, values);
    }
    public ArrayList<ChiTiet> getALLct(String sql, String... selectionArgs) {
        ArrayList<ChiTiet> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") ChiTiet s = new ChiTiet(
                    cursor.getInt(cursor.getColumnIndex("maCTDH")),
                    cursor.getInt(cursor.getColumnIndex("maHD")),
                    cursor.getInt(cursor.getColumnIndex("maDT")),
                    cursor.getInt(cursor.getColumnIndex("soLuong")),
                    cursor.getDouble(cursor.getColumnIndex("giaTien"))
            );
            list.add(s);
        }
        cursor.close(); // Đóng con trỏ khi hoàn thành công việc
        return list; //
    }
    public ArrayList<ChiTiet> getAll() {
        String sql = "SELECT * FROM ChiTietDonHang";
        return (ArrayList<ChiTiet>) getALLct(sql); // Gọi getALLSACH với một truy vấn SQL đã được định nghĩa trước
    }
}