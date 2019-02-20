/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view;

import app.controller.App_Controller;
import other.custom.GradientButton;
import canbo.view.CanBo_Registor;
import other.custom.DataString;
import static administrator.settings.Config.BG_BUTTON;
import static administrator.settings.Config.BG_BUTTON_HOVER;
import static administrator.settings.Config.BG_NAV;
import static administrator.settings.Config.FG_BUTTON;
import static administrator.settings.Config.FG_BUTTON_HOVER;
import canbo.view.CanBo_View;
import database.DatabaseHandler;
import app.model.App_Model;
import diemdanh.other.custom.*;
import diemdanh.view.DiemDanh_View;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import org.jdesktop.swingx.border.DropShadowBorder;
import sukien.view.SuKien_View;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.JFrame;
import login.Login_View;
import static login.Login_View.addmin_View;
import static login.Login_View.canBo_View;
import static login.Login_View.diemDanh_View;
import static login.Login_View.sinhVien_View;
import static login.Login_View.suKien_View;
import static login.Login_View.thongKe_View;
import org.jdesktop.swingx.JXCollapsiblePane;
import other.custom.CustComp;
import other.custom.TransitionPane;
import resources.ImageFile;
import static resources.Resources.back_Icon;
import static resources.Resources.canbo_Icon;
import static resources.Resources.dd_Large_Icon;
import static resources.Resources.diemdanh_Icon;
import static resources.Resources.exit_Icon;
import static resources.Resources.home_Icon;
import static resources.Resources.logo_Icon;
import static resources.Resources.logout_Icon;
import static resources.Resources.menu_Icon;
import static resources.Resources.sinhvien_Icon;
import static resources.Resources.sukien_Icon;
import static resources.Resources.taikhoan_Icon;
import static resources.Resources.themCB_Large_Icon;
import static resources.Resources.themSK_Large_Icon;
import static resources.Resources.themSV_Large_Icon;
import static resources.Resources.thongke_Icon;
import static resources.Resources.thongtin_Icon;
import static resources.Resources.toggle_Icon;
import sinhvien.view.SinhVien_Registor;
import sinhvien.view.SinhVien_View;
import thongke.ThongKe_View;

/**
 *
 * @author chuna
 */
public class App_View extends javax.swing.JFrame implements ActionListener {

    private static App_Controller controller = new App_Controller();

    public App_View() {
        initComponents();

    }

    public App_View(boolean visible) {
        initComponents();
        createUI();
        loadState();
        setVisible(visible);
    }

    private void createUI() {
        Insets insets = new Insets(10, 8, 10, 8);
        Dimension dimension = new Dimension(252, 50);
        Dimension dimension_2 = new Dimension(200, 50);

        pnl_Nav.setBackground(BG_NAV);
        btn_DiemDanh_Short = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "ĐIỂM DANH", dd_Large_Icon);
        btn_ThemCB_Short = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "ĐĂNG KÝ THẺ CÁN BỘ", themCB_Large_Icon);
        btn_ThemSV_Short = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "ĐĂNG KÝ THẺ SINH VIÊN", themSV_Large_Icon);
        btn_ThemSK_Short = new GradientButton(BG_BUTTON_HOVER, BG_NAV, FG_BUTTON, FG_BUTTON, "THÊM SỰ KIỆN", themSK_Large_Icon);
        btn_DiemDanh_Short.addActionListener(this);
        btn_ThemCB_Short.addActionListener(this);
        btn_ThemSV_Short.addActionListener(this);
        btn_ThemSK_Short.addActionListener(this);
        pnl_Related.add(btn_DiemDanh_Short);
        pnl_Related.add(btn_ThemSK_Short);
        pnl_Related.add(btn_ThemCB_Short);
        pnl_Related.add(btn_ThemSV_Short);

        Arrays.asList(btn_DiemDanh_Short, btn_ThemSK_Short, btn_ThemCB_Short, btn_ThemSV_Short).forEach((btn) -> {
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setFont(new Font("Arial", 0, 18));
        });

        btn_Toggle = new HoverButton(null, new Dimension(50, 52), insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, toggle_Icon,
                new ImageFile().getIconReverse(toggle_Icon));
        btn_Toggle.addActionListener((e) -> {
            toggleMenu();
            flag++;
        });
        btn_Home = new HoverButton(DataString.HOME, dimension, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, home_Icon,
                new ImageFile().getIconReverse(home_Icon));
        btn_Home.addActionListener((e) -> {
            TransitionPane.replacePane(pnl_Content, jPanel2);
            setClick(btn_Home);
        });
        btn_QLCanBo = new HoverButton(DataString.CADRE, dimension, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, canbo_Icon,
                new ImageFile().getIconReverse(canbo_Icon));
        btn_QLCanBo.addActionListener((e) -> {
            TransitionPane.replacePane(pnl_Content, Login_View.canBo_View);
            setClick(btn_QLCanBo);
        });
        btn_QLSinhVien = new HoverButton(DataString.STUDENT, dimension, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, sinhvien_Icon,
                new ImageFile().getIconReverse(sinhvien_Icon));
        btn_QLSinhVien.addActionListener((e) -> {
            TransitionPane.replacePane(pnl_Content, Login_View.sinhVien_View);
            setClick(btn_QLSinhVien);

        });
        btn_QLSuKien = new HoverButton(DataString.EVENT, dimension, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, sukien_Icon,
                new ImageFile().getIconReverse(sukien_Icon));
        btn_QLSuKien.addActionListener((e) -> {
            TransitionPane.replacePane(pnl_Content, Login_View.suKien_View);
            setClick(btn_QLSuKien);
        });
        btn_DiemDanh = new HoverButton(DataString.CHECK, dimension, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, diemdanh_Icon,
                new ImageFile().getIconReverse(diemdanh_Icon));
        btn_DiemDanh.addActionListener((e) -> {
            if (!DiemDanh_View.getChonSK().isSelected()) {
                diemDanh_View.show_ChonSK();
            } else {
                TransitionPane.replacePane(diemDanh_View.getContent(), diemDanh_View.getXemChiTiet());
                TransitionPane.replacePane(App_View.getContainer(), diemDanh_View);
            }
            setClick(btn_DiemDanh);
        });

        btn_ThongKe = new HoverButton(DataString.REPORT, dimension, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, thongke_Icon,
                new ImageFile().getIconReverse(thongke_Icon));
        btn_ThongKe.addActionListener((e) -> {
            TransitionPane.replacePane(pnl_Content, Login_View.thongKe_View);
            Login_View.thongKe_View.showPanel();
            setClick(btn_ThongKe);
        });

        btn_QLTaiKhoan = new HoverButton(DataString.ACCOUNT, dimension_2, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, taikhoan_Icon,
                new ImageFile().getIconReverse(taikhoan_Icon));
        btn_QLTaiKhoan.addActionListener((ActionEvent e) -> {
            TransitionPane.showDialogBox(pnl_Content, Login_View.addmin_View,
                    btn_QLTaiKhoan.getText(), new ImageFile().getIconReverse(taikhoan_Icon)
            );
            Login_View.addmin_View.showPanel();
        });

        btn_Dangxuat = new HoverButton(DataString.LOGOUT, new Dimension(180, 50), insets,
                FG_BUTTON_HOVER, FG_BUTTON, BG_BUTTON, BG_BUTTON_HOVER, logout_Icon,
                new ImageFile().getIconReverse(logout_Icon));
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
                java.util.logging.Logger.getLogger(Login_View.class
                        .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            java.awt.EventQueue.invokeLater(() -> {
                new Login_View().setVisible(true);
            });
            dispose();
        });
        btn_Thoat = new HoverButton(DataString.EXIT, new Dimension(180, 50), insets,
                FG_BUTTON_HOVER, FG_BUTTON, BG_BUTTON, BG_BUTTON_HOVER, exit_Icon,
                new ImageFile().getIconReverse(exit_Icon));
        btn_Thoat.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        //Create the popup menu.
        final JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new GridLayout(2, 1, 0, 0));
        popup.setBorder(new DropShadowBorder(Color.decode("#000000"), 2, (float) 0.6, 2, false, false, true, true));
        popup.setBackground(Color.white);
        popup.setOpaque(true);
        popup.add(btn_Dangxuat);
        popup.add(btn_Thoat);

        btn_Menu_2 = new HoverButton(null, new Dimension(50, 50), insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, menu_Icon,
                new ImageFile().getIconReverse(menu_Icon));
        btn_Menu_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                popup.show(e.getComponent(), e.getX() / 2, e.getY() + 20);
            }
        });

        btn_About = new HoverButton(DataString.ABOUT, dimension, insets, FG_BUTTON,
                FG_BUTTON_HOVER, BG_BUTTON, BG_BUTTON_HOVER, thongtin_Icon,
                new ImageFile().getIconReverse(thongtin_Icon));
        btn_About.addActionListener((e) -> {
            TransitionPane.showDialogBox(
                    this, Login_View.thongTin_View,
                    btn_About.getText(), thongtin_Icon
            );
        });

        lbl_Title = new JLabel("Phần mềm Điểm Danh");
        lbl_Title.setFont(new Font("Arial", 0, 16));
        lbl_Title.setBorder(new EmptyBorder(10, 2, 10, 8));
        lbl_Title.setForeground(FG_BUTTON);
        lbl_Title.setSize(new Dimension(200, 50));
        pnl_NavTop.add(btn_Toggle);
        pnl_NavTop.add(lbl_Title);
        pnl_NavTop.add(btn_Home);
        pnl_NavTop.add(btn_QLCanBo);
        pnl_NavTop.add(btn_QLSinhVien);
        pnl_NavTop.add(btn_QLSuKien);
        pnl_NavTop.add(btn_DiemDanh);
        pnl_NavTop.add(btn_ThongKe);

        pnl_NavBottom.add(btn_QLTaiKhoan);
        pnl_NavBottom.add(btn_Menu_2);
//        pnl_NavBottom.add(btn_ThietLap);
        pnl_NavBottom.add(btn_About);

        setTitle("Phần mềm ĐIỂM DANH");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 600));
        setSize(new Dimension(1200, 720));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        if (width <= 1366) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        setIconImage(logo_Icon.getImage());
        toggleMenu();
        collapse.setLayout(new BorderLayout());
        collapse.add(panel2, BorderLayout.CENTER);
        btn_ThemCB_Short.addActionListener(collapse.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
        btn_ThemSV_Short.addActionListener(collapse.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
        jButton1.addActionListener(collapse.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
        jButton1.setIcon(back_Icon);
    }

    public static JPanel getContainer() {
        return pnl_Content;
    }

    public static JPanel getNavigation() {
        return pnl_Nav;
    }

    public static App_Controller getController() {
        return controller;
    }

    public static void setController(App_Controller controller) {
        App_View.controller = controller;
    }

    private void setClick(HoverButton btn) {
        btn.setIsActive(true);
        btn.requestFocus();
        Component[] components = pnl_NavTop.getComponents();
        for (Component com : components) {
            if (com instanceof HoverButton) {
                if (com != btn) {
                    ((HoverButton) com).setIsActive(false);
                    ((HoverButton) com).reset();
                }
            }
        }
    }

    private void toggleMenu() {
        if (pnl_Nav.getSize().width > 50) {
            collapseMenu(true);
        } else {
            collapseMenu(false);
        }
    }

    private void collapseMenu(boolean b) {
        if (b) {
            CustComp.setAbsoluteSize(pnl_Nav, 50, HEIGHT_FRAME);
            CustComp.setSameToolTipText(this);
        } else {
            repaint();
            CustComp.setAbsoluteSize(pnl_Nav, 250, HEIGHT_FRAME);
            CustComp.setSameToolTipText(this, "");
        }
        width_PnlNav = pnl_Nav.getWidth();
        TransitionPane.repaint(pnl_Content);
        TransitionPane.repaint(pnl_Nav);
    }

    private void loadState() {
        try {
            App_Model m_home = controller.getState();
            id = m_home.getId();
            width_PnlNav = m_home.getLeftMenu();
            WIDTH_FRAME = m_home.getWidth();
            HEIGHT_FRAME = m_home.getHeight();
            System.out.println(WIDTH_FRAME);
            if (WIDTH_FRAME < 1900) {
                CustComp.setAbsoluteSize(this, WIDTH_FRAME, HEIGHT_FRAME);
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        } catch (NullPointerException e) {
            id = 1;
            width_PnlNav = 250;
            WIDTH_FRAME = 1200;
            HEIGHT_FRAME = 720;
        }

        switch (id) {
            case 1:
                TransitionPane.replacePane(pnl_Content, jPanel2);
                setClick(btn_Home);
                break;
            case 2:
                TransitionPane.replacePane(pnl_Content, Login_View.canBo_View = new CanBo_View());
                setClick(btn_QLCanBo);
                break;
            case 3:
                TransitionPane.replacePane(pnl_Content, Login_View.sinhVien_View = new SinhVien_View());
                setClick(btn_QLSinhVien);
                break;
            case 4:
                TransitionPane.replacePane(pnl_Content, Login_View.suKien_View = new SuKien_View());
                setClick(btn_QLSuKien);
                break;
            case 5:
                TransitionPane.replacePane(pnl_Content, Login_View.thongKe_View = new ThongKe_View());
                setClick(btn_ThongKe);
                break;
            default:
                break;
        }
    }

    private void saveState() {
        WIDTH_FRAME = getWidth();
        HEIGHT_FRAME = getHeight();
        Component com = pnl_Content.getComponents()[0];
        if (com instanceof JPanel) {
            JPanel pane = (JPanel) com;
            if (pane == pnl_Home) {
                controller.updateState(new App_Model(1, DataString.HOME, width_PnlNav, WIDTH_FRAME, HEIGHT_FRAME));
            } else if (pane == canBo_View) {
                controller.updateState(new App_Model(2, DataString.CADRE, width_PnlNav, WIDTH_FRAME, HEIGHT_FRAME));
            } else if (pane == sinhVien_View) {
                controller.updateState(new App_Model(3, DataString.STUDENT, width_PnlNav, WIDTH_FRAME, HEIGHT_FRAME));
            } else if (pane == suKien_View) {
                controller.updateState(new App_Model(4, DataString.EVENT, width_PnlNav, WIDTH_FRAME, HEIGHT_FRAME));
            } else if (pane == diemDanh_View) {
                controller.updateState(new App_Model(1, DataString.HOME, width_PnlNav, WIDTH_FRAME, HEIGHT_FRAME));
            } else if (pane == thongKe_View) {
                controller.updateState(new App_Model(5, DataString.REPORT, width_PnlNav, WIDTH_FRAME, HEIGHT_FRAME));
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        containerPane = new javax.swing.JPanel();
        pnl_Nav = new javax.swing.JPanel();
        pnl_NavTop = new javax.swing.JPanel();
        pnl_NavBottom = new javax.swing.JPanel();
        pnl_Content = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnl_Home = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnl_Related = new javax.swing.JPanel();
        collapse = new org.jdesktop.swingx.JXCollapsiblePane();

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblName.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lblName.setText("Đăng ký thẻ cán bộ");
        lblName.setPreferredSize(new java.awt.Dimension(170, 50));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(850, 600));
        setState(2);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        containerPane.setMinimumSize(new java.awt.Dimension(750, 600));
        containerPane.setPreferredSize(new java.awt.Dimension(1366, 768));

        pnl_Nav.setBackground(new java.awt.Color(32, 47, 90));
        pnl_Nav.setMaximumSize(new java.awt.Dimension(250, 32767));
        pnl_Nav.setMinimumSize(new java.awt.Dimension(50, 758));

        pnl_NavTop.setMinimumSize(new java.awt.Dimension(252, 362));
        pnl_NavTop.setOpaque(false);
        pnl_NavTop.setPreferredSize(new java.awt.Dimension(252, 362));
        pnl_NavTop.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 1));

        pnl_NavBottom.setOpaque(false);
        pnl_NavBottom.setPreferredSize(new java.awt.Dimension(0, 104));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 1);
        flowLayout1.setAlignOnBaseline(true);
        pnl_NavBottom.setLayout(flowLayout1);

        javax.swing.GroupLayout pnl_NavLayout = new javax.swing.GroupLayout(pnl_Nav);
        pnl_Nav.setLayout(pnl_NavLayout);
        pnl_NavLayout.setHorizontalGroup(
            pnl_NavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_NavLayout.createSequentialGroup()
                .addGroup(pnl_NavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_NavTop, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_NavBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_NavLayout.setVerticalGroup(
            pnl_NavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_NavLayout.createSequentialGroup()
                .addComponent(pnl_NavTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_NavBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnl_Content.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Content.setMinimumSize(new java.awt.Dimension(750, 550));
        pnl_Content.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pnl_ContentComponentResized(evt);
            }
        });
        pnl_Content.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jPanel2ComponentRemoved(evt);
            }
        });
        jPanel2.setLayout(new java.awt.BorderLayout());

        pnl_Home.setBackground(new java.awt.Color(250, 250, 250));
        pnl_Home.setPreferredSize(new java.awt.Dimension(600, 600));

        jPanel6.setPreferredSize(new java.awt.Dimension(1072, 300));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(32, 47, 90));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Phần mềm Điểm Danh");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(32, 47, 90));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Thiết kế bởi N8 Plus Team");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        jLabel3.setPreferredSize(new java.awt.Dimension(204, 42));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnl_Related.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 75, 50, 75));
        pnl_Related.setPreferredSize(new java.awt.Dimension(850, 500));
        pnl_Related.setLayout(new java.awt.GridLayout(2, 2, 50, 50));

        javax.swing.GroupLayout pnl_HomeLayout = new javax.swing.GroupLayout(pnl_Home);
        pnl_Home.setLayout(pnl_HomeLayout);
        pnl_HomeLayout.setHorizontalGroup(
            pnl_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addGroup(pnl_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnl_Related, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
        );
        pnl_HomeLayout.setVerticalGroup(
            pnl_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_HomeLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 495, Short.MAX_VALUE))
            .addGroup(pnl_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_HomeLayout.createSequentialGroup()
                    .addGap(104, 104, 104)
                    .addComponent(pnl_Related, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)))
        );

        jPanel2.add(pnl_Home, java.awt.BorderLayout.CENTER);

        collapse.setCollapsed(true);
        jPanel2.add(collapse, java.awt.BorderLayout.PAGE_START);

        pnl_Content.add(jPanel2);

        javax.swing.GroupLayout containerPaneLayout = new javax.swing.GroupLayout(containerPane);
        containerPane.setLayout(containerPaneLayout);
        containerPaneLayout.setHorizontalGroup(
            containerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerPaneLayout.createSequentialGroup()
                .addComponent(pnl_Nav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        containerPaneLayout.setVerticalGroup(
            containerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Content, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(pnl_Nav, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1147, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(containerPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(containerPane, javax.swing.GroupLayout.PREFERRED_SIZE, 564, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
    }//GEN-LAST:event_formMouseEntered

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveState();
        addmin_View.getAdmin_Controller().updateTimeLogout();
        Login_View.diemDanh_View.saveOnExit();
        DatabaseHandler.getInstance().closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (getWidth() < 1200) {
            collapseMenu(true);
        } else {
            collapseMenu(!(flag % 2 == 0));
        }
        if (collapse.isCollapsed()) {
            panel2.setPreferredSize(jPanel2.getSize());
        } else {
            int width = getContentPane().getWidth();
            int height = getContentPane().getHeight();
            panel2.setPreferredSize(new Dimension(width, height));
        }

    }//GEN-LAST:event_formComponentResized

    private void pnl_ContentComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnl_ContentComponentResized
        if (collapse.isCollapsed()) {
            panel2.setSize(jPanel2.getSize());
        } else {
            int width = getContentPane().getWidth();
            int height = getContentPane().getHeight();
            panel2.setPreferredSize(new Dimension(width, height));
        }
    }//GEN-LAST:event_pnl_ContentComponentResized

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel2ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel2ComponentRemoved

    }//GEN-LAST:event_jPanel2ComponentRemoved

    private int WIDTH_FRAME, HEIGHT_FRAME;
    private int width_PnlNav = 250;
    int id, flag = 0;

    private GradientButton btn_DiemDanh_Short;
    private GradientButton btn_ThemSK_Short;
    private GradientButton btn_ThemCB_Short;
    private GradientButton btn_ThemSV_Short;

    private JLabel lbl_Title;
    private HoverButton btn_QLTaiKhoan;
    private HoverButton btn_Menu_2;
    private HoverButton btn_Thoat;
    private HoverButton btn_Dangxuat;
    private HoverButton btn_DiemDanh;
    private HoverButton btn_Home;
    private HoverButton btn_About;
    private HoverButton btn_QLCanBo;
    private HoverButton btn_QLSinhVien;
    private HoverButton btn_QLSuKien;
    private HoverButton btn_ThongKe;
    private HoverButton btn_Toggle;
//    private HoverButton btn_ThietLap;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXCollapsiblePane collapse;
    private javax.swing.JPanel containerPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblName;
    private javax.swing.JPanel panel2;
    private static javax.swing.JPanel pnl_Content;
    private javax.swing.JPanel pnl_Home;
    private static javax.swing.JPanel pnl_Nav;
    private javax.swing.JPanel pnl_NavBottom;
    private javax.swing.JPanel pnl_NavTop;
    private javax.swing.JPanel pnl_Related;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob.equals(btn_DiemDanh_Short)) {
            btn_DiemDanh.doClick();
        }
        if (ob.equals(btn_ThemCB_Short)) {
            TransitionPane.replacePane(jPanel4, new CanBo_Registor());
            lblName.setText("Đăng ký thẻ cán bộ");
        }
        if (ob.equals(btn_ThemSV_Short)) {
            TransitionPane.replacePane(jPanel4, new SinhVien_Registor());
            lblName.setText("Đăng ký thẻ sinh viên");
        }
        if (ob.equals(btn_ThemSK_Short)) {
        }
    }

    public HoverButton getBtn_QLTaiKhoan() {
        return btn_QLTaiKhoan;
    }

    public HoverButton getBtn_DiemDanh() {
        return btn_DiemDanh;
    }

    public HoverButton getBtn_QLCanBo() {
        return btn_QLCanBo;
    }

    public HoverButton getBtn_QLSinhVien() {
        return btn_QLSinhVien;
    }

    public HoverButton getBtn_QLSuKien() {
        return btn_QLSuKien;
    }

    public HoverButton getBtn_ThongKe() {
        return btn_ThongKe;
    }

}
