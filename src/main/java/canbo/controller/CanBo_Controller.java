/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canbo.controller;

import app.view.App_View;
import canbo.model.CanBo_Model;
import canbo.view.CanBo_View;
import database.DatabaseHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import login.Login_Controller;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import other.custom.Alert;
import other.custom.ExtensionFileFilter;

/**
 *
 * @author chuna
 */
public class CanBo_Controller {

    //viết các phương thức, xử lý cơ sở dũ liệu trong này
    private static CanBo_View VIEW = CanBo_View.getView();
    private CanBo_Model canBo_Model;
    private ResultSet rs;
    private final String table = "CANBO";
    private final DatabaseHandler handler = Login_Controller.handler;
    private String path;
    private static ArrayList<CanBo_Model> add_Failed = new ArrayList<>();
    private static ArrayList<CanBo_Model> update_Failed = new ArrayList<>();
    private static ArrayList<CanBo_Model> delete_Failed = new ArrayList<>();
    private static String errorMessage = "";

    public CanBo_Controller() {
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

    public static ArrayList<CanBo_Model> getAdd_Failed() {
        return add_Failed;
    }

    public static ArrayList<CanBo_Model> getUpdate_Failed() {
        return update_Failed;
    }

    public static ArrayList<CanBo_Model> getDelete_Failed() {
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

    public Object[][] import_CanBo() {
        ArrayList<CanBo_Model> listCadre = new ArrayList<>();
        try {
            path = openFileExcel();
            if (path.equals("")) {
                return null;
            }
            listCadre = readCadreFromExcelFile(path);
            if (!listCadre.isEmpty()) {
                listCadre.remove(0);
            }
        } catch (NullPointerException ex) {
        }
        Object[][] array2Object = CanBo_Controller.array2Object(listCadre);
        return array2Object;
    }

    public ArrayList<CanBo_Model> readCadreFromExcelFile(String excelFilePath) {
        ArrayList<CanBo_Model> dsCanBo = new ArrayList<>();
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
                    CanBo_Model canBo = new CanBo_Model();

                    while (cellIterator.hasNext() && !getCellValue(nextRow.getCell(0)).equals("")) {
                        Cell nextCell = cellIterator.next();
                        int columnIndex = nextCell.getColumnIndex();
                        try {
                            switch (columnIndex) {
                                case 0:
                                    canBo.setMaCB((String) getCellValue(nextCell));
                                    break;
                                case 1:
                                    canBo.setTen((String) getCellValue(nextCell));
                                    break;
                                case 2:
                                    canBo.setEmail((String) getCellValue(nextCell));
                                    break;
                                case 3:
                                    canBo.setBoMon((String) getCellValue(nextCell));
                                    break;
                                case 4:
                                    canBo.setKhoa((String) getCellValue(nextCell));
                                    break;

                            }
                        } catch (ClassCastException e) {
                        }

                    }
                    canBo.setMaRFID("Chưa có");
                    dsCanBo.add(canBo);
                    System.out.println(canBo.getMaRFID());

                }
                workbook.close();
                inputStream.close();
            }
        } catch (IOException | NullPointerException e) {
        }

        return dsCanBo;
    }

    //tao viết từ đây trở xuống ok?
    public static boolean writeCadre(List<CanBo_Model> listCadre, String fileName) {

        if (fileName != null && !fileName.isEmpty()) {

            File file = new File(fileName);

            return writeCadre(listCadre, file);
        }

        return false;
    }

    public static boolean writeCadre(List<CanBo_Model> listCadre, File file) {

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
                        int length = listCadre != null ? listCadre.size() : 0;
                        System.out.println(length);
                        for (int i = 0; i < length; i++) {
                            System.out.println("day");
                            CanBo_Model cadre = listCadre.get(i);

                            if (cadre != null) {
                                System.out.println("d2");
                                Row row = sheet.createRow(i + 1);

                                cell = row.createCell(0);
                                cell.setCellValue(cadre.getMaCB());

                                cell = row.createCell(1);
                                cell.setCellValue(cadre.getTen());

                                cell = row.createCell(2);
                                cell.setCellValue(cadre.getEmail());

                                cell = row.createCell(3);
                                cell.setCellValue(cadre.getBoMon());

                                cell = row.createCell(4);
                                cell.setCellValue(cadre.getKhoa());

                                cell = row.createCell(5);
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

    public static Object[][] array2Object(ArrayList<CanBo_Model> list) {
        try {
            Object[][] oses = new Object[list.size()][7];
            for (int i = 0; i < list.size(); i++) {
                oses[i][0] = false;
                oses[i][1] = list.get(i).getMaCB();
                oses[i][2] = list.get(i).getTen();
                oses[i][3] = list.get(i).getEmail();
                oses[i][4] = list.get(i).getBoMon();
                oses[i][5] = list.get(i).getKhoa();
                oses[i][6] = list.get(i).getMaRFID();
            }
            return oses;

        } catch (NullPointerException e) {
            Object[][] empty = {{"Không có dữ liệu"}};
            return empty;
        }
    }

    //dùng hàm execAction để delete, update, insert dữ liệu, đéo!
    //dùng hàm execQuery để select dữ liệu
    public CanBo_Model load_CanBo(String mscb) {
        CanBo_Model canBo_Model = null;
        try {
            String sql = "SELECT macb, ten, email, boMon, khoa, maRFID FROM canbo "
                    + "INNER JOIN bomon ON canbo.mabomon = bomon.mabomon  "
                    + "INNER JOIN khoa ON canbo.makhoa = khoa.makhoa "
                    + "WHERE macb = '" + mscb + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                canBo_Model = new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                );
            }
        } catch (SQLException ex) {
            canBo_Model = null;
        }
        return canBo_Model;
    }

    public CanBo_Model load_CanBoByRFID(String maRFID) {
        CanBo_Model canBo_Model = null;
        try {
            String sql = "SELECT macb, ten, email, boMon, khoa, maRFID FROM canbo "
                    + "INNER JOIN bomon ON canbo.mabomon = bomon.mabomon  "
                    + "INNER JOIN khoa ON canbo.makhoa = khoa.makhoa "
                    + "WHERE maRFID = '" + maRFID + "'";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                canBo_Model = new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                );
            }
        } catch (SQLException ex) {
            canBo_Model = null;
        }
        return canBo_Model;
    }

    public ArrayList<CanBo_Model> load_CanBo() {
        ArrayList<CanBo_Model> lists = new ArrayList<>();
        try {
            String sql = "SELECT macb, ten, email, boMon, khoa, maRFID FROM canbo "
                    + "INNER JOIN bomon ON canbo.mabomon = bomon.mabomon  "
                    + "INNER JOIN khoa ON canbo.makhoa = khoa.makhoa";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                lists.add(new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                ));
            }
            return lists;
        } catch (SQLException | NullPointerException ex) {
            return null;
        }
    }

    public boolean check_CanBo(String maCB) {
        return load_CanBo(maCB) != null;
    }

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

    public ArrayList<CanBo_Model> load_CanBoNoRFID() {
        ArrayList<CanBo_Model> lists = new ArrayList<>();
        try {
            String sql = "SELECT macb, ten, email, boMon, khoa, maRFID FROM canbo "
                    + "INNER JOIN bomon ON canbo.mabomon = bomon.mabomon  "
                    + "INNER JOIN khoa ON canbo.makhoa = khoa.makhoa WHERE maRFID =''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                lists.add(new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                ));
            }
            return lists;
        } catch (SQLException ex) {
        }
        return null;
    }

    public ArrayList<CanBo_Model> load_CanBoHaveRFID() {
        ArrayList<CanBo_Model> lists = new ArrayList<>();
        try {
            String sql = "SELECT macb, ten, email, boMon, khoa, maRFID FROM canbo "
                    + "INNER JOIN bomon ON canbo.mabomon = bomon.mabomon  "
                    + "INNER JOIN khoa ON canbo.makhoa = khoa.makhoa WHERE maRFID <>''";
            rs = handler.execQuery(sql);
            while (rs.next()) {
                lists.add(new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                ));
            }
            return lists;
        } catch (SQLException ex) {
        }
        return null;
    }

    public boolean add_CanBo(CanBo_Model canBo_Model) {
        String maBoMon = App_View.getController().getMaBoMon(canBo_Model.getBoMon());
        String maKhoa = App_View.getController().getMaKhoa(canBo_Model.getKhoa());
        if (maBoMon.equals("") || maKhoa.equals("")) {
            errorMessage = "Dữ liệu sai định dạng";
            return false;
        }
        String sql = "INSERT INTO " + table + " (`macb`, `ten`, `email`, `mabomon`, `makhoa`, `maRFID`) "
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
            if (!check) {
                CanBo_Model model = cadre_Model1;
                add_Failed.add(model);
            }
        });
    }

    public boolean update_CanBo(CanBo_Model canBo_Model) {
        String macb = canBo_Model.getMaCB();
        String maBoMon = App_View.getController().getMaBoMon(canBo_Model.getBoMon());
        String maKhoa = App_View.getController().getMaKhoa(canBo_Model.getKhoa());
        if (maBoMon.equals("") || maKhoa.equals("")) {
            errorMessage = "Dữ liệu sai định dạng";
            return false;
        }
        String sql = "UPDATE `canbo` SET "
                + " `ten` = '" + canBo_Model.getTen() + "',"
                + " `email` = '" + canBo_Model.getEmail() + "',"
                + " `maBoMon` = '" + maBoMon + "',"
                + " `maKhoa` = '" + maKhoa + "',"
                + " `maRFID` = '" + canBo_Model.getMaRFID() + "'"
                + " WHERE `maCB` = '" + macb + "'";
        errorMessage = "";
        return handler.execAction(sql);
    }

    public void update_CanBo(ArrayList<CanBo_Model> canBo_Model) {
        update_Failed.clear();
        canBo_Model.forEach((cadre_Model1) -> {
            boolean b = update_CanBo(cadre_Model1);
            if (!b) {
                CanBo_Model model = cadre_Model1;
                update_Failed.add(model);
            }
        });
    }

    public boolean delete_CanBo(CanBo_Model cadre_Model) {
        String macb = cadre_Model.getMaCB();
        String sql = "DELETE FROM " + table + " WHERE `macb` = '" + macb + "'";
        return handler.execAction(sql);
    }

    public void delete_CanBo(ArrayList<CanBo_Model> cadre_Model) {
        delete_Failed.clear();
        cadre_Model.forEach((cadre_Model1) -> {
            boolean check = delete_CanBo(cadre_Model1);
            if (!check) {
                CanBo_Model model = cadre_Model1;
                delete_Failed.add(model);
            }
            else {
                Alert.showSuccess(null, "Đã cập nhật thay đổi", 2000);
            }
        });
    }

    public boolean existRFIDCanBo(String maRFID) {
        String sql = "SELECT maRFID FROM canbo "
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

    public boolean update_MaThe(String maCB, String maRFID) {
        String sql = "UPDATE " + table + " SET `maRFID` = '" + maRFID + "' "
                + "WHERE `macb` = '" + maCB + "'";
        return handler.execAction(sql);
    }

    public ArrayList<CanBo_Model> search_CanBo(String search) {
        ArrayList<CanBo_Model> list = new ArrayList<>();
        String sql = "";
        if (search.trim().equals("")) {
            sql = "SELECT macb, ten, email, boMon, khoa, maRFID FROM canbo "
                    + "INNER JOIN bomon ON canbo.mabomon = bomon.mabomon  "
                    + "INNER JOIN khoa ON canbo.makhoa = khoa.makhoa "
                    + "WHERE 1";
        } else {
            sql = "SELECT macb, ten, email, boMon, khoa, maRFID FROM canbo "
                    + "INNER JOIN bomon ON canbo.mabomon = bomon.mabomon  "
                    + "INNER JOIN khoa ON canbo.makhoa = khoa.makhoa "
                    + "WHERE macb LIKE '%" + search + "%' "
                    + "OR ten LIKE '%" + search + "%' "
                    + "OR bomon LIKE '%" + search + "%'";
        }
        try {
            rs = handler.execQuery(sql);
            while (rs.next()) {
                list.add(new CanBo_Model(
                        rs.getString("macb"), rs.getString("ten"),
                        rs.getString("email"), rs.getString("bomon"),
                        rs.getString("khoa"), rs.getString("maRFID")
                ));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public void update_CanBo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
