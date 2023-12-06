package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentCXN;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon.FragmentDXN;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentQuanLySP.FragmentKinhDoanh;
import hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentQuanLySP.FragmentNgungKinhDoanh;


public class FragmentQuanLySp extends Fragment {
    private SearchView searchView;

    private final String[] tabTitles = {"Kinh Doanh","Ngừng Kinh Doanh"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_sp, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tablayoutqlsp);
        ViewPager2 viewPager2 = view.findViewById(R.id.vp2qlsp);
        adapter_tablayout adapter_tablayoutSp = new adapter_tablayout(getActivity());
        viewPager2.setAdapter(adapter_tablayoutSp);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        });
        tabLayoutMediator.attach();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        return view;
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sapxep, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
class adapter_tablayout extends FragmentStateAdapter {
    public adapter_tablayout(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentKinhDoanh();
            case 1:
                return new FragmentNgungKinhDoanh();
            default:
                return null;
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }

}