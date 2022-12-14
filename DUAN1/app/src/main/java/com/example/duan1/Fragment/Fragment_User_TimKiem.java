package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.Adapter_User_TrangChu;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.Model.Pet;
import com.example.duan1.R;

import java.util.ArrayList;

public class Fragment_User_TimKiem extends Fragment {
    PetDAO petDAO;
    public static ArrayList<Pet> list;
    RecyclerView recyclerView;
    Adapter_User_TrangChu adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_trangchu, container, false);
        recyclerView = view.findViewById(R.id.recycleTrangChu);
        petDAO = new PetDAO(getContext());
        loadData(list);
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        list = petDAO.getAllDSPet();
//        loadData();
//    }


    private void loadData(ArrayList<Pet> list) {
        petDAO = new PetDAO(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new Adapter_User_TrangChu(list, getContext(),petDAO);
        recyclerView.setAdapter(adapter);
    }
}