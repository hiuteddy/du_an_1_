package hieunnph32561.fpoly.du_an_1_hieu.adapter.adpter_cua_nam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.model.LoaiSeries;

public class SpinnerTypeAdapter extends BaseAdapter {

    private final Context context;
    private List<LoaiSeries> loaiSeries;

    public SpinnerTypeAdapter(Context context, List<LoaiSeries> productTypeList) {
        this.context = context;
        this.loaiSeries = productTypeList;
    }

    @Override
    public int getCount() {
        return loaiSeries.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiSeries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_chon_loai, parent, false);
        }

        TextView txtTypeNameSpinner = convertView.findViewById(R.id.txtqlloaiseri);
        txtTypeNameSpinner.setText(loaiSeries.get(position).getTenLoaiSeri());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_chon_loai, parent, false);
        }
        TextView txtTypeNameDropdown = convertView.findViewById(R.id.txtqlloaiseri);

        txtTypeNameDropdown.setText(loaiSeries.get(position).getTenLoaiSeri());

        return convertView;
    }
}
