/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thongke;

import app.view.App_View;
import canbo.model.CanBo_Model;
import canbo.view.CanBo_List;
import database.DatabaseHandler;
import dscanbo.model.DSCanBo_Model;
import dscanbo.view.DSCanBo_View;
import dssinhvien.model.DSSinhVien_Model;
import dssinhvien.view.DSSinhVien_View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import login.Login_Controller;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import other.custom.Alert;
import sukien.model.SuKien_Model;
import sukien.view.SuKien_ListSK;

/**
 *
 * @author chuna
 */
public class ThongKe_Controller {

    private static DatabaseHandler handler = Login_Controller.handler;
    private ResultSet rs;
    private ArrayList<DSCanBo_Model> canBo_Models = new ArrayList<>();
    private ArrayList<DSSinhVien_Model> sinhVien_Models = new ArrayList<>();
    private CanBo_List table = new CanBo_List();
    private List<CanBo_Model> listCadre;

    public ThongKe_Controller() {
    }

    private static HSSFCellStyle createStyleForHeader(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setItalic(false);
        font.setStrikeout(false);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public static CellStyle getDefaultExcelStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setItalic(false);
        font.setStrikeout(false);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public void createSheetSuKien(HSSFWorkbook workbook, SuKien_Model suKien) {
        HSSFSheet sheet = workbook.createSheet("Sự kiện");
        Cell cell;
        Row row;
        HSSFCellStyle style = createStyleForTitle(workbook);
        CellStyle styleCell = getDefaultExcelStyle(workbook);
        row = sheet.createRow(2);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã sự kiện");
        cell.setCellStyle(style);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên sự kiện");
        cell.setCellStyle(style);
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Ngày diễn ra");
        cell.setCellStyle(style);
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Giờ vào");
        cell.setCellStyle(style);
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Giờ ra");
        cell.setCellStyle(style);

        row = sheet.createRow(3);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(suKien.getMaSK());
        cell.setCellStyle(styleCell);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(suKien.getTenSK());
        cell.setCellStyle(styleCell);
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue(suKien.getNgayDienRa().toString());
        cell.setCellStyle(styleCell);
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue(suKien.getGioVao().toString());
        cell.setCellStyle(styleCell);
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue(suKien.getGioRa().toString());
        cell.setCellStyle(styleCell);

    }

    public void createSheetDSCanBo(HSSFWorkbook workbook, List<DSCanBo_Model> dSCanBo_Models) {
        HSSFSheet sheet = workbook.createSheet("Danh sách điểm danh cán bộ ");
        Cell cell;
        Row row;
        HSSFCellStyle styleHeader = createStyleForHeader(workbook);
        HSSFCellStyle style = createStyleForTitle(workbook);
        CellStyle styleCell = getDefaultExcelStyle(workbook);
        row = sheet.createRow(1);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("BẢNG DANH SÁCH THỐNG KÊ ĐIỂM DANH CÁN BỘ");
        cell.setCellStyle(styleHeader);

        row = sheet.createRow(3);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("STT");
        cell.setCellStyle(style);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Mã cán bộ");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Tên cán bộ");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Thời gian điểm danh vào");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Thời gian điểm danh ra");
        cell.setCellStyle(style);

        for (int r = 0; r < dSCanBo_Models.size(); r++) {
            row = sheet.createRow(r + 4);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(r + 1);
            cell.setCellStyle(styleCell);
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(dSCanBo_Models.get(r).getMaCB());
            cell.setCellStyle(styleCell);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(dSCanBo_Models.get(r).getTen());
            cell.setCellStyle(styleCell);

            cell = row.createCell(4, CellType.STRING);
            Time diemDanhVao = dSCanBo_Models.get(r).getDiemDanhVao();
            cell.setCellStyle(styleCell);

            if (diemDanhVao == null) {
                cell.setCellValue("Chưa điểm danh");
            } else {
                cell.setCellValue(diemDanhVao.toString());
            }
            cell.setCellStyle(styleCell);

            cell = row.createCell(5, CellType.STRING);
            Time diemDanhRa = dSCanBo_Models.get(r).getDiemDanhRa();
            if (diemDanhRa == null) {
                cell.setCellValue("Chưa điểm danh");
            } else {
                cell.setCellValue(diemDanhRa.toString());
            }
            cell.setCellStyle(styleCell);

        }
    }

    public void createSheetDSSinhVien(HSSFWorkbook workbook, List<DSSinhVien_Model> dSSinhVien_Models) {
        HSSFSheet sheet = workbook.createSheet("Danh sách điểm danh sinh viên");
        Cell cell;
        Row row;
        HSSFCellStyle styleHeader = createStyleForHeader(workbook);
        HSSFCellStyle style = createStyleForTitle(workbook);
        CellStyle styleCell = getDefaultExcelStyle(workbook);
        row = sheet.createRow(1);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("BẢNG DANH SÁCH THỐNG KÊ ĐIỂM DANH SINH VIÊN");
        cell.setCellStyle(styleHeader);
        row = sheet.createRow(3);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("STT");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Mã sinh viên");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Tên sinh viên");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Thời gian điểm danh vào");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Thời gian điểm danh ra");
        cell.setCellStyle(style);

        for (int r = 0; r < dSSinhVien_Models.size(); r++) {
            row = sheet.createRow(r + 4);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(r + 1);
            cell.setCellStyle(styleCell);

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(dSSinhVien_Models.get(r).getMaSV());
            cell.setCellStyle(styleCell);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(dSSinhVien_Models.get(r).getTen());
            cell.setCellStyle(styleCell);

            cell = row.createCell(4, CellType.STRING);
            Time diemDanhVao = dSSinhVien_Models.get(r).getDiemDanhVao();
            cell.setCellStyle(styleCell);

            if (diemDanhVao == null) {
                cell.setCellValue("Chưa điểm danh");
            } else {
                cell.setCellValue(diemDanhVao.toString());
            }
            cell.setCellStyle(styleCell);

            cell = row.createCell(5, CellType.STRING);
            Time diemDanhRa = dSSinhVien_Models.get(r).getDiemDanhRa();
            if (diemDanhRa == null) {
                cell.setCellValue("Chưa điểm danh");
            } else {
                cell.setCellValue(diemDanhRa.toString());
            }
            cell.setCellStyle(styleCell);

        }
    }

    public void writeExcelFile(HSSFWorkbook workbook, String path, SuKien_Model suKien_Model, List<DSCanBo_Model> dSCanBo_Models, List<DSSinhVien_Model> dSSinhVien_Models) {
        createSheetSuKien(workbook, suKien_Model);
        createSheetDSCanBo(workbook, dSCanBo_Models);
        createSheetDSSinhVien(workbook, dSSinhVien_Models);
        File file = new File(path);
        file.getParentFile().mkdirs();

        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
        }
        try {
            workbook.write(outFile);
        } catch (IOException ex) {
            Alert.showMessageDialog(App_View.getContainer(), ex.getMessage(), "Lỗi ghi tập tin");
        }
        System.out.println("Created file: " + file.getAbsolutePath());
    }

    public void xuatFileBaoCao(SuKien_Model suKien_Model, String path) {
        if (checkSuKien(suKien_Model)) {
            List<DSCanBo_Model> load_TatCaCanBo = DSCanBo_View.getController().load_TatCaCanBo(suKien_Model.getMaSK());
            List<DSSinhVien_Model> load_TatCaSinhVien = DSSinhVien_View.getController().load_TatCaSinhVien(suKien_Model.getMaSK());
            writeExcelFile(new HSSFWorkbook(), path, suKien_Model, load_TatCaCanBo, load_TatCaSinhVien);
            Alert.showMessageDialog(App_View.getContainer(), "Đã xuất báo cáo thành công.", "Thông báo");
        } else {
            Alert.showMessageDialog(App_View.getContainer(), "Sự kiện không hợp kệ.", "Lỗi xuất báo cáo");
        }
    }

    public boolean checkSuKien(SuKien_Model suKien_Model) {
        return SuKien_ListSK.getController().isDiemDanh(suKien_Model.getMaSK());
    }
}
