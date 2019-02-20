/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinhvien.view;

import administrator.settings.Config;
import app.view.App_View;
import other.custom.Check;
import other.custom.DataString;
import other.custom.GradientButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import other.custom.CustomBalloonTip;
import other.custom.TransitionPane;
import resources.Resources;
import sinhvien.model.SinhVien_Model;

/**
 *
 * @author chuna
 */
public class SinhVien_Details extends javax.swing.JPanel implements ActionListener {

    private static SinhVien_View VIEW = SinhVien_View.getView();

    private boolean edit;
    private GradientButton btn_Luu;
    private SinhVien_Model sinhVien_Model;
    private int row;

    private CustomBalloonTip[] toolTips;
    private List<JTextComponent> asList;
    private List<JComboBox> asListComBox;
    private List<JComponent> asListAll;

    public SinhVien_Details() {
        initComponents();
        createUI();
    }

    private void createUI() {
        asListAll = Arrays.asList(txt_MSSV, txt_HoTen, txt_Email, cBox_Lop, cBox_Nganh, cBox_Khoa, cBox_NienKhoa);
        asList = Arrays.asList(txt_MSSV, txt_HoTen, txt_Email, txt_MaRFID);
        asListComBox = Arrays.asList(cBox_Lop, cBox_Nganh, cBox_Khoa, cBox_NienKhoa);
        toolTips = new CustomBalloonTip[8];
        toolTips[0] = new CustomBalloonTip(txt_MSSV, new JLabel());
        toolTips[0].setVisible(false);
        toolTips[1] = new CustomBalloonTip(txt_HoTen, new JLabel());
        toolTips[1].setVisible(false);
        toolTips[2] = new CustomBalloonTip(txt_Email, new JLabel());
        toolTips[2].setVisible(false);
        toolTips[3] = new CustomBalloonTip(cBox_Lop, new JLabel());
        toolTips[3].setVisible(false);
        toolTips[4] = new CustomBalloonTip(cBox_Nganh, new JLabel());
        toolTips[4].setVisible(false);
        toolTips[5] = new CustomBalloonTip(cBox_Khoa, new JLabel());
        toolTips[5].setVisible(false);
        toolTips[6] = new CustomBalloonTip(cBox_NienKhoa, new JLabel());
        toolTips[6].setVisible(false);
        toolTips[7] = new CustomBalloonTip(txt_MaRFID, new JLabel());
        toolTips[7].setVisible(false);

        btn_Luu = new GradientButton(Color.decode("#457fca"), Color.decode("#5691c8"), 0, "Đóng");
        btn_Luu.setFont(new Font("Arial", 0, 16));
        btn_Luu.setPreferredSize(new Dimension(230, 50));
        jPanel1.add(btn_Luu);

        btn_QuetThe.addActionListener(this);
        btn_Luu.addActionListener(this);

        String[] lops = App_View.getController().arrayList2String(App_View.getController().getLop());
        String[] khoas = App_View.getController().arrayList2String(App_View.getController().getKhoa());
        String[] nganhs = App_View.getController().arrayList2String(App_View.getController().getNganh());

        cBox_Lop.setPreferredSize(new Dimension(320, 32));
        cBox_Khoa.setPreferredSize(new Dimension(320, 32));
        cBox_Nganh.setPreferredSize(new Dimension(320, 32));

        cBox_Lop.removeAllItems();
        cBox_Khoa.removeAllItems();
        cBox_Nganh.removeAllItems();
        cBox_NienKhoa.removeAllItems();
        cBox_Lop.addItem("Chọn lớp");
        cBox_Khoa.addItem("Chọn khoa");
        cBox_Nganh.addItem("Chọn ngành");
        cBox_NienKhoa.addItem("Chọn niên khoá");

        for (String lop : lops) {
            cBox_Lop.addItem(lop);
        }
        for (String khoa : khoas) {
            cBox_Khoa.addItem(khoa);
        }
        for (String nganh : nganhs) {
            cBox_Nganh.addItem(nganh);
        }
        String[] nienKhoas = {"2012-2016", "2013-2017", "2014-2019", "2015-2020", "2016-2021", "2017-2022"};
        for (String nienKhoa : nienKhoas) {
            cBox_NienKhoa.addItem(nienKhoa);
        }
        Arrays.asList(txt_Lop, txt_Khoa, txt_Nganh, txt_NienKhoa).forEach((t) -> {
            t.setBackground(Color.white);
            t.setOpaque(true);
        });
        setTextComponent();
        setUpQuetThe();
    }

    private void setUpQuetThe() {
        txt_temp.setText("");
        txt_MaRFID.setFocusable(false);
        txt_MaRFID.setRequestFocusEnabled(false);
        txt_temp.setSelectionStart(0);
        txt_temp.setSelectionEnd(9);
        txt_temp.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateMaThe();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void updateMaThe() {
                if (txt_temp.getText().length() == 10) {
                    txt_MaRFID.setForeground(Color.decode("#2e3233"));
                    txt_MaRFID.setText(txt_temp.getText());
                }
            }
        });
        txt_temp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!Check.isRFIDValid(txt_temp.getText())) {
                    txt_temp.setText("");
                    txt_MaRFID.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!Check.isRFIDValid(txt_temp.getText())) {
                    txt_temp.setText("");
                    txt_MaRFID.setText("");
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (!Check.isRFIDValid(txt_temp.getText())) {
                    txt_temp.setText("");
                    txt_MaRFID.setText("");
                }
            }

        });
        txt_temp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txt_temp.getText().length() > 0) {
//                    btn_QuetThe.setVisible(!btn_QuetThe.getText().equals("Đang đợi thẻ .."));
                }
            }
        });

        txt_MaRFID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (txt_MaRFID.getText().length() == 10) {
                    btn_QuetThe.setText("Quét lại");
                    btn_QuetThe.setOpaque(true);
                    btn_QuetThe.setIcon(null);
                    btn_QuetThe.setForeground(Color.white);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

    }

    private boolean checkSave() {
        int count = 0;
        for (CustomBalloonTip balloonTip : toolTips) {
            if (!balloonTip.isShowNotify()) {
                count++;
            }
        }
        return count == toolTips.length;
    }

    private void checkInput() {
        for (int i = 0; i < asList.size(); i++) {
            if (!asList.get(i).equals(txt_MaRFID)) {
                switch (asList.get(i).getText()) {
                    case "Mã số sinh viên": {
                        toolTips[i].showNotify("Không được để trống", isEdit());
                        break;
                    }
                    case "Họ tên sinh viên": {
                        toolTips[i].showNotify("Không được để trống", isEdit());
                        break;
                    }
                    case "example@example.com": {
                        toolTips[i].showNotify("Không được để trống", isEdit());
                        break;
                    }
                    case "": {
                        toolTips[i].showNotify("Không được để trống", isEdit());
                        break;
                    }
                    default: {
                        toolTips[i].hideNotify();
                        break;
                    }
                }
            }
        }
        if (!txt_MSSV.getText().substring(0, 1).equals("B")) {
            toolTips[0].showNotify("Mã không hợp lệ", isEdit());
        }
        if (!txt_MSSV.getText().isEmpty() && !txt_MSSV.getText().equals("Mã số sinh viên")) {
            if (!Check.unAccent(txt_MSSV.getText())) {
                toolTips[0].showNotify("Mã số sinh viên không chứa ký tự có dấu", isEdit());
            }
        }

        String[] split = txt_HoTen.getText().split(" ");
        if (split.length < 2) {
            toolTips[1].showNotify("Phải nhập gồm họ và tên", isEdit());
        }
        if (cBox_Lop.getSelectedIndex() == 0) {
            toolTips[3].showNotify("Chưa chọn lớp", isEdit());
        }
        if (cBox_Nganh.getSelectedIndex() == 0) {
            toolTips[4].showNotify("Chưa chọn ngành", isEdit());
        }
        if (cBox_Khoa.getSelectedIndex() == 0) {
            toolTips[5].showNotify("Chưa chọn khoa", isEdit());
        }
        if (cBox_NienKhoa.getSelectedIndex() == 0) {
            toolTips[6].showNotify("Chưa chọn niên khoá", isEdit());
        }
        if (!Check.isValidEmailAddress(txt_Email.getText())) {
            toolTips[2].showNotify("Không đúng định dạng mail", isEdit());
        }
        if (!Check.isNameValid(txt_HoTen.getText())) {
            toolTips[1].showNotify("Tên chỉ được chứa ký tự là chữ", isEdit());
        }
        if (!txt_MaRFID.getText().isEmpty()
                && !txt_MaRFID.getText().equals("Chưa có")
                && !txt_MaRFID.getText().equals("Mã RFID")) {
            if (!Check.isRFIDValid(txt_MaRFID.getText())) {
                toolTips[7].showNotify("Mã RFID sai định dạng!", isEdit());
                btn_QuetThe.setText("Quét lại");
            }
        } else {
            txt_MaRFID.setText("Chưa có");
        }
    }

    private void setTextComponent() {
        asList.forEach((txt) -> {
            txt.setBackground(Color.white);
            txt.setOpaque(true);
            String temp = txt.getText();
            txt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (!(e.getKeyCode() == KeyEvent.VK_ESCAPE) && txt.getText().trim().isEmpty() || txt.getText().equals(temp)) {
                        txt.setText("");
                        txt.setForeground(Color.decode("#9999999"));
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        for (int i = 0; i < asListAll.size() - 1; i++) {
                            if (e.getSource().equals(asListAll.get(i))) {
                                asListAll.get(i + 1).requestFocus();
                            }
                        }
                    }
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    if (txt.getText().trim().isEmpty() || txt.getText().equals(temp)) {
                        txt.setText("");
                        txt.setForeground(Color.decode("#9999999"));
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                }
            });
            txt.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                        txt.setCaretPosition(0);
                        txt.setForeground(Color.decode("#999999"));
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                    Object object = e.getSource();
                    if (object.equals(txt_MSSV)) {
                        spr_Show.setForeground(Config.focusGained);
                        toolTips[0].hideNotify();
                    }
                    if (object.equals(txt_HoTen)) {
                        spr_Show5.setForeground(Config.focusGained);
                        toolTips[1].hideNotify();
                    }
                    if (object.equals(txt_Email)) {
                        spr_Show6.setForeground(Config.focusGained);
                        toolTips[2].hideNotify();
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                        txt.setText(temp);
                        txt.setForeground(Color.decode("#999999"));
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                    Object object = e.getSource();
                    if (object.equals(txt_MSSV)) {
                        spr_Show.setForeground(Config.focusLost);
                        if (txt_MSSV.getText().equals("") || txt_MSSV.getText().equals("Mã số sinh viên")) {
                            toolTips[0].showNotify("Không được để trống", isEdit());
                        } else if (!txt_MSSV.getText().substring(0, 1).equals("B")) {
                            toolTips[0].showNotify("Mã không hợp lệ", isEdit());
                        }
                        if (!txt_MSSV.getText().isEmpty() && !txt_MSSV.getText().equals("Mã số sinh viên")) {
                            if (!Check.unAccent(txt_MSSV.getText())) {
                                toolTips[0].showNotify("Mã số sinh viên không chứa ký tự có dấu", isEdit());
                            }
                        }
                    }
                    if (object.equals(txt_HoTen)) {
                        spr_Show5.setForeground(Config.focusLost);
                        if (txt_HoTen.getText().equals("") || txt_HoTen.getText().equals("Họ tên sinh viên")) {
                            toolTips[1].showNotify("Không được để trống", isEdit());
                        } else {
                            String[] split = txt_HoTen.getText().split(" ");
                            if (split.length < 2) {
                                toolTips[1].showNotify("Phải nhập gồm họ và tên", isEdit());
                            }
                        }
                    }
                    if (object.equals(txt_Email)) {
                        spr_Show6.setForeground(Config.focusLost);
                        if (txt_Email.getText().equals("") || txt_Email.getText().equals("example@example.com")) {
                            toolTips[2].showNotify("Không được để trống", isEdit());
                        }
                    }
                }
            });
            txt.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                        txt.setCaretPosition(0);
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                    if (e.getSource().equals(txt_MSSV)) {
                        spr_Show.setForeground(Config.focusGained);
                        toolTips[0].hideNotify();

                    }
                    if (e.getSource().equals(txt_HoTen)) {
                        spr_Show5.setForeground(Config.focusGained);
                        toolTips[1].hideNotify();

                    }
                    if (e.getSource().equals(txt_Email)) {
                        spr_Show6.setForeground(Config.focusGained);
                        toolTips[2].hideNotify();

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
        for (int i = 0; i < asListAll.size() - 1; i++) {
            asListAll.get(i).setNextFocusableComponent(asListAll.get(i + 1));
        }
    }

    public void setAccess(boolean edit, String type) {
        setEditor(edit);
        setEdit(edit);
        btn_Luu.setText(type);
    }

    private void setEditor(boolean edit) {
        asList.forEach((component) -> {
            if (!component.equals(txt_MaRFID)) {
                component.setEditable(edit);
                component.setFocusable(edit);
            }
        });

        if (edit) {
            btn_Luu.setText("Thêm");
            jPanel10.remove(txt_NienKhoa);
            jPanel10.remove(spr_Show10);
            jPanel10.add(cBox_NienKhoa, 1);

            jPanel5.remove(txt_Lop);
            jPanel5.remove(spr_Show7);
            jPanel5.add(cBox_Lop, 1);

            jPanel6.remove(txt_Nganh);
            jPanel6.remove(spr_Show8);
            jPanel6.add(cBox_Nganh, 1);

            jPanel9.remove(txt_Khoa);
            jPanel9.remove(spr_Show9);
            jPanel9.add(cBox_Khoa, 1);
        } else {
            btn_Luu.setText("Đóng");
            jPanel10.remove(cBox_NienKhoa);
            jPanel10.add(txt_NienKhoa, 1);
            jPanel10.add(spr_Show10);

            jPanel5.remove(cBox_Lop);
            jPanel5.add(txt_Lop, 1);
            jPanel5.add(spr_Show7);

            jPanel6.remove(cBox_Nganh);
            jPanel6.add(txt_Nganh, 1);
            jPanel6.add(spr_Show8);

            jPanel9.remove(cBox_Khoa);
            jPanel9.add(txt_Khoa, 1);
            jPanel9.add(spr_Show9);
            asList.forEach((txt) -> {
                txt.setForeground(Color.decode("#2e3233"));
            });
        }
        boolean update = txt_MaRFID.getText().equals("Mã RFID")
                || txt_MaRFID.getText().isEmpty()
                || txt_MaRFID.getText().equals("Chưa có");
        btn_QuetThe.setVisible(edit);
        btn_QuetThe.setText(update ? "Quét mã RFID" : "Cập nhật mã RFID");
        if (edit) {
            btn_Luu.setText("Thêm");
        } else {
            btn_Luu.setText("Đóng");
            asList.forEach((txt) -> {
                txt.setForeground(Color.decode("#2e3233"));
            });
        }
    }

    private void clearText() {
        txt_temp.setText("");
        asList.forEach((component) -> {
            component.setText("");
        });
        asListComBox.forEach((t) -> {
            t.setSelectedIndex(0);
        });
    }

    public void setText() {
        clearText();
        txt_MSSV.setText(sinhVien_Model.getMaSV());
        txt_HoTen.setText(sinhVien_Model.getTen());
        txt_Email.setText(sinhVien_Model.getEmail());
        txt_Lop.setText(sinhVien_Model.getLop());
        cBox_Lop.setSelectedItem(sinhVien_Model.getLop());

        txt_Khoa.setText(sinhVien_Model.getKhoa());
        cBox_Khoa.setSelectedItem(sinhVien_Model.getKhoa());

        txt_Nganh.setText(sinhVien_Model.getNganh());
        cBox_Nganh.setSelectedItem(sinhVien_Model.getNganh());

        txt_NienKhoa.setText(sinhVien_Model.getNienKhoa());
        cBox_NienKhoa.setSelectedItem(sinhVien_Model.getNienKhoa());

        txt_MaRFID.setText(sinhVien_Model.getMaRFID());
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public SinhVien_Model getSinhVien_Model() {
        return sinhVien_Model;
    }

    public void setSinhVien_Model(SinhVien_Model sinhVien_Model, int row) {
        this.sinhVien_Model = sinhVien_Model;
        this.row = row;
    }

    public void setSinhVien_Model(SinhVien_Model sinhVien_Model) {
        this.sinhVien_Model = sinhVien_Model;
    }

    public void setBtn_Luu(GradientButton btn_Luu) {
        this.btn_Luu = btn_Luu;
    }

    public GradientButton get_btnLuu() {
        return btn_Luu;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cBox_NienKhoa = new javax.swing.JComboBox<>();
        cBox_Lop = new javax.swing.JComboBox<>();
        cBox_Nganh = new javax.swing.JComboBox<>();
        cBox_Khoa = new javax.swing.JComboBox<>();
        pnl_Display = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_Macb = new javax.swing.JLabel();
        txt_MSSV = new javax.swing.JTextField();
        spr_Hide = new javax.swing.JSeparator();
        spr_Show = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        lbl_Macb1 = new javax.swing.JLabel();
        txt_HoTen = new javax.swing.JTextField();
        spr_Hide5 = new javax.swing.JSeparator();
        spr_Show5 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        lbl_Macb2 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        spr_Hide6 = new javax.swing.JSeparator();
        spr_Show6 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        lbl_Macb3 = new javax.swing.JLabel();
        txt_Lop = new javax.swing.JTextField();
        spr_Hide7 = new javax.swing.JSeparator();
        spr_Show7 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        lbl_Macb4 = new javax.swing.JLabel();
        txt_Nganh = new javax.swing.JTextField();
        spr_Hide8 = new javax.swing.JSeparator();
        spr_Show8 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        lbl_Macb6 = new javax.swing.JLabel();
        txt_Khoa = new javax.swing.JTextField();
        spr_Hide9 = new javax.swing.JSeparator();
        spr_Show9 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        lbl_Macb7 = new javax.swing.JLabel();
        txt_NienKhoa = new javax.swing.JTextField();
        spr_Hide10 = new javax.swing.JSeparator();
        spr_Show10 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        lbl_Macb5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txt_MaRFID = new javax.swing.JTextField();
        txt_temp = new javax.swing.JTextField();
        btn_QuetThe = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        cBox_NienKhoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cBox_NienKhoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2017-2018", "2016-2017", "2015-2016", "2014-2015" }));
        cBox_NienKhoa.setPreferredSize(new java.awt.Dimension(320, 32));
        cBox_NienKhoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cBox_NienKhoaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cBox_NienKhoaFocusLost(evt);
            }
        });

        cBox_Lop.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cBox_Lop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2017-2018", "2016-2017", "2015-2016", "2014-2015" }));
        cBox_Lop.setPreferredSize(new java.awt.Dimension(320, 32));
        cBox_Lop.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cBox_LopFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cBox_LopFocusLost(evt);
            }
        });

        cBox_Nganh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cBox_Nganh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2017-2018", "2016-2017", "2015-2016", "2014-2015" }));
        cBox_Nganh.setPreferredSize(new java.awt.Dimension(320, 32));
        cBox_Nganh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cBox_NganhFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cBox_NganhFocusLost(evt);
            }
        });

        cBox_Khoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cBox_Khoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2017-2018", "2016-2017", "2015-2016", "2014-2015" }));
        cBox_Khoa.setPreferredSize(new java.awt.Dimension(320, 32));
        cBox_Khoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cBox_KhoaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cBox_KhoaFocusLost(evt);
            }
        });

        setBackground(new java.awt.Color(255, 255, 255));

        pnl_Display.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 80, 30, 80));
        pnl_Display.setOpaque(false);
        pnl_Display.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 20));

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setText("MSSV");
        lbl_Macb.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel2.add(lbl_Macb);

        txt_MSSV.setEditable(false);
        txt_MSSV.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_MSSV.setForeground(new java.awt.Color(153, 153, 153));
        txt_MSSV.setText("Mã số sinh viên");
        txt_MSSV.setBorder(null);
        txt_MSSV.setOpaque(true);
        txt_MSSV.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel2.add(txt_MSSV);

        spr_Hide.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel2.add(spr_Hide);

        spr_Show.setName("txt_MSCB"); // NOI18N
        spr_Show.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel2.add(spr_Show);

        pnl_Display.add(jPanel2);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb1.setText("Họ tên:");
        lbl_Macb1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb1.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel3.add(lbl_Macb1);

        txt_HoTen.setEditable(false);
        txt_HoTen.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_HoTen.setForeground(new java.awt.Color(153, 153, 153));
        txt_HoTen.setText("Họ tên sinh viên");
        txt_HoTen.setBorder(null);
        txt_HoTen.setOpaque(true);
        txt_HoTen.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel3.add(txt_HoTen);

        spr_Hide5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide5.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel3.add(spr_Hide5);

        spr_Show5.setName("txt_HoTen"); // NOI18N
        spr_Show5.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel3.add(spr_Show5);

        pnl_Display.add(jPanel3);

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb2.setText("Email:");
        lbl_Macb2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb2.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel4.add(lbl_Macb2);

        txt_Email.setEditable(false);
        txt_Email.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Email.setForeground(new java.awt.Color(153, 153, 153));
        txt_Email.setText("example@example.com");
        txt_Email.setBorder(null);
        txt_Email.setNextFocusableComponent(cBox_Lop);
        txt_Email.setOpaque(true);
        txt_Email.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel4.add(txt_Email);

        spr_Hide6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide6.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel4.add(spr_Hide6);

        spr_Show6.setName("txt_Email"); // NOI18N
        spr_Show6.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel4.add(spr_Show6);

        pnl_Display.add(jPanel4);

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setText("Lớp:");
        lbl_Macb3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel5.add(lbl_Macb3);

        txt_Lop.setEditable(false);
        txt_Lop.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Lop.setForeground(new java.awt.Color(46, 50, 51));
        txt_Lop.setText("Tên lớp");
        txt_Lop.setBorder(null);
        txt_Lop.setNextFocusableComponent(cBox_Nganh);
        txt_Lop.setOpaque(true);
        txt_Lop.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel5.add(txt_Lop);

        spr_Hide7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide7.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel5.add(spr_Hide7);

        spr_Show7.setName("txt_BoMon"); // NOI18N
        spr_Show7.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel5.add(spr_Show7);

        pnl_Display.add(jPanel5);

        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setText("Ngành:");
        lbl_Macb4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel6.add(lbl_Macb4);

        txt_Nganh.setEditable(false);
        txt_Nganh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Nganh.setForeground(new java.awt.Color(46, 50, 51));
        txt_Nganh.setText("Tên ngành");
        txt_Nganh.setBorder(null);
        txt_Nganh.setNextFocusableComponent(cBox_Khoa);
        txt_Nganh.setOpaque(true);
        txt_Nganh.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel6.add(txt_Nganh);

        spr_Hide8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide8.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel6.add(spr_Hide8);

        spr_Show8.setName("txt_Khoa"); // NOI18N
        spr_Show8.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel6.add(spr_Show8);

        pnl_Display.add(jPanel6);

        jPanel9.setOpaque(false);
        jPanel9.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb6.setText("Khoa:");
        lbl_Macb6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb6.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel9.add(lbl_Macb6);

        txt_Khoa.setEditable(false);
        txt_Khoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Khoa.setForeground(new java.awt.Color(46, 50, 51));
        txt_Khoa.setText("Tên khoa");
        txt_Khoa.setBorder(null);
        txt_Khoa.setNextFocusableComponent(cBox_NienKhoa);
        txt_Khoa.setOpaque(true);
        txt_Khoa.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel9.add(txt_Khoa);

        spr_Hide9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide9.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel9.add(spr_Hide9);

        spr_Show9.setName("txt_Khoa"); // NOI18N
        spr_Show9.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel9.add(spr_Show9);

        pnl_Display.add(jPanel9);

        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb7.setText("Niên Khoá:");
        lbl_Macb7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb7.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel10.add(lbl_Macb7);

        txt_NienKhoa.setEditable(false);
        txt_NienKhoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_NienKhoa.setForeground(new java.awt.Color(46, 50, 51));
        txt_NienKhoa.setText("Niên khoá");
        txt_NienKhoa.setBorder(null);
        txt_NienKhoa.setNextFocusableComponent(btn_QuetThe);
        txt_NienKhoa.setOpaque(true);
        txt_NienKhoa.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel10.add(txt_NienKhoa);

        spr_Hide10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide10.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel10.add(spr_Hide10);

        spr_Show10.setName("txt_Khoa"); // NOI18N
        spr_Show10.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel10.add(spr_Show10);

        pnl_Display.add(jPanel10);

        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb5.setText("Mã RFID:");
        lbl_Macb5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb5.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel7.add(lbl_Macb5);

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(320, 36));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 3));

        txt_MaRFID.setEditable(false);
        txt_MaRFID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_MaRFID.setForeground(new java.awt.Color(46, 50, 51));
        txt_MaRFID.setText("Mã RFID");
        txt_MaRFID.setBorder(null);
        txt_MaRFID.setOpaque(true);
        txt_MaRFID.setPreferredSize(new java.awt.Dimension(190, 32));
        jPanel8.add(txt_MaRFID);

        txt_temp.setBackground(new java.awt.Color(240, 240, 240));
        txt_temp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_temp.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel8.add(txt_temp);

        btn_QuetThe.setBackground(new java.awt.Color(118, 175, 235));
        btn_QuetThe.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_QuetThe.setForeground(new java.awt.Color(255, 255, 255));
        btn_QuetThe.setText("Quét mã RFID");
        btn_QuetThe.setBorder(new other.custom.RoundedBorder()
        );
        btn_QuetThe.setBorderPainted(false);
        btn_QuetThe.setContentAreaFilled(false);
        btn_QuetThe.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_QuetThe.setMargin(new java.awt.Insets(0, 5, 0, 5));
        btn_QuetThe.setNextFocusableComponent(btn_Luu);
        btn_QuetThe.setOpaque(true);
        btn_QuetThe.setPreferredSize(new java.awt.Dimension(130, 32));
        jPanel8.add(btn_QuetThe);

        jPanel7.add(jPanel8);

        pnl_Display.add(jPanel7);

        jPanel1.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Display, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnl_Display, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cBox_NienKhoaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_NienKhoaFocusGained
        toolTips[6].hideNotify();
    }//GEN-LAST:event_cBox_NienKhoaFocusGained

    private void cBox_NienKhoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_NienKhoaFocusLost
        if (cBox_NienKhoa.getItemCount() > 0) {
            if (cBox_NienKhoa.getSelectedIndex() == 0) {
                toolTips[6].showNotify("Chưa chọn niên khoá", isEdit());
            }
        }
    }//GEN-LAST:event_cBox_NienKhoaFocusLost

    private void cBox_LopFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_LopFocusGained
        toolTips[3].hideNotify();
    }//GEN-LAST:event_cBox_LopFocusGained

    private void cBox_LopFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_LopFocusLost
        if (cBox_Lop.getItemCount() > 0) {
            if (cBox_Lop.getSelectedIndex() == 0) {
                toolTips[3].showNotify("Chưa chọn lớp", isEdit());
            }
        }
    }//GEN-LAST:event_cBox_LopFocusLost

    private void cBox_NganhFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_NganhFocusGained
        toolTips[4].hideNotify();
    }//GEN-LAST:event_cBox_NganhFocusGained

    private void cBox_NganhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_NganhFocusLost
        if (cBox_Nganh.getItemCount() > 0) {
            if (cBox_Nganh.getSelectedIndex() == 0) {
                toolTips[4].showNotify("Chưa chọn ngành", isEdit());
            }
        }
    }//GEN-LAST:event_cBox_NganhFocusLost

    private void cBox_KhoaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_KhoaFocusGained
        toolTips[5].hideNotify();
    }//GEN-LAST:event_cBox_KhoaFocusGained

    private void cBox_KhoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cBox_KhoaFocusLost
        if (cBox_Khoa.getItemCount() > 0) {
            if (cBox_Khoa.getSelectedIndex() == 0) {
                toolTips[5].showNotify("Chưa chọn khoa", isEdit());
            }
        }
    }//GEN-LAST:event_cBox_KhoaFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_QuetThe;
    private javax.swing.JComboBox<String> cBox_Khoa;
    private javax.swing.JComboBox<String> cBox_Lop;
    private javax.swing.JComboBox<String> cBox_Nganh;
    private javax.swing.JComboBox<String> cBox_NienKhoa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbl_Macb;
    private javax.swing.JLabel lbl_Macb1;
    private javax.swing.JLabel lbl_Macb2;
    private javax.swing.JLabel lbl_Macb3;
    private javax.swing.JLabel lbl_Macb4;
    private javax.swing.JLabel lbl_Macb5;
    private javax.swing.JLabel lbl_Macb6;
    private javax.swing.JLabel lbl_Macb7;
    private javax.swing.JPanel pnl_Display;
    private javax.swing.JSeparator spr_Hide;
    private javax.swing.JSeparator spr_Hide10;
    private javax.swing.JSeparator spr_Hide5;
    private javax.swing.JSeparator spr_Hide6;
    private javax.swing.JSeparator spr_Hide7;
    private javax.swing.JSeparator spr_Hide8;
    private javax.swing.JSeparator spr_Hide9;
    private javax.swing.JSeparator spr_Show;
    private javax.swing.JSeparator spr_Show10;
    private javax.swing.JSeparator spr_Show5;
    private javax.swing.JSeparator spr_Show6;
    private javax.swing.JSeparator spr_Show7;
    private javax.swing.JSeparator spr_Show8;
    private javax.swing.JSeparator spr_Show9;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_HoTen;
    private javax.swing.JTextField txt_Khoa;
    private javax.swing.JTextField txt_Lop;
    private javax.swing.JTextField txt_MSSV;
    private javax.swing.JTextField txt_MaRFID;
    private javax.swing.JTextField txt_Nganh;
    private javax.swing.JTextField txt_NienKhoa;
    private javax.swing.JTextField txt_temp;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btn_Luu)) {
            if (btn_Luu.getText().equals(DataString.NO_CHANGED)) {
                TransitionPane.closeDialogBox();
            }
            if (btn_Luu.getText().equals(DataString.INSERTED)) {
                addSinhVien();
            }
            if (btn_Luu.getText().equals(DataString.UPDATED)) {
                updateSinhVien();
            }
        } else if (source.equals(btn_QuetThe)) {
            quetThe();
        }
    }

    private void addSinhVien() {
        checkInput();
        if (checkSave()) {
            DefaultTableModel tableModel = VIEW.getSinhVienList().getTableModel();
            SinhVien_Model sinhvien = new SinhVien_Model(
                    txt_MSSV.getText().trim(),
                    txt_HoTen.getText().trim(),
                    txt_Email.getText().trim(),
                    String.valueOf(cBox_Lop.getSelectedItem()),
                    String.valueOf(cBox_Nganh.getSelectedItem()),
                    String.valueOf(cBox_Khoa.getSelectedItem()),
                    cBox_NienKhoa.getSelectedItem().toString().trim(),
                    txt_MaRFID.getText().trim()
            );
            SinhVien_List.getListAdd_temp().add(sinhvien);
            tableModel.insertRow(0, sinhvien.getObject());
            VIEW.getSinhVienList().getPaneTable().initFilterAndButtons();
            VIEW.setSave(true);
            VIEW.getCollapse().setCollapsed(false);
            TransitionPane.closeDialogBox();
        }
    }

    private void updateSinhVien() {
        row = VIEW.getSinhVienList().getTable().getSelectedRow();
        checkInput();
        if (checkSave()) {
            SinhVien_Model sv = new SinhVien_Model(
                    txt_MSSV.getText().trim(),
                    txt_HoTen.getText().trim(),
                    txt_Email.getText().trim(),
                    String.valueOf(cBox_Lop.getSelectedItem()),
                    String.valueOf(cBox_Nganh.getSelectedItem()),
                    String.valueOf(cBox_Khoa.getSelectedItem()),
                    cBox_NienKhoa.getSelectedItem().toString().trim(),
                    txt_MaRFID.getText().trim()
            );
            SinhVien_List.getListEdit_temp().add(sv);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getMaSV(), row, 1);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getTen(), row, 2);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getEmail(), row, 3);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getLop(), row, 4);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getNganh(), row, 5);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getKhoa(), row, 6);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getNienKhoa(), row, 7);
            VIEW.getSinhVienList().getPaneTable().getCustomTableModel().setValueAt(sv.getMaRFID(), row, 8);
            TransitionPane.closeDialogBox();
            VIEW.setSave(true);
        }
    }

    private void quetThe() {
        btn_QuetThe.setIcon(Resources.waiting_Gif);
        btn_QuetThe.setOpaque(false);
        btn_QuetThe.setText(" Đang đợi thẻ ..");
        btn_QuetThe.setForeground(Color.decode("#2e3233"));
        txt_MaRFID.setText("");
        txt_temp.setText("");
        txt_temp.requestFocus();
        txt_MaRFID.setForeground(Color.decode("#2e3233"));
    }

    public void setVisbleWhenDiemDanh(boolean b) {
        jPanel1.setVisible(b);
        jPanel4.setVisible(b);
        jPanel5.setVisible(b);
        jPanel6.setVisible(b);
        jPanel7.setVisible(b);
        jPanel9.setVisible(b);
        jPanel10.setVisible(b);
        txt_MSSV.setBackground(Color.white);
        txt_MSSV.setOpaque(b);
        txt_HoTen.setBackground(Color.white);
        txt_HoTen.setOpaque(b);
    }
}
