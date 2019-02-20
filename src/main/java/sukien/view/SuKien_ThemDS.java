/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.view;

import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import canbo.model.CanBo_Model;
import canbo.view.CanBo_List;
import other.custom.GradientButton;
import dscanbo.controller.DSCanBo_Controller;
import dscanbo.model.DSCanBo_Model;
import dscanbo.view.DSCanBo_View;
import dssinhvien.controller.DSSinhVien_Controller;
import dssinhvien.model.DSSinhVien_Model;
import dssinhvien.view.DSSinhVien_View;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import other.custom.Alert;
import other.custom.TransitionPane;
import resources.Resources;
import sinhvien.model.SinhVien_Model;
import sinhvien.view.SinhVien_List;
import sukien.controller.SuKien_Controller;

/**
 *
 * @author chuna
 */
public class SuKien_ThemDS extends javax.swing.JDialog implements ActionListener {

    private String[] columnNamesSV = {"Mã sinh viên", "Họ tên", "Mã thẻ"};
    private String[] columnNamesCB = {"Mã cán bộ", "Họ tên", "Mã thẻ"};

    final static int CANBO = 1;
    final static int SINHVIEN = 2;

    private static SuKien_ThemDS themDS;
    private static SuKien_View VIEW = SuKien_View.getView();
    private int choose;
    private String mask;
    private static SuKien_ChonKieuNhap chonDanhSach;
    private CanBo_List canBo_List;
    private SinhVien_List sinhVien_List;
    private GradientButton btn_Them, btn_Huy;

    private ArrayList<DSCanBo_Model> dSCanBo_Models = new ArrayList<>();
    private ArrayList<CanBo_Model> canBo_Models = new ArrayList<>();
    private ArrayList<SinhVien_Model> sinhVien_Models = new ArrayList<>();
    private ArrayList<DSSinhVien_Model> dSSinhVien_Models = new ArrayList<>();
    private ArrayList<DSCanBo_Model> dSCanBoTonTai;
    private ArrayList<DSSinhVien_Model> dSSinhVienTonTai;
    private JDialog dialog;
    private String thatBai;

    public SuKien_ThemDS(String mask) {
        initComponents();
        themDS = this;
        this.mask = mask;
        canBo_List = new CanBo_List();
        sinhVien_List = new SinhVien_List();
        createUI();
    }

    private void createUI() {
        chonDanhSach = new SuKien_ChonKieuNhap(SuKien_ChonKieuNhap.HETHONG);
        chonDanhSach.setLocationRelativeTo(VIEW);
        chonDanhSach.setVisible(true);
        btn_Them = new GradientButton(bg_Color1, bg_Color2, Resources.them_Icon);
        btn_Huy = new GradientButton(bg_Color1, bg_Color2, Resources.huy_Icon);
        pnl_Task.add(btn_Them);
        pnl_Task.add(btn_Huy);
        btn_Them.addActionListener(this);
        btn_Huy.addActionListener(this);
        pack();

        dialog = new JDialog();
        dialog.setSize(new Dimension(507, 175));
        pnl_Duplicate.setVisible(false);
        dialog.getContentPane().add(pnl_Confirm);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(this);
    }

    public static SuKien_ThemDS getThemDS() {
        return themDS;
    }

    public void setChoose(int choose) {
        this.choose = choose;
        showTable();
    }

    public void showTable() {
        switch (choose) {
            case CANBO: {
                showCanBoList();
                pack();
                break;
            }
            case SINHVIEN: {
                showSinhVienList();
                pack();
                break;
            }
        }
    }

    public void showCanBoList() {
        pnl_Table.removeAll();
        pnl_Table.add(canBo_List);
        canBo_List.execute();
        pnl_Table.validate();
        pnl_Table.repaint();
        canBo_Models = CanBo_List.getListSelected();
    }

    public void showSinhVienList() {
        pnl_Table.removeAll();
        pnl_Table.add(sinhVien_List);
        sinhVien_List.execute();
        pnl_Table.validate();
        pnl_Table.repaint();
        sinhVien_Models = SinhVien_List.getListSelected();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Confirm = new javax.swing.JPanel();
        lblWarning = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        btnSkip = new javax.swing.JButton();
        btnReplace = new javax.swing.JButton();
        btnDetails = new javax.swing.JButton();
        pnl_Duplicate = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTonTai = new javax.swing.JTable();
        pnl_Task = new javax.swing.JPanel();
        pnl_Table = new javax.swing.JPanel();

        pnl_Confirm.setBackground(new java.awt.Color(255, 255, 255));

        lblWarning.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblWarning.setText("Có 1 cán bộ đã tồn tại trong hệ thống, bạn có muốn ghi đè lên dữ liệu cũ?");
        lblWarning.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        btnCancel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTitle.setText("Import 14 cán bộ từ file excel");
        lblTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        btnSkip.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSkip.setText("Bỏ qua");
        btnSkip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSkipActionPerformed(evt);
            }
        });

        btnReplace.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnReplace.setText("Ghi đè");
        btnReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReplaceActionPerformed(evt);
            }
        });

        btnDetails.setText("Chi tiết");
        btnDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailsActionPerformed(evt);
            }
        });

        tblTonTai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblTonTai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblTonTai.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tblTonTai.setFillsViewportHeight(true);
        tblTonTai.setFocusable(false);
        tblTonTai.setRequestFocusEnabled(false);
        tblTonTai.setRowHeight(20);
        tblTonTai.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTonTai.setShowHorizontalLines(false);
        tblTonTai.setShowVerticalLines(false);
        jScrollPane2.setViewportView(tblTonTai);

        javax.swing.GroupLayout pnl_DuplicateLayout = new javax.swing.GroupLayout(pnl_Duplicate);
        pnl_Duplicate.setLayout(pnl_DuplicateLayout);
        pnl_DuplicateLayout.setHorizontalGroup(
            pnl_DuplicateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DuplicateLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2)
                .addGap(0, 0, 0))
        );
        pnl_DuplicateLayout.setVerticalGroup(
            pnl_DuplicateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_ConfirmLayout = new javax.swing.GroupLayout(pnl_Confirm);
        pnl_Confirm.setLayout(pnl_ConfirmLayout);
        pnl_ConfirmLayout.setHorizontalGroup(
            pnl_ConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ConfirmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_ConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_ConfirmLayout.createSequentialGroup()
                        .addGroup(pnl_ConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ConfirmLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnl_ConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_ConfirmLayout.createSequentialGroup()
                                .addComponent(btnDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReplace)
                                .addGap(18, 18, 18)
                                .addComponent(btnSkip)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel))
                            .addComponent(pnl_Duplicate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25))))
        );
        pnl_ConfirmLayout.setVerticalGroup(
            pnl_ConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ConfirmLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_Duplicate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pnl_ConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDetails)
                    .addComponent(btnReplace)
                    .addComponent(btnSkip)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pnl_Task.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_Task.setPreferredSize(new java.awt.Dimension(0, 45));
        pnl_Task.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 2));

        pnl_Table.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Task, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
            .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Task, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        TransitionPane.closeDialogBox();
        dialog.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSkipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkipActionPerformed
        if (choose == CANBO) {
            int countSuccess = 0;
            DSCanBo_View.getController().add_canBoThamGia(dSCanBo_Models, mask);
            updateCBTable();
            if (!DSCanBo_Controller.getAdd_Failed().isEmpty()) {
                thatBai = "";
                SuKien_Controller.getAdd_Failed().forEach((t) -> {
                    thatBai += "Thêm cán bộ có mã " + t.getMaSK() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSCanBo_Models.size() - DSCanBo_Controller.getAdd_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " sinh viên.", "Thông báo");
            }
        } else if (choose == SINHVIEN) {
            int countSuccess = 0;
            DSSinhVien_View.getController().add_sinhVienThamGia(dSSinhVien_Models, mask);
            updateDSSVTable();
            if (!DSSinhVien_Controller.getAdd_Failed().isEmpty()) {
                thatBai = "";
                DSSinhVien_Controller.getAdd_Failed().forEach((t) -> {
                    thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSSinhVien_Models.size() - DSSinhVien_Controller.getAdd_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " sinh viên.", "Thông báo");
            }
        }
    }//GEN-LAST:event_btnSkipActionPerformed

    private void btnReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplaceActionPerformed
        if (choose == CANBO) {
            int countSuccess = 0;
            DSCanBo_View.getController().update_canBoThamGia(dSCanBoTonTai, mask);
            DSCanBo_View.getController().add_canBoThamGia(dSCanBo_Models, mask);
            updateCBTable();
            if (!DSCanBo_Controller.getAdd_Failed().isEmpty()) {
                thatBai = "";
                DSCanBo_Controller.getAdd_Failed().forEach((t) -> {
                    thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSCanBo_Models.size() + DSCanBo_Controller.getAdd_Failed().size();
            }
            if (!DSCanBo_Controller.getUpdate_Failed().isEmpty()) {
                thatBai = "";
                DSCanBo_Controller.getUpdate_Failed().forEach((t) -> {
                    thatBai += "Ghi đè cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSCanBoTonTai.size() + DSCanBo_Controller.getUpdate_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " cán bộ.", "Thông báo");
            }
        } else if (choose == SINHVIEN) {
            int countSuccess = 0;
            DSSinhVien_View.getController().update_sinhVienThamGia(dSSinhVienTonTai, mask);
            DSSinhVien_View.getController().add_sinhVienThamGia(dSSinhVien_Models, mask);
            updateDSSVTable();
            if (!DSSinhVien_Controller.getAdd_Failed().isEmpty()) {
                thatBai = "";
                DSSinhVien_Controller.getAdd_Failed().forEach((t) -> {
                    thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSSinhVien_Models.size() - DSSinhVien_Controller.getAdd_Failed().size();
            }
            if (!DSSinhVien_Controller.getUpdate_Failed().isEmpty()) {
                thatBai = "";
                DSSinhVien_Controller.getUpdate_Failed().forEach((t) -> {
                    thatBai += "Ghi đè sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSSinhVienTonTai.size() - DSSinhVien_Controller.getUpdate_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " sinh viên.", "Thông báo");
            }
        }
    }//GEN-LAST:event_btnReplaceActionPerformed

    private void btnDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailsActionPerformed
        if (pnl_Duplicate.isVisible()) {
            pnl_Duplicate.setVisible(false);
            dialog.setSize(new Dimension(507, 175));
            dialog.validate();
            dialog.repaint();
            dialog.setLocationRelativeTo(this);
        } else {
            pnl_Duplicate.setVisible(true);
            dialog.setSize(new Dimension(507, 300));
            dialog.validate();
            dialog.repaint();
            dialog.setLocationRelativeTo(this);
        }
    }//GEN-LAST:event_btnDetailsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDetails;
    private javax.swing.JButton btnReplace;
    private javax.swing.JButton btnSkip;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblWarning;
    private javax.swing.JPanel pnl_Confirm;
    private javax.swing.JPanel pnl_Duplicate;
    private javax.swing.JPanel pnl_Table;
    private javax.swing.JPanel pnl_Task;
    private javax.swing.JTable tblTonTai;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btn_Them)) {
            if (choose == CANBO) {
                checkDupCanBo();
                importCB2Table();

            } else if (choose == SINHVIEN) {
                checkDupSinhVien();
                importSV2Table();
            }
        } else if (obj.equals(btn_Huy)) {
            dispose();
        }
    }

    private void checkDupCanBo() {
        Object[][] current_Data = DSCanBo_Controller.array2DSCanBo(canBo2DS(canBo_Models));
        Object[][] old_Data = DSCanBo_Controller.array2DSCanBo(DSCanBo_View.getController().load_TatCaCanBo(mask));
        dSCanBo_Models = new ArrayList<>();
        dSCanBoTonTai = new ArrayList<>();
        if (current_Data.length != 0) {
            for (Object[] data_1 : current_Data) {
                for (Object[] old_Data1 : old_Data) {
                    if (data_1[1].equals(old_Data1[1])) {
                        dSCanBoTonTai.add(new DSCanBo_Model(
                                String.valueOf(data_1[1]),
                                String.valueOf(data_1[2]),
                                String.valueOf(data_1[3])
                        ));
                        Arrays.fill(data_1, "");
                    }
                }
            }
            for (Object[] data_1 : current_Data) {
                if (!data_1[1].equals("")) {
                    dSCanBo_Models.add(new DSCanBo_Model(
                            String.valueOf(data_1[1]),
                            String.valueOf(data_1[2]),
                            String.valueOf(data_1[3])
                    ));
                }
            }
        } else {
            Alert.showMessageDialog(VIEW, "Bạn chưa chọn cán bộ nào", "Thông báo");
        }

    }

    private void importCB2Table() {
        DefaultTableModel listModel = new DefaultTableModel();
        listModel.setColumnIdentifiers(columnNamesCB);
        if (!dSCanBoTonTai.isEmpty()) {
            TransitionPane.setAlwaysOnTop(false);
            dSCanBoTonTai.forEach((t) -> {
                Object[] obj = {t.getMaCB(), t.getTen(), t.getMaRFID()};
                listModel.addRow(obj);
            });
            tblTonTai.setModel(listModel);
            lblTitle.setText("Thêm " + canBo_Models.size() + " cán bộ từ danh sách.");
            lblWarning.setText("<html>Có " + listModel.getRowCount() + " cán bộ đã có trong danh sách tham dự hiện tại.<br>"
                    + " Bạn có muốn ghi đè lên dữ liệu cũ?");
            dialog.setTitle("Xác nhận thêm danh sách cán bộ");
            pnl_Duplicate.setVisible(false);
            dialog.show();
        } else {
            int select = Alert.showQuestionDialog(this, "Bạn muốn thêm " + dSCanBo_Models.size() + " cán bộ này?", "Xác nhận thêm");
            if (select == Alert.OK) {
                int countSuccess = 0;
                DSCanBo_View.getController().add_canBoThamGia(dSCanBo_Models, mask);
                updateCBTable();
                if (!DSCanBo_Controller.getAdd_Failed().isEmpty()) {
                    thatBai = "";
                    DSCanBo_Controller.getAdd_Failed().forEach((t) -> {
                        thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                    });
                    Alert.showMessageDialog(VIEW, thatBai);
                    countSuccess += dSCanBo_Models.size() - DSCanBo_Controller.getAdd_Failed().size();
                }
                if (countSuccess > 0) {
                    Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " cán bộ.", "Thông báo");
                }
            }
        }
    }

    private void updateCBTable() {
        dispose();
        dialog.setVisible(false);
        VIEW.getSuKien_Details().getdSThamGia().getdSCanBo_View().getPanelTable().getComboBoxItem().setSelectedIndex(0);
        VIEW.getSuKien_Details().getdSThamGia().getdSCanBo_View().getPanelTable().initFilterAndButtons();
        VIEW.getSuKien_Details().getdSThamGia().getdSCanBo_View().refresh();
        VIEW.getBtn_LuuDS().setEnabled(false);
        VIEW.getBtn_XoaDS().setEnabled(false);
        VIEW.getSuKien_Details().getdSThamGia().showDSCanBo();
    }

    public ArrayList<DSCanBo_Model> canBo2DS(ArrayList<CanBo_Model> canBo_Models) {
        ArrayList<DSCanBo_Model> dsCanBo = new ArrayList<>();
        canBo_Models.forEach((t) -> {
            dsCanBo.add(new DSCanBo_Model(t.getMaCB(), t.getTen(), t.getMaRFID()));
        });
        return dsCanBo;
    }

//    sinh viên
    private void checkDupSinhVien() {
        Object[][] current_Data = DSSinhVien_Controller.array2DSSinhVien(sinhVien2DS(sinhVien_Models));
        Object[][] old_Data = DSSinhVien_Controller.array2DSSinhVien(DSSinhVien_View.getController().load_TatCaSinhVien(mask));
        dSSinhVien_Models = new ArrayList<>();
        dSSinhVienTonTai = new ArrayList<>();
        if (current_Data.length != 0) {
            for (Object[] data_1 : current_Data) {
                for (Object[] old_Data1 : old_Data) {
                    if (data_1[1].equals(old_Data1[1])) {
                        dSSinhVienTonTai.add(new DSSinhVien_Model(
                                String.valueOf(data_1[1]),
                                String.valueOf(data_1[2]),
                                String.valueOf(data_1[3])
                        ));
                        Arrays.fill(data_1, "");
                    }
                }
            }
            for (Object[] data_1 : current_Data) {
                if (!data_1[1].equals("")) {
                    dSSinhVien_Models.add(new DSSinhVien_Model(
                            String.valueOf(data_1[1]),
                            String.valueOf(data_1[2]),
                            String.valueOf(data_1[3])
                    ));
                }
            }
        } else {
            Alert.showMessageDialog(VIEW, "Bạn chưa chọn sinh viên nào", "Thông báo");
        }

    }

    private void importSV2Table() {
        DefaultTableModel listModel = new DefaultTableModel();
        listModel.setColumnIdentifiers(columnNamesSV);
        if (!dSSinhVienTonTai.isEmpty()) {
            TransitionPane.setAlwaysOnTop(false);
            dSSinhVienTonTai.forEach((t) -> {
                Object[] obj = {t.getMaSV(), t.getTen(), t.getMaRFID()};
                listModel.addRow(obj);
            });
            tblTonTai.setModel(listModel);
            lblTitle.setText("Thêm " + sinhVien_Models.size() + " sinh viên từ danh sách.");
            lblWarning.setText("<html> Có " + listModel.getRowCount() + " sinh viên đã có trong danh sách tham dự hiện tại.<br>"
                    + " Bạn có muốn ghi đè lên dữ liệu cũ?");
            dialog.setTitle("Xác nhận thêm danh sách sinh viên");
            pnl_Duplicate.setVisible(false);
            dialog.show();
        } else {
            int select = Alert.showQuestionDialog(this, "Bạn muốn thêm " + dSSinhVien_Models.size() + " sinh viên này?", "Xác nhận thêm");
            if (select == Alert.OK) {
                int countSuccess = 0;
                DSSinhVien_View.getController().add_sinhVienThamGia(dSSinhVien_Models, mask);
                updateDSSVTable();
                if (!DSSinhVien_Controller.getAdd_Failed().isEmpty()) {
                    thatBai = "";
                    DSSinhVien_Controller.getAdd_Failed().forEach((t) -> {
                        thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                    });
                    Alert.showMessageDialog(VIEW, thatBai);
                    countSuccess += dSSinhVien_Models.size() - DSSinhVien_Controller.getAdd_Failed().size();
                }
                if (countSuccess > 0) {
                    Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " sinh viên.", "Thông báo");
                }
            }
        }
    }

    private void updateDSSVTable() {
        dispose();
        dialog.setVisible(false);
        VIEW.getSuKien_Details().getdSThamGia().getdSSinhVien_View().getPanelTable().getComboBoxItem().setSelectedIndex(0);
        VIEW.getSuKien_Details().getdSThamGia().getdSSinhVien_View().getPanelTable().initFilterAndButtons();
        VIEW.getSuKien_Details().getdSThamGia().getdSSinhVien_View().refresh();
        VIEW.getBtn_LuuDS().setEnabled(false);
        VIEW.getBtn_XoaDS().setEnabled(false);
        VIEW.getSuKien_Details().getdSThamGia().showDSSinhVien();
    }

    public ArrayList<DSSinhVien_Model> sinhVien2DS(ArrayList<SinhVien_Model> sinhVien_Models) {
        ArrayList<DSSinhVien_Model> dsSinhVien = new ArrayList<>();
        sinhVien_Models.forEach((t) -> {
            dsSinhVien.add(new DSSinhVien_Model(t.getMaSV(), t.getTen(), t.getMaRFID()));
        });
        return dsSinhVien;
    }
}
