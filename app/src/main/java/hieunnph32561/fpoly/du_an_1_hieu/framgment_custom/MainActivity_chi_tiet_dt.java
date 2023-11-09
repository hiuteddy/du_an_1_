package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.giohangDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;


public class MainActivity_chi_tiet_dt extends AppCompatActivity {
    private int quantity = 1;
    private double totalPrice = 0.0;

    giohangDAO dao;
    GioHang gioHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chi_tiet_dt);


        TextView btngio = findViewById(R.id.addgiohang);
        TextView tvQuantity = findViewById(R.id.tvQuantity);
        ImageView btnPlus = findViewById(R.id.btnPlus);
        ImageView btnMinus = findViewById(R.id.btnMinus);


        Intent intent = getIntent();
        int maDt = intent.getIntExtra("maDT",0);
        String ten = intent.getStringExtra("tenDT");
        String maLoaiSeries = intent.getStringExtra("maLoaiSeries");
        Double giaTien = (double) intent.getDoubleExtra("giaTien", 0);
        String moTa = intent.getStringExtra("moTa");

// Hiển thị dữ liệu lên các TextView tương ứng
        TextView tvName = findViewById(R.id.tvTendt);
        TextView tvloaidt = findViewById(R.id.tvLoaisr);
        TextView tvgia = findViewById(R.id.tvGiadt);
        TextView tvmota = findViewById(R.id.tvChitiet);


        tvName.setText("Title: " + ten);
        tvloaidt.setText("Series: " + maLoaiSeries);
        tvgia.setText("$: " + giaTien + " đ");
        tvmota.setText("Chi tiết: " + moTa);


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


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity++; // Tăng số lượng lên 1 đơn vị
                totalPrice += giaTien; // Tăng tổng giá tiền theo giá của mỗi sản phẩm
                tvQuantity.setText(String.valueOf(quantity)); // Hiển thị số lượng mới
                // tvTotalPrice.setText("Tổng $: " + totalPrice); // Hiển thị tổng giá tiền mới
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--; // Giảm số lượng đi 1 đơn vị
                    totalPrice -= giaTien; // Giảm tổng giá tiền theo giá của mỗi sản phẩm
                    tvQuantity.setText(String.valueOf(quantity)); // Hiển thị số lượng mới
                    // tvTotalPrice.setText("Tổng $: " + totalPrice); // Hiển thị tổng giá tiền mới
                }
            }
        });
        btngio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  int maGh = gioHang.getMaGh();
                GioHang gioHang = new GioHang(maDt, giaTien, quantity);
                giohangDAO dao = new giohangDAO(getApplicationContext());

                if (dao.checkExistence(maDt)) {
                    Toast.makeText(MainActivity_chi_tiet_dt.this, "Tên đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.insert(gioHang) > 0) {
                        Toast.makeText(MainActivity_chi_tiet_dt.this, "Thêm thành công vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity_chi_tiet_dt.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


}
