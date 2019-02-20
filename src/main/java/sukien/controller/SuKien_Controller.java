/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.controller;

import app.view.App_View;
import canbo.model.CanBo_Model;
import database.DatabaseHandler;
import dscanbo.view.DSCanBo_View;
import dssinhvien.view.DSSinhVien_View;
import other.custom.ExtensionFileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import login.Login_Controller;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import other.custom.Alert;
import sinhvien.model.SinhVien_Model;
import sukien.model.SuKien_Model;

/**
 *
 * @author chuna
 */
public class SuKien_Controller {

    //viết các phương thức, xử lý cơ sở dũ liệu trong này
    private SuKien_Model event_Model;
    private ArrayList<SuKien_Model> event_Models = new ArrayList<>();
    private ResultSet rs;
    private final String table = "SUKIEN";
    private final DatabaseHandler handler = Login_Controller.handler;
    private String path;
    private static ArrayList<SuKien_Model> add_Failed = new ArrayList<>();
    private static ArrayList<SuKien_Model> update_Failed = new ArrayList<>();
    private static ArrayList<SuKien_Model> delete_Failed = new ArrayList<>();
    private String errorMessage;

    public SuKien_Controller() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static ArrayList<SuKien_Model> getAdd_Failed() {
        return add_Failed;
    }

    public static ArrayList<SuKien_Model> getUpdate_Failed() {
        return update_Failed;
    }

    public static ArrayList<SuKien_Model> getDelete_Failed() {
        return delete_Failed;
    }

    public static void clearAdd() {
        add_Failed.clear();
    }

    public static void clearDel() {
        delete_Failed.clear();
    }

    public static void clearUpdate() {
        update_Failed.clear();
    }

    public String openFileExcel() {
        String pathFile = "";
        JFileChooser chooseFile = new JFileChooser();
        chooseFile.setDialogTitle("Chọn file");
        ExtensionFileFilter filter1 = new ExtensionFileFilter("XLS and XLSX", new String[]{"xls", "xlsx"});
        chooseFile.setFileFilter(filter1);
        FileNameExtensionFilter ft = new FileNameExtensionFilter("Excel Files", "xls", "xlsx");
        chooseFile.addChoosableFileFilter(ft);
        int kq = chooseFile.showOpenDialog(null);
        if (kq == JFileChooser.APPROVE_OPTION) {
            pathFile = chooseFile.getSelectedFile().getAbsolutePath();
        }
        if (kq == JFileChooser.CANCEL_OPTION) {
            pathFile = "";
        }
        return pathFile;
    }

    public Object[][] import_SK() {
        ArrayList<SuKien_Model> listCadre = new ArrayList<>();
        try {
            path = openFileExcel();
            if (path.equals("")) {
                return null;
            }
            listCadre = readSuKienFromExcelFile(path);
            if (!listCadre.isEmpty()) {
                listCadre.remove(0);
            }
        } catch (NullPointerException ex) {
        }
        return SuKien_Controller.array2Object(listCadre);
    }

    public ArrayList<SuKien_Model> readSuKienFromExcelFile(String excelFilePath) {
        ArrayList<SuKien_Model> listCadre = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

            Workbook workbook = getWorkbook(inputStream, excelFilePath);
            Sheet firstSheet;
            Iterator<Row> iterator;
            if (workbook != null) {
                firstSheet = workbook.getSheetAt(0);
                iterator = firstSheet.iterator();
                while (iterator.hasNext()) {
                    Row nextRow = iterator.next();

                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    SuKien_Model event_ = new SuKien_Model();

                    while (cellIterator.hasNext()) {
                        Cell nextCell = cellIterator.next();
                        int columnIndex = nextCell.getColumnIndex();
                        try {
                            switch (columnIndex) {
                                case 0:
                                    event_.setMaSK((String) getCellValue(nextCell));
                                    break;
                                case 1:
                                    event_.setTenSK((String) getCellValue(nextCell));
                                    break;
                                case 2:
                                    boolean date = String.valueOf(getCellValue(nextCell)).equalsIgnoreCase("ngay");
                                    Date d = null;
                                    if (date) {

                                    } else {
                                        java.util.Date javaDate = DateUtil.getJavaDate((double) Double.valueOf(String.valueOf(getCellValue(nextCell))));
//                                        da = new SimpleDateFormat("yyyy-MM-dd").format(javaDate);
                                        d = new Date(javaDate.getTime());
                                    }
                                    event_.setNgayDienRa(d);
                                    break;
                                case 3:
                                    boolean time = String.valueOf(getCellValue(nextCell)).equalsIgnoreCase("batDau");
                                    Time ti = null;
                                    if (time) {

                                    } else {
                                        java.util.Date javaTime = DateUtil.getJavaDate((double) Double.valueOf(String.valueOf(getCellValue(nextCell))));
                                        ti = new Time(javaTime.getTime());
                                    }
                                    event_.setGioDiemDanhVao(ti);
                                    break;
                                case 4:
                                    boolean endtime = String.valueOf(getCellValue(nextCell)).equalsIgnoreCase("ketThuc");
                                    Time et = null;
                                    if (endtime) {

                                    } else {
                                        java.util.Date endTime = DateUtil.getJavaDate((double) Double.valueOf(String.valueOf(getCellValue(nextCell))));
                                        et = new Time(endTime.getTime());
                                    }
                                    event_.setGioDiemDanhRa(et);
                                    break;
                            }
                        } catch (ClassCastException e) {
                        }

                    }
                    listCadre.add(event_);

                }
                workbook.close();
                inputStream.close();
            }
        } catch (IOException | NullPointerException e) {
        }

        return listCadre;
    }

    //tao viết từ đây trở xuống ok?
    public void writeExcel(ArrayList<SuKien_Model> listCadre, String excelFilePath) {
        try {
            Workbook workbook = getWorkbook(excelFilePath);
            Sheet sheet = workbook.createSheet();
            createHeaderRow(sheet);
            int rowCount = 0;

            for (SuKien_Model event_ : listCadre) {
                Row row = sheet.createRow(++rowCount);
                writeCadre(event_, row);
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException ex) {
            Logger.getLogger(SuKien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeCadre(SuKien_Model event_, Row row) {
        //Chỗ này là ghi ra file theo từng cột ok?

        Cell cell = row.createCell(1);
        cell.setCellValue(event_.getMaSK());

        cell = row.createCell(2);
        cell.setCellValue(event_.getTenSK());

        cell = row.createCell(3);
        cell.setCellValue(event_.getNgayDienRa());
    }

    private void createHeaderRow(Sheet sheet) {

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        org.apache.poi.ss.usermodel.Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);
        Cell cellTitle = row.createCell(1);
        //chỗ này tạo tiêu đề ok ?

        cellTitle.setCellStyle(cellStyle);
        cellTitle.setCellValue("Title");

        Cell cellAuthor = row.createCell(2);
        cellAuthor.setCellStyle(cellStyle);
        cellAuthor.setCellValue("Author");

        Cell cellPrice = row.createCell(3);
        cellPrice.setCellStyle(cellStyle);
        cellPrice.setCellValue("Price");
    }

    //tao viết có từ đây trở lên thôi ok?
    public Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }
        return null;
    }

    public Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) {
        Workbook workbook = null;
        try {
            if (excelFilePath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (excelFilePath.endsWith("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                JOptionPane.showMessageDialog(null, "Không phải file Excel");
            }
            return workbook;
        } catch (IOException | NullPointerException ex) {
            return null;
        }
    }

    private Workbook getWorkbook(String excelFilePath)
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    public static Object[][] array2Object(ArrayList<SuKien_Model> list) {
        try {
            Object[][] oses = new Object[list.size()][8];
            for (int i = 0; i < list.size(); i++) {
                oses[i][0] = false;
                oses[i][1] = list.get(i).getMaSK();
                oses[i][2] = list.get(i).getTenSK();
                oses[i][3] = list.get(i).getNgayDienRa();
                oses[i][4] = list.get(i).getGioVao();
                oses[i][5] = list.get(i).getGioRa();
                oses[i][6] = list.get(i).isDiemDanh();
            }
            return oses;
        } catch (NullPointerException e) {
            Object[][] empty = {{"Không có dữ liệu"}};
            return empty;
        }
    }

    //dùng hàm execAction để delete, update, insert dữ liệu, đéo!
    //dùng hàm execQuery để select dữ liệu
    public int getRowCount() {
        try {
            String sql = "SELECT COUNT(*) FROM " + table;
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return 0;
    }

    public SuKien_Model load_SuKien(String mask) {
        try {
            String sql = "SELECT * FROM " + table + " WHERE maSK = '" + mask + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                event_Model = new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                );
            }
            return event_Model;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SuKien_Model> load_SuKien(int start, int end) {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " LIMIT " + start + " , " + end;
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                ));
            }
            return list;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SuKien_Model> load_SuKienChuaDiemDanh() {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE diemdanh = false";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                ));
            }
            return list;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SuKien_Model> load_SuKienCoTheDiemDanh() {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE diemdanh = false AND ngaydienra > CURRENT_TIMESTAMP;";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                ));
            }
            return list;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SuKien_Model> load_SuKienDaDiemDanh() {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE diemdanh = true";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                ));
            }
            return list;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SuKien_Model> load_SuKienBatBuoc() {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE batbuoc = true";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                ));
            }
            return list;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SuKien_Model> load_SuKienKhongBatBuoc() {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE batbuoc = false";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                ));
            }
            return list;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SuKien_Model> load_SuKien() {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE 1 ";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SuKien_Model(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getTime(4),
                        rs.getTime(5),
                        rs.getTime(6),
                        rs.getTime(7),
                        rs.getBoolean(8)
                ));
            }
            return list;
        } catch (SQLException | NullPointerException ex) {
        }
        return list;
    }

    public Date getNgayDienRa(String maSK) {
        try {
            String sql = "SELECT  ngaydienra  FROM " + table + " WHERE maSK = '" + maSK + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getDate(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time getGioVao(String maSK) {
        try {
            String sql = "SELECT giovao FROM " + table + " WHERE maSK = '" + maSK + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time getGioKetThucVao(String maSK) {
        try {
            String sql = "SELECT gioktvao FROM " + table + " WHERE maSK = '" + maSK + "' ";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time getGioRa(String maSK) {
        try {
            String sql = "SELECT giora FROM " + table + " WHERE maSK = '" + maSK + "' ";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public Time getGioKetThucRa(String maSK) {
        try {
            String sql = "SELECT gioktra FROM " + table + " WHERE maSK = '" + maSK + "' ";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getTime(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public boolean add_SuKien(SuKien_Model event_Model) {
        String sql = "INSERT INTO " + table + " (maSK, tenSK, "
                + "ngaydienra, giovao, giora, diemdanh)"
                + " VALUES ('" + event_Model.getMaSK() + "',"
                + " '" + event_Model.getTenSK() + "',"
                + " '" + event_Model.getNgayDienRa() + "',"
                + " '" + event_Model.getGioVao() + "',"
                + " '" + event_Model.getGioRa() + "',"
                + " false "
                + ")";
        return handler.execAction(sql);
    }

    public void add_SuKien(ArrayList<SuKien_Model> event_Model) {
        add_Failed.clear();
        event_Model.forEach((event_Model1) -> {
            boolean check = add_SuKien(event_Model1);
            if (!check) {
                add_Failed.add(event_Model1);
            }
        });
    }

    public boolean update_SuKien(SuKien_Model event_Model) {
        String sql = "UPDATE sukien SET "
                + "tenSK = '" + event_Model.getTenSK() + "', "
                + "ngayDienRa = '" + event_Model.getNgayDienRa() + "', "
                + "gioVao = '" + event_Model.getGioVao() + "', "
                + "gioRa = '" + event_Model.getGioRa() + "' "
                + "WHERE sukien.maSK = '" + event_Model.getMaSK() + "'";
        return handler.execAction(sql);
    }

    public void update_SuKien(ArrayList<SuKien_Model> event_Model) {
        update_Failed.clear();
        event_Model.forEach((event_Model1) -> {
            boolean check = update_SuKien(event_Model1);
            if (!check) {
                update_Failed.add(event_Model1);
            }
        });
    }

    public boolean update_GioKTVao(String mask, Time gioKTVao) {
        String sql = "UPDATE " + table + " SET "
                + " gioktvao = '" + gioKTVao + "'"
                + " WHERE  maSK  = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean update_GioKTRa(String mask, Time gioKTDiemDanhRa) {
        String sql = "UPDATE " + table + " SET "
                + " gioktra = '" + gioKTDiemDanhRa + "'"
                + " WHERE maSK = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean delete_SuKien(SuKien_Model event_Model) {
        String mask = event_Model.getMaSK();
        boolean diemDanh = event_Model.isDiemDanh();
        if (diemDanh) {
            int select = Alert.showQuestionDialog(App_View.getContainer(), "Sự kiện đã được điểm danh. Xoá sự kiện sẽ xoá luôn dữ liệu điểm danh của sự kiện.\n"
                    + "Bạn vẫn muốn xoá?", "Cảnh báo");
            if (select == Alert.OK) {
                int cb = DSCanBo_View.getController().countTatCaTrongDS(mask);
                int sv = DSSinhVien_View.getController().countTatCaTrongDS(mask);
                if (cb != 0 || sv != 0) {
                    select = Alert.showQuestionDialog(App_View.getContainer(), "Sự kiện đang có danh sách tham dự. Xoá sự kiện sẽ xoá luôn danh sách tham dự.\n"
                            + "Bạn vẫn muốn xoá?", "Cảnh báo");
                    if (select == Alert.YES) {
                        return delete_SuKien(mask);
                    }
                } else {
                    return delete_SuKien(mask);
                }
            }
        } else {
            int cb = DSCanBo_View.getController().countTatCaTrongDS(mask);
            int sv = DSSinhVien_View.getController().countTatCaTrongDS(mask);
            if (cb != 0 || sv != 0) {
                int select = Alert.showQuestionDialog(App_View.getContainer(), "Sự kiện đang có danh sách tham dự. Xoá sự kiện sẽ xoá luôn danh sách tham dự.\n"
                        + "Bạn vẫn muốn xoá?", "Cảnh báo");
                if (select == Alert.YES) {
                    return delete_SuKien(mask);
                }
            } else {
                return delete_SuKien(mask);
            }
        }
        return false;
    }

    public boolean delete_SuKien(String mask) {
        boolean deleteCB = DSCanBo_View.getController().deleteAll(mask);
        boolean deleteSV = DSSinhVien_View.getController().deleteAll(mask);
        if (deleteCB && deleteSV) {
            String sql = "DELETE FROM " + table + " WHERE maSK = '" + mask + "'";
            return handler.execAction(sql);
        }
        return false;
    }

    public void delete_SuKien(ArrayList<SuKien_Model> event_Model) {
        delete_Failed.clear();
        event_Model.forEach((event_Model1) -> {
            boolean check = delete_SuKien(event_Model1);
            if (!check) {
                delete_Failed.add(event_Model1);
            }
        });

    }

    public boolean setDiemDanh(boolean b, String mask) {
        String sql = "UPDATE " + table + " SET "
                + " diemDanh = " + b + ""
                + " WHERE maSK = '" + mask + "'";
        return handler.execAction(sql);
    }

    public boolean isDiemDanh(String mask) {
        try {
            String sql = "SELECT diemdanh FROM " + table + " WHERE maSK = '" + mask + "' ";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuKien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isDiemDanhVao(String mask) {
        try {
            String sql = "SELECT gioktvao FROM " + table + " WHERE maSK = '" + mask + "' ";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getTime(1) != null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuKien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isDiemDanhRa(String mask) {
        try {
            String sql = "SELECT gioktra FROM " + table + " WHERE maSK = '" + mask + "' ";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                return rs.getTime(1) != null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuKien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean add_CanBo(CanBo_Model canBo_Model) {
        String maBoMon = App_View.getController().getMaBoMon(canBo_Model.getBoMon());
        String maKhoa = App_View.getController().getMaKhoa(canBo_Model.getKhoa());
        if (maBoMon.equals("") || maKhoa.equals("")) {
            errorMessage = "Dữ liệu sai định dạng";
            return false;
        }
        String sql = "INSERT INTO canbotemp (macb, ten, email, mabomon, makhoa, maRFID) "
                + "VALUES ('" + canBo_Model.getMaCB() + "',"
                + " '" + canBo_Model.getTen() + "',"
                + " '" + canBo_Model.getEmail() + "',"
                + " '" + maBoMon + "',"
                + " '" + maKhoa + "',"
                + " '" + canBo_Model.getMaRFID() + "'"
                + ")";
        errorMessage = "";
        return handler.execAction(sql);
    }

    public void add_CanBo(ArrayList<CanBo_Model> cadre_Model) {
        add_Failed.clear();
        cadre_Model.forEach((cadre_Model1) -> {
            boolean check = add_CanBo(cadre_Model1);
        });
    }

    public boolean add_SinhVien(SinhVien_Model sinhVien_Model) {
        String maLop = App_View.getController().getMaLop(sinhVien_Model.getLop());
        String maNganh = App_View.getController().getMaNganh(sinhVien_Model.getNganh());
        String maKhoa = App_View.getController().getMaKhoa(sinhVien_Model.getKhoa());
        if (maLop.equals("") || maNganh.equals("") || maKhoa.equals("")) {
            errorMessage = "Dữ liệu sai định dạng";
            return false;
        }
        String sql = "INSERT INTO sinhvientemp (maSV, ten, email, maLop, maNganh, maKhoa, nienKhoa, maRFID)"
                + " VALUES ('" + sinhVien_Model.getMaSV() + "',"
                + " '" + sinhVien_Model.getTen() + "',"
                + " '" + sinhVien_Model.getEmail() + "',"
                + " '" + maLop + "',"
                + " '" + maNganh + "',"
                + " '" + maKhoa + "',"
                + " '" + sinhVien_Model.getNienKhoa() + "',"
                + " '" + sinhVien_Model.getMaRFID() + "')";
        errorMessage = "";
        return handler.execAction(sql);
    }

    public void add_SinhVien(ArrayList<SinhVien_Model> sinhVien_Model_Model) {
        sinhVien_Model_Model.forEach((sinhVien_Model1) -> {
            boolean b = add_SinhVien(sinhVien_Model1);
        });
    }
}
