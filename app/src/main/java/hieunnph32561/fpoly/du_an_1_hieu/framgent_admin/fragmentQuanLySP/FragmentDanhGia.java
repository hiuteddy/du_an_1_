package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentQuanLySP;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu.adapter_danhgia;
import hieunnph32561.fpoly.du_an_1_hieu.dao.danhgiaDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DanhGia;


public class FragmentDanhGia extends Fragment {

    RecyclerView rcvqldt;
    adapter_danhgia adapter;
    ArrayList<DanhGia> list;
    danhgiaDAO danhgiaDAO;

    FloatingActionButton btnAddSp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && isResumed()) {
//            loaddata();
//        }
//    }
//
    @Override
    public void onResume() {
        super.onResume();

            loaddata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kinh_doanh, container, false);

        rcvqldt = view.findViewById(R.id.rcvqldt);
        btnAddSp = view.findViewById(R.id.floatBtnAdd);
        btnAddSp.setVisibility(View.GONE);
        danhgiaDAO = new danhgiaDAO(getContext());

        list = danhgiaDAO.getAll();
        adapter = new adapter_danhgia(getContext(), list);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
        return view;
    }

    public void loaddata() {
        list = danhgiaDAO.getAll();
        adapter = new adapter_danhgia(getContext(), list);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
    }

}
