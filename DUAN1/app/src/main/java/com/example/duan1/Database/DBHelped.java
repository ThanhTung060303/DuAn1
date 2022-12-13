package com.example.duan1.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelped extends SQLiteOpenHelper {

    public DBHelped(Context c){
        super(c,"QUANLYTHUCUNG1",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbTaiKhoan = "CREATE TABLE TAIKHOAN(matk integer primary key autoincrement, tentk text, matkhau text,loaitk integer)";
        // trạng thái 0 là chưa bán, 1 là đang xác nhận, 2 là đã bán
        db.execSQL(dbTaiKhoan);

        String dbLoaiPet = "CREATE TABLE LOAIPET (maloaipet text primary key ,tenloaipet text, loai text)";
        db.execSQL(dbLoaiPet);

        String dbPet = "CREATE TABLE PET(mapet integer primary key autoincrement, tenpet text,tuoi integer,hinhanh text,maloai text references LOAIPET(maloaipet),gia integer,trangthai integer )";
        db.execSQL(dbPet);

        String dbDonHang = "CREATE TABLE DONHANG(madon integer primary key autoincrement,masp integer references PET(mapet), manguoimua integer references TAIKHOAN(matk),ngaymua text,trangthaimua integer)";
        //0 là chưa bán, 1 là đã bán
        db.execSQL(dbDonHang);
        //data mẫu
        // 0 là admin , 1 là người dùng
        db.execSQL("INSERT INTO TAIKHOAN VALUES (1, 'Admin01','12345',0),(2,'user01','54321',1),(3,'user02','54321',1),(4,'user03','54321',1),(5,'user04','54321',1),(6,'user05','54321',1)");
        db.execSQL("INSERT INTO LOAIPET VALUES ('C01', 'Chó Nhật','chó'),('C02', 'Chó Alaska','chó'),('C03', 'Chó Chihuahua','chó'),('C04', 'Chó Nhật','chó'),('C05', 'Chó Lạp Xưởng','chó'),('C06', 'Chó Poolde','chó'),('C07', 'Chó Pug','chó'),('C08', 'Chó Phốc Sốc','chó'),('C09', 'Chó Shiba','chó')");
        db.execSQL("INSERT INTO LOAIPET VALUES ('M01', 'Mèo Ba Tư','mèo'),('M02', 'Mèo Ai Cập','mèo'),('M03', 'Mèo Anh lông dài','mèo'),('M04', 'Mèo Anh lông ngắn','mèo'),('M05', 'Mèo Xiêm','mèo'),('M06', 'Mèo Ragdoll','mèo')");
        //
        db.execSQL("INSERT INTO PET VALUES (1,'Lu Lu',10, 'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','C02',500000,0)");
        db.execSQL("INSERT INTO PET VALUES (2,'Xám',12,'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','C04',700000,0)");
        db.execSQL("INSERT INTO PET VALUES (3,'Mực',8,'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','C07',800000,1)");
        db.execSQL("INSERT INTO PET VALUES (4,'Lu Ly',10, 'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','C08',800000,1)");
        db.execSQL("INSERT INTO PET VALUES (5,'Rich',12,'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','C02',700000,2)");
        db.execSQL("INSERT INTO PET VALUES (6,'Sen',8,'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','C09',500000,0)");
        db.execSQL("INSERT INTO PET VALUES (7,'Krixi',10, 'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','M02',500000,0)");
        db.execSQL("INSERT INTO PET VALUES (8,'Tama',12,'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','M05',1900000,0)");
        db.execSQL("INSERT INTO PET VALUES (9,'Shizuka',8,'https://firebasestorage.googleapis.com/v0/b/duan1-15f6d.appspot.com/o/image%2F*c62d8a70-f88d-49c0-8adb-a18dcb660f71?alt=media&token=661677f1-4d5e-481a-b5c2-15f28805104f','M06',1500000,0)");

        //
        db.execSQL("INSERT INTO DONHANG VALUES(1,3,3,'chưa mua',0),(2,4,3,'chưa mua',0),(3,5,4,'19/03/2022',1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i!=i1){
            db.execSQL("DROP TABLE IF EXISTS TAIKHOAN");
            db.execSQL("DROP TABLE IF EXISTS LOAIPET");
            db.execSQL("DROP TABLE IF EXISTS PET");
            db.execSQL("DROP TABLE IF EXISTS DONHANG");
            onCreate(db);
        }
    }
}

