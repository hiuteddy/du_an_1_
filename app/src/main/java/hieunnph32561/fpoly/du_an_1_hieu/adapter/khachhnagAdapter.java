package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.FragmentKhachHang;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class khachhnagAdapter extends RecyclerView.Adapter<khachhnagAdapter.khachhangViewHolder> implements Filterable {
    FragmentKhachHang fragment;
    private Context context;
    private ArrayList<TaiKhoan> list,listOld;
    private ArrayList<HoaDon> list1;
    private List<TaiKhoan> listSearch = new ArrayList<>();

    public khachhnagAdapter(Context context, ArrayList<TaiKhoan> list, ArrayList<HoaDon> list1) {
        this.context = context;
        this.list = list;
        this.list1 = list1;
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
        holder.txtmatk.setText("Ma TK: " + list.get(position).getMaTk());
        holder.txttenDN.setText("Ten dang nhap: " + list.get(position).getTenDN());
        holder.txthoten.setText("Ho ten: " + list.get(position).getHoten());
        holder.txtsdt.setText("SDT: " + list.get(position).getSdt());
        holder.txtdiachi.setText("Dia chi: " + list.get(position).getDiachi());
        holder.txtmahd.setText("Ma HD: " + list1.get(position).getMaHD());
        holder.txttongtien.setText("Tong tien: " + list1.get(position).getTongTien());
        holder.txtngaydat.setText("Ngay dat: " + list1.get(position).getNgay());
        holder.txttrangthai.setText("Trang thai: " + list1.get(position).getTrangThai());
        holder.txtphuongthuc.setText("Phuong thuc: " + list1.get(position).getPhuongthuc());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public class khachhangViewHolder extends RecyclerView.ViewHolder{
        //int maHD, int maTk, int tongTien, String ngay, int trangThai, String phuongthuc
          TextView txtmatk,txtdiachi,txtsdt,txttenDN, txthoten,txtmahd, txttongtien,txtngaydat,txttrangthai,
        txtphuongthuc;
        public khachhangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmatk = itemView.findViewById(R.id.txtmatk);
            txtdiachi = itemView.findViewById(R.id.txtdiachi);
            txtsdt = itemView.findViewById(R.id.txtsdt);
            txttenDN = itemView.findViewById(R.id.txttenDN);
            txthoten = itemView.findViewById(R.id.txthoten);
            txtmahd = itemView.findViewById(R.id.txtmahd);
            txttongtien = itemView.findViewById(R.id.txttongtien);
            txtngaydat = itemView.findViewById(R.id.txtngaydat);
            txttrangthai = itemView.findViewById(R.id.txttrangthai);
            txtphuongthuc = itemView.findViewById(R.id.txtphuongthuc);

        }
    }

    @Override
    public  Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                }else{
                    ArrayList<TaiKhoan> listSearch = new ArrayList<>();
                    for (TaiKhoan sach: listOld){
                        if (sach.getHoten().toLowerCase().contains(strSearch.toLowerCase())){
                            listSearch.add(sach);
                        }
                    }
                    list = listSearch;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<TaiKhoan>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
