package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
<<<<<<< HEAD
=======
import android.content.Intent;
import android.content.SharedPreferences;
>>>>>>> 8d89440 (Initial commit)
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_giohang;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.giohangDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.khachhangDAO;
<<<<<<< HEAD
=======
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
>>>>>>> 8d89440 (Initial commit)
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.KhachHang;
<<<<<<< HEAD
=======
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;
>>>>>>> 8d89440 (Initial commit)


public class MainActivity_gio_hang_custom extends AppCompatActivity {
    RecyclerView rcvgh;
    giohangDAO ghDAO;
    adapter_giohang adapter_giohang;
    private TextView txtTongSoLuong;
    private TextView txtTongGia;
    private TextView dathang;

    hoadonDAO hoadonDAO;
    khachhangDAO khachhangDAO;
    chitietDAO chitietDAO;
<<<<<<< HEAD
=======
    taikhoanDAO taikhoanDAO;
>>>>>>> 8d89440 (Initial commit)
    ArrayList<KhachHang> listkh = new ArrayList<>();
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
        khachhangDAO = new khachhangDAO(this);
        chitietDAO=new chitietDAO(this);
<<<<<<< HEAD
=======
        taikhoanDAO=new taikhoanDAO(this);
>>>>>>> 8d89440 (Initial commit)
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

<<<<<<< HEAD
=======


>>>>>>> 8d89440 (Initial commit)
    private void showKhachHangInputDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_dat_hang, null);

        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtDienThoai = view.findViewById(R.id.edtDienThoai);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
        Button button = view.findViewById(R.id.btndat);

<<<<<<< HEAD
=======




>>>>>>> 8d89440 (Initial commit)
        listkh = khachhangDAO.getAll();
        if (!listkh.isEmpty()) {
             edtDiaChi.setText(listkh.get(0).getDiachi());
            edtDienThoai.setText(String.valueOf(listkh.get(0).getDienThoai()));
            edtHoTen.setText(listkh.get(0).getHoTen());
        }

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
=======
//                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
//                String userId = preferences.getString("userId", ""); // Sử dụng key là "userId"
//                TaiKhoan taiKhoan =taikhoanDAO.getID(userId);
//                hoaDon.setMaKH(taiKhoan.getMaTk());


>>>>>>> 8d89440 (Initial commit)
                int maKhachHang = listkh.get(0).getMaKh();

                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaKH(maKhachHang);
                hoaDon.setDiaChi(edtDiaChi.getText().toString());
                hoaDon.setTongTien((int) adapter_giohang.getTotalPrice());
                hoaDon.setTrangThai(0);
                hoaDon.setNgay(String.valueOf(new Date()));

                long result = hoadonDAO.insert(hoaDon);

                if (result > 0) {
                    int maHoaDonMoiNhat = hoadonDAO.getMaHoaDonMoiNhat(); // Lấy mã hóa đơn mới nhất từ bảng hóa đơn
                  //  list = ghDAO.getAll();

                    for (GioHang gioHang : list) {
                        int maHoaDon = maHoaDonMoiNhat; // Sử dụng mã hóa đơn mới nhất

                        ChiTiet chiTietSanPham = new ChiTiet(0, maHoaDon, gioHang.getMadt(), gioHang.getSoLuong(), gioHang.getGia());

                        long chiTietResult = chitietDAO.insert(chiTietSanPham);
                        if (chiTietResult > 0) {
                        Toast.makeText(MainActivity_gio_hang_custom.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
            //    }


//                else {
//                    Toast.makeText(MainActivity_gio_hang_custom.this, "Thành công", Toast.LENGTH_SHORT).show();
//                }

//                if (result > 0) {
//                    Toast.makeText(MainActivity_gio_hang_custom.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    ghDAO.deleteAllGioHang();
                    list.clear();
                    adapter_giohang.notifyDataSetChanged();
                 //   loaddata();
                    updateTotalValues();


                }
                else {
                    Toast.makeText(MainActivity_gio_hang_custom.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });


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