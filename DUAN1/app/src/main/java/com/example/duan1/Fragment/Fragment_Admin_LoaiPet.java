package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.duan1.Adapter.AdapterLoaiPet;
import com.example.duan1.Adapter.AdapterSanPham;
import com.example.duan1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Fragment_Admin_LoaiPet extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_loaipet, container, false);
        TabLayout tabLayout = view.findViewById(R.id.TabLayoutLoaiPet);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerLoaiPet);

        AdapterLoaiPet adapter = new AdapterLoaiPet(getActivity());
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0){
                    tab.setText("CHÓ");
                }
                else{
                    tab.setText("MÈO");
                }
            }
        }).attach();
        return view;
    }
}
