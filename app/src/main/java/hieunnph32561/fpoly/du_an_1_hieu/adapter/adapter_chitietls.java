package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_chitietls extends RecyclerView.Adapter<hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_chitietls.ViewHodelsanpham> {

    Context context;
    ArrayList<ChiTiet> list;
    chitietDAO dao;
    hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO hoadonDAO;
    hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO dienthoaiDAO;
    loaidtDAO loaidtDAO;

    public adapter_chitietls(Context context, ArrayList<ChiTiet> list) {
        this.context = context;
        this.list = list;
        this.dao = new chitietDAO(context);
        hoadonDAO = new hoadonDAO(context);
        dienthoaiDAO = new dienthoaiDAO(context);
        loaidtDAO=new loaidtDAO(context);
    }

    @NonNull
    @Override
    public hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_chitietls.ViewHodelsanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lschitiet, parent, false);
        return new hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_chitietls.ViewHodelsanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_chitietls.ViewHodelsanpham holder, int position) {
        ChiTiet chiTiet = list.get(position);

        DienThoai dienThoai=dienthoaiDAO.getID(String.valueOf(chiTiet.getMadt()));

        LoaiSeries loaiSeries=loaidtDAO.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

       holder.txtmachitiet.setText("Mã chi tiết đơn: "+String.valueOf(chiTiet.getMact()));
        holder.txtmadt.setText("Tên điện thoại: "+String.valueOf(dienThoai.getTenDT()));
        holder.txthoadon.setText("Tên loại: " + loaiSeries.getTenLoaiSeri());
        holder.txtsoluong.setText("Số lượng: " + chiTiet.getSoluong());
        holder.txtgiatien.setText("Giá điện thoại: " + chiTiet.getGiatien());
        holder.txttongtien.setText("Tổng tiền:" + chiTiet.getGiatien() * chiTiet.getSoluong());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHodelsanpham extends RecyclerView.ViewHolder {
        TextView txtmachitiet, txtmadt, txthoadon, txtsoluong, txtgiatien,txttongtien;

        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
            txtmachitiet = itemView.findViewById(R.id.txtmachitiet);
            txtmadt = itemView.findViewById(R.id.txtmadt);
            txthoadon = itemView.findViewById(R.id.txthoadon);
            txtsoluong = itemView.findViewById(R.id.txtsoluong);
            txtgiatien = itemView.findViewById(R.id.txtgiadt);
            txttongtien=itemView.findViewById(R.id.txttongtien);

        }
    }
}

