package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_qlsp extends RecyclerView.Adapter<adapter_qlsp.ViewHodelsanpham> {
    private Context context;
    private ArrayList<DienThoai> list;
    private dienthoaiDAO dao;
    private loaidtDAO daoo;
    private List<LoaiSeries> listLS;
    private SpinnerTypeAdapter spinnerTypeAdapter;

    public adapter_qlsp(Context context, ArrayList<DienThoai> list) {
        this.context = context;
        this.list = list;
        dao = new dienthoaiDAO(context);
        daoo = new loaidtDAO(context);
        listLS = daoo.getAll();
    }

    @NonNull
    @Override
    public ViewHodelsanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qldt, parent, false);
        return new ViewHodelsanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelsanpham holder, int position) {
        DienThoai dienThoai = list.get(position);

        LoaiSeries loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        holder.tenDt.setText(dienThoai.getTenDT());
        holder.loaiDt.setText(loaiSeries.getTenLoaiSeri());
        holder.giaDt.setText(String.format("%s VNĐ", dienThoai.getGiaTien()));
        holder.soluong.setText(""+dienThoai.getSoLuong());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Xử lý sự kiện khi item RecyclerView được click
        holder.itemView.setOnClickListener(v -> showEditDialog(dienThoai, position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHodelsanpham extends RecyclerView.ViewHolder {
        TextView tenDt, giaDt, loaiDt,soluong;
        ImageView delete;

        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
            tenDt = itemView.findViewById(R.id.txtqltendt);
            giaDt = itemView.findViewById(R.id.txtqlgia);
            loaiDt = itemView.findViewById(R.id.txtqlloai);
            delete = itemView.findViewById(R.id.btnxoa);
            soluong=itemView.findViewById(R.id.txtqlsl);
        }
    }

    private void showEditDialog(DienThoai dienThoai, int index) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_load_dt);
        dialog.setCancelable(false);
        dao = new dienthoaiDAO(context);

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_load);
        EditText edtTenSP = dialog.findViewById(R.id.editqlName);
        EditText edtGia = dialog.findViewById(R.id.editqlPrice);
        EditText edtMota = dialog.findViewById(R.id.editqlDescribe);
        EditText edtsl=dialog.findViewById(R.id.editsoluong);
        Spinner editqlSeries = dialog.findViewById(R.id.editqlSeries);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSumbit);

        edtTenSP.setText(dienThoai.getTenDT());
        edtGia.setText(String.valueOf(dienThoai.getGiaTien()));
        edtMota.setText(dienThoai.getMoTa());
        edtsl.setText(""+dienThoai.getSoLuong());
        titledialog.setText("Product Repair");
        btnSubmit.setText("Repair");

        spinnerTypeAdapter = new SpinnerTypeAdapter(context, listLS);
        editqlSeries.setAdapter(spinnerTypeAdapter);

        // Tìm index của loại seri hiện tại trong danh sách loại seri
        int selectedTypeIndex = -1;
        for (int i = 0; i < listLS.size(); i++) {
            LoaiSeries loaiSeries = listLS.get(i);
            if (loaiSeries.getMaLoaiSeri() == dienThoai.getMaLoaiSeri()) {
                selectedTypeIndex = i;
                break;
            }
        }

        if (selectedTypeIndex != -1) {
            editqlSeries.setSelection(selectedTypeIndex);
        }

        btnSubmit.setOnClickListener(v -> {
            String tenSP = edtTenSP.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();
            String mota = edtMota.getText().toString().trim();
            int sl= Integer.parseInt(edtsl.getText().toString().trim());

            if (TextUtils.isEmpty(tenSP)) {
                showToast("Vui lòng nhập tên sản phẩm");
            } else {
                Double gia = Double.parseDouble(giaStr);

                dienThoai.setTenDT(tenSP);
                dienThoai.setGiaTien(gia);
                dienThoai.setMoTa(mota);
                dienThoai.setSoLuong(sl);

                // Gán mã loại seri từ Spinner vào đối tượng điện thoại
                LoaiSeries selectedType = (LoaiSeries) editqlSeries.getSelectedItem();
                dienThoai.setMaLoaiSeri(selectedType.getMaLoaiSeri());

                dao.update(dienThoai, dienThoai.getMaDT());
                list.set(index, dienThoai);
                showToast("Đã cập nhật Sản phẩm");
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
