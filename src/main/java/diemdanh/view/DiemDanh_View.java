/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.view;

import administrator.settings.Config;
import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import static administrator.settings.Config.fg_Color1;
import static administrator.settings.Config.fg_Color2;
import app.view.App_View;
import canbo.model.CanBo_Model;
import canbo.view.CanBo_List;
import dscanbo.model.DSCanBo_Model;
import dscanbo.view.DSCanBo_View;
import dssinhvien.model.DSSinhVien_Model;
import dssinhvien.view.DSSinhVien_View;
import other.custom.CountTimer;
import other.custom.DataString;
import other.custom.GradientButton;
import other.custom.Util;
import sukien.view.SuKien_ListSK;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import login.Login_View;
import other.custom.Alert;
import other.custom.TransitionPane;
import resources.Resources;
import static resources.Resources.autoIcon;
import static resources.Resources.change_Icon;
import static resources.Resources.chonSuKien_Icon;
import static resources.Resources.manual_Icon;
import static resources.Resources.start_Icon;
import static resources.Resources.stopIcon;
import static resources.Resources.timerIcon;
import sinhvien.model.SinhVien_Model;
import sinhvien.view.SinhVien_Details;
import sinhvien.view.SinhVien_List;
import thongke.ThongKe_View;

/**
 *
 * @author chuna
 */
public class DiemDanh_View extends javax.swing.JPanel implements ActionListener {

    private static CountTimer countTimer;
    private static ArrayList<DSCanBo_Model> listDel_CanBo = new ArrayList<>();
    private static ArrayList<DSSinhVien_Model> listDel_SinhVien = new ArrayList<>();
    private static DiemDanh_ChonSK chonSK;
    private DiemDanh_Details xemChiTiet;

    public DiemDanh_View() {
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        initComponents();
        xemChiTiet = new DiemDanh_Details();
        chonSK = new DiemDanh_ChonSK(xemChiTiet);
        createUI();
    }

    public void show_ChonSK() {
        chonSK = new DiemDanh_ChonSK(xemChiTiet);
        chonSK.showPanel();
        chonSK.setVisible(true);
    }

    public DiemDanh_Details getXemChiTiet() {
        return xemChiTiet;
    }

    public static DiemDanh_ChonSK getChonSK() {
        return chonSK;
    }

    private void createUI() {
        btn_DoiBang = new GradientButton(bg_Color1, bg_Color2, change_Icon);
        btn_DoiBang.setToolTipText("Xem danh sách khác");
        btn_DoiBang.addActionListener(this);
        btn_ThuCong = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Điểm danh thủ công", manual_Icon);
        btn_ThuCong.setToolTipText("Điểm danh thủ công");
        btn_ThuCong.addActionListener(this);
        btn_ThuCong.setEnabled(false);
        btn_HuyDLDiemDanh = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Huỷ dữ liệu điểm danh", Resources.huy_Icon);
        btn_HuyDLDiemDanh.setToolTipText("Huỷ dữ liệu điểm danh");
        btn_HuyDLDiemDanh.addActionListener(this);
        btn_DoiSK = new GradientButton(bg_Color1, bg_Color2, chonSuKien_Icon);
        btn_DoiSK.setToolTipText("Chọn sự kiện khác");
        btn_DoiSK.addActionListener(this);
        btn_DoiSK.setHorizontalAlignment(SwingUtilities.LEFT);
        btn_BatDauDiemDanh = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Điểm danh", start_Icon);
        btn_BatDauDiemDanh.setToolTipText("Bắt đầu Điểm danh vào");
        btn_BatDauDiemDanh.addActionListener(this);
        btn_BatDauDiemDanh.setHorizontalAlignment(SwingUtilities.LEFT);
        btn_DiemDanhTuDong = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Điểm danh tự động", autoIcon);
        btn_DiemDanhTuDong.setToolTipText("Điểm danh tự động");
        btn_DiemDanhTuDong.addActionListener(this);
        btn_DiemDanhTuDong.setHorizontalAlignment(SwingUtilities.LEFT);
        btn_DiemDanhTuDong.setEnabled(false);

        btn_XemKetQua = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Xem báo cáo", Resources.thongKe_Icon);
        btn_XemKetQua.setToolTipText("Xem báo cáo");
        btn_XemKetQua.addActionListener(this);

        pnl_Left.add(btn_DoiBang);
        pnl_Left.add(btn_DoiSK);

        pnl_Right.add(btn_XemKetQua);
        pnl_Right.add(btn_DiemDanhTuDong);
        pnl_Right.add(btn_ThuCong);
        pnl_Right.add(btn_BatDauDiemDanh);
        pnl_Right.setBackground(Config.color_Action);
        pnl_Time.setVisible(false);
        btn_DiemDanhTuDong.setVisible(false);
        btn_ThuCong.setVisible(false);
        btn_XemKetQua.setVisible(false);
        countTimer = new CountTimer(txt_Clock);
        txt_Clock.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (getCountTimer().isStop()) {
                    if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh vào")) {
                        SuKien_ListSK.getController().update_GioKTVao(mask, ktBd);
                        btn_DiemDanhTuDong.setEnabled(false);
                    }
                    if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh ra")) {
                        SuKien_ListSK.getController().update_GioKTRa(mask, ktBd);
                        SuKien_ListSK.getController().setDiemDanh(true, mask);
                        btn_DiemDanhTuDong.setEnabled(false);
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        spn_Time.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    btn_OK.doClick();
                }
            }
        });
        dialogDiemDanh.setIconImage(autoIcon.getImage());
        dialogHenGio.setTitle("Chọn khoảng thời gian điểm danh.");
        dialogHenGio.setIconImage(timerIcon.getImage());

    }

    public void setUpDiemDanhThuCong() {
        txt_Ma.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_Ma.getText().equals("Nhập vào để điểm danh") || txt_Ma.getText().isEmpty()) {
                    txt_Ma.setCaretPosition(0);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_Ma.getText().equals("Nhập vào để điểm danh") || txt_Ma.getText().isEmpty()) {
                    txt_Ma.setForeground(Color.decode("#e1e1e1"));
                } else {
                    txt_Ma.setForeground(Color.decode("#2e3233"));
                }
            }
        });
        txt_Ma.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (txt_Ma.getText().equals("Nhập vào để điểm danh") || txt_Ma.getText().isEmpty()) {
                    txt_Ma.setText("");
                } else {
                    txt_Ma.setForeground(Color.decode("#2e3233"));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        txt_Ma.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txt_Ma.getText().equals("Nhập vào để điểm danh") || txt_Ma.getText().isEmpty()) {
                    txt_Ma.setCaretPosition(0);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (txt_Ma.getText().equals("Nhập vào để điểm danh") || txt_Ma.getText().isEmpty()) {
                    txt_Ma.setForeground(Color.decode("#e1e1e1"));
                } else {
                    txt_Ma.setForeground(Color.decode("#2e3233"));
                }
            }
        });
    }

    public JPanel getContent() {
        return pnl_Content;
    }

    public void setPnl_Content(JPanel pnl_Content) {
        this.pnl_Content = pnl_Content;
    }

    public GradientButton getBtn_BatDauDiemDanh() {
        return btn_BatDauDiemDanh;
    }

    public void setBtn_BatDauDiemDanh(GradientButton btn_BatDauDiemDanh) {
        this.btn_BatDauDiemDanh = btn_BatDauDiemDanh;
    }

    public GradientButton getBtn_ThuCong() {
        return btn_ThuCong;
    }

    public void setBtn_ThuCong(GradientButton btn_ThuCong) {
        this.btn_ThuCong = btn_ThuCong;
    }

    public JPanel getPnl_Clock() {
        return pnl_Clock;
    }

    public void setPnl_Clock(JPanel pnl_Clock) {
        this.pnl_Clock = pnl_Clock;
    }

    public JPanel getPnl_Time() {
        return pnl_Time;
    }

    public JTextField getTxt_Clock() {
        return txt_Clock;
    }

    public void setTxt_Clock(JTextField txt_Clock) {
        this.txt_Clock = txt_Clock;
    }

    public static CountTimer getCountTimer() {
        return countTimer;
    }

    public static void setCountTimer(CountTimer countTimer) {
        DiemDanh_View.countTimer = countTimer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_HenGioDiemDanh = new javax.swing.JPanel();
        spn_Time = new javax.swing.JSpinner();
        btn_OK = new javax.swing.JButton();
        lbl_TieuDe = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_Huy = new javax.swing.JButton();
        pnl_DiemDanhThuCong = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btn_DiemDanhRa = new javax.swing.JButton();
        btn_DiemDanhVao = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_Ma = new javax.swing.JTextField();
        pnl_Action = new javax.swing.JPanel();
        pnl_Left = new javax.swing.JPanel();
        pnl_Right = new javax.swing.JPanel();
        pnl_Content = new javax.swing.JPanel();
        pnl_Button = new javax.swing.JPanel();
        lbl_Title = new javax.swing.JLabel();
        pnl_Time = new javax.swing.JPanel();
        pnl_Clock = new javax.swing.JPanel();
        lbl_Name = new javax.swing.JLabel();
        txt_Clock = new javax.swing.JTextField();

        pnl_HenGioDiemDanh.setBackground(new java.awt.Color(255, 255, 255));

        spn_Time.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        spn_Time.setModel(new javax.swing.SpinnerNumberModel(15, 1, 20, 1));
        spn_Time.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        spn_Time.setMinimumSize(new java.awt.Dimension(120, 32));
        spn_Time.setPreferredSize(new java.awt.Dimension(100, 32));

        btn_OK.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_OK.setText("Đồng ý");
        btn_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OKActionPerformed(evt);
            }
        });

        lbl_TieuDe.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbl_TieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TieuDe.setText("Thời gian điểm danh:");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Phút");

        btn_Huy.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_Huy.setText("Huỷ bỏ");
        btn_Huy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_HenGioDiemDanhLayout = new javax.swing.GroupLayout(pnl_HenGioDiemDanh);
        pnl_HenGioDiemDanh.setLayout(pnl_HenGioDiemDanhLayout);
        pnl_HenGioDiemDanhLayout.setHorizontalGroup(
            pnl_HenGioDiemDanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_HenGioDiemDanhLayout.createSequentialGroup()
                .addGroup(pnl_HenGioDiemDanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_HenGioDiemDanhLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(lbl_TieuDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spn_Time, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_HenGioDiemDanhLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(btn_OK)
                        .addGap(29, 29, 29)
                        .addComponent(btn_Huy)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnl_HenGioDiemDanhLayout.setVerticalGroup(
            pnl_HenGioDiemDanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_HenGioDiemDanhLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnl_HenGioDiemDanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_TieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spn_Time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(pnl_HenGioDiemDanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_OK, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Huy, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnl_HenGioDiemDanhLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_OK, spn_Time});

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));

        btn_DiemDanhRa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btn_DiemDanhRa.setText("Điểm danh ra");
        btn_DiemDanhRa.setContentAreaFilled(false);
        btn_DiemDanhRa.setOpaque(true);
        btn_DiemDanhRa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DiemDanhRaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_DiemDanhRa);

        btn_DiemDanhVao.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btn_DiemDanhVao.setText("Điểm danh vào");
        btn_DiemDanhVao.setContentAreaFilled(false);
        btn_DiemDanhVao.setOpaque(true);
        btn_DiemDanhVao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DiemDanhVaoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_DiemDanhVao);

        txt_Ma.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_Ma.setForeground(new java.awt.Color(102, 102, 102));
        txt_Ma.setText("Nhập vào MSSV/MSCB để điểm danh");
        txt_Ma.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txt_Ma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_MaFocusGained(evt);
            }
        });
        txt_Ma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_MaMouseClicked(evt);
            }
        });
        txt_Ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaActionPerformed(evt);
            }
        });
        txt_Ma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_MaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_DiemDanhThuCongLayout = new javax.swing.GroupLayout(pnl_DiemDanhThuCong);
        pnl_DiemDanhThuCong.setLayout(pnl_DiemDanhThuCongLayout);
        pnl_DiemDanhThuCongLayout.setHorizontalGroup(
            pnl_DiemDanhThuCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnl_DiemDanhThuCongLayout.setVerticalGroup(
            pnl_DiemDanhThuCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DiemDanhThuCongLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setBackground(new java.awt.Color(255, 255, 254));
        addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                formComponentAdded(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnl_Action.setMinimumSize(new java.awt.Dimension(700, 45));
        pnl_Action.setPreferredSize(new java.awt.Dimension(977, 45));

        pnl_Left.setOpaque(false);
        pnl_Left.setPreferredSize(new java.awt.Dimension(250, 100));
        pnl_Left.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 2));

        pnl_Right.setOpaque(false);
        pnl_Right.setPreferredSize(new java.awt.Dimension(712, 45));
        pnl_Right.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));

        javax.swing.GroupLayout pnl_ActionLayout = new javax.swing.GroupLayout(pnl_Action);
        pnl_Action.setLayout(pnl_ActionLayout);
        pnl_ActionLayout.setHorizontalGroup(
            pnl_ActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ActionLayout.createSequentialGroup()
                .addComponent(pnl_Left, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Right, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE))
        );
        pnl_ActionLayout.setVerticalGroup(
            pnl_ActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ActionLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnl_ActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_Left, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pnl_Right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pnl_Content.setBackground(new java.awt.Color(255, 255, 254));
        pnl_Content.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Button.setBackground(new java.awt.Color(249, 249, 249));
        pnl_Button.setMinimumSize(new java.awt.Dimension(64, 0));
        pnl_Button.setPreferredSize(new java.awt.Dimension(157, 50));
        pnl_Button.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Title.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(102, 102, 102));
        lbl_Title.setText("Điểm danh");
        lbl_Title.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 15, 20));
        lbl_Title.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        pnl_Button.add(lbl_Title);

        pnl_Time.setBackground(new java.awt.Color(249, 249, 249));
        pnl_Time.setLayout(new java.awt.GridLayout(1, 0));

        pnl_Clock.setBackground(new java.awt.Color(249, 249, 249));
        pnl_Clock.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnl_Clock.setOpaque(false);
        pnl_Clock.setPreferredSize(new java.awt.Dimension(400, 50));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0);
        flowLayout1.setAlignOnBaseline(true);
        pnl_Clock.setLayout(flowLayout1);

        lbl_Name.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lbl_Name.setForeground(new java.awt.Color(102, 102, 102));
        lbl_Name.setText("Thời gian còn lại:");
        lbl_Name.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 10));
        lbl_Name.setMinimumSize(new java.awt.Dimension(139, 50));
        lbl_Name.setPreferredSize(new java.awt.Dimension(200, 50));
        pnl_Clock.add(lbl_Name);

        txt_Clock.setEditable(false);
        txt_Clock.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txt_Clock.setForeground(new java.awt.Color(46, 50, 51));
        txt_Clock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_Clock.setText("99:99:99");
        txt_Clock.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_Clock.setFocusable(false);
        txt_Clock.setPreferredSize(new java.awt.Dimension(125, 50));
        pnl_Clock.add(txt_Clock);

        pnl_Time.add(pnl_Clock);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnl_Action, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_Time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(pnl_Content, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addGap(48, 48, 48))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 440, Short.MAX_VALUE)
                    .addComponent(pnl_Action, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pnl_Button, pnl_Time});

    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    }//GEN-LAST:event_formComponentResized

    private void formComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_formComponentAdded

    }//GEN-LAST:event_formComponentAdded
    private Time ktBd, ktRa;
    private String mask;
    private void btn_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OKActionPerformed
        boolean check = (int) spn_Time.getValue() <= Config.LIMIT;
        dialogHenGio.dispose();
        if (check) {
            btn_DiemDanhTuDong.setEnabled(true);
            btn_ThuCong.setEnabled(true);
            countTimer.setTime(new Time(0, (int) spn_Time.getValue(), 0));
            countTimer.start();
            if (btn_BatDauDiemDanh.getText().equals("Bắt đầu Điểm danh vào")) {
                ktBd = Util.plusTime(currentTime, new Time(0, (int) spn_Time.getValue(), 0));
                btn_BatDauDiemDanh.setText("Dừng điểm danh vào");
            } else if (btn_BatDauDiemDanh.getText().equals("Bắt đầu Điểm danh ra")) {
                ktRa = Util.plusTime(currentTime, new Time(0, (int) spn_Time.getValue(), 0));
                btn_BatDauDiemDanh.setText("Dừng điểm danh ra");
            }
            pnl_Time.setVisible(true);
            btn_BatDauDiemDanh.setIcon(stopIcon);
            btn_HuyDLDiemDanh.setVisible(true);
            btn_DiemDanhTuDong.setVisible(true);
            btn_ThuCong.setVisible(true);

        } else {
            Alert.showWarning(this, "Thời gian điểm danh tối đa là " + Config.LIMIT + " phút.");
            pnl_Time.setVisible(false);
        }

    }//GEN-LAST:event_btn_OKActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private void btn_DiemDanhVaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DiemDanhVaoActionPerformed
        String ma = txt_Ma.getText().trim();
        String masv, macb;
        if (!ma.isEmpty() && ma.substring(0, 1).equalsIgnoreCase("B")) {
            masv = ma;
            SinhVien_Model sinhVienDKSK = DSSinhVien_View.getController().getSinhVienByMaSV(masv, mask);
            if (sinhVienDKSK == null) {
                Alert.showMessageDialog(this, "Sinh viên chưa có trong danh sách tham dự hoặc mã sinh viên không hợp lệ!", "Thông báo");
            } else {
                if (!DSSinhVien_View.getController().isDiemDanhVaoByMaSV(masv, mask)) {
                    DSSinhVien_View.getController().update_DiemDanhVao(new Time(new java.util.Date().getTime()), masv, mask);
                    DSSinhVien_View.getController().setDiemDanhTuDong(false, masv);
                    Alert.showMessageDialog(this, "Đã điểm danh vào thành công!", "Thông báo");
                    SinhVien_Model sinhVien = SinhVien_List.getController().load_SinhVien(masv);
                    if (sinhVien == null) {
                        int select = Alert.showQuestionDialog(this, "Sinh viên chưa có trong hệ thống.\n"
                                + "Bạn có muốn thêm sinh viên vào hệ thống không?", "Thông báo");
                        if (select == Alert.OK) {
                            SinhVien_Details sinhVien_Details = new SinhVien_Details();
                            sinhVien_Details.setSinhVien_Model(sinhVien);
                            TransitionPane.showDialogBox(this, sinhVien_Details, "Thêm sinh viên mới", Resources.sinhVien_Icon);
                        }
                    }
                    xemChiTiet.getdSThamGia().showDSSinhVien();
                } else {
                    Alert.showMessageDialog(this, "Sinh viên đã được điểm danh vào rồi!", "Thông báo");
                }
            }
        } else {
            macb = ma;
            CanBo_Model canBo_Model = CanBo_List.getController().load_CanBo(macb);
            if (canBo_Model == null) {
                Alert.showMessageDialog(this, "Cán bộ chưa có trong hệ thống hoặc mã cán bộ không hợp lệ!", "Thông báo");
            } else {
                CanBo_Model canBoByMaCB = DSCanBo_View.getController().getCanBoByMaCB(macb, mask);
                if (canBoByMaCB == null) {
                    Alert.showMessageDialog(this, "Cán bộ chưa có trong danh sách tham dự hoặc mã cán bộ không hợp lệ!", "Thông báo");
                } else {
                    if (!DSCanBo_View.getController().isDiemDanhVaoByMaCB(macb, mask)) {
                        DSCanBo_View.getController().update_DiemDanhVao(new Time(new java.util.Date().getTime()), macb, mask);
                        DSCanBo_View.getController().setDiemDanhTuDong(false, macb, mask);
                        Alert.showMessageDialog(this, "Đã điểm danh vào thành công!", "Thông báo");
                        xemChiTiet.getdSThamGia().showDSCanBo();
                    } else {
                        Alert.showMessageDialog(this, "Cán bộ đã được điểm danh vào rồi!", "Thông báo");
                    }
                }
            }

        }
        diemDanhTuDong.setAlwaysOnTop(true);
        diemDanhTuDong.getTxtInputText().setFocusable(true);
        diemDanhTuDong.getTxtInputText().requestFocus();
        dialogDiemDanh.dispose();
    }//GEN-LAST:event_btn_DiemDanhVaoActionPerformed

    private void btn_DiemDanhRaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DiemDanhRaActionPerformed
        String ma = txt_Ma.getText().trim();
        String masv, macb;
        if (!ma.isEmpty() && ma.substring(0, 1).equalsIgnoreCase("B")) {
            masv = ma;
            SinhVien_Model canBo_Model = SinhVien_List.getController().load_SinhVien(masv);
            if (canBo_Model == null) {
                Alert.showMessageDialog(this, "Sinh viên chưa có trong hệ thống hoặc mã sinh viên không hợp lệ!", "Thông báo");
            } else {
                SinhVien_Model canBoByMaCB = DSSinhVien_View.getController().getSinhVienByMaSV(masv, mask);
                if (canBoByMaCB == null) {
                    Alert.showMessageDialog(this, "Sinh viên chưa có trong danh sách tham dự hoặc mã sinh viên không hợp lệ!", "Thông báo");
                } else {
                    if (!DSSinhVien_View.getController().isDiemDanhRaByMaSV(masv, mask)) {
                        DSSinhVien_View.getController().update_DiemDanhRa(new Time(new java.util.Date().getTime()), masv, mask);
                        Alert.showMessageDialog(this, "Đã điểm danh ra thành công!", "Thông báo");
                        xemChiTiet.getdSThamGia().showDSSinhVien();
                    } else {
                        Alert.showMessageDialog(this, "Sinh viên đã được điểm danh ra rồi!", "Thông báo");
                    }
                }
            }
        } else {
            macb = ma;
            CanBo_Model canBo_Model = CanBo_List.getController().load_CanBo(macb);
            if (canBo_Model == null) {
                Alert.showMessageDialog(this, "Cán bộ chưa có trong hệ thống hoặc mã cán bộ không hợp lệ!", "Thông báo");
            } else {
                CanBo_Model canBoByMaCB = DSCanBo_View.getController().getCanBoByMaCB(macb, mask);
                if (canBoByMaCB == null) {
                    Alert.showMessageDialog(this, "Cán bộ chưa có trong danh sách tham dự hoặc mã cán bộ không hợp lệ!", "Thông báo");
                } else {
                    if (!DSCanBo_View.getController().isDiemDanhRaByMaCb(macb, mask)) {
                        DSCanBo_View.getController().update_DiemDanhRa(new Time(new java.util.Date().getTime()), macb, mask);
                        Alert.showMessageDialog(this, "Đã điểm danh ra thành công!", "Thông báo");
                        xemChiTiet.getdSThamGia().showDSCanBo();
                    } else {
                        Alert.showMessageDialog(this, "Cán bộ đã được điểm danh ra rồi!", "Thông báo");
                    }
                }
            }

        }
        dialogDiemDanh.dispose();
        diemDanhTuDong.setAlwaysOnTop(true);
        diemDanhTuDong.getTxtInputText().setFocusable(true);
        diemDanhTuDong.getTxtInputText().requestFocus();
    }//GEN-LAST:event_btn_DiemDanhRaActionPerformed

    private void txt_MaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaActionPerformed

    private void txt_MaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MaFocusGained
        if (txt_Ma.getText().equals("Nhập vào MSSV/MSCB để điểm danh")) {
            txt_Ma.setCaretPosition(0);
        } else {
        }
    }//GEN-LAST:event_txt_MaFocusGained

    private void txt_MaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_MaMouseClicked
        if (txt_Ma.getText().equals("Nhập vào MSSV/MSCB để điểm danh")) {
            txt_Ma.setCaretPosition(0);
        } else {
        }
    }//GEN-LAST:event_txt_MaMouseClicked

    private void txt_MaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_MaKeyPressed
        if (txt_Ma.getText().equals("Nhập vào MSSV/MSCB để điểm danh")) {
            txt_Ma.setText("");
            txt_Ma.setForeground(Color.BLACK);
        } else {
            txt_Ma.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txt_MaKeyPressed

    private void btn_HuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HuyActionPerformed
        dialogHenGio.dispose();
    }//GEN-LAST:event_btn_HuyActionPerformed

    private GradientButton btn_DoiSK;
    private GradientButton btn_BatDauDiemDanh;
    private GradientButton btn_ThuCong;
    private GradientButton btn_HuyDLDiemDanh;
    private GradientButton btn_DoiBang;
    private GradientButton btn_DiemDanhTuDong;
    private GradientButton btn_XemKetQua;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_DiemDanhRa;
    private javax.swing.JButton btn_DiemDanhVao;
    private javax.swing.JButton btn_Huy;
    private javax.swing.JButton btn_OK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_Name;
    private javax.swing.JLabel lbl_TieuDe;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel pnl_Action;
    private javax.swing.JPanel pnl_Button;
    private javax.swing.JPanel pnl_Clock;
    private javax.swing.JPanel pnl_Content;
    private javax.swing.JPanel pnl_DiemDanhThuCong;
    private javax.swing.JPanel pnl_HenGioDiemDanh;
    private javax.swing.JPanel pnl_Left;
    private javax.swing.JPanel pnl_Right;
    private javax.swing.JPanel pnl_Time;
    private javax.swing.JSpinner spn_Time;
    private javax.swing.JTextField txt_Clock;
    private javax.swing.JTextField txt_Ma;
    // End of variables declaration//GEN-END:variables

    private JDialog dialogHenGio = new JDialog();
    private JDialog dialogDiemDanh = new JDialog();
    private static DiemDanh_TuDong diemDanhTuDong;
    private Date currentDate, ngayDienRa;
    private Time currentTime, gioVao, gioKTVao, gioRa, gioKTRa;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob.equals(btn_BatDauDiemDanh)) {
            mask = xemChiTiet.getSuKien_Model().getMaSK();
            currentDate = new Date(System.currentTimeMillis());
            currentTime = new Time(System.currentTimeMillis());
            ngayDienRa = SuKien_ListSK.getController().getNgayDienRa(mask);
            gioVao = SuKien_ListSK.getController().getGioVao(mask);
            gioRa = SuKien_ListSK.getController().getGioRa(mask);
            btn_DoiSK.setEnabled(false);
            int kq = Util.compare(currentDate, ngayDienRa);

            if (kq == 0) {
                if (btn_BatDauDiemDanh.getText().equals("Bắt đầu Điểm danh vào")) {
                    int rs1 = Util.compare(new Time(0, 20, 0), Util.between(gioVao, currentTime));
                    if (rs1 >= 0) {
                        spn_Time.setValue(15);
                        dialogHenGio.getContentPane().add(pnl_HenGioDiemDanh);
                        dialogHenGio.pack();
                        dialogHenGio.setLocationRelativeTo(this);
                        dialogHenGio.show();
                        btn_DoiSK.setEnabled(false);
                        btn_HuyDLDiemDanh.setVisible(false);
                        btn_DiemDanhTuDong.setVisible(true);
                        btn_ThuCong.setVisible(true);
                    } else if (rs1 < 0) {
                        Alert.showMessageDialog(App_View.getContainer(), "Sự kiện chỉ có thể điểm danh vào sớm nhất là trước 20 phút"
                                + "\nvà trễ nhất là 20 phút so với thời gian bắt đầu.", "Thông báo");
                    }

                }
                if (btn_BatDauDiemDanh.getText().equals("Bắt đầu Điểm danh ra")) {
                    int rs1 = Util.compare(new Time(0, 20, 0), Util.between(gioRa, currentTime));
                    if (rs1 >= 0) {
                        spn_Time.setValue(15);
                        dialogHenGio.getContentPane().add(pnl_HenGioDiemDanh);
                        dialogHenGio.pack();
                        dialogHenGio.setLocationRelativeTo(this);
                        dialogHenGio.show();
                        btn_DoiSK.setEnabled(false);
                        btn_HuyDLDiemDanh.setVisible(false);
                        btn_DiemDanhTuDong.setVisible(true);
                        btn_ThuCong.setVisible(true);
                    } else if (rs1 < 0) {
                        Alert.showMessageDialog(App_View.getContainer(), "Sự kiện chỉ có thể điểm danh ra sớm nhất là trước 20 phút"
                                + "\nvà trễ nhất là 20 phút so với thời gian ra.", "Thông báo");
                    }
                }
                if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh vào")) {
                    SuKien_ListSK.getController().update_GioKTVao(mask, ktBd);
                    btn_BatDauDiemDanh.setText("Bắt đầu Điểm danh ra");
                    countTimer.stop();
                    txt_Clock.setText("00:00:00");
                    pnl_Time.setVisible(false);
                    btn_HuyDLDiemDanh.setVisible(true);
                    btn_DiemDanhTuDong.setVisible(false);
                    btn_ThuCong.setVisible(false);
                    btn_DoiSK.setEnabled(true);
                }
                if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh ra")) {
                    SuKien_ListSK.getController().update_GioKTRa(mask, ktRa);
                    SuKien_ListSK.getController().setDiemDanh(true, mask);
                    btn_BatDauDiemDanh.setText("Đã Điểm Danh");
                    countTimer.stop();
                    txt_Clock.setText("00:00:00");
                    pnl_Time.setVisible(false);
                    btn_DiemDanhTuDong.setVisible(false);
                    btn_ThuCong.setVisible(false);
                    btn_BatDauDiemDanh.setEnabled(false);
                    btn_HuyDLDiemDanh.setVisible(true);
                    btn_DoiSK.setEnabled(true);
                    btn_XemKetQua.setVisible(true);
                }
            } else if (kq < 0) {
                Alert.showWarning(this, "Chưa đến ngày điểm danh");
            } else if (kq > 0) {
                Alert.showWarning(this, "Không thể điểm danh sự kiện đã diễn ra.");
            }
        }
        if (ob.equals(btn_DoiBang)) {
            xemChiTiet.changeTable();
        }
        if (ob.equals(btn_DoiSK)) {
            show_ChonSK();
            getChonSK().setSelected(false);
        }
        if (ob.equals(btn_ThuCong)) {
            diemDanhThuCong();
        }

        if (ob.equals(btn_DiemDanhTuDong)) {
            if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh vào")) {
                diemDanhTuDong = new DiemDanh_TuDong(DataString.DIEMDANHVAO);
                diemDanhTuDong.setMask(chonSK.getSuKien_List().getValue().getMaSK());
                diemDanhTuDong.setLocationRelativeTo(App_View.getContainer());
                diemDanhTuDong.setVisible(true);
                diemDanhTuDong.setTenSK(xemChiTiet.getTxt_Ten().getText());
            }
            if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh ra")) {
                diemDanhTuDong = new DiemDanh_TuDong(DataString.DIEMDANHRA);
                diemDanhTuDong.setMask(chonSK.getSuKien_List().getValue().getMaSK());
                diemDanhTuDong.setLocationRelativeTo(App_View.getContainer());
                diemDanhTuDong.setVisible(true);
                btn_HuyDLDiemDanh.setEnabled(true);
                diemDanhTuDong.setTenSK(xemChiTiet.getTxt_Ten().getText());
            }
        }
        if (ob.equals(btn_HuyDLDiemDanh)) {
            if (xemChiTiet.isScreen()) {
                xoa_CanBo();
            } else {
                xoa_SinhVien();
            }
            if (SuKien_ListSK.getController().isDiemDanhVao(mask) || SuKien_ListSK.getController().isDiemDanhVao(mask)) {
                SuKien_ListSK.getController().setDiemDanh(false, mask);
            }
        }
        if (ob.equals(btn_XemKetQua)) {
            TransitionPane.replacePane(App_View.getContainer(), Login_View.thongKe_View);
            Login_View.thongKe_View.xem_SuKien(xemChiTiet.getSuKien_Model());
        }

    }

    private void xoa_CanBo() {
        listDel_CanBo = DSCanBo_View.getListDel_DSCanBo();
        if (listDel_CanBo.size() > 0) {
            if (Alert.showQuestionDialog(App_View.getContainer(), "Lưu lại thay đổi?", "Xác nhận lưu") == Alert.OK) {
                DSCanBo_View.getController().huy_TatCaDLDiemDanh(mask);
                Alert.showMessageDialog(this, "Đã cập nhật thay đổi!", "Thông báo");
            }
            listDel_CanBo.clear();
        } else {
            DSCanBo_Model cb_del = xemChiTiet.getdSThamGia().getdSCanBo_View().getDSCanBo();
            if (Alert.showQuestionDialog(App_View.getContainer(), "Lưu lại thay đổi?", "Xác nhận lưu") == Alert.OK) {
                DSCanBo_View.getController().huy_DuLieuDiemDanh(cb_del.getMaCB(), mask);
                Alert.showMessageDialog(this, "Đã cập nhật thay đổi!", "Thông báo");
            }
        }
    }

    private void xoa_SinhVien() {
        listDel_SinhVien = DSSinhVien_View.getListDel_DSSV();
        if (listDel_SinhVien.size() > 0) {
            if (Alert.showQuestionDialog(App_View.getContainer(), "Lưu lại thay đổi?", "Xác nhận lưu") == Alert.OK) {
                DSCanBo_View.getController().huy_TatCaDLDiemDanh(mask);
                Alert.showMessageDialog(this, "Đã cập nhật thay đổi!", "Thông báo");
            }
            listDel_SinhVien.clear();
        } else {
            DSSinhVien_Model cb_del = xemChiTiet.getdSThamGia().getdSSinhVien_View().getDSSinhVien();
            if (Alert.showQuestionDialog(App_View.getContainer(), "Lưu lại thay đổi?", "Xác nhận lưu") == Alert.OK) {
                DSSinhVien_View.getController().huy_DuLieuDiemDanh(cb_del.getMaSV(), mask);
                Alert.showMessageDialog(this, "Đã cập nhật thay đổi!", "Thông báo");
            }
        }
    }

    public void diemDanhThuCong() {
        txt_Ma.requestFocus();
        btn_DiemDanhVao.setVisible(btn_BatDauDiemDanh.getText().equals("Dừng điểm danh vào"));
        btn_DiemDanhRa.setVisible(btn_BatDauDiemDanh.getText().equals("Dừng điểm danh ra"));
        dialogDiemDanh.setTitle("Điểm danh thủ công");
        dialogDiemDanh.setIconImage(Resources.dd_Large_Icon.getImage());
        dialogDiemDanh.getContentPane().add(pnl_DiemDanhThuCong);
        dialogDiemDanh.pack();
        dialogDiemDanh.setLocationRelativeTo(this);
        txt_Ma.setText("Nhập vào MSSV/MSCB để điểm danh");
        diemDanhTuDong.setAlwaysOnTop(false);
        dialogDiemDanh.show();
    }

    public void saveOnExit() {
        if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh vào")) {
            SuKien_ListSK.getController().update_GioKTVao(mask, ktBd);
        }
        if (btn_BatDauDiemDanh.getText().equals("Dừng điểm danh ra")) {
            SuKien_ListSK.getController().update_GioKTRa(mask, ktBd);
            SuKien_ListSK.getController().setDiemDanh(true, mask);
        }
    }
}
