package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.Database.DBHelped;
import com.example.duan1.Model.TaiKhoan;

import java.util.ArrayList;

public class TaiKhoanDAO {
    DBHelped dbHelped ;
    SharedPreferences preferences;
    public TaiKhoanDAO(Context c){
        dbHelped = new DBHelped(c);
        preferences = c.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
    }

    public ArrayList<TaiKhoan> getDSTaiKhoan(){
        ArrayList<TaiKhoan> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TAIKHOAN",null);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new TaiKhoan(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    //
   public ArrayList<TaiKhoan> getDSKhachHang(){
        ArrayList<TaiKhoan> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT A.MATK,A.TENTK, COUNT(B.MANGUOIMUA) FROM TAIKHOAN A LEFT JOIN DONHANG B ON A.MATK=B.MANGUOIMUA WHERE A.LOAITK = 1 GROUP BY A.MATK",null);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new TaiKhoan(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }


    //
    public ArrayList<String> getDSTenTaiKhoan(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT TENTK FROM TAIKHOAN",null);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                String x = new String(cursor.getString(0));
                list.add(x);
            }while (cursor.moveToNext());
        }
        return list;
    }
    // check đăng nhập
    public Boolean checkDangNhap(String tentk, String matkhau){
        SQLiteDatabase sqLiteDatabase = dbHelped.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TAIKHOAN where tentk = ? and matkhau = ?", new String[]{tentk,matkhau});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("matk",cursor.getInt(0));
            editor.putString("tentk",cursor.getString(1));
            editor.putString("matkhau",cursor.getString(2));
            editor.putInt("loaitk",cursor.getInt(3));
            editor.commit();
            return true;
        }else{
            return false;
        }
    }
    //
    public boolean themTaiKhoan( String tentk,String matkhau,int loaitk){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from TAIKHOAN where tentk = ? and matkhau = ?", new String[]{tentk,matkhau});
       /* if(cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("matk",cursor.getInt(0));
            editor.putString("tentk",cursor.getString(1));
            editor.putString("matkhau",cursor.getString(2));
            editor.putInt("loaitk",cursor.getInt(3));
            editor.commit();
        }

        */
        ContentValues contentValues = new ContentValues();
        contentValues.put("tentk", tentk);
        contentValues.put("matkhau", matkhau);
        contentValues.put("loaitk", loaitk);
        long check = sqLiteDatabase.insert("TAIKHOAN", null, contentValues);
        if(check==-1)
            return false;
        return true;
    }

    public int capNhatMatKhau(int matk,String oldpass, String newpass){
        SQLiteDatabase sqLiteDatabase = dbHelped.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TAIKHOAN WHERE matk = ? and matkhau=?", new String[]{String.valueOf(matk),oldpass});
        if(cursor.getCount()>0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau",newpass);
            long check = sqLiteDatabase.update("TAIKHOAN",contentValues,"matk=?", new String[]{String.valueOf(matk)});
            if(check ==-1) {
                return -1;
            }else {
                return 1;
            }
        }
        return 0;
    }
}
