package hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.giohangDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.loaidtDAO;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_gio_hang_custom;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_giohang extends RecyclerView.Adapter<adapter_giohang.ViewHodelsanpham> {
    Context context;
    ArrayList<GioHang> list;
    giohangDAO dao;
    dienthoaiDAO daoo;
    LoaiSeries loaiSeries;
    DienThoai dienThoai;
    loaidtDAO loaidtDAO;

    private MainActivity_gio_hang_custom mActivity;

    public adapter_giohang(MainActivity_gio_hang_custom activity, Context context, ArrayList<GioHang> list) {
        this.mActivity = activity;
        this.context = context;
        this.list = list;
        this.dao = new giohangDAO(context);
        daoo = new dienthoaiDAO(context);
        loaidtDAO = new loaidtDAO(context);
    }

    @NonNull
    @Override
    public adapter_giohang.ViewHodelsanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gio, parent, false);
        return new adapter_giohang.ViewHodelsanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_giohang.ViewHodelsanpham holder, int position) {
        GioHang gioHang = list.get(position);

        dienThoai = daoo.getID(String.valueOf(gioHang.getMadt()));
        loaiSeries = loaidtDAO.getID(String.valueOf(gioHang.getMadt()));

        holder.tenDt.setText("" + dienThoai.getTenDT());
        holder.soluong.setText("" + gioHang.getSoLuong());
        holder.giaDt.setText(String.format("%,.0f VNĐ", gioHang.getGia()));
        holder.tongtien.setText(String.format("%,.0f VNĐ", gioHang.getSoLuong() * gioHang.getGia() + 20000) );
        holder.soluong.setText(String.valueOf(gioHang.getSoLuong()));

        //update giá từng sản phẩm
        updateTotalPrice(holder, gioHang);
        //update giá tổng đơn hàng
        updateTotalValues();

        byte[] anhData = daoo.getAnhByMaDT(dienThoai.getMaDT());
        if (anhData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhData, 0, anhData.length);
            holder.anh.setImageBitmap(bitmap);
        } else {
            holder.anh.setImageResource(R.drawable.iphone15);
        }
        holder.imgcong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                dienThoai = daoo.getID(String.valueOf(gioHang.getMadt()));
                if (soLuong < dienThoai.getSoLuong()) {
                    soLuong++;
                    gioHang.setSoLuong(soLuong);
                    holder.soluong.setText(String.valueOf(soLuong));
                    updateTotalPrice(holder, gioHang);
                    notifyDataSetChanged();
                    updateTotalValues(); // Cập nhật tổng số lượng và tổng giá trị
                } else {
                    Toast.makeText(context, "Số lượng sản phẩm vượt quá số lượng có sẵn trong kho", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imgtru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                if (soLuong > 1) {
                    soLuong--;
                    gioHang.setSoLuong(soLuong);
                    holder.soluong.setText(String.valueOf(soLuong));
                    updateTotalPrice(holder, gioHang);
                    notifyDataSetChanged(); // Cập nhật lại giao diện sau khi thay đổi số lượng
                    updateTotalValues(); // Cập nhật tổng số lượng và tổng giá trị
                }
            }
        });
        holder.txtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dao.delete(Integer.parseInt(String.valueOf(gioHang.getMadt()))) > 0) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(dao.getAll());
                            notifyDataSetChanged();
                            updateTotalValues(); // Cập nhật tổng số lượng và tổng giá trị
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHodelsanpham extends RecyclerView.ViewHolder {
        TextView tenDt, giaDt, soluong, tongtien;
        ImageView txtdelete, imgtru, imgcong, anh;

        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
            tenDt = itemView.findViewById(R.id.titleTxt);
            giaDt = itemView.findViewById(R.id.feeEachItem);
            soluong = itemView.findViewById(R.id.numberItemTxt);
            tongtien = itemView.findViewById(R.id.totalEachItem);
            txtdelete = itemView.findViewById(R.id.imgdelete);
            imgtru = itemView.findViewById(R.id.minusCartBtn);
            imgcong = itemView.findViewById(R.id.plusCartBtn);
            anh = itemView.findViewById(R.id.picCart);
        }
    }

    private void updateTotalPrice(adapter_giohang.ViewHodelsanpham holder, GioHang gioHang) {
        int tongTien = (int) (gioHang.getSoLuong() * gioHang.getGia());
        holder.tongtien.setText(String.format("%,d VNĐ",tongTien));
    }

    private void updateTotalValues() {
        mActivity.updateTotalValues();
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (GioHang gioHang : list) {
            totalPrice += gioHang.getGia() * gioHang.getSoLuong();
        }
        return totalPrice;
    }
}