package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.TaiKhoan;

public class Activity_DangNhap extends AppCompatActivity {
    Button btnBack,btnDN;
    EditText etUser, etPass;
    TaiKhoanDAO taiKhoanDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        btnBack = findViewById(R.id.btn_backDangNhap);
        btnDN = findViewById(R.id.btn_dangNhapStart);
        etUser = findViewById(R.id.et_inputUserDangNhap);
        etPass = findViewById(R.id.et_inputPassDangNhap);
        taiKhoanDAO = new TaiKhoanDAO(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_DangNhap.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String pass = etPass.getText().toString();
                if(taiKhoanDAO.checkDangNhap(user,pass)){
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
                    int loaiTK = sharedPreferences.getInt("loaitk",0);
                    if(loaiTK==0){
                        Intent i = new Intent(Activity_DangNhap.this, Activity_Admin.class);
                        startActivity(i);
                    }
                    else{
                        Intent i1 = new Intent(Activity_DangNhap.this, Activity_User.class);
                        startActivity(i1);
                    }
                   //String hoten = sharedPreferences.getString("hoten","");
                    //txtTen.setText("Xin Chào ! "+ hoten);
                }
                else{
                    showDiaLog("Tên tài khoản hoặc mật khẩu chưa chính xác!");
                }

            }
        });
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
}