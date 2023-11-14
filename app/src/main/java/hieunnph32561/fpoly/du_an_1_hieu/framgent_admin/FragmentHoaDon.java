package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import android.annotation.SuppressLint;
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
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentDXN;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentDangGiao;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentHuy;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentThanhCong;

 public class FragmentHoaDon extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationProduct);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.chuaxacnhan);

        return view;
    }

     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         Fragment fragment = null;
         int itemId = item.getItemId();

         if (itemId == R.id.chuaxacnhan) {
             fragment = new FragmentCXN();
         } else if (itemId == R.id.daxacnhan) {
             fragment = new FragmentDXN();
         } else if (itemId == R.id.danggiao) {
             fragment = new FragmentDangGiao();
         } else if (itemId == R.id.dagiao) {
             fragment = new FragmentThanhCong();
         } else if (itemId == R.id.dahuy) {
             fragment = new FragmentHuy();
         }

         if (fragment != null) {
             rePlaceFrag(fragment);
             return true;
         }

         return false;
     }

    private void rePlaceFrag(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.frameHoaDon, fragment).commit();
    }
}