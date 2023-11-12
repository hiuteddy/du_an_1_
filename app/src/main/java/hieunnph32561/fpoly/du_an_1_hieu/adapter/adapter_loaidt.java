package hieunnph32561.fpoly.du_an_1_hieu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class adapter_loaidt extends RecyclerView.Adapter<adapter_loaidt.ViewHolder> {
        private Context context;
        private ArrayList<LoaiSeries> listLoai;
        private OnItemClickListener listener;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public adapter_loaidt(Context context, ArrayList<LoaiSeries> listLoai) {
            this.context = context;
            this.listLoai = listLoai;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loaidt, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            LoaiSeries loaiSeries = listLoai.get(position);
            holder.categoryName.setText(loaiSeries.getTenLoaiSeri());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return listLoai.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView categoryPic;
            TextView categoryName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryPic = itemView.findViewById(R.id.categoryPic);
                categoryName = itemView.findViewById(R.id.categoryName);
            }
        }
    }

