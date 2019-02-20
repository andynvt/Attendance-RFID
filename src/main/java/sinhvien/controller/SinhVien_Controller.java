package sinhvien.controller;

import app.view.App_View;
import database.DatabaseHandler;
import other.custom.ExtensionFileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import sinhvien.model.SinhVien_Model;
import sinhvien.view.SinhVien_View;
import java.util.Iterator;
import java.util.List;
import login.Login_Controller;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import other.custom.Alert;

public class SinhVien_Controller {

    //viết các phương thức, xử lý cơ sở dũ liệu trong này
    private static SinhVien_View VIEW = SinhVien_View.getView();
    private SinhVien_Model sinhVien_Model;
    private ResultSet rs;
    private final String table = "SINHVIEN";
    private final DatabaseHandler handler = Login_Controller.handler;
    private String path;
    private static ArrayList<SinhVien_Model> add_Failed = new ArrayList<>();
    private static ArrayList<SinhVien_Model> update_Failed = new ArrayList<>();
    private static ArrayList<SinhVien_Model> delete_Failed = new ArrayList<>();
    private static String errorMessage = "";

    public SinhVien_Controller() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static ArrayList<SinhVien_Model> getAdd_Failed() {
        return add_Failed;
    }

    public static ArrayList<SinhVien_Model> getUpdate_Failed() {
        return update_Failed;
    }

    public static ArrayList<SinhVien_Model> getDelete_Failed() {
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
        int kq = chooseFile.showOpenDialog(App_View.getContainer());
        if (kq == JFileChooser.APPROVE_OPTION) {
            pathFile = chooseFile.getSelectedFile().getAbsolutePath();
        }
        if (kq == JFileChooser.CANCEL_OPTION) {
            pathFile = "";
        }
        return pathFile;
    }

    public Object[][] import_SinhVien() {
        ArrayList<SinhVien_Model> listStudent = new ArrayList<>();
        try {
            path = openFileExcel();
            listStudent = readCadreFromExcelFile(path);
            if (!listStudent.isEmpty()) {
                listStudent.remove(0);
            }
        } catch (NullPointerException ex) {
        }
        return SinhVien_Controller.array2Object(listStudent);
    }

    public Object[][] import_DS() {
        ArrayList<SinhVien_Model> listSV = new ArrayList<>();
        try {
            path = openFileExcel();
            listSV = readCadreFromExcelFile(path);
            if (!listSV.isEmpty()) {
                listSV.remove(0);
            }
        } catch (NullPointerException ex) {
        }
        return SinhVien_Controller.array2Object(listSV);
    }

    public ArrayList<SinhVien_Model> readCadreFromExcelFile(String excelFilePath) {
        ArrayList<SinhVien_Model> listStudent = new ArrayList<>();
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
                    SinhVien_Model cadre_ = new SinhVien_Model();

                    while (cellIterator.hasNext() && !getCellValue(nextRow.getCell(0)).equals("")) {
                        Cell nextCell = cellIterator.next();
                        int columnIndex = nextCell.getColumnIndex();
                        try {
                            switch (columnIndex) {
                                case 0:
                                    cadre_.setMaSV((String) getCellValue(nextCell));
                                    break;
                                case 1:
                                    cadre_.setTen((String) getCellValue(nextCell));
                                    break;
                                case 2:
                                    cadre_.setEmail((String) getCellValue(nextCell));
                                    break;
                                case 3:
                                    cadre_.setLop((String) getCellValue(nextCell));
                                    break;
                                case 4:
                                    cadre_.setNganh((String) getCellValue(nextCell));
                                    break;
                                case 5:
                                    cadre_.setKhoa((String) getCellValue(nextCell));
                                    break;
                                case 6:
                                    cadre_.setNienKhoa((String) getCellValue(nextCell));
                                    break;

                            }
                        } catch (ClassCastException e) {
                        }

                    }
                    cadre_.setMaRFID("Chưa có");
                    listStudent.add(cadre_);

                }
                workbook.close();
                inputStream.close();
            }
        } catch (IOException e) {
        }

        return listStudent;
    }

    //tao viết từ đây trở xuống ok?
    public static boolean writeCadre(List<SinhVien_Model> listStudent, String fileName) {

        if (fileName != null && !fileName.isEmpty()) {

            File file = new File(fileName);

            return writeCadre(listStudent, file);
        }

        return false;
    }

    public boolean check_SinhVien(String masv) {
        return load_SinhVien(masv) != null;
    }

    public static boolean writeCadre(List<SinhVien_Model> listStudent, File file) {

        if (file != null) {

            try {

                // Create a new workbook
                XSSFWorkbook workbook = new XSSFWorkbook();

                if (workbook != null) {

                    // Create a new sheet
                    XSSFSheet sheet = workbook.createSheet("Cán Bộ");

                    if (sheet != null) {

                        // Create header row
                        Row header = sheet.createRow(0);

                        Cell cell = header.createCell(0);
                        cell.setCellValue("Mã Cán bộ");

                        cell = header.createCell(1);
                        cell.setCellValue("Tên");

                        cell = header.createCell(2);
                        cell.setCellValue("Email");

                        cell = header.createCell(3);
                        cell.setCellValue("Bộ môn");

                        cell = header.createCell(4);
                        cell.setCellValue("Khoa");

                        cell = header.createCell(5);
                        cell.setCellValue("Mã thẻ");

                        // Create content rows
                        int length = listStudent != null ? listStudent.size() : 0;
                        System.out.println(length);
                        for (int i = 0; i < length; i++) {
                            System.out.println("day");
                            SinhVien_Model cadre = listStudent.get(i);

                            if (cadre != null) {
                                System.out.println("d2");
                                Row row = sheet.createRow(i + 1);

                                cell = row.createCell(0);
                                cell.setCellValue(cadre.getMaSV());

                                cell = row.createCell(1);
                                cell.setCellValue(cadre.getTen());

                                cell = row.createCell(2);
                                cell.setCellValue(cadre.getEmail());

                                cell = row.createCell(3);
                                cell.setCellValue(cadre.getLop());

                                cell = row.createCell(4);
                                cell.setCellValue(cadre.getNganh());

                                cell = row.createCell(5);
                                cell.setCellValue(cadre.getKhoa());

                                cell = row.createCell(6);
                                cell.setCellValue(cadre.getNienKhoa());

                                cell = row.createCell(7);
                                cell.setCellValue(cadre.getMaRFID());

                            }
                        }
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    workbook.write(fileOutputStream);

                    workbook.close();

                    return true;
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
        return false;
    }

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
                JOptionPane.showMessageDialog(App_View.getContainer(), "Không phải file Excel");
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

    public static Object[][] array2Object(ArrayList<SinhVien_Model> list) {
        try {
            Object[][] oses = new Object[list.size()][9];
            for (int i = 0; i < list.size(); i++) {
                oses[i][0] = false;
                oses[i][1] = list.get(i).getMaSV();
                oses[i][2] = list.get(i).getTen();
                oses[i][3] = list.get(i).getEmail();
                oses[i][4] = list.get(i).getLop();
                oses[i][5] = list.get(i).getNganh();
                oses[i][6] = list.get(i).getKhoa();
                oses[i][7] = list.get(i).getNienKhoa();
                oses[i][8] = list.get(i).getMaRFID();
            }
            return oses;

        } catch (NullPointerException e) {
            Object[][] empty = {{"Không có dữ liệu"}};
            return empty;
        }
    }

    //dùng hàm execAction để delete, update, insert dữ liệu, đéo!
    //dùng hàm execQuery để select dữ liệu
    public SinhVien_Model load_SinhVienByRFID(String maRFID) {
        try {
            String sql = "SELECT maSV , ten, email, Lop,  Nganh,  Khoa, nienKhoa, maRFID FROM sinhvien "
                    + "INNER JOIN lop ON sinhvien.maLop = lop.maLop "
                    + "INNER JOIN nganh ON sinhvien.maNganh = nganh.maNganh "
                    + "INNER JOIN khoa ON sinhvien.maKhoa = khoa.maKhoa "
                    + "WHERE maRFID  = '" + maRFID + "'";
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
        } catch (SQLException ex) {
            sinhVien_Model = null;
        }
        return sinhVien_Model;
    }

    public SinhVien_Model load_SinhVien(String masv) {
        SinhVien_Model sinhVien_Model = null;
        try {
            String sql = "SELECT maSV , ten, email, Lop,  Nganh,  Khoa, nienKhoa, maRFID FROM sinhvien "
                    + "INNER JOIN lop ON sinhvien.maLop = lop.maLop "
                    + "INNER JOIN nganh ON sinhvien.maNganh = nganh.maNganh "
                    + "INNER JOIN khoa ON sinhvien.maKhoa = khoa.maKhoa "
                    + "WHERE masv = '" + masv + "'";
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
        } catch (SQLException ex) {
            sinhVien_Model = null;
        }
        return sinhVien_Model;
    }

    public ArrayList<SinhVien_Model> load_SinhVien() {
        ArrayList<SinhVien_Model> lists = new ArrayList<>();
        try {
            String sql = "SELECT maSV , ten, email, Lop,  Nganh,  Khoa, nienKhoa, maRFID FROM sinhvien "
                    + "INNER JOIN lop ON sinhvien.maLop = lop.maLop "
                    + "INNER JOIN nganh ON sinhvien.maNganh = nganh.maNganh "
                    + "INNER JOIN khoa ON sinhvien.maKhoa = khoa.maKhoa";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                lists.add(new SinhVien_Model(
                        rs.getString("masv"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("lop"),
                        rs.getString("nganh"), rs.getString("khoa"),
                        rs.getString("nienkhoa"), rs.getString("maRFID")
                ));
            }
            return lists;
        } catch (SQLException ex) {
            Alert.showErrorDialog("LOI", table);
        }
        return null;
    }

    public ArrayList<SinhVien_Model> load_SinhVienNoRFID() {
        ArrayList<SinhVien_Model> lists = new ArrayList<>();
        try {
            String sql = "SELECT maSV , ten, email, Lop,  Nganh,  Khoa, nienKhoa, maRFID FROM sinhvien "
                    + "INNER JOIN lop ON sinhvien.maLop = lop.maLop "
                    + "INNER JOIN nganh ON sinhvien.maNganh = nganh.maNganh "
                    + "INNER JOIN khoa ON sinhvien.maKhoa = khoa.maKhoa "
                    + "WHERE maRFID =''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                lists.add(new SinhVien_Model(
                        rs.getString("masv"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("lop"),
                        rs.getString("nganh"), rs.getString("khoa"),
                        rs.getString("nienkhoa"), rs.getString("maRFID")
                ));
            }
            return lists;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<SinhVien_Model> load_SinhVienHaveRFID() {
        ArrayList<SinhVien_Model> lists = new ArrayList<>();
        try {
            String sql = "SELECT maSV , ten, email, Lop,  Nganh,  Khoa, nienKhoa, maRFID FROM sinhvien "
                    + "INNER JOIN lop ON sinhvien.maLop = lop.maLop "
                    + "INNER JOIN nganh ON sinhvien.maNganh = nganh.maNganh "
                    + "INNER JOIN khoa ON sinhvien.maKhoa = khoa.maKhoa "
                    + "WHERE maRFID <>''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                lists.add(new SinhVien_Model(
                        rs.getString("masv"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("lop"),
                        rs.getString("nganh"), rs.getString("khoa"),
                        rs.getString("nienkhoa"), rs.getString("maRFID")
                ));
            }
            return lists;
        } catch (SQLException ex) {
        }
        return null;
    }

    public boolean add_SinhVien(SinhVien_Model sinhVien_Model) {
        String maLop = App_View.getController().getMaLop(sinhVien_Model.getLop());
        String maNganh = App_View.getController().getMaNganh(sinhVien_Model.getNganh());
        String maKhoa = App_View.getController().getMaKhoa(sinhVien_Model.getKhoa());
        if (maLop.equals("") || maNganh.equals("") || maKhoa.equals("")) {
            errorMessage = "Dữ liệu sai định dạng";
            return false;
        }
        String sql = "INSERT INTO " + table + " (maSV, ten, email, maLop, maNganh, maKhoa, nienKhoa, maRFID)"
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
            if (!b) {
                add_Failed.add(sinhVien_Model1);
            }
            else {
                Alert.showMessageDialog("Đã cập nhật thay đổi!", "Thông báo");
            }
        });
    }

    public boolean update_SinhVien(SinhVien_Model sinhVien_Model_Model) {
        String maLop = App_View.getController().getMaLop(sinhVien_Model_Model.getLop());
        String maNganh = App_View.getController().getMaNganh(sinhVien_Model_Model.getNganh());
        String maKhoa = App_View.getController().getMaKhoa(sinhVien_Model_Model.getKhoa());
        String masv = sinhVien_Model_Model.getMaSV();
        if (maLop.equals("") || maNganh.equals("") || maKhoa.equals("")) {
            errorMessage = "Dữ liệu sai định dạng";
            return false;
        }
        String sql = "UPDATE " + table + " SET "
                + " ten = '" + sinhVien_Model_Model.getTen() + "',"
                + " email = '" + sinhVien_Model_Model.getEmail() + "',"
                + " maLop = '" + maLop + "',"
                + " maNganh = '" + maNganh + "',"
                + " maKhoa = '" + maKhoa + "',"
                + " nienKhoa = '" + sinhVien_Model_Model.getNienKhoa() + "',"
                + " maRFID = '" + sinhVien_Model_Model.getMaRFID() + "'"
                + " WHERE maSV = '" + masv + "'";
        errorMessage = "";
        return handler.execAction(sql);
    }

    public void update_SinhVien(ArrayList<SinhVien_Model> sinhVien_Model_Model) {
        sinhVien_Model_Model.forEach((sinhVien_Model1) -> {
            boolean b = update_SinhVien(sinhVien_Model1);
            if (!b) {
                update_Failed.add(sinhVien_Model1);
            }
            else {
                Alert.showMessageDialog("Đã cập nhật thay đổi!", "Thông báo");
            }
        });
    }

    public boolean delete_SinhVien(SinhVien_Model sinhVien_Model_Model) {
        String masv = sinhVien_Model_Model.getMaSV();
        String sql = "DELETE FROM " + table + " WHERE masv = '" + masv + "'";
        return handler.execAction(sql);
    }

    public void delete_SinhVien(ArrayList<SinhVien_Model> sinhVien_Model_Model) {
        sinhVien_Model_Model.forEach((sinhVien_Model1) -> {
            boolean b = delete_SinhVien(sinhVien_Model1);
            if (!b) {
                delete_Failed.add(sinhVien_Model1);
            }
            else {
                 Alert.showMessageDialog("Đã cập nhật thay đổi!", "Thông báo");
            }
        });
    }

    public boolean existRFIDSinhVien(String maRFID) {
        String sql = "SELECT maRFID FROM sinhvien "
                + "WHERE `maRFID` = '" + maRFID + "'";
        rs = handler.execQuery(sql);
        try {
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            return false;

        }
        return false;
    }

    public boolean update_MaThe(String mssv, String maRFID) {
        String sql = "UPDATE " + table + " SET maRFID = '" + maRFID + "' "
                + "WHERE masv = '" + mssv + "'";
        return handler.execAction(sql);
    }

    public ArrayList<SinhVien_Model> search_SinhVien(String search) {
        ArrayList<SinhVien_Model> list = new ArrayList<>();
        String sql = "";
        if (!search.trim().equals("")) {
            sql = "SELECT maSV , ten, email, Lop,  Nganh,  Khoa, nienKhoa, maRFID FROM sinhvien "
                    + "INNER JOIN lop ON sinhvien.maLop = lop.maLop "
                    + "INNER JOIN nganh ON sinhvien.maNganh = nganh.maNganh "
                    + "INNER JOIN khoa ON sinhvien.maKhoa = khoa.maKhoa "
                    + "WHERE masv LIKE '%" + search + "%' "
                    + "OR ten LIKE '%" + search + "%' "
                    + "OR lop LIKE '%" + search + "%'";
        } else {
            sql = "SELECT maSV , ten, email, Lop,  Nganh,  Khoa, nienKhoa, maRFID FROM sinhvien "
                    + "INNER JOIN lop ON sinhvien.maLop = lop.maLop "
                    + "INNER JOIN nganh ON sinhvien.maNganh = nganh.maNganh "
                    + "INNER JOIN khoa ON sinhvien.maKhoa = khoa.maKhoa "
                    + "WHERE 1";
        }
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new SinhVien_Model(
                        rs.getString("masv"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("lop"),
                        rs.getString("nganh"), rs.getString("khoa"),
                        rs.getString("nienkhoa"), rs.getString("maRFID")
                ));
            }
        } catch (SQLException e) {
        }
        return list;
    }
}
