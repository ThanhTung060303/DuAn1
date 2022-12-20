package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DBHelped;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.LoaiPet;
import com.example.duan1.Model.TaiKhoan;

import java.util.ArrayList;

public class DonHangDAO {
    DBHelped dbHelped ;
    SharedPreferences preferences;

    public DonHangDAO(Context c){

        dbHelped = new DBHelped(c);
        preferences = c.getSharedPreferences("THONGTINDONHANG",Context.MODE_PRIVATE);
    }

    public Boolean checkDonHang(String tensp){
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT A.MADON, B.TENPET,B.HINHANH, C.TENTK , A.NGAYMUA, B.GIA ,A.TRANGTHAIMUA FROM DONHANG A INNER JOIN PET B ON A.MASP = B.MAPET INNER JOIN TAIKHOAN C ON A.MANGUOIMUA = C.MATK WHERE B.TENPET=?", new String[]{tensp});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("madon",cursor.getInt(0));
            editor.putString("tenpet",cursor.getString(1));
            editor.putString("hinhanh",cursor.getString(2));
            editor.putString("tentk",cursor.getString(3));
            editor.putString("ngaymua",cursor.getString(4));
            editor.putInt("giatri",cursor.getInt(5));
            editor.putInt("trangthaimua",cursor.getInt(6));
            editor.commit();
            return true;
        }else{
            return false;
        }
    }

    public boolean themDonHangMoi(int masp,int manguoimua, String ngaymua){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("masp",masp);
        contentValues.put("manguoimua",manguoimua);
        contentValues.put("ngaymua",ngaymua);
        contentValues.put("trangthaimua",0);

        long check = sqLiteDatabase.insert("DONHANG",null,contentValues);
        if(check==-1)
            return false;
        return true;
    }


    public ArrayList<DonHang> getDSGioHang(int manguoimua,int trangthaimua){
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT A.MADON, B.TENPET,B.HINHANH, A.NGAYMUA, B.GIA, A.TRANGTHAIMUA FROM DONHANG A INNER JOIN PET B ON A.MASP = B.MAPET INNER JOIN TAIKHOAN C ON A.MANGUOIMUA = C.MATK WHERE MANGUOIMUA = ? AND TRANGTHAIMUA =?",new String[]{String.valueOf(manguoimua),String.valueOf(trangthaimua)});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new DonHang(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getInt(4),cursor.getInt(5)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<DonHang> getDSDonHang(int trangthaimua){
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT A.MADON, B.TENPET,B.HINHANH, C.TENTK , A.NGAYMUA, B.GIA, A.TRANGTHAIMUA FROM DONHANG A INNER JOIN PET B ON A.MASP = B.MAPET INNER JOIN TAIKHOAN C ON A.MANGUOIMUA = C.MATK WHERE A.TRANGTHAIMUA = ?",new String[]{String.valueOf(trangthaimua)});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new DonHang(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getInt(5),cursor.getInt(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean xoaDonHang(int madonCanXoa){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        long check = sqLiteDatabase.delete("DONHANG", "madon=?",new String[]{String.valueOf(madonCanXoa)});
        if(check == -1)
            return false;
        return true;
    }

    public boolean thayDoiTrangThaiDonHang(int madon, String ngaymua){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthaimua",1);
        contentValues.put("ngaymua", ngaymua);
        long check=sqLiteDatabase.update("DONHANG",contentValues,"madon=?",new String[]{String.valueOf(madon)});
        if(check==-1)
            return false;
        return true;
    }
}
