package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_qlsp extends RecyclerView.Adapter<adapter_qlsp.ViewHodelsanpham> {
    private Fragment fragment;
    public static ImageView anhDT;
    public static int REQUEST_CODE_ADD = 111;
    private Context context;
    private ArrayList<DienThoai> list,listGoc;
    DienThoai dienThoai;
    private dienthoaiDAO dao;
    private loaidtDAO daoo;
    private List<LoaiSeries> listLS;
    private SpinnerTypeAdapter spinnerTypeAdapter;

    public adapter_qlsp(Context context, ArrayList<DienThoai> list,Fragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
        this.listGoc = list;
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
        dienThoai = list.get(position);
        LoaiSeries loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        holder.tenDt.setText(dienThoai.getTenDT());
        holder.loaiDt.setText(loaiSeries.getTenLoaiSeri());
        holder.giaDt.setText(String.format("Giá: %,.0f VNĐ", dienThoai.getGiaTien()));
        holder.soluong.setText("Số Lượng: "+dienThoai.getSoLuong());

        Bitmap bitmap;
        byte[] hinhanhDT = dienThoai.getAnhDT();
        if (hinhanhDT != null && hinhanhDT.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(hinhanhDT, 0, hinhanhDT.length);
            holder.imgqldt.setImageBitmap(bitmap);
        } else {
            bitmap = null;
            holder.imgqldt.setImageResource(R.drawable.baseline_phone_iphone_24);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện xóa sản phẩm tại vị trí position
            }
        });

        // Xử lý sự kiện khi item RecyclerView được click
        holder.itemView.setOnClickListener(v -> showEditDialog(position, bitmap));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHodelsanpham extends RecyclerView.ViewHolder {
        TextView tenDt, giaDt, loaiDt, soluong;
        ImageView delete,imgqldt;

        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
            tenDt = itemView.findViewById(R.id.txtqltendt);
            giaDt = itemView.findViewById(R.id.txtqlgia);
            loaiDt = itemView.findViewById(R.id.txtqlloai);
            delete = itemView.findViewById(R.id.btnxoa);
            soluong = itemView.findViewById(R.id.txtqlsl);
            imgqldt = itemView.findViewById(R.id.imgqldt);
        }
    }

    private void showEditDialog(int index, Bitmap bitmap) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_load_dt);
        dialog.setCancelable(false);
        dao = new dienthoaiDAO(context);
        dienThoai = list.get(index);

        TextView titledialog = dialog.findViewById(R.id.tilte_dialog_load);
        titledialog.setText("Repair Product");

        EditText edtTenSP = dialog.findViewById(R.id.editqlName);
        EditText edtGia = dialog.findViewById(R.id.editqlPrice);
        EditText edtMota = dialog.findViewById(R.id.editqlDescribe);
        EditText edtsl = dialog.findViewById(R.id.editsoluong);
        Spinner editqlSeries = dialog.findViewById(R.id.editqlSeries);
        AppCompatButton btnSubmit = dialog.findViewById(R.id.btnSumbit);

        anhDT= dialog.findViewById(R.id.anhDT);
        if (bitmap!=null) {
            anhDT.setImageBitmap(bitmap);
        }else {
            anhDT.setImageResource(R.drawable.baseline_phone_iphone_24);
        }

        edtTenSP.setText(dienThoai.getTenDT());
        edtGia.setText(String.valueOf(dienThoai.getGiaTien()));
        edtMota.setText(dienThoai.getMoTa());
        edtsl.setText(String.valueOf(dienThoai.getSoLuong()));

        spinnerTypeAdapter = new SpinnerTypeAdapter(context, listLS);
        editqlSeries.setAdapter(spinnerTypeAdapter);
        editqlSeries.setSelection(getSpinnerPosition(dienThoai.getMaLoaiSeri()));

        anhDT.setOnClickListener(v -> {
            openImageChooser();
        });

        btnSubmit.setText("Repair");
        btnSubmit.setOnClickListener(v -> {
            String tenSP = edtTenSP.getText().toString().trim();
            String gia = edtGia.getText().toString().trim();
            String mota = edtMota.getText().toString().trim();
            String soluong = edtsl.getText().toString().trim();

            BitmapDrawable bitmapDrawable = (BitmapDrawable) anhDT.getDrawable();
            Bitmap bitmapEdit = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmapEdit.compress(Bitmap.CompressFormat.PNG, 100,byteArray);
            byte [] bytesAnh = byteArray.toByteArray();

            int selectedSeriesPosition = editqlSeries.getSelectedItemPosition();

            if (TextUtils.isEmpty(tenSP) || TextUtils.isEmpty(gia) || TextUtils.isEmpty(mota) || TextUtils.isEmpty(soluong)) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                dienThoai.setTenDT(tenSP);
                dienThoai.setGiaTien(Double.parseDouble(gia));
                dienThoai.setMoTa(mota);
                dienThoai.setSoLuong(Integer.parseInt(soluong));
                dienThoai.setMaLoaiSeri(listLS.get(selectedSeriesPosition).getMaLoaiSeri());
                dienThoai.setAnhDT(bytesAnh);

                dao.update(dienThoai, dienThoai.getMaDT());
                list.set(index, dienThoai);
                notifyDataSetChanged();
                Toast.makeText(context, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }
    public void sortDescending() {
        Collections.sort(listGoc, new Comparator<DienThoai>() {
            @Override
            public int compare(DienThoai dt1, DienThoai dt2) {
                return Integer.compare(dt2.getSoLuong(), dt1.getSoLuong());
            }
        });
        notifyDataSetChanged();
    }
    public void sortAscending() {
        Collections.sort(listGoc, new Comparator<DienThoai>() {
            @Override
            public int compare(DienThoai dt1, DienThoai dt2) {
                return Integer.compare(dt1.getSoLuong(), dt2.getSoLuong());
            }
        });
        notifyDataSetChanged();
    }

    public void filter(String keyword) {
        ArrayList<DienThoai> filteredList = new ArrayList<>();
        for (DienThoai dt : listGoc) {
            if (dt.getTenDT().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(dt);
            }
        }
        list = filteredList;
        notifyDataSetChanged();
    }
    private int getSpinnerPosition(int maLoaiSeri) {
        for (int i = 0; i < listLS.size(); i++) {
            if (listLS.get(i).getMaLoaiSeri() == maLoaiSeri) {
                return i;
            }
        }
        return 0;
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        fragment.startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CODE_ADD);

    }
}