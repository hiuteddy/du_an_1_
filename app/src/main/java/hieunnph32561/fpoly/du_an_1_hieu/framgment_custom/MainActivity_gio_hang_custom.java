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
import android.widget.RadioGroup;
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
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;


public class MainActivity_gio_hang_custom extends AppCompatActivity {
    RecyclerView rcvgh;
    giohangDAO ghDAO;
    adapter_giohang adapter_giohang;
    private TextView txtTongSoLuong;
    private TextView txtTongGia;
    private TextView txthoten, txtdiachi, txtsdt;
    private TextView dathang;
    private String phuongThucVanChuyen;

    hoadonDAO hoadonDAO;
    chitietDAO chitietDAO;
    taikhoanDAO taikhoanDAO;
    ArrayList<GioHang> list = new ArrayList<>();
    TaiKhoan taiKhoan;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gio_hang_custom);

        radioGroup = findViewById(R.id.rdovc);
        txthoten = findViewById(R.id.txthoten);
        txtsdt = findViewById(R.id.txtsdt);
        txtdiachi = findViewById(R.id.txtdiachi);
        rcvgh = findViewById(R.id.cartView);
        txtTongSoLuong = findViewById(R.id.totalFeeTxt);
        txtTongGia = findViewById(R.id.totalTxt);
        dathang = findViewById(R.id.txtdathang);

        hoadonDAO = new hoadonDAO(this);
        chitietDAO = new chitietDAO(this);
        taikhoanDAO = new taikhoanDAO(this);

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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioshipcode) {
                    phuongThucVanChuyen = "Ship cod";
                } else if (checkedId == R.id.radioonile) {
                    phuongThucVanChuyen = "Thanh toán online";
                }
            }
        });
        dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.isEmpty()) {
                    Toast.makeText(MainActivity_gio_hang_custom.this, "Giỏ hàng trống bạn không thể đặt hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity_gio_hang_custom.this, "Vui lòng chọn phương thức giao hàng", Toast.LENGTH_SHORT).show();
                    return;

                }
                showKhachHangInputDialog();
                // radioGroup.se;

            }
        });

        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        taiKhoan = taikhoanDAO.getID(username);
        txthoten.setText("Họ Tên: " + taiKhoan.getHoten());
        txtsdt.setText("Số Điện Thoại:" + taiKhoan.getSdt());
        txtdiachi.setText("Địa Chỉ: " + taiKhoan.getDiachi());


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
                TaiKhoan maKhachHang = taikhoanDAO.getID(username);
                String phuongThuc = phuongThucVanChuyen;


                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaTk(maKhachHang.getMaTk());
                hoaDon.setPhuongthuc(phuongThuc);
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
                            radioGroup.clearCheck();
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