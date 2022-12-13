package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.duan1.Adapter.AdapterSanPham;
import com.example.duan1.Adapter.Adapter_Admin_DonHang;
import com.example.duan1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Fragment_Admin_DonHang extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_donhang, container, false);
        TabLayout tabLayout = view.findViewById(R.id.TabLayoutDonHang);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerDonHang);

        Adapter_Admin_DonHang adapter = new Adapter_Admin_DonHang(getActivity());
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0){
                    tab.setText("Chờ xác nhận");
                }
                else{
                    tab.setText("Đã bán");
                }
            }
        }).attach();
        return view;
    }
}
