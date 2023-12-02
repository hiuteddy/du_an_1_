package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.Trangchu;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu.adapter_giohang;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.giohangDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;


public class MainActivity_gio_hang_custom extends AppCompatActivity {

    private RecyclerView rcvgh;
    private adapter_giohang adapterGioHang;
    private TextView txtTongSoLuong, txtphiship, txtTongGia, txthoten, txtdiachi, txtsdt, dathang, stk, tnh;
    private String phuongThucVanChuyen;
    private hoadonDAO hoadonDAO;
    private chitietDAO chitietDAO;
    private taikhoanDAO taikhoanDAO;
    private dienthoaiDAO dienthoaiDAO;
    private giohangDAO ghDAO;

    private ArrayList<GioHang> gioHangList = new ArrayList<>();
    private TaiKhoan taiKhoan;
    private RadioGroup radioGroup;
    private Double shipperPrice = 0.0;
    private long millis = System.currentTimeMillis();
    private java.sql.Date date = new java.sql.Date(millis);
    private ImageView imggio, suatt;
    private Button btnstar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gio_hang_custom);

        initializeViews();
        setupToolbar();
        setupListeners();
        loadUserData();
        setupRecyclerView();
    }

    private void initializeViews() {
        rcvgh = findViewById(R.id.cartView);
        txtTongSoLuong = findViewById(R.id.totalFeeTxt);
        txtphiship = findViewById(R.id.deliveryTxt);
        txtTongGia = findViewById(R.id.totalTxt);
        txthoten = findViewById(R.id.txthoten);
        txtdiachi = findViewById(R.id.txtdiachi);
        txtsdt = findViewById(R.id.txtsdt);
        dathang = findViewById(R.id.txtdathang);
        radioGroup = findViewById(R.id.rdovc);
        imggio = findViewById(R.id.gio);
        btnstar = findViewById(R.id.chu);
        suatt = findViewById(R.id.imgsua);
        stk = findViewById(R.id.textView10);
        tnh = findViewById(R.id.textView6);

        hoadonDAO = new hoadonDAO(this);
        chitietDAO = new chitietDAO(this);
        taikhoanDAO = new taikhoanDAO(this);
        dienthoaiDAO = new dienthoaiDAO(this);
        stk.setVisibility(View.GONE);
        tnh.setVisibility(View.GONE);

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setupListeners() {
        btnstar.setOnClickListener(v -> startActivity(new Intent(this, Trangchu.class)));

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioshipcode) {
                phuongThucVanChuyen = "Ship cod";
                shipperPrice = 20000.0;
                txtphiship.setText(String.format("%,d VNĐ", 20000));
                updateTotalValues();
                stk.setVisibility(View.GONE);
                tnh.setVisibility(View.GONE);

            } else if (checkedId == R.id.radioonile) {
                phuongThucVanChuyen = "Thanh toán online";
                shipperPrice = 0.0;
                txtphiship.setText(String.format("%,d VNĐ", 0));
                updateTotalValues();
                stk.setVisibility(View.VISIBLE);
                tnh.setVisibility(View.VISIBLE);
            }
        });

        dathang.setOnClickListener(v -> {
            if (gioHangList.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống bạn không thể đặt hàng", Toast.LENGTH_SHORT).show();
            } else if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức giao hàng", Toast.LENGTH_SHORT).show();
            } else {
                showKhachHangInputDialog();
            }
        });

        suatt.setOnClickListener(v -> showEditUserInfoDialog());
    }

    private void showKhachHangInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn có chắc chắn muốn đặt hàng này?");
        builder.setPositiveButton("Yes", (dialog, which) -> placeOrder());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void placeOrder() {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        TaiKhoan maKhachHang = taikhoanDAO.getID(username);
        String phuongThuc = phuongThucVanChuyen;

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaTk(maKhachHang.getMaTk());
        hoaDon.setPhuongthuc(phuongThuc);
        hoaDon.setTongTien((double) adapterGioHang.getTotalPrice());
        hoaDon.setTrangThai(0);
        hoaDon.setNgay((java.sql.Date.valueOf(String.valueOf(date))));

        long result = hoadonDAO.insert(hoaDon);

        if (result > 0) {
            int maHoaDonMoiNhat = hoadonDAO.getMaHoaDonMoiNhat();

            for (GioHang gioHang : gioHangList) {
                int maHoaDon = maHoaDonMoiNhat;
                ChiTiet chiTietSanPham = new ChiTiet(0, maHoaDon, gioHang.getMadt(), gioHang.getSoLuong(), gioHang.getGia());
                long chiTietResult = chitietDAO.insert(chiTietSanPham);
                if (chiTietResult > 0) {
                    startActivity(new Intent(this, Trangchu.class));
                    Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    radioGroup.clearCheck();
                    int soLuong = chiTietSanPham.getSoluong();
                    dienthoaiDAO.updateSoLuong(gioHang.getMadt(), soLuong);
                    showNotification();
                }
            }
            ghDAO.deleteAllGioHang(taiKhoan.getMaTk());
            gioHangList.clear();
            adapterGioHang.notifyDataSetChanged();
            updateTotalValues();
        } else {
            Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserData() {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        taiKhoan = taikhoanDAO.getID(username);
        txthoten.setText(String.valueOf(taiKhoan.getHoten()));
        txtsdt.setText(String.valueOf(taiKhoan.getSdt()));
        txtdiachi.setText(String.valueOf(taiKhoan.getDiachi()));
    }

    private void setupRecyclerView() {
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        taiKhoan = taikhoanDAO.getID(username);
        ghDAO = new giohangDAO(this);
        gioHangList = ghDAO.getAllByMaKhachHang(taiKhoan.getMaTk());
        adapterGioHang = new adapter_giohang(this, this, gioHangList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvgh.setLayoutManager(layoutManager);
        rcvgh.setAdapter(adapterGioHang);
        if (gioHangList.isEmpty()) {
            imggio.setVisibility(View.VISIBLE);
            btnstar.setVisibility(View.VISIBLE);
        } else {
            imggio.setVisibility(View.GONE);
            btnstar.setVisibility(View.GONE);
        }
    }

    public void updateTotalValues() {
        int totalQuantity = 0;
        double totalPrice = 0;

        for (GioHang gioHang : gioHangList) {
            totalQuantity += gioHang.getSoLuong();
            totalPrice += gioHang.getSoLuong() * gioHang.getGia();
        }
        totalPrice += shipperPrice;
        txtTongSoLuong.setText(String.valueOf(totalQuantity));
        txtTongGia.setText(String.format("%,.0f VNĐ", totalPrice));
    }

    @SuppressLint("MissingPermission")
    private void showNotification() {
        RemoteViews notificationBigLayout = new RemoteViews(getPackageName(), R.layout.linner_notification);

        notificationBigLayout.setImageViewResource(R.id.notification_icon, R.drawable.baseline_circle_notifications_24);
        notificationBigLayout.setTextViewText(R.id.notification_title, "Đặt hàng thành công");
        notificationBigLayout.setTextViewText(R.id.notification_message, "Đơn hàng của bạn đã được đặt thành công");
        notificationBigLayout.setViewPadding(R.id.notification_icon, 0, 0, 0, 0);
        notificationBigLayout.setViewPadding(R.id.notification_title, 0, 0, 0, 0);
        notificationBigLayout.setViewPadding(R.id.notification_message, 0, 0, 0, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomBigContentView(notificationBigLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void showEditUserInfoDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_suadc, null);

        EditText etHoTen = dialogView.findViewById(R.id.ethitenu);
        EditText etPhone = dialogView.findViewById(R.id.etPhoneu);
        EditText etDiaChi = dialogView.findViewById(R.id.etDiaChiu);

        etHoTen.setText(taiKhoan.getHoten());
        etPhone.setText(taiKhoan.getSdt());
        etDiaChi.setText(taiKhoan.getDiachi());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    String hoTen = etHoTen.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String diaChi = etDiaChi.getText().toString().trim();

                    if (hoTen.isEmpty() || phone.isEmpty() || diaChi.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    taiKhoan.setHoten(hoTen);
                    taiKhoan.setSdt(phone);
                    taiKhoan.setDiachi(diaChi);
                    taikhoanDAO.update(taiKhoan);

                    txthoten.setText(hoTen);
                    txtsdt.setText(phone);
                    txtdiachi.setText(diaChi);

                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}


//public class MainActivity_gio_hang_custom extends AppCompatActivity {
//    RecyclerView rcvgh;
//    adapter_giohang adapter_giohang;
//    TextView txtTongSoLuong, txtphiship, txtTongGia, txthoten, txtdiachi, txtsdt, dathang;
//    String phuongThucVanChuyen;
//    hoadonDAO hoadonDAO;
//    chitietDAO chitietDAO;
//    taikhoanDAO taikhoanDAO;
//    dienthoaiDAO dienthoaiDAO;
//    giohangDAO ghDAO;
//
//    ArrayList<GioHang> list = new ArrayList<>();
//    TaiKhoan taiKhoan;
//    RadioGroup radioGroup;
//    Double shipperPrice = 0.0;
//    long millis = System.currentTimeMillis();
//    java.sql.Date date = new java.sql.Date(millis);
//    ImageView imggio, suatt;
//    Button btnstar;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_gio_hang_custom);
//
//        radioGroup = findViewById(R.id.rdovc);
//        txthoten = findViewById(R.id.txthoten);
//        txtsdt = findViewById(R.id.txtsdt);
//        txtdiachi = findViewById(R.id.txtdiachi);
//        rcvgh = findViewById(R.id.cartView);
//        txtTongSoLuong = findViewById(R.id.totalFeeTxt);
//        txtTongGia = findViewById(R.id.totalTxt);
//        dathang = findViewById(R.id.txtdathang);
//        txtphiship = findViewById(R.id.deliveryTxt);
//        imggio = findViewById(R.id.gio);
//        btnstar = findViewById(R.id.chu);
//        suatt = findViewById(R.id.imgsua);
//
//        hoadonDAO = new hoadonDAO(this);
//        chitietDAO = new chitietDAO(this);
//        taikhoanDAO = new taikhoanDAO(this);
//        dienthoaiDAO = new dienthoaiDAO(this);
//
//        updateTotalValues();
//        loaddata();
//        suattnd();
//
//        Toolbar toolbar = findViewById(R.id.toolbarr);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        btnstar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity_gio_hang_custom.this, Trangchu.class));
//            }
//        });
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.radioshipcode) {
//                    phuongThucVanChuyen = "Ship cod";
//                    shipperPrice = 20000.0;
//                    txtphiship.setText(String.format("%,d VNĐ", 20000));
//                    updateTotalValues();
//
//                } else if (checkedId == R.id.radioonile) {
//                    phuongThucVanChuyen = "Thanh toán online";
//                    shipperPrice = 0.0;
//                    txtphiship.setText(String.format("%,d VNĐ", 0));
//                    updateTotalValues();
//
//                }
//            }
//        });
//        dathang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (list.isEmpty()) {
//                    Toast.makeText(MainActivity_gio_hang_custom.this, "Giỏ hàng trống bạn không thể đặt hàng", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (radioGroup.getCheckedRadioButtonId() == -1) {
//                    Toast.makeText(MainActivity_gio_hang_custom.this, "Vui lòng chọn phương thức giao hàng", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                showKhachHangInputDialog();
//            }
//        });
//
//        btnstar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity_gio_hang_custom.this, Trangchu.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
//        String username = preferences.getString("username", "");
//        taiKhoan = taikhoanDAO.getID(username);
//        txthoten.setText("" + taiKhoan.getHoten());
//        txtsdt.setText("" + taiKhoan.getSdt());
//        txtdiachi.setText("" + taiKhoan.getDiachi());
//    }
//
//    public void showKhachHangInputDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Xác nhận đặt hàng");
//        builder.setMessage("Bạn có chắc chắn muốn đặt hàng này?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @SuppressLint("MissingPermission")
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
//                String username = preferences.getString("username", "");
//                TaiKhoan maKhachHang = taikhoanDAO.getID(username);
//                String phuongThuc = phuongThucVanChuyen;
//
//                HoaDon hoaDon = new HoaDon();
//                hoaDon.setMaTk(maKhachHang.getMaTk());
//                hoaDon.setPhuongthuc(phuongThuc);
//                hoaDon.setTongTien((double) adapter_giohang.getTotalPrice());
//                hoaDon.setTrangThai(0);
//                hoaDon.setNgay((java.sql.Date.valueOf(String.valueOf(date))));
//
//                long result = hoadonDAO.insert(hoaDon);
//
//                if (result > 0) {
//                    int maHoaDonMoiNhat = hoadonDAO.getMaHoaDonMoiNhat();
//
//                    for (GioHang gioHang : list) {
//                        int maHoaDon = maHoaDonMoiNhat;
//                        ChiTiet chiTietSanPham = new ChiTiet(0, maHoaDon, gioHang.getMadt(), gioHang.getSoLuong(), gioHang.getGia());
//                        long chiTietResult = chitietDAO.insert(chiTietSanPham);
//                        if (chiTietResult > 0) {
//                            startActivity(new Intent(MainActivity_gio_hang_custom.this, Trangchu.class));
//                            Toast.makeText(MainActivity_gio_hang_custom.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
//                            radioGroup.clearCheck();
//                            int soLuong = chiTietSanPham.getSoluong();
//                            dienthoaiDAO.updateSoLuong(gioHang.getMadt(), soLuong);
//                            showNotification();
//                        }
//                    }
//                    ghDAO.deleteAllGioHang();
//                    list.clear();
//                    adapter_giohang.notifyDataSetChanged();
//                    updateTotalValues();
//                } else {
//                    Toast.makeText(MainActivity_gio_hang_custom.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
//                }
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }
//
//    public void loaddata() {
//        ghDAO = new giohangDAO(this);
//        list = ghDAO.getAll();
//        adapter_giohang = new adapter_giohang(this, this, list);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        rcvgh.setLayoutManager(layoutManager);
//        rcvgh.setAdapter(adapter_giohang);
//        if (list.isEmpty()) {
//            imggio.setVisibility(View.VISIBLE);
//            btnstar.setVisibility(View.VISIBLE);
//        } else {
//            imggio.setVisibility(View.GONE);
//            btnstar.setVisibility(View.GONE);
//        }
//    }
//
//    public void updateTotalValues() {
//        int totalQuantity = 0;
//        double totalPrice = 0;
//
//        for (GioHang gioHang : list) {
//            totalQuantity += gioHang.getSoLuong();
//            totalPrice += gioHang.getSoLuong() * gioHang.getGia();
//        }
//        totalPrice += shipperPrice;
//        txtTongSoLuong.setText(String.valueOf(totalQuantity));
//        txtTongGia.setText(String.format("%,.0f VNĐ",totalPrice));
//    }
//
//    @SuppressLint("MissingPermission")
//    private void showNotification() {
//        RemoteViews notificationBigLayout = new RemoteViews(getPackageName(), R.layout.linner_notification);
//
//        notificationBigLayout.setImageViewResource(R.id.notification_icon, R.drawable.baseline_circle_notifications_24);
//        notificationBigLayout.setTextViewText(R.id.notification_title, "Đặt hàng thành công");
//        notificationBigLayout.setTextViewText(R.id.notification_message, "Đơn hàng của bạn đã được đặt thành công");
//        notificationBigLayout.setViewPadding(R.id.notification_icon, 0, 0, 0, 0);  // Padding phải của ImageView
//        notificationBigLayout.setViewPadding(R.id.notification_title, 0, 0, 0, 0);  // Padding trái và phải của TextView tiêu đề
//        notificationBigLayout.setViewPadding(R.id.notification_message, 0, 0, 0, 0);  // Padding trái và phải của TextView thông điệp
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity_gio_hang_custom.this, "CHANNEL_ID")
//                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
//                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                .setCustomBigContentView(notificationBigLayout)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity_gio_hang_custom.this);
//        notificationManager.notify(1, builder.build());
//    }
//
//    public void suattnd() {
//        suatt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater inflater = LayoutInflater.from(MainActivity_gio_hang_custom.this);
//                View dialogView = inflater.inflate(R.layout.dialog_suadc, null);
//
//                EditText etHoTen = dialogView.findViewById(R.id.ethitenu);
//                EditText etPhone = dialogView.findViewById(R.id.etPhoneu);
//                EditText etDiaChi = dialogView.findViewById(R.id.etDiaChiu);
//
//                // Đặt thông tin hiện tại lên dialog
//                etHoTen.setText(taiKhoan.getHoten());
//                etPhone.setText(taiKhoan.getSdt());
//                etDiaChi.setText(taiKhoan.getDiachi());
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_gio_hang_custom.this);
//                builder.setView(dialogView)
//                        .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String hoTen = etHoTen.getText().toString().trim();
//                                String phone = etPhone.getText().toString().trim();
//                                String diaChi = etDiaChi.getText().toString().trim();
//
//                                // Kiểm tra dữ liệu nhập vào
//                                if (hoTen.isEmpty() || phone.isEmpty() || diaChi.isEmpty()) {
//                                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                taiKhoan.setHoten(hoTen);
//                                taiKhoan.setSdt(phone);
//                                taiKhoan.setDiachi(diaChi);
//                                taikhoanDAO.update(taiKhoan);
//
//                                txthoten.setText(hoTen);
//                                txtsdt.setText(phone);
//                                txtdiachi.setText(diaChi);
//
//
//                                Toast.makeText(getApplicationContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//
//        });
//    }
//}