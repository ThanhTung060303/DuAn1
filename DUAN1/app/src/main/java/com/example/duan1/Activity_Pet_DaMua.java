package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class Activity_Pet_DaMua extends AppCompatActivity {
    ImageView imageView, ivBack;
    TextView txtmasp, txttensp,txttuoi,txtloai,txtgia,txtTrangThai,txtDonHang;
    PetDAO petDAO;
    LoaiPetDAO loaiPetDAO;
    DonHangDAO donHangDAO;
    int mapet,matk;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_da_mua);
        imageView = findViewById(R.id.ivSanPhamBan);
        ivBack = findViewById(R.id.ivBackPetBan);
        txtmasp = findViewById(R.id.txtMaSPban);
        txttensp = findViewById(R.id.txtTenSPban);
        txttuoi = findViewById(R.id.txtTuoiSPban);
        txtloai = findViewById(R.id.txtLoaiSPban);
        txtgia = findViewById(R.id.txtGiaSPban);
        txtTrangThai = findViewById(R.id.txtTrangThaiMua);
        txtDonHang = findViewById(R.id.txtXemDonhang);

       // String x = getIntent().getExtras().get("matk").toString();
        String y =getIntent().getExtras().get("mapet").toString();
        mapet = Integer.parseInt(y);
        //matk = Integer.parseInt(x);

        sharedPreferences = getSharedPreferences("THONGTINPET",MODE_PRIVATE);

        petDAO = new PetDAO(this);
        loaiPetDAO = new LoaiPetDAO(this);
        donHangDAO = new DonHangDAO(this);
        boolean check = petDAO.checkPet(mapet);
        if(check){
            String tensp = sharedPreferences.getString("tenpet","");
            int tuoi = sharedPreferences.getInt("tuoi",0);
            String loai = sharedPreferences.getString("maloai","");
            String tenloai=loaiPetDAO.getTenLoaiPet(loai);
            int gia = sharedPreferences.getInt("gia",0);
            String hinhanh = sharedPreferences.getString("hinhanh","");

            txtmasp.setText(mapet+"");
            txttensp.setText(tensp);
            txttuoi.setText(tuoi+"");
            txtgia.setText(gia+"");
            txtloai.setText(tenloai);
            Glide.with(this)
                    .load(hinhanh)
                    .into(imageView);
        }
        txtDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Pet_DaMua.this, Activity_XemDonHang.class);
                String tenpet = sharedPreferences.getString("tenpet","");
                i.putExtra("mapet",mapet);
                i.putExtra("tenpet",tenpet);
                startActivityForResult(i,999);
            }
        });

    }
}