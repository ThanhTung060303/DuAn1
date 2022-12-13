package com.example.duan1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duan1.Activity_Sua_Xoa_SP;
import com.example.duan1.Activity_XemDonHang;
import com.example.duan1.DAO.DonHangDAO;
import com.example.duan1.DAO.TaiKhoanDAO;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.TaiKhoan;
import com.example.duan1.R;

import java.util.ArrayList;

public class Adapter_Admin_DonHang_ChuaXN extends BaseAdapter {
    private Context c;
    private ArrayList<DonHang> list;
    private TaiKhoanDAO taiKhoanDAO;
    private DonHangDAO donHangDAO;
    public Adapter_Admin_DonHang_ChuaXN(Context c, ArrayList<DonHang>list,DonHangDAO donHangDAO){
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
        TextView txtMadon, txtTenSp,txtNgayMua,txtGiatri;
        LinearLayout item;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        Adapter_Admin_DonHang_ChuaXN.ViewOfItem viewOfItem;

        if(view==null){
            viewOfItem = new Adapter_Admin_DonHang_ChuaXN.ViewOfItem();
            view = inf.inflate(R.layout.item_donhang, parent, false);
            viewOfItem.txtMadon = view.findViewById(R.id.txtMadon);
            viewOfItem.txtTenSp = view.findViewById(R.id.txtTenSP_donhang);
            viewOfItem.txtNgayMua = view.findViewById(R.id.txtNgayMua);
            viewOfItem.txtGiatri = view.findViewById(R.id.txtGiatri);
            viewOfItem.item = view.findViewById(R.id.item_donhang);

            view.setTag(viewOfItem);
        }
        else{
            viewOfItem = (Adapter_Admin_DonHang_ChuaXN.ViewOfItem) view.getTag();
        }
        viewOfItem.txtMadon.setText(list.get(position).getMadon()+"");
        viewOfItem.txtTenSp.setText(list.get(position).getTenpet());
        viewOfItem.txtNgayMua.setText(list.get(position).getNgaymua());
        viewOfItem.txtGiatri.setText(list.get(position).getGiatri()+"VND");
        viewOfItem.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Activity_XemDonHang.class);
                String tenpet = list.get(position).getTenpet();

                i.putExtra("tenpet",tenpet);

                ((Activity)c).startActivityForResult(i,999);
            }
        });

        return view;
    }
}