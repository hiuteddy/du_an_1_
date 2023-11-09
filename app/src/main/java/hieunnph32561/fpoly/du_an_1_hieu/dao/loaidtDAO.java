package hieunnph32561.fpoly.du_an_1_hieu.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.database.DbHelper;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class loaidtDAO {
    private DbHelper dBhelper;

    public loaidtDAO(Context context) {
        dBhelper = new DbHelper(context);
    }


    public ArrayList<LoaiSeries> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<LoaiSeries> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") LoaiSeries ls = new LoaiSeries(
                    cursor.getInt(cursor.getColumnIndex("maLoaiSeries")),
                    cursor.getString(cursor.getColumnIndex("tenLoaiSeries"))
            );
            list.add(ls);
        }


        cursor.close();

        return list;
    }


    public ArrayList<LoaiSeries> getAll() {
        String sql = "SELECT * FROM LoaiSeriesDT";
        return getALLSACH(sql);
    }

    public LoaiSeries getID(String id) {
        String sql = "select * from LoaiSeriesDT where maLoaiSeries=?";
        ArrayList<LoaiSeries> list = getALLSACH(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return new LoaiSeries();
        }
    }
}
