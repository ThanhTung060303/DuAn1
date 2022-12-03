package com.example.duan1.Model;

public class DonHang {
    private int madon,manguoimua,masp,giatri;
    private String ngaymua,tenpet,tentk,hinhanh;

    public DonHang(int madon, int manguoimua, int masp) {
        this.madon = madon;
        this.manguoimua = manguoimua;
        this.masp = masp;
    }

    public DonHang(int madon, String tenpet,String hinhanh, String ngaymua, int giatri) {
        this.madon = madon;
        this.giatri = giatri;
        this.ngaymua = ngaymua;
        this.tenpet = tenpet;
        this.tentk = tentk;
        this.hinhanh = hinhanh;
    }

    public DonHang(int madon, String tenpet,String hinhanh, String tentk, String ngaymua, int giatri) {
        this.madon = madon;
        this.giatri = giatri;
        this.ngaymua = ngaymua;
        this.tenpet = tenpet;
        this.tentk = tentk;
        this.hinhanh = hinhanh;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getGiatri() {
        return giatri;
    }

    public void setGiatri(int giatri) {
        this.giatri = giatri;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public String getTenpet() {
        return tenpet;
    }

    public void setTenpet(String tenpet) {
        this.tenpet = tenpet;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public int getMadon() {
        return madon;
    }

    public void setMadon(int madon) {
        this.madon = madon;
    }

    public int getManguoimua() {
        return manguoimua;
    }

    public void setManguoimua(int manguoimua) {
        this.manguoimua = manguoimua;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

}
