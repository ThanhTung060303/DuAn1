package com.example.duan1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.TaiKhoan;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity_DangKy extends AppCompatActivity {
    Button btnBack,btnDangKy;
    EditText etTK,etPass,etCheck;
    TaiKhoanDAO taiKhoanDAO;
    ArrayList<String> listTenTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        btnBack = findViewById(R.id.btn_backDangKy);
        btnDangKy = findViewById(R.id.btn_dangKyStart);
        etTK = findViewById(R.id.et_inputUserDangKy);
        etPass = findViewById(R.id.et_inputPassDangKy);
        etCheck = findViewById(R.id.et_inputCheckPassDangKy);

        taiKhoanDAO = new TaiKhoanDAO(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_DangKy.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = etTK.getText().toString();
                String matkhau = etPass.getText().toString();
                String checkMK = etCheck.getText().toString();
                boolean check = checkDangKy();
                if(check){
                    if(taiKhoanDAO.themTaiKhoan(taikhoan,matkhau,1)){
                        if(taiKhoanDAO.checkDangNhap(taikhoan, matkhau)){
                            SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
                            String tenTK = sharedPreferences.getString("tentk","");
                            String ht = "Đăng ký thành công";
                            Toast.makeText(Activity_DangKy.this, tenTK, Toast.LENGTH_SHORT).show();
                            showDiaLogDKThanhCong(ht);
                        }


                    }
                    else{
                        String ht = "Đăng ký thất bại";
                        showDiaLog(ht);
                    }
                }
            }
        });
    }

    private boolean checkDangKy(){
        String taikhoan = etTK.getText().toString();
        String matkhau = etPass.getText().toString();
        String checkMK = etCheck.getText().toString();
        if(taikhoan.equals("") || matkhau.equals("") || checkMK.equals("")){
            String ht = "Vui lòng nhập đầy đủ thông tin!";
            showDiaLog(ht);
            return false;
        }
        else{
            listTenTK= taiKhoanDAO.getDSTenTaiKhoan();
            for(String tentk: listTenTK){
                if(taikhoan.equalsIgnoreCase(tentk)){
                    String ht = "Tên tài khoản đã tồn tại!";
                    showDiaLog(ht);
                    return false;
                }
                else if(!matkhau.equals(checkMK)){
                    String ht = "Mật khẩu không trùng khớp!";
                    showDiaLog(ht);
                    return false;
                }
            }
        }
        return true;
    }

    private void showDiaLog(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thongbao,null);
        TextView hienthi = view.findViewById(R.id.txtThongTin);
        hienthi.setText(x);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void showDiaLogDKThanhCong(String x){
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
                Intent i = new Intent(Activity_DangKy.this,Activity_User.class);
                //Intent intent = i.putExtra("ten",taikhoan);
                //Intent intenta = i.putExtra("mk",matkhau);
                //startActivityForResult(i,666);
                startActivity(i);
            }
        });
    }

}