package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_giohang;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.giohangDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;


public class MainActivity_gio_hang_custom extends AppCompatActivity {
    RecyclerView rcvgh;
    giohangDAO ghDAO;
    adapter_giohang adapter_giohang;
    private TextView txtTongSoLuong;
    private TextView txtTongGia;
    private TextView dathang;

    hoadonDAO hoadonDAO;
    chitietDAO chitietDAO;
    taikhoanDAO taikhoanDAO;
    ArrayList<GioHang> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gio_hang_custom);

        rcvgh = findViewById(R.id.cartView);
        txtTongSoLuong = findViewById(R.id.totalFeeTxt);
        txtTongGia = findViewById(R.id.totalTxt);
        dathang = findViewById(R.id.txtdathang);
        hoadonDAO = new hoadonDAO(this);
        chitietDAO=new chitietDAO(this);
        taikhoanDAO=new taikhoanDAO(this);
        loaddata();
        updateTotalValues();

        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Thiết lập sự kiện click cho nút back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKhachHangInputDialog();
            }
        });
    }


    public void showKhachHangInputDialog() {
        // Lấy đối tượng khóa học tương ứng
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn có chắc chắn muốn đặt hàng này?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa khóa học khỏi cơ sở dữ liệu
                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                String username = preferences.getString("username", "");
                hieunnph32561.du_an_1_hieu_lam.du_an_1_hieu_lam.model.TaiKhoan maKhachHang = taikhoanDAO.getID(username);

                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaKH(maKhachHang.getMaTk());
                hoaDon.setDiaChi(maKhachHang.getDiachi());
                hoaDon.setTongTien((int) adapter_giohang.getTotalPrice());
                hoaDon.setTrangThai(0);
                hoaDon.setNgay(String.valueOf(new Date()));

                long result = hoadonDAO.insert(hoaDon);

                if (result > 0) {
                    int maHoaDonMoiNhat = hoadonDAO.getMaHoaDonMoiNhat();

                    for (GioHang gioHang : list) {
                        int maHoaDon = maHoaDonMoiNhat;
                        ChiTiet chiTietSanPham = new ChiTiet(0, maHoaDon, gioHang.getMadt(), gioHang.getSoLuong(), gioHang.getGia());
                        long chiTietResult = chitietDAO.insert(chiTietSanPham);
                        if (chiTietResult > 0) {
                            Toast.makeText(MainActivity_gio_hang_custom.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    ghDAO.deleteAllGioHang();
                    list.clear();
                    adapter_giohang.notifyDataSetChanged();
                    updateTotalValues();
                } else {
                    Toast.makeText(MainActivity_gio_hang_custom.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }



    public void loaddata() {
        ghDAO = new giohangDAO(this);
        list = ghDAO.getAll();
        adapter_giohang = new adapter_giohang(this, this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvgh.setLayoutManager(layoutManager);
        rcvgh.setAdapter(adapter_giohang);
    }

    public void updateTotalValues() {
        int totalQuantity = 0;
        double totalPrice = 0;

        for (GioHang gioHang : list) {
            totalQuantity += gioHang.getSoLuong();
            totalPrice += gioHang.getSoLuong() * gioHang.getGia();
        }

        txtTongSoLuong.setText(String.valueOf(totalQuantity));
        txtTongGia.setText(String.valueOf(totalPrice));
    }
}