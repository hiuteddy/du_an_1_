package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentKhachHang;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class khachhnagAdapter extends RecyclerView.Adapter<khachhnagAdapter.khachhangViewHolder>  {
    FragmentKhachHang fragment;
    private Context context;
    private ArrayList<TaiKhoan> list,listOld;
    taikhoanDAO taikhoanDAO;
    private List<TaiKhoan> listSearch = new ArrayList<>();
    RecyclerView rcc;


    public khachhnagAdapter(Context context, ArrayList<TaiKhoan> list) {
        this.context = context;
        this.list = list;
        taikhoanDAO = new taikhoanDAO(context);
    }

    public khachhnagAdapter(@NonNull Context context, FragmentKhachHang fragmentKhachHang, List<TaiKhoan> listSearch) {
        this.context = context;
        this.fragment = fragmentKhachHang;
        this.listSearch = listSearch;
    }


    @NonNull
    @Override
    public khachhangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khachhang,parent,false);
        return new khachhangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull khachhangViewHolder holder, int position) {
        //maTk, String tenDN, String matKhau, String hoten, String sdt, String diachi
        holder.txtmatk.setText("Mã TK: " + list.get(position).getMaTk());
        holder.txttenDN.setText("Tên đăng nhập: " + list.get(position).getTenDN());
        holder.txtmk.setText("Mật khẩu: " + list.get(position).getMatKhau());
        holder.txthoten.setText("Họ và tên: " + list.get(position).getHoten());
        holder.txtsdt.setText("SDT: " + list.get(position).getSdt());
        holder.txtdiachi.setText("Địa chỉ: " + list.get(position).getDiachi());
        TaiKhoan tk = list.get(position);
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate(tk);

            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(taikhoanDAO.xoaKhachHang(tk.getMaTk())){
                            list.clear();
                            list.addAll(taikhoanDAO.getDSDL());
                            notifyDataSetChanged();

                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    public void dialogUpdate(TaiKhoan tk){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view1 = inflater.inflate(R.layout.item_addkhachhang,null );

        builder.setView(view1);
        EditText edittenDN = view1.findViewById(R.id.edittenDN);
        EditText edtmk = view1.findViewById(R.id.editmk);
        EditText edthoten = view1.findViewById(R.id.editHoten);
        EditText edtdiachi = view1.findViewById(R.id.editdiachi);
        EditText edtsdt= view1.findViewById(R.id.editsdt);

        edittenDN.setText(tk.getTenDN());
        edtmk.setText(tk.getMatKhau());
        edthoten.setText(tk.getHoten());
        edtsdt.setText(tk.getSdt());
        edtdiachi.setText(tk.getDiachi());
        builder.setNegativeButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tk.setTenDN(edittenDN.getText().toString());
                tk.setMatKhau(edtmk.getText().toString());
                tk.setHoten(edthoten.getText().toString());
                tk.setSdt(edtsdt.getText().toString());
                tk.setDiachi(edtdiachi.getText().toString());


                TaiKhoan taiKhoan = new TaiKhoan();
                if (TextUtils.isEmpty(edittenDN.getText().toString()) || TextUtils.isEmpty(edthoten.getText().toString()) || TextUtils.isEmpty(edtsdt.getText().toString()) || TextUtils.isEmpty( edtdiachi.getText().toString()) || TextUtils.isEmpty(edtmk.getText().toString())){
                    Toast.makeText(context, "Khong duoc de trong thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (taikhoanDAO.updateKhachHang(tk)){
                        list.clear();
                        list.addAll(taikhoanDAO.getDSDL());
                        notifyDataSetChanged();

                        Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "cap nhat that bai", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        builder.setPositiveButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog  = builder.create();
        dialog.show();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public class khachhangViewHolder extends RecyclerView.ViewHolder{
        //int maHD, int maTk, int tongTien, String ngay, int trangThai, String phuongthuc
          TextView txtmatk,txtdiachi,txtsdt,txttenDN, txthoten,txtmk;
          ImageView btnUpdate, btndelete;

        public khachhangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmatk = itemView.findViewById(R.id.txtmatk);
            txttenDN = itemView.findViewById(R.id.txttenDN);
            txtmk = itemView.findViewById(R.id.txtmk);
            txthoten = itemView.findViewById(R.id.txtHoten);
            txtsdt = itemView.findViewById(R.id.txtSdt);
            txtdiachi = itemView.findViewById(R.id.txtdiachi);
            btndelete = itemView.findViewById(R.id.btndelete);
            btnUpdate = itemView.findViewById(R.id.btnedit);


        }
    }

}
