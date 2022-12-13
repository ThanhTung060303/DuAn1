package com.example.duan1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1.Fragment.Fragment_Admin_Cho;
import com.example.duan1.Fragment.Fragment_Admin_LoaiCho;
import com.example.duan1.Fragment.Fragment_Admin_LoaiMeo;
import com.example.duan1.Fragment.Fragment_Admin_Meo;
import com.example.duan1.Fragment.Fragment_User_GioHang_ChuaXN;
import com.example.duan1.Fragment.Fragment_User_GioHang_DaMua;

public class Adapter_User_GioHang extends FragmentStateAdapter
{
    public Adapter_User_GioHang(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new Fragment_User_GioHang_ChuaXN();
        }
        else{
            return new Fragment_User_GioHang_DaMua();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
