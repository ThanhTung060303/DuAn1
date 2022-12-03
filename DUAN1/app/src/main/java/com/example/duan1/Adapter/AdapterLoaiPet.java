package com.example.duan1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1.Fragment.Fragment_Admin_Cho;
import com.example.duan1.Fragment.Fragment_Admin_LoaiCho;
import com.example.duan1.Fragment.Fragment_Admin_LoaiMeo;
import com.example.duan1.Fragment.Fragment_Admin_Meo;

public class AdapterLoaiPet extends FragmentStateAdapter
{
    public AdapterLoaiPet(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new Fragment_Admin_LoaiCho();
        }
        else{
            return new Fragment_Admin_LoaiMeo();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
