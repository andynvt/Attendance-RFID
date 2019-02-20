/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssinhvien.model;

import java.sql.Time;

/**
 *
 * @author chuna
 */
public class DSSinhVien_Model {

    private String masv;
    private String ten;
    private String mathe;
    private Time diemDanhVao;
    private Time diemDanhRa;
    private boolean diemDanhTuDong;

    public DSSinhVien_Model() {
    }

    public DSSinhVien_Model(String masv, String ten) {
        this.masv = masv;
        this.ten = ten;
    }

    public DSSinhVien_Model(String macb, String ten, String mathe) {
        this.masv = macb;
        this.ten = ten;
        this.mathe = mathe;
    }

    public DSSinhVien_Model(String masv, String ten, String mathe, Time diemDanhVao, Time diemDanhRa, boolean diemDanhTuDong) {
        this.masv = masv;
        this.ten = ten;
        this.mathe = mathe;
        this.diemDanhVao = diemDanhVao;
        this.diemDanhRa = diemDanhRa;
        this.diemDanhTuDong = diemDanhTuDong;
    }

    public String getMaSV() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMaRFID() {
        return mathe;
    }

    public void setMathe(String mathe) {
        this.mathe = mathe;
    }

    public Time getDiemDanhVao() {
        return diemDanhVao;
    }

    public void setDiemDanhVao(Time diemDanhVao) {
        this.diemDanhVao = diemDanhVao;
    }

    public Time getDiemDanhRa() {
        return diemDanhRa;
    }

    public void setDiemDanhRa(Time diemDanhRa) {
        this.diemDanhRa = diemDanhRa;
    }

    public boolean isDiemDanhTuDong() {
        return diemDanhTuDong;
    }

    public void setDiemDanhTuDong(boolean kieudiemdanh) {
        this.diemDanhTuDong = kieudiemdanh;
    }

}
