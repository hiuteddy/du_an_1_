package hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.model.Top;

public class adapter_top10 extends RecyclerView.Adapter<adapter_top10.hodeltop10> {

    private Context context;
    private ArrayList<Top> list;

    public adapter_top10(Context context, ArrayList<Top> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public adapter_top10.hodeltop10 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top10, parent, false);
        return new hodeltop10(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_top10.hodeltop10 holder, int position) {
        Top top = list.get(position);
        holder.ten.setText(top.tenDt);
        holder.sl.setText("" + top.soLuong);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class hodeltop10 extends RecyclerView.ViewHolder {

        public TextView ten, sl;

        public hodeltop10(@NonNull View itemView) {
            super(itemView);

            ten = itemView.findViewById(R.id.tvtdt);
            sl = itemView.findViewById(R.id.tvsl);
        }
    }

}
