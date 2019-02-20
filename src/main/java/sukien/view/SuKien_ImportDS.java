/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.view;

import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import static administrator.settings.Config.color_Action;
import static administrator.settings.Config.fg_Color1;
import static administrator.settings.Config.fg_Color2;
import app.view.App_View;
import canbo.controller.CanBo_Controller;
import canbo.model.CanBo_Model;
import canbo.view.CanBo_List;
import other.custom.GradientButton;
import other.custom.RoundedBorder;
import dscanbo.controller.DSCanBo_Controller;
import dscanbo.model.DSCanBo_Model;
import dscanbo.view.DSCanBo_View;
import dssinhvien.controller.DSSinhVien_Controller;
import dssinhvien.model.DSSinhVien_Model;
import dssinhvien.view.DSSinhVien_View;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import other.custom.Alert;
import other.custom.TransitionPane;
import other.table.CustomTable;
import resources.Resources;
import static resources.Resources.huy_Icon;
import static resources.Resources.luu_Icon;
import sinhvien.controller.SinhVien_Controller;
import sinhvien.model.SinhVien_Model;
import sinhvien.view.SinhVien_List;

/**
 *
 * @author chuna
 */
public class SuKien_ImportDS extends javax.swing.JFrame implements ActionListener {

    private CustomTable tb_DS;
    private final String[] colNameCanBo = {"Tất cả", "Mã cán bộ", "Họ tên", "Mã thẻ"};
    private final String[] colNameCanBo2 = {"Tất cả", "Mã cán bộ", "Họ tên", "Email", "Bộ môn", "Khoa"};
    private final String[] colNameCanBo3 = {"Mã cán bộ", "Họ tên"};
    private final String[] colNameSinhVien = {"Tất cả", "Mã sinh viên", "Họ tên", "Mã thẻ"};
    private final String[] colNameSinhVien2 = {"Tất cả", "Mã sinh viên", "Họ tên", "Email", "Lớp", "Ngành", "Khoa", "Niên khóa"};
    private final String[] colNameSinhVien3 = {"Mã sinh viên", "Họ tên"};

    private Object[][] data;
    private static SuKien_View VIEW = SuKien_View.getView();
    private static SuKien_ImportDS importDS;
    private int choose;
    final static int CANBO = 1;
    final static int SINHVIEN = 2;
    private String mask;
    private static SuKien_ChonKieuNhap chonKieuNhap;

    private JDialog dialog;
    private ArrayList<CanBo_Model> canBo_NoInfo = new ArrayList<>();
    private ArrayList<SinhVien_Model> sinhVien_NoInfo = new ArrayList<>();
    private ArrayList<DSCanBo_Model> dSCanBo_Moi = new ArrayList<>();
    private ArrayList<DSCanBo_Model> dSCBTonTais = new ArrayList<>();
    private ArrayList<DSSinhVien_Model> dSSinhVien_Moi = new ArrayList<>();
    private ArrayList<DSSinhVien_Model> dSSVTonTais = new ArrayList<>();
    private String thatBai;

    public SuKien_ImportDS(String mask) {
        initComponents();
        importDS = this;
        this.mask = mask;
        createUI();
        loadData();
    }

    public SuKien_ImportDS(Object[][] ob) {
        initComponents();
        this.data = ob;
        importDS = this;
        createUI();
        loadData();
    }

    public JPanel getPnl_Path() {
        return pnl_Path;
    }

    public void setData(Object[][] data) {
        this.data = data;
        loadData();
    }

    private void createUI() {
        chonKieuNhap = new SuKien_ChonKieuNhap(SuKien_ChonKieuNhap.EXCEL);
        chonKieuNhap.setLocationRelativeTo(VIEW);
        chonKieuNhap.setVisible(true);

        btn_Huy = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Huỷ bỏ", huy_Icon);
        btn_Huy.addActionListener(this);

        btn_Luu = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Lưu lại", luu_Icon);
        btn_Luu.addActionListener(this);

        btn_ReImport = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Chọn tập tin khác", Resources.import_Icon);
        btn_ReImport.addActionListener(this);

        pnl_Action.add(btn_ReImport);
        pnl_Action.add(btn_Luu);
        pnl_Action.add(btn_Huy);

        pnl_Action.setBackground(color_Action);
        dialog = new JDialog();
        dialog.setSize(new Dimension(507, 175));
        pnl_Duplicate.setVisible(false);
        dialog.getContentPane().add(pnl_Confirm);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(this);
        setResizable(false);

    }

    public void setLabel(JLabel c) {
        c.setFont(new Font("Arial", 0, 14));
        c.setSize(new Dimension(c.getWidth(), 40));
        CompoundBorder border = new CompoundBorder(new RoundedBorder(), new EmptyBorder(5, 2, 5, 2));
        c.setBorder(border);
    }

    private void loadData() {
        boolean canBo = choose == CANBO;
        boolean kieuNhap = chonKieuNhap.getKieuNhap() == SuKien_ChonKieuNhap.HETHONG;
        if (canBo) {
            tb_DS = new CustomTable(data, kieuNhap ? colNameCanBo : colNameCanBo2);
            tb_DS.setModel(new DefaultTableModel(data, kieuNhap ? colNameCanBo : colNameCanBo2) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        } else {
            tb_DS = new CustomTable(data, kieuNhap ? colNameSinhVien : colNameSinhVien2);
            tb_DS.setModel(new DefaultTableModel(data, kieuNhap ? colNameSinhVien : colNameSinhVien2) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        }

        pnl_Table.removeAll();
        pnl_Table.add(tb_DS.getScrollPane());
        pnl_Table.validate();
        pnl_Table.repaint();
        tb_DS.hideColumn(0);
        pack();
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    public static SuKien_View getVIEW() {
        return VIEW;
    }

    public static SuKien_ImportDS getImportDS() {
        return importDS;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        lbl_Title = new javax.swing.JLabel();
        pnl_Table = new javax.swing.JPanel();
        pnl_Path = new javax.swing.JPanel();
        pnl_Action = new javax.swing.JPanel();

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
                .addComponent(lblWarning, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_Duplicate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(pnl_ConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDetails)
                    .addComponent(btnReplace)
                    .addComponent(btnSkip)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        lbl_Title.setBackground(new java.awt.Color(255, 255, 255));
        lbl_Title.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(102, 102, 102));
        lbl_Title.setText("Nhập danh sách tham gia sự kiện");
        lbl_Title.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 15, 10));
        lbl_Title.setOpaque(true);

        pnl_Table.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        pnl_Table.setOpaque(false);
        pnl_Table.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Path.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 15, 15));
        pnl_Path.setOpaque(false);
        pnl_Path.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 2));

        pnl_Action.setBackground(new java.awt.Color(204, 204, 255));
        pnl_Action.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_Action.setPreferredSize(new java.awt.Dimension(0, 45));
        pnl_Action.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Title, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
            .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Action, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Path, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbl_Title)
                .addGap(0, 0, 0)
                .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Path, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        TransitionPane.closeDialogBox();
        dialog.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSkipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkipActionPerformed
        if (choose == CANBO) {
            int countSuccess = 0;
            if (!canBo_NoInfo.isEmpty()) {
                int select = Alert.showQuestionDialog(
                        VIEW, "Có " + canBo_NoInfo.size() + " cán bộ chưa có đăng ký thông tin.\n"
                        + "Bạn có muốn thêm vào hệ thống", "Thông báo");
                if (select == Alert.OK) {
                    CanBo_List.getController().add_CanBo(canBo_NoInfo);
                    if (!CanBo_Controller.getAdd_Failed().isEmpty()) {
                        thatBai = "";
                        CanBo_Controller.getAdd_Failed().forEach((t) -> {
                            thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                        });
                        Alert.showMessageDialog(VIEW, thatBai);
                    }
                    DSCanBo_View.getController().add_canBoThamGia(canBo2DS(canBo_NoInfo), mask);
                    countSuccess += canBo_NoInfo.size() - CanBo_Controller.getAdd_Failed().size();
                }
            } else if (!dSCanBo_Moi.isEmpty()) {
                DSCanBo_View.getController().add_canBoThamGia(dSCanBo_Moi, mask);
                if (!DSCanBo_Controller.getAdd_Failed().isEmpty()) {
                    thatBai = "";
                    DSCanBo_Controller.getAdd_Failed().forEach((t) -> {
                        thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " vào danh sách tham gia thất bại.\n";
                    });
                    Alert.showMessageDialog(VIEW, thatBai);
                }
                countSuccess += dSCanBo_Moi.size() - DSCanBo_Controller.getAdd_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " cán bộ.", "Thông báo");
                updateDSCBTable();

            } else {
                dispose();
            }
        } else if (choose == SINHVIEN) {
            int countSuccess = 0;
            if (!sinhVien_NoInfo.isEmpty()) {
                int select = Alert.showQuestionDialog(
                        VIEW, "Có " + sinhVien_NoInfo.size() + " sinh viên chưa có đăng ký thông tin.\n"
                        + "Bạn có muốn thêm vào hệ thống", "Thông báo");
                if (select == Alert.OK) {
                    SinhVien_List.getController().add_SinhVien(sinhVien_NoInfo);
                    if (!SinhVien_Controller.getAdd_Failed().isEmpty()) {
                        thatBai = "";
                        SinhVien_Controller.getAdd_Failed().forEach((t) -> {
                            thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                        });
                        Alert.showMessageDialog(VIEW, thatBai);
                    }
                    DSSinhVien_View.getController().add_sinhVienThamGia(sinhVien2DS(sinhVien_NoInfo), mask);
                    countSuccess += sinhVien_NoInfo.size() - SinhVien_Controller.getAdd_Failed().size();
                }
            } else if (!dSSinhVien_Moi.isEmpty()) {
                DSSinhVien_View.getController().add_sinhVienThamGia(dSSinhVien_Moi, mask);
                if (!DSSinhVien_Controller.getAdd_Failed().isEmpty()) {
                    thatBai = "";
                    DSSinhVien_Controller.getAdd_Failed().forEach((t) -> {
                        thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " vào danh sách tham gia thất bại.\n";
                    });
                    Alert.showMessageDialog(VIEW, thatBai);
                }
                countSuccess += dSSinhVien_Moi.size() - DSSinhVien_Controller.getAdd_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " sinh viên.", "Thông báo");
                updateDSSVTable();
            } else {
                dispose();
            }
        }
    }//GEN-LAST:event_btnSkipActionPerformed

    private void btnReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplaceActionPerformed
        if (choose == CANBO) {
            int countSuccess = 0;
            DSCanBo_View.getController().update_canBoThamGia(dSCBTonTais, mask);
            DSCanBo_View.getController().add_canBoThamGia(dSCanBo_Moi, mask);
            updateDSCBTable();
            if (!DSCanBo_Controller.getAdd_Failed().isEmpty()) {
                thatBai = "";
                DSCanBo_Controller.getAdd_Failed().forEach((t) -> {
                    thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSCanBo_Moi.size() + DSCanBo_Controller.getAdd_Failed().size();
            }
            if (!DSCanBo_Controller.getUpdate_Failed().isEmpty()) {
                thatBai = "";
                DSCanBo_Controller.getUpdate_Failed().forEach((t) -> {
                    thatBai += "Ghi đè cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSCBTonTais.size() + DSCanBo_Controller.getUpdate_Failed().size();
            }
            if (!canBo_NoInfo.isEmpty()) {
                int select = Alert.showQuestionDialog(
                        VIEW, "Có " + canBo_NoInfo.size() + " cán bộ chưa có đăng ký thông tin.\n"
                        + "Bạn có muốn thêm vào hệ thống", "Thông báo");
                if (select == Alert.OK) {
                    CanBo_List.getController().add_CanBo(canBo_NoInfo);
                    if (!CanBo_Controller.getAdd_Failed().isEmpty()) {
                        thatBai = "";
                        CanBo_Controller.getAdd_Failed().forEach((t) -> {
                            thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                        });
                        Alert.showMessageDialog(VIEW, thatBai);
                    }
                    DSCanBo_View.getController().add_canBoThamGia(canBo2DS(canBo_NoInfo), mask);
                    countSuccess += canBo_NoInfo.size() - CanBo_Controller.getAdd_Failed().size();
                }
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " cán bộ.", "Thông báo");
            }
        } else if (choose == SINHVIEN) {
            int countSuccess = 0;
            DSSinhVien_View.getController().update_sinhVienThamGia(dSSVTonTais, mask);
            DSSinhVien_View.getController().add_sinhVienThamGia(dSSinhVien_Moi, mask);
            updateDSSVTable();
            if (!DSSinhVien_Controller.getAdd_Failed().isEmpty()) {
                thatBai = "";
                DSSinhVien_Controller.getAdd_Failed().forEach((t) -> {
                    thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSSinhVien_Moi.size() - DSSinhVien_Controller.getAdd_Failed().size();
            }
            if (!DSSinhVien_Controller.getUpdate_Failed().isEmpty()) {
                thatBai = "";
                DSSinhVien_Controller.getUpdate_Failed().forEach((t) -> {
                    thatBai += "Ghi đè sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                });
                Alert.showMessageDialog(VIEW, thatBai);
                countSuccess += dSSVTonTais.size() - DSSinhVien_Controller.getUpdate_Failed().size();
            }
            if (!sinhVien_NoInfo.isEmpty()) {
                int select = Alert.showQuestionDialog(
                        VIEW, "Có " + sinhVien_NoInfo.size() + " sinh viên chưa có đăng ký thông tin.\n"
                        + "Bạn có muốn thêm vào hệ thống", "Thông báo");
                if (select == Alert.OK) {
                    SinhVien_List.getController().add_SinhVien(sinhVien_NoInfo);
                    if (!SinhVien_Controller.getAdd_Failed().isEmpty()) {
                        thatBai = "";
                        SinhVien_Controller.getAdd_Failed().forEach((t) -> {
                            thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                        });
                        Alert.showMessageDialog(VIEW, thatBai);
                    }
                    DSSinhVien_View.getController().add_sinhVienThamGia(sinhVien2DS(sinhVien_NoInfo), mask);
                    countSuccess += sinhVien_NoInfo.size() - SinhVien_Controller.getAdd_Failed().size();
                }
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " sinh viên.", "Thông báo");
            }
        }
    }//GEN-LAST:event_btnReplaceActionPerformed

    private void btnDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailsActionPerformed
        if (pnl_Duplicate.isVisible()) {
            pnl_Duplicate.setVisible(false);
            dialog.setSize(new Dimension(507, 200));
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
    private GradientButton btn_ReImport;

    private GradientButton btn_Huy;
    private GradientButton btn_Luu;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDetails;
    private javax.swing.JButton btnReplace;
    private javax.swing.JButton btnSkip;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblWarning;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel pnl_Action;
    private javax.swing.JPanel pnl_Confirm;
    private javax.swing.JPanel pnl_Duplicate;
    private javax.swing.JPanel pnl_Path;
    private javax.swing.JPanel pnl_Table;
    private javax.swing.JTable tblTonTai;
    // End of variables declaration//GEN-END:variables

    public void setBtnLuu(boolean f) {
        btn_Luu.setVisible(f);
    }

    public JPanel getPath() {
        return pnl_Path;
    }

    public void setPnl_Path(JPanel pnl_Path) {
        this.pnl_Path = pnl_Path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob.equals(btn_ReImport)) {
            TransitionPane.setAlwaysOnTop(false);
            VIEW.import_SK();
        }
        if (ob.equals(btn_Luu)) {
            if (choose == CANBO) {
                checkDupCanBo();
                importCB2Table();
            } else if (choose == SINHVIEN) {
                checkDupSinhVien();
                importSV2Table();
            }
        }
        if (ob.equals(btn_Huy)) {
            TransitionPane.closeDialogBox();
        }

    }

    private ArrayList<CanBo_Model> getCanBoFromTable() {
        Object[][] tableData = tb_DS.getTableData();
        ArrayList<CanBo_Model> canBo_Models = new ArrayList<>();
        for (Object[] objects : tableData) {
            canBo_Models.add(new CanBo_Model(
                    objects[1].toString(),
                    objects[2].toString(),
                    objects[3].toString(),
                    objects[4].toString(),
                    objects[5].toString()
            ));
        }
        return canBo_Models;
    }

    private ArrayList<SinhVien_Model> getSinhVienFromTable() {
        Object[][] tableData = tb_DS.getTableData();
        ArrayList<SinhVien_Model> sinhVien_Models = new ArrayList<>();
        for (Object[] objects : tableData) {
            sinhVien_Models.add(new SinhVien_Model(
                    objects[1].toString(),
                    objects[2].toString(),
                    objects[3].toString(),
                    objects[4].toString(),
                    objects[5].toString(),
                    objects[6].toString(),
                    objects[7].toString()
            ));
        }
        return sinhVien_Models;
    }

    private void checkDupCanBo() {
        ArrayList<CanBo_Model> canBo2DS = getCanBoFromTable();
        canBo_NoInfo = new ArrayList<>();
        dSCanBo_Moi = new ArrayList<>();
        dSCBTonTais = new ArrayList<>();
        canBo2DS.forEach((t) -> {
            boolean tonTai = DSCanBo_View.getController().check_CanBoThamGia(t.getMaCB(), mask);
            if (tonTai) {
                dSCBTonTais.add(new DSCanBo_Model(t.getMaCB(), t.getTen()));
            } else {
                boolean check_TonTai = CanBo_List.getController().check_CanBo(t.getMaCB());
                if (!check_TonTai) {
                    t.setMaRFID("Chưa có");
                    canBo_NoInfo.add(t);
                } else {
                    dSCanBo_Moi.add(new DSCanBo_Model(t.getMaCB(), t.getTen()));
                }
            }
        });
    }

    private void importCB2Table() {
        int countSuccess = 0;
        if (dSCanBo_Moi.isEmpty() && dSCBTonTais.isEmpty() && canBo_NoInfo.isEmpty()) {
            Alert.showMessageDialog(App_View.getContainer(), "File excel không có dữ liệu!", "Thông báo");
        } else {
            DefaultTableModel listModel = new DefaultTableModel();
            listModel.setColumnIdentifiers(colNameCanBo3);
            if (!dSCBTonTais.isEmpty()) {
                TransitionPane.setAlwaysOnTop(false);
                dSCBTonTais.forEach((t) -> {
                    Object[] obj = {t.getMaCB(), t.getTen(), t.getMaRFID()};
                    listModel.addRow(obj);
                });
                tblTonTai.setModel(listModel);
                lblTitle.setText("Import " + tb_DS.getRowCount() + " cán bộ từ tập tin.");
                lblWarning.setText("<html>Có " + listModel.getRowCount() + " cán bộ đã có trong danh sách tham gia của sự kiện hiện tại."
                        + "<br> Bạn có muốn ghi đè lên dữ liệu cũ?");
                dialog.setTitle("Xác nhận import danh sách cán bộ tham gia");
                pnl_Duplicate.setVisible(false);
                dialog.setLocationRelativeTo(this);
                dialog.show();
            } else if (!canBo_NoInfo.isEmpty()) {
                int select = Alert.showQuestionDialog(
                        VIEW, "Có " + canBo_NoInfo.size() + " cán bộ chưa có đăng ký thông tin.\n"
                        + "Bạn có muốn thêm vào hệ thống", "Thông báo");
                if (select == Alert.OK) {
                    CanBo_List.getController().add_CanBo(canBo_NoInfo);
                    if (!CanBo_Controller.getAdd_Failed().isEmpty()) {
                        thatBai = "";
                        CanBo_Controller.getAdd_Failed().forEach((t) -> {
                            thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " thất bại.\n";
                        });
                        Alert.showMessageDialog(VIEW, thatBai);
                    }
                    DSCanBo_View.getController().add_canBoThamGia(canBo2DS(canBo_NoInfo), mask);
                    countSuccess += canBo_NoInfo.size() - CanBo_Controller.getAdd_Failed().size();
                }
            } else if (!dSCanBo_Moi.isEmpty()) {
                DSCanBo_View.getController().add_canBoThamGia(dSCanBo_Moi, mask);
                if (!DSCanBo_Controller.getAdd_Failed().isEmpty()) {
                    thatBai = "";
                    DSCanBo_Controller.getAdd_Failed().forEach((t) -> {
                        thatBai += "Thêm cán bộ có mã " + t.getMaCB() + " vào danh sách tham gia thất bại.\n";
                    });
                    Alert.showMessageDialog(VIEW, thatBai);
                }
                countSuccess += dSCanBo_Moi.size() - DSCanBo_Controller.getAdd_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " cán bộ.", "Thông báo");
                updateDSCBTable();

            } else {
                dispose();
            }
        }

    }

    private void updateDSCBTable() {
        dispose();
        dialog.setVisible(false);
        VIEW.getSuKien_Details().getdSThamGia().getdSCanBo_View().getPanelTable().getComboBoxItem().setSelectedIndex(0);
        VIEW.getSuKien_Details().getdSThamGia().getdSCanBo_View().getPanelTable().initFilterAndButtons();
        VIEW.getSuKien_Details().getdSThamGia().getdSCanBo_View().execute();
        VIEW.getSuKien_Details().getdSThamGia().getdSCanBo_View().refresh();
        VIEW.getSuKien_Details().getdSThamGia().showDSCanBo();
    }

    public ArrayList<DSCanBo_Model> canBo2DS(ArrayList<CanBo_Model> canBo_Models) {
        ArrayList<DSCanBo_Model> dsCanBo = new ArrayList<>();
        canBo_Models.forEach((t) -> {
            dsCanBo.add(new DSCanBo_Model(t.getMaCB(), t.getTen()));
        });
        return dsCanBo;
    }

    private void checkDupSinhVien() {
        ArrayList<SinhVien_Model> sinhVien2DS = getSinhVienFromTable();
        sinhVien_NoInfo = new ArrayList<>();
        dSSinhVien_Moi = new ArrayList<>();
        dSSVTonTais = new ArrayList<>();
        sinhVien2DS.forEach((t) -> {
            boolean tonTai = DSSinhVien_View.getController().check_SinhVienThamGia(t.getMaSV(), mask);
            System.out.println(tonTai);
            if (tonTai) {
                dSSVTonTais.add(new DSSinhVien_Model(t.getMaSV(), t.getTen()));
            } else {
                boolean check_TonTai = SinhVien_List.getController().check_SinhVien(t.getMaSV());
                if (!check_TonTai) {
                    t.setMaRFID("Chưa có");
                    sinhVien_NoInfo.add(t);
                } else {
                    dSSinhVien_Moi.add(new DSSinhVien_Model(t.getMaSV(), t.getTen()));
                }
            }
        });
    }

    private void importSV2Table() {
        int countSuccess = 0;
        if (dSSinhVien_Moi.isEmpty() && dSSVTonTais.isEmpty() && sinhVien_NoInfo.isEmpty()) {
            Alert.showMessageDialog(App_View.getContainer(), "File excel không có dữ liệu!", "Thông báo");
        } else {
            DefaultTableModel listModel = new DefaultTableModel();
            listModel.setColumnIdentifiers(colNameSinhVien3);
            if (!dSSVTonTais.isEmpty()) {
                TransitionPane.setAlwaysOnTop(false);
                dSSVTonTais.forEach((t) -> {
                    Object[] obj = {t.getMaSV(), t.getTen(), t.getMaRFID()};
                    listModel.addRow(obj);
                });
                tblTonTai.setModel(listModel);
                lblTitle.setText("Import " + tb_DS.getRowCount() + " sinh viên từ tập tin.");
                lblWarning.setText("Có " + listModel.getRowCount() + " sinh viên đã có trong hệ thống."
                        + " Bạn có muốn ghi đè lên dữ liệu cũ?");
                dialog.setTitle("Xác nhận import danh sách sinh viên");
                pnl_Duplicate.setVisible(false);
                dialog.setLocationRelativeTo(this);
                dialog.show();
            } else if (!sinhVien_NoInfo.isEmpty()) {
                int select = Alert.showQuestionDialog(
                        VIEW, "Có " + sinhVien_NoInfo.size() + " sinh viên chưa có đăng ký thông tin.\n"
                        + "Bạn có muốn thêm vào hệ thống", "Thông báo");
                if (select == Alert.OK) {
                    SinhVien_List.getController().add_SinhVien(sinhVien_NoInfo);
                    if (!SinhVien_Controller.getAdd_Failed().isEmpty()) {
                        thatBai = "";
                        SinhVien_Controller.getAdd_Failed().forEach((t) -> {
                            thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " thất bại.\n";
                        });
                        Alert.showMessageDialog(VIEW, thatBai);
                    }
                    DSSinhVien_View.getController().add_sinhVienThamGia(sinhVien2DS(sinhVien_NoInfo), mask);
                    countSuccess += sinhVien_NoInfo.size() - SinhVien_Controller.getAdd_Failed().size();
                }
            } else if (!dSSinhVien_Moi.isEmpty()) {
                DSSinhVien_View.getController().add_sinhVienThamGia(dSSinhVien_Moi, mask);
                if (!DSSinhVien_Controller.getAdd_Failed().isEmpty()) {
                    thatBai = "";
                    DSSinhVien_Controller.getAdd_Failed().forEach((t) -> {
                        thatBai += "Thêm sinh viên có mã " + t.getMaSV() + " vào danh sách tham gia thất bại.\n";
                    });
                    Alert.showMessageDialog(VIEW, thatBai);
                }
                countSuccess += dSSinhVien_Moi.size() - DSSinhVien_Controller.getAdd_Failed().size();
            }
            if (countSuccess > 0) {
                Alert.showMessageDialog(VIEW, "Đã thêm thành công " + countSuccess + " sinh viên.", "Thông báo");
                updateDSSVTable();
            } else {
                dispose();
            }
        }

    }

    private void updateDSSVTable() {
        dispose();
        dialog.setVisible(false);
        VIEW.getSuKien_Details().getdSThamGia().getdSSinhVien_View().getPanelTable().getComboBoxItem().setSelectedIndex(0);
        VIEW.getSuKien_Details().getdSThamGia().getdSSinhVien_View().getPanelTable().initFilterAndButtons();
        VIEW.getSuKien_Details().getdSThamGia().getdSSinhVien_View().execute();
        VIEW.getSuKien_Details().getdSThamGia().getdSSinhVien_View().refresh();
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
