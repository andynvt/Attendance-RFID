/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.view;

import sukien.model.SuKien_Model;
import sukien.controller.SuKien_Controller;
import other.custom.DataString;
import other.custom.FormatTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import other.custom.TransitionPane;
import other.table.CustomTable;
import other.table.CustomTable.MyCustomTableModel;
import other.table.PanelTable;
import static resources.Resources.search_Icon;

/**
 *
 * @author chuna
 */
public class SuKien_ListSK extends javax.swing.JPanel {

    private static SuKien_View VIEW = SuKien_View.getView();

    private TableModel tbM_SuKien;
    private String[] columnNames = {"Tất cả", "Mã Sự Kiện", "Tên sự kiện", "Ngày diễn ra", "Giờ bắt đầu", "Giờ kết thúc", "Đã điểm danh"};
    private static SuKien_Controller controller = new SuKien_Controller();
    private ArrayList<SuKien_Model> suKien_Models = new ArrayList<>();
    private int row;
    private boolean checkBox;
    private PanelTable panelTable;
    private int type = MANAGEMENT;

    public final static int MANAGEMENT = 1, ATTENDENCE = 2, REPORT = 3;

    private static ArrayList<SuKien_Model> listAdd_temp = new ArrayList<>();
    private static ArrayList<SuKien_Model> listEdit_temp = new ArrayList<>();
    private static ArrayList<SuKien_Model> listDel_temp = new ArrayList<>();

    private static ArrayList<Integer> row_Del = new ArrayList<>();

    public SuKien_ListSK(int type) {
        initComponents();
        this.type = type;
        btn_TimKiem.setIcon(search_Icon);
        createUI();
        loadData();
    }

    public void loadDaDiemDanh() {
        suKien_Models = controller.load_SuKienDaDiemDanh();
        setCbxFilter(DataString.itemCbxThongKe);
    }

    public void loadChuaDiemDanh() {
        suKien_Models = controller.load_SuKienChuaDiemDanh();
        setCbxFilter(DataString.itemCbxDiemDanh);
    }

    public void loadCoTheDiemDanh() {
        suKien_Models = controller.load_SuKienCoTheDiemDanh();
        setCbxFilter(DataString.itemCbxDiemDanh);
    }

    public void loadTatCa() {
        suKien_Models = controller.load_SuKien();
        setCbxFilter(DataString.itemCbxSuKien);
    }

    public static void clearListAdd() {
        listAdd_temp.clear();
    }

    public static void clearListDel() {
        listDel_temp.clear();
    }

    public static void clearListUpdate() {
        listEdit_temp.clear();
    }

    public static void clearListRowDel() {
        row_Del.clear();
    }

    private void createUI() {

    }

    public void loadData() {
        switch (type) {
            case MANAGEMENT:
                loadTatCa();
                break;
            case ATTENDENCE:
                loadChuaDiemDanh();
                break;
            case REPORT:
                loadDaDiemDanh();
                break;
        }
        panelTable = new PanelTable(SuKien_Controller.array2Object(suKien_Models), columnNames);
        panelTable.getPanel().add(pnl_CbxFilter);
        panelTable.getPanel().add(pnl_Search);
        tbM_SuKien = panelTable.getTable().getModel();
        panelTable.getTable().setCheckBox(0);
        panelTable.getTable().setCellCheckBox(6);
        panelTable.getTable().setWidth(0, 50);
        cbxFilter.addItemListener((e) -> {
            String typeView = cbxFilter.getModel().getSize() < 1 ? "Tất cả" : cbxFilter.getSelectedItem().toString();
            suKien_Models = selectTypeView(typeView);
            setDataTable(SuKien_Controller.array2Object(suKien_Models));
        });
        FormatTextField.formatSearchField(txt_TimKiem, panelTable.getTable());
        TransitionPane.replacePane(pnl_Content, panelTable);
        txt_TimKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                panelTable.getComboBoxItem().setSelectedIndex(0);
                panelTable.initFilterAndButtons();
            }

        });
        getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            private int click = 0;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (click % 2 == 0) {
                    selectAll();
                    click++;
                } else {
                    getTable().clearSelection();
                    row_Del.clear();
                    listDel_temp.clear();
                }
            }
        });
        getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectMany();
            }
        });
    }

    private void selectAll() {
        listDel_temp.clear();
        row_Del.clear();
        for (int i = 0; i < getTable().getRowCount(); i++) {
            listDel_temp.add(new SuKien_Model(
                    getTable().getModel().getValueAt(i, 1).toString(),
                    getTable().getModel().getValueAt(i, 2).toString(),
                    Date.valueOf(getTable().getModel().getValueAt(i, 3).toString()),
                    Time.valueOf(getTable().getModel().getValueAt(i, 4).toString()),
                    Time.valueOf(getTable().getModel().getValueAt(i, 5).toString()),
                    Boolean.valueOf(getTable().getModel().getValueAt(i, 6).toString())
            ));
            row_Del.add(i);
        }
    }

    private void selectMany() {
        row_Del.clear();
        listDel_temp.clear();
        for (int i = 0; i < getTable().getRowCount(); i++) {
            if (getTable().isSelectedRows(i, 0)) {
                listDel_temp.add(new SuKien_Model(
                        getTable().getModel().getValueAt(i, 1).toString(),
                        getTable().getModel().getValueAt(i, 2).toString(),
                        Date.valueOf(getTable().getModel().getValueAt(i, 3).toString()),
                        Time.valueOf(getTable().getModel().getValueAt(i, 4).toString()),
                        Time.valueOf(getTable().getModel().getValueAt(i, 5).toString()),
                        Boolean.valueOf(getTable().getModel().getValueAt(i, 6).toString())
                ));
                row_Del.add(i);
            }
        }
        boolean selectAll = row_Del.size() == getTable().getRowCount();
        if (selectAll && !getTable().getHeader().isClicked()) {
            getTable().getHeader().setMousePressed(true);
            getTable().getHeader().headerClick();
            getTable().getHeader().repaintCheckBox(getTable());
        } else if (!selectAll && getTable().getHeader().isClicked()) {
            getTable().getHeader().setMousePressed(true);
            getTable().getHeader().headerClick();
            getTable().getHeader().repaintCheckBox(getTable());
            row_Del.forEach((t) -> {
                getTable().setValueAt(true, t, 0);
            });
        }
    }

    private void setDataTable(Object[][] data) {
        DefaultTableModel dm = (DefaultTableModel) panelTable.getTable().getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        dm.setDataVector(data, columnNames);
        panelTable.getTable().setCheckBox(0);
        panelTable.getTable().setCellCheckBox(6);
        panelTable.getTable().setCellCheckBox(7);
        panelTable.getTable().setWidth(0, 50);
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    private ArrayList<SuKien_Model> selectTypeView(String typeView) {
        ArrayList<SuKien_Model> list = new ArrayList<>();
        if (typeView.equals("Tất cả")) {
            list = controller.load_SuKien();
        }
        if (typeView.equals("Bắt buộc")) {
            list = controller.load_SuKienBatBuoc();
        }
        if (typeView.equals("Không bắt buộc")) {
            list = controller.load_SuKienKhongBatBuoc();
        }
        if (typeView.equals("Đã điểm danh")) {
            list = controller.load_SuKienDaDiemDanh();
        }
        if (typeView.equals("Chưa điểm danh")) {
            list = controller.load_SuKienChuaDiemDanh();
        }
        return list;
    }

    public ArrayList<SuKien_Model> getSuKien_Models() {
        return suKien_Models;
    }

    public void setSuKien_Models(ArrayList<SuKien_Model> suKien_Models) {
        this.suKien_Models = suKien_Models;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_CbxFilter = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxFilter = new javax.swing.JComboBox<>();
        pnl_Search = new javax.swing.JPanel();
        txt_TimKiem = new javax.swing.JTextField();
        btn_TimKiem = new javax.swing.JButton();
        pnl_Content = new javax.swing.JPanel();

        pnl_CbxFilter.setOpaque(false);
        pnl_CbxFilter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 12));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Bộ lọc:");
        pnl_CbxFilter.add(jLabel2);

        cbxFilter.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbxFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Bắt buộc", "Không bắt buộc", " " }));
        cbxFilter.setBorder(null);
        cbxFilter.setOpaque(false);
        cbxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterActionPerformed(evt);
            }
        });
        pnl_CbxFilter.add(cbxFilter);

        pnl_Search.setOpaque(false);
        pnl_Search.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));

        txt_TimKiem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_TimKiem.setForeground(new java.awt.Color(102, 102, 102));
        txt_TimKiem.setText("Nhập Mã sự kiện, Tên sự kiện, ");
        txt_TimKiem.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        txt_TimKiem.setPreferredSize(new java.awt.Dimension(270, 36));
        txt_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_TimKiemKeyReleased(evt);
            }
        });
        pnl_Search.add(txt_TimKiem);

        btn_TimKiem.setBackground(new java.awt.Color(102, 153, 255));
        btn_TimKiem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_TimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btn_TimKiem.setBorderPainted(false);
        btn_TimKiem.setContentAreaFilled(false);
        btn_TimKiem.setFocusPainted(false);
        btn_TimKiem.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_TimKiem.setOpaque(true);
        btn_TimKiem.setPreferredSize(new java.awt.Dimension(40, 36));
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });
        btn_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_TimKiemKeyReleased(evt);
            }
        });
        pnl_Search.add(btn_TimKiem);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnl_Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 15));
        pnl_Content.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    public SuKien_Model getValue() {
        SuKien_Model event_Model_ = new SuKien_Model();
        try {
            row = panelTable.getTable().getSelectedRow();
            if (row != -1) {
                event_Model_.setMaSK(String.valueOf(panelTable.getTable().getValueAt(row, 1)));
                event_Model_.setTenSK(String.valueOf(panelTable.getTable().getValueAt(row, 2)));
                Date d = Date.valueOf(String.valueOf(panelTable.getTable().getValueAt(row, 3)));
                Time bd = Time.valueOf(String.valueOf(panelTable.getTable().getValueAt(row, 4)));
                Time kt = Time.valueOf(String.valueOf(panelTable.getTable().getValueAt(row, 5)));
                event_Model_.setNgayDienRa(d);
                event_Model_.setGioDiemDanhVao(bd);
                event_Model_.setGioDiemDanhRa(kt);
                event_Model_.setDiemDanh(Boolean.valueOf(String.valueOf(panelTable.getTable().getValueAt(row, 6))));
            }
        } catch (NullPointerException ex) {
        }
        return event_Model_;
    }

    private void cbxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterActionPerformed

    }//GEN-LAST:event_cbxFilterActionPerformed

    private void txt_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TimKiemKeyReleased
        btn_TimKiem.doClick();
    }//GEN-LAST:event_txt_TimKiemKeyReleased

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        panelTable.getTable().setRowSorter(CustomTable.Filter(txt_TimKiem.getText(), tbM_SuKien));
    }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void btn_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_TimKiemKeyReleased

    }//GEN-LAST:event_btn_TimKiemKeyReleased

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
     }//GEN-LAST:event_formComponentShown

    public TableModel getTableModel_SuKien() {
        return tbM_SuKien;
    }

    public void setTableModel_SuKien(TableModel tbM_SuKien) {
        this.tbM_SuKien = tbM_SuKien;
    }

    public PanelTable getPaneTable() {
        return panelTable;
    }

    public CustomTable getTable() {
        return panelTable.getTable();
    }

    public void setPaneTable(PanelTable table) {
        this.panelTable = table;
    }

    public JPanel getContent() {
        return pnl_Content;
    }

    public void setPnl_Content(JPanel pnl_Content) {
        this.pnl_Content = pnl_Content;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void refreshTable() {
        panelTable.executeTable();
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public static SuKien_Controller getController() {
        return controller;
    }

    public static void setController(SuKien_Controller controller) {
        SuKien_ListSK.controller = controller;
    }

    public static ArrayList<SuKien_Model> getListAdd_temp() {
        return listAdd_temp;
    }

    public static void setListAdd_temp(ArrayList<SuKien_Model> listAdd_temp) {
        SuKien_ListSK.listAdd_temp = listAdd_temp;
    }

    public static ArrayList<SuKien_Model> getListEdit_temp() {
        return listEdit_temp;
    }

    public static void setListEdit_temp(ArrayList<SuKien_Model> listEdit_temp) {
        SuKien_ListSK.listEdit_temp = listEdit_temp;
    }

    public static ArrayList<SuKien_Model> getListDel_temp() {
        return listDel_temp;
    }

    public static void setListDel_temp(ArrayList<SuKien_Model> listDel_temp) {
        SuKien_ListSK.listDel_temp = listDel_temp;
    }

    public static ArrayList<Integer> getRow_Del() {
        return row_Del;
    }

    public static void setRow_Del(ArrayList<Integer> row_Del) {
        SuKien_ListSK.row_Del = row_Del;
    }

    public void execute() {
        panelTable.executeTable();
    }

    public void setCbxFilter(String[] content) {
        cbxFilter.removeAllItems();
        for (String s : content) {
            cbxFilter.addItem(s);
        }
        cbxFilter.setMaximumRowCount(cbxFilter.getModel().getSize());
    }

    public void refresh() {
        MyCustomTableModel model = (MyCustomTableModel) getTable().getModel();
        ArrayList<SuKien_Model> load_SinhVien = SuKien_ListSK.getController().load_SuKien();
        model.setDataVector(SuKien_Controller.array2Object(load_SinhVien));
        model.fireTableDataChanged();
        getTable().setCheckBox(0);
        getTable().setCellCheckBox(6);
        getTable().setWidth(0, 50);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JComboBox<String> cbxFilter;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnl_CbxFilter;
    private javax.swing.JPanel pnl_Content;
    private javax.swing.JPanel pnl_Search;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables

}
