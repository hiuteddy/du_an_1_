package hieunnph32561.fpoly.du_an_1_hieu.adapter.adapter_cua_hieu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls.framgment_cxn;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls.framgments_chohuy;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls.framgments_dg;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls.framgment_dxn;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls.framgments_huy;
import hieunnph32561.fpoly.du_an_1_hieu.framgment_custom.framgment_ls.framgments_tc;

public class adapter_tablayout_ls extends FragmentStateAdapter {
        public adapter_tablayout_ls(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new framgment_cxn();
            } else if (position == 1) {
                return new framgment_dxn();
            } else if (position == 2) {
                return new framgments_dg();
            } else if (position == 3) {
                return new framgments_tc();
            } else if (position == 4) {
                return new framgments_chohuy();
            } else
                return new framgments_huy();
            }


        @Override
        public int getItemCount() {
            return 6;
        }

    }



