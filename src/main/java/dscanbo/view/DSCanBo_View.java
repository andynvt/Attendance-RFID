/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dscanbo.view;

import app.view.App_View;
import dscanbo.model.DSCanBo_Model;
import dscanbo.controller.DSCanBo_Controller;
import canbo.model.CanBo_Model;
import canbo.view.CanBo_List;
import canbo.view.CanBo_Detail;
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
import other.table.CustomTable.MyCustomTableModel;
import other.table.PanelTable;
import resources.Resources;

/**
 *
 * @author chuna
 */
public class DSCanBo_View extends javax.swing.JPanel {

    private String[] columnNames_DD = {"Tất cả", "Mã cán bộ", "Họ tên", "Mã thẻ", "Điểm danh vào", "Điểm danh ra", "Điểm danh tự động"};
    private String[] columnNames = {"Tất cả", "Mã cán bộ", "Họ tên", "Mã thẻ"};

    private ArrayList<DSCanBo_Model> canBoThamGia_Models;
    private CanBo_Model canBo_Model;
    private CanBo_Detail xemChiTiet;

    private String masukien;
    private static JDialog dialog = new JDialog();
    private TableModel tbM_CanBo;

    private static DSCanBo_Controller controller;
    private boolean diemDanh;
    private Font font = new Font("Arial", 0, 14);
    private PanelTable panelTable;
    private int click;

    private static int count;
    private static ArrayList<DSCanBo_Model> listDel_DSCanBo = new ArrayList<>();
    private static ArrayList<Integer> row_Del = new ArrayList<>();

    public DSCanBo_View() {
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
        xemChiTiet = new CanBo_Detail();
        controller = new DSCanBo_Controller();
        createUI();

    }

    public DSCanBo_View(String masukien) {
        this.masukien = masukien;
        this.diemDanh = false;
        xemChiTiet = new CanBo_Detail();
        controller = new DSCanBo_Controller();
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

    public DSCanBo_View(String masukien, boolean diemDanh) {
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
        xemChiTiet = new CanBo_Detail();
        controller = new DSCanBo_Controller();
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

    private void loadData() {
        canBoThamGia_Models = controller.load_TatCaCanBo(masukien);
        count = canBoThamGia_Models.size();
        panelTable = new PanelTable(DSCanBo_Controller.array2DSCanBo(canBoThamGia_Models), diemDanh ? columnNames_DD : columnNames, "Danh sách cán bộ tham dự");
        panelTable.getPanel().add(pnl_Filter);
        panelTable.getPanel().add(pnl_Search);
        TransitionPane.replacePane(this, panelTable);
        tbM_CanBo = panelTable.getTable().getModel();
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
                if (e.getClickCount() == 2) {
                    xem_CanBo();
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
                selectMany();
            }

            public void xem_CanBo() {
                try {
                    int row = panelTable.getTable().getSelectedRow();
                    if (row != -1) {
                        canBo_Model = CanBo_List.getController().load_CanBo(String.valueOf(tbM_CanBo.getValueAt(row, 1)));
                        xemChiTiet.setCanBo_Model(canBo_Model);
                        xemChiTiet.setText();
                        xemChiTiet.get_btnLuu().addActionListener((e) -> {
                            dialog.hide();
                        });
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
            canBoThamGia_Models = selectTypeView(typeView);
            setDataTable(DSCanBo_Controller.array2DSCanBo(canBoThamGia_Models));
        });
        FormatTextField.formatSearchField(txt_Search, panelTable.getTable());

    }

    private void selectAll() {
        if (click % 2 == 0) {
            row_Del.clear();
            listDel_DSCanBo.clear();
            for (int i = 0; i < panelTable.getTable().getRowCount(); i++) {
                listDel_DSCanBo.add(new DSCanBo_Model(
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
        listDel_DSCanBo.clear();
        for (int i = 0; i < panelTable.getTable().getRowCount(); i++) {
            if (panelTable.getTable().isSelectedRows(i, 0)) {
                listDel_DSCanBo.add(new DSCanBo_Model(
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

    public CanBo_Model getValue() {
        try {
            int row = panelTable.getTable().getSelectedRow();
            CanBo_Model cadre_Model = new CanBo_Model();
            if (row != -1) {
                cadre_Model.setMaCB(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 1)));
                cadre_Model.setTen(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 2)));
                cadre_Model.setEmail(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 3)));
                cadre_Model.setBoMon(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 4)));
                cadre_Model.setKhoa(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 5)));
                cadre_Model.setMaRFID(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 6)));
                return cadre_Model;
            }
        } catch (NullPointerException ex) {
        }
        return null;
    }

    public DSCanBo_Model getDSCanBo() {
        try {
            int row = panelTable.getTable().getSelectedRow();
            DSCanBo_Model cadre_Model = new DSCanBo_Model();
            if (row != -1) {
                cadre_Model.setMacb(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 1)));
                cadre_Model.setTen(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 2)));
                cadre_Model.setMathe(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 3)));
                cadre_Model.setDiemDanhVao(Time.valueOf(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 4))));
                cadre_Model.setDiemDanhRa(Time.valueOf(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 5))));
                cadre_Model.setDiemDanhTuDong(Boolean.valueOf(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 6))));
                return cadre_Model;
            }
        } catch (NullPointerException ex) {
        }
        return null;
    }

    public DSCanBo_Model getDSCanBoLite() {
        try {
            int row = panelTable.getTable().getSelectedRow();
            DSCanBo_Model dsCanBo = new DSCanBo_Model();
            if (row != -1) {
                dsCanBo.setMacb(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 1)));
                dsCanBo.setTen(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 2)));
                dsCanBo.setMathe(String.valueOf(panelTable.getTable().getModel().getValueAt(row, 3)));
                return dsCanBo;
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
        panelTable.getTable().setWidth(0, 50);
    }

    private ArrayList<DSCanBo_Model> selectTypeView(String typeView) {
        ArrayList<DSCanBo_Model> diemDanhCanBo_Models = new ArrayList<>();
        if (typeView.equals("Tất cả")) {
            diemDanhCanBo_Models = controller.load_TatCaCanBo(masukien);
        }
        if (typeView.equals("Vắng mặt(có đăng ký)")) {
            diemDanhCanBo_Models = controller.load_CanBoVangCoDK(masukien);

        }
        if (typeView.equals("Có mặt")) {
            diemDanhCanBo_Models = controller.load_CanBoCoMat(masukien);

        }
        if (typeView.equals("Vắng mặt(không có đăng ký)")) {
            diemDanhCanBo_Models = controller.load_CanBoVangKoDK(masukien);

        }
        if (typeView.equals("Đi trễ")) {
            diemDanhCanBo_Models = controller.load_CanBoDiTre(masukien);

        }
        if (typeView.equals("Điểm danh bằng máy")) {
            diemDanhCanBo_Models = controller.load_CanBoDDTuDong(masukien);

        }
        if (typeView.equals("Điểm danh thủ công")) {
            diemDanhCanBo_Models = controller.load_CanBoDDThuCong(masukien);

        }
        if (typeView.equals("Chưa đăng ký thẻ")) {
            diemDanhCanBo_Models = controller.load_CanBoNoRFID(masukien);

        }
        if (typeView.equals("Đã đăng ký thẻ")) {
            diemDanhCanBo_Models = controller.load_CanBoHaveRFID(masukien);
        }
        return diemDanhCanBo_Models;
    }

    public static DSCanBo_Controller getController() {
        return controller;
    }

    public String getMasukien() {
        return masukien;
    }

    public void setMasukien(String masukien) {
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

    public void setCbxFilter(String[] content) {
        cbxFilter.removeAllItems();
        for (String s : content) {
            cbxFilter.addItem(s);
        }
        cbxFilter.setMaximumRowCount(cbxFilter.getModel().getSize());
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

    public MyCustomTableModel getTableModel() {
        return getTable().getTableModel();
    }

    public static ArrayList<DSCanBo_Model> getListDel_DSCanBo() {
        return listDel_DSCanBo;
    }

    public static void setListDel_DSCanBo(ArrayList<DSCanBo_Model> listDel_DSCanBo) {
        DSCanBo_View.listDel_DSCanBo = listDel_DSCanBo;
    }

    public static ArrayList<Integer> getRow_Del() {
        return row_Del;
    }

    public static void setRow_Del(ArrayList<Integer> row_Del) {
        DSCanBo_View.row_Del = row_Del;
    }

    public static int getCount() {
        return count;
    }

    public void execute() {
        panelTable.executeTable();
    }

    public void refresh() {
        MyCustomTableModel tableModel = (MyCustomTableModel) getTable().getModel();
        ArrayList<DSCanBo_Model> list = getController().load_TatCaCanBo(masukien);
        tableModel.setDataVector(DSCanBo_Controller.array2DSCanBo(list));
        getTable().setCheckBox(0);
    }

    public void addDataVector(Object[][] dataVector) {
        MyCustomTableModel tableModel = (MyCustomTableModel) getTable().getModel();
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

    public void deleteRow(int row) {
        CustomTable.MyCustomTableModel tableModel = (CustomTable.MyCustomTableModel) getTable().getModel();
        tableModel.removeRow(row);
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
