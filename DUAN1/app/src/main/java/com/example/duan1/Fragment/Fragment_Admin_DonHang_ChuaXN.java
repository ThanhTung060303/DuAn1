package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.Adapter.Adapter_Admin_Cho;
import com.example.duan1.Adapter.Adapter_Admin_DonHang_ChuaXN;
import com.example.duan1.Adapter.Adapter_Admin_KhachHang;
import com.example.duan1.DAO.DonHangDAO;
import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;

import java.util.ArrayList;

public class Fragment_Admin_DonHang_ChuaXN extends Fragment {
    ListView listView;
    ArrayList<DonHang> list ;
    TaiKhoanDAO taiKhoanDAO;
    Adapter_Admin_DonHang_ChuaXN adapter;
    DonHangDAO donHangDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_donhang_chuaxn, container, false);
        listView = view.findViewById(R.id.listViewDonHang_ChuaXN);
        //taiKhoanDAO = new TaiKhoanDAO(getContext());
        donHangDAO = new DonHangDAO(getContext());
        loadDuLieu();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDuLieu();
    }

    private void loadDuLieu(){
        list = donHangDAO.getDSDonHang(0);
        adapter = new Adapter_Admin_DonHang_ChuaXN(getContext(),list,donHangDAO);
        try {
            listView.setAdapter(adapter);
        }catch (Exception e) {
            Toast.makeText(getContext(), "Load khong duoc", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), list.size()+"", Toast.LENGTH_SHORT).show();
        }
    }

}
