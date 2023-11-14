package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hieunnph32561.fpoly.du_an_1_hieu.R;

public class FragmentKhachHang extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);
        return view;
    }
}