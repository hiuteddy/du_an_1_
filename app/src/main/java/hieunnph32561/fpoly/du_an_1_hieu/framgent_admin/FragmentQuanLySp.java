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
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.SpinnerTypeAdapter;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_qlsp;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;


public class FragmentQuanLySp extends Fragment {
    private SearchView searchView;
    RecyclerView rcvqldt;
    FloatingActionButton btnAddSp;
    dienthoaiDAO dtDAO;

    private SpinnerTypeAdapter spinnerTypeAdapter;
    loaidtDAO loaidao;
    adapter_qlsp adapter;
    private List<LoaiSeries> listLS;

    ArrayList<DienThoai> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_sp, container, false);
        rcvqldt = view.findViewById(R.id.rcvqldt);
        btnAddSp = view.findViewById(R.id.floatBtnAdd);
        btnAddSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        LoadData();
        return view;
    }

    private void LoadData() {
        dtDAO = new dienthoaiDAO(getContext());
        loaidao = new loaidtDAO(getContext());
        listLS = loaidao.getAll();
        list = dtDAO.getAll();
        adapter = new adapter_qlsp(getContext(), list);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sapxep, menu);
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
                //    handleSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
//    private void handleSearch(String query) {
//        List<DienThoai> listSearch = new ArrayList<>();
//        for (DienThoai dt : list) {
//            if (dt.getTenDT().toLowerCase().contains(query.toLowerCase())) {
//                listSearch.add(dt);
//            }
//        }
//        adapter = new adapter_qlsp( getActivity(), (ArrayList<DienThoai>) listSearch);
//        rcvqldt.setAdapter(adapter);
//
//    }

    private void showAddDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_load_dt);
        dialog.setCancelable(false);
        dtDAO = new dienthoaiDAO(getContext());

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_load);
        EditText edtTenSP = dialog.findViewById(R.id.editqlName);
        EditText edtGia = dialog.findViewById(R.id.editqlPrice);
        EditText edtsl = dialog.findViewById(R.id.editsoluong);
        EditText edtMota = dialog.findViewById(R.id.editqlDescribe);
        Spinner editqlSeries = dialog.findViewById(R.id.editqlSeries);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSumbit);

        titledialog.setText("Product Add");
        btnSubmit.setText("Add");

        spinnerTypeAdapter = new SpinnerTypeAdapter(getContext(), listLS);
        editqlSeries.setAdapter(spinnerTypeAdapter);

        btnSubmit.setOnClickListener(v -> {
            String tenSP = edtTenSP.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();
            String mota = edtMota.getText().toString().trim();
            int sl= Integer.parseInt(edtsl.getText().toString().trim());

            if (TextUtils.isEmpty(tenSP)) {
                showToast("Vui lòng nhập tên sản phẩm");
            } else {
                Double gia = Double.parseDouble(giaStr);

                DienThoai newDienThoai = new DienThoai();
                newDienThoai.setTenDT(tenSP);
                newDienThoai.setGiaTien(gia);
                newDienThoai.setMoTa(mota);
                newDienThoai.setSoLuong(sl);

                // Gán mã loại seri từ Spinner vào đối tượng điện thoại
                LoaiSeries selectedType = (LoaiSeries) editqlSeries.getSelectedItem();
                newDienThoai.setMaLoaiSeri(selectedType.getMaLoaiSeri());

                dtDAO.add(newDienThoai);
                list.add(newDienThoai);
                 adapter.notifyDataSetChanged();

                showToast("Đã Thêm Sản phẩm");
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
    //giảm dần
//    private void sortBooksByNameDescending() {
//        Collections.sort(list, new Comparator<DienThoai>() {
//            @Override
//            public int compare(DienThoai dienThoai, DienThoai t1) {
//                return Double.compare(t1.getGiaTien(), dienThoai.getGiaTien());
//            }
//        });
//
//        adapter.notifyDataSetChanged();
//    }
//    //tăng dần
//    private void sortBooksByNameAscending() {
//        Collections.sort(list, new Comparator<DienThoai>() {
//            @Override
//            public int compare(DienThoai dienThoai, DienThoai t1) {
//                return Double.compare(dienThoai.getGiaTien(), t1.getGiaTien());
//            }
//        });
//
//        adapter.notifyDataSetChanged();
//    }
//    //sap xep theo gia tien
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