package hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.danhgiaDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_chi_tiet_dt;
import hieunnph32561.fpoly.du_an_1_hieu.model.DanhGia;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_dienthoai extends RecyclerView.Adapter<adapter_dienthoai.ViewHodelsanpham> {
    Context context;
    dienthoaiDAO dao;
    loaidtDAO daoo;
    danhgiaDAO dgDao;
    ArrayList<DienThoai> list;
    LoaiSeries loaiSeries;
    DienThoai dienThoai;

    public adapter_dienthoai(Context context, ArrayList<DienThoai> list) {
        this.context = context;
        this.list = list;
        dao = new dienthoaiDAO(context);
        daoo = new loaidtDAO(context);
        dgDao = new danhgiaDAO(context);
    }


    @NonNull
    @Override
    public ViewHodelsanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dienthoai, parent, false);
        return new ViewHodelsanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelsanpham holder, int position) {
        dienThoai = list.get(position);

        loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        holder.tenDt.setText("" + dienThoai.getTenDT());
        holder.loaiDt.setText(loaiSeries.getTenLoaiSeri());
        holder.giaDt.setText(String.format("%,.0f VNĐ", dienThoai.getGiaTien()));

        double tb = 0;
        int tong = 0, tg = 0;
        for (DanhGia x: dgDao.getAll()) {
            if (x.getMaDt() == dienThoai.getMaDT()){
                tong = tong+x.getDiem();
                tg = tg + 1;
            }
        }
        if (tong != 0 && tg != 0){
            tb = tong/tg;
        }
        holder.danhGiaTB.setText("Đánh Giá: " + tb);

        //lấy ảnh
        Bitmap bitmap;
        byte[] hinhanhDT = dienThoai.getAnhDT();
        if (hinhanhDT != null && hinhanhDT.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(hinhanhDT, 0, hinhanhDT.length);
            holder.anhdt.setImageBitmap(bitmap);
        } else {
            holder.anhdt.setImageResource(R.drawable.baseline_phone_iphone_24);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    DienThoai dienThoai = list.get(position);

                    Intent intent = new Intent(context, MainActivity_chi_tiet_dt.class);
                    loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));
                    String maLoaiSeries = String.valueOf(loaiSeries.getTenLoaiSeri());
                    intent.putExtra("bitmapImage", hinhanhDT);
                    intent.putExtra("maDT", dienThoai.getMaDT());
                    intent.putExtra("tenDT", dienThoai.getTenDT());
                    intent.putExtra("maLoaiSeries", maLoaiSeries);
                    intent.putExtra("giaTien", dienThoai.getGiaTien());
                    intent.putExtra("moTa", dienThoai.getMoTa());
                    context.startActivity(intent);
                }
            }
        });

    }
    public void updateData(ArrayList<DienThoai> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHodelsanpham extends RecyclerView.ViewHolder {
        TextView  tenDt, giaDt, loaiDt, danhGiaTB;
        ImageView anhdt;

        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
            tenDt = itemView.findViewById(R.id.txttendt);
            giaDt = itemView.findViewById(R.id.txtgia);
            loaiDt = itemView.findViewById(R.id.txtloaisr);
            anhdt = itemView.findViewById(R.id.imganh);
            danhGiaTB = itemView.findViewById(R.id.danhGiaTB);
        }
    }
}