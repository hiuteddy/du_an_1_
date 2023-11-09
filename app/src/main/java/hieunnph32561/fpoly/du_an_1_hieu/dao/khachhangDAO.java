package hieunnph32561.fpoly.du_an_1_hieu.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.KhachHang;

public class khachhangDAO {

    private DbHelper dbHelper;

    public khachhangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<KhachHang> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") KhachHang s = new KhachHang(
                    cursor.getInt(cursor.getColumnIndex("maKh")),
                    cursor.getString(cursor.getColumnIndex("hoTen")),
                    cursor.getString(cursor.getColumnIndex("dienThoai")),
                    cursor.getString(cursor.getColumnIndex("diaChi"))
                    );
            list.add(s);
        }
        cursor.close(); // Đóng con trỏ khi hoàn thành công việc
        return list; //
    }

    public long insert(KhachHang s) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", s.getHoTen());
        values.put("dienThoai", s.getDienThoai());
        values.put("diaChi", s.getDiachi());
        return database.insert("KhachHang", null, values); // Trả về ID của hàng được chèn
    }


    public ArrayList<KhachHang> getAll() {
        String sql = "SELECT * FROM KhachHang";
        return (ArrayList<KhachHang>) getALLSACH(sql); // Gọi getALLSACH với một truy vấn SQL đã được định nghĩa trước
    }


    public KhachHang getID(String id) {
        String sql = "select * from KhachHang where maKh=?";
        ArrayList<KhachHang> list = getALLSACH(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new KhachHang();
        }
    }

}

