package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
    private static final int PICK_IMAGE_REQUEST = 1;
    private SearchView searchView;
    RecyclerView rcvqldt;
    FloatingActionButton btnAddSp;
    dienthoaiDAO dtDAO;
    Uri imageUri;
    Dialog dialog;
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
        btnAddSp.setOnClickListener(v -> showAddDialog());
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
        // Rest of your onCreateOptionsMenu code
    }

    private void showAddDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_load_dt);
        dialog.setCancelable(false);
        dtDAO = new dienthoaiDAO(getContext());

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_load);
        EditText edtTenSP = dialog.findViewById(R.id.editqlName);
        Spinner spnLoaiSP = dialog.findViewById(R.id.editqlSeries);
        ImageView imgHinhSP = dialog.findViewById(R.id.anhDT);
        EditText edtGia = dialog.findViewById(R.id.editqlPrice);
        EditText edtMoTa = dialog.findViewById(R.id.editqlDescribe);
        EditText edtSoLuong = dialog.findViewById(R.id.editsoluong);
        AppCompatButton btnAdd = dialog.findViewById(R.id.btnSumbit);

        titledialog.setText("Add Product");

        spinnerTypeAdapter = new SpinnerTypeAdapter(getContext(), listLS);
        spnLoaiSP.setAdapter(spinnerTypeAdapter);

        imgHinhSP.setOnClickListener(v -> openImageChooser());

        btnAdd.setText("Add");
        btnAdd.setOnClickListener(v -> {
            String tenSP = edtTenSP.getText().toString().trim();
            LoaiSeries loaiSP = (LoaiSeries) spnLoaiSP.getSelectedItem();
            DienThoai dienThoai = new DienThoai();
            if (TextUtils.isEmpty(tenSP) || loaiSP == null || imageUri == null || TextUtils.isEmpty(edtGia.getText().toString())) {
                showToast("Vui lòng nhập đầy đủ thông tin");
            } else {
                try {
                    dienThoai.setMaLoaiSeri(loaiSP.getMaLoaiSeri());
                    dienThoai.setGiaTien(Double.parseDouble(edtGia.getText().toString().trim()));
                    dienThoai.setMoTa(edtMoTa.getText().toString().trim());
                    dienThoai.setAnhDT(imageUri.toString());
                    dienThoai.setTenDT(tenSP);
                    dienThoai.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString().trim()));
                    dtDAO.add(dienThoai);
                    showToast("Thêm sản phẩm thành công");
                    dialog.dismiss();
                    LoadData();
                } catch (NumberFormatException e) {
                    showToast("Lỗi định dạng số, vui lòng kiểm tra lại giá và số lượng");
                }
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }
}
