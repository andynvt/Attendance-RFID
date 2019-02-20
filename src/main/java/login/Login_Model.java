/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

/**
 *
 * @author chuna
 */
public class Login_Model {

    private String taiKhoan;
    private String matKhau;
    private boolean ghiNho;

    public Login_Model(String taiKhoan, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public Login_Model(String taiKhoan, String matKhau, boolean ghiNho) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ghiNho = ghiNho;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isGhiNho() {
        return ghiNho;
    }

    public void setGhiNho(boolean ghiNho) {
        this.ghiNho = ghiNho;
    }

}
