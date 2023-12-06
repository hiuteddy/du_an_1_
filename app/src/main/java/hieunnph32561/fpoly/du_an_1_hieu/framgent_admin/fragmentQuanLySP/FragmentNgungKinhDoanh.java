package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentQuanLySP;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adpter_cua_nam.SpinnerTypeAdapter;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adpter_cua_nam.adapter_qlsp;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class FragmentNgungKinhDoanh extends Fragment {
    RecyclerView rcvqldt;
    FloatingActionButton btnAddSp;
    adapter_qlsp adapter;
    ArrayList<DienThoai> list;
    List<LoaiSeries> listLS;
    dienthoaiDAO dtDAO;
    loaidtDAO loaidao;
    ImageView imgHinhSP;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kinh_doanh, container, false);

        rcvqldt = view.findViewById(R.id.rcvqldt);
        btnAddSp = view.findViewById(R.id.floatBtnAdd);
        btnAddSp.setVisibility(View.GONE);
        dtDAO = new dienthoaiDAO(getContext());
        loaidao = new loaidtDAO(getContext());
        listLS = loaidao.getAll();
        list = dtDAO.getAllNKD();

        adapter = new adapter_qlsp(getContext(), list,this);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
        updateAdapterData();
        return view;
    }
    private void updateAdapterData() {
        list = dtDAO.getAllNKD();
        adapter.notifyDataSetChanged(); // Update the adapter
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == adapter_qlsp.REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                    if (imgHinhSP !=null) {
                        imgHinhSP.setImageBitmap(bitmap);
                    }
                    if (adapter_qlsp.anhDT != null){
                        adapter_qlsp.anhDT.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    showToast("Không thể đọc được hình ảnh");
                }
            } else {
                showToast("Không có hình ảnh được chọn");
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.asc) {
            adapter.sortAscending();
            return true;
        } else if (id == R.id.desc) {
            adapter.sortDescending();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}