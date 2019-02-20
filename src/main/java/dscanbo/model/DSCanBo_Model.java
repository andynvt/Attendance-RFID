/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dscanbo.model;

import java.sql.Time;

/**
 *
 * @author chuna
 */
public class DSCanBo_Model {

    private String macb;
    private String ten;
    private String mathe;
    private Time diemDanhVao;
    private Time diemDanhRa;
    private boolean diemDanhTuDong;

    public DSCanBo_Model() {
    }

    public DSCanBo_Model(String macb, String ten) {
        this.macb = macb;
        this.ten = ten;
    }

    public DSCanBo_Model(String macb, String ten, String mathe) {
        this.macb = macb;
        this.ten = ten;
        this.mathe = mathe;
    }

    public DSCanBo_Model(String macb, String ten, String mathe, Time diemDanhVao, Time diemDanhRa, boolean diemDanhTuDong) {
        this.macb = macb;
        this.ten = ten;
        this.mathe = mathe;
        this.diemDanhVao = diemDanhVao;
        this.diemDanhRa = diemDanhRa;
        this.diemDanhTuDong = diemDanhTuDong;
    }

    public String getMaCB() {
        return macb;
    }

    public void setMacb(String macb) {
        this.macb = macb;
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
