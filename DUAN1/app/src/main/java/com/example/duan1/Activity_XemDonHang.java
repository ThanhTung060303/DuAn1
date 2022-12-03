package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1.DAO.DonHangDAO;
import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.DAO.PetDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity_XemDonHang extends AppCompatActivity {
    ImageView imageView, ivBack;
    TextView txtmadon, txttensp,txtngaymua,txtgia,txtTenTK;
    PetDAO petDAO;
    LoaiPetDAO loaiPetDAO;
    DonHangDAO donHangDAO;
    int mapet,matk;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        imageView = findViewById(R.id.ivBackXemDonHang);
        ivBack = findViewById(R.id.ivBackXemDonHang);
        txtmadon = findViewById(R.id.txtMaDonXemDonHang);
        txttensp = findViewById(R.id.txtTenSPXemDonHang);
        txtgia = findViewById(R.id.txtGiaSPXemDonHang);
        txtngaymua = findViewById(R.id.txtNgayMuaXemDonHang);
        txtTenTK = findViewById(R.id.txtTenTkXemDonHang);

        // String x = getIntent().getExtras().get("matk").toString();
        String tenpet =getIntent().getExtras().get("tenpet").toString();
        //matk = Integer.parseInt(x);

        sharedPreferences = getSharedPreferences("THONGTINDONHANG",MODE_PRIVATE);

        //petDAO = new PetDAO(this);
        //loaiPetDAO = new LoaiPetDAO(this);
        donHangDAO = new DonHangDAO(this);
        boolean check = donHangDAO.checkDonHang(tenpet);
        if(check) {
            int madon = sharedPreferences.getInt("madon", 0);
            String tensp = sharedPreferences.getString("tenpet", "");
            String hinhanh = sharedPreferences.getString("hinhanh", "");
            String tentk = sharedPreferences.getString("tentk", "");
            String ngaymua = sharedPreferences.getString("ngaymua", "");
            int gia = sharedPreferences.getInt("giatri", 0);

            txtmadon.setText(madon + "");
            txttensp.setText(tensp);
            txtTenTK.setText(tentk + "");
            txtgia.setText(gia + " VND");
            txtngaymua.setText(ngaymua);
            Glide.with(this)
                    .load(hinhanh)
                    .into(imageView);
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}