package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu.adapter_chitietls;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;

public class MainActivity_chi_tiet_ls extends AppCompatActivity {

    adapter_chitietls adapterChitietls;
    RecyclerView rclctls;
    ArrayList<ChiTiet> list = new ArrayList<>();
    hoadonDAO hoadonDAO;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chi_tiet_ls);
        rclctls = findViewById(R.id.rclctls);
        loaddata();
        Toolbar toolbar = findViewById(R.id.toolbarrr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public void loaddata() {
        Intent intent = getIntent();
        int tenDT = intent.getIntExtra("productId", 0);
        hoadonDAO = new hoadonDAO(this);
        list = (ArrayList<ChiTiet>) hoadonDAO.getChiTietByMaHoaDon(Integer.parseInt(String.valueOf(tenDT)));
        adapterChitietls = new adapter_chitietls(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rclctls.setLayoutManager(layoutManager);
        rclctls.setAdapter(adapterChitietls);
    }

}