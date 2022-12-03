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

public class Activity_Mua_SP extends AppCompatActivity {
    ImageView imageView, ivBack;
    TextView txtmasp, txttensp,txttuoi,txtloai,txtgia;
    Button btnBack, btnMua;
    PetDAO petDAO;
    LoaiPetDAO loaiPetDAO;
    DonHangDAO donHangDAO;
    int mapet,matk;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_sp);
        imageView = findViewById(R.id.ivSanPhamMua);
        ivBack = findViewById(R.id.ivBackMuaPet);
        txtmasp = findViewById(R.id.txtMaSP);
        txttensp = findViewById(R.id.txtTenSPMua);
        txttuoi = findViewById(R.id.txtTuoiSP);
        txtloai = findViewById(R.id.txtLoaiSP);
        txtgia = findViewById(R.id.txtGiaSPMua);
        btnBack = findViewById(R.id.btn_BackTrangChu);
        btnMua = findViewById(R.id.btn_MuaPet);

        String x = getIntent().getExtras().get("matk").toString();
        String y =getIntent().getExtras().get("mapet").toString();
        mapet = Integer.parseInt(y);
        matk = Integer.parseInt(x);

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
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogYesNo("Xác nhận đơn hàng của bạn!");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }

    public String getNow(){
        // lấy thời gian
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(date);
        return ngay;
    }

    private void showDiaLogYesNo(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_y_n,null);
        TextView hienthi = view.findViewById(R.id.txtCauHoi);
        hienthi.setText(x);
        builder.setView(view);

        builder.setPositiveButton("Mua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String now = getNow();
                boolean check = donHangDAO.themDonHangMoi(mapet,matk,now);
                if(check){
                    showDiaLogAddThanhCong("Mua thành công!");
                    petDAO.thayDoiTrangThaiPet(mapet);
                }
                else{
                    showDiaLog("Mua không thành công!");
                }

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDiaLog(String x){
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