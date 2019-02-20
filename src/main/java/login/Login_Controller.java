/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import database.DatabaseHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import other.custom.Alert;

/**
 *
 * @author chuna
 */
public class Login_Controller {

    public static DatabaseHandler handler = new DatabaseHandler();
    private ResultSet rs;
    private final String table_TAIKHOAN = "TAIKHOAN";
    private boolean checkData = false;

    public Login_Controller() {
        initTaiKhoan();
        createDefaultAccount();
    }

    private void initTaiKhoan() {
        String query = "CREATE TABLE " + table_TAIKHOAN + " ( "
                + "username VARCHAR(30) NOT NULL , "
                + "password VARCHAR(64) NOT NULL , "
                + "ghinho BOOLEAN NOT NULL DEFAULT FALSE , "
                + "PRIMARY KEY (username)) ENGINE = InnoDB;";
        checkData = handler.isExistTable(table_TAIKHOAN, query);
    }

    private void createDefaultAccount() {
        if (!isDefaultExist()) {
            String sql = "INSERT INTO " + table_TAIKHOAN + " VALUES ('admin', 'admin','0');";
            try {
                handler.execAction(sql);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public boolean checkLogin(Login_Model model) {
        try {
            String sql = "SELECT username, password FROM " + table_TAIKHOAN + " WHERE "
                    + "username='" + model.getTaiKhoan() + "' AND "
                    + "password='" + model.getMatKhau() + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    public boolean isDefaultExist() {
        try {
            String sql = "SELECT password FROM " + table_TAIKHOAN
                    + " WHERE username = 'admin'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public String getMatKhau(Login_Model am) {
        String sql = "SELECT password FROM " + table_TAIKHOAN
                + " WHERE username = '" + am.getTaiKhoan() + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public boolean checkMatKhau(String pass) {
        String sql = "SELECT password FROM " + table_TAIKHOAN
                + " WHERE password = '" + pass + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public Login_Model getTaiKhoanGhiNho() {
        if (checkData) {
            String sql = "SELECT username, password FROM " + table_TAIKHOAN
                    + " WHERE ghinho = '1'";
            try {
                rs = handler.execQuery(sql);
                while (rs.next()) {
                    return new Login_Model(rs.getString("username"), rs.getString("password"));
                }
            } catch (SQLException e) {
                return null;
            }
        }
        return null;
    }

    public boolean isGhiNho(Login_Model am) {
        if (checkData) {
            String sql = "SELECT ghinho FROM " + table_TAIKHOAN
                    + " WHERE username = '" + am.getTaiKhoan() + "'";
            try {
                rs = handler.execQuery(sql);
                while (rs.next()) {
                    return rs.getInt(1) == 1;
                }
            } catch (SQLException e) {
                return false;
            } catch (NullPointerException e) {
                Alert.showWarning(null, "Tài khoản hoặc mật khẩu không hợp lệ!");
            }
        }
        return false;
    }

    public void resetTaiKhoan() {
        String sql = "UPDATE `taikhoan` SET `ghinho`='0' WHERE 1";
        handler.execAction(sql);
    }

    public void setGhiNho(Login_Model lm) {
        resetTaiKhoan();
        int ghiNho = lm.isGhiNho() ? 1 : 0;
        String sql = "UPDATE " + table_TAIKHOAN + " SET "
                + "ghinho = '" + ghiNho + "' "
                + "WHERE  username = '" + lm.getTaiKhoan() + "'";
        if (!handler.execAction(sql)) {
//            Alert.showWarning(null, "Tài khoản hoặc mật khẩu không hợp lệ!");
        }
    }
}
