/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import database.DatabaseHandler;
import app.model.App_Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import login.Login_Controller;

/**
 *
 * @author chuna
 */
public class App_Controller {

    private App_Model app;
    private ResultSet rs;
    private final String table_Nganh = "NGANH";
    private final String table_Lop = "LOP";
    private final String table_Khoa = "KHOA";
    private final String table_BoMon = "BOMON";
    private final String table_App = "app_data";
    private final String table_Log = "log_history";
    private final String table_CanBo = "canbo";
    private final String table_SinhVien = "sinhvien";
    private final String table_SuKien = "sukien";
    private final String table_DiemDanhCanBo = "canbothamgia";
    private final String table_DiemDanhSinhVien = "sinhvienthamgia";
    private final DatabaseHandler handler = Login_Controller.handler;

    public App_Controller() {
        boolean appData = createTable_AppData();
        boolean log = createTable_Log();
        boolean khoa = createTable_Khoa();
        boolean boMon = createTable_BoMon();
        boolean nganh = createTable_Nganh();
        boolean lop = createTable_Lop();
        boolean canBo = create_CanBo();
        boolean sinhVien = create_SinhVien();
        boolean suKien = createTable_SuKien();
        boolean cBThamGia = createTable_CanBoThamGia();
        boolean sVThamGia = createTable_SinhVienThamGia();
        if (appData && log && nganh && lop && khoa && boMon && canBo && sinhVien && cBThamGia && sVThamGia && suKien) {
            System.out.println("Tất cả dữ liệu đã sẵn sàng!");
        }
    }

    private boolean createTable_AppData() {
        String query = "CREATE TABLE " + table_App + " ( "
                + "id int(5) primary key, "
                + "function_name varchar(200), "
                + "left_menu int(10), "
                + "width int(10), "
                + "height int(10))";
        return handler.isExistTable(table_App, query);
    }

    public boolean createTable_Log() {
        String query = "CREATE TABLE " + table_Log + " ( "
                + "id int NOT NULL AUTO_INCREMENT , "
                + "username VARCHAR(30) NOT NULL , "
                + "thoidiemdangnhap TIMESTAMP NOT NULL , "
                + "thoidiemdangxuat TIMESTAMP NOT NULL , "
                + "PRIMARY KEY (id), "
                + "FOREIGN KEY (username) REFERENCES TAIKHOAN(username)"
                + ")";
        return handler.isExistTable(table_Log, query);
    }

    private boolean createTable_Nganh() {
        String query = "CREATE TABLE NGANH( "
                + "    maNganh VARCHAR(15) PRIMARY KEY, "
                + "    Nganh VARCHAR(50) UNIQUE NOT NULL,"
                + "    maKhoa VARCHAR(15) NOT NULL,"
                + "    CONSTRAINT fk_maKhoa_Nganh FOREIGN KEY(maKhoa) REFERENCES KHOA(maKhoa) ON UPDATE CASCADE);";
        return handler.isExistTable(table_Nganh, query);
    }

    private boolean createTable_Lop() {
        String query = "CREATE TABLE LOP( "
                + "    maLop VARCHAR(15) PRIMARY KEY, "
                + "    Lop VARCHAR(50) UNIQUE NOT NULL, "
                + "    maNganh VARCHAR(15) NOT NULL,"
                + "    CONSTRAINT fk_maNganh_Lop FOREIGN KEY(maNganh) REFERENCES NGANH(maNganh) ON UPDATE CASCADE);";
        return handler.isExistTable(table_Lop, query);
    }

    private boolean createTable_Khoa() {
        String query = "CREATE TABLE KHOA( "
                + "    maKhoa VARCHAR(15) PRIMARY KEY, "
                + "    Khoa VARCHAR(50) UNIQUE NOT NULL "
                + ");";
        return handler.isExistTable(table_Khoa, query);
    }

    private boolean createTable_BoMon() {
        String query = "CREATE TABLE BOMON( "
                + "    maBoMon VARCHAR(15) PRIMARY KEY, "
                + "    BoMon VARCHAR(50) UNIQUE NOT NULL, "
                + "    maKhoa VARCHAR(15) NOT NULL,"
                + "    CONSTRAINT fk_maKhoa_BM FOREIGN KEY(maKhoa) REFERENCES KHOA(maKhoa) ON UPDATE CASCADE);";
        return handler.isExistTable(table_BoMon, query);
    }

    private boolean create_CanBo() {
        String query = "CREATE TABLE canbo( "
                + "    maCB VARCHAR(15) PRIMARY KEY, "
                + "    ten VARCHAR(50) NOT NULL, "
                + "    email VARCHAR(50) NOT NULL, "
                + "    maBoMon VARCHAR(15) NOT NULL, "
                + "    maKhoa VARCHAR(15) NOT NULL, "
                + "    maRFID VARCHAR(15) DEFAULT 'Chưa có', "
                + "    CONSTRAINT fk_maBoMon_CB FOREIGN KEY(maBoMon) REFERENCES BOMON(maBoMon) ON UPDATE CASCADE , "
                + "    CONSTRAINT fk_maKhoa_CB FOREIGN KEY(maKhoa) REFERENCES KHOA(maKhoa) ON UPDATE CASCADE "
                + ");";
        return handler.isExistTable(table_CanBo, query);
    }

    private boolean create_SinhVien() {
        String query = "CREATE TABLE sinhvien( "
                + "    maSV VARCHAR(15) PRIMARY KEY, "
                + "    ten VARCHAR(50) NOT NULL, "
                + "    email VARCHAR(50) NOT NULL, "
                + "    maLop VARCHAR(15) NOT NULL, "
                + "    maNganh VARCHAR(15) NOT NULL, "
                + "    maKhoa VARCHAR(15) NOT NULL, "
                + "    nienKhoa VARCHAR(15) NOT NULL, "
                + "    maRFID VARCHAR(15) DEFAULT 'Chưa có', "
                + "    CONSTRAINT fk_maLop_SV FOREIGN KEY(maLop) REFERENCES LOP(maLop)ON UPDATE CASCADE, "
                + "    CONSTRAINT fk_maNganh_SV FOREIGN KEY(maNganh) REFERENCES NGANH(maNganh) ON UPDATE CASCADE, "
                + "    CONSTRAINT fk_maKhoa_SV FOREIGN KEY(maKhoa) REFERENCES KHOA(maKhoa) ON UPDATE CASCADE"
                + ");";
        return handler.isExistTable(table_SinhVien, query);
    }

    private boolean createTable_SuKien() {
        String query = "CREATE TABLE sukien( "
                + "    maSK VARCHAR(15) PRIMARY KEY, "
                + "    tenSK VARCHAR(50) NOT NULL, "
                + "    ngayDienRa DATE NOT NULL, "
                + "    gioVao TIME NOT NULL, "
                + "    gioKTVao TIME DEFAULT NULL, "
                + "    gioRa TIME NOT NULL, "
                + "    gioKTRa TIME DEFAULT NULL, "
                + "    diemDanh BOOLEAN DEFAULT FALSE, "
                + "    CHECK(gioVao < gioRa  "
                + "          AND gioKTVao > gioVao  "
                + "          AND gioKTVao < gioRa  "
                + "          AND gioKTRa > gioRa) "
                + ");";
        return handler.isExistTable(table_SuKien, query);
    }

    private boolean createTable_CanBoThamGia() {
        String query = "CREATE TABLE canbothamgia( "
                + "    maSK VARCHAR(15) NOT NULL, "
                + "    maCB VARCHAR(15) NOT NULL, "
                + "    thoiGianVao TIME DEFAULT NULL, "
                + "    thoiGianRa TIME DEFAULT NULL, "
                + "    diemdanhtudong BOOLEAN default false, "
                + "    PRIMARY KEY(maSK, maCB),     "
                + "    CONSTRAINT fk_maSK_CB FOREIGN KEY(maSK) REFERENCES sukien(maSK) ON UPDATE CASCADE, "
                + "    CONSTRAINT fk_maCB_CB FOREIGN KEY(maCB) REFERENCES canbo(maCB) ON UPDATE CASCADE"
                + ");";
        return handler.isExistTable(table_DiemDanhCanBo, query);
    }

    private boolean createTable_SinhVienThamGia() {
        String query = "CREATE TABLE sinhvienthamgia( "
                + "    maSK VARCHAR(15) NOT NULL, "
                + "    maSV VARCHAR(50) NOT NULL, "
                + "    thoiGianVao TIME DEFAULT NULL, "
                + "    thoiGianRa TIME DEFAULT NULL, "
                + "    diemdanhtudong BOOLEAN default false, "
                + "    PRIMARY KEY(maSK, maSV), "
                + "    CONSTRAINT fk_maSK_SV FOREIGN KEY(maSK) REFERENCES sukien(maSK) ON UPDATE CASCADE, "
                + "    CONSTRAINT fk_maSV_SV FOREIGN KEY(maSV) REFERENCES sinhvien(maSV) ON UPDATE CASCADE "
                + ");";
        return handler.isExistTable(table_DiemDanhSinhVien, query);
    }

    public App_Model getState() {
        try {
            String sql = "SELECT * FROM " + table_App;
            rs = handler.execQuery(sql);
            while (rs.next()) {
                app = new App_Model(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
            }
        } catch (SQLException | NullPointerException ex) {
            app = new App_Model(1, "Trang chủ", 250, 1366, 768);
        }
        return app;
    }

    public boolean deleteState() {
        if (getState() != null) {
            String sql1 = "DELETE FROM " + table_App;
            return handler.execAction(sql1);
        }
        return false;
    }

    public boolean updateState(App_Model home) {
        if (deleteState()) {
            String sql = "INSERT INTO " + table_App + "(id, function_name, left_menu, width, height)"
                    + " VALUES(" + home.getId() + ",'" + home.getName() + "', "
                    + home.getLeftMenu() + ", " + home.getWidth() + ", " + home.getHeight() + ")";
            return handler.execAction(sql);
        }
        return false;
    }

    public String getMaNganh(String nganh) {
        String sql = "SELECT `maNganh`  FROM `nganh` WHERE nganh = '" + nganh + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public String getNganh(String maNganh) {
        String sql = "SELECT `nganh`  FROM `nganh` WHERE nganh = '" + maNganh + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public ArrayList<String> getNganh() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT `nganh`  FROM `nganh` ";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public String getMaLop(String lop) {
        String sql = "SELECT `maLop`  FROM `lop` WHERE lop = '" + lop + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public String getLop(String maLop) {
        String sql = "SELECT `lop`  FROM `lop` WHERE malop = '" + maLop + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public ArrayList<String> getLop() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT `lop`  FROM `lop` ";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public String getMaBoMon(String boMon) {
        String sql = "SELECT `maBoMon`  FROM `bomon` WHERE boMon = '" + boMon + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public String getBoMon(String maBoMon) {
        String sql = "SELECT `boMon`  FROM `bomon` WHERE mabomon = '" + maBoMon + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public ArrayList<String> getBoMon() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT `bomon`  FROM `bomon` ";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public String getMaKhoa(String khoa) {
        String sql = "SELECT `maKhoa`  FROM `khoa` WHERE khoa = '" + khoa + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public String getKhoa(String maKhoa) {
        String sql = "SELECT `khoa`  FROM `khoa` WHERE maKhoa = '" + maKhoa + "'";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public ArrayList<String> getKhoa() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT `khoa`  FROM `khoa` ";
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public String[] arrayList2String(ArrayList<String> arrayList) {
        String[] ses = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            ses[i] = arrayList.get(i);
        }
        return ses;
    }
}
