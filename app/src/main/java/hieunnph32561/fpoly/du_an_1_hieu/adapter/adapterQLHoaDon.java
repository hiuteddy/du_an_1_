package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class adapterQLHoaDon extends ArrayAdapter<ChiTiet> {
    private Context context;
    private List<ChiTiet> chiTietList;
    private hoadonDAO daoHD;
    private dienthoaiDAO daoDT;
    private chitietDAO daoCT;
    taikhoanDAO daoTK;
    int trangThai;

    public adapterQLHoaDon(Context context, List<ChiTiet> chiTietList) {
        super(context, R.layout.item_ql_hoa_don, chiTietList);
        this.context = context;
        this.chiTietList = chiTietList;
        daoHD = new hoadonDAO(context);
        daoDT = new dienthoaiDAO(context);
        daoCT = new chitietDAO(context);
        daoTK = new taikhoanDAO(context);
    }

    static class ViewHolder {
        TextView textViewMaHoaDon,textViewNgayHoaDon,textViewTenDT,textViewSoLuong,textViewDonGia,textViewTongTien,textViewNguoiNhan,textViewDiaChiNhan;
        Button btnxn;
        Button btnHuy;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ql_hoa_don, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewMaHoaDon = convertView.findViewById(R.id.maHoaDon);
            viewHolder.textViewNgayHoaDon = convertView.findViewById(R.id.ngayHoaDon);
            viewHolder.textViewTenDT = convertView.findViewById(R.id.hdTendt);
            viewHolder.textViewSoLuong = convertView.findViewById(R.id.hdSoLuong);
            viewHolder.textViewDonGia = convertView.findViewById(R.id.hdDonGia);
            viewHolder.textViewTongTien = convertView.findViewById(R.id.hdTongTien);
            viewHolder.textViewNguoiNhan = convertView.findViewById(R.id.tvNguoiNhan);
            viewHolder.textViewDiaChiNhan = convertView.findViewById(R.id.tvDiachiNhan);
            viewHolder.btnxn = convertView.findViewById(R.id.btnXN);
            viewHolder.btnHuy = convertView.findViewById(R.id.btnHuyDon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChiTiet chiTietItem = chiTietList.get(position);
        HoaDon hoaDon = daoHD.getID(String.valueOf(chiTietItem.getMahd()));
        DienThoai dienThoai = daoDT.getID(String.valueOf(chiTietItem.getMadt()));
        TaiKhoan taiKhoan = daoTK.getIDma(String.valueOf(hoaDon.getMaTk()));

        viewHolder.textViewMaHoaDon.setText("Mã Hóa Đơn: " + chiTietItem.getMahd());
        viewHolder.textViewNgayHoaDon.setText(hoaDon.getNgay());
        viewHolder.textViewTenDT.setText(dienThoai.getTenDT());
        viewHolder.textViewSoLuong.setText("Số Lượng: " + chiTietItem.getSoluong());
        viewHolder.textViewDonGia.setText("Đơn Giá: " + dienThoai.getGiaTien() + " VNĐ");
        viewHolder.textViewTongTien.setText("Tổng Tiền: " + chiTietItem.getGiatien() + " VNĐ");
        viewHolder.textViewNguoiNhan.setText("Người Nhận: " + taiKhoan.getHoten());
        viewHolder.textViewDiaChiNhan.setText("Địa Chỉ: " + taiKhoan.getDiachi());

        trangThai = hoaDon.getTrangThai();
        if (trangThai == 0) {
            viewHolder.btnxn.setText("Xác Nhận");
        } else if (trangThai == 1) {
            viewHolder.btnxn.setText("Giao Hàng");
        } else if (trangThai == 2) {
            viewHolder.btnxn.setText("Hoàn Thành");
        } else if (trangThai == 3) {
            viewHolder.btnxn.setVisibility(View.GONE);
        } else if (trangThai == 4) {
            viewHolder.btnxn.setText("Khôi Phục");
            viewHolder.btnHuy.setVisibility(View.GONE);
        }

        viewHolder.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoHD.update(hoaDon.getMaHD(), 4);
                updateList();
            }
        });

        viewHolder.btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trangThai == 4) {
                    daoHD.update(hoaDon.getMaHD(), 0);
                    updateList();
                } else {
                    daoHD.update(hoaDon.getMaHD(), trangThai + 1);
                    updateList();
                }
            }
        });

        return convertView;
    }
    @SuppressLint("SuspiciousIndentation")
    public void updateList() {
        chiTietList.clear();
        for (ChiTiet x: daoCT.getAll()) {
            HoaDon don = daoHD.getID(String.valueOf(x.getMahd()));
            if (trangThai == don.getTrangThai())
            chiTietList.add(x);
        }
        notifyDataSetChanged();
    }
}