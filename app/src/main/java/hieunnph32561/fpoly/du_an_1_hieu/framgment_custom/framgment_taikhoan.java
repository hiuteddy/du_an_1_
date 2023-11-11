package hieunnph32561.fpoly.du_an_1_hieu.framgment_custom;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;

public class framgment_taikhoan extends Fragment {

    private Context context;

    private TextView tvHoTenNguoiDung;
    private TextView tvPhoneNguoiDung;
    private TextView tvDiacChiNguoiDung, tvmk, tvuser;
    private Button btnDoiMatKhau;
    private ImageView btnChinhSuaThongTin;

    private taikhoanDAO dao;
    private hieunnph32561.du_an_1_hieu_lam.du_an_1_hieu_lam.model.TaiKhoan user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.framgments_taikhoan, container, false);

        // Khởi tạo đối tượng context
        context = getContext();

        // Khởi tạo SharedPreferences sau khi context đã được khởi tạo
        SharedPreferences preferences = context.getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        tvHoTenNguoiDung = v.findViewById(R.id.tvHoTenNguoiDung);
        tvPhoneNguoiDung = v.findViewById(R.id.tvPhoneNguoiDung);
        tvDiacChiNguoiDung = v.findViewById(R.id.tvDiacChiNguoiDung);
        tvmk = v.findViewById(R.id.tvmk);
        tvuser = v.findViewById(R.id.tvusernd);


        // btnDoiMatKhau = v.findViewById(R.id.btndoimk);
        btnChinhSuaThongTin = v.findViewById(R.id.imvcs);

        dao = new taikhoanDAO(context);
        user = dao.getID(username);

        // Đặt thông tin người dùng lên giao diện
        tvHoTenNguoiDung.setText(user.getHoten());
        tvPhoneNguoiDung.setText(user.getSdt());
        tvDiacChiNguoiDung.setText(user.getDiachi());
        tvmk.setText(user.getMatKhau());
        tvuser.setText(user.getTenDN());

        // Xử lý sự kiện khi nhấn nút Đổi mật khẩu
//        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Mở activity ThayDoiMatKhauActivity
//            }
//        });

        // Xử lý sự kiện khi nhấn nút Chỉnh sửa thông tin
        btnChinhSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });

        return v;
    }

    private void showUpdateDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_update, null);

        EditText etHoTen = dialogView.findViewById(R.id.ethoten);
        EditText etPhone = dialogView.findViewById(R.id.etPhone);
        EditText etDiaChi = dialogView.findViewById(R.id.etDiaChi);
        EditText edmk = dialogView.findViewById(R.id.etMk);


        // Đặt thông tin hiện tại lên dialog
        etHoTen.setText(user.getHoten());
        etPhone.setText(user.getSdt());
        etDiaChi.setText(user.getDiachi());
        edmk.setText(user.getMatKhau());


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hoTen = etHoTen.getText().toString().trim();
                        String phone = etPhone.getText().toString().trim();
                        String diaChi = etDiaChi.getText().toString().trim();
                        String mk = edmk.getText().toString().trim();


                        // Kiểm tra dữ liệu nhập vào
                        if (hoTen.isEmpty() || phone.isEmpty() || diaChi.isEmpty()) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Cập nhật thông tin tài khoản
                        //   user.getTenDN();
                        user.setHoten(hoTen);
                        user.setSdt(phone);
                        user.setDiachi(diaChi);
                        user.setMatKhau(mk);
                        dao.update(user);

                        // Cập nhật thông tin trên giao diện
                        tvHoTenNguoiDung.setText(hoTen);
                        tvPhoneNguoiDung.setText(phone);
                        tvDiacChiNguoiDung.setText(diaChi);
                        tvmk.setText(mk);


                        Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
