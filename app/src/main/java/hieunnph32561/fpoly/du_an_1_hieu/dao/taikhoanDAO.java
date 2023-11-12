package hieunnph32561.fpoly.du_an_1_hieu.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;


public class taikhoanDAO {
    private DbHelper dbHelper;

    public taikhoanDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<TaiKhoan> getALLTT(String sql, String... selectionArgs) {
        ArrayList<TaiKhoan> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") TaiKhoan tt = new TaiKhoan(
                    cursor.getInt(cursor.getColumnIndex("maTk")),
                    cursor.getString(cursor.getColumnIndex("tenDN")),
                    cursor.getString(cursor.getColumnIndex("matKhau")),
                    cursor.getString(cursor.getColumnIndex("hoTen")),
                    cursor.getString(cursor.getColumnIndex("sdt")),
                    cursor.getString(cursor.getColumnIndex("diaChi"))

            );

            list.add(tt);
        }
        cursor.close(); // Đóng con trỏ khi hoàn thành công việc
        return list; //
    }

    public int checkLogin(String id, String pass) {
        String sql = "select * from TaiKhoan where tenDN=? and matKhau=?";
        List<TaiKhoan> list = getALLTT(sql, id, pass);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public TaiKhoan getID(String id) {
        String sql = "select * from TaiKhoan where tenDN=?";
        ArrayList<TaiKhoan> list = getALLTT(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new TaiKhoan();
        }
    }

    public TaiKhoan getIDma(String id) {
        String sql = "select * from TaiKhoan where maTk=?";
        ArrayList<TaiKhoan> list = getALLTT(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new TaiKhoan();
        }
    }

    @SuppressLint("Range")
    public int getMaKhachHangByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int maKhachHang = -1; // Giá trị không hợp lệ

        String[] columns = {"maTk"};
        String selection = "tenDN = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("TaiKhoan", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            maKhachHang = cursor.getInt(cursor.getColumnIndex("maTk"));
        }

        cursor.close();
        db.close();

        return maKhachHang;
    }

    public long insert(TaiKhoan s) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenDN", s.getTenDN());
        values.put("matKhau", s.getMatKhau());
        values.put("hoTen", s.getHoten());
        values.put("sdt", s.getSdt());
        values.put("diaChi", s.getDiachi());
        return database.insert("TaiKhoan", null, values); // Trả về ID của hàng được chèn
    }

    public long update(TaiKhoan user) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("tenDN",user.getTenDN());
        values.put("hoten", user.getHoten());
        values.put("sdt", user.getSdt());
        values.put("diachi", user.getDiachi());
        values.put("matkhau", user.getMatKhau());
        return database.update("TaiKhoan", values, "tenDN=?", new String[]{String.valueOf(user.getTenDN())});
    }
}
