/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinhvien.view;

import other.custom.DataString;
import other.custom.FormatTextField;
import dssinhvien.model.DSSinhVien_Model;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import sinhvien.controller.SinhVien_Controller;
import sinhvien.model.SinhVien_Model;
import javax.swing.table.DefaultTableModel;
import other.table.CustomTable;
import other.table.PanelTable;
import static resources.Resources.search_Icon;

/**
 *
 * @author Hiep_Nguyen
 */
public class SinhVien_List extends javax.swing.JPanel {

    private String[] columnNames = {"Tất cả", "MSCB", "Họ tên", "Email", "Lớp", "Ngành", "Khoa", "Niên Khóa", "Mã RFID"};
    private static SinhVien_Controller controller = new SinhVien_Controller();
    private boolean checkBox = false, initCombo = false;
    private PanelTable paneTable;
    private static ArrayList<SinhVien_Model> sinhVien_Model = new ArrayList<>();
    private static ArrayList<SinhVien_Model> listAdd_temp = new ArrayList<>();
    private static ArrayList<SinhVien_Model> listEdit_temp = new ArrayList<>();
    private static ArrayList<SinhVien_Model> listDel_temp = new ArrayList<>();
    private static ArrayList<Integer> row_Del = new ArrayList<>();
    private static int click = 0;

    public SinhVien_List() {
        initComponents();
        createUI();
        loadData();
    }

    private void createUI() {
        btn_TimKiem.setIcon(search_Icon);
        String[] aModel = DataString.itemCbxSinhVien;
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
        sinhVien_Model = controller.load_SinhVien();
        paneTable = new PanelTable(SinhVien_Controller.array2Object(sinhVien_Model), columnNames);
        paneTable.getPanel().add(pnl_CbxFilter);
        paneTable.getPanel().add(pnl_Search);
        paneTable.getTable().setCheckBox(0);
        paneTable.getTable().setWidth(0, 40);

        cbxFilter.addItemListener((e) -> {
            String typeView = cbxFilter.getModel().getSize() < 1 ? "Tất cả" : cbxFilter.getSelectedItem().toString();
            sinhVien_Model = selectTypeView(typeView);
            setDataTable(SinhVien_Controller.array2Object(sinhVien_Model));
            initCombo = true;
        });
        paneTable.getTable().setCheckBox(0);
        paneTable.getTable().setWidth(0, 60);
        paneTable.getTable().setWidth(1, 60);
        paneTable.getTable().setWidth(2, 120);
        paneTable.getTable().setWidth(3, 80);
        paneTable.getTable().setWidth(4, 100);
        paneTable.getTable().setWidth(5, 100);
        paneTable.getTable().setWidth(6, 100);
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
                listDel_temp.add(new SinhVien_Model(
                        String.valueOf(getTable().getModel().getValueAt(i, 1)),
                        String.valueOf(getTable().getModel().getValueAt(i, 2)),
                        String.valueOf(getTable().getModel().getValueAt(i, 3)),
                        String.valueOf(getTable().getModel().getValueAt(i, 4)),
                        String.valueOf(getTable().getModel().getValueAt(i, 5)),
                        String.valueOf(getTable().getModel().getValueAt(i, 6)),
                        String.valueOf(getTable().getModel().getValueAt(i, 7)),
                        String.valueOf(getTable().getModel().getValueAt(i, 8))
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
                listDel_temp.add(new SinhVien_Model(
                        String.valueOf(getTable().getModel().getValueAt(i, 1)),
                        String.valueOf(getTable().getModel().getValueAt(i, 2)),
                        String.valueOf(getTable().getModel().getValueAt(i, 3)),
                        String.valueOf(getTable().getModel().getValueAt(i, 4)),
                        String.valueOf(getTable().getModel().getValueAt(i, 5)),
                        String.valueOf(getTable().getModel().getValueAt(i, 6)),
                        String.valueOf(getTable().getModel().getValueAt(i, 7)),
                        String.valueOf(getTable().getModel().getValueAt(i, 8))
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

    public Object[][] convertList2Object(ArrayList<DSSinhVien_Model> dscb) {
        Object[][] ob = new Object[dscb.size()][4];
        for (int i = 0; i < ob.length; i++) {
            ob[i][0] = false;
            ob[i][1] = dscb.get(i).getMaSV();
            ob[i][2] = dscb.get(i).getTen();
            ob[i][3] = dscb.get(i).getMaRFID();
        }
        return ob;
    }

    public SinhVien_Model getValue() {
        try {
            int row = getTable().getSelectedRow();
            SinhVien_Model student_Model = new SinhVien_Model();
            if (row != -1) {
                student_Model.setMaSV(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 1)));
                student_Model.setTen(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 2)));
                student_Model.setEmail(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 3)));
                student_Model.setLop(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 4)));
                student_Model.setNganh(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 5)));
                student_Model.setKhoa(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 6)));
                student_Model.setNienKhoa(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 7)));
                student_Model.setMaRFID(String.valueOf(paneTable.getTable().getModel().getValueAt(row, 8)));
                return student_Model;
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
//        paneTable.getTable().setWidth(1, 50);
//        paneTable.getTable().setWidth(2, 120);
//        paneTable.getTable().setWidth(3, 120);
//        paneTable.getTable().setWidth(4, 200);
//        paneTable.getTable().setWidth(5, 80);
    }

    public static ArrayList<SinhVien_Model> getListSelected() {
        return listDel_temp;
    }

    public void refreshTable() {
        sinhVien_Model = controller.load_SinhVien();
        setDataTable(SinhVien_Controller.array2Object(sinhVien_Model));
    }

    private ArrayList<SinhVien_Model> selectTypeView(String typeView) {
        ArrayList<SinhVien_Model> SinhVien_Models = new ArrayList<>();
        if (typeView.equals("Tất cả")) {
            SinhVien_Models = controller.load_SinhVien();
        }
        if (typeView.equals("Chưa đăng ký thẻ")) {
            SinhVien_Models = controller.load_SinhVienNoRFID();
        }
        if (typeView.equals("Đã đăng ký thẻ")) {
            SinhVien_Models = controller.load_SinhVienHaveRFID();
        }
        return SinhVien_Models;
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

    public static SinhVien_Controller getController() {
        return controller;
    }

    public static void setController(SinhVien_Controller controller) {
        SinhVien_List.controller = controller;
    }

    public ArrayList<SinhVien_Model> getSinhVien_Model() {
        return sinhVien_Model;
    }

    public void setSinhVien_Model(ArrayList<SinhVien_Model> sinhVien_Model) {
        SinhVien_List.sinhVien_Model = sinhVien_Model;
    }

    public static ArrayList<SinhVien_Model> getListAdd_temp() {
        return listAdd_temp;
    }

    public static void setListAdd_temp(ArrayList<SinhVien_Model> listAdd_temp) {
        SinhVien_List.listAdd_temp = listAdd_temp;
    }

    public static ArrayList<SinhVien_Model> getListEdit_temp() {
        return listEdit_temp;
    }

    public static void setListEdit_temp(ArrayList<SinhVien_Model> listEdit_temp) {
        SinhVien_List.listEdit_temp = listEdit_temp;
    }

    public static ArrayList<SinhVien_Model> getListDel_temp() {
        return listDel_temp;
    }

    public static void setListDel_temp(ArrayList<SinhVien_Model> listDel_temp) {
        SinhVien_List.listDel_temp = listDel_temp;
    }

    public static ArrayList<Integer> getRow_Del() {
        return row_Del;
    }

    public static void setRow_Del(ArrayList<Integer> row_Del) {
        SinhVien_List.row_Del = row_Del;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        SinhVien_List.click = click;
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

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
        paneTable.getTable().setCheckBox(0);
    }
// </editor-fold>  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
        cbxFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Tất cả", "Đã có mã RFID", "Chưa có mã RFID"}));
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
    }// </editor-fold>                        

    private void cbxFilterActionPerformed(java.awt.event.ActionEvent evt) {
        if (initCombo) {
            PanelTable.setSelectAll();
        }
    }

    public void refresh() {
        for (int i = 0; i < getTable().getRowCount(); i++) {
            getTableModel().removeRow(i);
        }
        CustomTable.MyCustomTableModel model = getTableModel();
        model.getDataVector().removeAllElements();
        ArrayList<SinhVien_Model> load_CanBo = getController().load_SinhVien();
        model.setDataVector(SinhVien_Controller.array2Object(load_CanBo));
        model.fireTableDataChanged();
        paneTable.getTable().setCheckBox(0);
        paneTable.getTable().setWidth(0, 40);
        paneTable.getTable().setWidth(1, 50);
        paneTable.getTable().setWidth(2, 120);
        paneTable.getTable().setWidth(3, 120);
        paneTable.getTable().setWidth(4, 200);
        paneTable.getTable().setWidth(5, 80);
    }

    private void txt_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {

    }

    private void btn_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {

    }

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JComboBox<String> cbxFilter;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnl_CbxFilter;
    private javax.swing.JPanel pnl_Search;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration                   

    public void execute() {
        paneTable.executeTable();
    }

}
