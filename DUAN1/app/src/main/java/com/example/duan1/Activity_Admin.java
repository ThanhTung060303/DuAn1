package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.duan1.Fragment.Fragment_Admin_DonHang;
import com.example.duan1.Fragment.Fragment_Admin_KhachHang;
import com.example.duan1.Fragment.Fragment_Admin_LoaiPet;
import com.example.duan1.Fragment.Fragment_Admin_SanPham;
import com.example.duan1.Fragment.Fragment_Admin_ThongKe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationBarView;

public class Activity_Admin extends AppCompatActivity {
    Fragment fragment;
    ImageView ivUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        RelativeLayout relative = findViewById(R.id.relative);
        BottomNavigationView bottom = findViewById(R.id.navi_Admin);
        FrameLayout frameLayout = findViewById(R.id.frameLayout_Admin);
        ivUser = findViewById(R.id.ivUser);

        fragment = new Fragment_Admin_SanPham();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout_Admin,fragment)
                .commit();

        bottom.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.mSanPham:
                        item.setChecked(true);
                        fragment = new Fragment_Admin_SanPham();
                        break;
                    case R.id.mKhachHang:
                        item.setChecked(true);
                        fragment = new Fragment_Admin_KhachHang();
                        break;
                    case R.id.mDonHang:
                        item.setChecked(true);
                        fragment = new Fragment_Admin_DonHang();
                        break;
                    case R.id.mLoaiPet:
                        item.setChecked(true);
                        fragment = new Fragment_Admin_LoaiPet();
                        break;
                    default:
                        item.setChecked(true);
                        fragment = new Fragment_Admin_SanPham();
                        break;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout_Admin,fragment)
                        .commit();
                return false;
            }
        });

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Admin.this, activityTaiKhoan.class);
                startActivity(i);
            }
        });
    }
}