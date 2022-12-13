package com.example.duan1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.duan1.Activity_DangKy;
import com.example.duan1.Activity_Mua_SP;
import com.example.duan1.Activity_Pet_DaMua;
import com.example.duan1.Activity_Sua_Xoa_SP;
import com.example.duan1.Activity_ThemSanPham;
import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.Model.LoaiPet;
import com.example.duan1.Model.Pet;
import com.example.duan1.R;

import java.util.ArrayList;

public class Adapter_Admin_Cho extends BaseAdapter {
    private Context c;
    private ArrayList<Pet> listCho;
    private PetDAO petDAO;

    public Adapter_Admin_Cho(Context c, ArrayList<Pet> listCho, PetDAO petDAO) {
        this.c = c;
        this.listCho = listCho;
        this.petDAO = petDAO;
    }
    @Override
    public int getCount() {
        return listCho.size();
    }

    @Override
    public Object getItem(int position) {
        return listCho.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewOfItem {
        TextView txtTenPet, txtXemTT, txtTrangThai;
        ImageView iv;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        Adapter_Admin_Cho.ViewOfItem viewOfItem;

        if(view==null){
            viewOfItem = new Adapter_Admin_Cho.ViewOfItem();
            view = inf.inflate(R.layout.item_pet, parent, false);
            viewOfItem.txtTenPet = view.findViewById(R.id.txtTenPet);
            viewOfItem.txtXemTT = view.findViewById(R.id.txtXemThem);
            viewOfItem.txtTrangThai = view.findViewById(R.id.txtTrangthaiItem);
            viewOfItem.iv = view.findViewById(R.id.ivPet);
            view.setTag(viewOfItem);
        }
        else{
            viewOfItem = (Adapter_Admin_Cho.ViewOfItem) view.getTag();
        }
        viewOfItem.txtTenPet.setText(listCho.get(position).getTenpet());
        int trangthai = listCho.get(position).getTrangthai();
        if(trangthai==0){
            viewOfItem.txtTrangThai.setText("Đang bán");
            viewOfItem.txtTrangThai.setTextColor(ContextCompat.getColor(c, R.color.green));
            viewOfItem.txtXemTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    Intent i = new Intent(c, Activity_Sua_Xoa_SP.class);
                    int mapet = listCho.get(position).getMapet();
                    i.putExtra("mapet",mapet);
                    i.putExtra("loai","chó");
                    ((Activity)c).startActivityForResult(i,999);
                }
            });

        }
        else if(trangthai==1){
            viewOfItem.txtTrangThai.setText("Chờ xác nhận");
            viewOfItem.txtTrangThai.setTextColor(ContextCompat.getColor(c, R.color.purple_200));
            viewOfItem.txtXemTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    Intent i = new Intent(c, Activity_Pet_DaMua.class);
                    int mapet = listCho.get(position).getMapet();
                    i.putExtra("mapet",mapet);
                    ((Activity)c).startActivityForResult(i,999);
                }
            });
        }
        else{
            viewOfItem.txtTrangThai.setText("Đã bán");
            viewOfItem.txtTrangThai.setTextColor(ContextCompat.getColor(c, R.color.red));
            viewOfItem.txtXemTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    Intent i = new Intent(c, Activity_Pet_DaMua.class);
                    int mapet = listCho.get(position).getMapet();
                    i.putExtra("mapet",mapet);
                    ((Activity)c).startActivityForResult(i,999);
                }
            });

        }
        Glide.with(c)
                .load(listCho.get(position).getHinhanh())
                .into(viewOfItem.iv);


        return view;
    }
}
