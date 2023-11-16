package hieunnph32561.fpoly.du_an_1_hieu.framgent_admin.fragmentHoaDon;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import hieunnph32561.fpoly.du_an_1_hieu.R;
import hieunnph32561.fpoly.du_an_1_hieu.adapter.adapterQLHoaDon;
import hieunnph32561.fpoly.du_an_1_hieu.dao.chitietDAO;
import hieunnph32561.fpoly.du_an_1_hieu.dao.hoadonDAO;
import hieunnph32561.fpoly.du_an_1_hieu.model.ChiTiet;
import hieunnph32561.fpoly.du_an_1_hieu.model.HoaDon;

public class FragmentHuy extends Fragment {

    private  SearchView searchView;
    private ListView listView;
    private adapterQLHoaDon adapter;
    private List<ChiTiet> chiTietList;
    chitietDAO daoCT;
    hoadonDAO daoHD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_hoa_don, container, false);

        listView = view.findViewById(R.id.lvQuanLyHoaDon);
        daoCT = new chitietDAO(getContext());
        daoHD = new hoadonDAO(getContext());
        chiTietList = new ArrayList<>();
        List<ChiTiet> listsetAdapter = new ArrayList<>();
        chiTietList = daoCT.getAll();

        for (ChiTiet x: chiTietList) {
            HoaDon don = daoHD.getID(String.valueOf(x.getMahd()));
            if (don.getTrangThai()==4){
                listsetAdapter.add(x);
            }
        }

        adapter = new adapterQLHoaDon(getContext(), listsetAdapter);
        listView.setAdapter(adapter);

        return view;
    }

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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //handleSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}