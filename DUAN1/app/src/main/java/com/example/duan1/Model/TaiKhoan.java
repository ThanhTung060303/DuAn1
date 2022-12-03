package com.example.duan1.Model;

public class TaiKhoan {
    private int matk,loaitk,solanmua;
    private String tentk, matkhau;

    public TaiKhoan(int matk, String tentk, int solanmua) {
        this.matk = matk;
        this.solanmua = solanmua;
        this.tentk = tentk;
    }

    public TaiKhoan(int matk, String tentk, String matkhau, int loaitk) {
        this.matk = matk;
        this.loaitk = loaitk;
        this.tentk = tentk;
        this.matkhau = matkhau;
    }

    public TaiKhoan(int matk, String tentk, String matkhau) {
        this.matk = matk;
        this.tentk = tentk;
        this.matkhau = matkhau;
    }

    public TaiKhoan(String tentk, String matkhau, int loaitk) {
        this.tentk = tentk;
        this.matkhau = matkhau;
        this.loaitk = loaitk;
    }

    public int getSolanmua() {
        return solanmua;
    }

    public void setSolanmua(int solanmua) {
        this.solanmua = solanmua;
    }

    public TaiKhoan(String tentk) {
        this.tentk = tentk;
    }

    public int getMatk() {
        return matk;
    }

    public void setMatk(int matk) {
        this.matk = matk;
    }

    public int getLoaitk() {
        return loaitk;
    }

    public void setLoaitk(int loaitk) {
        this.loaitk = loaitk;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
