package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_lichsu;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class framgment_lich_su_hoa_don extends Fragment {
    RecyclerView rcvdt;
    hoadonDAO chitietDAO;
    TaiKhoan taiKhoan;
    taikhoanDAO taikhoanDAO;
    adapter_lichsu adapter_lichsu;
    ArrayList<HoaDon> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.framgment_lich_su, container, false);
        rcvdt = v.findViewById(R.id.rclls);
        taikhoanDAO = new taikhoanDAO(getContext());
        loaddata();

        setHasOptionsMenu(true); // Bật menu

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_xac_nhan_hoa_don, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.chuaxacnhan) {
            setList(0);
            return true;
        } else if (id == R.id.daxacnhan) {
            setList(1);
            return true;
        } else if (id == R.id.danggiao) {
            setList(2);
            return true;
        } else if (id == R.id.dagiao) {
            setList(3);
            return true;
        } else if (id == R.id.dahuy) {
            setList(4);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void loaddata() {
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");
        taiKhoan = taikhoanDAO.getID(username);
        chitietDAO = new hoadonDAO(getContext());
        list = chitietDAO.getAllByMaKhachHang(taiKhoan.getMaTk());
        adapter_lichsu = new adapter_lichsu(getContext(), list) {
        };
        rcvdt.setAdapter(adapter_lichsu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rcvdt.setLayoutManager(gridLayoutManager);
    }
    void setList(int index){
        list.clear();
        for ( HoaDon x : chitietDAO.getAllByMaKhachHang(taiKhoan.getMaTk())) {
            if (index == x.getTrangThai()){
                list.add(x);
            }
        }
        adapter_lichsu.notifyDataSetChanged();
    }
}
