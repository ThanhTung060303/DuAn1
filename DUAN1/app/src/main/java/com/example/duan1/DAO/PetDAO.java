package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DBHelped;
import com.example.duan1.Model.LoaiPet;
import com.example.duan1.Model.Pet;

import java.util.ArrayList;

public class PetDAO {
    DBHelped dbHelped ;
    SharedPreferences preferences;

    public PetDAO(Context c){

        dbHelped = new DBHelped(c);
        preferences = c.getSharedPreferences("THONGTINPET",Context.MODE_PRIVATE);
    }

    public ArrayList<Pet> getDSPet(String x){
        ArrayList<Pet> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PET WHERE MALOAI IN (SELECT MALOAIPET FROM LOAIPET WHERE LOAI=? )ORDER BY TRANGTHAI",new String[]{x});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Pet(cursor.getInt(0),cursor.getString(1),cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5),cursor.getInt(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean thayDoiTrangThaiPet(int mapet){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai",1);
        long check=sqLiteDatabase.update("PET",contentValues,"mapet=?",new String[]{String.valueOf(mapet)});
        if(check==-1){
            return false;
        }
        return true;
    }
    public ArrayList<Pet> getAllDSPet(){
        ArrayList<Pet> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped .getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PET WHERE TRANGTHAI=0", null);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Pet(cursor.getInt(0),cursor.getString(1),cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5),cursor.getInt(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themMoiPet(String tenpet, int tuoi, String hinhanh, String maloai , int gia){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenpet",tenpet);
        contentValues.put("tuoi",tuoi);
        contentValues.put("hinhanh",hinhanh);
        contentValues.put("maloai",maloai);
        contentValues.put("gia",gia);
        contentValues.put("trangthai",0);
        long check = sqLiteDatabase.insert("PET",null,contentValues);
        if(check==-1){
            return false;
        }
        return true;
    }

    public Boolean checkPet(int mapet){
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from PET where mapet = ?", new String[]{String.valueOf(mapet)});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("mapet",cursor.getInt(0));
            editor.putString("tenpet",cursor.getString(1));
            editor.putInt("tuoi",cursor.getInt(2));
            editor.putString("hinhanh",cursor.getString(3));
            editor.putString("maloai",cursor.getString(4));
            editor.putInt("gia",cursor.getInt(5));
            editor.putInt("trangthai",cursor.getInt(6));
            editor.commit();
            return true;
        }else{
            return false;
        }
    }

    public boolean capNhatThongTin(int mapet,String tenpet,int tuoi,String hinhanh, String maloai, int gia){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenpet", tenpet);
        contentValues.put("tuoi", tuoi);
        contentValues.put("hinhanh", hinhanh);
        contentValues.put("maloai", maloai);
        contentValues.put("gia", gia);
        long check = sqLiteDatabase.update("PET",contentValues, "mapet=?", new String[]{String.valueOf(mapet)});
        if(check==-1)
            return false;
        return true;
    }
    public boolean xoaPet(int idCanXoa){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        long check = sqLiteDatabase.delete("PET", "mapet=?",new String[]{String.valueOf(idCanXoa)});
        if(check==-1)
            return false;
        return true;
    }
}
