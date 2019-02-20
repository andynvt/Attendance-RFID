/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canbo.view;

import canbo.model.CanBo_Model;
import canbo.controller.CanBo_Controller;
import dscanbo.model.DSCanBo_Model;
import other.custom.DataString;
import other.custom.FormatTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import other.table.CustomTable;
import other.table.PanelTable;
import static resources.Resources.search_Icon;

/**
 *
 * @author chuna
 */
public class CanBo_List extends javax.swing.JPanel {

    private String[] columnNames = {"Tất cả", "MSCB", "Họ tên", "Email", "Bộ môn", "Khoa", "Mã RFID"};
    private static CanBo_Controller controller = new CanBo_Controller();
    private boolean checkBox = false, initCombo = false;
    private PanelTable paneTable;
    private static ArrayList<CanBo_Model> canBo_Model = new ArrayList<>();
//    private static ArrayList<CanBo_Model> listAdd = new ArrayList<>();
    private static ArrayList<CanBo_Model> listAdd_temp = new ArrayList<>();
    private static ArrayList<CanBo_Model> listEdit_temp = new ArrayList<>();
    private static ArrayList<CanBo_Model> listDel_temp = new ArrayList<>();
    private static ArrayList<Integer> row_Del = new ArrayList<>();
    private static int click = 0;

    public CanBo_List() {
        initComponents();
        createUI();
        loadData();
    }

    private void createUI() {
        btn_TimKiem.setIcon(search_Icon);
        String[] aModel = DataString.itemCbxCanBo;
        cbxFilter.removeAllItems();
        Arrays.asList(aModel).forEach((t) -> {
            cbxFilter.addItem(t);
        });
        cbxFilter.setMaximumRowCount(cbxFilter.getModel().getSize());
        txt_TimKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                paneTable.getComboBoxItem().setSelectedIndex(0);
                paneTable.initFilterAndButtons();
            }

        });
    }

    public void loadData() {
        canBo_Model = controller.load_CanBo();
        paneTable = new PanelTable(CanBo_Controller.array2Object(canBo_Model), columnNames);
        paneTable.getPanel().add(pnl_CbxFilter);
        paneTable.getPanel().add(pnl_Search);
        paneTable.getTable().setCheckBox(0);
        paneTable.getTable().setWidth(0, 40);

        cbxFilter.addItemListener((e) -> {
            String typeView = cbxFilter.getModel().getSize() < 1 ? "Tất cả" : cbxFilter.getSelectedItem().toString();
            canBo_Model = selectTypeView(typeView);
            setDataTable(CanBo_Controller.array2Object(canBo_Model));
            initCombo = true;
        });
        paneTable.getTable().setCheckBox(0);
        paneTable.getTable().setWidth(0, 40);
        paneTable.getTable().setWidth(1, 50);
        paneTable.getTable().setWidth(2, 120);
        paneTable.getTable().setWidth(3, 120);
        paneTable.getTable().setWidth(4, 200);
        paneTable.getTable().setWidth(5, 80);
        FormatTextField.formatSearchField(txt_TimKiem, paneTable.getTable());
        this.removeAll();
        this.add(paneTable);
        this.validate();
        this.repaint();

        getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectAll();
                click++;
            }
        });
        getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectMany();
            }
        });

        getTable().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectMany();
                }
            }
        });
    }

    private void selectAll() {
        if (click % 2 == 0) {
            row_Del.clear();
            listDel_temp.clear();
            for (int i = 0; i < getTable().getRowCount(); i++) {
                listDel_temp.add(new CanBo_Model(
                        String.valueOf(getTable().getModel().getValueAt(i, 1)),
                        String.valueOf(getTable().getModel().getValueAt(i, 2)),
                        String.valueOf(getTable().getModel().getValueAt(i, 3)),
                        String.valueOf(getTable().getModel().getValueAt(i, 4)),
                        String.valueOf(getTable().getModel().getValueAt(i, 5)),
                        String.valueOf(getTable().getModel().getValueAt(i, 6))
                ));
                row_Del.add(i);
            }
        }
    }

    private void selectMany() {
        row_Del.clear();
        listDel_temp.clear();
        for (int i = 0; i < getTable().getRowCount(); i++) {
            if (getTable().isSelectedRows(i, 0)) {
                listDel_temp.add(new CanBo_Model(
                        String.valueOf(getTable().getModel().getValueAt(i, 1)),
                        String.valueOf(getTable().getModel().getValueAt(i, 2)),
                        String.valueOf(getTable().getModel().getValueAt(i, 3)),
                        String.valueOf(getTable().getModel().getValueAt(i, 4)),
                        String.valueOf(getTable().getModel().getValueAt(i, 5)),
                        String.valueOf(getTable().getModel().getValueAt(i, 6))
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

    public Object[][] convertList2Object(ArrayList<DSCanBo_Model> dscb) {
        Object[][] ob = new Object[dscb.size()][4];
        for (int i = 0; i < ob.length; i++) {
            ob[i][0] = false;
            ob[i][1] = dscb.get(i).getMaCB();
            ob[i][2] = dscb.get(i).getTen();
            ob[i][3] = dscb.get(i).getMaRFID();
        }
        return ob;
    }

    public CanBo_Model getValue() {
        try {
            int row = getTable().getSelectedRow();
            CanBo_Model cadre_Model = new CanBo_Model();
            if (row != -1) {
                cadre_Model.setMaCB(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 1)));
                cadre_Model.setTen(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 2)));
                cadre_Model.setEmail(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 3)));
                cadre_Model.setBoMon(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 4)));
                cadre_Model.setKhoa(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 5)));
                cadre_Model.setMaRFID(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 6)));
                return cadre_Model;
            }
        } catch (NullPointerException ex) {
        }
        return null;
    }

    public void setDataTable(Object[][] data) {
        DefaultTableModel dm = (DefaultTableModel) paneTable.getTable().getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        dm.setDataVector(data, columnNames);
        paneTable.getTable().setModel(dm);
        paneTable.getTable().setCheckBox(0);
        paneTable.getTable().setWidth(0, 40);
        paneTable.getTable().setWidth(1, 50);
        paneTable.getTable().setWidth(2, 120);
        paneTable.getTable().setWidth(3, 120);
        paneTable.getTable().setWidth(4, 200);
        paneTable.getTable().setWidth(5, 80);
    }

    public void refreshTable() {
        canBo_Model = controller.load_CanBo();
        setDataTable(CanBo_Controller.array2Object(canBo_Model));
    }

    private ArrayList<CanBo_Model> selectTypeView(String typeView) {
        ArrayList<CanBo_Model> CanBo_Models = new ArrayList<>();
        if (typeView.equals("Tất cả")) {
            CanBo_Models = controller.load_CanBo();
        }
        if (typeView.equals("Chưa đăng ký thẻ")) {
            CanBo_Models = controller.load_CanBoNoRFID();
        }
        if (typeView.equals("Đã đăng ký thẻ")) {
            CanBo_Models = controller.load_CanBoHaveRFID();
        }
        return CanBo_Models;
    }

    // <editor-fold defaultstate="collapsed" desc="Get & SET">    
    public CustomTable.MyCustomTableModel getTableModel() {
        return paneTable.getCustomTableModel();
    }

    public PanelTable getPaneTable() {
        return paneTable;
    }

    public CustomTable getTable() {
        return paneTable.getTable();
    }

    public static CanBo_Controller getController() {
        return controller;
    }

    public void setcontroller(CanBo_Controller controller) {
        CanBo_List.controller = controller;
    }

    public ArrayList<CanBo_Model> getCanBo_Model() {
        return canBo_Model;
    }

    public void setCanBo_Model(ArrayList<CanBo_Model> canBo_Model) {
        CanBo_List.canBo_Model = canBo_Model;
    }

    public static ArrayList<CanBo_Model> getListAdd_temp() {
        return listAdd_temp;
    }

    public static void setListAdd_temp(ArrayList<CanBo_Model> listAdd_temp) {
        CanBo_List.listAdd_temp = listAdd_temp;
    }

    public static ArrayList<CanBo_Model> getListEdit_temp() {
        return listEdit_temp;
    }

    public static void setListEdit_temp(ArrayList<CanBo_Model> listEdit_temp) {
        CanBo_List.listEdit_temp = listEdit_temp;
    }

    public static ArrayList<CanBo_Model> getListDel_temp() {
        return listDel_temp;
    }

    public static void setListDel_temp(ArrayList<CanBo_Model> listDel_temp) {
        CanBo_List.listDel_temp = listDel_temp;
    }

    public static ArrayList<Integer> getRow_Del() {
        return row_Del;
    }

    public static void setRow_Del(ArrayList<Integer> row_Del) {
        CanBo_List.row_Del = row_Del;
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
//    public static ArrayList<CanBo_Model> getListAdd() {
//        return listAdd;
//    }
//
//    public static void setListAdd(ArrayList<CanBo_Model> listAdd) {
//        CanBo_List.listAdd = listAdd;
//    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        CanBo_List.click = click;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
        paneTable.getTable().setCheckBox(0);
    }

    public static ArrayList<CanBo_Model> getListSelected() {
        return listDel_temp;
    }
// </editor-fold>  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_CbxFilter = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxFilter = new javax.swing.JComboBox<>();
        pnl_Search = new javax.swing.JPanel();
        txt_TimKiem = new javax.swing.JTextField();
        btn_TimKiem = new javax.swing.JButton();

        pnl_CbxFilter.setOpaque(false);
        pnl_CbxFilter.setPreferredSize(new java.awt.Dimension(187, 45));
        pnl_CbxFilter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 12));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Bộ lọc:");
        pnl_CbxFilter.add(jLabel2);

        cbxFilter.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbxFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã có mã RFID", "Chưa có mã RFID" }));
        cbxFilter.setBorder(null);
        cbxFilter.setLightWeightPopupEnabled(false);
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
        txt_TimKiem.setText("Nhập MSCB, Tên, Bộ môn, Khoa");
        txt_TimKiem.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        txt_TimKiem.setPreferredSize(new java.awt.Dimension(220, 36));
        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });
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

        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 15));
        setLayout(new java.awt.GridLayout(1, 0));
    }// </editor-fold>//GEN-END:initComponents

    private void cbxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterActionPerformed
        if (initCombo) {
            PanelTable.setSelectAll();
        }
    }//GEN-LAST:event_cbxFilterActionPerformed
    public void refresh() {
        for (int i = 0; i < getTable().getRowCount(); i++) {
            getTableModel().removeRow(i);
        }
        CustomTable.MyCustomTableModel model = getTableModel();
        model.getDataVector().removeAllElements();
        ArrayList<CanBo_Model> load_CanBo = getController().load_CanBo();
        model.setDataVector(CanBo_Controller.array2Object(load_CanBo));
        model.fireTableDataChanged();

        paneTable.getTable().setCheckBox(0);
        paneTable.getTable().setWidth(0, 40);
        paneTable.getTable().setWidth(1, 50);
        paneTable.getTable().setWidth(2, 120);
        paneTable.getTable().setWidth(3, 120);
        paneTable.getTable().setWidth(4, 200);
        paneTable.getTable().setWidth(5, 80);
    }
    private void txt_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TimKiemKeyReleased

    }//GEN-LAST:event_txt_TimKiemKeyReleased

    private void btn_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_TimKiemKeyReleased

    }//GEN-LAST:event_btn_TimKiemKeyReleased

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
     }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TimKiemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JComboBox<String> cbxFilter;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnl_CbxFilter;
    private javax.swing.JPanel pnl_Search;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables

    public void execute() {
        paneTable.executeTable();
    }

}
