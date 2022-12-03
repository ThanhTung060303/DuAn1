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
import com.example.duan1.Adapter.Adapter_Admin_KhachHang;
import com.example.duan1.DAO.DonHangDAO;
import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;

import java.util.ArrayList;

public class Fragment_Admin_KhachHang extends Fragment {
    ListView listView;
    ArrayList<TaiKhoan> list ;
    TaiKhoanDAO taiKhoanDAO;
    Adapter_Admin_KhachHang adapter;
    DonHangDAO donHangDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_khachhang, container, false);
        listView = view.findViewById(R.id.listViewKhachHang);
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        //donHangDAO = new DonHangDAO(getContext());
        loadDuLieu();
        return view;
    }

    private void loadDuLieu(){
        list = taiKhoanDAO.getDSKhachHang();
        adapter = new Adapter_Admin_KhachHang(getContext(),list,taiKhoanDAO);
        try {
            listView.setAdapter(adapter);
        }catch (Exception e) {
            Toast.makeText(getContext(), "Load khong duoc", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), list.size()+"", Toast.LENGTH_SHORT).show();
        }
    }

}
