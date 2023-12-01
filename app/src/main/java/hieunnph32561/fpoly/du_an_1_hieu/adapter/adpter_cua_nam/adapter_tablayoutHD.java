package hieunnph32561.fpoly.du_an_1_hieu.adapter.adpter_cua_nam;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentCXN;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentChoHuy;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentDXN;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentDangGiao;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentHuy;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentThanhCong;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls.framgment_dxn;

public class adapter_tablayoutHD extends FragmentStateAdapter {
    public adapter_tablayoutHD(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentCXN();
            case 1:
                return new FragmentDXN();
            case 2:
                return new FragmentDangGiao();
            case 3:
                return new FragmentThanhCong();
            case 4:
                return new FragmentChoHuy();
            case 5:
                return new FragmentHuy();
            default:
                return null;
        }
    }


    @Override
    public int getItemCount() {
        return 6;
    }

}
