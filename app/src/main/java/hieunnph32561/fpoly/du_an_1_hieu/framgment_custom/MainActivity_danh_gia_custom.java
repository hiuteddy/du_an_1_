package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.Trangchu;
import hieunnph32561.fpoly.du_an_1_hieu.dao.danhgiaDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.dienthoaiDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.DanhGia;
import hieunnph32561.fpoly.du_an_1_hieu.model.DienThoai;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class MainActivity_danh_gia_custom extends AppCompatActivity {

    private ImageView imageProduct;
    private TextView textProductName;
    private RatingBar ratingBar;
    private EditText editComment;
    private Button buttonSubmit;
    danhgiaDAO danhgiaDAO;
    taikhoanDAO taikhoanDAO;
    dienthoaiDAO dienthoaiDAO;
    long millis = System.currentTimeMillis();
    java.sql.Date date = new java.sql.Date(millis);

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
        dienthoaiDAO = new dienthoaiDAO(this);
        // Tham chiếu các phần tử trong bảng đánh giá
        imageProduct = findViewById(R.id.imageProduct);
        textProductName = findViewById(R.id.textProductName);
        ratingBar = findViewById(R.id.ratingBar);
        editComment = findViewById(R.id.editComment);
        buttonSubmit = findViewById(R.id.buttonSubmit);


        Intent intent = getIntent();
        int tenDT = intent.getIntExtra("productId", 0);

        DienThoai dienThoai=dienthoaiDAO.getID(String.valueOf(tenDT));
        textProductName.setText(dienThoai.getTenDT());

        byte[] anhData = dienthoaiDAO.getAnhByMaDT(dienThoai.getMaDT());
        if (anhData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhData, 0, anhData.length);
            imageProduct.setImageBitmap(bitmap);
        } else {
            imageProduct.setImageResource(R.drawable.iphone15);
        }



        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                String username = preferences.getString("username", "");

                TaiKhoan maKhachHang = taikhoanDAO.getID(username);
                float rating = ratingBar.getRating();
                String comment = editComment.getText().toString();

                Intent intent = getIntent();
                int tenDT = intent.getIntExtra("productId", 0);

                DanhGia danhGia = new DanhGia();
                danhGia.setMaDt(tenDT);
                danhGia.setMaTk(maKhachHang.getMaTk());
                danhGia.setDiem((int) rating);
                danhGia.setNhanxet(comment);
                danhGia.setThoigian((java.sql.Date.valueOf(String.valueOf(date))));

                long insertedId = danhgiaDAO.insert(danhGia);

                if (insertedId != -1) {
                    Toast.makeText(MainActivity_danh_gia_custom.this, "Thêm đánh giá thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity_danh_gia_custom.this, Trangchu.class));

                } else {
                    Toast.makeText(MainActivity_danh_gia_custom.this, "Thêm đánh giá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}