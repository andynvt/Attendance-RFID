package about;

import app.view.App_View;
import diemdanh.other.custom.TabbedPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import other.custom.TransitionPane;

public class About_View extends javax.swing.JPanel {

    private JPanel pnl_Info, pnl_Info_Top, pnl_Cancel1;
    private JLabel lbl_n8;
    private JTextArea txa_Info;
    private JButton btn_Cancel1;

    private JPanel pnl_Guide, pnl_Guide_Top, pnl_Guide_Bottom, pnl_Cancel2;
    private JButton btn_trangchu, btn_quanliCB, btn_quanliSV, btn_quanliSK, btn_diemdanh, btn_thongke, btn_quanliDN, btn_Cancel2;
    private JLabel lbl_guide;

    public About_View() {
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
        TabbedPane.setTitleTab(jTabbedPane1.indexOfComponent(jPanel1), "Thông tin phần mềm", jTabbedPane1);
        TabbedPane.setTitleTab(jTabbedPane1.indexOfComponent(jPanel2), "Hướng dẫn sử dụng", jTabbedPane1);

        jPanel1.setPreferredSize(new Dimension(920, 550));
        jPanel1.setSize(new Dimension(920, 550));

        jPanel2.setPreferredSize(new Dimension(920, 550));
        jPanel2.setSize(new Dimension(920, 550));

        About_Info();
        About_Guide();

    }

    public void About_Info() {
        pnl_Info = new JPanel();
        pnl_Info.setLayout(new FlowLayout(FlowLayout.LEADING));
        Dimension size = new Dimension(920, 550);
        pnl_Info.setPreferredSize(size);
        pnl_Info.setSize(size);
        pnl_Info.setBackground(Color.decode("#e1e1e1"));

        pnl_Info_Top = new JPanel();
        pnl_Info_Top.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnl_Info_Top.setPreferredSize(new Dimension(910, 80));
        pnl_Info_Top.setSize(new Dimension(910, 80));
        pnl_Info_Top.setBackground(Color.decode("#6a7390"));

        lbl_n8 = new JLabel("");
        lbl_n8.setIcon(new ImageIcon("src/main/resources/image/1.png"));
        lbl_n8.setText("");

        pnl_Cancel1 = new JPanel();
        pnl_Cancel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnl_Cancel1.setPreferredSize(new Dimension(920, 40));
        pnl_Cancel1.setSize(new Dimension(920, 40));
        pnl_Cancel1.setBackground(Color.decode("#e1e1e1"));

        btn_Cancel1 = new JButton("Đóng");
        btn_Cancel1.setPreferredSize(new Dimension(50, 25));
        btn_Cancel1.setSize(new Dimension(50, 25));
        btn_Cancel1.setContentAreaFilled(false);
        btn_Cancel1.setOpaque(true);
        btn_Cancel1.setFocusable(false);
        btn_Cancel1.setFont(new Font("Arial", Font.BOLD, 13));
        btn_Cancel1.setMargin(new Insets(0, 0, 0, 0));
        btn_Cancel1.setForeground(Color.DARK_GRAY);
        btn_Cancel1.setBackground(Color.LIGHT_GRAY);

        btn_Cancel1.addActionListener((ActionEvent e) -> {
            TransitionPane.closeDialogBox();
        });

        txa_Info = new JTextArea();
        txa_Info.setFont(new Font("Arial", Font.BOLD, 16));
        txa_Info.setEditable(false);
        txa_Info.setBackground(Color.decode("#e1e1e1"));
        txa_Info.setForeground(Color.decode("#000022"));
        txa_Info.setText(" - Tên phần mềm: Phần mềm Điểm Danh V1.0\n"
                + " - Ngôn ngữ lập trình: Java\n"
                + " - Công cụ lập trình: NetBeans IDE 8.2\n"
                + " - Được tạo bởi: nhóm N8 Plus\n"
                + " - Thành viên nhóm:\n"
                + "     + Nguyễn Hoài Chung\n"
                + "     + Huỳnh Khắc Duy\n"
                + "     + Nguyễn Văn Hiệp\n"
                + "     + Đặng Minh Nhựt\n"
                + "     + Dương Thành Oai\n"
                + "     + Phạm Ngọc Long Phi\n"
                + "     + Võ Hoài Phong\n"
                + "     + Võ Nguyễn Đại Phúc\n"
                + "     + Nguyễn Văn Tài\n"
                + " - Điện thoại liên hệ: 0914582382\n"
                + " - Phần mềm phục vụ cho nhu cầu điểm danh của người dùng.\n"
                + " - Để tiện sử dụng có thể xem trang Hướng dẫn.\n"
                + " - Phần mềm có thêm tài liệu đặc tả, thiết kế mô tả chi tiết sản phẩm.\n"
                + " - Ngày hoàn thiện sản phẩm : 8/11/2017\n"
                + " - Khoảng thời gian hoàn thành dự án: 4 tháng\n"
                + " - Dự kiến phần mềm sẽ tiếp tục phát triển thêm một số chức năng.\n"
                + " - Website nhóm: n8plus.com");

        pnl_Info.add(pnl_Info_Top);
        pnl_Info_Top.add(lbl_n8);
        pnl_Info.add(txa_Info);
        pnl_Cancel1.add(btn_Cancel1);
        pnl_Info.add(pnl_Cancel1);
        jPanel1.add(pnl_Info);
    }

    public void About_Guide() {
        pnl_Guide = new JPanel();
        pnl_Guide.setLayout(new FlowLayout(FlowLayout.LEADING));
        Dimension size = new Dimension(920, 550);
        pnl_Guide.setPreferredSize(size);
        pnl_Guide.setSize(size);
        pnl_Guide.setBackground(Color.decode("#e1e1e1"));

        pnl_Guide_Top = new JPanel();
        pnl_Guide_Top.setLayout(new FlowLayout(FlowLayout.LEADING));
        pnl_Guide_Top.setPreferredSize(new Dimension(920, 465));
        pnl_Guide_Top.setSize(new Dimension(920, 465));
        pnl_Guide_Top.setBackground(Color.decode("#e1e1e1"));

        pnl_Guide_Bottom = new JPanel();
        pnl_Guide_Bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnl_Guide_Bottom.setPreferredSize(new Dimension(920, 40));
        pnl_Guide_Bottom.setSize(new Dimension(920, 40));
        pnl_Guide_Bottom.setBackground(Color.decode("#e1e1e1"));

        pnl_Cancel2 = new JPanel();
        pnl_Cancel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnl_Cancel2.setPreferredSize(new Dimension(920, 40));
        pnl_Cancel2.setSize(new Dimension(920, 40));
        pnl_Cancel2.setBackground(Color.decode("#e1e1e1"));

        btn_trangchu = new JButton("Trang chủ");
        btn_trangchu.setPreferredSize(new Dimension(100, 30));
        btn_trangchu.setSize(new Dimension(100, 30));
        btn_trangchu.setContentAreaFilled(false);
        btn_trangchu.setOpaque(true);
        btn_trangchu.setFocusable(false);
        btn_trangchu.setFont(new Font("Arial", Font.BOLD, 13));
        btn_trangchu.setMargin(new Insets(0, 0, 0, 0));
        btn_trangchu.setForeground(Color.WHITE);
        btn_trangchu.setBackground(Color.DARK_GRAY);

        btn_quanliCB = new JButton("Quản lý cán bộ");
        btn_quanliCB.setPreferredSize(new Dimension(120, 30));
        btn_quanliCB.setSize(new Dimension(120, 30));
        btn_quanliCB.setContentAreaFilled(false);
        btn_quanliCB.setOpaque(true);
        btn_quanliCB.setFocusable(false);
        btn_quanliCB.setFont(new Font("Arial", Font.BOLD, 13));
        btn_quanliCB.setMargin(new Insets(0, 0, 0, 0));
        btn_quanliCB.setForeground(Color.WHITE);
        btn_quanliCB.setBackground(Color.DARK_GRAY);

        btn_quanliSV = new JButton("Quản lý sinh viên");
        btn_quanliSV.setPreferredSize(new Dimension(140, 30));
        btn_quanliSV.setSize(new Dimension(140, 30));
        btn_quanliSV.setContentAreaFilled(false);
        btn_quanliSV.setOpaque(true);
        btn_quanliSV.setFocusable(false);
        btn_quanliSV.setFont(new Font("Arial", Font.BOLD, 13));
        btn_quanliSV.setMargin(new Insets(0, 0, 0, 0));
        btn_quanliSV.setForeground(Color.WHITE);
        btn_quanliSV.setBackground(Color.DARK_GRAY);

        btn_quanliSK = new JButton("Quản lý sự kiện");
        btn_quanliSK.setPreferredSize(new Dimension(130, 30));
        btn_quanliSK.setSize(new Dimension(130, 30));
        btn_quanliSK.setContentAreaFilled(false);
        btn_quanliSK.setOpaque(true);
        btn_quanliSK.setFocusable(false);
        btn_quanliSK.setFont(new Font("Arial", Font.BOLD, 13));
        btn_quanliSK.setMargin(new Insets(0, 0, 0, 0));
        btn_quanliSK.setForeground(Color.WHITE);
        btn_quanliSK.setBackground(Color.DARK_GRAY);

        btn_diemdanh = new JButton("Điểm danh");
        btn_diemdanh.setPreferredSize(new Dimension(100, 30));
        btn_diemdanh.setSize(new Dimension(100, 30));
        btn_diemdanh.setContentAreaFilled(false);
        btn_diemdanh.setOpaque(true);
        btn_diemdanh.setFocusable(false);
        btn_diemdanh.setFont(new Font("Arial", Font.BOLD, 13));
        btn_diemdanh.setMargin(new Insets(0, 0, 0, 0));
        btn_diemdanh.setForeground(Color.WHITE);
        btn_diemdanh.setBackground(Color.DARK_GRAY);

        btn_thongke = new JButton("Thống kê");
        btn_thongke.setPreferredSize(new Dimension(100, 30));
        btn_thongke.setSize(new Dimension(100, 30));
        btn_thongke.setContentAreaFilled(false);
        btn_thongke.setOpaque(true);
        btn_thongke.setFocusable(false);
        btn_thongke.setFont(new Font("Arial", Font.BOLD, 13));
        btn_thongke.setMargin(new Insets(0, 0, 0, 0));
        btn_thongke.setForeground(Color.WHITE);
        btn_thongke.setBackground(Color.DARK_GRAY);

        btn_quanliDN = new JButton("Quản lý tài khoản");
        btn_quanliDN.setPreferredSize(new Dimension(140, 30));
        btn_quanliDN.setSize(new Dimension(140, 30));
        btn_quanliDN.setContentAreaFilled(false);
        btn_quanliDN.setOpaque(true);
        btn_quanliDN.setFocusable(false);
        btn_quanliDN.setFont(new Font("Arial", Font.BOLD, 13));
        btn_quanliDN.setMargin(new Insets(0, 0, 0, 0));
        btn_quanliDN.setForeground(Color.WHITE);
        btn_quanliDN.setBackground(Color.DARK_GRAY);

        btn_Cancel2 = new JButton("Đóng");
        btn_Cancel2.setPreferredSize(new Dimension(50, 25));
        btn_Cancel2.setSize(new Dimension(50, 25));
        btn_Cancel2.setContentAreaFilled(false);
        btn_Cancel2.setOpaque(true);
        btn_Cancel2.setFocusable(false);
        btn_Cancel2.setFont(new Font("Arial", Font.BOLD, 13));
        btn_Cancel2.setMargin(new Insets(0, 0, 0, 0));
        btn_Cancel2.setForeground(Color.DARK_GRAY);
        btn_Cancel2.setBackground(Color.LIGHT_GRAY);

        lbl_guide = new JLabel("");
        lbl_guide.setIcon(new ImageIcon("src/main/resources/image/3.png"));
        lbl_guide.setText(null);

        btn_trangchu.addActionListener((ActionEvent e) -> {
            lbl_guide.setIcon(new ImageIcon("src/main/resources/image/3.png"));
        });

        btn_quanliCB.addActionListener((ActionEvent e) -> {
            lbl_guide.setIcon(new ImageIcon("src/main/resources/image/4.png"));
        });

        btn_quanliSV.addActionListener((ActionEvent e) -> {
            lbl_guide.setIcon(new ImageIcon("src/main/resources/image/5.png"));
        });

        btn_quanliSK.addActionListener((ActionEvent e) -> {
            lbl_guide.setIcon(new ImageIcon("src/main/resources/image/6.png"));
        });

        btn_diemdanh.addActionListener((ActionEvent e) -> {
            lbl_guide.setIcon(new ImageIcon("src/main/resources/image/7.png"));
        });

        btn_thongke.addActionListener((ActionEvent e) -> {
            lbl_guide.setIcon(new ImageIcon("src/main/resources/image/8.png"));
        });

        btn_quanliDN.addActionListener((ActionEvent e) -> {
            lbl_guide.setIcon(new ImageIcon("src/main/resources/image/9.png"));
        });

        btn_Cancel2.addActionListener((ActionEvent e) -> {

        });

        pnl_Guide_Top.add(lbl_guide);
        pnl_Guide_Bottom.add(btn_trangchu);
        pnl_Guide_Bottom.add(btn_quanliCB);
        pnl_Guide_Bottom.add(btn_quanliSV);
        pnl_Guide_Bottom.add(btn_quanliSK);
        pnl_Guide_Bottom.add(btn_diemdanh);
        pnl_Guide_Bottom.add(btn_thongke);
        pnl_Guide_Bottom.add(btn_quanliDN);
        pnl_Cancel2.add(btn_Cancel2);

        pnl_Guide.add(pnl_Guide_Top);
        pnl_Guide.add(pnl_Guide_Bottom);
        pnl_Guide.add(pnl_Cancel2);
        jPanel2.add(pnl_Guide);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 624, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 494, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 624, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 494, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        add(jTabbedPane1);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
