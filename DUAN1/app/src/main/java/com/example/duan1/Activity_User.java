package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Fragment.Fragment_User_GioHang;
import com.example.duan1.Fragment.Fragment_User_TrangChu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Activity_User extends AppCompatActivity {
    Fragment fragment;
    ImageView ivUser;
    TextView txtShowTen;
    TaiKhoanDAO taiKhoanDAO;
    String ten,mk;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        RelativeLayout relative = findViewById(R.id.relative_User);
        BottomNavigationView bottom = findViewById(R.id.navi_User);
        FrameLayout frameLayout = findViewById(R.id.frameLayout_User);
        ivUser = findViewById(R.id.ivUser);
        txtShowTen = findViewById(R.id.txtTenUserChao);
        sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String tentk = sharedPreferences.getString("tentk","");
        Toast.makeText(this, "Teen:  " +tentk, Toast.LENGTH_SHORT).show();
        //ten = getIntent().getExtras().get("ten").toString();
        txtShowTen.setText(tentk);
        fragment = new Fragment_User_TrangChu();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout_User,fragment)
                .commit();

        bottom.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.mTrangChu:
                        item.setChecked(true);
                        fragment = new Fragment_User_TrangChu();
                        break;
                    case R.id.mGioHang:
                        item.setChecked(true);
                        fragment = new Fragment_User_GioHang();
                        break;
                    default:
                        item.setChecked(true);
                        fragment = new Fragment_User_TrangChu();
                        break;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout_User,fragment)
                        .commit();
                return false;
            }
        });
        taiKhoanDAO = new TaiKhoanDAO(Activity_User.this);
        //taiKhoanDAO.checkDangNhap(ten,mk);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentk = sharedPreferences.getString("tentk","");
                String matkhau = sharedPreferences.getString("matkhau", "");

                Intent i = new Intent(Activity_User.this, activityTaiKhoan.class);
                //Intent intent = i.putExtra("tentk", tentk);
                //Intent intent1 = i.putExtra("matkhau", matkhau);
                //startActivityForResult(i,666);
                startActivity(i);
            }
        });
    }
}