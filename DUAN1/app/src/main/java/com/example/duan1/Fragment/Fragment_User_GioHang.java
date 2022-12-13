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
import com.example.duan1.Adapter.Adapter_User_GioHang;
import com.example.duan1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Fragment_User_GioHang extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_giohang, container, false);
        TabLayout tabLayout = view.findViewById(R.id.TabLayoutGioHang);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerGioHang);

        Adapter_User_GioHang adapter = new Adapter_User_GioHang(getActivity());
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0){
                    tab.setText("Đang xác nhận");
                }
                else{
                    tab.setText("Đã mua");
                }
            }
        }).attach();
        return view;
    }
}
