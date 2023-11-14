package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.khachhnagAdapter;
import hieunnph32561.fpoly.du_an_1_hieu.dao.taikhoanDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.model.TaiKhoan;

public class FragmentKhachHang extends Fragment {
    private SearchView searchView;
    private List<TaiKhoan> list = new ArrayList<>();
    private ArrayList<HoaDon> list1;
    private khachhnagAdapter adapter;
    private RecyclerView rcc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);
         rcc = view.findViewById(R.id.rcckhachhang);
        taikhoanDAO taikhoandao = new taikhoanDAO(getContext());
        ArrayList<TaiKhoan> list = taikhoandao.getDSDL();
        list1 = taikhoandao.getDSHD();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcc.setLayoutManager(manager);
        khachhnagAdapter adapter = new khachhnagAdapter(getContext(),list,list1);
        rcc.setAdapter(adapter);
        return view;

    }
    private void handleSearch(String query) {
        List<TaiKhoan> listSearch = new ArrayList<>();
        for (TaiKhoan sach : list) {
            if (sach.getHoten().toLowerCase().contains(query.toLowerCase())) {
                listSearch.add(sach);
            }
        }
        adapter = new khachhnagAdapter(getActivity(), this, listSearch);
        rcc.setAdapter(adapter);

    }

}