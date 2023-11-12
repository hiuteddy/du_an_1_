package hieunnph32561.fpoly.du_an_1_hieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentHoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentKhachHang;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentQuanLySp;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentSeries;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentTrangChu;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_gio_hang_custom;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ds_dt;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_lich_su_hoa_don;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_taikhoan;
import hieunnph32561.fpoly.du_an_1_hieu.model.GioHang;

public class Trangchu extends AppCompatActivity {
    DrawerLayout drawerLayout;

    View headerLayout;
    Toolbar toolbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Trang chủ");
        NavigationView navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        // Xóa đoạn mã này

        // toolbar.setTitle("Quản lý phiếu mượn"); // Thay "Tên Fragment mặc định" bằng tiêu đề bạn muốn hiển thị


        headerLayout = navigationView.getHeaderView(0);
        textView = headerLayout.findViewById(R.id.textViewEmail);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        textView.setText("" + user + "!");

        if (user != null && user.equalsIgnoreCase("user")) {
            navigationView.getMenu().findItem(R.id.sub_muahang).setVisible(true);
            navigationView.getMenu().findItem(R.id.sub_canhan).setVisible(true);
            Fragment defaultFragment = new framgment_ds_dt(); // Thay thế QlPhieuMuonFragment bằng Fragment mặc định bạn muốn hiển thị
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, defaultFragment)
                    .commit();
        }
       else {
            navigationView.getMenu().findItem(R.id.nav_quanly).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_thongke).setVisible(true);
            navigationView.getMenu().findItem(R.id.sub_canhan).setVisible(true);
            Fragment defaultFragment = new FragmentTrangChu(); // Thay thế QlPhieuMuonFragment bằng Fragment mặc định bạn muốn hiển thị
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, defaultFragment)
                    .commit();
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.sub_tt) {
                    fragment = new framgment_ds_dt();
                }
                if(item.getItemId() == R.id.sub_ls){
                    fragment = new framgment_lich_su_hoa_don();

                }
                if(item.getItemId() ==R.id.sub_taikhoan){
                    fragment=new framgment_taikhoan();
                }
                if(item.getItemId() ==R.id.sub_giohang){
                    startActivity(new Intent(Trangchu.this, MainActivity_gio_hang_custom.class));
                }

                if (item.getItemId() == R.id.sub_Logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Trangchu.this);
                    builder.setMessage("Do you want to exit ?")
                            .setTitle("Message")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Trangchu.this, Manhinhcho.class);
                                    startActivity(intent);

                                    finish();
                                }
                            });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                }
                if(item.getItemId() == R.id.nav_Home){
                    fragment = new FragmentTrangChu();
                    toolbar.setTitle("Trang Chủ");
                }
                if(item.getItemId() == R.id.nav_donhang){
                    fragment = new FragmentHoaDon();
                    toolbar.setTitle("Đơn hàng");
                }
                if(item.getItemId() == R.id.nav_khachhang){
                    fragment = new FragmentKhachHang();
                    toolbar.setTitle("Khách Hàng");
                }
                if(item.getItemId() == R.id.nav_dienthoai){
                    fragment = new FragmentQuanLySp();
                    toolbar.setTitle("Quản Lý Sản Phẩm");
                }
                if(item.getItemId() == R.id.nav_loaiseries){
                    fragment = new FragmentSeries();
                    toolbar.setTitle("Loại Series");
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout, fragment)
                            .commit();
                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }
    public void settitleToolbar(String mess){
        toolbar.setTitle(mess);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
