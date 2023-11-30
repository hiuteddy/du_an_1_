package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.Trangchu;

public class FragmentTrangChu extends Fragment {

    LinearLayout btnsp, btndt, btnhd, btnkh, btnbc, btnsr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        btnbc = view.findViewById(R.id.btnbc);
        btnsp = view.findViewById(R.id.btnsp);
        btnsr = view.findViewById(R.id.btnsr);
        btnhd = view.findViewById(R.id.btnhd);
        btnkh = view.findViewById(R.id.btnkh);
        btndt = view.findViewById(R.id.btndt);

        btnbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentBanChay();
                loadOtherFragment(fragment);
                setToolbarTitle("Bán Chạy");
            }
        });
        btnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentQuanLySp();
                loadOtherFragment(fragment);
                setToolbarTitle("Quản Lý Sản Phẩm");
            }
        });
        btnsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentSeries();
                loadOtherFragment(fragment);
                setToolbarTitle("Quản Lý Series");
            }
        });
        btnhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentHoaDon();
                loadOtherFragment(fragment);
                setToolbarTitle("Quản Lý Hóa Đơn");
            }
        });
        btnkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new FragmentKhachHang();
                loadOtherFragment(fragment);
                setToolbarTitle("Quản Lý Khách Hàng");
            }
        });
        btndt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentDoanhThu();
                loadOtherFragment(fragment);
                setToolbarTitle("Doanh Thu"); // Đặt tiêu đề thanh công cụ
            }
        });

        return view;
    }

    private void loadOtherFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setToolbarTitle(String title) {
        if (getActivity() instanceof Trangchu) {
            Trangchu activity = (Trangchu) getActivity();
            activity.setTitle(title);
        }
    }
}