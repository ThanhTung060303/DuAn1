package com.example.duan1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1.Fragment.Fragment_Admin_Cho;
import com.example.duan1.Fragment.Fragment_Admin_LoaiCho;
import com.example.duan1.Fragment.Fragment_Admin_LoaiMeo;
import com.example.duan1.Fragment.Fragment_Admin_Meo;

public class AdapterSanPham extends FragmentStateAdapter
{
    public AdapterSanPham(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new Fragment_Admin_Cho();
        }
        else{
            return new Fragment_Admin_Meo();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
