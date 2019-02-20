/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssinhvien.view;

import app.view.App_View;
import dssinhvien.controller.DSSinhVien_Controller;
import dssinhvien.model.DSSinhVien_Model;
import other.custom.DataString;
import other.custom.FormatTextField;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import other.custom.TransitionPane;
import other.table.CustomTable;
import other.table.PanelTable;
import resources.Resources;
import sinhvien.model.SinhVien_Model;
import sinhvien.view.SinhVien_Details;
import sinhvien.view.SinhVien_List;

/**
 *
 * @author chuna
 */
public class DSSinhVien_View extends javax.swing.JPanel {

    private String[] columnNames_DD = {"Tất cả", "Mã sinh viên", "Họ tên", "Mã thẻ", "Điểm danh vào", "Điểm danh ra", "Điểm danh tự động"};
    private String[] columnNames = {"Tất cả", "Mã sinh viên", "Họ tên", "Mã thẻ"};

    private ArrayList<DSSinhVien_Model> sinhVienThamGia_Models;
    private SinhVien_Model sinhVien_Model;
    private SinhVien_Details xemChiTiet;

    private String masukien;
    private static JDialog dialog = new JDialog();
    private TableModel tbM_SinhVien;

    private static DSSinhVien_Controller controller;
    private boolean diemDanh = false;
    private Font font = new Font("Arial", 0, 14);
    private PanelTable panelTable;
    private CustomTable customTable;
    private static ArrayList<DSSinhVien_Model> listDel_DSSV = new ArrayList<>();
    private static ArrayList<Integer> row_Del = new ArrayList<>();
    private int click;
    private static int count;

    public DSSinhVien_View() {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        initComponents();
        xemChiTiet = new SinhVien_Details();
        controller = new DSSinhVien_Controller();
        createUI();

    }

    public DSSinhVien_View(String masukien) {
        this.masukien = masukien;
        this.diemDanh = false;
        xemChiTiet = new SinhVien_Details();
        controller = new DSSinhVien_Controller();
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        initComponents();
        createUI();
        loadData();
    }

    public DSSinhVien_View(String masukien, boolean diemDanh) {
        this.masukien = masukien;
        this.diemDanh = diemDanh;
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        initComponents();
        xemChiTiet = new SinhVien_Details();
        controller = new DSSinhVien_Controller();
        createUI();
        loadData();
    }

    private void createUI() {
        jButton1.setIcon(Resources.search_Icon);
        txt_Search.setFont(font);
        txt_Search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                panelTable.getComboBoxItem().setSelectedIndex(0);
                panelTable.initFilterAndButtons();
            }

        });
    }

    private void loadData() throws NullPointerException {
        sinhVienThamGia_Models = controller.load_TatCaSinhVien(masukien);
        count = sinhVienThamGia_Models.size();
        panelTable = new PanelTable(DSSinhVien_Controller.array2DSSinhVien(sinhVienThamGia_Models), diemDanh ? columnNames_DD : columnNames, "Danh sách sinh viên tham dự");
        panelTable.getPanel().add(pnl_Filter);
        panelTable.getPanel().add(pnl_Search);
        TransitionPane.replacePane(this, panelTable);
        tbM_SinhVien = panelTable.getTable().getModel();
        panelTable.getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectAll();
                click++;
            }
        });
        panelTable.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectMany();
                if (e.getClickCount() == 2) {
                    xem_SinhVien();
                    dialog.getContentPane().removeAll();
                    dialog.getContentPane().add(xemChiTiet);
                    dialog.repaint();
                    dialog.validate();
                    dialog.setResizable(false);
                    dialog.setAlwaysOnTop(true);
                    dialog.pack();
                    dialog.setLocationRelativeTo(App_View.getContainer());
                    dialog.setVisible(true);
                }
            }

            public void xem_SinhVien() {
                try {
                    int row = panelTable.getTable().getSelectedRow();
                    if (row != -1) {
                        sinhVien_Model = SinhVien_List.getController().load_SinhVien(String.valueOf(tbM_SinhVien.getValueAt(row, 1)));
                        xemChiTiet.setSinhVien_Model(sinhVien_Model);
                        xemChiTiet.setText();
//                        xemChiTiet..addActionListener((e) -> {
//                            dialog.hide();
//                        });
                    }
                } catch (NullPointerException ex) {
                }
                xemChiTiet.setAccess(false, DataString.NO_CHANGED);
            }
        });
        panelTable.getTable().setCheckBox(0);
        if (isDiemDanh()) {
            panelTable.getTable().setCellCheckBox(6);
        }
        panelTable.getTable().setWidth(0, 40);
        cbxFilter.addItemListener((e) -> {
            String typeView = cbxFilter.getModel().getSize() < 1 ? "Tất cả" : cbxFilter.getSelectedItem().toString();
            sinhVienThamGia_Models = selectTypeView(typeView);
            setDataTable(DSSinhVien_Controller.array2DSSinhVien(sinhVienThamGia_Models));
        });
        FormatTextField.formatSearchField(txt_Search, panelTable.getTable());
    }

    private void selectAll() {
        if (click % 2 == 0) {
            row_Del.clear();
            listDel_DSSV.clear();
            for (int i = 0; i < panelTable.getTable().getRowCount(); i++) {
                listDel_DSSV.add(new DSSinhVien_Model(
                        String.valueOf(panelTable.getTable().getModel().getValueAt(i, 1)),
                        String.valueOf(panelTable.getTable().getModel().getValueAt(i, 2)),
                        String.valueOf(panelTable.getTable().getModel().getValueAt(i, 3))
                ));

                row_Del.add(i);
            }
        }
    }

    private void selectMany() {
        row_Del.clear();
        listDel_DSSV.clear();
        for (int i = 0; i < panelTable.getTable().getRowCount(); i++) {
            if (panelTable.getTable().isSelectedRows(i, 0)) {
                listDel_DSSV.add(new DSSinhVien_Model(
                        String.valueOf(panelTable.getTable().getModel().getValueAt(i, 1)),
                        String.valueOf(panelTable.getTable().getModel().getValueAt(i, 2)),
                        String.valueOf(panelTable.getTable().getModel().getValueAt(i, 3))
                ));
                row_Del.add(i);
            }
        }
        boolean selectAll = row_Del.size() == panelTable.getTable().getRowCount();
        if (selectAll && !panelTable.getTable().getHeader().isClicked()) {
            panelTable.getTable().getHeader().setMousePressed(true);
            panelTable.getTable().getHeader().headerClick();
            panelTable.getTable().getHeader().repaintCheckBox(panelTable.getTable());
        } else if (!selectAll && panelTable.getTable().getHeader().isClicked()) {
            panelTable.getTable().getHeader().setMousePressed(true);
            panelTable.getTable().getHeader().headerClick();
            panelTable.getTable().getHeader().repaintCheckBox(panelTable.getTable());
            row_Del.forEach((t) -> {
                panelTable.getTable().setValueAt(true, t, 0);
            });
        }
    }

    public SinhVien_Model getValue() {
        try {
            int row = panelTable.getTable().getSelectedRow();
            SinhVien_Model cadre_Model = new SinhVien_Model();
            if (row != -1) {
                cadre_Model.setMaSV(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 1)));
                cadre_Model.setTen(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 2)));
                cadre_Model.setEmail(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 3)));
                cadre_Model.setLop(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 4)));
                cadre_Model.setNganh(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 5)));
                cadre_Model.setKhoa(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 6)));
                cadre_Model.setNienKhoa(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 7)));
                cadre_Model.setMaRFID(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 8)));
                return cadre_Model;
            }
        } catch (NullPointerException ex) {
        }
        return null;
    }

    public DSSinhVien_Model getDSSinhVien() {
        try {
            int row = panelTable.getTable().getSelectedRow();
            DSSinhVien_Model diemDanhSV_Model = new DSSinhVien_Model();
            if (row != -1) {
                diemDanhSV_Model.setMasv(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 1)));
                diemDanhSV_Model.setTen(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 2)));
                diemDanhSV_Model.setMathe(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 3)));
                diemDanhSV_Model.setDiemDanhVao(Time.valueOf(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 4))));
                diemDanhSV_Model.setDiemDanhRa(Time.valueOf(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 5))));
                diemDanhSV_Model.setDiemDanhTuDong(Boolean.valueOf(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 6))));
                return diemDanhSV_Model;
            }
        } catch (NullPointerException ex) {
        }
        return null;
    }

    public DSSinhVien_Model getDSSinhVienLite() {
        try {
            int row = panelTable.getTable().getSelectedRow();
            DSSinhVien_Model diemDanhSV_Model = new DSSinhVien_Model();
            if (row != -1) {
                diemDanhSV_Model.setMasv(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 1)));
                diemDanhSV_Model.setTen(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 2)));
                diemDanhSV_Model.setMathe(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 3)));
                return diemDanhSV_Model;
            }
        } catch (NullPointerException ex) {
        }
        return null;
    }

    public void setDataTable(Object[][] data) {
        DefaultTableModel dm = (DefaultTableModel) panelTable.getTable().getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        dm.setDataVector(data, isDiemDanh() ? columnNames_DD : columnNames);
        panelTable.getTable().setModel(dm);
        panelTable.getTable().setCheckBox(0);
        if (isDiemDanh()) {
            panelTable.getTable().setCellCheckBox(6);
        }
        panelTable.getTable().setWidth(0, 40);
    }

    private ArrayList<DSSinhVien_Model> selectTypeView(String typeView) {
        ArrayList<DSSinhVien_Model> diemDanhSinhVien_Models = new ArrayList<>();
        if (typeView.equals("Tất cả")) {
            diemDanhSinhVien_Models = controller.load_TatCaSinhVien(masukien);
        }
        if (typeView.equals("Vắng mặt(có đăng ký)")) {
            diemDanhSinhVien_Models = controller.load_SinhVienVangCoDK(masukien);

        }
        if (typeView.equals("Có mặt")) {
            diemDanhSinhVien_Models = controller.load_SinhVienCoMat(masukien);

        }
        if (typeView.equals("Vắng mặt(không có đăng ký)")) {
            diemDanhSinhVien_Models = controller.load_SinhVienVangKoDK(masukien);

        }
        if (typeView.equals("Đi trễ")) {
            diemDanhSinhVien_Models = controller.load_SinhVienDiTre(masukien);

        }
        if (typeView.equals("Điểm danh bằng máy")) {
            diemDanhSinhVien_Models = controller.load_SinhVienDDTuDong(masukien);

        }
        if (typeView.equals("Điểm danh thủ công")) {
            diemDanhSinhVien_Models = controller.load_SinhVienDDThuCong(masukien);

        }
        if (typeView.equals("Chưa đăng ký thẻ")) {
            diemDanhSinhVien_Models = controller.load_SinhVienNoRFID(masukien);

        }
        if (typeView.equals("Đã đăng ký thẻ")) {
            diemDanhSinhVien_Models = controller.load_SinhVienHaveRFID(masukien);
        }
        return diemDanhSinhVien_Models;
    }

    public static DSSinhVien_Controller getController() {
        return controller;
    }

    public String getMasukien() {
        return masukien;
    }

    public void setMasukien(String masukien) throws NullPointerException {
        this.masukien = masukien;
        loadData();
    }

    public boolean isDiemDanh() {
        return diemDanh;
    }

    public void setDiemDanh(boolean diemDanh) throws NullPointerException {
        this.diemDanh = diemDanh;
        loadData();
    }

    public PanelTable getPanelTable() {
        return panelTable;
    }

    public void setPanelTable(PanelTable panelTable) {
        this.panelTable = panelTable;
    }

    public CustomTable getTable() {
        return panelTable.getTable();
    }

    public CustomTable.MyCustomTableModel getTableModel() {
        return getTable().getTableModel();
    }

    public void setCbxFilter(String[] content) {
        cbxFilter.removeAllItems();
        for (String s : content) {
            cbxFilter.addItem(s);
        }
        cbxFilter.setMaximumRowCount(cbxFilter.getModel().getSize());
    }

    public static ArrayList<DSSinhVien_Model> getListDel_DSSV() {
        return listDel_DSSV;
    }

    public static void setListDel_DSSV(ArrayList<DSSinhVien_Model> listDel_DSSV) {
        DSSinhVien_View.listDel_DSSV = listDel_DSSV;
    }

    public static ArrayList<Integer> getRow_Del() {
        return row_Del;
    }

    public static void setRow_Del(ArrayList<Integer> row_Del) {
        DSSinhVien_View.row_Del = row_Del;
    }

    public static int getCount() {
        return count;
    }

    public void execute() {
        panelTable.executeTable();
    }

    public void refresh() {
        CustomTable.MyCustomTableModel tableModel = (CustomTable.MyCustomTableModel) getTable().getModel();
        ArrayList<DSSinhVien_Model> list = getController().load_TatCaSinhVien(masukien);
        tableModel.setDataVector(DSSinhVien_Controller.array2DSSinhVien(list));
        getTable().setCheckBox(0);
    }

    public void addDataVector(Object[][] dataVector) {
        CustomTable.MyCustomTableModel tableModel = (CustomTable.MyCustomTableModel) getTable().getModel();
        tableModel.setDataVector(dataVector);
        tableModel.fireTableDataChanged();
        getTable().setCheckBox(0);
    }

    public void insertRow(int row, Object[] rowData) {
        CustomTable.MyCustomTableModel tableModel = (CustomTable.MyCustomTableModel) getTable().getModel();
        tableModel.insertRow(row, rowData);
        tableModel.fireTableDataChanged();
        getTable().setCheckBox(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Search = new javax.swing.JPanel();
        txt_Search = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        pnl_Filter = new javax.swing.JPanel();
        cbxFilter = new javax.swing.JComboBox<>();

        pnl_Search.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Search.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 8));
        pnl_Search.setMinimumSize(new java.awt.Dimension(266, 23));
        pnl_Search.setOpaque(false);
        pnl_Search.setPreferredSize(new java.awt.Dimension(266, 36));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0);
        flowLayout1.setAlignOnBaseline(true);
        pnl_Search.setLayout(flowLayout1);

        txt_Search.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Search.setText("Nhập mã cán bộ, tên, bộ môn");
        txt_Search.setPreferredSize(new java.awt.Dimension(214, 36));
        txt_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SearchActionPerformed(evt);
            }
        });
        pnl_Search.add(txt_Search);

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setOpaque(true);
        jButton1.setPreferredSize(new java.awt.Dimension(36, 36));
        pnl_Search.add(jButton1);

        pnl_Filter.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Filter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 8));
        pnl_Filter.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        pnl_Filter.setOpaque(false);
        pnl_Filter.setLayout(new java.awt.GridLayout(1, 0));

        cbxFilter.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbxFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        pnl_Filter.add(cbxFilter);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 15));
        setLayout(new java.awt.GridLayout(1, 0));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SearchActionPerformed

    }//GEN-LAST:event_txt_SearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxFilter;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel pnl_Filter;
    private javax.swing.JPanel pnl_Search;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables

}
