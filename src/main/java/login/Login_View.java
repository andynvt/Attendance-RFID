/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import about.About_View;
import administrator.log.Log_Model;
import administrator.log.Log_View;
import app.view.App_View;
import canbo.view.CanBo_View;
import diemdanh.view.DiemDanh_View;
import other.custom.GradientButton;
import other.custom.GradientPanel;
import sukien.view.SuKien_View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import other.custom.Alert;
import other.custom.CustComp;
import static resources.Resources.close_Icon;
import static resources.Resources.close_hover_Icon;
import static resources.Resources.load_Icon;
import static resources.Resources.logo;
import static resources.Resources.logo_Main;
import static resources.Resources.pass_Icon;
import static resources.Resources.user_Icon;

import sinhvien.view.SinhVien_View;
import thongke.ThongKe_View;

/**
 *
 * @author proxc
 */
public class Login_View extends javax.swing.JFrame implements ActionListener, KeyListener {

    public static App_View app_View;

    private static final Login_Controller login_Controller = new Login_Controller();
    private Login_Model login_Model;
    private int xy, xx;

    private Log_Model admin_Model;
    public static CanBo_View canBo_View;
    public static SinhVien_View sinhVien_View;
    public static SuKien_View suKien_View;
    public static DiemDanh_View diemDanh_View;
    public static ThongKe_View thongKe_View;
    public static Log_View addmin_View;
    public static About_View thongTin_View;

    public Login_View() {
        initComponents();
        createUI();
        getReadyAccount();
        getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
        setVisible(true);
    }

    private void initView() {
        app_View = new App_View();
        canBo_View = new CanBo_View();
        sinhVien_View = new SinhVien_View();
        suKien_View = new SuKien_View();
        thongKe_View = new ThongKe_View();
        addmin_View = new Log_View();
        thongTin_View = new About_View();
        diemDanh_View = new DiemDanh_View();
    }

    public static Login_Controller getLogin_Controller() {
        return login_Controller;
    }

    private void createUI() {
        Insets insets = new Insets(5, 5, 0, 10);
        lb_User.setIcon(user_Icon);
        lb_Pass.setIcon(pass_Icon);
        img_loader.setIcon(load_Icon);
        lbl_logo.setIcon(logo);
        setIconImage(logo_Main.getImage());
        btn_Login = new GradientButton(Color.decode("#457fca"), Color.decode("#5691c8"), 0, "ĐĂNG NHẬP");
        btn_Login.setPreferredSize(new Dimension(220, 48));
        btn_Login.setFont(new Font("Arial", 1, 16));
        btn_Login.addActionListener(this);
        jPanel2.add(btn_Login);
        pnl_Heading = new GradientPanel(Color.black, Color.black, 0);
        lbl_close.setIcon(close_Icon);
        txt_pwd.setEchoChar((char) 0);
        txt_username.setBorder(new EmptyBorder(insets));
        txt_pwd.setBorder(new EmptyBorder(insets));
        setLocationRelativeTo(this);
        setInput();
        txt_username.addKeyListener(this);
        txt_pwd.addKeyListener(this);
        ckBox_GhiNho.addKeyListener(this);
    }

    private void getReadyAccount() {
        try {
            Login_Model login_Model_ = login_Controller.getTaiKhoanGhiNho();
            txt_username.setText(login_Model_.getTaiKhoan());
            txt_pwd.setText(login_Model_.getMatKhau());
        } catch (NullPointerException e) {

        }
    }

    private void setInput() {
        ArrayList<JTextComponent> list = CustComp.getTextComponents(pnl_body);
        list.forEach((txt) -> {
            String temp = txt.getText();
            txt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (txt.getText().trim().isEmpty() || txt.getText().equals(temp)) {
                        txt.setText("");
                        txt.setForeground(Color.decode("#666666"));
                        if (txt.equals(txt_pwd)) {
                            txt_pwd.setEchoChar((char) 0);
                        }
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                        if (txt.equals(txt_pwd)) {
                            txt_pwd.setEchoChar('\u25cf');
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (txt.equals(txt_pwd)) {
                        txt_pwd.setEchoChar('\u25cf');
                    }
                }
            });
            txt.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                        txt.setCaretPosition(0);
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                    if (e.getSource() == txt_username) {
                        setColorLine(jSeparator1, Color.decode("#1E88E5"));
                    }
                    if (e.getSource() == txt_pwd) {
                        setColorLine(jSeparator2, Color.decode("#1E88E5"));
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (txt.getText().isEmpty()) {
                        txt.setText(temp);
                    }
                    txt.setForeground(Color.decode("#666666"));
                    if (e.getSource() == txt_username) {
                        setColorLine(jSeparator1, Color.decode("#999999"));
                    }
                    if (e.getSource() == txt_pwd) {
                        setColorLine(jSeparator2, Color.decode("#999999"));
                        if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                            txt_pwd.setEchoChar((char) 0);
                            txt_pwd.setText(temp);
                        }
                    }
                }

                private void setColorLine(JSeparator js, Color color) {
                    js.setForeground(color);
                    js.setBackground(color);
                }
            });
            txt.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                        txt.setCaretPosition(0);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                        txt.setCaretPosition(0);
                    }
                }

            });
        });
        txt_username.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkGhiNho();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkGhiNho();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkGhiNho();
            }

        });
    }

    private boolean checkValidate() {
        char[] toCharArray = txt_username.getText().toCharArray();
        int c = 0;
        for (int i = 0; i < toCharArray.length; i++) {
            if (!(toCharArray[i] == '\\')) {
                c++;
            }
        }
        return c == toCharArray.length;
    }

    private void checkGhiNho() {
        if (checkValidate()) {
            login_Model = new Login_Model(txt_username.getText().trim(), txt_pwd.getText());
            if (login_Model != null && login_Controller.isGhiNho(login_Model)) {
                txt_pwd.setEchoChar('\u25cf');
                ckBox_GhiNho.setSelected(true);
                txt_pwd.setText(login_Controller.getMatKhau(login_Model));
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        login = new javax.swing.JPanel();
        pnl_body = new javax.swing.JPanel();
        txt_username = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lb_Pass = new javax.swing.JLabel();
        lb_User = new javax.swing.JLabel();
        txt_pwd = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        ckBox_GhiNho = new javax.swing.JCheckBox();
        pnl_Heading = new javax.swing.JPanel();
        lbl_close = new javax.swing.JLabel();
        lbl_logo = new javax.swing.JLabel();
        loader = new javax.swing.JPanel();
        img_loader = new javax.swing.JLabel();
        lbl_loader = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder1.setCornerSize(1);
        dropShadowBorder1.setShadowColor(new java.awt.Color(249, 249, 249));
        dropShadowBorder1.setShadowSize(2);
        dropShadowBorder1.setShowLeftShadow(true);
        dropShadowBorder1.setShowTopShadow(true);
        pnl_bg.setBorder(dropShadowBorder1);
        pnl_bg.setPreferredSize(new java.awt.Dimension(400, 500));
        pnl_bg.setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.CardLayout());

        login.setPreferredSize(new java.awt.Dimension(400, 500));
        login.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                loginMouseDragged(evt);
            }
        });
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                loginMousePressed(evt);
            }
        });

        pnl_body.setBackground(new java.awt.Color(255, 255, 254));
        pnl_body.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 35, 30, 60));
        pnl_body.setPreferredSize(new java.awt.Dimension(400, 377));

        txt_username.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_username.setForeground(new java.awt.Color(102, 102, 102));
        txt_username.setText("Tên đăng nhập");
        txt_username.setBorder(null);
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_usernameFocusGained(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jSeparator2.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        lb_Pass.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lb_Pass.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lb_Pass.setPreferredSize(new java.awt.Dimension(32, 32));

        lb_User.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lb_User.setPreferredSize(null);

        txt_pwd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_pwd.setForeground(new java.awt.Color(102, 102, 102));
        txt_pwd.setText("Mật khẩu");
        txt_pwd.setBorder(null);
        txt_pwd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_pwdFocusGained(evt);
            }
        });

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 36));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        ckBox_GhiNho.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ckBox_GhiNho.setText("Ghi nhớ");
        ckBox_GhiNho.setOpaque(false);
        ckBox_GhiNho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckBox_GhiNhoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_bodyLayout = new javax.swing.GroupLayout(pnl_body);
        pnl_body.setLayout(pnl_bodyLayout);
        pnl_bodyLayout.setHorizontalGroup(
            pnl_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_bodyLayout.createSequentialGroup()
                .addGroup(pnl_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_User, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_Pass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addComponent(txt_pwd)
                    .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addGroup(pnl_bodyLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnl_bodyLayout.createSequentialGroup()
                        .addComponent(ckBox_GhiNho)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pnl_bodyLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lb_Pass, lb_User});

        pnl_bodyLayout.setVerticalGroup(
            pnl_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_bodyLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnl_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_bodyLayout.createSequentialGroup()
                        .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lb_User, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(pnl_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_bodyLayout.createSequentialGroup()
                        .addComponent(txt_pwd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lb_Pass, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ckBox_GhiNho)
                .addGap(76, 76, 76)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        pnl_Heading.setBackground(new java.awt.Color(153, 204, 255));
        pnl_Heading.setPreferredSize(new java.awt.Dimension(400, 113));
        pnl_Heading.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnl_HeadingMouseDragged(evt);
            }
        });
        pnl_Heading.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_HeadingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_HeadingMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnl_HeadingMousePressed(evt);
            }
        });

        lbl_close.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lbl_close.setForeground(new java.awt.Color(51, 51, 51));
        lbl_close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_closeMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_closeMousePressed(evt);
            }
        });

        lbl_logo.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        lbl_logo.setForeground(new java.awt.Color(51, 51, 51));
        lbl_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_logo.setPreferredSize(new java.awt.Dimension(96, 96));

        javax.swing.GroupLayout pnl_HeadingLayout = new javax.swing.GroupLayout(pnl_Heading);
        pnl_Heading.setLayout(pnl_HeadingLayout);
        pnl_HeadingLayout.setHorizontalGroup(
            pnl_HeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_HeadingLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127)
                .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_HeadingLayout.setVerticalGroup(
            pnl_HeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_HeadingLayout.createSequentialGroup()
                .addGroup(pnl_HeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_HeadingLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_body, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(pnl_Heading, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addComponent(pnl_Heading, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_body, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel1.add(login, "card2");

        loader.setPreferredSize(new java.awt.Dimension(400, 500));

        img_loader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_loader.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbl_loader.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_loader.setForeground(new java.awt.Color(153, 153, 153));
        lbl_loader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_loader.setText("Đang đăng nhập ...");
        lbl_loader.setDoubleBuffered(true);

        javax.swing.GroupLayout loaderLayout = new javax.swing.GroupLayout(loader);
        loader.setLayout(loaderLayout);
        loaderLayout.setHorizontalGroup(
            loaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_loader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
            .addComponent(img_loader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        loaderLayout.setVerticalGroup(
            loaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loaderLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(img_loader, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(lbl_loader)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        jPanel1.add(loader, "card3");

        pnl_bg.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_bg, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusGained
     }//GEN-LAST:event_txt_usernameFocusGained

    private void txt_pwdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_pwdFocusGained

    }//GEN-LAST:event_txt_pwdFocusGained

    private void lbl_closeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMousePressed
        System.exit(0);
    }//GEN-LAST:event_lbl_closeMousePressed
    private void loginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMouseDragged

    }//GEN-LAST:event_loginMouseDragged

    private void loginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMousePressed

    }//GEN-LAST:event_loginMousePressed

    private void pnl_HeadingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_HeadingMouseEntered
     }//GEN-LAST:event_pnl_HeadingMouseEntered

    private void pnl_HeadingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_HeadingMouseExited
     }//GEN-LAST:event_pnl_HeadingMouseExited

    private void lbl_closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMouseEntered
        lbl_close.setBackground(Color.red);
        lbl_close.setOpaque(true);
        lbl_close.setIcon(close_hover_Icon);
    }//GEN-LAST:event_lbl_closeMouseEntered

    private void lbl_closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMouseExited
        lbl_close.setOpaque(false);
        lbl_close.setIcon(close_Icon);
    }//GEN-LAST:event_lbl_closeMouseExited

    private void pnl_HeadingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_HeadingMousePressed
        xx = evt.getX();
        xy = evt.getY();    }//GEN-LAST:event_pnl_HeadingMousePressed

    private void pnl_HeadingMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_HeadingMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_pnl_HeadingMouseDragged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void ckBox_GhiNhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckBox_GhiNhoActionPerformed

    }//GEN-LAST:event_ckBox_GhiNhoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/pla html 
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
    }
    private GradientButton btn_Login;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ckBox_GhiNho;
    private javax.swing.JLabel img_loader;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_Pass;
    private javax.swing.JLabel lb_User;
    private javax.swing.JLabel lbl_close;
    private javax.swing.JLabel lbl_loader;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JPanel loader;
    private javax.swing.JPanel login;
    private javax.swing.JPanel pnl_Heading;
    private javax.swing.JPanel pnl_bg;
    private javax.swing.JPanel pnl_body;
    private javax.swing.JPasswordField txt_pwd;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob.equals(btn_Login)) {
            loginActionPerformed();
        }
    }

    private void loginActionPerformed() {
        login_Model = new Login_Model(txt_username.getText().trim(), txt_pwd.getText());
        if (checkValidate() && login_Controller.checkLogin(login_Model)) {
            if (ckBox_GhiNho.isSelected()) {
                login_Model.setGhiNho(true);
                login_Controller.setGhiNho(login_Model);
                login();
            } else {
                login_Model.setGhiNho(false);
                login_Controller.setGhiNho(login_Model);
                login();
            }
        } else {
            Alert.showWarning(this, "Bạn đã nhập sai mật khẩu hoặc tên tài khoản \n"
                    + "Xin mời bạn thử lại");
        }
    }

    private void login() {
        initView();
        admin_Model = new Log_Model(login_Model.getTaiKhoan());
        addmin_View.getAdmin_Controller().insertTimeLogin(admin_Model);
        loader.show();
        login.hide();
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                app_View = new App_View(true);
                dispose();
            }
        }, (long) (1000 * 0));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btn_Login.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
