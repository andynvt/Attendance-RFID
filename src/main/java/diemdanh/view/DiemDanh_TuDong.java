/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.view;

import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import static administrator.settings.Config.fg_Color1;
import static administrator.settings.Config.fg_Color2;
import app.view.App_View;
import canbo.model.CanBo_Model;
import canbo.view.CanBo_Detail;
import canbo.view.CanBo_List;
import other.custom.DataString;
import dscanbo.view.DSCanBo_View;
import dssinhvien.view.DSSinhVien_View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import login.Login_View;
import other.custom.Alert;
import other.custom.Check;
import other.custom.GradientButton;
import other.custom.TransitionPane;
import resources.Resources;
import static resources.Resources.dd_Large_Icon;
import sinhvien.model.SinhVien_Model;
import sinhvien.view.SinhVien_Details;
import sinhvien.view.SinhVien_List;
import text2speech.Text2Speech;

/**
 *
 * @author chuna
 */
public class DiemDanh_TuDong extends javax.swing.JDialog {

    private int countFailed = 0;
    private int click = 0;
    private boolean diemDanh = false;
    private String maRFID;
    private Text2Speech text2Speech;
    private String mask;
    private int loaiDiemDanh;
    private JButton btn_AmThanh;

    public DiemDanh_TuDong() {
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
        text2Speech = new Text2Speech();
    }

    public DiemDanh_TuDong(int loaiDiemDanh) {
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
        this.loaiDiemDanh = loaiDiemDanh;
        initComponents();
        createUI();
        text2Speech = new Text2Speech();
    }

    public static void main(String[] args) {
        new DiemDanh_TuDong().setVisible(true);
    }

    private void createUI() {
        setAlwaysOnTop(true);
        setTitle("Điểm danh tự động");
        setIconImage(dd_Large_Icon.getImage());
        pnl_Content.setForeground(Color.decode("#f1fafe"));
        txtInputText.requestFocus();
        txtInputText.setSelectedTextColor(Color.WHITE);
        txtInputText.setSelectionColor(Color.WHITE);
        txtInputText.setCaretColor(Color.white);
        txtInputText.setSelectionStart(0);
        txtInputText.setSelectionEnd(txtInputText.getText().length());
        txtInputText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                diemDanhAction();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        txtInputText.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setInputMaThe();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setInputMaThe();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setInputMaThe();
            }

            private void setInputMaThe() {
                txtInputText.setSelectionStart(0);
                txtInputText.setSelectionEnd(txtInputText.getText().length());
            }
        });

        txtInputText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txt_Alert.setText("Đang điểm danh ...");
            }

            @Override
            public void focusLost(FocusEvent e) {
                txt_Alert.setText("Tạm dừng điểm danh. Click vào đây để tiếp tục");
            }

        });
        txtInputText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!Check.isRFIDValid(txtInputText.getText())) {
                    txtInputText.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!Check.isRFIDValid(txtInputText.getText())) {
                    txtInputText.setText("");
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (!Check.isRFIDValid(txtInputText.getText())) {
                    txtInputText.setText("");

                }
            }

        });

        btn_AmThanh = new JButton(Resources.sound_Icon);
        btn_AmThanh.setRequestFocusEnabled(false);
        btn_AmThanh.setOpaque(false);
        btn_AmThanh.setBorder(null);
        btn_AmThanh.setContentAreaFilled(false);
        btn_AmThanh.setBorderPainted(false);
        btn_AmThanh.setFocusPainted(false);
        btn_AmThanh.addActionListener((e) -> {
            click++;
            if (click % 2 == 0) {
                btn_AmThanh.setIcon(Resources.sound_Icon);
            } else {
                btn_AmThanh.setIcon(Resources.mute_Icon);
            }
            txtInputText.requestFocus();
        });

        pnl_Header.add(btn_AmThanh, BorderLayout.EAST);
        JTextField label = new JTextField();
        try {
            label = Login_View.diemDanh_View.getTxt_Clock();
            label.setFont(new Font("Consolas", 0, 45));
            pnl_Clock.add(label);
        } catch (NullPointerException e) {
        }
        try {
            Time time = Time.valueOf(label.getText());
            int h = Math.abs(time.getHours()), m = Math.abs(time.getMinutes()), s = Math.abs(time.getSeconds());
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Alert.showSuccess(DiemDanh_TuDong.this, "Điểm danh sự kiện đã hoàn thành.");
                    btn_Hide.doClick(500);
                    Login_View.app_View.show();
                }
            }, (h * 3600 + m * 60 + s) * 1000);
        } catch (IllegalArgumentException e) {
        }

        pack();
    }

    public int getLoaiDiemDanh() {
        return loaiDiemDanh;
    }

    public void setLoaiDiemDanh(int loaiDiemDanh) {
        this.loaiDiemDanh = loaiDiemDanh;
    }

    public void diemDanhAction() {
        boolean sound = click % 2 == 0;
        if (sound) {
            if (loaiDiemDanh == DataString.DIEMDANHVAO) {
                diemDanhVao();
            } else {
                diemDanhRa();
            }
        } else {
            if (loaiDiemDanh == DataString.DIEMDANHVAO) {
                diemDanhVaoNoSpeech();
            } else {
                diemDanhRaNoSpeech();
            }
        }
        if (countFailed == 3) {
            Login_View.diemDanh_View.diemDanhThuCong();
            txtInputText.setFocusable(false);
            countFailed = 0;
        }
    }

    private void diemDanhVao() {
        diemDanh = txtInputText.getText().length() == 10;
        if (diemDanh) {
            txtInputText.setSelectionStart(0);
            txtInputText.setSelectionEnd(9);
            maRFID = txtInputText.getText().trim();
            CanBo_Model canBoDaDKThe = CanBo_List.getController().load_CanBoByRFID(maRFID);
            if (canBoDaDKThe == null) {
                SinhVien_Model sinhVienDaDKThe = SinhVien_List.getController().load_SinhVienByRFID(maRFID);
                if (sinhVienDaDKThe == null) {
                    txt_Alert.setText("Mã thẻ chưa được đăng ký hoặc không hợp lệ");
                    text2Speech.setContent(txt_Alert.getText());
                    text2Speech.playSpeech();
                    countFailed++;
                    TransitionPane.repaint(pnl_Info);
                } else {
                    SinhVien_Model sVDangKySK = DSSinhVien_View.getController().getSinhVienByRFID(maRFID, mask);
                    if (sVDangKySK == null) {
                        txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                        text2Speech.setContent(txt_Alert.getText());
                        text2Speech.playSpeech();
                        TransitionPane.repaint(pnl_Info);

                    } else {
                        SinhVien_Details sinhVien_Details = new SinhVien_Details();
                        sinhVien_Details.setSinhVien_Model(sVDangKySK);
                        sinhVien_Details.setText();
                        sinhVien_Details.setAccess(false, DataString.NO_CHANGED);
                        sinhVien_Details.setVisbleWhenDiemDanh(false);
                        TransitionPane.replacePane(pnl_Info, sinhVien_Details);
                        if (DSSinhVien_View.getController().isDiemDanhVao(maRFID, mask)) {
                            txt_Alert.setText("Chào bạn " + sVDangKySK.getTen() + " Bạn đã điểm danh vào rồi");
                            text2Speech.setContent(txt_Alert.getText());
                            text2Speech.playSpeech();
                            countFailed = 0;
                        } else {
                            txt_Alert.setText("Chào bạn " + sVDangKySK.getTen());
                            text2Speech.setContent(txt_Alert.getText());
                            text2Speech.playSpeech();
                            countFailed = 0;
                            DSSinhVien_View.getController().update_DiemDanhVao(new Time(new java.util.Date().getTime()), sVDangKySK.getMaSV(), mask);
                            DSSinhVien_View.getController().setDiemDanhTuDong(true, sVDangKySK.getMaSV());
                            Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSSinhVien();
                        }
                    }
                }
            } else {
                CanBo_Model canBoDKSK = DSCanBo_View.getController().getCanBoByRFID(maRFID, mask);
                if (canBoDKSK == null) {
                    txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                    text2Speech.setContent(txt_Alert.getText());
                    text2Speech.playSpeech();
                    TransitionPane.repaint(pnl_Info);
                } else {
                    CanBo_Detail canBo_Detail = new CanBo_Detail();
                    canBo_Detail.setCanBo_Model(canBoDKSK);
                    canBo_Detail.setText();
                    canBo_Detail.setAccess(false, DataString.NO_CHANGED);
                    canBo_Detail.setVisbleWhenDiemDanh(false);
                    TransitionPane.replacePane(pnl_Info, canBo_Detail);
                    if (DSCanBo_View.getController().isDiemDanhVao(maRFID, mask)) {
                        txt_Alert.setText("Xin Chào " + canBoDKSK.getTen() + "\nBạn đã điểm danh vào rồi");
                        text2Speech.setContent(txt_Alert.getText());
                        text2Speech.playSpeech();
                        countFailed = 0;
                    } else {
                        txt_Alert.setText("Xin Chào " + canBoDKSK.getTen());
                        text2Speech.setContent(txt_Alert.getText());
                        text2Speech.playSpeech();
                        countFailed = 0;
                        DSCanBo_View.getController().update_DiemDanhVao(new Time(new java.util.Date().getTime()), canBoDKSK.getMaCB(), mask);
                        DSCanBo_View.getController().setDiemDanhTuDong(true, canBoDKSK.getMaCB(), mask);
                        Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSCanBo();
                    }
                }
            }
            diemDanh = false;
        }
    }

    private void diemDanhRa() {
        diemDanh = txtInputText.getText().length() == 10;
        if (diemDanh) {
            txtInputText.setSelectionStart(0);
            txtInputText.setSelectionEnd(9);
            maRFID = txtInputText.getText().trim();
            CanBo_Model canBoDaDKThe = CanBo_List.getController().load_CanBoByRFID(maRFID);
            if (canBoDaDKThe == null) {
                SinhVien_Model sinhVienDaDKThe = SinhVien_List.getController().load_SinhVienByRFID(maRFID);
                if (sinhVienDaDKThe == null) {
                    txt_Alert.setText("Mã thẻ chưa được đăng ký hoặc không hợp lệ");
                    text2Speech.setContent(txt_Alert.getText());
                    text2Speech.playSpeech();
                    countFailed++;
                    TransitionPane.repaint(pnl_Info);

                } else {
                    SinhVien_Model sinhVien_Model = DSSinhVien_View.getController().getSinhVienByRFID(maRFID, mask);
                    if (sinhVien_Model == null) {
                        txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                        text2Speech.setContent(txt_Alert.getText());
                        text2Speech.playSpeech();
                        TransitionPane.repaint(pnl_Info);

                    } else {
                        SinhVien_Details sinhVien_Details = new SinhVien_Details();
                        sinhVien_Details.setSinhVien_Model(sinhVien_Model);
                        TransitionPane.replacePane(pnl_Info, sinhVien_Details);
                        if (DSSinhVien_View.getController().isDiemDanhVao(maRFID, mask)) {
                            txt_Alert.setText("Chào bạn " + sinhVien_Model.getTen() + "\nBạn đã điểm danh ra rồi");
                            text2Speech.setContent(txt_Alert.getText());
                            text2Speech.playSpeech();
                            countFailed = 0;
                        } else {
                            txt_Alert.setText("Tạm biệt " + sinhVien_Model.getTen());
                            text2Speech.setContent(txt_Alert.getText());
                            text2Speech.playSpeech();
                            countFailed = 0;
                            DSSinhVien_View.getController().update_DiemDanhRa(new Time(new java.util.Date().getTime()), sinhVien_Model.getMaSV(), mask);
                            Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSSinhVien();
                        }
                    }
                }
            } else {
                CanBo_Model canBo_Model = DSCanBo_View.getController().getCanBoByRFID(maRFID, mask);
                if (canBo_Model == null) {
                    txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                    text2Speech.setContent(txt_Alert.getText());
                    text2Speech.playSpeech();
                    countFailed++;
                    TransitionPane.repaint(pnl_Info);

                } else {
                    CanBo_Detail canBo_Detail = new CanBo_Detail();
                    canBo_Detail.setCanBo_Model(canBo_Model);
                    TransitionPane.replacePane(pnl_Info, canBo_Detail);
                    if (DSCanBo_View.getController().isDiemDanhVao(maRFID, mask)) {
                        txt_Alert.setText("Xin Chào " + canBo_Model.getTen() + "\nBạn đã điểm danh ra rồi");
                        text2Speech.setContent(txt_Alert.getText());
                        text2Speech.playSpeech();
                        countFailed = 0;
                    } else {
                        txt_Alert.setText("Tạm biệt " + canBo_Model.getTen());
                        text2Speech.setContent(txt_Alert.getText());
                        text2Speech.playSpeech();
                        countFailed = 0;
                        DSCanBo_View.getController().update_DiemDanhRa(new Time(new java.util.Date().getTime()), canBo_Model.getMaCB(), mask);
                        Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSCanBo();
                    }
                }
            }
            diemDanh = false;
        }
    }

    private void diemDanhVaoNoSpeech() {
        diemDanh = txtInputText.getText().length() == 10;
        if (diemDanh) {
            txtInputText.setSelectionStart(0);
            txtInputText.setSelectionEnd(9);
            maRFID = txtInputText.getText().trim();
            CanBo_Model canBoDaDKThe = CanBo_List.getController().load_CanBoByRFID(maRFID);
            if (canBoDaDKThe == null) {
                SinhVien_Model sinhVienDaDKThe = SinhVien_List.getController().load_SinhVienByRFID(maRFID);
                if (sinhVienDaDKThe == null) {
                    txt_Alert.setText("Mã thẻ chưa được đăng ký hoặc không hợp lệ");
                    countFailed++;
                    TransitionPane.repaint(pnl_Info);

                } else {
                    SinhVien_Model sinhVien_Model = DSSinhVien_View.getController().getSinhVienByRFID(maRFID, mask);
                    if (sinhVien_Model == null) {
                        txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                        TransitionPane.repaint(pnl_Info);

                    } else {
                        if (DSSinhVien_View.getController().isDiemDanhVao(maRFID, mask)) {
                            txt_Alert.setText("Chào bạn " + sinhVien_Model.getTen() + "\nBạn đã điểm danh vào rồi");
                            countFailed = 0;
                        } else {
                            txt_Alert.setText("Chào bạn " + sinhVien_Model.getTen());
                            countFailed = 0;
                            DSSinhVien_View.getController().update_DiemDanhVao(new Time(new java.util.Date().getTime()), sinhVien_Model.getMaSV(), mask);
                            DSSinhVien_View.getController().setDiemDanhTuDong(true, sinhVien_Model.getMaSV());
                            Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSSinhVien();
                        }
                    }
                }
            } else {
                CanBo_Model canBo_Model = DSCanBo_View.getController().getCanBoByRFID(maRFID, mask);
                if (canBo_Model == null) {
                    txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                    TransitionPane.repaint(pnl_Info);

                } else {
                    if (DSCanBo_View.getController().isDiemDanhVao(maRFID, mask)) {
                        txt_Alert.setText("Xin Chào " + canBo_Model.getTen() + "\nBạn đã điểm danh vào rồi");
                        countFailed = 0;
                    } else {
                        txt_Alert.setText("Xin Chào " + canBo_Model.getTen());
                        countFailed = 0;
                        DSCanBo_View.getController().update_DiemDanhVao(new Time(new java.util.Date().getTime()), canBo_Model.getMaCB(), mask);
                        DSCanBo_View.getController().setDiemDanhTuDong(true, canBo_Model.getMaCB(), mask);
                        Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSCanBo();
                    }
                }
            }
            diemDanh = false;
        }
    }

    private void diemDanhRaNoSpeech() {
        diemDanh = txtInputText.getText().length() == 10;
        if (diemDanh) {
            txtInputText.setSelectionStart(0);
            txtInputText.setSelectionEnd(9);
            maRFID = txtInputText.getText().trim();
            CanBo_Model canBoDaDKThe = CanBo_List.getController().load_CanBoByRFID(maRFID);
            if (canBoDaDKThe == null) {
                SinhVien_Model sinhVienDaDKThe = SinhVien_List.getController().load_SinhVienByRFID(maRFID);
                if (sinhVienDaDKThe == null) {
                    txt_Alert.setText("Mã thẻ chưa được đăng ký hoặc không hợp lệ");
                    countFailed++;
                    TransitionPane.repaint(pnl_Info);

                } else {
                    SinhVien_Model sinhVien_Model = DSSinhVien_View.getController().getSinhVienByRFID(maRFID, mask);
                    if (sinhVien_Model == null) {
                        txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                        TransitionPane.repaint(pnl_Info);

                    } else {
                        if (DSSinhVien_View.getController().isDiemDanhVao(maRFID, mask)) {
                            txt_Alert.setText("Chào bạn " + sinhVien_Model.getTen() + "\nBạn đã điểm danh ra rồi");
                            countFailed = 0;
                        } else {
                            txt_Alert.setText("Tạm biệt " + sinhVien_Model.getTen());
                            countFailed = 0;
                            DSSinhVien_View.getController().update_DiemDanhRa(new Time(new java.util.Date().getTime()), sinhVien_Model.getMaSV(), mask);
                            Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSSinhVien();
                        }
                    }
                }
            } else {
                CanBo_Model canBo_Model = DSCanBo_View.getController().getCanBoByRFID(maRFID, mask);
                if (canBo_Model == null) {
                    txt_Alert.setText("Bạn chưa đăng ký sự kiện này!");
                    TransitionPane.repaint(pnl_Info);

                } else {
                    if (DSCanBo_View.getController().isDiemDanhVao(maRFID, mask)) {
                        txt_Alert.setText("Xin Chào " + canBo_Model.getTen() + "\nBạn đã điểm danh ra rồi");
                        countFailed = 0;
                    } else {
                        txt_Alert.setText("Tạm biệt " + canBo_Model.getTen());
                        countFailed = 0;
                        DSCanBo_View.getController().update_DiemDanhRa(new Time(new java.util.Date().getTime()), canBo_Model.getMaCB(), mask);
                        Login_View.diemDanh_View.getXemChiTiet().getdSThamGia().showDSCanBo();
                    }
                }
            }
            diemDanh = false;
        }
    }

    public JTextField getTxtInputText() {
        return txtInputText;
    }

    public void setTxtInputText(JTextField txtInputText) {
        this.txtInputText = txtInputText;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setTenSK(String name) {
        lbl_TenSK.setText(name);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Alert = new javax.swing.JPanel();
        txtInputText = new javax.swing.JTextField();
        pnl_Header = new javax.swing.JPanel();
        lbl_TenSK = new javax.swing.JLabel();
        pnl_Content = new javax.swing.JPanel();
        pnl_Title = new javax.swing.JPanel();
        lbl_Title = new javax.swing.JLabel();
        pnl_Clock = new javax.swing.JPanel();
        pnl_View = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txt_Alert = new javax.swing.JTextField();
        pnl_Info = new javax.swing.JPanel();
        pnl_Task = new javax.swing.JPanel();
        btn_DiemDanhThuCong = new javax.swing.JButton();
        btn_Hide = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(650, 725));
        setPreferredSize(new java.awt.Dimension(850, 630));
        setResizable(false);
        setSize(new java.awt.Dimension(850, 630));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        pnl_Alert.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Alert.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 0, 0, 0));
        pnl_Alert.setLayout(new java.awt.BorderLayout());

        txtInputText.setColumns(10);
        txtInputText.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtInputText.setForeground(new java.awt.Color(255, 255, 255));
        txtInputText.setBorder(null);
        txtInputText.setMinimumSize(new java.awt.Dimension(0, 0));
        txtInputText.setPreferredSize(new java.awt.Dimension(0, 0));
        txtInputText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInputTextActionPerformed(evt);
            }
        });
        pnl_Alert.add(txtInputText, java.awt.BorderLayout.PAGE_START);

        pnl_Header.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Header.setLayout(new java.awt.BorderLayout());

        lbl_TenSK.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lbl_TenSK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TenSK.setText("Tên sự kiện");
        lbl_TenSK.setPreferredSize(new java.awt.Dimension(135, 75));
        pnl_Header.add(lbl_TenSK, java.awt.BorderLayout.CENTER);

        pnl_Alert.add(pnl_Header, java.awt.BorderLayout.PAGE_START);

        pnl_Content.setLayout(new java.awt.BorderLayout());

        pnl_Title.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Title.setPreferredSize(new java.awt.Dimension(260, 100));
        pnl_Title.setLayout(new java.awt.BorderLayout());

        lbl_Title.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lbl_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Title.setText("Điểm danh sẽ dừng sau");
        lbl_Title.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl_Title.setPreferredSize(new java.awt.Dimension(191, 36));
        pnl_Title.add(lbl_Title, java.awt.BorderLayout.PAGE_START);

        pnl_Clock.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Clock.setPreferredSize(new java.awt.Dimension(0, 80));
        pnl_Clock.setLayout(new java.awt.GridLayout(1, 0));
        pnl_Title.add(pnl_Clock, java.awt.BorderLayout.CENTER);

        pnl_Content.add(pnl_Title, java.awt.BorderLayout.PAGE_START);

        pnl_View.setLayout(new java.awt.BorderLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(201, 120));
        jPanel1.setPreferredSize(new java.awt.Dimension(201, 80));
        jPanel1.setLayout(new java.awt.BorderLayout());

        txt_Alert.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txt_Alert.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_Alert.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 25));
        txt_Alert.setFocusable(false);
        txt_Alert.setPreferredSize(new java.awt.Dimension(500, 50));
        txt_Alert.setRequestFocusEnabled(false);
        jPanel1.add(txt_Alert, java.awt.BorderLayout.CENTER);

        pnl_View.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnl_Info.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Info.setLayout(new java.awt.BorderLayout());
        pnl_View.add(pnl_Info, java.awt.BorderLayout.CENTER);

        pnl_Task.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Task.setPreferredSize(new java.awt.Dimension(100, 52));
        pnl_Task.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 1));

        btn_DiemDanhThuCong.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btn_DiemDanhThuCong.setText("Điểm danh thủ công");
        btn_DiemDanhThuCong.setPreferredSize(new java.awt.Dimension(220, 45));
        btn_DiemDanhThuCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DiemDanhThuCongActionPerformed(evt);
            }
        });
        pnl_Task.add(btn_DiemDanhThuCong);

        btn_Hide.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btn_Hide.setText("Ẩn xuống");
        btn_Hide.setPreferredSize(new java.awt.Dimension(125, 45));
        btn_Hide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HideActionPerformed(evt);
            }
        });
        pnl_Task.add(btn_Hide);

        pnl_View.add(pnl_Task, java.awt.BorderLayout.PAGE_END);

        pnl_Content.add(pnl_View, java.awt.BorderLayout.CENTER);

        pnl_Alert.add(pnl_Content, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnl_Alert);
    }// </editor-fold>//GEN-END:initComponents

    private void txtInputTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInputTextActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        txt_Alert.setText("Đang điểm danh ...");
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        txt_Alert.setText("<html><p align='center'>Tạm dừng điểm danh. "
                + "Click vào đây để tiếp tục</p></html>");
    }//GEN-LAST:event_formWindowLostFocus

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        btn_Hide.doClick();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        btn_Hide.doClick();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        txtInputText.requestFocus();
    }//GEN-LAST:event_formWindowDeactivated

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        txtInputText.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void btn_HideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HideActionPerformed
        dispose();
        txtInputText.requestFocus();
        JTextField txt_Clock = Login_View.diemDanh_View.getTxt_Clock();
        txt_Clock.setBackground(Color.white);
        txt_Clock.setOpaque(true);
        txt_Clock.setPreferredSize(new Dimension(150, 50));
        txt_Clock.setFont(new java.awt.Font("Arial", 0, 24));
        Login_View.diemDanh_View.getPnl_Time().add(Login_View.diemDanh_View.getTxt_Clock());
        Login_View.diemDanh_View.getPnl_Time().validate();
        Login_View.diemDanh_View.getPnl_Time().repaint();
        dispose();
        txtInputText.requestFocus();
    }//GEN-LAST:event_btn_HideActionPerformed

    private void btn_DiemDanhThuCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DiemDanhThuCongActionPerformed
        Login_View.diemDanh_View.diemDanhThuCong();
        requestFocus(false);
    }//GEN-LAST:event_btn_DiemDanhThuCongActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_DiemDanhThuCong;
    private javax.swing.JButton btn_Hide;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_TenSK;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel pnl_Alert;
    private javax.swing.JPanel pnl_Clock;
    private javax.swing.JPanel pnl_Content;
    private javax.swing.JPanel pnl_Header;
    private javax.swing.JPanel pnl_Info;
    private javax.swing.JPanel pnl_Task;
    private javax.swing.JPanel pnl_Title;
    private javax.swing.JPanel pnl_View;
    private javax.swing.JTextField txtInputText;
    private javax.swing.JTextField txt_Alert;
    // End of variables declaration//GEN-END:variables
}
