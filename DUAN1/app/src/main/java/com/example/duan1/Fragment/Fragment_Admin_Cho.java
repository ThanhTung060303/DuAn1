package com.example.duan1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.Activity_ThemSanPham;
import com.example.duan1.Adapter.AdapterLoaiMeo;
import com.example.duan1.Adapter.Adapter_Admin_Cho;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.Model.Pet;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_Admin_Cho extends Fragment {
    FloatingActionButton floatadd;
    ListView listView;
    Adapter_Admin_Cho adapter;
    PetDAO petDAO;
    ArrayList<Pet> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_cho, container, false);
        floatadd = view.findViewById(R.id.floataddCho);
        listView = view.findViewById(R.id.listViewCho);

        petDAO = new PetDAO(getContext());
        loadDuLieu();

        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_ThemSanPham.class);
                i.putExtra("loai","chó");

                startActivityForResult(i,999);
            }
        });

        return view;
    }

    private void loadDuLieu(){
        list = petDAO.getDSPet("chó");
        Log.d("MYLOG", "list.size: " + list.size());
        for (int i = 0; i < list.size(); i++) {
            Log.d("MYLOG", "item: " + list.get(i).getHinhanh());
        }
        adapter = new Adapter_Admin_Cho(getContext(),list,petDAO);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDuLieu();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MYLOG", "aa");
        if(requestCode==999&&resultCode==-1){
            int check=data.getExtras().getInt("capnhat");
            if(check==1){
                Toast.makeText(getContext(), check+"", Toast.LENGTH_SHORT).show();
                loadDuLieu();
            }
        }
    }
}
