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
import android.widget.TextView;

import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.Model.LoaiPet;
import com.example.duan1.R;

import java.util.ArrayList;

public class AdapterLoaiCho extends BaseAdapter {
    private Context c;
    private ArrayList<LoaiPet> listLoaiCho;
    private LoaiPetDAO loaiPetDAO;

    public AdapterLoaiCho(Context c, ArrayList<LoaiPet> listLoaiCho, LoaiPetDAO loaiPetDAO) {
        this.c = c;
        this.listLoaiCho = listLoaiCho;
        this.loaiPetDAO = loaiPetDAO;
    }

    @Override
    public int getCount() {
        return listLoaiCho.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaiCho.get(position);
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
        viewOfItem.txtMaLoai.setText(listLoaiCho.get(position).getMaloaipet());
        viewOfItem.txtTenLoai.setText(listLoaiCho.get(position).getTenloaipet());

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogCapNhat(listLoaiCho.get(position));
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiPetDAO = new LoaiPetDAO(c);

                int check = loaiPetDAO.xoaLoaiPet(listLoaiCho.get(position).getMaloaipet());
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
        listLoaiCho.clear();
        listLoaiCho = loaiPetDAO.getDSLoaiPet("chó");
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
