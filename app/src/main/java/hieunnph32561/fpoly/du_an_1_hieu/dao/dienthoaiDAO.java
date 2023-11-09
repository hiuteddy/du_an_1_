package hieunnph32561.fpoly.du_an_1_hieu.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class dienthoaiDAO {
    private DbHelper dbHelper;

    public dienthoaiDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<DienThoai> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<DienThoai> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") DienThoai s = new DienThoai(
                    cursor.getInt(cursor.getColumnIndex("maDT")),
                    cursor.getInt(cursor.getColumnIndex("maLoaiSeries")),
                    cursor.getString(cursor.getColumnIndex("tenDT")),
                    cursor.getDouble(cursor.getColumnIndex("giaTien")),
                    cursor.getString(cursor.getColumnIndex("moTa"))

            );
            list.add(s);
        }
        cursor.close(); // Đóng con trỏ khi hoàn thành công việc
        return list; //
    }

    public ArrayList<DienThoai> getAll() {
        String sql = "SELECT * FROM DienThoai";
        return (ArrayList<DienThoai>) getALLSACH(sql); // Gọi getALLSACH với một truy vấn SQL đã được định nghĩa trước
    }
    public DienThoai getID(String id) {
        String sql = "select * from DienThoai where maDT=?";
        ArrayList<DienThoai> list = getALLSACH(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new DienThoai();
        }
    }
}