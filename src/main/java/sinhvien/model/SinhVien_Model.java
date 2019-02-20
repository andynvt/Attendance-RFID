package sinhvien.model;

public class SinhVien_Model {

    private String maSV;
    private String ten;
    private String email;
    private String lop;
    private String nganh;
    private String khoa;
    private String nienKhoa;
    private String maRFID;

    public SinhVien_Model() {

    }

    public SinhVien_Model(String maSV, String ten, String email, String lop, String nganh, String khoa, String nienKhoa, String maRFID) {
        this.maSV = maSV;
        this.ten = ten;
        this.email = email;
        this.lop = lop;
        this.nganh = nganh;
        this.khoa = khoa;
        this.nienKhoa = nienKhoa;
        this.maRFID = maRFID;
    }

    public SinhVien_Model(String maSV, String ten, String email, String lop, String nganh, String khoa, String nienKhoa) {
        this.maSV = maSV;
        this.ten = ten;
        this.email = email;
        this.lop = lop;
        this.nganh = nganh;
        this.khoa = khoa;
        this.nienKhoa = nienKhoa;
    }

    public Object[] getObject() {
        try {
            Object[] sinhvien = {false, maSV, ten, email, lop, nganh, khoa, nienKhoa, maRFID};
            return sinhvien;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
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

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getNienKhoa() {
        return nienKhoa;
    }

    public void setNienKhoa(String nienKhoa) {
        this.nienKhoa = nienKhoa;
    }

    public String getMaRFID() {
        return maRFID;
    }

    public void setMaRFID(String maRFID) {
        this.maRFID = maRFID;
    }

    public String getString() {
        return maSV + '\n'
                + ten + '\n'
                + email + '\n'
                + lop + '\n'
                + nganh + '\n'
                + khoa + '\n'
                + nienKhoa + '\n'
                + maRFID + '\n';
    }
}
