/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.view;

import sukien.model.SuKien_Model;
import administrator.settings.Config;
import static administrator.settings.Config.BG_BUTTON_HOVER;
import static administrator.settings.Config.BG_NAV;
import static administrator.settings.Config.FG_BUTTON;
import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import app.view.App_View;
import other.custom.DataString;
import other.custom.GradientButton;
import dscanbo.controller.DSCanBo_Controller;
import dscanbo.model.DSCanBo_Model;
import dscanbo.view.DSCanBo_View;
import dssinhvien.controller.DSSinhVien_Controller;
import dssinhvien.model.DSSinhVien_Model;
import dssinhvien.view.DSSinhVien_View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXCollapsiblePane;
import other.custom.Alert;
import other.custom.TransitionPane;
import other.table.CustomTable.MyCustomTableModel;
import resources.Resources;
import static resources.Resources.import_Icon;
import static resources.Resources.import_Large_Icon;
import static resources.Resources.luu_Icon;
import static resources.Resources.refresh_Icon;
import static resources.Resources.suKien_Large_Icon;
import static resources.Resources.sua_Icon;
import static resources.Resources.themSK_Large_Icon;
import static resources.Resources.them_Icon;
import static resources.Resources.xem_Icon;
import static resources.Resources.xoa_Icon;
import sinhvien.view.SinhVien_List;
import sukien.controller.SuKien_Controller;
import static sukien.view.SuKien_ListSK.MANAGEMENT;

/**
 *
 * @author chuna
 */
public class SuKien_View extends javax.swing.JPanel implements ActionListener {

    private static SuKien_View view;
    private static SuKien_DetailSK suKien_Details;
    private static JDialog dialog = new JDialog();
    private static SuKien_ListSK suKien_ListSK;
    private boolean nameButton;
    private GradientButton btn_XemDSSK;
    private GradientButton btn_ImportSK;
    private GradientButton btn_ThemSK_Short;
    private GradientButton btn_NhapDSTG;

    private ArrayList<SuKien_Model> listDel_SK = new ArrayList<>();
    private ArrayList<DSCanBo_Model> listDel_DSCB = new ArrayList<>();
    private ArrayList<DSSinhVien_Model> listDel_DSSV = new ArrayList<>();
    private String thatBai = "";
    private JPanel details;
    private Action ae = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(0, "Danh sách sự kiện");
        }
    };
    private Action toggleAction;
    private SuKien_ThemDS themDS;
    private SuKien_ImportDS importDS;

    public SuKien_View() {
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
        view = this;
        suKien_Details = new SuKien_DetailSK();
        suKien_ListSK = new SuKien_ListSK(SuKien_ListSK.MANAGEMENT);
        initComponents();
        createUI();
    }

    public void showPanel() {
        suKien_ListSK.execute();
        loadData();
        btn_LuuDS.setEnabled(false);
        btn_LuuSK.setEnabled(false);
        btn_XoaDS.setEnabled(false);
        btn_XoaSK.setEnabled(false);
        btn_SuaSK.setEnabled(false);
        lbl_Title1.setText("Danh sách sự kiện");

    }

    public static SuKien_View getView() {
        return view;
    }

    public SuKien_ListSK getSuKienList() {
        return suKien_ListSK;
    }

    public void execute() {
        suKien_ListSK.getPaneTable().executeTable();
    }

    private void createUI() {
        btn_XemSK = new GradientButton(bg_Color1, bg_Color2, xem_Icon);
        btn_XemSK.setToolTipText("Xem chi tiết");
        btn_XemSK.addActionListener(this);

        btn_ThemSK = new GradientButton(bg_Color1, bg_Color2, them_Icon);
        btn_ThemSK.setToolTipText("Thêm sự kiện mới");

        btn_ThemSK.addActionListener(this);

        btn_SuaSK = new GradientButton(bg_Color1, bg_Color2, sua_Icon);
        btn_SuaSK.setToolTipText("Chỉnh sửa thông tin sự kiện");

        btn_SuaSK.addActionListener(this);

        btn_XoaSK = new GradientButton(bg_Color1, bg_Color2, xoa_Icon);
        btn_XoaSK.setToolTipText("Xoá sự kiện");

        btn_XoaSK.addActionListener(this);

        btn_TaiLaiSK = new GradientButton(bg_Color1, bg_Color2, refresh_Icon);
        btn_TaiLaiSK.setToolTipText("Huỷ");

        btn_TaiLaiSK.addActionListener(this);

        btn_LuuSK = new GradientButton(bg_Color1, bg_Color2, luu_Icon);
        btn_LuuSK.setToolTipText("Lưu lại");

        btn_LuuSK.addActionListener(this);

        btn_Import = new GradientButton(bg_Color1, bg_Color2, import_Icon);
        btn_LuuSK.setToolTipText("Nhập danh sách");

        btn_Import.addActionListener(this);
        pnl_Action_1.add(btn_ThemSK);
        pnl_Action_1.add(btn_Import);
        pnl_Action_1.add(btn_XemSK);
        pnl_Action_1.add(btn_SuaSK);
        pnl_Action_1.add(btn_XoaSK);
        pnl_Action_1.add(btn_TaiLaiSK);
        pnl_Action_1.add(btn_LuuSK);
        pnl_Action_1.setBackground(Config.color_Action);
        TransitionPane.replacePane(pnl_Task, pnl_Action_1);

        btn_DoiBang = new GradientButton(bg_Color1, bg_Color2, Resources.change_Icon);
        btn_DoiBang.setToolTipText("Chuyển đổi danh sách");
        btn_DoiBang.addActionListener(this);

        btn_ThemTuDS = new GradientButton(bg_Color1, bg_Color2, them_Icon);
        btn_ThemTuDS.setToolTipText("Thêm thành viên tham dự");
        btn_ThemTuDS.addActionListener(this);

        btn_ImportDS = new GradientButton(bg_Color1, bg_Color2, import_Icon);
        btn_ImportDS.setToolTipText("Import thành viên tham dự");

        btn_ImportDS.addActionListener(this);

        btn_XoaDS = new GradientButton(bg_Color1, bg_Color2, xoa_Icon);
        btn_XoaDS.setToolTipText("Xoá thành viên tham dự");

        btn_XoaDS.addActionListener(this);

        btn_TaiLaiDS = new GradientButton(bg_Color1, bg_Color2, refresh_Icon);
        btn_TaiLaiDS.setToolTipText("Tải lại");

        btn_TaiLaiDS.addActionListener(this);

        btn_LuuDS = new GradientButton(bg_Color1, bg_Color2, luu_Icon);
        btn_LuuDS.setToolTipText("Lưu lại");

        btn_LuuDS.addActionListener(this);

        pnl_Action_2.add(btn_DoiBang);
        pnl_Action_2.add(btn_ThemTuDS);
        pnl_Action_2.add(btn_ImportDS);
        pnl_Action_2.add(btn_XoaDS);
        pnl_Action_2.add(btn_TaiLaiDS);
        pnl_Action_2.add(btn_LuuDS);

        pnl_Content.add(suKien_ListSK);
        btn_SuaSK.setEnabled(suKien_ListSK.getTable().isSelected());
        btn_XemSK.setEnabled(suKien_ListSK.getTable().isSelected());
        btn_XoaSK.setEnabled(suKien_ListSK.getTable().isSelected());
        btn_LuuSK.setEnabled(false);

        btn_XemDSSK = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "Xem danh sách sự kiện", suKien_Large_Icon);
        btn_ThemSK_Short = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "THÊM sự kiện MỚI", themSK_Large_Icon);
        btn_ImportSK = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "Nhập danh sách sự kiện", import_Large_Icon);
        btn_NhapDSTG = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "Nhập DANH SÁCH Tham gia", import_Large_Icon);
        btn_XemDSSK.addActionListener(this);
        btn_ImportSK.addActionListener(this);
        btn_ThemSK_Short.addActionListener(this);
        btn_NhapDSTG.addActionListener(this);
        Arrays.asList(btn_XemDSSK, btn_ThemSK_Short, btn_ImportSK, btn_NhapDSTG).forEach((btn) -> {
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setFont(new Font("Arial", 0, 18));
            btn.setPreferredSize(new Dimension(300, 200));
            jPanel1.add(btn);
            btn.setText(btn.getText().toUpperCase());
        });
        collapse.setLayout(new BorderLayout());
        collapse.add(panel2, BorderLayout.CENTER);
        toggleAction = collapse.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);
        btn_XemDSSK.addActionListener(toggleAction);
        btn_NhapDSTG.addActionListener(toggleAction);
        toggle.addActionListener(toggleAction);
        toggle.setIcon(Resources.back_Icon);
    }

    private void loadData() {
        suKien_ListSK.getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!suKien_ListSK.getTable().getHeader().isClicked()) {
                    suKien_ListSK.getTable().getHeader().setMousePressed(true);
                    suKien_ListSK.getTable().getHeader().headerClick();
                    suKien_ListSK.getTable().getHeader().repaintCheckBox(suKien_ListSK.getTable());
                    btn_XoaSK.setEnabled(true);
                    btn_XemSK.setEnabled(false);
                    btn_SuaSK.setEnabled(false);
                } else {
                    btn_XoaSK.setEnabled(false);
                }
            }
        });
        suKien_ListSK.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xem_SuKien();
                }
                if (suKien_ListSK.getTable().getCheckedRow(0) > 1) {
                    btn_XoaSK.setEnabled(true);
                    btn_XemSK.setEnabled(false);
                    btn_SuaSK.setEnabled(false);
                } else {
                    btn_XoaSK.setEnabled(suKien_ListSK.getTable().isSelected());
                    btn_XemSK.setEnabled(suKien_ListSK.getTable().isSelected());
                    btn_SuaSK.setEnabled(suKien_ListSK.getTable().isSelected());
                }
            }
        });
        btn_XoaSK.setEnabled(false);
        btn_XemSK.setEnabled(false);
        btn_SuaSK.setEnabled(false);
        suKien_ListSK.getTable().setCheckBox(0);
        setTextButton();
    }

    public void setTitle(String s, boolean show) {
        this.lbl_Title1.setText(s);
        this.lbl_Title1.setVisible(show);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Action_2 = new javax.swing.JPanel();
        pnl_Action_1 = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        toggle = new javax.swing.JButton();
        lbl_Title = new javax.swing.JLabel();
        pnl_Pane = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnl_Content = new javax.swing.JPanel();
        pnl_Task = new javax.swing.JPanel();
        collapse = new org.jdesktop.swingx.JXCollapsiblePane();
        jPanel2 = new javax.swing.JPanel();
        lbl_Title1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        pnl_Action_2.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_Action_2.setPreferredSize(new java.awt.Dimension(0, 50));
        pnl_Action_2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 2));

        pnl_Action_1.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_Action_1.setPreferredSize(new java.awt.Dimension(0, 50));
        pnl_Action_1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 2));

        toggle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        toggle.setBorder(null);
        toggle.setContentAreaFilled(false);

        lbl_Title.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lbl_Title.setText("Danh sách sự kiện");
        lbl_Title.setPreferredSize(new java.awt.Dimension(170, 50));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(toggle, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lbl_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toggle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnl_Pane.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Content.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Content.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Task.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_Task.setPreferredSize(new java.awt.Dimension(0, 45));
        pnl_Task.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnl_Task, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addGap(45, 45, 45))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGap(0, 526, Short.MAX_VALUE)
                    .addComponent(pnl_Task, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnl_Pane.add(jPanel4);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        collapse.setCollapsed(true);
        add(collapse, java.awt.BorderLayout.NORTH);

        lbl_Title1.setBackground(new java.awt.Color(249, 249, 249));
        lbl_Title1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lbl_Title1.setForeground(new java.awt.Color(102, 102, 102));
        lbl_Title1.setText("Quản Lý Sự kiện");
        lbl_Title1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 15, 20));
        lbl_Title1.setOpaque(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(75, 75, 75, 75));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 500));
        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 50, 50));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Title1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbl_Title1)
                .addContainerGap(606, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

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

    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    public JPanel get_PanelContent() {
        return pnl_Content;
    }

    public void setPnl_Content(JPanel pnl_Content) {
        this.pnl_Content = pnl_Content;
    }

    public JPanel getPnl_Task() {
        return pnl_Task;
    }

    public void setPnl_Task(JPanel pnl_Task) {
        this.pnl_Task = pnl_Task;
    }

    public SuKien_DetailSK getSuKien_Details() {
        return suKien_Details;
    }

    public void setSuKien_Details(SuKien_DetailSK suKien_Details) {
        this.suKien_Details = suKien_Details;
    }

    public JDialog getDialog() {
        return dialog;
    }

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }

    public GradientButton getBtn_ThemTuDS() {
        return btn_ThemTuDS;
    }

    public void setBtn_ThemTuDS(GradientButton btn_ThemTuDS) {
        this.btn_ThemTuDS = btn_ThemTuDS;
    }

    public GradientButton getBtn_LuuDS() {
        return btn_LuuDS;
    }

    public void setBtn_LuuDS(GradientButton btn_LuuDS) {
        this.btn_LuuDS = btn_LuuDS;
    }

    public JXCollapsiblePane getCollapse() {
        return collapse;
    }

    public void setCollapse(JXCollapsiblePane collapse) {
        this.collapse = collapse;
    }

    //</editor-fold>
    private void setTextButton() {
        nameButton = getWidth() > 1000;
        btn_DoiBang.setText(nameButton ? "Chuyển danh sách" : "");
        btn_XemSK.setText(nameButton ? "Xem chi tiết" : "");
        btn_ThemSK.setText(nameButton ? "Thêm sự kiện" : "");
        btn_ThemTuDS.setText(nameButton ? "Thêm từ hệ thống" : "");
        btn_ImportDS.setText(nameButton ? "Thêm từ tệp excel" : "");
        btn_SuaSK.setText(nameButton ? "Cập nhật sự kiện" : "");
        btn_XoaSK.setText(nameButton ? "Xoá" : "");
        btn_XoaDS.setText(nameButton ? "Xoá" : "");
        btn_TaiLaiSK.setText(nameButton ? "Làm mới" : "");
        btn_TaiLaiDS.setText(nameButton ? "Làm mới" : "");
        btn_LuuSK.setText(nameButton ? "Lưu" : "");
        btn_LuuDS.setText(nameButton ? "Lưu" : "");
        btn_Import.setText(nameButton ? "Nhập danh sách" : "");
    }

    public void setSave(boolean b) {
        btn_LuuSK.setEnabled(b);
    }

    public JPanel getjPanel4() {
        return jPanel4;
    }

    public void setjPanel4(JPanel jPanel4) {
        this.jPanel4 = jPanel4;
    }

    public JPanel getPnl_Pane() {
        return pnl_Pane;
    }

    public void setPnl_Pane(JPanel pnl_Pane) {
        this.pnl_Pane = pnl_Pane;
    }

    public GradientButton getBtn_XoaDS() {
        return btn_XoaDS;
    }

    public void setBtn_XoaDS(GradientButton btn_XoaDS) {
        this.btn_XoaDS = btn_XoaDS;
    }

    public SuKien_ImportDS getImportDS() {
        return importDS;
    }

    private GradientButton btn_ThemSK;
    private GradientButton btn_XemSK;
    private GradientButton btn_SuaSK;
    private GradientButton btn_XoaSK;
    private GradientButton btn_TaiLaiSK;
    private GradientButton btn_LuuSK;

    private GradientButton btn_ThemTuDS;
    private GradientButton btn_ImportDS;
    private GradientButton btn_XoaDS;
    private GradientButton btn_TaiLaiDS;
    private GradientButton btn_LuuDS;
    private GradientButton btn_Import;
    private GradientButton btn_DoiBang;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXCollapsiblePane collapse;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JLabel lbl_Title1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel pnl_Action_1;
    private javax.swing.JPanel pnl_Action_2;
    private javax.swing.JPanel pnl_Content;
    private javax.swing.JPanel pnl_Pane;
    private javax.swing.JPanel pnl_Task;
    private javax.swing.JButton toggle;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob.equals(btn_XemDSSK)) {
            TransitionPane.replacePane(pnl_Pane, jPanel4);
            showPanel();
        }
        if (ob.equals(btn_ThemSK_Short)) {
            them_SuKien();
        }
        if (ob.equals(btn_ImportSK)) {
            showPanel();
            import_SK();
        }
        if (ob.equals(btn_NhapDSTG)) {
            TransitionPane.replacePane(pnl_Pane, jPanel4);
            showPanel();
        }
        if (ob.equals(btn_XemSK)) {
            xem_SuKien();
        }
        if (ob.equals(btn_ThemSK)) {
            them_SuKien();
        }
        if (ob.equals(btn_SuaSK)) {
            sua_SuKien();
        }
        if (ob.equals(btn_XoaSK)) {
            xoa_SuKien();
        }
        if (ob.equals(btn_TaiLaiSK)) {
            refresh();
        }
        if (ob.equals(btn_LuuSK)) {
            int select = Alert.showQuestionDialog(this, "Bạn muốn lưu lại thay đổi", "Xác nhận lưu lại");
            if (select == Alert.OK) {
                luu_SuKien();
                Alert.showSuccess(this, "Đã cập nhật thay đổi!", 2000);
                refresh();
                btn_LuuSK.setEnabled(false);
            }
        }
        if (ob.equals(btn_ThemTuDS)) {
            SuKien_Model value = suKien_ListSK.getValue();
            themDS = new SuKien_ThemDS(value.getMaSK());
        }

        if (ob.equals(btn_XoaDS)) {
            xoa_DanhSach();
        }
        if (ob.equals(btn_TaiLaiDS)) {
            tai_DanhSach();
        }
        if (ob.equals(btn_LuuDS)) {
            luu_DanhSach();
        }
        if (ob.equals(btn_Import)) {
            import_SK();
            showPanel();
        }
        if (ob.equals(btn_DoiBang)) {
            switch (SuKien_DSThamGia.getTypeTable()) {
                case 1:
                    suKien_Details.getdSThamGia().showDSSinhVien();
                    break;
                case 2:
                    suKien_Details.getdSThamGia().showDSCanBo();
                    break;
                case 0:
                    suKien_Details.getdSThamGia().showEmpty();
                    break;
                default:
                    break;
            }
        }
        if (ob.equals(btn_ImportDS)) {
            SuKien_Model value = suKien_ListSK.getValue();
            importDS = new SuKien_ImportDS(value.getMaSK());
        }
    }

    private void changePanel(int pos, String title) {
        boolean screen = pos == 0;
        TransitionPane.replacePane(pnl_Content, screen ? suKien_ListSK = new SuKien_ListSK(MANAGEMENT) : suKien_Details);
        lbl_Title.setText(title);
        btn_DoiBang.setVisible(!screen);
        TransitionPane.replacePane(pnl_Task, screen ? pnl_Action_1 : pnl_Action_2);
        if (screen) {
            showPanel();
            toggle.removeActionListener(ae);
            toggle.addActionListener(toggleAction);
        } else {
            toggle.removeActionListener(toggleAction);
            toggle.addActionListener(ae);
        }
    }

    public boolean load_SuKien() {
        if (suKien_ListSK.getTable().isSelected()) {
            suKien_Details.setSuKien_Model(suKien_ListSK.getValue());
            return true;
        }
        return false;
    }

    public void xem_SuKien() {
        if (load_SuKien()) {
            TransitionPane.replacePane(pnl_Task, pnl_Action_2);
            TransitionPane.replacePane(pnl_Content, suKien_Details);
            suKien_Details.setAccess(false, DataString.NO_CHANGED);
            suKien_Details.getdSThamGia().showTable();
            changePanel(1, "Xem chi tiết sự kiện");
        }
    }

    public void them_SuKien() {
        suKien_Details.setAccess(true, DataString.INSERTED);
        TransitionPane.showDialogBox(this, suKien_Details.getPnl_Display(), "Thêm sự kiện mới", Resources.suKien_Icon);
        TransitionPane.getDialog().setSize(new Dimension(500, 450));
        TransitionPane.getDialog().setLocationRelativeTo(this);
        suKien_Details.setDataDefault();
        lbl_Title1.setText("Danh sách sự kiện");
        btn_LuuSK.setActionCommand("Them");
        showPanel();
        TransitionPane.replacePane(pnl_Pane, jPanel4);
    }

    private void sua_SuKien() {
        load_SuKien();
        suKien_Details.setAccess(true, DataString.UPDATED);
        TransitionPane.showDialogBox(this, suKien_Details.getPnl_Display(), "Cập nhật thông tin sự kiện", Resources.sua_Icon);
        TransitionPane.getDialog().setSize(new Dimension(500, 450));
        TransitionPane.getDialog().setLocationRelativeTo(this);
        btn_LuuSK.setActionCommand("Sua");
    }

    private void xoa_SuKien() {
        ArrayList<SuKien_Model> listDel_temp = SuKien_ListSK.getListDel_temp();
        ArrayList<Integer> row_Del = SuKien_ListSK.getRow_Del();
        if (listDel_temp.size() > 0) {
            Collections.reverse(row_Del);
            if (Alert.showQuestionDialog(this, "Bạn muốn xoá các lựa chọn?", "Xác nhận xoá") == Alert.OK) {
                DefaultTableModel dtm = (DefaultTableModel) suKien_ListSK.getTable().getModel();
                row_Del.forEach((row) -> {
                    dtm.removeRow(row);
                });

                listDel_SK.addAll(listDel_temp);
                suKien_ListSK.getTable().setModel(dtm);
                suKien_ListSK.getPaneTable().initFilterAndButtons();
                suKien_ListSK.getTable().clearSelection();
                SuKien_ListSK.clearListDel();
                SuKien_ListSK.clearListRowDel();
                setSave(true);
            }
        } else {
            SuKien_Model suKien_del = suKien_ListSK.getValue();
            if (Alert.showQuestionDialog(this, "Bạn muốn xoá?\n\n" + suKien_del.getString(), "Xác nhận xoá") == Alert.OK) {
                DefaultTableModel dtm = (DefaultTableModel) suKien_ListSK.getTable().getModel();
                int row = suKien_ListSK.getTable().getSelectedRow();
                listDel_temp.add(new SuKien_Model(
                        String.valueOf(suKien_ListSK.getTable().getValueAt(row, 1)),
                        String.valueOf(suKien_ListSK.getTable().getValueAt(row, 2)),
                        Date.valueOf(String.valueOf(suKien_ListSK.getTable().getValueAt(row, 3))),
                        Time.valueOf(String.valueOf(suKien_ListSK.getTable().getValueAt(row, 4))),
                        Time.valueOf(String.valueOf(suKien_ListSK.getTable().getValueAt(row, 5))),
                        Boolean.valueOf(String.valueOf(suKien_ListSK.getTable().getValueAt(row, 6)))
                ));
                listDel_SK.addAll(listDel_temp);
                suKien_ListSK.getTable().clearSelection();
                dtm.removeRow(row);
                suKien_ListSK.getPaneTable().initFilterAndButtons();
                suKien_ListSK.getTable().setModel(dtm);
                SuKien_ListSK.clearListDel();
                SuKien_ListSK.clearListRowDel();
                setSave(true);
            }

        }
    }

    public void luu_SuKien() {
        ArrayList<SuKien_Model> listAdd_temp = SuKien_ListSK.getListAdd_temp();
        if (listAdd_temp.size() > 0) {
            SuKien_ListSK.getController().add_SuKien(listAdd_temp);
            if (!SuKien_Controller.getAdd_Failed().isEmpty()) {
                ArrayList<SuKien_Model> add_Failed = SuKien_Controller.getAdd_Failed();
                thatBai = "";
                add_Failed.forEach((t) -> {
                    thatBai += "Thêm sự kiện " + t.getMaSK() + " --- " + t.getTenSK() + " thất bại.";
                });
                Alert.showMessageDialog(this, thatBai, "Thêm thất bại");
                SuKien_Controller.clearAdd();
            }
            SuKien_ListSK.clearListAdd();
            refresh();
        }
        ArrayList<SuKien_Model> listEdit_temp = SuKien_ListSK.getListEdit_temp();
        if (listEdit_temp.size() > 0) {
            SuKien_ListSK.getController().update_SuKien(listEdit_temp);
            if (!SuKien_Controller.getUpdate_Failed().isEmpty()) {
                ArrayList<SuKien_Model> update_Failed = SuKien_Controller.getUpdate_Failed();
                thatBai = "";
                update_Failed.forEach((t) -> {
                    thatBai += "Cập nhật sự kiện " + t.getMaSK() + " --- " + t.getTenSK() + " thất bại.";
                });
                Alert.showMessageDialog(this, thatBai, "Cập nhật thất bại");
                SuKien_Controller.clearUpdate();
            }
            SuKien_ListSK.clearListUpdate();
            refresh();
        }

        if (listDel_SK.size() > 0) {
            SuKien_ListSK.getController().delete_SuKien(listDel_SK);
            if (!SuKien_Controller.getDelete_Failed().isEmpty()) {
                ArrayList<SuKien_Model> update_Failed = SuKien_Controller.getDelete_Failed();
                thatBai = "";
                update_Failed.forEach((t) -> {
                    thatBai += "Xoá sự kiện " + t.getMaSK() + " --- " + t.getTenSK() + " thất bại.";
                });
                Alert.showMessageDialog(this, thatBai, "Xoá thất bại");
                SuKien_Controller.clearDel();
            }
            listDel_SK.clear();
        }

    }

    public void refresh() {
        btn_XoaSK.setEnabled(false);
        btn_LuuSK.setEnabled(false);
        btn_SuaSK.setEnabled(false);
        btn_XemSK.setEnabled(false);
        suKien_ListSK.getTable().getTableModel().fireTableDataChanged();
        suKien_ListSK.refresh();
        suKien_ListSK.setCheckBox(false);
    }

    public void import_SK() {
        luu_SuKien();
        Object[][] ob = SuKien_ListSK.getController().import_SK();
        SuKien_ImportSK pnl_Import = null;
        if (ob != null && ob.length > 0) {
            pnl_Import = new SuKien_ImportSK(ob);
            JLabel lbl_Path_ = new JLabel(SuKien_ListSK.getController().getPath());
            pnl_Import.setLabel(lbl_Path_);
            pnl_Import.getPnl_Path().add(lbl_Path_);
            pnl_Import.getPnl_Path().validate();
            pnl_Import.getPnl_Path().repaint();
            pnl_Import.setVisible(true);
            setSave(true);
        } else if (ob != null) {
            Alert.showMessageDialog(this, "Tập tin không có dữ liệu.", "Import thất bại");
        } else if (ob == null) {
            Alert.showMessageDialog(this, "Bạn chưa chọn file nào.", "Import thất bại");
        }
    }

    public void import_DS() {
        btn_LuuSK.setActionCommand("Them");
        Object[][] ob = SinhVien_List.getController().import_DS();
        SuKien_ImportDS pnl_ImportDS = null;
        if (ob != null && ob.length > 0) {
            pnl_ImportDS = new SuKien_ImportDS(ob);
            pnl_ImportDS.setVisible(true);
            JLabel lbl_Path_ = new JLabel(SinhVien_List.getController().getPath());
            pnl_ImportDS.setLabel(lbl_Path_);
            pnl_ImportDS.getPnl_Path().add(lbl_Path_);
            pnl_ImportDS.getPnl_Path().validate();
            pnl_ImportDS.getPnl_Path().repaint();
            setSave(true);
        } else if (ob != null) {
            Alert.showMessageDialog(this, "Tập tin không có dữ liệu.", "Import thất bại");
        } else if (ob == null) {
            Alert.showMessageDialog(this, "Bạn chưa chọn file nào.", "Import thất bại");
        }
    }

    private void xoa_DanhSach() {
        btn_LuuDS.setActionCommand("XoaDS");
        int typeTable = SuKien_DSThamGia.getTypeTable();
        if (typeTable == 1) {
            ArrayList<DSCanBo_Model> listDel_temp = DSCanBo_View.getListDel_DSCanBo();
            ArrayList<Integer> row_Del = DSCanBo_View.getRow_Del();
            if (listDel_temp.size() > 0) {
                Collections.reverse(row_Del);
                if (Alert.showQuestionDialog(this, "Bạn muốn xoá các lựa chọn?", "Xác nhận xoá") == Alert.OK) {
                    MyCustomTableModel dtm = suKien_Details.getdSThamGia().getdSCanBo_View().getTableModel();
                    row_Del.forEach((row) -> {
                        dtm.removeRow(row);
                    });
                    listDel_DSCB.addAll(listDel_temp);
                    suKien_Details.getdSThamGia().getdSCanBo_View().getTable().setModel(dtm);
                    suKien_Details.getdSThamGia().getdSCanBo_View().getPanelTable().initFilterAndButtons();
                    suKien_Details.getdSThamGia().getdSCanBo_View().getTable().clearSelection();
                    DSCanBo_View.getListDel_DSCanBo().clear();
                    DSCanBo_View.getRow_Del().clear();
                    btn_LuuDS.setEnabled(true);
                }
            } else {
                DSCanBo_Model dsCanBo = suKien_Details.getdSThamGia().getdSCanBo_View().getDSCanBoLite();
                if (Alert.showQuestionDialog(this, "Bạn muốn xoá?\n\n" + dsCanBo.getMaCB() + " -- " + dsCanBo.getTen(), "Xác nhận xoá") == Alert.OK) {
                    MyCustomTableModel dtm = suKien_Details.getdSThamGia().getdSCanBo_View().getTableModel();
                    int row = suKien_Details.getdSThamGia().getdSCanBo_View().getTable().getSelectedRow();
                    listDel_temp.add(new DSCanBo_Model(
                            String.valueOf(suKien_Details.getdSThamGia().getdSCanBo_View().getTable().getValueAt(row, 1)),
                            String.valueOf(suKien_Details.getdSThamGia().getdSCanBo_View().getTable().getValueAt(row, 2)),
                            String.valueOf(suKien_Details.getdSThamGia().getdSCanBo_View().getTable().getValueAt(row, 3))
                    ));
                    listDel_DSCB.addAll(listDel_temp);
                    dtm.removeRow(row);
                    suKien_Details.getdSThamGia().getdSCanBo_View().getTable().setModel(dtm);
                    suKien_Details.getdSThamGia().getdSCanBo_View().getPanelTable().initFilterAndButtons();
                    suKien_Details.getdSThamGia().getdSCanBo_View().getTable().clearSelection();
                    DSCanBo_View.getListDel_DSCanBo().clear();
                    DSCanBo_View.getRow_Del().clear();
                    btn_LuuDS.setEnabled(true);
                }
            }
        } else if (typeTable == 2) {
            ArrayList<DSSinhVien_Model> listDel_temp = DSSinhVien_View.getListDel_DSSV();
            ArrayList<Integer> row_Del = DSSinhVien_View.getRow_Del();
            if (listDel_temp.size() > 0) {
                Collections.reverse(row_Del);
                if (Alert.showQuestionDialog(this, "Bạn muốn xoá các lựa chọn?", "Xác nhận xoá") == Alert.OK) {
                    MyCustomTableModel dtm = suKien_Details.getdSThamGia().getdSSinhVien_View().getTableModel();
                    row_Del.forEach((row) -> {
                        dtm.removeRow(row);
                    });
                    listDel_DSSV.addAll(listDel_temp);
                    suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().setModel(dtm);
                    suKien_Details.getdSThamGia().getdSSinhVien_View().getPanelTable().initFilterAndButtons();
                    suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().clearSelection();
                    DSSinhVien_View.getListDel_DSSV().clear();
                    DSSinhVien_View.getRow_Del().clear();
                    btn_LuuDS.setEnabled(true);
                }
            } else {
                DSSinhVien_Model dsSinhVien = suKien_Details.getdSThamGia().getdSSinhVien_View().getDSSinhVienLite();
                if (Alert.showQuestionDialog(this, "Bạn muốn xoá?\n\n" + dsSinhVien.getMaSV() + " -- " + dsSinhVien.getTen(), "Xác nhận xoá") == Alert.OK) {
                    MyCustomTableModel dtm = suKien_Details.getdSThamGia().getdSSinhVien_View().getTableModel();
                    int row = suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().getSelectedRow();
                    listDel_temp.add(new DSSinhVien_Model(
                            String.valueOf(suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().getValueAt(row, 1)),
                            String.valueOf(suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().getValueAt(row, 2)),
                            String.valueOf(suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().getValueAt(row, 3))
                    ));
                    listDel_DSSV.addAll(listDel_temp);
                    dtm.removeRow(row);
                    suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().setModel(dtm);
                    suKien_Details.getdSThamGia().getdSSinhVien_View().getPanelTable().initFilterAndButtons();
                    suKien_Details.getdSThamGia().getdSSinhVien_View().getTable().clearSelection();
                    DSSinhVien_View.getListDel_DSSV().clear();
                    DSSinhVien_View.getRow_Del().clear();
                    btn_LuuDS.setEnabled(true);
                }
            }
        }
    }

    public void luu_DanhSach() {
        if (btn_LuuDS.getActionCommand().equals("Them")) {
            Alert.showMessageDialog(this, "Đã cập nhật thay đổi!", "Thông báo");
        }
        if (btn_LuuDS.getActionCommand().equals("XoaDS")) {
            String masukien = suKien_Details.getdSThamGia().getdSCanBo_View().getMasukien();
            if (listDel_DSCB.size() > 0) {
                DSCanBo_View.getController().delete_dsCanBo(listDel_DSCB, masukien);
                if (!DSCanBo_Controller.getDelete_Failed().isEmpty()) {
                    ArrayList<DSCanBo_Model> delete_Failed = DSCanBo_Controller.getDelete_Failed();
                    thatBai = "";
                    delete_Failed.forEach((t) -> {
                        thatBai += "Xoá sự kiện " + t.getMaCB() + " --- " + t.getTen() + " thất bại.";
                    });
                    Alert.showMessageDialog(this, thatBai, "Xoá thất bại");
                }
                Alert.showMessageDialog(this, "Đã cập nhật thay đổi!", "Thông báo");
                listDel_DSCB.clear();
                tai_DanhSach();
                suKien_Details.getdSThamGia().showDSCanBo();

            }
            if (listDel_DSSV.size() > 0) {
                DSSinhVien_View.getController().delete_dsSinhVien(listDel_DSSV, masukien);
                if (!DSSinhVien_Controller.getDelete_Failed().isEmpty()) {
                    ArrayList<DSSinhVien_Model> delete_Failed = DSSinhVien_Controller.getDelete_Failed();
                    thatBai = "";
                    delete_Failed.forEach((t) -> {
                        thatBai += "Xoá sinh viên " + t.getMaSV() + " --- " + t.getTen() + " thất bại.";
                    });
                    Alert.showMessageDialog(this, thatBai, "Xoá thất bại");
                }
                Alert.showMessageDialog(this, "Đã cập nhật thay đổi!", "Thông báo");
                listDel_DSSV.clear();
                tai_DanhSach();
                suKien_Details.getdSThamGia().showDSSinhVien();
            }
        }
    }

    private void tai_DanhSach() {
        btn_LuuDS.setEnabled(false);
        btn_LuuSK.setEnabled(false);
        btn_XoaDS.setEnabled(false);
        btn_XoaSK.setEnabled(false);
        btn_SuaSK.setEnabled(false);
        suKien_Details.getdSThamGia().showTable();
    }

}
