package com.example.duan1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duan1.Activity_Sua_Xoa_SP;
import com.example.duan1.DAO.DonHangDAO;
import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;

import java.util.ArrayList;

public class Adapter_Admin_KhachHang extends BaseAdapter {
    private Context c;
    private ArrayList<TaiKhoan> list;
    private TaiKhoanDAO taiKhoanDAO;
    private DonHangDAO donHangDAO;
    public Adapter_Admin_KhachHang(Context c, ArrayList<TaiKhoan>list,TaiKhoanDAO taiKhoanDAO){
        this.c = c;
        this.list = list;
        this.taiKhoanDAO = taiKhoanDAO;
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
        TextView txtMakh, txtTenkh,txtSolanmua;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        Adapter_Admin_KhachHang.ViewOfItem viewOfItem;

        if(view==null){
            viewOfItem = new Adapter_Admin_KhachHang.ViewOfItem();
            view = inf.inflate(R.layout.item_khachhang, parent, false);
            viewOfItem.txtMakh = view.findViewById(R.id.txtMaKhachHang);
            viewOfItem.txtTenkh = view.findViewById(R.id.txtTenKhachHang);
            viewOfItem.txtSolanmua = view.findViewById(R.id.txtSoLanMua);
            view.setTag(viewOfItem);
        }
        else{
            viewOfItem = (Adapter_Admin_KhachHang.ViewOfItem) view.getTag();
        }
        viewOfItem.txtMakh.setText(list.get(position).getMatk()+"");
        viewOfItem.txtTenkh.setText(list.get(position).getTentk());
        if(list.get(position).getSolanmua()==0){
            viewOfItem.txtSolanmua.setText("Chưa mua lần nào");
        }else {
            viewOfItem.txtSolanmua.setText(list.get(position).getSolanmua() + " lần");
        }
        return view;
    }
}
