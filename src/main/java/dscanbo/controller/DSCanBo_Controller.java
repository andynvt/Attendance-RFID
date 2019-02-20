/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dscanbo.controller;

import canbo.model.CanBo_Model;
import database.DatabaseHandler;
import other.custom.Util;
import sukien.view.SuKien_ListSK;
import dscanbo.model.DSCanBo_Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.Login_Controller;
import thongke.ThongKe_Controller;

/**
 *
 * @author chuna
 */
public class DSCanBo_Controller {

    private final String table = "canbothamgia";
    private static final DatabaseHandler handler = Login_Controller.handler;
    private ResultSet rs;
    private static ArrayList<DSCanBo_Model> add_Failed = new ArrayList<>();
    private static ArrayList<DSCanBo_Model> update_Failed = new ArrayList<>();
    private static ArrayList<DSCanBo_Model> delete_Failed = new ArrayList<>();

    public DSCanBo_Controller() {
    }

    public static ArrayList<DSCanBo_Model> getAdd_Failed() {
        return add_Failed;
    }

    public static ArrayList<DSCanBo_Model> getUpdate_Failed() {
        return update_Failed;
    }

    public static ArrayList<DSCanBo_Model> getDelete_Failed() {
        return delete_Failed;
    }

    public static Object[][] array2DSCanBo(ArrayList<DSCanBo_Model> list) {
        try {
            Object[][] data = new Object[list.size()][7];
            for (int i = 0; i < list.size(); i++) {
                data[i][0] = false;
                data[i][1] = list.get(i).getMaCB();
                data[i][2] = list.get(i).getTen();
                data[i][3] = list.get(i).getMaRFID();
                data[i][4] = list.get(i).getDiemDanhVao();
                data[i][5] = list.get(i).getDiemDanhRa();
                data[i][6] = list.get(i).isDiemDanhTuDong();
            }
            return data;
        } catch (NullPointerException e) {
            Object[][] empty = {{"Không có dữ liệu"}};
            return empty;
        }
    }

    public int getRowCount() {
        try {
            String sql = "SELECT COUNT(*) FROM " + table;
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | NullPointerException ex) {
        }
        return 0;
    }

    public ArrayList<DSCanBo_Model> load_CanBoVangKoDK(String maSK) {
        ArrayList<DSCanBo_Model> canBoThamGia_Models = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT canbo.macb, ten, maRFID,"
                    + " canbothamgia.thoigianvao, canbothamgia.thoigianra,"
                    + " canbothamgia.diemdanhtudong "
                    + " FROM canbo "
                    + " INNER JOIN canbothamgia ON canbo.macb = canbothamgia.macb "
                    + " INNER JOIN sukien ON canbothamgia.maSK = sukien.maSK "
                    + " WHERE sukien.batbuoc = '1' AND canbothamgia.thoigianvao IS NULL AND sukien.maSK = '" + maSK + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                canBoThamGia_Models.add(new DSCanBo_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getBoolean(6)
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return canBoThamGia_Models;
    }

    public ArrayList<DSCanBo_Model> load_CanBoVangCoDK(String mask) {
        ArrayList<DSCanBo_Model> canBoThamGia_Models = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT canbothamgia.macb, canbo.ten, canbo.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM canbothamgia INNER JOIN canbo ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.maSK = '" + mask + "' AND thoigianvao IS NULL";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                canBoThamGia_Models.add(new DSCanBo_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getBoolean(6)
                ));
            }
        } catch (SQLException ex) {

        }
        return canBoThamGia_Models;
    }

    public ArrayList<DSCanBo_Model> load_CanBoCoMat(String mask) {
        ArrayList<DSCanBo_Model> canBoThamGia_Models = new ArrayList<>();
        try {
            String sql = "SELECT  canbothamgia.macb, canbo.ten, canbo.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM canbothamgia INNER JOIN canbo ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.maSK = '" + mask + "' and thoigianvao  IS NOT NULL and thoigianra IS NOT NULL";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                canBoThamGia_Models.add(new DSCanBo_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getBoolean(6)
                ));
            }
        } catch (SQLException | NullPointerException ex) {

        }
        return canBoThamGia_Models;
    }

    public ArrayList<DSCanBo_Model> load_TatCaCanBo(String mask) {
        ArrayList<DSCanBo_Model> canBoThamGia_Models = new ArrayList<>();
        try {
            String sql = "SELECT canbothamgia.macb, canbo.ten, canbo.maRFID,"
                    + " thoigianvao,thoigianra, diemdanhtudong FROM canbothamgia "
                    + "INNER JOIN canbo ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.mask = '" + mask + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                canBoThamGia_Models.add(new DSCanBo_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getBoolean(6)
                ));
            }
        } catch (SQLException ex) {
        }
        return canBoThamGia_Models;
    }

    public boolean check_CanBoThamGia(String macb, String mask) {
        try {
            String sql = "SELECT macb FROM `canbothamgia` "
                    + "WHERE canbothamgia.mask = '" + mask + "' "
                    + "AND canbothamgia.macb = '" + macb + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException | NullPointerException e) {
            return false;
        }
        return false;
    }

    public ArrayList<DSCanBo_Model> load_CanBoNoRFID(String mask) {
        ArrayList<DSCanBo_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT canbothamgia.macb, canbo.ten, canbo.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM `canbothamgia` INNER JOIN canbo ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.maSK = '" + mask + "' AND canbo.maRFID =''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String macb = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSCanBo_Model(macb, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public ArrayList<DSCanBo_Model> load_CanBoHaveRFID(String mask) {
        ArrayList<DSCanBo_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT canbothamgia.macb, canbo.ten, canbo.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM `canbothamgia` INNER JOIN canbo ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.maSK = '" + mask + "'  AND canbo.maRFID <>''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String macb = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSCanBo_Model(macb, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public ArrayList<DSCanBo_Model> load_CanBoDiTre(String mask) {
        ArrayList<DSCanBo_Model> list = new ArrayList<>();
        Time timeLimit = SuKien_ListSK.getController().getGioKetThucVao(mask);
        try {
            String sql = "SELECT canbothamgia.macb, canbo.ten, canbo.maRFID, "
                    + "thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM `canbothamgia` INNER JOIN canbo "
                    + "ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.maSK = '" + mask + "'"
                    + " AND thoigianra IS NOT NULL"
                    + " AND thoigianvao > '" + timeLimit + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String macb = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSCanBo_Model(macb, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public CanBo_Model getCanBoByRFID(String maRFID, String maSK) {
        CanBo_Model canBo_Model = null;
        try {
            String sql = "SELECT canbo.macb, canbo.ten,canbo.email, bomon.BoMon, khoa.Khoa, canbo.maRFID "
                    + "FROM canbo, bomon, khoa "
                    + "WHERE canbo.macb = (SELECT macb FROM canbothamgia WHERE maSK = '" + maSK + "' "
                    + "AND canbothamgia.macb = (SELECT canbo.macb FROM canbo WHERE maRFID = '" + maRFID + "')) "
                    + "AND bomon.maBoMon=canbo.maBoMon AND khoa.maKhoa=canbo.maKhoa";
            rs = handler.execQuery(sql);
            while (rs.next()) {

                canBo_Model = new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                );
            }
        } catch (SQLException | NullPointerException ex) {
            canBo_Model = null;
        }
        return canBo_Model;
    }

    public CanBo_Model getCanBoByMaCB(String macb, String maSK) {
        CanBo_Model canBo_Model = null;
        try {
            String sql = "SELECT canbo.macb, canbo.ten,canbo.email, bomon.BoMon, khoa.Khoa, canbo.maRFID "
                    + "FROM canbo, bomon, khoa "
                    + "WHERE canbo.macb = (SELECT macb FROM canbothamgia WHERE maSK = '" + maSK + "' "
                    + "AND canbothamgia.macb = (SELECT canbo.macb FROM canbo WHERE macb = '" + macb + "')) "
                    + "AND bomon.maBoMon=canbo.maBoMon AND khoa.maKhoa=canbo.maKhoa";
            rs = handler.execQuery(sql);
            while (rs.next()) {

                canBo_Model = new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                );
            }
        } catch (SQLException | NullPointerException ex) {
        }
        return canBo_Model;
    }

    public ArrayList<DSCanBo_Model> load_CanBoDDThuCong(String maSK) {
        ArrayList<DSCanBo_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT  canbothamgia.macb, canbo.ten, canbo.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM `canbothamgia` INNER JOIN canbo ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.maSK = '" + maSK + "'  AND canbothamgia.diemdanhtudong = FALSE";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String macb = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSCanBo_Model(macb, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public ArrayList<DSCanBo_Model> load_CanBoDDTuDong(String maSK) {
        ArrayList<DSCanBo_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT  canbothamgia.macb, canbo.ten, canbo.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM `canbothamgia` INNER JOIN canbo ON canbothamgia.macb = canbo.macb "
                    + "WHERE canbothamgia.maSK = '" + maSK + "'  AND canbothamgia.diemdanhtudong = TRUE";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String macb = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSCanBo_Model(macb, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public int countCanBoDiTre(String maSK) {
        return load_CanBoDiTre(maSK).size();
    }

    public int countCanBoDDMay(String maSK) {
        return load_CanBoDDTuDong(maSK).size();
    }

    public int countCanBoDDThuCong(String maSK) {
        return load_CanBoDDThuCong(maSK).size();
    }

    public int countCanBoChuaCoThe(String maSK) {
        return load_CanBoNoRFID(maSK).size();
    }

    public int countCanBoCoThe(String maSK) {
        return load_CanBoHaveRFID(maSK).size();
    }

    public int countCBVangCoDK(String mask) {
        return load_CanBoVangCoDK(mask).size();
    }

    public int countCanBoVangKoDK(String maSK) {
        return load_CanBoVangKoDK(maSK).size();
    }

    public int countCanBoCoMat(String mask) {
        return load_CanBoCoMat(mask).size();
    }

    public int countTatCaTrongDS(String mask) {
        return load_TatCaCanBo(mask).size();
    }

    public Time get_ThoiDiemVao(String maRFID, String mask) {
        String sql = "SELECT `thoigianvao` FROM `canbothamgia` "
                + "WHERE canbothamgia.macb = (SELECT macb FROM canbo "
                + "WHERE canbo.maRFID = '" + maRFID + "')"
                + "AND canbothamgia.mask = '" + mask + "'";
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time get_ThoiDiemRa(String maRFID, String mask) {
        String sql = "SELECT `thoigianra` FROM `canbothamgia` "
                + "WHERE canbothamgia.macb = (SELECT macb FROM canbo "
                + "WHERE canbo.maRFID = '" + maRFID + "')"
                + "AND canbothamgia.mask = '" + mask + "'";
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time get_ThoiDiemVaoByMaCB(String macb, String mask) {
        String sql = "SELECT `thoigianvao` FROM `canbothamgia` "
                + "WHERE canbothamgia.macb = '" + macb + "'"
                + "AND canbothamgia.mask = '" + mask + "'";
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time get_ThoiDiemRaByMaCB(String macb, String mask) {
        String sql = "SELECT `thoigianra` FROM `canbothamgia` "
                + "WHERE canbothamgia.macb = '" + macb + "'"
                + "AND canbothamgia.mask = '" + mask + "'";
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public boolean isDiemDanhVao(String maRFID, String mask) {
        return get_ThoiDiemVao(maRFID, mask) != null;
    }

    public boolean isDiemDanhRa(String maRFID, String mask) {
        return get_ThoiDiemRa(maRFID, mask) != null;
    }

    public boolean isDiemDanhVaoByMaCB(String maRFID, String mask) {
        return get_ThoiDiemVaoByMaCB(maRFID, mask) != null;
    }

    public boolean isDiemDanhRaByMaCb(String maRFID, String mask) {
        return get_ThoiDiemRaByMaCB(maRFID, mask) != null;
    }

    public boolean update_DiemDanhVao(Time Time, String macb, String mask) {
        String sql = "UPDATE `canbothamgia` SET `thoigianvao` = '" + Time + "' "
                + "WHERE `canbothamgia`.`macb` = '" + macb + "' AND canbothamgia.mask = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean update_DiemDanhRa(Time Time, String macb, String mask) {
        String sql = "UPDATE `canbothamgia` SET `thoigianra` = '" + Time + "' "
                + "WHERE `canbothamgia`.`macb` = '" + macb + "' AND canbothamgia.mask = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean setDiemDanhTuDong(boolean b, String macb, String mask) {
        String sql = "UPDATE canbothamgia SET diemdanhtudong = " + b + " "
                + "WHERE `canbothamgia`.`macb` = '" + macb + "' AND canbothamgia.mask = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean huy_DuLieuDiemDanh(String macb, String mask) {
        String sql = "UPDATE `canbothamgia` SET `thoigianvao` = NULL, `thoigianra` = NULL "
                + "WHERE `canbothamgia`.`maSK` = '" + mask + "' AND `canbothamgia`.`macb` = '" + macb + "'";
        return handler.execAction(sql);
    }

    public boolean huy_TatCaDLDiemDanh(String mask) {
        String sql = "UPDATE `sinhvienthamgia` SET `thoiGianVao` = NULL,`thoigianra` = NULL  "
                + "WHERE `sinhvienthamgia`.`maSK` = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean add_canBoThamGia(DSCanBo_Model cb_Model, String mask) {
        String sql = "INSERT INTO " + table + " (`maSK`, `macb`)"
                + " VALUES ('" + mask + "', '" + cb_Model.getMaCB() + "')";
        return handler.execAction(sql);
    }

    public void add_canBoThamGia(ArrayList<DSCanBo_Model> dSCanBo_Models, String mask) {
        add_Failed.clear();
        dSCanBo_Models.forEach((cb_Model1) -> {
            boolean b = add_canBoThamGia(cb_Model1, mask);
            if (!b) {
                add_Failed.add(cb_Model1);
            }
        });
    }

    public boolean update_canBoThamGia(DSCanBo_Model cb_Model, String mask) {
        String sql = "UPDATE `canbothamgia` SET `maSV` = '" + cb_Model.getMaCB() + "' "
                + "WHERE `canbothamgia`.`maSK` = '" + mask + "' "
                + "AND `canbothamgia`.`maSV` = '" + cb_Model.getMaCB() + "'";
        return handler.execAction(sql);
    }

    public void update_canBoThamGia(ArrayList<DSCanBo_Model> cb_Model, String mask) {
        update_Failed.clear();
        cb_Model.forEach((cb_Model1) -> {
            boolean b = update_canBoThamGia(cb_Model1, mask);
            if (!b) {
                update_Failed.add(cb_Model1);
            }
        });
    }

    public boolean deleteAll(String maSK) {
        String sql = "DELETE FROM " + table + " WHERE maSK = '" + maSK + "'";
        return handler.execAction(sql);
    }

    public boolean delete_dsCanBo(DSCanBo_Model cb_Model, String mask) {
        String macb = cb_Model.getMaCB();
        String sql = "DELETE FROM " + table + " WHERE macb = '" + macb + "' "
                + "AND maSK = '" + mask + "'";
        return handler.execAction(sql);
    }

    public void delete_dsCanBo(ArrayList<DSCanBo_Model> cb_Model, String mask) {
        delete_Failed.clear();
        cb_Model.forEach((cb_Model1) -> {
            boolean b = delete_dsCanBo(cb_Model1, mask);
            if (!b) {
                delete_Failed.add(cb_Model1);
            }
        });
    }

}
