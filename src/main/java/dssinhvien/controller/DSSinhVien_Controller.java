/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssinhvien.controller;

import database.DatabaseHandler;
import other.custom.Util;
import sukien.view.SuKien_ListSK;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.Login_Controller;
import dssinhvien.model.DSSinhVien_Model;
import sinhvien.model.SinhVien_Model;
import thongke.ThongKe_Controller;

/**
 *
 * @author chuna
 */
public class DSSinhVien_Controller {

    private final String table = "sinhvienthamgia";
    private static final DatabaseHandler handler = Login_Controller.handler;
    private ResultSet rs;
    private static ArrayList<DSSinhVien_Model> add_Failed = new ArrayList<>();
    private static ArrayList<DSSinhVien_Model> update_Failed = new ArrayList<>();
    private static ArrayList<DSSinhVien_Model> delete_Failed = new ArrayList<>();

    public DSSinhVien_Controller() {
    }

    public static ArrayList<DSSinhVien_Model> getAdd_Failed() {
        return add_Failed;
    }

    public static ArrayList<DSSinhVien_Model> getUpdate_Failed() {
        return update_Failed;
    }

    public static ArrayList<DSSinhVien_Model> getDelete_Failed() {
        return delete_Failed;
    }

    public static Object[][] array2DSSinhVien(ArrayList<DSSinhVien_Model> list) {
        try {
            Object[][] data = new Object[list.size()][7];
            for (int i = 0; i < list.size(); i++) {
                data[i][0] = false;
                data[i][1] = list.get(i).getMaSV();
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

    public ArrayList<DSSinhVien_Model> load_SinhVienVangKoDK(String maSK) {
        ArrayList<DSSinhVien_Model> sinhVienThamGia_Models = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT\n"
                    + "    sinhvien.masv,\n"
                    + "    ten,\n"
                    + "    maRFID,sinhvienthamgia.thoigianvao,sinhvienthamgia.thoigianra,sinhvienthamgia.diemdanhtudong\n"
                    + "FROM\n"
                    + "    sinhvien\n"
                    + "INNER JOIN sinhvienthamgia ON sinhvien.masv = sinhvienthamgia.masv\n"
                    + "INNER JOIN sukien ON sinhvienthamgia.maSK = sukien.maSK\n"
                    + "WHERE\n"
                    + "    sukien.batbuoc = '1' AND sinhvienthamgia.thoigianvao IS NULL AND sukien.maSK = '" + maSK + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                sinhVienThamGia_Models.add(new DSSinhVien_Model(
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
        return sinhVienThamGia_Models;
    }

    public ArrayList<DSSinhVien_Model> load_SinhVienVangCoDK(String mask) {
        ArrayList<DSSinhVien_Model> sinhVienThamGia_Models = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM sinhvienthamgia INNER JOIN sinhvien ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE sinhvienthamgia.maSK = '" + mask + "' and thoigianvao is null";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                sinhVienThamGia_Models.add(new DSSinhVien_Model(
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
        return sinhVienThamGia_Models;
    }

    public ArrayList<DSSinhVien_Model> load_SinhVienCoMat(String mask) {
        ArrayList<DSSinhVien_Model> sinhVienThamGia_Models = new ArrayList<>();

        try {
            String sql = "SELECT  sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM sinhvienthamgia INNER JOIN sinhvien ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE sinhvienthamgia.maSK = '" + mask + "' and thoigianvao is not null and thoigianra is NOT null";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                sinhVienThamGia_Models.add(new DSSinhVien_Model(
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
        return sinhVienThamGia_Models;
    }

    public ArrayList<DSSinhVien_Model> load_TatCaSinhVien(String mask) {
        ArrayList<DSSinhVien_Model> sinhVienThamGia_Models = new ArrayList<>();
        try {
            String sql = "SELECT sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID,"
                    + " thoigianvao,thoigianra, diemdanhtudong FROM sinhvienthamgia "
                    + "INNER JOIN sinhvien ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE sinhvienthamgia.mask = '" + mask + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                sinhVienThamGia_Models.add(new DSSinhVien_Model(
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
        return sinhVienThamGia_Models;
    }

    public boolean check_SinhVienThamGia(String masv, String mask) {
        try {
            String sql = "SELECT masv FROM `sinhvienthamgia` "
                    + "WHERE sinhvienthamgia.mask = '" + mask + "' "
                    + "AND sinhvienthamgia.masv = '" + masv + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return true;
            }

        } catch (SQLException | NullPointerException e) {
        }
        return false;
    }

    public ArrayList<DSSinhVien_Model> load_SinhVienNoRFID(String mask) {
        ArrayList<DSSinhVien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT  sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM sinhvienthamgia INNER JOIN sinhvien ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE sinhvienthamgia.maSK = '" + mask + "' AND sinhvien.maRFID =''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String masv = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSSinhVien_Model(masv, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public ArrayList<DSSinhVien_Model> load_SinhVienHaveRFID(String mask) {
        ArrayList<DSSinhVien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT  sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID, thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM sinhvienthamgia INNER JOIN sinhvien ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE sinhvienthamgia.maSK = '" + mask + "'  AND sinhvien.maRFID <>''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String masv = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSSinhVien_Model(masv, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public ArrayList<DSSinhVien_Model> load_SinhVienDiTre(String mask) {
        ArrayList<DSSinhVien_Model> list = new ArrayList<>();
        Time t1 = SuKien_ListSK.getController().getGioVao(mask);
        Time t2 = SuKien_ListSK.getController().getGioKetThucVao(mask);
        Time timeLimit = Util.divideTime(Util.betweenTime(t1, t2), 2);
        try {
            String sql = "SELECT sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID,"
                    + " thoigianvao, thoigianra, diemdanhtudong "
                    + "FROM sinhvienthamgia INNER JOIN sinhvien "
                    + "ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE sinhvienthamgia.maSK = '" + mask + "'"
                    + " AND thoigianra IS NOT NULL"
                    + " AND thoigianvao > '" + timeLimit + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String masv = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSSinhVien_Model(masv, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public ArrayList<DSSinhVien_Model> load_SinhVienDDThuCong(String maSK) {
        ArrayList<DSSinhVien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID, "
                    + "thoigianvao, thoigianra, diemdanhtudong FROM sinhvienthamgia "
                    + "INNER JOIN sinhvien ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE  thoigianvao IS NOT NULL  AND thoigianra IS NOT NULL "
                    + "AND diemdanhtudong = '0' "
                    + "AND maSK = (SELECT maSK FROM sukien "
                    + "WHERE maSK = '" + maSK + "')";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String masv = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSSinhVien_Model(masv, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public ArrayList<DSSinhVien_Model> load_SinhVienDDTuDong(String maSK) {
        ArrayList<DSSinhVien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT sinhvienthamgia.masv, sinhvien.ten, sinhvien.maRFID, "
                    + "thoigianvao, thoigianra, diemdanhtudong FROM sinhvienthamgia  "
                    + "INNER JOIN sinhvien ON sinhvienthamgia.masv = sinhvien.masv "
                    + "WHERE  thoigianvao IS NOT NULL  AND thoigianra IS NOT NULL "
                    + "AND diemdanhtudong = '1' "
                    + "AND maSK = (SELECT maSK FROM sukien "
                    + "WHERE maSK = '" + maSK + "')";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                String masv = rs.getString(1);
                String ten = rs.getString(2);
                String maRFID = rs.getString(3);
                Time diemDanhVao = rs.getTime(4);
                Time diemDanhRa = rs.getTime(5);
                boolean diemDanhTuDong = rs.getBoolean(6);
                list.add(new DSSinhVien_Model(masv, ten, maRFID, diemDanhVao, diemDanhRa, diemDanhTuDong));
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    public SinhVien_Model getSinhVienByRFID(String maRFID, String maSK) {
        SinhVien_Model sinhVien_Model = null;
        try {
            String sql = "SELECT sinhvien.masv, sinhvien.ten, sinhvien.email, lop.lop, nganh.Nganh, khoa.Khoa,sinhvien.nienKhoa, sinhvien.maRFID FROM sinhvien, lop,nganh, khoa \n"
                    + "WHERE sinhvien.maSV = (SELECT maSv FROM sinhvienthamgia WHERE maSK = '" + maSK + "' \n"
                    + "                    AND sinhvienthamgia.maSV = (SELECT sinhvien.maSV FROM sinhvien WHERE maRFID = '" + maRFID + "')) \n"
                    + "                    AND lop.maLop=sinhvien.maLop AND nganh.maNganh=sinhvien.maNganh AND khoa.maKhoa=sinhvien.maKhoa";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                sinhVien_Model = new SinhVien_Model(
                        rs.getString("masv"),
                        rs.getString("ten"),
                        rs.getString("email"),
                        rs.getString("lop"),
                        rs.getString("nganh"),
                        rs.getString("khoa"),
                        rs.getString("nienkhoa"),
                        rs.getString("maRFID")
                );
            }
        } catch (SQLException | NullPointerException ex) {
            sinhVien_Model = null;
        }
        return sinhVien_Model;
    }

    public SinhVien_Model getSinhVienByMaSV(String masv, String maSK) {
        try {
            String sql = "SELECT sinhvien.masv, sinhvien.ten, sinhvien.email, lop.lop, nganh.Nganh, khoa.Khoa,sinhvien.nienKhoa, sinhvien.maRFID FROM sinhvien, lop,nganh, khoa \n"
                    + "WHERE sinhvien.maSV = (SELECT maSv FROM sinhvienthamgia WHERE maSK = '" + maSK + "' \n"
                    + "                    AND sinhvienthamgia.maSV = (SELECT sinhvien.maSV FROM sinhvien WHERE masv = '" + masv + "')) \n"
                    + "                    AND lop.maLop=sinhvien.maLop AND nganh.maNganh=sinhvien.maNganh AND khoa.maKhoa=sinhvien.maKhoa";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return new SinhVien_Model(
                        rs.getString("masv"),
                        rs.getString("ten"),
                        rs.getString("email"),
                        rs.getString("lop"),
                        rs.getString("nganh"),
                        rs.getString("khoa"),
                        rs.getString("nienkhoa"),
                        rs.getString("maRFID")
                );
            }
        } catch (SQLException | NullPointerException ex) {
        }
        return null;
    }

    public int countSinhVienDiTre(String maSK) {
        return load_SinhVienDiTre(maSK).size();
    }

    public int countSinhVienDDMay(String maSK) {
        return load_SinhVienDDTuDong(maSK).size();
    }

    public int countSinhVienDDThuCong(String maSK) {
        return load_SinhVienDDThuCong(maSK).size();
    }

    public int countSinhVienChuaCoThe(String maSK) {
        return load_SinhVienNoRFID(maSK).size();
    }

    public int countSinhVienCoThe(String maSK) {
        return load_SinhVienHaveRFID(maSK).size();
    }

    public int countSV_VangCoDK(String mask) {
        return load_SinhVienVangCoDK(mask).size();
    }

    public int countSinhVienVangKoDK(String maSK) {
        return load_SinhVienVangKoDK(maSK).size();
    }

    public int countSinhVienCoMat(String mask) {
        return load_SinhVienCoMat(mask).size();
    }

    public int countTatCaTrongDS(String mask) {
        return load_TatCaSinhVien(mask).size();
    }

    public Time get_ThoiDiemVao(String maRFID, String mask) {
        String sql = "SELECT thoigianvao FROM sinhvienthamgia "
                + " WHERE sinhvienthamgia.masv = (SELECT masv FROM sinhvien "
                + " WHERE sinhvien.maRFID = '" + maRFID + "') "
                + "AND sinhvienthamgia.mask = '" + mask + "'";
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time get_ThoiDiemVaoByMaSV(String masv, String mask) {
        String sql = "SELECT thoigianvao FROM sinhvienthamgia "
                + " WHERE sinhvienthamgia.masv = '" + masv + "'"
                + "AND sinhvienthamgia.mask = '" + mask + "'";
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
        String sql = "SELECT thoigianra FROM sinhvienthamgia "
                + " WHERE sinhvienthamgia.masv = (SELECT masv FROM sinhvien "
                + " WHERE sinhvien.maRFID = '" + maRFID + "')"
                + " AND sinhvienthamgia.mask = '" + mask + "'";
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time get_ThoiDiemRaByMaSV(String masv, String mask) {
        String sql = "SELECT thoigianra FROM sinhvienthamgia "
                + " WHERE sinhvienthamgia.masv =  '" + masv + "'"
                + " AND sinhvienthamgia.mask = '" + mask + "'";
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

    public boolean isDiemDanhVaoByMaSV(String masv, String mask) {
        return get_ThoiDiemVaoByMaSV(masv, mask) != null;
    }

    public boolean isDiemDanhRaByMaSV(String masv, String mask) {
        return get_ThoiDiemRaByMaSV(masv, mask) != null;
    }

    public void update_DiemDanhVao(Time Time, String masv, String mask) {
        String sql = "UPDATE sinhvienthamgia SET thoigianvao = '" + Time + "' "
                + "WHERE sinhvienthamgia.masv = '" + masv + "' AND sinhvienthamgia.mask = '" + mask + "'";
        handler.execAction(sql);
    }

    public void update_DiemDanhRa(Time Time, String masv, String mask) {
        String sql = "UPDATE sinhvienthamgia SET thoigianra = '" + Time + "' "
                + "WHERE sinhvienthamgia.masv = '" + masv + "' AND sinhvienthamgia.mask = '" + mask + "'";
        handler.execAction(sql);
    }

    public void setDiemDanhTuDong(boolean b, String masv) {
        String sql = "UPDATE sinhvienthamgia SET diemdanhtudong = " + b + " WHERE sinhvienthamgia.masv = '" + masv + "'";
        handler.execAction(sql);
    }

    public boolean huy_DuLieuDiemDanh(String masv, String mask) {
        String sql = "UPDATE sinhvienthamgia SET thoigianvao = NULL, thoigianra = NULL "
                + "WHERE sinhvienthamgia.maSK = '" + mask + "' AND sinhvienthamgia.masv = '" + masv + "'";
        return handler.execAction(sql);
    }

    public boolean huy_TatCaDLDiemDanh(String mask) {
        String sql = "UPDATE `sinhvienthamgia` SET `thoiGianVao` = NULL,`thoigianra` = NULL  "
                + "WHERE `sinhvienthamgia`.`maSK` = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean add_sinhVienThamGia(DSSinhVien_Model sv_Model, String mask) {
        String sql = "INSERT INTO " + table + " (maSK, masv, thoigianvao, thoigianra, diemdanhtudong)"
                + " VALUES ('" + mask + "',"
                + " '" + sv_Model.getMaSV() + "',"
                + " NULL,"
                + " NULL,"
                + " '0')";
        return handler.execAction(sql);
    }

    public void add_sinhVienThamGia(ArrayList<DSSinhVien_Model> sv_Model, String mask) {
        add_Failed.clear();
        sv_Model.forEach((sv_Model1) -> {
            boolean check = add_sinhVienThamGia(sv_Model1, mask);
            if (!check) {
                add_Failed.add(sv_Model1);
            }
        });
    }

    public boolean delete_dsSinhVien(DSSinhVien_Model sv_Model, String mask) {
        String masv = sv_Model.getMaSV();
        String sql = "DELETE FROM " + table + " WHERE masv = '" + masv + "' "
                + "AND maSK = '" + mask + "'";
        return handler.execAction(sql);
    }

    public void delete_dsSinhVien(ArrayList<DSSinhVien_Model> sv_Model, String mask) {
        delete_Failed.clear();
        sv_Model.forEach((event_Model1) -> {
            boolean check = delete_dsSinhVien(event_Model1, mask);
            if (!check) {
                delete_Failed.add(event_Model1);
            }
        });
    }

    public boolean deleteAll(String maSK) {
        String sql = "DELETE FROM " + table + " WHERE maSK = '" + maSK + "'";
        return handler.execAction(sql);
    }

    public boolean update_SinhVienThamGia(DSSinhVien_Model dSSinhVien_Model, String mask) {
        String sql = "UPDATE `sinhvienthamgia` SET `maSV` = '" + dSSinhVien_Model.getMaSV() + "' "
                + "WHERE `sinhvienthamgia`.`maSK` = '" + mask + "' "
                + "AND `sinhvienthamgia`.`maSV` = '" + dSSinhVien_Model.getMaSV() + "'";
        return handler.execAction(sql);
    }

    public void update_sinhVienThamGia(ArrayList<DSSinhVien_Model> dSSinhVien, String mask) {
        update_Failed.clear();
        dSSinhVien.forEach((t) -> {
            boolean check = update_SinhVienThamGia(t, mask);
            if (!check) {
                update_Failed.add(t);
            }
        });
    }

    public int countSV_VangSKBatBuoc(String maSK) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
