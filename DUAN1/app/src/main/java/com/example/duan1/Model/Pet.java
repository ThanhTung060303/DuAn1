package com.example.duan1.Model;

public class Pet {
    private String tenpet,hinhanh,maloai;
    private int mapet,gia,tuoi,trangthai;

    public Pet(int mapet, String tenpet,int tuoi, String hinhanh,  String maloai, int gia,int trangthai) {
        this.mapet = mapet;
        this.tenpet = tenpet;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.tuoi = tuoi;
        this.maloai = maloai;
        this.trangthai = trangthai;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getMapet() {
        return mapet;
    }

    public void setMapet(int mapet) {
        this.mapet = mapet;
    }

    public String getTenpet() {
        return tenpet;
    }

    public void setTenpet(String tenpet) {
        this.tenpet = tenpet;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }
}
