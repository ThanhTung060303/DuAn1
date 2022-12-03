package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.Model.LoaiPet;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterLoaiMeo extends BaseAdapter {
    private Context c;
    private ArrayList<LoaiPet> listLoaiMeo;
    private LoaiPetDAO loaiPetDAO;

    public AdapterLoaiMeo(Context c, ArrayList<LoaiPet> listLoaiMeo, LoaiPetDAO loaiPetDAO) {
        this.c = c;
        this.listLoaiMeo = listLoaiMeo;
        this.loaiPetDAO = loaiPetDAO;
    }

    @Override
    public int getCount() {
        return listLoaiMeo.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaiMeo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewOfItem{
        TextView txtMaLoai,txtTenLoai;
        ImageView ivSua,ivXoa;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        ViewOfItem viewOfItem;

        if(view==null){
            viewOfItem = new ViewOfItem();
            view = inf.inflate(R.layout.item_loaipet, parent, false);
            viewOfItem.txtMaLoai = view.findViewById(R.id.txtMaLoai);
            viewOfItem.txtTenLoai = view.findViewById(R.id.txtTenLoai);
            viewOfItem.ivSua = view.findViewById(R.id.ivSuaLoaiPet);
            viewOfItem.ivXoa = view.findViewById(R.id.ivXoaLoaiPet);
            view.setTag(viewOfItem);
        }
        else{
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.txtMaLoai.setText(listLoaiMeo.get(position).getMaloaipet());
        viewOfItem.txtTenLoai.setText(listLoaiMeo.get(position).getTenloaipet());

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogCapNhat(listLoaiMeo.get(position));
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiPetDAO = new LoaiPetDAO(c);

                int check = loaiPetDAO.xoaLoaiPet(listLoaiMeo.get(position).getMaloaipet());
                switch (check){
                    case 1:
                        reloadData();
                        showDiaLog("Xóa thành công!");
                        break;
                    case -1:
                        showDiaLog("Không thể xóa loại pet này!");
                        break;
                    case 0:
                        showDiaLog("Xóa không thành công");
                    default:
                        break;
                }
            }
        });
        return view;
    }

    public void reloadData(){
        listLoaiMeo.clear();
        listLoaiMeo = loaiPetDAO.getDSLoaiPet("mèo");
        notifyDataSetChanged();
    }
    public void showDiaLog(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
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
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDiaLogCapNhat(LoaiPet loaipet){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaipet,null);
        builder.setView(view);
        TextView txtMaLoai = view.findViewById(R.id.txtMaLoaiCapNhat);
        EditText etTenLoai = view.findViewById(R.id.et_inputCapNhatTenLoai);

        txtMaLoai.setText("Mã pet: "+ loaipet.getMaloaipet());
        etTenLoai.setText(loaipet.getTenloaipet());

        builder.setNegativeButton("Cập Nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenmoi = etTenLoai.getText().toString();
                String maloai = loaipet.getMaloaipet();
                boolean check = loaiPetDAO.capNhatThongTin(maloai,tenmoi);
                if(check){
                    showDiaLog("Cập nhật thành công!");
                    reloadData();
                }
                else{
                    showDiaLog("Cập nhật không thành công!");
                }

            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
