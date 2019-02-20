package thongke;

import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import static administrator.settings.Config.fg_Color1;
import static administrator.settings.Config.fg_Color2;
import app.view.App_View;
import other.custom.GradientButton;
import sukien.view.SuKien_ListSK;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import login.Login_View;
import other.custom.Alert;
import other.custom.StringUtils;
import other.custom.TransitionPane;
import resources.Resources;
import static resources.Resources.change_Icon;
import sukien.model.SuKien_Model;

/**
 *
 * @author chuna
 */
public class ThongKe_View extends javax.swing.JPanel implements ActionListener {

    private GradientButton btn_DoiBang, btn_XemThongKe, btn_XuatRaExcel, btn_ToggleBarChart, btn_Refresh;

    private SuKien_ListSK suKien_ListSK;
    private ThongKe_Details details = new ThongKe_Details();
    private ThongKe_Controller controller = new ThongKe_Controller();
    private SuKien_Model suKien_Model;

    public ThongKe_Controller getController() {
        return controller;
    }

    public void setController(ThongKe_Controller controller) {
        this.controller = controller;
    }

    public ThongKe_View() {
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
        suKien_ListSK = new SuKien_ListSK(SuKien_ListSK.REPORT);
        initComponents();
        createUI();
    }

    public ThongKe_View(SuKien_Model suKien_Model) {
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
        suKien_ListSK = new SuKien_ListSK(SuKien_ListSK.REPORT);
        this.suKien_Model = suKien_Model;
        xem_SuKien(this.suKien_Model);
        changePanel(1, suKien_Model.getTenSK());
    }

    public void showPanel() {
        suKien_ListSK.execute();
        loadData();
    }

    private void createUI() {
        btn_XuatRaExcel = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Xuất kết quả điểm danh", Resources.import_Icon);
        btn_XuatRaExcel.addActionListener(this);
        btn_ToggleBarChart = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Ẩn biểu đồ", Resources.bieuDo_Icon);
        btn_ToggleBarChart.addActionListener(this);
        btn_XemThongKe = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Xem Thống kê", Resources.thongKe_Icon);
        btn_XemThongKe.addActionListener(this);
        btn_DoiBang = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Chuyển đổi danh sách", change_Icon);
        btn_Refresh = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Làm mới", Resources.refresh_Icon);
        btn_Refresh.addActionListener(this);
        btn_DoiBang.addActionListener(this);
        pnl_TaskLeft.add(btn_DoiBang);

        pnl_TaskRight.add(btn_Refresh);
        pnl_TaskRight.add(btn_XemThongKe);
        pnl_TaskRight.add(btn_ToggleBarChart);
        pnl_TaskRight.add(btn_XuatRaExcel);

        btn_DoiBang.setVisible(false);
        btn_ToggleBarChart.setVisible(false);
        btn_Back.setIcon(Resources.back_Icon);
        btn_Back.setVisible(false);
        btn_ChiTiet.setIcon(Resources.xem_Icon);
        btn_ChiTiet.setVisible(false);
        TransitionPane.replacePane(getPnl_Table(), suKien_ListSK);
        btn_ToggleBarChart.addActionListener(details.getToggleAction());
        btn_ToggleBarChart.setText("Xem dưới dạng biểu đồ");
        btn_XemThongKe.setEnabled(false);
        btn_XuatRaExcel.setEnabled(false);
    }

    private void loadData() {
        suKien_ListSK.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btn_XemThongKe.setEnabled(true);
                btn_XuatRaExcel.setEnabled(true);
                suKien_Model = suKien_ListSK.getValue();
                xem_SuKien();
                if (e.getClickCount() == 2) {
                    changePanel(1, suKien_Model.getTenSK());
                    details.showTable();
                }
            }
        });
        suKien_ListSK.getTable().setCheckBox(0);
        suKien_ListSK.getTable().hideColumn(0);
    }

    public void xem_SuKien() {
        details.setSuKien_Model(suKien_ListSK.getValue());
        details.loadData();
    }

    public void xem_SuKien(SuKien_Model suKien_Model) {
        details.setSuKien_Model(suKien_Model);
        details.loadData();
        changePanel(1, suKien_Model.getTenSK());
        details.showTable();
    }

    public SuKien_ListSK getSuKien_ListSK() {
        return suKien_ListSK;
    }

    public void setSuKien_ListSK(SuKien_ListSK suKien_ListSK) {
        this.suKien_ListSK = suKien_ListSK;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Table = new javax.swing.JPanel();
        pnl_Task = new javax.swing.JPanel();
        pnl_TaskLeft = new javax.swing.JPanel();
        pnl_TaskRight = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_Back = new javax.swing.JButton();
        lbl_Title = new javax.swing.JLabel();
        btn_ChiTiet = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1098, 768));

        pnl_Table.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Table.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Task.setBackground(new java.awt.Color(204, 204, 255));
        pnl_Task.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_Task.setPreferredSize(new java.awt.Dimension(0, 45));

        pnl_TaskLeft.setPreferredSize(new java.awt.Dimension(200, 100));
        pnl_TaskLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        pnl_TaskRight.setPreferredSize(new java.awt.Dimension(500, 100));
        pnl_TaskRight.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        javax.swing.GroupLayout pnl_TaskLayout = new javax.swing.GroupLayout(pnl_Task);
        pnl_Task.setLayout(pnl_TaskLayout);
        pnl_TaskLayout.setHorizontalGroup(
            pnl_TaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TaskLayout.createSequentialGroup()
                .addComponent(pnl_TaskLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_TaskRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_TaskLayout.setVerticalGroup(
            pnl_TaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TaskLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnl_TaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_TaskLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(pnl_TaskRight, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));
        jPanel3.setMinimumSize(new java.awt.Dimension(64, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(177, 50));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0);
        flowLayout1.setAlignOnBaseline(true);
        jPanel3.setLayout(flowLayout1);

        btn_Back.setBorderPainted(false);
        btn_Back.setContentAreaFilled(false);
        btn_Back.setFocusPainted(false);
        btn_Back.setHideActionText(true);
        btn_Back.setPreferredSize(new java.awt.Dimension(54, 42));
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Back);

        lbl_Title.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(102, 102, 102));
        lbl_Title.setText("Thống kê");
        lbl_Title.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 15, 10));
        lbl_Title.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel3.add(lbl_Title);

        btn_ChiTiet.setFont(new java.awt.Font("Sitka Banner", 0, 14)); // NOI18N
        btn_ChiTiet.setBorder(null);
        btn_ChiTiet.setBorderPainted(false);
        btn_ChiTiet.setContentAreaFilled(false);
        btn_ChiTiet.setFocusPainted(false);
        btn_ChiTiet.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn_ChiTiet.setPreferredSize(new java.awt.Dimension(40, 40));
        btn_ChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChiTietActionPerformed(evt);
            }
        });
        jPanel3.add(btn_ChiTiet);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Task, javax.swing.GroupLayout.DEFAULT_SIZE, 1098, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1098, Short.MAX_VALUE)
            .addComponent(pnl_Table, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Task, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        changePanel(0, "Sự kiện");
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_ChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChiTietActionPerformed
        Login_View.suKien_View.getSuKien_Details().setSuKien_Model(suKien_ListSK.getValue());
        TransitionPane.showDialogBox(this, Login_View.suKien_View.getSuKien_Details().getPnl_Display(), "Chi tiết sự kiện", Resources.suKien_Icon);
        TransitionPane.getDialog().setSize(new Dimension(500, 450));
        TransitionPane.getDialog().setLocationRelativeTo(this);
    }//GEN-LAST:event_btn_ChiTietActionPerformed

    private void changePanel(int pos, String title) {
        boolean screen = pos == 0;
        TransitionPane.replacePane(getPnl_Table(), screen ? suKien_ListSK : details);
        btn_Back.setVisible(!screen);
        lbl_Title.setText(title);
        btn_DoiBang.setVisible(!screen);
        btn_ToggleBarChart.setVisible(!screen);
//        btn_XuatRaExcel.setText(screen ? "Xuất danh sách sự kiện" : "Xuất kết quả điểm danh");
        btn_XemThongKe.setVisible(screen);
        btn_ChiTiet.setVisible(!screen);
        if (screen) {
            showPanel();
        } else {
            details.excuteDanhSach();
        }
    }

    public JPanel getPnl_Table() {
        return pnl_Table;
    }

    public void setPnl_Table(JPanel pnl_Table) {
        this.pnl_Table = pnl_Table;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Back;
    private javax.swing.JButton btn_ChiTiet;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel pnl_Table;
    private javax.swing.JPanel pnl_Task;
    private javax.swing.JPanel pnl_TaskLeft;
    private javax.swing.JPanel pnl_TaskRight;
    // End of variables declaration//GEN-END:variables

    private int toggle = -1;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob.equals(btn_DoiBang)) {
            if (details.getPnl_Table().getComponent(0).equals(details.getdSCanBo_View())) {
                TransitionPane.replacePane(details.getPnl_Table(), details.getdSSinhVien_View());
                btn_DoiBang.setToolTipText("Xem Danh Sách Sinh Viên");
                details.showDSSinhVien();
            } else {
                TransitionPane.replacePane(details.getPnl_Table(), details.getdSCanBo_View());
                btn_DoiBang.setToolTipText("Xem Danh Sách Cán Bộ");
                details.showDSCanBo();
            }
        }
        if (ob.equals(btn_XemThongKe)) {
            xem_SuKien();
            changePanel(1, suKien_ListSK.getValue().getTenSK());
            details.showTable();
        }
        if (ob.equals(btn_ToggleBarChart)) {
            toggle++;
            if (toggle % 2 == 0) {
                btn_ToggleBarChart.setText("Xem dưới dạng danh sách");
            } else {
                btn_ToggleBarChart.setText("Xem dưới dạng biểu đồ");
            }
        }
        if (ob.equals(btn_XuatRaExcel)) {
            int select = Alert.showQuestionDialog(this, "Bạn muốn xuất báo cáo dưới dạng tập tin Excel không?", "Xác nhận xuất báo cáo");
            if (select == Alert.OK) {
                String str = getPathFileSave();
                if (!str.equals("")) {
                    String[] tokens = str.split("\\.(?=[^\\.]+$)");
                    if (tokens[1].equals("xls")) {
                        controller.xuatFileBaoCao(suKien_Model, str);
                    } else {
                        str += ".xls";
                        controller.xuatFileBaoCao(suKien_Model, str);
                    }
                } else {
                    Alert.showWarning(this, "Tên tập tin không hợp lệ.");
                }
            }
        }
        if (ob.equals(btn_Refresh)) {
            if (!btn_ToggleBarChart.isVisible()) {
                showPanel();
            } else {
                details.showTable();
            }
        }
    }

    public String getPathFileSave() {
        JFileChooser c = new JFileChooser();
        String path = "", dir = "";
        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            path = c.getSelectedFile().getName();
            dir = c.getCurrentDirectory().toString();
            return dir + "\\" + path;
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            return "";
        }
        return "";
    }
}
