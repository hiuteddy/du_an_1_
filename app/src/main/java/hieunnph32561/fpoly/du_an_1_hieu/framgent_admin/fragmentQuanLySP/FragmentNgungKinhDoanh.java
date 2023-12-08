package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentQuanLySP;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adpter_cua_nam.adapter_qlsp;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class FragmentNgungKinhDoanh extends Fragment {
    RecyclerView rcvqldt;
    FloatingActionButton btnAddSp;
    adapter_qlsp adapter;
    ArrayList<DienThoai> list;
    ArrayList<DienThoai> listdlm;

    List<LoaiSeries> listLS;
    dienthoaiDAO dtDAO;
    loaidtDAO loaidao;
    ImageView imgHinhSP;


    @Override
    public void onResume() {
        super.onResume();
      //  loaddata();
        updateAdapterData();

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
        list = dtDAO.getAllNKD(1);

        adapter = new adapter_qlsp(getContext(), list, this);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
        updateAdapterData();
        return view;
    }

    private void updateAdapterData() {
        list.clear(); // Clear old list
        listdlm = dtDAO.getAllNKD(1);

        for (DienThoai x : listdlm) {
            if (x.getTrangThai() == 1) {
                list.add(x); // Add to the new list only when the status is 0 and the account ID matches
            }
            if (x.getTrangThai() == 0) {
                list.remove(x);
                // Remove the invoice from the list if the status is 4 and the account ID matches
            }
        }

        adapter.notifyDataSetChanged(); // Update
    }

    public void loaddata() {
        list = dtDAO.getAllKDD(1);
        adapter = new adapter_qlsp(getContext(), list, this);
        rcvqldt.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvqldt.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == adapter_qlsp.REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                    if (imgHinhSP != null) {
                        imgHinhSP.setImageBitmap(bitmap);
                    }
                    if (adapter_qlsp.anhDT != null) {
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