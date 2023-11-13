package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentCXN;

public class FragmentHoaDon extends Fragment {
    BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Cho phép Fragment có các menu riêng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationProduct);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        // Đoạn code sau đây chưa hoàn thiện, cần cung cấp đối tượng Fragment mặc định để thay thế cho defaultFragment
        Fragment defaultFragment = new FragmentCXN();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frameHoaDon, defaultFragment)
                .commit();

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.chuaxacnhan) {
                    FragmentCXN chuxacnhan = new FragmentCXN();
                    chuxacnhan.setArguments(bundle);
                    rePlaceFrag(chuxacnhan);
                }
            }
        });
        return view;
    }
    private void rePlaceFrag(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.frameHoaDon, fragment).commit();
    }
}