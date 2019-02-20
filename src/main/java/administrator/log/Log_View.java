/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrator.log;

import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import static administrator.settings.Config.fg_Color1;
import static administrator.settings.Config.fg_Color2;
import app.view.App_View;
import other.custom.GradientButton;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import login.Login_View;
import static login.Login_View.addmin_View;
import other.custom.Alert;
import other.custom.TransitionPane;
import other.table.PanelTable;
import static resources.Resources.exit_Icon;
import static resources.Resources.logout_Icon;
import static resources.Resources.refresh_Icon;

/**
 *
 * @author chuna
 */
public class Log_View extends javax.swing.JPanel {

    private String[] columnNames = new String[]{"STT", "Tài khoản", "Thời điểm đăng nhập", "Thời điểm đăng xuất"};
    private ArrayList<Log_Model> admin_Models = new ArrayList<>();
    private final Log_Controller admin_Controller = new Log_Controller();
    private PanelTable table;

    public Log_Controller getAdmin_Controller() {
        return admin_Controller;
    }

    public Log_View() {
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

    public void showPanel() {
        table.executeTable();
    }

    private void createUI() {
        btn_Reset = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Xoá lịch sử làm việc", refresh_Icon);
        btn_Reset.addActionListener((ActionEvent e) -> {
            int select = Alert.showQuestionDialog(this, "Bạn muốn xoá lịch sử làm việc?\nHệ thống sẽ đăng xuất sau khi xoá lịch sử.", "Xoá lịch sử?");
            if (select == Alert.OK) {
                admin_Controller.clearLog();
                new Login_View().setVisible(true);
                Login_View.app_View.dispose();
                TransitionPane.closeDialogBox();
            }
        });

        btn_Dangxuat = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Đăng xuất", logout_Icon);
        btn_Dangxuat.addActionListener((ActionEvent e) -> {
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
                java.util.logging.Logger.getLogger(Login_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            java.awt.EventQueue.invokeLater(() -> {
                addmin_View.getAdmin_Controller().updateTimeLogout();
                new Login_View().setVisible(true);
            });
            TransitionPane.closeDialogBox();
            Login_View.app_View.dispose();
        });
        btn_Thoat = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Thoát ứng dụng", exit_Icon);
        btn_Thoat.addActionListener((ActionEvent e) -> {
            addmin_View.getAdmin_Controller().updateTimeLogout();
            System.exit(0);
        });
        pnl_AdvanceBar.add(btn_Reset);
        pnl_AdvanceBar.add(btn_Dangxuat);
        pnl_AdvanceBar.add(btn_Thoat);
    }

    private void loadData() {
        admin_Models = admin_Controller.load_TimeWork();
        table = new PanelTable(Log_Controller.array2Object(admin_Models), columnNames);
        table.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TransitionPane.replacePane(pnl_Table, table);
        TableColumnModel colModel = table.getTable().getColumnModel();
        colModel.getColumn(0).setPreferredWidth(80);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(300);
        colModel.getColumn(3).setPreferredWidth(300);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Table = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnl_AdvanceBar = new javax.swing.JPanel();

        pnl_Table.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Table.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 15, 5));
        pnl_Table.setLayout(new java.awt.GridLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Quản lý phiên đăng nhập");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 15, 10));
        jLabel2.setOpaque(true);

        pnl_AdvanceBar.setBackground(new java.awt.Color(204, 204, 255));
        pnl_AdvanceBar.setMinimumSize(new java.awt.Dimension(0, 45));
        pnl_AdvanceBar.setPreferredSize(new java.awt.Dimension(0, 45));
        pnl_AdvanceBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addComponent(pnl_AdvanceBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnl_AdvanceBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private GradientButton btn_Reset;
    private GradientButton btn_Dangxuat;
    private GradientButton btn_Thoat;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnl_AdvanceBar;
    private javax.swing.JPanel pnl_Table;
    // End of variables declaration//GEN-END:variables
}
