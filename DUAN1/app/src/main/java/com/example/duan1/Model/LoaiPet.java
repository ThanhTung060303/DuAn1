package com.example.duan1.Model;

public class LoaiPet {
    private String maloaipet,tenloaipet,loai;

    public LoaiPet(String maloaipet, String tenloaipet,String loai) {
        this.maloaipet = maloaipet;
        this.tenloaipet = tenloaipet;
        this.loai = loai;
    }

    public String getMaloaipet() {
        return maloaipet;
    }

    public void setMaloaipet(String maloaipet) {
        this.maloaipet = maloaipet;
    }

    public String getTenloaipet() {
        return tenloaipet;
    }

    public void setTenloaipet(String tenloaipet) {
        this.tenloaipet = tenloaipet;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
