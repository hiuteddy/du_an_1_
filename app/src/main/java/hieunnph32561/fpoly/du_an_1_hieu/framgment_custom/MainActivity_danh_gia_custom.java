package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.danhgiaDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DanhGia;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class MainActivity_danh_gia_custom extends AppCompatActivity {

    private ImageView imageProduct;
    private TextView textProductName;
    private RatingBar ratingBar;
    private EditText editComment;
    private Button buttonSubmit;
    danhgiaDAO danhgiaDAO;
    taikhoanDAO taikhoanDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_danh_gia_custom);

        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        danhgiaDAO = new danhgiaDAO(this);
        taikhoanDAO = new taikhoanDAO(this);
        // Tham chiếu các phần tử trong bảng đánh giá
        imageProduct = findViewById(R.id.imageProduct);
        textProductName = findViewById(R.id.textProductName);
        ratingBar = findViewById(R.id.ratingBar);
        editComment = findViewById(R.id.editComment);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Thiết lập sự kiện khi nút "Gửi đánh giá" được nhấn
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int tenDT = intent.getIntExtra("productId", 0);
                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                String username = preferences.getString("username", "");
                TaiKhoan maKhachHang = taikhoanDAO.getID(username);
                float rating = ratingBar.getRating();
                String comment = editComment.getText().toString();

                DanhGia danhGia = new DanhGia();
                danhGia.setMaDt(tenDT);
                danhGia.setMaTk(maKhachHang.getMaTk());
                danhGia.setDiem((int) rating);
                danhGia.setNhanxet(comment);
                danhGia.setThoigian(new Date());

                long insertedId = danhgiaDAO.insert(danhGia);

                if (insertedId != -1) {
                    Toast.makeText(MainActivity_danh_gia_custom.this, "Thêm đánh giá thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity_danh_gia_custom.this, "Thêm đánh giá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}