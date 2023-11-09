package hieunnph32561.fpoly.du_an_1_hieu.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class taikhoanDAO {
    private DbHelper dbHelper;

    public taikhoanDAO(Context context) {
        dbHelper = new DbHelper(context) ;
    }
    public ArrayList<TaiKhoan> getALLTT(String sql, String... selectionArgs) {
        ArrayList<TaiKhoan> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") TaiKhoan tt = new TaiKhoan(
                    cursor.getInt(cursor.getColumnIndex("maTk")),
                    cursor.getString(cursor.getColumnIndex("tenDN")),
                    cursor.getString(cursor.getColumnIndex("matKhau"))
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
}
