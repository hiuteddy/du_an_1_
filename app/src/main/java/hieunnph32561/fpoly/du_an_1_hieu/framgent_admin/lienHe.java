package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.hotrokhachhang;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class lienHe extends AppCompatActivity {
    TextView txtlienHe;
    ImageView img;
    private Context context;
    private taikhoanDAO dao;
    private TaiKhoan user;
    public lienHe(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        txtlienHe = findViewById(R.id.txtlienHe);
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
        dao  = new taikhoanDAO(this);
        String sdt = dao.getSDT();
        // Đặt thông tin người dùng lên giao diện
        txtlienHe.setText(sdt);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key",txtlienHe.getText().toString());
        editor.apply();
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                showdialog(id);
//            }
//        });
    }
//    private void  showdialog(int id){
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View dialogView = inflater.inflate(R.layout.item_lienhe, null);
//        EditText edtlienhe = dialogView.findViewById(R.id.edtlienhe);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//       edtlienhe.setText(user.getSdt());
//        builder.setView(dialogView)
//                .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String phone = edtlienhe.getText().toString();
//                        // Kiểm tra dữ liệu nhập vào
//                        if ( phone.isEmpty() ) {
//                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        user.setSdt(phone);
//                        dao.updateSdt(phone,id);
//                        // Cập nhật thông tin trên giao diện
//                        txtlienHe.setText(phone);
//                        Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }
}