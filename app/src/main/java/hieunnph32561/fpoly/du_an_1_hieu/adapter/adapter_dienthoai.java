package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_chi_tiet_dt;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_gio_hang_custom;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_dienthoai extends RecyclerView.Adapter<adapter_dienthoai.ViewHodelsanpham> {
    Context context;
    ArrayList<DienThoai> list;
    dienthoaiDAO dao;
    loaidtDAO daoo;
    LoaiSeries loaiSeries;
    ArrayList<LoaiSeries> listLS = new ArrayList<>();



    public adapter_dienthoai(Context context, ArrayList<DienThoai> list) {
        this.context = context;
        this.list = list;
        dao = new dienthoaiDAO(context);
        daoo = new loaidtDAO(context);
    }


    @NonNull
    @Override
    public ViewHodelsanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dienthoai, parent, false);
        return new ViewHodelsanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelsanpham holder, int position) {
        DienThoai dienThoai = list.get(position);

        // Lấy thông tin loại series từ bảng LoaiSeries dựa trên khóa ngoại maLoaiSeries
        loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

     //   holder.maDt.setText("" + dienThoai.getMaDT());
        holder.tenDt.setText("" + dienThoai.getTenDT());
        holder.loaiDt.setText(loaiSeries.getTenLoaiSeri()); // Lấy tên loại series từ đối tượng LoaiSeries
        holder.giaDt.setText("" + dienThoai.getGiaTien());
       // holder.chiTiet.setText("" + dienThoai.getMoTa());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    DienThoai dienThoai = list.get(position);

                    Intent intent = new Intent(context, MainActivity_chi_tiet_dt.class);
                    loaiSeries = daoo.getID(String.valueOf(dienThoai.getMaLoaiSeri()));
                    String maLoaiSeries = String.valueOf(loaiSeries.getTenLoaiSeri());
                    intent.putExtra("maDT", dienThoai.getMaDT());
                    intent.putExtra("tenDT", dienThoai.getTenDT());
                    intent.putExtra("maLoaiSeries", maLoaiSeries);
                    intent.putExtra("giaTien", dienThoai.getGiaTien());
                    intent.putExtra("moTa", dienThoai.getMoTa());

                    // Đính kèm các dữ liệu khác cần thiết

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
        TextView  tenDt, giaDt, loaiDt, chiTiet;
        ImageView txtdelete;

        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
           // maDt = itemView.findViewById(R.id.txtmasach);
            tenDt = itemView.findViewById(R.id.txttendt);
            giaDt = itemView.findViewById(R.id.txtgia);
            loaiDt = itemView.findViewById(R.id.txtloaisr);
            chiTiet = itemView.findViewById(R.id.txtchitiet);
        }
    }
}