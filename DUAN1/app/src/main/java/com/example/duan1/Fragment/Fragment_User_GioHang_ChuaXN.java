package com.example.duan1.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.duan1.Adapter.Adapter_User_GioHang;
import com.example.duan1.Adapter.Adapter_User_GioHang_ChuaXN;
import com.example.duan1.DAO.DonHangDAO;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;

import java.util.ArrayList;

public class Fragment_User_GioHang_ChuaXN extends Fragment {
    ListView listView;
    ArrayList<DonHang> list ;
    TaiKhoanDAO taiKhoanDAO;
    Adapter_User_GioHang_ChuaXN adapter;
    DonHangDAO donHangDAO;
    PetDAO petDAO;
    SharedPreferences sharedPreferences;
    int manguoimua;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_giohang_chuaxn, container, false);
        listView = view.findViewById(R.id.listViewGioHang_ChuaXN);

        sharedPreferences = getActivity().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        manguoimua  = sharedPreferences.getInt("matk",0);
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        donHangDAO = new DonHangDAO(getContext());
        petDAO = new PetDAO(getContext());
        loadDuLieu();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDuLieu();
    }

    private void loadDuLieu(){
        list = donHangDAO.getDSGioHang(manguoimua,0);
        adapter = new Adapter_User_GioHang_ChuaXN(getContext(),list,donHangDAO,petDAO);
        try {
            listView.setAdapter(adapter);
        }catch (Exception e) {
            Toast.makeText(getContext(), "Load khong duoc", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), list.size()+"", Toast.LENGTH_SHORT).show();
        }
    }

}
