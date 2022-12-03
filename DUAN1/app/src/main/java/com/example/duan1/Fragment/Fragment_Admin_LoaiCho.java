package com.example.duan1.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.duan1.Adapter.AdapterLoaiCho;
import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.Model.LoaiPet;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_Admin_LoaiCho extends Fragment {
    FloatingActionButton floatAdd;
    ArrayList<LoaiPet> list;
    LoaiPetDAO loaiPetDAO;
    AdapterLoaiCho adapter;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_loaicho, container, false);
        floatAdd = view.findViewById(R.id.floataddLoaiCho);
        listView = view.findViewById(R.id.listViewLoaiCho);

        loaiPetDAO = new LoaiPetDAO(getContext());
        loadDuLieu();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThem();
            }
        });

        return view;
    }

    private void showDialogThem(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_addloaipet,null);
        TextView txtTitle = view.findViewById(R.id.txtTitleAddLoaiPet);
        EditText  etmaloai = view.findViewById(R.id.et_inputAddMaLoai);
        EditText ettenloai = view.findViewById(R.id.et_inputAddTenLoai);
        alert.setView(view);
        alert.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String maloai = etmaloai.getText().toString();
                String tenloai = ettenloai.getText().toString();

                AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                if(loaiPetDAO.themMoiLoaiPet(maloai,tenloai,"chó")){
                    message.setTitle("Thông Báo");
                    message.setMessage("Thêm thành công!");
                    message.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog a = message.create();
                    a.show();
                    loadDuLieu();
                }
                else {
                    message.setTitle("Thông Báo");
                    message.setMessage("Thêm không thành công!");
                    message.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog a = message.create();
                    a.show();
                }
            }
        });
        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void loadDuLieu(){
        list = loaiPetDAO.getDSLoaiPet("chó");
        adapter = new AdapterLoaiCho(getContext(),list,loaiPetDAO);
        listView.setAdapter(adapter);
    }
}