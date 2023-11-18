package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_chi_tiet_ls;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;


public abstract class adapter_lichsu extends RecyclerView.Adapter<adapter_lichsu.ViewHodelsanpham> {
    Context context;
    ArrayList<HoaDon> list;
    chitietDAO dao;
    hoadonDAO hoadonDAO;
    dienthoaiDAO dienthoaiDAO;
    taikhoanDAO taikhoanDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



    public adapter_lichsu(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
        this.dao = new chitietDAO(context);
        hoadonDAO = new hoadonDAO(context);
        dienthoaiDAO = new dienthoaiDAO(context);
        taikhoanDAO=new taikhoanDAO(context);
    }

    @NonNull
    @Override
    public adapter_lichsu.ViewHodelsanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lich_su, parent, false);
        return new ViewHodelsanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_lichsu.ViewHodelsanpham holder, int position) {
        HoaDon hoaDon = list.get(position);



        TaiKhoan taiKhoan = taikhoanDAO.getIDma(String.valueOf(hoaDon.getMaTk()));

        holder.txtmadon.setText("Mã hóa đơn: " + String.valueOf(hoaDon.getMaHD()));
        holder.txtngay.setText("Ngày đặt: " + String.valueOf(sdf.format(hoaDon.getNgay())));
        holder.txtdienthoai.setText("Số điện thoại: " + "" + taiKhoan.getSdt());
        holder.txtmaKH.setText("Tên khách hàng: " + "" + taiKhoan.getHoten());
        holder.txttongTien.setText("Tổng tiền: " + hoaDon.getTongTien());
        holder.txttrangThai.setText("Trạng thái: " + hoaDon.getTrangThai());
        if (hoaDon.getTrangThai() == 0) {
            holder.txttrangThai.setText("Trạng thái: Chờ xác nhân");
        } else if (hoaDon.getTrangThai() == 1) {
            holder.txttrangThai.setText("Trạng thái: Đã xác nhân");
        } else if (hoaDon.getTrangThai() == 2) {
            holder.txttrangThai.setText("Trạng thái: Đang giao");
            holder.btnhuy.setVisibility(View.GONE);
        } else if (hoaDon.getTrangThai() == 3) {
            holder.txttrangThai.setText("Trạng thái: Giao hàng thành công");
            holder.btnhuy.setVisibility(View.GONE);
        } else if (hoaDon.getTrangThai() == 4) {
            holder.txttrangThai.setText("Trạng thái: Đã hủy");
            holder.btnhuy.setVisibility(View.GONE);
        }
        holder.txtdiaChi.setText("Địa chỉ: " + taiKhoan.getDiachi());
        holder.txtphuongthuc.setText("Phương thức: " + hoaDon.getPhuongthuc());


        holder.btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bạn đã chọn hóa đơn: " + hoaDon.getMaHD(), Toast.LENGTH_SHORT).show();
//                // Chuyển sang Activity ProductDetailActivity
                Intent intent = new Intent(context, MainActivity_chi_tiet_ls.class);
                intent.putExtra("productId", hoaDon.getMaHD()); // Truyền mã sản phẩm
                context.startActivity(intent);
            }
        });
        holder.btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy đối tượng khóa học tương ứng
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận hủy");
                builder.setMessage("Bạn có chắc chắn muốn hủy sản phẩm này?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa khóa học khỏi cơ sở dữ liệu
                        if (hoadonDAO.update(hoaDon.getMaHD(), 4) > 0) {
                            Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(hoadonDAO.getAll());
                            notifyDataSetChanged(); // Cập nhật lại dữ liệu trên RecyclerView
                        } else {
                            Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hủy thao tác xóa
                        dialog.dismiss();
                    }
                });
                // Hiển thị hộp thoại xác nhận xóa
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
        TextView txtmaKH, txttongTien, txtngay, txttrangThai, txtdiaChi, txtmadon, txtdienthoai,txtphuongthuc;
        Button btnchitiet;
        Button btnchitiett, btnhuy;


        public ViewHodelsanpham(@NonNull View itemView) {
            super(itemView);
            txtmadon = itemView.findViewById(R.id.txtmadon);
            txtmaKH = itemView.findViewById(R.id.txtmakh);
            txtngay = itemView.findViewById(R.id.txtngaydat);
            txttrangThai = itemView.findViewById(R.id.txttrangthai);
            txtdiaChi = itemView.findViewById(R.id.txtdiachi);
            txttongTien = itemView.findViewById(R.id.tongTien);
            txtdienthoai = itemView.findViewById(R.id.txtdienthoai);
            txtphuongthuc=itemView.findViewById(R.id.txtphuongthuc);

            btnchitiet = itemView.findViewById(R.id.btnchitiett);
            btnhuy = itemView.findViewById(R.id.btnxoa);

        }
    }
}
