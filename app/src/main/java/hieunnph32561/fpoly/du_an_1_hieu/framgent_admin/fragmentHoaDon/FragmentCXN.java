package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hieunnph32561.fpoly.du_an_1_hieu.R;

public class FragmentCXN extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cxn, container, false);

        return view;
    }
}