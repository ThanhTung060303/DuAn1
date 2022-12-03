package com.example.duan1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;

import java.util.ArrayList;

public class Adapter_User_GioHang extends BaseAdapter {
    private Context c;
    private ArrayList<DonHang> list;
    private TaiKhoanDAO taiKhoanDAO;
    private DonHangDAO donHangDAO;
    public Adapter_User_GioHang(Context c, ArrayList<DonHang>list,DonHangDAO donHangDAO){
        this.c = c;
        this.list = list;
        this.donHangDAO = donHangDAO;
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
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        Adapter_User_GioHang.ViewOfItem viewOfItem;

        if(view==null){
            viewOfItem = new Adapter_User_GioHang.ViewOfItem();
            view = inf.inflate(R.layout.item_giohang, parent, false);
            viewOfItem.txtTenSP = view.findViewById(R.id.txtTenPetGioHang);
            viewOfItem.txtGia = view.findViewById(R.id.txtGiaPetGioHang);
            viewOfItem.txtMadon = view.findViewById(R.id.txtMaDonGioHang);
            viewOfItem.txtNgayMua = view.findViewById(R.id.txtNgayMuaGioHang);
            viewOfItem.iv = view.findViewById(R.id.ivGioHang);

            view.setTag(viewOfItem);
        }
        else{
            viewOfItem = (Adapter_User_GioHang.ViewOfItem) view.getTag();
        }
        viewOfItem.txtNgayMua.setText(list.get(position).getNgaymua());
        viewOfItem.txtMadon.setText(list.get(position).getMadon()+"");
        viewOfItem.txtTenSP.setText(list.get(position).getTenpet());
        viewOfItem.txtGia.setText(list.get(position).getGiatri()+ "VND");
        Glide.with(c)
                .load(list.get(position).getHinhanh())
                .into(viewOfItem.iv);
        return view;
    }
}
