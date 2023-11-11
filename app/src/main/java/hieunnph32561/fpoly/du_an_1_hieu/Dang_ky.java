package hieunnph32561.fpoly.du_an_1_hieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;

public class Dang_ky extends AppCompatActivity {
    EditText edttendn, edtmk, edthoten, edtdiach, edtsdt;
    Button btndk;
    taikhoanDAO taikhoanDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);


            setContentView(R.layout.activity_dang_ky);
            edttendn = findViewById(R.id.editUsernamedk);
            edtmk = findViewById(R.id.editpassdk);
            edthoten = findViewById(R.id.edithotendk);
            edtdiach = findViewById(R.id.editdcdk);
            edtsdt = findViewById(R.id.editsdtdk);
            btndk = findViewById(R.id.btndangky);
            taikhoanDAO = new taikhoanDAO(this);

            btndk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hieunnph32561.du_an_1_hieu_lam.du_an_1_hieu_lam.model.TaiKhoan taiKhoan = new hieunnph32561.du_an_1_hieu_lam.du_an_1_hieu_lam.model.TaiKhoan();
                    taiKhoan.setTenDN(edttendn.getText().toString());
                    taiKhoan.setMatKhau(edtmk.getText().toString());
                    taiKhoan.setHoten(edthoten.getText().toString());
                    taiKhoan.setDiachi(edtdiach.getText().toString());
                    taiKhoan.setSdt(edtsdt.getText().toString());
                    if (edtdiach.getText().toString().isEmpty() || edtdiach.getText().toString().isEmpty() ||
                            edtdiach.getText().toString().isEmpty() || edtdiach.getText().toString().isEmpty() ||
                            edtdiach.getText().toString().isEmpty()) {
                        Toast.makeText(Dang_ky.this, "K để trống ô nhập", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (taikhoanDAO.insert(taiKhoan) > 0) {
                        Toast.makeText(Dang_ky.this, "Bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Dang_ky.this, Dangnhap.class));
                    } else {
                        Toast.makeText(Dang_ky.this, "Thông tin k hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
