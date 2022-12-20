package com.example.duan1.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1.Activity_Mua_SP;
import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.Model.Pet;
import com.example.duan1.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_User_TrangChu extends RecyclerView.Adapter<Adapter_User_TrangChu.ViewHolder>{
    private ArrayList<Pet> list;
    private Context c;
    private PetDAO petDAO;
    private LoaiPetDAO loaiPetDAO;
    SharedPreferences sharedPreferences;

    public Adapter_User_TrangChu(ArrayList<Pet> list, Context c,PetDAO petDAO) {
        this.list = list;
        this.c = c;
        this.petDAO = petDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_trangchu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sharedPreferences = c.getSharedPreferences("THONGTIN", MODE_PRIVATE);
        int matk = sharedPreferences.getInt("matk",0);
        int mapet = list.get(holder.getAdapterPosition()).getMapet();
        String maloai = list.get(holder.getAdapterPosition()).getMaloai();

        loaiPetDAO = new LoaiPetDAO(c);
        String loai = loaiPetDAO.getTenLoaiPet(maloai);
        int gia = list.get(position).getGia();
        String strgia = NumberFormat.getNumberInstance(Locale.US).format(gia);

        holder.txtLoaiSp.setText(loai);
        holder.txtTenSP.setText(list.get(position).getTenpet());
        holder.txtGia.setText(strgia+"VND");
        Glide.with(c)
                .load(list.get(position).getHinhanh())
                .into(holder.ivSP);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(c, Activity_Mua_SP.class);
                i.putExtra("mapet",mapet+"");
                i.putExtra("matk",matk+"");
                ((Activity)c).startActivityForResult(i,999);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenSP, txtGia,txtLoaiSp;
        ImageView ivSP;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGia = itemView.findViewById(R.id.txtGiaSP);
            ivSP = itemView.findViewById(R.id.imgSP);
            cardView = itemView.findViewById(R.id.cardSP);
            txtLoaiSp = itemView.findViewById(R.id.txtLoaiSP_Trangchu);

        }
    }
}
