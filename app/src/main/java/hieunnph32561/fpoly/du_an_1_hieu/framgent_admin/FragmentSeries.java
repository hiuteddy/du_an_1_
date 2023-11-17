package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.SpinnerTypeAdapter;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_qlSeries;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_qlsp;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class FragmentSeries extends Fragment {
   //private SearchView searchView;
    private RecyclerView recyclerView;
    private adapter_qlSeries adapter;
    private List<LoaiSeries> dataList;
    FloatingActionButton btnadd;
    private loaidtDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, container, false);
        recyclerView = view.findViewById(R.id.rcvqldt);
        btnadd = view.findViewById(R.id.floatBtnAdd);
        dao = new loaidtDAO(getContext());
        dataList = dao.getAll();

        adapter = new adapter_qlSeries(getContext(), dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        return view;
    }

    private void showAddDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_series);
        dialog.setCancelable(false);

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_Series);
        EditText edtTenSeries = dialog.findViewById(R.id.editSeriesName);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSumbit);

        titledialog.setText("Series Add");
        btnSubmit.setText("Add");


        btnSubmit.setOnClickListener(v -> {
            String tenSeries = edtTenSeries.getText().toString().trim();

            if (TextUtils.isEmpty(tenSeries)) {
                showToast("Vui lòng nhập tên sản phẩm");
            } else {

                LoaiSeries series = new LoaiSeries();
                series.setMaLoaiSeri(dataList.size()+1);
                series.setTenLoaiSeri(tenSeries);

                dao.add(series);
                dataList.add(series);

                showToast("Đã Thêm Series");
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_sapxep, menu);
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        MenuItem searchItem = menu.findItem(R.id.search);
//        searchView = (SearchView) searchItem.getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //handleSearch(newText);
//                return true;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//    private void handleSearch(String query) {
//        List<LoaiSeries> listSearch = new ArrayList<>();
//        for (LoaiSeries dt : dataList) {
//            if (dt.getTenLoaiSeri().toLowerCase().contains(query.toLowerCase())) {
//                listSearch.add(dt);
//            }
//        }
//        adapter = new adapter_qlSeries(getContext(),listSearch);
//        recyclerView.setAdapter(adapter);
//    }
//    private void sortBooksByNameDescending() {
//        Collections.sort(dataList, new Comparator<LoaiSeries>() {
//            @Override
//            public int compare(LoaiSeries loaiSeries, LoaiSeries t1) {
//                return t1.getTenLoaiSeri().toLowerCase().compareTo(loaiSeries.getTenLoaiSeri().toLowerCase());
//            }
//
//
//        });
//
//        adapter.notifyDataSetChanged();
//    }
//    //tăng dần
//    private void sortBooksByNameAscending() {
//        Collections.sort(dataList, new Comparator<LoaiSeries>() {
//            @Override
//            public int compare(LoaiSeries loaiSeries, LoaiSeries t1) {
//                return loaiSeries.getTenLoaiSeri().toLowerCase().compareTo(t1.getTenLoaiSeri().toLowerCase());
//            }
//
//        });
//
//        adapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.asc){
//            sortBooksByNameAscending();
//            return true;
//        }else if(id == R.id.desc){
//            sortBooksByNameDescending();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}