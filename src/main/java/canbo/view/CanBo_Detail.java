/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canbo.view;

import administrator.settings.Config;
import app.view.App_View;
import canbo.model.CanBo_Model;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import login.Login_View;
import other.custom.Check;
import other.custom.CustomBalloonTip;
import other.custom.DataString;
import other.custom.GradientButton;
import other.custom.TransitionPane;
import other.table.CustomTable.MyCustomTableModel;
import resources.Resources;

/**
 *
 * @author chuna
 */
public class CanBo_Detail extends javax.swing.JPanel implements ActionListener {

    private static CanBo_View VIEW = CanBo_View.getView();

    private boolean edit;
    private GradientButton btn_Luu;
    private CanBo_Model canBo_Model;
    private int row;

    private CustomBalloonTip[] toolTips;
    private List<JComponent> asListAll;
    private List<JTextField> asListText;
    private List<JComboBox> asListCombox;

    public CanBo_Detail() {
        initComponents();
        createUI();
    }

    private void createUI() {
        asListAll = Arrays.asList(txt_MSCB, txt_HoTen, txt_Email, cbx_BoMon, cbx_Khoa, txt_MaRFID);
        asListText = Arrays.asList(txt_MSCB, txt_HoTen, txt_Email, txt_MaRFID);
        asListCombox = Arrays.asList(cbx_BoMon, cbx_Khoa);
        toolTips = new CustomBalloonTip[6];
        toolTips[0] = new CustomBalloonTip(txt_MSCB, new JLabel());
        toolTips[0].setVisible(false);
        toolTips[1] = new CustomBalloonTip(txt_HoTen, new JLabel());
        toolTips[1].setVisible(false);
        toolTips[2] = new CustomBalloonTip(txt_Email, new JLabel());
        toolTips[2].setVisible(false);
        toolTips[3] = new CustomBalloonTip(cbx_BoMon, new JLabel());
        toolTips[3].setVisible(false);
        toolTips[4] = new CustomBalloonTip(cbx_Khoa, new JLabel());
        toolTips[4].setVisible(false);
        toolTips[5] = new CustomBalloonTip(txt_MaRFID, new JLabel());
        toolTips[5].setVisible(false);

        btn_Luu = new GradientButton(Color.decode("#457fca"), Color.decode("#5691c8"), 0, "Đóng");
        btn_Luu.setFont(new Font("Arial", 0, 16));
        btn_Luu.setPreferredSize(new Dimension(230, 50));
        jPanel1.add(btn_Luu);

        btn_QuetThe.addActionListener(this);
        btn_Luu.addActionListener(this);

        cbx_BoMon.setPreferredSize(new Dimension(320, 32));
        cbx_Khoa.setPreferredSize(new Dimension(320, 32));
        cbx_BoMon.removeAllItems();
        cbx_Khoa.removeAllItems();

        String[] boMons = App_View.getController().arrayList2String(App_View.getController().getBoMon());
        cbx_BoMon.addItem("Chọn bộ môn");
        for (String boMon : boMons) {
            cbx_BoMon.addItem(boMon);
        }
        String[] khoas = App_View.getController().arrayList2String(App_View.getController().getKhoa());
        cbx_Khoa.addItem("Chọn khoa");
        for (String khoa : khoas) {
            cbx_Khoa.addItem(khoa);
        }
        setTextComponent();
        setUpQuetThe();
        txt_BoMon.setBackground(Color.white);
        txt_Khoa.setBackground(Color.white);
        txt_BoMon.setOpaque(true);
        txt_Khoa.setOpaque(true);
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
        txt_temp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txt_temp.getText().length() > 0) {
//                    btn_QuetThe.setIcon(Resources.waiting_Gif);
//                    btn_QuetThe.setOpaque(false);
//                    btn_QuetThe.setText(" Đang đợi thẻ ..");
//                    btn_QuetThe.setForeground(Color.decode("#2e3233"));
                }
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
        for (int i = 0; i < asListText.size(); i++) {
            if (!asListText.get(i).equals(txt_MaRFID)) {
                switch (((JTextField) asListAll.get(i)).getText()) {
                    case "Mã số cán bộ": {
                        toolTips[i].showNotify("Không được để trống", isEdit());
                        break;
                    }
                    case "Họ tên cán bộ": {
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
        if (txt_MSCB.getText().substring(0, 1).equals("B")) {
            toolTips[0].showNotify("Mã không hợp lệ", isEdit());
        }
        if (!txt_MSCB.getText().isEmpty() && !txt_MSCB.getText().equals("Mã số cán bộ")) {
            if (!Check.unAccent(txt_MSCB.getText())) {
                toolTips[0].showNotify("Mã số cán bộ không chứa ký tự có dấu", isEdit());
            }
        }
        String[] split = txt_HoTen.getText().split(" ");
        if (split.length < 2) {
            toolTips[1].showNotify("Phải nhập gồm họ và tên", isEdit());
        }

        if (cbx_BoMon.getSelectedIndex() == 0) {
            toolTips[3].showNotify("Chưa chọn bộ môn", isEdit());
        }

        if (cbx_Khoa.getSelectedIndex() == 0) {
            toolTips[4].showNotify("Chưa chọn khoa", isEdit());

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
                toolTips[5].showNotify("Mã RFID sai định dạng!", isEdit());
                btn_QuetThe.setText("Quét lại");
            }
        } else {
            txt_MaRFID.setText("Chưa có");
        }
    }

    private void setTextComponent() {
        asListText.forEach((txt) -> {
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
                    if (e.getSource().equals(txt_MSCB)) {
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
                public void focusLost(FocusEvent e) {
                    Object object = e.getSource();
                    if (txt.getText().equals("") || txt.getText().equals(temp)) {
                        txt.setText(temp);
                        txt.setForeground(Color.decode("#999999"));
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                    if (object.equals(txt_MSCB)) {
                        spr_Show.setForeground(Config.focusLost);
                        if (txt_MSCB.getText().equals("") || txt_MSCB.getText().equals("Mã số cán bộ")) {
                            toolTips[0].showNotify("Không được để trống", isEdit());
                        } else if (txt_MSCB.getText().substring(0, 1).equals("B")) {
                            toolTips[0].showNotify("Mã không hợp lệ", isEdit());
                        }
                        if (!txt_MSCB.getText().isEmpty() && !txt_MSCB.getText().equals("Mã số cán bộ")) {
                            if (!Check.unAccent(txt_MSCB.getText())) {
                                toolTips[0].showNotify("Mã số cán bộ không chứa ký tự có dấu", isEdit());
                            }
                        }
                    }
                    if (object.equals(txt_HoTen)) {
                        spr_Show5.setForeground(Config.focusLost);
                        if (txt_HoTen.getText().equals("") || txt_HoTen.getText().equals("Họ tên cán bộ")) {
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
                    if (e.getSource().equals(txt_MSCB)) {
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
            });

        });
        for (int i = 0; i < asListAll.size() - 1; i++) {
            asListAll.get(i).setNextFocusableComponent(asListAll.get(i + 1));
        }
    }

    public void setAccess(boolean edit, String type) {
        setEditor(edit);
        setEdit(edit);
        btn_Luu.setText(type.isEmpty() ? DataString.NO_CHANGED : type);
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    private void setEditor(boolean isEdit) {
        asListText.forEach((text) -> {
            JTextField txt = (JTextField) text;
            if (!txt.equals(txt_MaRFID)) {
                txt.setEditable(isEdit);
                txt.setFocusable(isEdit);
            }
        });
        if (isEdit) {
            jPanel5.remove(txt_BoMon);
            jPanel5.remove(spr_Show7);
            jPanel5.add(cbx_BoMon, 1);
            jPanel6.remove(1);
            jPanel6.remove(spr_Show8);
            jPanel6.add(cbx_Khoa, 1);
        } else {
            jPanel5.remove(1);
            jPanel5.add(txt_BoMon, 1);
            jPanel5.add(spr_Show7);
            jPanel6.remove(1);
            jPanel6.add(txt_Khoa, 1);
            jPanel6.add(spr_Show8);
        }

        boolean update = txt_MaRFID.getText().equals("Mã RFID")
                || txt_MaRFID.getText().isEmpty()
                || txt_MaRFID.getText().equals("Chưa có");
        btn_QuetThe.setVisible(isEdit);
        btn_QuetThe.setText(update ? "Quét mã RFID" : "Cập nhật mã RFID");
        if (isEdit) {
            btn_Luu.setText("Thêm");
        } else {
            btn_Luu.setText("Đóng");
            asListAll.forEach((txt) -> {
                txt.setForeground(Color.decode("#2e3233"));
            });
        }

    }

    private void clearText() {
        txt_temp.setText("");
        asListText.forEach((text) -> {
            text.setText("");
        });
        asListCombox.forEach((t) -> {
            t.setSelectedIndex(0);
        });
    }

    public CanBo_Model getCanBo_Model() {
        return canBo_Model;
    }

    public void setCanBo_Model(CanBo_Model canBo_Model) {
        this.canBo_Model = canBo_Model;
    }

    public void setText() {
        clearText();
        txt_MSCB.setText(canBo_Model.getMaCB());
        txt_HoTen.setText(canBo_Model.getTen());
        txt_Email.setText(canBo_Model.getEmail());
        txt_BoMon.setText(canBo_Model.getBoMon());
        cbx_BoMon.setSelectedItem(canBo_Model.getBoMon());
        txt_Khoa.setText(canBo_Model.getKhoa());
        cbx_Khoa.setSelectedItem(canBo_Model.getKhoa());
        txt_MaRFID.setText(canBo_Model.getMaRFID());
    }

    //<editor-fold defaultstate="collapsed" desc=" Get and set ">
    public void setBtn_Luu(GradientButton btn_Luu) {
        this.btn_Luu = btn_Luu;
    }

    public GradientButton get_btnLuu() {
        return btn_Luu;
    }

    //</editor-fold>
    @SuppressWarnings(value = "unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbx_BoMon = new javax.swing.JComboBox<>();
        cbx_Khoa = new javax.swing.JComboBox<>();
        pnl_Display = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_Macb = new javax.swing.JLabel();
        txt_MSCB = new javax.swing.JTextField();
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
        txt_BoMon = new javax.swing.JTextField();
        spr_Hide7 = new javax.swing.JSeparator();
        spr_Show7 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        lbl_Macb4 = new javax.swing.JLabel();
        txt_Khoa = new javax.swing.JTextField();
        spr_Hide8 = new javax.swing.JSeparator();
        spr_Show8 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        lbl_Macb5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txt_MaRFID = new javax.swing.JTextField();
        txt_temp = new javax.swing.JTextField();
        btn_QuetThe = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        cbx_BoMon.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbx_BoMon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_BoMon.setMaximumSize(new java.awt.Dimension(320, 32));
        cbx_BoMon.setPreferredSize(new java.awt.Dimension(320, 32));
        cbx_BoMon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_BoMonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbx_BoMonFocusLost(evt);
            }
        });

        cbx_Khoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbx_Khoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_Khoa.setMaximumSize(new java.awt.Dimension(320, 32));
        cbx_Khoa.setPreferredSize(new java.awt.Dimension(320, 32));
        cbx_Khoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_KhoaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbx_KhoaFocusLost(evt);
            }
        });

        setBackground(new java.awt.Color(255, 255, 255));

        pnl_Display.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 30, 60));
        pnl_Display.setOpaque(false);
        pnl_Display.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 28));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setText("MSCB:");
        lbl_Macb.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel2.add(lbl_Macb);

        txt_MSCB.setEditable(false);
        txt_MSCB.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_MSCB.setForeground(new java.awt.Color(153, 153, 153));
        txt_MSCB.setText("Mã số cán bộ");
        txt_MSCB.setBorder(null);
        txt_MSCB.setOpaque(true);
        txt_MSCB.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel2.add(txt_MSCB);

        spr_Hide.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel2.add(spr_Hide);

        spr_Show.setName("txt_MSCB"); // NOI18N
        spr_Show.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel2.add(spr_Show);

        pnl_Display.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
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
        txt_HoTen.setText("Họ tên cán bộ");
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
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
        txt_Email.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel4.add(txt_Email);

        spr_Hide6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide6.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel4.add(spr_Hide6);

        spr_Show6.setName("txt_Email"); // NOI18N
        spr_Show6.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel4.add(spr_Show6);

        pnl_Display.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setText("Bộ môn:");
        lbl_Macb3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel5.add(lbl_Macb3);

        txt_BoMon.setEditable(false);
        txt_BoMon.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_BoMon.setForeground(new java.awt.Color(46, 50, 51));
        txt_BoMon.setText("Tên bộ môn");
        txt_BoMon.setBorder(null);
        txt_BoMon.setOpaque(true);
        txt_BoMon.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel5.add(txt_BoMon);

        spr_Hide7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide7.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel5.add(spr_Hide7);

        spr_Show7.setName("txt_BoMon"); // NOI18N
        spr_Show7.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel5.add(spr_Show7);

        pnl_Display.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setText("Khoa:");
        lbl_Macb4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel6.add(lbl_Macb4);

        txt_Khoa.setEditable(false);
        txt_Khoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Khoa.setForeground(new java.awt.Color(46, 50, 51));
        txt_Khoa.setText("Tên khoa");
        txt_Khoa.setBorder(null);
        txt_Khoa.setOpaque(true);
        txt_Khoa.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel6.add(txt_Khoa);

        spr_Hide8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide8.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel6.add(spr_Hide8);

        spr_Show8.setName("txt_Khoa"); // NOI18N
        spr_Show8.setPreferredSize(new java.awt.Dimension(320, 1));
        jPanel6.add(spr_Show8);

        pnl_Display.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb5.setText("Mã RFID:");
        lbl_Macb5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb5.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel7.add(lbl_Macb5);

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(320, 36));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 3));

        txt_MaRFID.setEditable(false);
        txt_MaRFID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_MaRFID.setForeground(new java.awt.Color(46, 50, 51));
        txt_MaRFID.setText("Mã RFID");
        txt_MaRFID.setBorder(null);
        txt_MaRFID.setPreferredSize(new java.awt.Dimension(160, 32));
        jPanel8.add(txt_MaRFID);

        txt_temp.setBackground(new java.awt.Color(240, 240, 240));
        txt_temp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_temp.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel8.add(txt_temp);

        btn_QuetThe.setBackground(new java.awt.Color(118, 175, 235));
        btn_QuetThe.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_QuetThe.setForeground(new java.awt.Color(255, 255, 255));
        btn_QuetThe.setText("Quét mã RFID");
        btn_QuetThe.setContentAreaFilled(false);
        btn_QuetThe.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_QuetThe.setIconTextGap(0);
        btn_QuetThe.setMargin(new java.awt.Insets(0, 5, 0, 5));
        btn_QuetThe.setMaximumSize(new java.awt.Dimension(100, 25));
        btn_QuetThe.setMinimumSize(new java.awt.Dimension(100, 25));
        btn_QuetThe.setOpaque(true);
        btn_QuetThe.setPreferredSize(new java.awt.Dimension(160, 32));
        jPanel8.add(btn_QuetThe);

        jPanel7.add(jPanel8);

        pnl_Display.add(jPanel7);

        jPanel1.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
            .addComponent(pnl_Display, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnl_Display, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbx_BoMonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_BoMonFocusGained
        toolTips[3].hideNotify();
    }//GEN-LAST:event_cbx_BoMonFocusGained

    private void cbx_KhoaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_KhoaFocusGained
        toolTips[4].hideNotify();
    }//GEN-LAST:event_cbx_KhoaFocusGained

    private void cbx_BoMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_BoMonFocusLost
        if (cbx_BoMon.getItemCount() > 0) {
            if (cbx_BoMon.getSelectedItem().equals("") || cbx_BoMon.getSelectedItem().equals("Chọn bộ môn")) {
                toolTips[3].showNotify("Chưa chọn bộ môn", isEdit());
            }
        }
    }//GEN-LAST:event_cbx_BoMonFocusLost

    private void cbx_KhoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_KhoaFocusLost
        if (cbx_Khoa.getItemCount() > 0) {
            if (cbx_Khoa.getSelectedItem().equals("") || cbx_Khoa.getSelectedItem().equals("Chọn khoa")) {
                toolTips[4].showNotify("Chưa chọn khoa", isEdit());
            }
        }
    }//GEN-LAST:event_cbx_KhoaFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_QuetThe;
    private javax.swing.JComboBox<String> cbx_BoMon;
    private javax.swing.JComboBox<String> cbx_Khoa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lbl_Macb;
    private javax.swing.JLabel lbl_Macb1;
    private javax.swing.JLabel lbl_Macb2;
    private javax.swing.JLabel lbl_Macb3;
    private javax.swing.JLabel lbl_Macb4;
    private javax.swing.JLabel lbl_Macb5;
    private javax.swing.JPanel pnl_Display;
    private javax.swing.JSeparator spr_Hide;
    private javax.swing.JSeparator spr_Hide5;
    private javax.swing.JSeparator spr_Hide6;
    private javax.swing.JSeparator spr_Hide7;
    private javax.swing.JSeparator spr_Hide8;
    private javax.swing.JSeparator spr_Show;
    private javax.swing.JSeparator spr_Show5;
    private javax.swing.JSeparator spr_Show6;
    private javax.swing.JSeparator spr_Show7;
    private javax.swing.JSeparator spr_Show8;
    private javax.swing.JTextField txt_BoMon;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_HoTen;
    private javax.swing.JTextField txt_Khoa;
    private javax.swing.JTextField txt_MSCB;
    private javax.swing.JTextField txt_MaRFID;
    private javax.swing.JTextField txt_temp;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_Luu)) {
            if (btn_Luu.getText().equals("Đóng")) {
                TransitionPane.closeDialogBox();
            }
            if (btn_Luu.getText().equals("Thêm")) {
                addCanBo();
            }
            if (btn_Luu.getText().equals("Cập nhật")) {
                updateCanBo();
            }
        }
        if (e.getSource().equals(btn_QuetThe)) {
            quetThe();
        }
    }

    private void addCanBo() {
        checkInput();
        if (checkSave()) {
            MyCustomTableModel tableModel = VIEW.getCanBoList().getTableModel();
            CanBo_Model canbo = new CanBo_Model(
                    txt_MSCB.getText().trim(),
                    txt_HoTen.getText().trim(),
                    txt_Email.getText().trim(),
                    String.valueOf(cbx_BoMon.getSelectedItem()),
                    String.valueOf(cbx_Khoa.getSelectedItem()),
                    txt_MaRFID.getText().trim()
            );
            CanBo_List.getListAdd_temp().add(canbo);
            tableModel.insertRow(0, canbo.getObject());
            Login_View.canBo_View.getCanBoList().getPaneTable().initFilterAndButtons();
            Login_View.canBo_View.setSave(true);
            TransitionPane.closeDialogBox();
            VIEW.getCollapse().setCollapsed(false);
        }
    }

    private void updateCanBo() {
        row = VIEW.getCanBoList().getTable().getSelectedRow();
        checkInput();
        if (checkSave()) {
            CanBo_Model cb = new CanBo_Model(
                    txt_MSCB.getText().trim(),
                    txt_HoTen.getText().trim(),
                    txt_Email.getText().trim(),
                    String.valueOf(cbx_BoMon.getSelectedItem()),
                    String.valueOf(cbx_Khoa.getSelectedItem()),
                    txt_MaRFID.getText().trim()
            );
            CanBo_List.getListEdit_temp().add(cb);
            VIEW.getCanBoList().getTableModel().setValueAt(cb.getMaCB(), row, 1);
            VIEW.getCanBoList().getTableModel().setValueAt(cb.getTen(), row, 2);
            VIEW.getCanBoList().getTableModel().setValueAt(cb.getEmail(), row, 3);
            VIEW.getCanBoList().getTableModel().setValueAt(cb.getBoMon(), row, 4);
            VIEW.getCanBoList().getTableModel().setValueAt(cb.getKhoa(), row, 5);
            VIEW.getCanBoList().getTableModel().setValueAt(cb.getMaRFID(), row, 6);
            Login_View.canBo_View.setSave(true);
            TransitionPane.closeDialogBox();
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

    }
}
