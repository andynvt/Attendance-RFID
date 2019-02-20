/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrator.log;

import java.sql.Timestamp;

/**
 *
 * @author chuna
 */
public class Log_Model {

    private int id;
    private String taiKhoan;
    private Timestamp batDau;
    private Timestamp ketThuc;

    public Log_Model() {
    }

    public Log_Model(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public Log_Model(String taiKhoan, Timestamp batDau, Timestamp ketThuc) {
        this.taiKhoan = taiKhoan;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
    }

    public Log_Model(int id, String taiKhoan, Timestamp batDau, Timestamp ketThuc) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public Timestamp getBatDau() {
        return batDau;
    }

    public void setBatDau(Timestamp batDau) {
        this.batDau = batDau;
    }

    public Timestamp getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(Timestamp ketThuc) {
        this.ketThuc = ketThuc;
    }

}
