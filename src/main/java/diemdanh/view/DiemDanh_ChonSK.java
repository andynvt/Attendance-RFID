/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.view;

import app.view.App_View;
import sukien.view.SuKien_ListSK;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import login.Login_View;
import other.custom.Alert;
import other.custom.TransitionPane;

/**
 *
 * @author chuna
 */
public class DiemDanh_ChonSK extends JDialog {

    private DiemDanh_Details xemChiTiet;
    private SuKien_ListSK suKien_List;
    private boolean selected;

    public DiemDanh_ChonSK(DiemDanh_Details details) {
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
        suKien_List = new SuKien_ListSK(SuKien_ListSK.ATTENDENCE);
        xemChiTiet = details;
        initComponents();
    }

    public void showPanel() {
        createUI();
        loadData();
        suKien_List.execute();
    }

    private void createUI() {
        if (suKien_List.getSuKien_Models().isEmpty()) {
            Alert.showMessageDialog(App_View.getContainer(), "Chưa có sự kiện để điểm danh.", "Thông báo");
        } else {
            TransitionPane.replacePane(getPnl_Content(), suKien_List);
            pack();
            setLocationRelativeTo(App_View.getContainer());
        }
    }

    private void loadData() {
        suKien_List.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xem_SuKien();
            }
        });
        suKien_List.getPaneTable().getTable().setCheckBox(0);
        suKien_List.getPaneTable().getTable().hideColumn(0);
    }

    public void xem_SuKien() {
        try {
            xemChiTiet.setSuKien_Model(suKien_List.getValue());
        } catch (NullPointerException e) {
            System.out.println("Loi");
        }

    }

    public SuKien_ListSK getSuKien_List() {
        return suKien_List;
    }

    public void setSuKien_List(SuKien_ListSK suKien_List) {
        this.suKien_List = suKien_List;
    }

    public JPanel getPnl_Content() {
        return pnl_Content;
    }

    public void setPnl_Content(JPanel pnl_Content) {
        this.pnl_Content = pnl_Content;
    }

    public DiemDanh_Details getXemChiTiet() {
        return xemChiTiet;
    }

    public void setXemChiTiet(DiemDanh_Details xemChiTiet) {
        this.xemChiTiet = xemChiTiet;
    }

    public JButton getBtn_Chon() {
        return btn_Chon;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnl_Action = new javax.swing.JPanel();
        btn_Chon = new javax.swing.JButton();
        btn_Dong = new javax.swing.JButton();
        pnl_Content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        pnl_Action.setPreferredSize(new java.awt.Dimension(0, 36));
        pnl_Action.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        btn_Chon.setBackground(new java.awt.Color(33, 150, 243));
        btn_Chon.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_Chon.setForeground(new java.awt.Color(255, 255, 255));
        btn_Chon.setText("Chọn");
        btn_Chon.setBorderPainted(false);
        btn_Chon.setContentAreaFilled(false);
        btn_Chon.setFocusPainted(false);
        btn_Chon.setOpaque(true);
        btn_Chon.setPreferredSize(new java.awt.Dimension(91, 36));
        btn_Chon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChonActionPerformed(evt);
            }
        });
        pnl_Action.add(btn_Chon);

        btn_Dong.setBackground(new java.awt.Color(33, 150, 243));
        btn_Dong.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_Dong.setForeground(new java.awt.Color(255, 255, 255));
        btn_Dong.setText("Huỷ bỏ");
        btn_Dong.setBorderPainted(false);
        btn_Dong.setContentAreaFilled(false);
        btn_Dong.setFocusPainted(false);
        btn_Dong.setOpaque(true);
        btn_Dong.setPreferredSize(new java.awt.Dimension(91, 36));
        btn_Dong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DongActionPerformed(evt);
            }
        });
        pnl_Action.add(btn_Dong);

        pnl_Content.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Action, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
            .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_DongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DongActionPerformed
        dispose();
        selected = false;
    }//GEN-LAST:event_btn_DongActionPerformed

    private void btn_ChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChonActionPerformed
        xemChiTiet.setDiemDanh(true);
        xemChiTiet.setSuKien_Model(suKien_List.getValue());
        TransitionPane.replacePane(Login_View.diemDanh_View.getContent(), xemChiTiet);
        TransitionPane.replacePane(App_View.getContainer(), Login_View.diemDanh_View);
        if (xemChiTiet.isEmptyCB() && xemChiTiet.isEmptySV()) {
            Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setEnabled(false);
            Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setText("Bắt đầu Điểm danh vào");
        } else {
            if (SuKien_ListSK.getController().isDiemDanh(suKien_List.getValue().getMaSK())) {
                Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setText("Đã điểm danh");
                Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setEnabled(false);
            } else {
                if (SuKien_ListSK.getController().isDiemDanhVao(suKien_List.getValue().getMaSK())) {
                    Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setEnabled(true);
                    Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setText("Bắt đầu Điểm danh ra");
                    if (SuKien_ListSK.getController().isDiemDanhRa(suKien_List.getValue().getMaSK())) {
                        Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setText("Đã điểm danh");
                        Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setEnabled(false);
                        SuKien_ListSK.getController().setDiemDanh(true, suKien_List.getValue().getMaSK());
                    }
                } else {
                    Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setEnabled(true);
                    Login_View.diemDanh_View.getBtn_BatDauDiemDanh().setText("Bắt đầu Điểm danh vào");
                }
            }
        }
        selected = true;
        xemChiTiet.getdSThamGia().showTable();
        dispose();
    }//GEN-LAST:event_btn_ChonActionPerformed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        dispose();
    }//GEN-LAST:event_formWindowLostFocus

    public boolean isSelected() {
        System.out.println(selected);
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Chon;
    private javax.swing.JButton btn_Dong;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnl_Action;
    private javax.swing.JPanel pnl_Content;
    // End of variables declaration//GEN-END:variables
}
