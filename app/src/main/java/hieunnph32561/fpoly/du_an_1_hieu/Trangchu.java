package hieunnph32561.fpoly.du_an_1_hieu;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentBanChay;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentDoanhThu;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentHoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentKhachHang;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentQuanLySp;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentSeries;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentTrangChu;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.MainActivity_gio_hang_custom;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ds_dt;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_lich_su_hoa_don;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_taikhoan;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.hotrokhachhang;

public class Trangchu extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        drawerLayout = findViewById(R.id.drawerlayout);
        NavigationView navigationView = findViewById(R.id.navigationview);

        // Hiển thị tên người dùng trong header
        View headerLayout = navigationView.getHeaderView(0);
        textView = headerLayout.findViewById(R.id.textViewEmail);
        SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String user = preferences.getString("username", "");
        textView.setText("" + user + "!");

        // Xác định vai trò người dùng và hiển thị fragment tương ứng
        Fragment defaultFragment;
        String title;
        Menu menu = navigationView.getMenu();
        if (user != null && user.equalsIgnoreCase("admin")) {
            menu.findItem(R.id.nav_quanly).setVisible(true);
            menu.findItem(R.id.nav_thongke).setVisible(true);
            menu.findItem(R.id.sub_canhan).setVisible(true);
            defaultFragment = new FragmentTrangChu();
            title = "Trang Chủ";
        } else {
            menu.findItem(R.id.sub_muahang).setVisible(true);
            menu.findItem(R.id.sub_canhan).setVisible(true);
            defaultFragment = new framgment_ds_dt();
            title = "Danh sách điện thoại";
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, defaultFragment)
                .commit();
        toolbar.setTitle(title);

        // Xử lý sự kiện khi chọn mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            String fragmentTitle = "";

            int itemId = item.getItemId();
            if (itemId == R.id.sub_tt) {
                fragment = new framgment_ds_dt();
                fragmentTitle = "Danh sách điện thoại";
            } else if (itemId == R.id.sub_ls) {
                fragment = new framgment_lich_su_hoa_don();
                fragmentTitle = "Lịch sử hóa đơn";
            } else if (itemId == R.id.sub_taikhoan) {
                fragment = new framgment_taikhoan();
                fragmentTitle = "Tài khoản";
            } else if (itemId == R.id.sub_giohang) {
                startActivity(new Intent(Trangchu.this, MainActivity_gio_hang_custom.class));
            } else if (itemId == R.id.sub_Logout) {
                showExitDialog();
            } else if (itemId == R.id.nav_Home) {
                fragment = new FragmentTrangChu();
                fragmentTitle = "Trang Chủ";
            } else if (itemId == R.id.nav_donhang) {
                fragment = new FragmentHoaDon();
                fragmentTitle = "Đơn hàng";
            } else if (itemId == R.id.nav_khachhang) {
                fragment = new FragmentKhachHang();
                fragmentTitle = "Khách Hàng";
            } else if (itemId == R.id.nav_dienthoai) {
                fragment = new FragmentQuanLySp();
                fragmentTitle = "Quản Lý Sản Phẩm";
            } else if (itemId == R.id.nav_loaiseries) {
                fragment = new FragmentSeries();
                fragmentTitle = "Loại Series";
            } else if (itemId == R.id.sub_Top) {
                fragment = new FragmentBanChay();
                fragmentTitle = "Top 10";
            } else if (itemId == R.id.sub_DoanhThu) {
                fragment = new FragmentDoanhThu();
                fragmentTitle = "Doanh thu";
            } else if (itemId == R.id.nav_lienhe) {
                startActivity(new Intent(Trangchu.this, hotrokhachhang.class));
                fragmentTitle = "Hỗ trợ";
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, fragment)
                        .commit();
                toolbar.setTitle(fragmentTitle);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc muống đăng xuất?");
        builder.setPositiveButton("Đăng xuất", (dialog, which) -> {
            SharedPreferences.Editor editor = getSharedPreferences("USER_DATA", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(Trangchu.this, Manhinhcho.class));
            finish();
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}