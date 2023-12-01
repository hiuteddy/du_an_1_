package hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_danh_gia_custom;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_chitietls extends RecyclerView.Adapter<adapter_chitietls.ViewHodelsanpham> {

    Context context;
    ArrayList<ChiTiet> list;
    chitietDAO dao;
    hoadonDAO hoadonDAO;
    dienthoaiDAO dienthoaiDAO;
    loaidtDAO loaidtDAO;


    public adapter_chitietls(Context context, ArrayList<ChiTiet> list) {
        this.context = context;
        this.list = list;
        this.dao = new chitietDAO(context);
        hoadonDAO = new hoadonDAO(context);
        dienthoaiDAO = new dienthoaiDAO(context);
        loaidtDAO = new loaidtDAO(context);
    }

    @NonNull
    @Override
    public adapter_chitietls.ViewHodelsanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lschitiet, parent, false);
        return new adapter_chitietls.ViewHodelsanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_chitietls.ViewHodelsanpham holder, int position) {
        ChiTiet chiTiet = list.get(position);

        DienThoai dienThoai = dienthoaiDAO.getID(String.valueOf(chiTiet.getMadt()));

        LoaiSeries loaiSeries = loaidtDAO.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        HoaDon hoaDon = hoadonDAO.getID(String.valueOf(chiTiet.getMahd()));

        holder.txtmachitiet.setText("Mã chi tiết đơn: " + String.valueOf(chiTiet.getMact()));
        holder.txtmadt.setText("Tên điện thoại: " + String.valueOf(dienThoai.getTenDT()));
        holder.txthoadon.setText("Tên loại: " + loaiSeries.getTenLoaiSeri());
        holder.txtsoluong.setText("Số lượng: " + chiTiet.getSoluong());
        holder.txtgiatien.setText(String.format("Giá điện thoại: %,.0f VNĐ", chiTiet.getGiatien()) );
        holder.txttongtien.setText(String.format("Tổng tiền: %,.0f VNĐ", chiTiet.getGiatien()* chiTiet.getSoluong()) );

        byte[] anhData = dienthoaiDAO.getAnhByMaDT(dienThoai.getMaDT());
        if (anhData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhData, 0, anhData.length);
            holder.imganh.setImageBitmap(bitmap);
        } else {
            holder.imganh.setImageResource(R.drawable.iphone15);
        }
        if (hoaDon.getTrangThai() == 3 ){
            holder.btndg.setVisibility(View.VISIBLE);

        }else {
            holder.btndg.setVisibility(View.GONE);
        }
        holder.btndg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_danh_gia_custom.class);
                intent.putExtra("productId", chiTiet.getMadt()); // Truyền mã sản phẩm
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHodelsanpham extends RecyclerView.ViewHolder {
        TextView txtmachitiet, txtmadt, txthoadon, txtsoluong, txtgiatien, txttongtien;
        ImageView imganh;
        Button btndg;

        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
            txtmachitiet = itemView.findViewById(R.id.txtmachitiet);
            txtmadt = itemView.findViewById(R.id.txtmadt);
            txthoadon = itemView.findViewById(R.id.txthoadon);
            txtsoluong = itemView.findViewById(R.id.txtsoluong);
            txtgiatien = itemView.findViewById(R.id.txtgiadt);
            txttongtien = itemView.findViewById(R.id.txttongtien);
            imganh=itemView.findViewById(R.id.imganh);
            btndg=itemView.findViewById(R.id.btndanhg);

        }
    }
}

