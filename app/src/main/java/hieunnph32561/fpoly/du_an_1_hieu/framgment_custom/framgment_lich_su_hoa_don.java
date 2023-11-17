package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_lichsu;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;

public class framgment_lich_su_hoa_don extends Fragment {
    RecyclerView rcvdt;
    hoadonDAO chitietDAO;
    adapter_lichsu adapter_lichsu;
    ArrayList<HoaDon> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.framgment_lich_su, container, false);
        rcvdt = v.findViewById(R.id.rclls);
        loaddata();
        return v;

    }

    public void loaddata() {
        chitietDAO = new hoadonDAO(getContext());
        list = chitietDAO.getAll();
        adapter_lichsu = new adapter_lichsu(getContext(), list) {
        };
        rcvdt.setAdapter(adapter_lichsu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rcvdt.setLayoutManager(gridLayoutManager);
    }

}
