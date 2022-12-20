package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duan1.Activity_Sua_Xoa_SP;
import com.example.duan1.DAO.DonHangDAO;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_User_GioHang_ChuaXN extends BaseAdapter {
    private Context c;
    private ArrayList<DonHang> list;
    private PetDAO petDAO;
    private DonHangDAO donHangDAO;
    public Adapter_User_GioHang_ChuaXN(Context c, ArrayList<DonHang>list,DonHangDAO donHangDAO, PetDAO petDAO){
        this.c = c;
        this.list = list;
        this.donHangDAO = donHangDAO;
        this.petDAO = petDAO;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewOfItem {
        TextView txtTenSP,txtGia,txtMadon,txtNgayMua;
        ImageView iv;
        Button btn_huydon;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        Adapter_User_GioHang_ChuaXN.ViewOfItem viewOfItem;

        if(view==null){
            viewOfItem = new Adapter_User_GioHang_ChuaXN.ViewOfItem();
            view = inf.inflate(R.layout.item_giohang, parent, false);
            viewOfItem.txtTenSP = view.findViewById(R.id.txtTenPetGioHang);
            viewOfItem.txtGia = view.findViewById(R.id.txtGiaPetGioHang);
            viewOfItem.txtMadon = view.findViewById(R.id.txtMaDonGioHang);
            viewOfItem.txtNgayMua = view.findViewById(R.id.txtNgayMuaGioHang);
            viewOfItem.iv = view.findViewById(R.id.ivGioHang);
            viewOfItem.btn_huydon = view.findViewById(R.id.btn_User_HuyDon);

            view.setTag(viewOfItem);
        }
        else{
            viewOfItem = (Adapter_User_GioHang_ChuaXN.ViewOfItem) view.getTag();
        }
        int gia = list.get(position).getGiatri();
        String strgia = NumberFormat.getNumberInstance(Locale.US).format(gia);
        viewOfItem.txtNgayMua.setText(list.get(position).getNgaymua());
        viewOfItem.txtMadon.setText(list.get(position).getMadon()+"");
        viewOfItem.txtTenSP.setText(list.get(position).getTenpet());
        viewOfItem.txtGia.setText(strgia+ "VND");
        Glide.with(c)
                .load(list.get(position).getHinhanh())
                .into(viewOfItem.iv);
        int trangthaimua = list.get(position).getTrangthaimua();
        if(trangthaimua==1){
            viewOfItem.btn_huydon.setVisibility(View.GONE);
        }
        viewOfItem.btn_huydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int madon = list.get(position).getMadon();
                String tenpet = list.get(position).getTenpet();
                int mapet = petDAO.getMaPet(tenpet);
                donHangDAO.xoaDonHang(madon);
                petDAO.thayDoiTrangThaiPet(mapet,0);
                showDiaLogAddThanhCong("Bạn đã hủy đơn hàng này!");
            }
        });
        return view;
    }

    private void showDiaLogAddThanhCong(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
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
