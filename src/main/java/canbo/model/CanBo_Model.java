/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canbo.model;

/**
 *
 * @author chuna
 */
public class CanBo_Model {

    private String maCB;
    private String ten;
    private String email;
    private String boMon;
    private String khoa;
    private String maRFID;

    public CanBo_Model() {

    }

    public CanBo_Model(String maCB, String ten, String email, String boMon, String khoa, String maRFID) {
        this.maCB = maCB;
        this.ten = ten;
        this.email = email;
        this.boMon = boMon;
        this.khoa = khoa;
        this.maRFID = maRFID;
    }

    public CanBo_Model(String maCB, String ten, String email, String boMon, String khoa) {
        this.maCB = maCB;
        this.ten = ten;
        this.email = email;
        this.boMon = boMon;
        this.khoa = khoa;
    }

    public Object[] getObject() {
        try {
            Object[] canbo = {false, maCB, ten, email, boMon, khoa, maRFID};
            return canbo;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getMaCB() {
        return maCB;
    }

    public void setMaCB(String maCB) {
        this.maCB = maCB;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaRFID() {
        return maRFID;
    }

    public void setMaRFID(String maRFID) {
        this.maRFID = maRFID;
    }

    public String getBoMon() {
        return boMon;
    }

    public void setBoMon(String boMon) {
        this.boMon = boMon;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getString() {
        return maCB + '\n'
                + ten + '\n'
                + email + '\n'
                + boMon + '\n'
                + khoa + '\n'
                + maRFID + '\n';
    }
}
