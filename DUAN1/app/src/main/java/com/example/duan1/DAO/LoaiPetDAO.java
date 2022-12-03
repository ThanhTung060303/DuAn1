package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DBHelped;
import com.example.duan1.Model.LoaiPet;

import java.util.ArrayList;

public class LoaiPetDAO {
    DBHelped dbHelped ;

    public LoaiPetDAO(Context c){
        dbHelped = new DBHelped(c);
    }

    public ArrayList<LoaiPet> getDSLoaiPet(String x){
        ArrayList<LoaiPet> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAIPET WHERE LOAI=?",new String[]{x});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiPet(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public String getTenLoaiPet(String maloaipet){
        String tenloai = "chuaw co";
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT TENLOAIPET FROM LOAIPET WHERE MALOAIPET=?",new String[]{maloaipet});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                tenloai = cursor.getString(0);
            }while (cursor.moveToNext());
        }
        return tenloai;
    }

    public boolean themMoiLoaiPet(String maloai,String tenloai, String loai){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maloaipet",maloai);
        contentValues.put("tenloaipet",tenloai);
        contentValues.put("loai",loai);
        long check = sqLiteDatabase.insert("LOAIPET",null,contentValues);
        if(check==-1){
            return false;
        }
        return true;
    }

    public int xoaLoaiPet(String idCanXoa){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PET WHERE maloai=?", new String[]{idCanXoa});
        if(cursor.getCount()!=0){
            return -1;
        }
        long check = sqLiteDatabase.delete("LOAIPET", "maloaipet=?",new String[]{idCanXoa});
        if(check == -1)
            return 0;
        return 1;
    }

    public boolean capNhatThongTin(String maloaipet,String tenloai){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloaipet", tenloai);
        long check = sqLiteDatabase.update("LOAIPET",contentValues, "maloaipet=?", new String[]{maloaipet});
        if(check==-1)
            return false;
        return true;
    }
}
