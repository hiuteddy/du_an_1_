package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_qlsp extends RecyclerView.Adapter<adapter_qlsp.ViewHodelsanpham> {
    private static final int PICK_IMAGE_REQUEST = 1;
    private OnImageSelectedListener onImageSelectedListener;
    private Context context;
    private ArrayList<DienThoai> list;
    DienThoai dienThoai;
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
        dienThoai = list.get(position);

        LoaiSeries loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        holder.tenDt.setText(dienThoai.getTenDT());
        holder.loaiDt.setText(loaiSeries.getTenLoaiSeri());
        holder.giaDt.setText(String.format("%s VNĐ", dienThoai.getGiaTien()));
        holder.soluong.setText(""+dienThoai.getSoLuong());
        if (dienThoai.getAnhDT() != null && !dienThoai.getAnhDT().isEmpty()) {
            Picasso.get().load(dienThoai.getAnhDT())
                    .error(R.drawable.baseline_phone_iphone_24)
                    .into(holder.imgqldt);
        } else {
            Picasso.get().load(R.drawable.baseline_phone_iphone_24)
                    .error(R.drawable.baseline_phone_iphone_24)
                    .into(holder.imgqldt);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện xóa sản phẩm tại vị trí position
            }
        });

        // Xử lý sự kiện khi item RecyclerView được click
        holder.itemView.setOnClickListener(v -> showEditDialog(position));
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

    private void showEditDialog(int index) {
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

        ImageView anhDT = dialog.findViewById(R.id.anhDT);

        edtTenSP.setText(dienThoai.getTenDT());
        edtGia.setText(String.valueOf(dienThoai.getGiaTien()));
        edtMota.setText(dienThoai.getMoTa());
        edtsl.setText(String.valueOf(dienThoai.getSoLuong()));
        if (dienThoai.getAnhDT() != null && !dienThoai.getAnhDT().isEmpty()) {
            Picasso.get().load(dienThoai.getAnhDT()).into(anhDT);
        } else {
            Picasso.get().load(R.drawable.baseline_phone_iphone_24)
                    .error(R.drawable.baseline_phone_iphone_24)
                    .into(anhDT);
        }

        spinnerTypeAdapter = new SpinnerTypeAdapter(context, listLS);
        editqlSeries.setAdapter(spinnerTypeAdapter);
        editqlSeries.setSelection(getSpinnerPosition(dienThoai.getMaLoaiSeri()));

        anhDT.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        onImageSelectedListener = imageUri -> {
            Picasso.get().load(imageUri).into(anhDT);
            dienThoai.setAnhDT(imageUri.toString());
        };

        setOnImageSelectedListener(imageUri -> {
            Picasso.get().load(imageUri).into(anhDT);
            dienThoai.setAnhDT(imageUri.toString());
        });

        btnSubmit.setText("Repair");
        btnSubmit.setOnClickListener(v -> {
            String tenSP = edtTenSP.getText().toString().trim();
            String gia = edtGia.getText().toString().trim();
            String mota = edtMota.getText().toString().trim();
            String soluong = edtsl.getText().toString().trim();
            int selectedSeriesPosition = editqlSeries.getSelectedItemPosition();

            if (TextUtils.isEmpty(tenSP) || TextUtils.isEmpty(gia) || TextUtils.isEmpty(mota) || TextUtils.isEmpty(soluong)) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                dienThoai.setTenDT(tenSP);
                dienThoai.setGiaTien(Double.parseDouble(gia));
                dienThoai.setMoTa(mota);
                dienThoai.setSoLuong(Integer.parseInt(soluong));
                dienThoai.setMaLoaiSeri(listLS.get(selectedSeriesPosition).getMaLoaiSeri());

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

    private int getSpinnerPosition(int maLoaiSeri) {
        for (int i = 0; i < listLS.size(); i++) {
            if (listLS.get(i).getMaLoaiSeri() == maLoaiSeri) {
                return i;
            }
        }
        return 0;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, ImageView anhDT) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            onImageSelectedListener.onImageSelected(filePath);
            dienThoai.setAnhDT(String.valueOf(filePath));
            Picasso.get().load(filePath).into(anhDT);
        }
    }

    public void setOnImageSelectedListener(OnImageSelectedListener listener) {
        this.onImageSelectedListener = listener;
    }

    public interface OnImageSelectedListener {
        void onImageSelected(Uri imageUri);
    }
}