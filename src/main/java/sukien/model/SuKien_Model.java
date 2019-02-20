/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author chuna
 */
public class SuKien_Model {

    private String maSK;
    private String tenSK;
    private Date ngayDienRa;//format dd/MM/yyyy
    private Time gioDiemDanhVao;//format hh:mm
    private Time gioKTDiemDanhVao;//format hh:mm
    private Time gioDiemDanhRa;//format hh:mm
    private Time gioKTDiemDanhRa;//format hh:mm
    private boolean diemDanh;

    public SuKien_Model() {
    }

    public Object[] getObject() {
        try {
            Object[] sukien = {false, maSK, tenSK, ngayDienRa, gioDiemDanhVao, gioDiemDanhRa, diemDanh, diemDanh};
            return sukien;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public SuKien_Model(String maSK, String tenSK, Date ngayDienRa, Time gioBatDau,
            Time gioKetThuc) {
        this.maSK = maSK;
        this.tenSK = tenSK;
        this.ngayDienRa = ngayDienRa;
        this.gioDiemDanhVao = gioBatDau;
        this.gioDiemDanhRa = gioKetThuc;
    }

    public SuKien_Model(String maSK, String tenSK, Date ngayDienRa, Time gioBatDau,
            Time gioKetThuc, boolean diemDanh) {
        this.maSK = maSK;
        this.tenSK = tenSK;
        this.ngayDienRa = ngayDienRa;
        this.gioDiemDanhVao = gioBatDau;
        this.gioDiemDanhRa = gioKetThuc;
        this.diemDanh = diemDanh;
    }

    public SuKien_Model(String maSK, String tenSK, Date ngayDienRa, Time gioDiemDanhVao,
            Time gioKTDiemDanhVao, Time gioDiemDanhRa, Time gioKTDiemDanhRa, boolean diemDanh) {
        this.maSK = maSK;
        this.tenSK = tenSK;
        this.ngayDienRa = ngayDienRa;
        this.gioDiemDanhVao = gioDiemDanhVao;
        this.gioKTDiemDanhVao = gioKTDiemDanhVao;
        this.gioDiemDanhRa = gioDiemDanhRa;
        this.gioKTDiemDanhRa = gioKTDiemDanhRa;
        this.diemDanh = diemDanh;
    }

    public String getMaSK() {
        return maSK;
    }

    public void setMaSK(String maSK) {
        this.maSK = maSK;
    }

    public String getTenSK() {
        return tenSK;
    }

    public void setTenSK(String tenSK) {
        this.tenSK = tenSK;
    }

    public Date getNgayDienRa() {
        return ngayDienRa;
    }

    public void setNgayDienRa(Date ngayDienRa) {
        this.ngayDienRa = ngayDienRa;
    }

    public Time getGioVao() {
        return gioDiemDanhVao;
    }

    public void setGioDiemDanhVao(Time gioDiemDanhVao) {
        this.gioDiemDanhVao = gioDiemDanhVao;
    }

    public Time getGioKTVao() {
        return gioKTDiemDanhVao;
    }

    public void setGioKTDiemDanhVao(Time gioKTDiemDanhVao) {
        this.gioKTDiemDanhVao = gioKTDiemDanhVao;
    }

    public Time getGioRa() {
        return gioDiemDanhRa;
    }

    public void setGioDiemDanhRa(Time gioDiemDanhRa) {
        this.gioDiemDanhRa = gioDiemDanhRa;
    }

    public Time getGioKTRa() {
        return gioKTDiemDanhRa;
    }

    public void setGioKTDiemDanhRa(Time gioKTDiemDanhRa) {
        this.gioKTDiemDanhRa = gioKTDiemDanhRa;
    }

    public boolean isDiemDanh() {
        return diemDanh;
    }

    public void setDiemDanh(boolean diemDanh) {
        this.diemDanh = diemDanh;
    }

    public String getString() {
        return maSK + '\n'
                + tenSK + '\n'
                + ngayDienRa + '\n'
                + gioDiemDanhVao + '\n'
                + gioDiemDanhRa + '\n';
    }

}
