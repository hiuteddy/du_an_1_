package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu.adapter_lichsu;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class framgments_tc  extends Fragment {
    private RecyclerView recyclerView;
    private adapter_lichsu adapter;
    private List<HoaDon> chiTietList;
    private hoadonDAO daoHD;
    private taikhoanDAO taikhoanDAO;
    private ArrayList<HoaDon> listsetAdapter;
    private boolean isFragmentVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            updateAdapterData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            updateAdapterData();
        }
    }

    private void updateAdapterData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");
        TaiKhoan taiKhoan = taikhoanDAO.getID(username);

        listsetAdapter.clear(); // Xóa danh sách cũ

        for (HoaDon x : chiTietList) {
            if (x.getTrangThai() == 3 && x.getMaTk()==(taiKhoan.getMaTk())) {
                listsetAdapter.add(x); // Thêm vào danh sách mới chỉ khi trạng thái là 0 và mã khách hàng khớp
            }
        }

        adapter.notifyDataSetChanged(); // Cập nhật adapter
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgments_ls_custom, container, false);

        recyclerView = view.findViewById(R.id.rcllsct);
        taikhoanDAO = new taikhoanDAO(getContext());
        daoHD = new hoadonDAO(getContext());

        chiTietList = daoHD.getAll();
        listsetAdapter = new ArrayList<>();

        adapter = new adapter_lichsu(getContext(), listsetAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}