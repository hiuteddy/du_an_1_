package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_danhgia;
import hieunnph32561.fpoly.du_an_1_hieu.dao.danhgiaDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.giohangDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DanhGia;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;


public class MainActivity_chi_tiet_dt extends AppCompatActivity {
    private int quantity = 1;
    private double totalPrice = 0.0;

    DienThoai dienThoai;
    dienthoaiDAO dao;
    RecyclerView rcldg;
    danhgiaDAO danhgiaDAO;
    adapter_danhgia adapter_dg;
    ArrayList<DanhGia> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chi_tiet_dt);

        rcldg = findViewById(R.id.rcldanhgia);
        dao = new dienthoaiDAO(getApplicationContext());
        danhgiaDAO = new danhgiaDAO(this);
        loaddata();

        TextView btngio = findViewById(R.id.addgiohang);
        TextView tvQuantity = findViewById(R.id.tvQuantity);
        ImageView btnPlus = findViewById(R.id.btnPlus);
        ImageView btnMinus = findViewById(R.id.btnMinus);


        Intent intent = getIntent();

        byte[] hinhanhDT = getIntent().getByteArrayExtra("bitmapImage");
        if (hinhanhDT != null && hinhanhDT.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanhDT, 0, hinhanhDT.length);
            ImageView anh = findViewById(R.id.imageView);
            anh.setImageBitmap(bitmap);
        } else {
            ImageView anh = findViewById(R.id.imageView);
            anh.setImageResource(R.drawable.baseline_phone_iphone_24);
        }
        int maDt = intent.getIntExtra("maDT", 0);
        String ten = intent.getStringExtra("tenDT");
        String maLoaiSeries = intent.getStringExtra("maLoaiSeries");
        Double giaTien = (double) intent.getDoubleExtra("giaTien", 0);
        String moTa = intent.getStringExtra("moTa");

        TextView tvName = findViewById(R.id.tvTendt);
        TextView tvloaidt = findViewById(R.id.tvLoaisr);
        TextView tvgia = findViewById(R.id.tvGiadt);
        TextView tvmota = findViewById(R.id.tvChitiet);
        TextView tvsoluong = findViewById(R.id.tvsoluong);
        dienThoai = dao.getID(String.valueOf(maDt));


        tvName.setText("Title: " + ten);
        tvloaidt.setText("Series: " + maLoaiSeries);
        tvgia.setText("$: " + giaTien + " đ");
        tvmota.setText("Chi tiết: " + moTa);
        tvsoluong.setText("" + dienThoai.getSoLuong());


        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < dienThoai.getSoLuong()) {
                    quantity++; // Tăng số lượng lên 1 đơn vị
                    totalPrice += giaTien; // Tăng tổng giá tiền theo giá của mỗi sản phẩm
                    tvQuantity.setText(String.valueOf(quantity));
                } else {
                    Toast.makeText(getApplicationContext(), "Số lượng sản phẩm vượt quá số lượng có sẵn trong kho", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--; // Giảm số lượng đi 1 đơn vị
                    totalPrice -= giaTien; // Giảm tổng giá tiền theo giá của mỗi sản phẩm
                    tvQuantity.setText(String.valueOf(quantity)); // Hiển thị số lượng mới
                }
            }
        });
        btngio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang gioHang = new GioHang(maDt, giaTien, quantity);
                giohangDAO dao = new giohangDAO(getApplicationContext());

                if (quantity > dienThoai.getSoLuong()) {
                    Toast.makeText(MainActivity_chi_tiet_dt.this, "Không the đặt hàng số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dao.checkExistence(maDt)) {
                    Toast.makeText(MainActivity_chi_tiet_dt.this, "Tên đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.insert(gioHang) > 0) {
                        startActivity(new Intent(MainActivity_chi_tiet_dt.this, MainActivity_gio_hang_custom.class));
                        Toast.makeText(MainActivity_chi_tiet_dt.this, "Thêm thành công vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity_chi_tiet_dt.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void loaddata() {
        Intent intent = getIntent();
        int maDt = intent.getIntExtra("maDT", 0);
        danhgiaDAO = new danhgiaDAO(getApplicationContext());
        list = danhgiaDAO.getBymatk(maDt);
        adapter_dg = new adapter_danhgia(getApplicationContext(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcldg.setLayoutManager(layoutManager);
        rcldg.setAdapter(adapter_dg);
    }

}
