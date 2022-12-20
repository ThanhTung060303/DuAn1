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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity_XemDonHang extends AppCompatActivity {
    Button btn_chotdon,btn_huydon;
    ImageView imageView, ivBack;
    TextView txtmadon, txttensp,txtngaymua,txtgia,txtTenTK;
    PetDAO petDAO;
    LoaiPetDAO loaiPetDAO;
    DonHangDAO donHangDAO;
    int mapet=0,matk,madon;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        imageView = findViewById(R.id.ivSanPhamDonHang);
        ivBack = findViewById(R.id.ivBackXemDonHang);
        txtmadon = findViewById(R.id.txtMaDonXemDonHang);
        txttensp = findViewById(R.id.txtTenSPXemDonHang);
        txtgia = findViewById(R.id.txtGiaSPXemDonHang);
        txtngaymua = findViewById(R.id.txtNgayMuaXemDonHang);
        txtTenTK = findViewById(R.id.txtTenTkXemDonHang);
        btn_chotdon = findViewById(R.id.btn_Admin_ChotDon);
        btn_huydon = findViewById(R.id.btn_Admin_HuyDon);

        petDAO = new PetDAO(this);
        // String x = getIntent().getExtras().get("matk").toString();
        String tenpet =getIntent().getExtras().get("tenpet").toString();
        mapet = petDAO.getMaPet(tenpet);
        //Toast.makeText(this, "mã pet: "+ mapet, Toast.LENGTH_SHORT).show();
        //matk = Integer.parseInt(x);

        sharedPreferences = getSharedPreferences("THONGTINDONHANG",MODE_PRIVATE);


        //loaiPetDAO = new LoaiPetDAO(this);
        donHangDAO = new DonHangDAO(this);
        boolean check = donHangDAO.checkDonHang(tenpet);
        if(check) {
            madon = sharedPreferences.getInt("madon", 0);
            String tensp = sharedPreferences.getString("tenpet", "");
            String hinhanh = sharedPreferences.getString("hinhanh", "");
            String tentk = sharedPreferences.getString("tentk", "");
            String ngaymua = sharedPreferences.getString("ngaymua", "");
            int gia = sharedPreferences.getInt("giatri", 0);
            String strgia = NumberFormat.getNumberInstance(Locale.US).format(gia);
            int trangthaimua = sharedPreferences.getInt("trangthaimua", 0);
            if(trangthaimua==1){
                btn_chotdon.setVisibility(View.GONE);
                btn_huydon.setVisibility(View.GONE);
            }
            txtmadon.setText(madon + "");
            txttensp.setText(tensp);
            txtTenTK.setText(tentk + "");
            txtgia.setText(strgia + " VND");
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

        btn_chotdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy thời gian
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String ngay = simpleDateFormat.format(date);
                donHangDAO.thayDoiTrangThaiDonHang(madon,ngay);
                petDAO.thayDoiTrangThaiPet(mapet,2);
                showDiaLogAddThanhCong("Đã xác nhận đơn hàng này!");
            }
        });
        btn_huydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donHangDAO.xoaDonHang(madon);
                petDAO.thayDoiTrangThaiPet(mapet,0);
                showDiaLogAddThanhCong("Đã hủy đơn hàng này!");
            }
        });

    }

    private void showDiaLogAddThanhCong(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thongbao,null);
        TextView hienthi = view.findViewById(R.id.txtThongTin);
        Button btn = view.findViewById(R.id.btn_ok);
        hienthi.setText(x);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }
}