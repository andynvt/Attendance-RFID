/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrator.log;

import app.view.App_View;
import database.DatabaseHandler;
import other.custom.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.Login_Controller;
import login.Login_View;
import other.custom.Alert;

/**
 *
 * @author chuna
 */
public class Log_Controller {

    private final DatabaseHandler handler = Login_Controller.handler;
    private ResultSet rs;
    private final String table_LOG = "Log_history";
    private Log_Model model;

    public Log_Controller() {
    }

    public int getMaxID() {
        String sql = "SELECT MAX(id) FROM " + table_LOG;
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(Log_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    public Timestamp getThoiDiemDangNhap(int id) {

        String sql = "SELECT `thoidiemdangnhap` FROM " + table_LOG + " WHERE id = " + id;
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return rs.getTimestamp(1);
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(Log_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertTimeLogin(Log_Model lm) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        String sql = "INSERT INTO " + table_LOG
                + " (username, thoidiemdangnhap) VALUES ('"
                + lm.getTaiKhoan() + "','"
                + timestamp
                + "')";
        handler.execAction(sql);
    }

    public void updateTimeLogout() {
        int maxId = getMaxID();
        Timestamp timestampBD = getThoiDiemDangNhap(maxId);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        String sql = "UPDATE " + table_LOG + " SET "
                + "thoidiemdangnhap = '" + timestampBD + "', "
                + "thoidiemdangxuat = '" + timestamp + "' "
                + "WHERE id = " + maxId;
        handler.execAction(sql);
    }

    public void clearLog() {
        String sql = "DROP TABLE " + table_LOG;
        handler.execAction(sql);
        Alert.showSuccess(Login_View.addmin_View, "Đã xoá lịch sử.");
        App_View.getController().createTable_Log();
    }

    public ArrayList<Log_Model> load_TimeWork() {
        ArrayList<Log_Model> admin_Models = new ArrayList<>();
        try {
            String sql = "SELECT * FROM log_history";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                Timestamp login = rs.getTimestamp(3);
                Timestamp exit = rs.getTimestamp(4);
                admin_Models.add(new Log_Model(rs.getInt(1), rs.getString(2), login, exit));
            }
        } catch (SQLException | NullPointerException ex) {
            return null;
        }
        return admin_Models;
    }

    public static Object[][] array2Object(ArrayList<Log_Model> admin_Models) {
        Object[][] oses;
        try {
            oses = new Object[admin_Models.size()][4];
            for (int i = 0; i < admin_Models.size(); i++) {
                oses[i][0] = admin_Models.get(i).getId();
                oses[i][1] = admin_Models.get(i).getTaiKhoan();
                Object batdau, ketthuc;

                batdau = Util.formatTimestamp(admin_Models.get(i).getBatDau());
                ketthuc = Util.formatTimestamp(admin_Models.get(i).getKetThuc());
                oses[i][2] = batdau;
                oses[i][3] = ketthuc;
            }
        } catch (NullPointerException e) {
            Object[][] empty = {{"Không có lịch sử"}};
            return empty;
        }
        return oses;

    }

}
