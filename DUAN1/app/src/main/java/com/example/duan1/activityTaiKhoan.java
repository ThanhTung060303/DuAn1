package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.TaiKhoanDAO;
import com.google.android.material.navigation.NavigationView;

public class activityTaiKhoan extends AppCompatActivity {
    ImageView ivBack, ivShow;
    TextView txtTen,txtMatk,txtTentk, txtmk;
    CardView cardView;
    NavigationView navigationView;
    int check = 1, a ;
    TaiKhoanDAO taiKhoanDAO;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
        ivBack = findViewById(R.id.ivBackTaiKhoan);
        ivShow = findViewById(R.id.ivShowTTTaiKhoan);
        txtTen = findViewById(R.id.txtTenTKShow);
        txtMatk = findViewById(R.id.txtMaTK);
        txtTentk = findViewById(R.id.txtTenTK);
        txtmk = findViewById(R.id.txtMatKhauTK);
        cardView = findViewById(R.id.CardViewTaiKhoan);
        navigationView = findViewById(R.id.navigationViewTaiKhoan);
        cardView.setVisibility(View.GONE);

        taiKhoanDAO = new TaiKhoanDAO(this);

        sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        int loaiTK = sharedPreferences.getInt("loaitk",0);
        if(loaiTK==1){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mThemTK).setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mDangXuatTK:
                            Intent i  = new Intent(activityTaiKhoan.this, MainActivity.class);
                            startActivity(i);
                        break;
                    case R.id.mThoatApp:
                        finishAffinity();
                        break;
                    case R.id.mDoiMatKhauTK:
                        showDiaLogDoiMK();
                        break;
                    case R.id.mThemTK:
                        showDiaLogAddAdmin();
                        break;


                }
                return false;
            }
        });

        sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        int matk = sharedPreferences.getInt("matk", 0);
        String tentk = sharedPreferences.getString("tentk","");
        String mk = sharedPreferences.getString("matkhau","");
        txtTen.setText(tentk);
        txtMatk.setText(String.valueOf(matk));
        txtTentk.setText(tentk);
        txtmk.setText(mk);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loaitk = sharedPreferences.getInt("loaitk", 0);
                if(loaitk==0){
                    Intent i = new Intent(activityTaiKhoan.this, Activity_Admin.class);
                    startActivity(i);
                }else if(loaitk==1){
                    Intent i = new Intent(activityTaiKhoan.this, Activity_User.class);
                    startActivity(i);
                }

            }
        });
        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThongTin(check);
                check ++;
            }
        });
    }

    private void showThongTin(int i){
        if(i %2==0){
            cardView.setVisibility(View.GONE);
            ivShow.setImageResource(R.mipmap.right);

        }else {
            cardView.setVisibility(View.VISIBLE);
            ivShow.setImageResource(R.mipmap.down);
        }
    }

    /*private boolean showDiaLogYesNo(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_y_n,null);
        TextView hienthi = view.findViewById(R.id.txtCauHoi);
        hienthi.setText(x);
        builder.setView(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cardView.setVisibility(View.VISIBLE);
               a =1;
                new activityTaiKhoan().CheckOK();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        int b = a;
        AlertDialog dialog = builder.create();
        dialog.show();
        if(b ==1){
            return true;
        }
        return false;
    }

    private boolean CheckOK(){
        return true;
    }

     */

    private void showDiaLogDoiMK(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setNegativeButton("Cập Nhật", null)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau,null);
        TextView txtMaTK  = view.findViewById(R.id.txtMaTaiKhoanCapNhat);
        EditText etOldpass = view.findViewById(R.id.et_OldPass);
        EditText etNewpass = view.findViewById(R.id.et_NewPass);
        EditText etReNewpass = view.findViewById(R.id.et_ReNewPass);

        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        int matk = sharedPreferences.getInt("matk", 0);
        String tentk = sharedPreferences.getString("tentk","");
        // cập nhật
        txtMaTK.setText("Mã TK: "+ matk + "  Tên: "+ tentk);
        builder.setView(view);

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();

        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldpass = etOldpass.getText().toString();
                String newpass = etNewpass.getText().toString();
                String renewpass = etReNewpass.getText().toString();
                if (oldpass.equals("") || newpass.equals("") || renewpass.equals("")) {
                    showDiaLogThongBao("Vui lòng nhập đầy đủ thông tin!");
                } else {
                    if (newpass.equalsIgnoreCase(renewpass)) {
                        int check = taiKhoanDAO.capNhatMatKhau(matk, oldpass, newpass);
                        if (check == 1) {
                            showDiaLogThongBao("Cập nhật mật khẩu thành công!");
                            Intent i = new Intent(activityTaiKhoan.this, Activity_DangNhap.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else if (check == 0) {
                            showDiaLogThongBao("Mật khẩu cũ không đúng!");
                        } else {
                            showDiaLogThongBao("Cập nhật mật khẩu thất bại!");
                        }
                    } else {
                        showDiaLogThongBao("Nhập mật khẩu không trùng nhau!");
                    }
                }
            }
        });
    }

    private void showDiaLogThongBao(String x){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thongbao,null);
        TextView hienthi = view.findViewById(R.id.txtThongTin);
        hienthi.setText(x);
        builder.setView(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDiaLogAddAdmin(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setNegativeButton("Thêm", null)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themadmin,null);
        EditText etTenTK = view.findViewById(R.id.et_tenTkAddAdmin);
        EditText etMK = view.findViewById(R.id.et_NewPassAddAdmin);
        EditText etCheckMK = view.findViewById(R.id.et_RePassAddAdmin);

        builder.setView(view);

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();

        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tentk = etTenTK.getText().toString();
                String mk = etMK.getText().toString();
                String remk = etCheckMK.getText().toString();
                if (tentk.equals("") || mk.equals("") || remk.equals("")) {
                    showDiaLogThongBao("Vui lòng nhập đầy đủ thông tin!");
                } else {
                    if (mk.equalsIgnoreCase(remk)) {
                        boolean check = taiKhoanDAO.themTaiKhoan(tentk,mk,0);
                        if (check) {
                            showDiaLogThongBao("Thêm Admin thành công!");
                            alertDialog.cancel();
                        } else {
                            showDiaLogThongBao("Thêm Admin thất bại!");
                        }
                    } else {
                        showDiaLogThongBao("Nhập mật khẩu không trùng nhau!");
                    }
                }
            }
        });
    }
}