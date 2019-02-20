/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canbo.view;

import canbo.model.CanBo_Model;
import static administrator.settings.Config.BG_BUTTON_HOVER;
import static administrator.settings.Config.BG_NAV;
import static administrator.settings.Config.FG_BUTTON;
import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import static administrator.settings.Config.color_Action;
import static administrator.settings.Config.fg_Color1;
import static administrator.settings.Config.fg_Color2;
import canbo.controller.CanBo_Controller;
import other.custom.DataString;
import other.custom.GradientButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import login.Login_View;
import org.jdesktop.swingx.JXCollapsiblePane;
import other.custom.Alert;
import other.custom.CustomBalloonTip;
import other.custom.TransitionPane;
import resources.Resources;
import static resources.Resources.dd_Large_Icon;
import static resources.Resources.import_Icon;
import static resources.Resources.import_Large_Icon;
import static resources.Resources.luu_Icon;
import static resources.Resources.refresh_Icon;
import static resources.Resources.sua_Icon;
import static resources.Resources.themCB_Large_Icon;
import static resources.Resources.themSV_Large_Icon;
import static resources.Resources.them_Icon;
import static resources.Resources.xem_Icon;
import static resources.Resources.xoa_Icon;

/**
 *
 * @author chuna
 */
public class CanBo_View extends javax.swing.JPanel implements ActionListener {

    private GradientButton btn_XemDS;
    private GradientButton btn_DangKyThe;
    private GradientButton btn_ThemCB;
    private GradientButton btn_NhapDS;
    private static CanBo_View view;

    private boolean nameButton;
    private CanBo_Detail canBoDetails;
    private CanBo_List canBoList;
    private CanBo_Import pnl_Import;
    private CanBo_Registor canBo_Registor;
    private String thatBai = "";
    private ArrayList<CanBo_Model> listDel_CanBo = new ArrayList<>();

    private CustomBalloonTip balloonTip;

    public CanBo_View() {
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
            java.util.logging.Logger.getLogger(Login_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        view = this;
        canBoDetails = new CanBo_Detail();
        canBoList = new CanBo_List();
        canBo_Registor = new CanBo_Registor();
        initComponents();
        createUI();
    }

    private void createUI() {
        btn_XemDS = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "Xem danh sách cán bộ", dd_Large_Icon);
        btn_DangKyThe = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "ĐĂNG KÝ THẺ CÁN BỘ", themCB_Large_Icon);
        btn_ThemCB = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "THÊM CÁN BỘ MỚI", themSV_Large_Icon);
        btn_NhapDS = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "NHẬP DANH SÁCH CÁN BỘ", import_Large_Icon);
        btn_XemDS.addActionListener(this);
        btn_DangKyThe.addActionListener(this);
        btn_ThemCB.addActionListener(this);
        btn_NhapDS.addActionListener(this);
        Arrays.asList(btn_XemDS, btn_DangKyThe, btn_ThemCB, btn_NhapDS).forEach((btn) -> {
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setFont(new Font("Arial", 0, 18));
            btn.setPreferredSize(new Dimension(300, 200));
            jPanel1.add(btn);
            btn.setText(btn.getText().toUpperCase());
        });

        btn_Xem = new GradientButton(bg_Color1, bg_Color2, xem_Icon);
        btn_Xem.setToolTipText("Xem chi tiết");
        btn_Xem.addActionListener(this);

        btn_Them = new GradientButton(bg_Color1, bg_Color2, them_Icon);
        btn_Them.setToolTipText("Thêm cán bộ mới");

        btn_Them.addActionListener(this);

        btn_Sua = new GradientButton(bg_Color1, bg_Color2, sua_Icon);
        btn_Sua.setToolTipText("Chỉnh sửa thông tin cán bộ");

        btn_Sua.addActionListener(this);

        btn_Xoa = new GradientButton(bg_Color1, bg_Color2, xoa_Icon);
        btn_Xoa.setToolTipText("Xoá cán bộ");

        btn_Xoa.addActionListener(this);

        btn_TaiLai = new GradientButton(bg_Color1, bg_Color2, refresh_Icon);
        btn_TaiLai.setToolTipText("Huỷ");

        btn_TaiLai.addActionListener(this);

        btn_Luu = new GradientButton(bg_Color1, bg_Color2, luu_Icon);
        btn_Luu.setToolTipText("Lưu lại");

        btn_Luu.addActionListener(this);

        btn_Import = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Lấy dữ liệu từ file excel", import_Icon);
        btn_Import.addActionListener(this);

        pnl_Action.add(btn_Them);
        pnl_Action.add(btn_Import);
        pnl_Action.add(btn_Xem);
        pnl_Action.add(btn_Sua);
        pnl_Action.add(btn_Xoa);
        pnl_Action.add(btn_TaiLai);
        pnl_Action.add(btn_Luu);
        pnl_Action.setBackground(color_Action);
        pnl_Content.add(canBoList);
        btn_Sua.setEnabled(canBoList.getTable().isSelected());
        btn_Xem.setEnabled(canBoList.getTable().isSelected());
        btn_Xoa.setEnabled(canBoList.getTable().isSelected());
        btn_Luu.setEnabled(false);
        setTextButton();
        collapse.setLayout(new BorderLayout());
        collapse.add(panel2, BorderLayout.CENTER);
        btn_XemDS.addActionListener(collapse.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
        btn_DangKyThe.addActionListener(collapse.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
        toggle.addActionListener(collapse.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
        toggle.setIcon(Resources.back_Icon);

    }

    public void showPanel() {
        canBoList.execute();
        loadData();
    }

    private void loadData() {
        canBoList.getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!canBoList.getTable().getHeader().isClicked()) {
                    canBoList.getTable().getHeader().setMousePressed(true);
                    canBoList.getTable().getHeader().headerClick();
                    canBoList.getTable().getHeader().repaintCheckBox(canBoList.getTable());
                    btn_Xoa.setEnabled(true);
                    btn_Xem.setEnabled(false);
                    btn_Sua.setEnabled(false);
                } else {
                    btn_Xoa.setEnabled(false);
                }
            }

        });
        canBoList.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xem_CanBo();
                }
                if (canBoList.getTable().getCheckedRow(0) > 1) {
                    btn_Xoa.setEnabled(true);
                    btn_Xem.setEnabled(false);
                    btn_Sua.setEnabled(false);
                } else {
                    btn_Xoa.setEnabled(canBoList.getTable().isSelected());
                    btn_Xem.setEnabled(canBoList.getTable().isSelected());
                    btn_Sua.setEnabled(canBoList.getTable().isSelected());
                }

            }
        });

    }

    public static CanBo_View getView() {
        return view;
    }

    public CanBo_List getCanBoList() {
        return canBoList;
    }

    public void setCanBoList(CanBo_List canBoList) {
        this.canBoList = canBoList;
    }

    private void setTextButton() {
        nameButton = getWidth() > 1000;
        btn_Xem.setText(nameButton ? "Xem chi tiết" : "");
        btn_Them.setText(nameButton ? "Thêm cán bộ" : "");
        btn_Sua.setText(nameButton ? "Cập nhật cán bộ" : "");
        btn_Xoa.setText(nameButton ? "Xoá" : "");
        btn_TaiLai.setText(nameButton ? "Làm mới" : "");
        btn_Luu.setText(nameButton ? "Lưu" : "");
        btn_Import.setText(nameButton ? "Nhập danh sách" : "");
    }

    public void setSave(boolean s) {
        btn_Luu.setEnabled(s);
    }

    public void execute() {
        canBoList.getPaneTable().executeTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel2 = new javax.swing.JPanel();
        panel3 = new javax.swing.JPanel();
        toggle = new javax.swing.JButton();
        lbl_Title1 = new javax.swing.JLabel();
        pnl_Pane = new javax.swing.JPanel();
        panel4 = new javax.swing.JPanel();
        pnl_Content = new javax.swing.JPanel();
        pnl_Action = new javax.swing.JPanel();
        collapse = new org.jdesktop.swingx.JXCollapsiblePane();
        jPanel2 = new javax.swing.JPanel();
        lbl_Title = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        toggle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        toggle.setBorder(null);
        toggle.setContentAreaFilled(false);

        lbl_Title1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_Title1.setText("Danh sách cán bộ");
        lbl_Title1.setPreferredSize(new java.awt.Dimension(170, 50));

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addComponent(toggle, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lbl_Title1, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Title1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toggle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnl_Pane.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Content.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Content.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Action.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_Action.setPreferredSize(new java.awt.Dimension(0, 45));
        pnl_Action.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 2));

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnl_Action, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE))
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addGap(45, 45, 45))
            .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                    .addGap(0, 418, Short.MAX_VALUE)
                    .addComponent(pnl_Action, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnl_Pane.add(panel4);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        collapse.setCollapsed(true);
        add(collapse, java.awt.BorderLayout.NORTH);

        lbl_Title.setBackground(new java.awt.Color(249, 249, 249));
        lbl_Title.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(102, 102, 102));
        lbl_Title.setText("Quản Lý Cán Bộ");
        lbl_Title.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 15, 20));
        lbl_Title.setOpaque(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(75, 75, 75, 75));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 500));
        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 50, 50));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Title, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbl_Title)
                .addContainerGap(630, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    }//GEN-LAST:event_formComponentShown

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        setTextButton();

        if (collapse.isCollapsed()) {
            panel2.setPreferredSize(jPanel2.getSize());
        } else {
            int width = getWidth();
            int height = getHeight();
            panel2.setPreferredSize(new Dimension(width, height));
        }
    }//GEN-LAST:event_formComponentResized
    //<editor-fold defaultstate="collapsed" desc="GET AND SET">

    public JPanel getPnl_Bottom() {
        return pnl_Content;
    }

    public void setPnl_Bottom(JPanel pnl_Bottom) {
        this.pnl_Content = pnl_Bottom;
    }

    public CanBo_Detail getCanBoDetails() {
        return canBoDetails;
    }

    public void setCanBoDetail(CanBo_Detail canBoDetails) {
        this.canBoDetails = canBoDetails;
    }

    public JPanel getPnl_Pane() {
        return pnl_Pane;
    }

    public void setPnl_Pane(JPanel pnl_Pane) {
        this.pnl_Pane = pnl_Pane;
    }

    public JPanel getPanel4() {
        return panel4;
    }

    public void setjPanel4(JPanel jPanel4) {
        this.panel4 = jPanel4;
    }

    public JButton getToggle() {
        return toggle;
    }

    public void setToggle(JButton toggle) {
        this.toggle = toggle;
    }

    public JXCollapsiblePane getCollapse() {
        return collapse;
    }

    public void setCollapse(JXCollapsiblePane collapse) {
        this.collapse = collapse;
    }

    public JLabel getLbl_Title() {
        return lbl_Title;
    }

    public void setTitle(String s, boolean show) {
        this.lbl_Title1.setText(s);
        this.lbl_Title1.setVisible(show);
    }
//</editor-fold>
    private GradientButton btn_Them;
    private GradientButton btn_Xem;
    private GradientButton btn_Sua;
    private GradientButton btn_Xoa;
    private GradientButton btn_TaiLai;
    private GradientButton btn_Luu;
    private GradientButton btn_Import;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXCollapsiblePane collapse;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JLabel lbl_Title1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel pnl_Action;
    private javax.swing.JPanel pnl_Content;
    private javax.swing.JPanel pnl_Pane;
    private javax.swing.JButton toggle;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob.equals(btn_XemDS)) {
            TransitionPane.replacePane(pnl_Pane, panel4);
            showPanel();
            lbl_Title1.setText("Danh sách cán bộ");
        }
        if (ob.equals(btn_ThemCB)) {
            them_CanBo();
            showPanel();
            lbl_Title1.setText("Danh sách cán bộ");
        }
        if (ob.equals(btn_DangKyThe)) {
            TransitionPane.replacePane(pnl_Pane, canBo_Registor);
            lbl_Title1.setText("Đăng ký thẻ cán bộ");
        }
        if (ob.equals(btn_NhapDS)) {
            showPanel();
            import_CanBo();
        }
        if (ob.equals(btn_Xem)) {
            xem_CanBo();
        }
        if (ob.equals(btn_Them)) {
            them_CanBo();
        }
        if (ob.equals(btn_Sua)) {
            sua_CanBo();
        }
        if (ob.equals(btn_Xoa)) {
            xoa_CanBo();
        }
        if (ob.equals(btn_TaiLai)) {
            refresh();
        }
        if (ob.equals(btn_Luu)) {
            luu_CanBo();
        }
        if (ob.equals(btn_Import)) {
            import_CanBo();
        }
    }

    public void load_CanBo() {
        try {
            canBoDetails.setCanBo_Model(canBoList.getValue());
            canBoDetails.setText();
            canBoDetails.setAccess(false, DataString.NO_CHANGED);
        } catch (NullPointerException e) {
        }
    }

    public void xem_CanBo() {
        load_CanBo();
        if (canBoList.getValue().getMaRFID().isEmpty()) {
            TransitionPane.showDialogBox(this, canBoDetails, "Thông tin chi tiết", Resources.chuaDK_Icon);
        } else {
            TransitionPane.showDialogBox(this, canBoDetails, "Thông tin chi tiết", Resources.daDK_Icon);
        }
    }

    public void them_CanBo() {
        canBoDetails = new CanBo_Detail();
        canBoDetails.setAccess(true, DataString.INSERTED);
        TransitionPane.showDialogBox(this, canBoDetails, "Thêm cán bộ mới", them_Icon);
    }

    private void sua_CanBo() {
        load_CanBo();
        canBoDetails.setAccess(true, DataString.UPDATED);
        TransitionPane.showDialogBox(this, canBoDetails, "Sửa thông tin cán bộ", sua_Icon);
    }

    private void xoa_CanBo() {
        ArrayList<CanBo_Model> listDel_temp = CanBo_List.getListDel_temp();
        ArrayList<Integer> row_Del = CanBo_List.getRow_Del();
        if (listDel_temp.size() > 0) {
            Collections.reverse(row_Del);
            if (Alert.showQuestionDialog(this, "Bạn muốn xoá các lựa chọn?", "Xác nhận xoá") == Alert.OK) {
                DefaultTableModel dtm = (DefaultTableModel) canBoList.getTable().getModel();
                row_Del.forEach((row) -> {
                    dtm.removeRow(row);
                });
                listDel_CanBo.addAll(listDel_temp);
                canBoList.getTable().setModel(dtm);
                canBoList.getTable().clearSelection();
                canBoList.getPaneTable().initFilterAndButtons();
                btn_Luu.setEnabled(true);
                CanBo_List.clearListRowDel();
                CanBo_List.clearListDel();
            }
        } else {
            CanBo_Model canBo_Model = canBoList.getValue();
            if (Alert.showQuestionDialog(this, "Bạn muốn xoá?\n\n" + canBo_Model.getString(), "Xác nhận xoá") == Alert.OK) {
                DefaultTableModel dtm = (DefaultTableModel) canBoList.getTable().getModel();
                int row = canBoList.getTable().getSelectedRow();
                listDel_CanBo.add(new CanBo_Model(
                        String.valueOf(canBoList.getTable().getValueAt(row, 1)),
                        String.valueOf(canBoList.getTable().getValueAt(row, 2)),
                        String.valueOf(canBoList.getTable().getValueAt(row, 3)),
                        String.valueOf(canBoList.getTable().getValueAt(row, 4)),
                        String.valueOf(canBoList.getTable().getValueAt(row, 5)),
                        String.valueOf(canBoList.getTable().getValueAt(row, 6))
                ));
                listDel_CanBo.addAll(listDel_temp);
                dtm.removeRow(row);
                canBoList.getTable().setModel(dtm);
                canBoList.getPaneTable().initFilterAndButtons();
                btn_Luu.setEnabled(true);
                CanBo_List.clearListRowDel();
                CanBo_List.clearListDel();
                canBoList.getTable().clearSelection();
            }
        }
    }

    public void import_CanBo() {
        Object[][] ob = CanBo_List.getController().import_CanBo();
        if (ob != null && ob.length > 0) {
            pnl_Import = new CanBo_Import(ob);
            TransitionPane.showDialogBox(this, pnl_Import, "Import danh sách cán bộ", import_Icon);
            JLabel lbl_Path_ = new JLabel(CanBo_List.getController().getPath());
            pnl_Import.setLabel(lbl_Path_);
            pnl_Import.getPnl_Path().add(lbl_Path_);
            pnl_Import.getPnl_Path().validate();
            pnl_Import.getPnl_Path().repaint();
            setSave(true);
        } else if (ob != null) {
            Alert.showMessageDialog(this, "Tập tin không có dữ liệu.", "Import thất bại");
        } else if (ob == null) {
            Alert.showMessageDialog(this, "Bạn chưa chọn file nào.", "Import thất bại");
        }
    }

    private void luu_CanBo() {
        int select = Alert.showQuestionDialog(this, "Lưu lại thay đổi?", "Xác nhận lưu");
        if (select == Alert.OK) {
            ArrayList<CanBo_Model> listAdd_temp = CanBo_List.getListAdd_temp();
            if (listAdd_temp.size() > 0) {
                CanBo_List.getController().add_CanBo(listAdd_temp);
                if (!CanBo_Controller.getAdd_Failed().isEmpty()) {
                    ArrayList<CanBo_Model> add_Failed = CanBo_Controller.getAdd_Failed();
                    thatBai = "";
                    add_Failed.forEach((t) -> {
                        thatBai += "Thêm cán bộ " + t.getMaCB() + " -- " + t.getTen() + " thất bại!\n";
                    });
                    Alert.showMessageDialog(this, thatBai);
                    CanBo_Controller.clearAdd();
                }
                CanBo_List.clearListAdd();
            }
            ArrayList<CanBo_Model> listEdit_temp = CanBo_List.getListEdit_temp();
            System.out.println("Cập nhật: "+listEdit_temp.size());
            if (listEdit_temp.size() > 0) {
                CanBo_List.getController().update_CanBo(listEdit_temp);
                if (!CanBo_Controller.getUpdate_Failed().isEmpty()) {
                    ArrayList<CanBo_Model> update_Failed = CanBo_Controller.getUpdate_Failed();
                    thatBai = "";
                    update_Failed.forEach((t) -> {
                        thatBai += "Cập nhật cán bộ " + t.getMaCB() + " -- " + t.getTen() + " thất bại!\n";
                    });
                    Alert.showMessageDialog(this, thatBai);
                    CanBo_Controller.clearUpdate();
                }
                CanBo_List.clearListUpdate();
            }
            if (listDel_CanBo.size() > 0) {
                CanBo_List.getController().delete_CanBo(listDel_CanBo);
                if (!CanBo_Controller.getDelete_Failed().isEmpty()) {
                    listDel_CanBo.clear();
                    ArrayList<CanBo_Model> delete_Failed = CanBo_Controller.getDelete_Failed();
                    thatBai = "";
                    delete_Failed.forEach((t) -> {
                        thatBai += "Xoá cán bộ " + t.getMaCB() + " -- " + t.getTen() + " thất bại!\n";
                    });
                    Alert.showMessageDialog(this, thatBai);
                    Alert.showWarning(this, "Cán bộ đang có trong danh sách tham gia của 1 sự kiện! Vui lòng thử lại sau!");
                }
            }
            canBoList.getTable().getTableModel().fireTableDataChanged();
            refresh();
            btn_Luu.setEnabled(false);
        }
    }

    public void refresh() {
        listDel_CanBo.clear();
        btn_Luu.setEnabled(false);
        btn_Xoa.setEnabled(false);
        btn_Sua.setEnabled(false);
        btn_Xem.setEnabled(false);
        canBoList.refresh();
        canBoList.setCheckBox(false);
    }

}
