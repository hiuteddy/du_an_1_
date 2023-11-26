package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.model.DanhGia;

public class adapter_danhgia extends RecyclerView.Adapter<adapter_danhgia.ViewHolder> {
    private Context context;
    private ArrayList<DanhGia> listLoai;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public adapter_danhgia(Context context, ArrayList<DanhGia> listLoai) {
        this.context = context;
        this.listLoai = listLoai;
    }

    @NonNull
    @Override
    public adapter_danhgia.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhgia, parent, false);
        return new adapter_danhgia.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_danhgia.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DanhGia danhGia = listLoai.get(position);
        holder.tvten.setText("" + danhGia.getMaTk());
        holder.tvsp.setText("" + danhGia.getMaDt());
        holder.tvngay.setText("Ngày đặt: " + String.valueOf(sdf.format(danhGia.getThoigian())));
        holder.tvdiem.setRating(Float.parseFloat("" + danhGia.getDiem()));
        holder.tvnhanxet.setText(danhGia.getNhanxet());


    }

    @Override
    public int getItemCount() {
        return listLoai.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvten, tvsp, tvngay, tvnhanxet;
        RatingBar tvdiem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvten = itemView.findViewById(R.id.txtusert);
            tvsp = itemView.findViewById(R.id.textProductNamet);
            tvngay = itemView.findViewById(R.id.textDateTimet);
            tvdiem = itemView.findViewById(R.id.ratingBart);
            tvnhanxet = itemView.findViewById(R.id.textdgt);

        }
    }

}
