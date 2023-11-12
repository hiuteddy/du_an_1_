package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_dienthoai;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_loaidt;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;


public class framgment_ds_dt extends Fragment {
    private SearchView searchView;
    RecyclerView rcvdt,rcvLoai;
    dienthoaiDAO dtDAO;
    adapter_dienthoai adapter_dienthoai;
    TextView txtload;

    ArrayList<DienThoai> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.framgment_ds_dt, container, false);
        rcvdt = v.findViewById(R.id.rcdt);
        rcvLoai=v.findViewById(R.id.recyclerView);
        txtload=v.findViewById(R.id.txtclickds);
        loaddata();
        txtload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddata();
            }
        });
        return v;
    }

    public void loaddata() {
        dtDAO = new dienthoaiDAO(getContext());
        // Lấy danh sách loại sản phẩm
        loaidtDAO loaiDAO = new loaidtDAO(getContext());
        ArrayList<LoaiSeries> listLoai = loaiDAO.getAll();

        // Tạo adapter cho danh sách loại sản phẩm hàng ngang
        adapter_loaidt adapterLoai = new adapter_loaidt(getContext(), listLoai);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvLoai.setLayoutManager(layoutManager);
        rcvLoai.setAdapter(adapterLoai);

        // Thiết lập sự kiện click cho danh sách loại sản phẩm
        adapterLoai.setOnItemClickListener(new adapter_loaidt.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Lấy loại sản phẩm được chọn
                LoaiSeries loaiSeries = listLoai.get(position);

                // Lấy danh sách sản phẩm tương ứng với loại sản phẩm được chọn
                ArrayList<DienThoai> listSanPham = dtDAO.getDienThoaiByLoai(loaiSeries.getMaLoaiSeri());

                // Cập nhật danh sách sản phẩm vào RecyclerView chính
                adapter_dienthoai.updateData(listSanPham);
            }
        });

        // Hiển thị danh sách sản phẩm ban đầu
        list = dtDAO.getAll();
        adapter_dienthoai = new adapter_dienthoai(getContext(), list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvdt.setLayoutManager(gridLayoutManager);
        rcvdt.setAdapter(adapter_dienthoai);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shop_search, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //handleSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shop){
            Intent intent = new Intent(getActivity(), MainActivity_gio_hang_custom.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
