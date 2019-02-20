/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.view;

import sukien.model.SuKien_Model;
import administrator.settings.Config;
import other.custom.DataString;
import other.custom.FormatPattern;
import other.custom.Util;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import javax.swing.text.JTextComponent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import login.Login_View;
import other.custom.Check;
import other.custom.CustComp;
import other.custom.CustomBalloonTip;
import other.custom.TransitionPane;

/**
 *
 * @author chuna
 */
public class SuKien_DetailSK extends javax.swing.JPanel implements FocusListener {

    private static SuKien_View VIEW = SuKien_View.getView();
    private SuKien_Model suKien_Model;
    boolean diemDanh;
    private int row;
    private boolean b;
    private static String mask;
    private SuKien_DSThamGia dSThamGia;
    private CustomBalloonTip[] tipWarning;
    private boolean edit;

    public SuKien_DetailSK() {
        suKien_Model = new SuKien_Model();
        initComponents();
        createUI();
    }

    public SuKien_DetailSK(SuKien_Model event_Model) {
        this.suKien_Model = event_Model;
        initComponents();
        createUI();
    }

    private void createUI() {
        tipWarning = new CustomBalloonTip[5];

        tipWarning[0] = new CustomBalloonTip(txt_MSK, new JLabel());
        tipWarning[0].setVisible(false);
        tipWarning[1] = new CustomBalloonTip(txt_Ten, new JLabel());
        tipWarning[1].setVisible(false);
        tipWarning[2] = new CustomBalloonTip(dPicker_Start, new JLabel());
        tipWarning[2].setVisible(false);
        tipWarning[3] = new CustomBalloonTip(spn_Begin, new JLabel());
        tipWarning[3].setVisible(false);
        tipWarning[4] = new CustomBalloonTip(spn_End, new JLabel());
        tipWarning[4].setVisible(false);
        txt_MSK.addFocusListener(this);
        txt_Ten.addFocusListener(this);
        styleSpinner();
        setInput();
    }

    public void showPanel() {
        loadData();
    }

    public JPanel getPnl_Display() {
        return pnl_Display;
    }

    public void setPnl_Display(JPanel pnl_Display) {
        this.pnl_Display = pnl_Display;
    }

    private void loadData() {
        setData();
        dSThamGia = new SuKien_DSThamGia(mask);
        pnl_Table.removeAll();
        pnl_Table.add(dSThamGia);
        pnl_Table.validate();
        pnl_Table.repaint();
    }

    private void styleSpinner() {
        spn_Begin.setBorder(new LineBorder(Color.decode("#e1e1e1")));
        spn_End.setBorder(new LineBorder(Color.decode("#e1e1e1")));
        dPicker_Start.setFormats(new String[]{"EEEE, dd/MM/yyyy"});
        FormatPattern.formatSpinner(spn_Begin, "HH:mm");
        FormatPattern.formatSpinner(spn_End, "HH:mm");
        dPicker_Start.setDate(new java.util.Date());
        dPicker_Start.getEditor().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateInString = dPicker_Start.getEditor().getText();
                try {
                    java.util.Date date = formatter.parse(dateInString);
                    java.util.Date date1 = new java.util.Date(date.getYear(), date.getMonth(), date.getDate());
                    dPicker_Start.setDate(date1);
                } catch (ParseException pe) {
                }
            }
        });
        txt_MSK.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    txt_Ten.requestFocus();
                }
            }

        });
        txt_Ten.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    dPicker_Start.requestFocus();
                }
            }

        });
        dPicker_Start.getEditor().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    spn_Begin.requestFocus();
                }
            }

        });
        spn_Begin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    spn_End.requestFocus();
                }
            }
        });
        spn_End.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    btn_OK.requestFocus();
                }
            }
        });
        Time time = new Time(System.currentTimeMillis());
        spn_Begin.setValue(time);
        spn_End.setValue(Util.plusTime(time, new Time(2, 0, 0)));
    }

    private void setData() {
        txt_MSK.setText(suKien_Model.getMaSK());
        txt_Ten.setText(suKien_Model.getTenSK());
        txt_NgayDienRa.setText(Util.formatDate(suKien_Model.getNgayDienRa()));
        txt_GioBatDau.setText(suKien_Model.getGioVao().toString());
        txt_GioKetThuc.setText(suKien_Model.getGioRa().toString());
        dPicker_Start.setDate(suKien_Model.getNgayDienRa());
        mask = suKien_Model.getMaSK();
    }

    public void setDataDefault() {
        txt_MSK.setForeground(Color.decode("#999999"));
        txt_Ten.setForeground(Color.decode("#999999"));
        styleSpinner();
    }

    public SuKien_Model getData() {
        String gioVao, gioRa;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        gioVao = String.valueOf(format.format(spn_Begin.getValue()));
        gioRa = String.valueOf(format.format(spn_End.getValue()));
        SuKien_Model sk_model = new SuKien_Model(
                txt_MSK.getText().trim(),
                txt_Ten.getText().trim(),
                new Date(dPicker_Start.getDate().getTime()),
                Time.valueOf(gioVao),
                Time.valueOf(gioRa)
        );
        return sk_model;
    }

    private boolean checkSave() {
        int count = 0;
        for (CustomBalloonTip balloonTip : tipWarning) {
            System.out.println(balloonTip.isShowNotify());
            if (!balloonTip.isShowNotify()) {
                count++;
            }
        }
        return count == tipWarning.length;
    }

    public void checkInput() {
        String gioVao, gioRa;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        gioVao = String.valueOf(format.format(spn_Begin.getValue()));
        gioRa = String.valueOf(format.format(spn_End.getValue()));
        if (txt_MSK.getText().isEmpty() || txt_MSK.getText().equals("Mã sự kiện")) {
            tipWarning[0].showNotify("Không được để trống trường này", edit, 3);
        } else {
            if (!Check.unAccent(txt_MSK.getText())) {
                tipWarning[0].showNotify("Mã sự kiện không chứa ký tự có dấu", edit);
            }
        }
        if (txt_MSK.getText().trim().length() > 14) {
            tipWarning[0].showNotify("Tối đa 15 ký tự", edit);
        }

        if (txt_Ten.getText().isEmpty() || txt_Ten.getText().equals("Tên sự kiện")) {
            tipWarning[1].showNotify("Không được để trống trường này", edit, 3);
        }
        if (Util.compare(new Date(dPicker_Start.getDate().getTime()), new Date(System.currentTimeMillis())) < 0) {
            tipWarning[2].showNotify("Không thể chọn ngày đã diễn ra", edit, 3);
        } else {
            tipWarning[2].hideNotify();
        }
        if (spn_Begin.getValue().equals("")) {
            tipWarning[3].showNotify("Không được để trống trường này", edit);
        } else if (Util.compare(Time.valueOf(gioVao), new Time(System.currentTimeMillis())) < 0) {
            tipWarning[3].showNotify("Không thể chọn thời gian đã qua", edit, 3);
        } else {
            tipWarning[3].hideNotify();
        }
        if (spn_End.getValue().equals("")) {
            tipWarning[4].showNotify("Không được để trống trường này", edit, 3);
        } else if (Util.compare(Time.valueOf(gioRa), Time.valueOf(gioVao)) < 0) {
            tipWarning[4].showNotify("Không thể chọn thời gian ra sớm hơn thời gian vào", edit, 3);
        } else {
            tipWarning[4].hideNotify();
        }

    }

    private void setInput() {
        ArrayList<JTextComponent> list = CustComp.getTextComponents(pnl_Display);
        list.forEach((txt) -> {
            String temp = txt.getText();
            txt.setBackground(Color.WHITE);
            txt.setOpaque(true);
            txt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (txt.getText().trim().isEmpty() || txt.getText().equals(temp)) {
                        txt.setText("");
                        txt.setForeground(Color.decode("#9999999"));
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
            txt.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {
                        txt.setCaretPosition(0);
                        txt.setForeground(Color.decode("#9999999"));
                    } else {
                        txt.setForeground(Color.decode("#2e3233"));
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (txt.getText().isEmpty() || txt.getText().equals(temp)) {

                        txt.setText(temp);
                        txt.setForeground(Color.decode("#999999"));
                    }
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
    }

    public void setAccess(boolean edit, String type) {
        setEditor(edit, type);
    }

    private void setEditor(boolean edit, String type) {
        this.edit = edit;
        ArrayList<JTextComponent> list = CustComp.getTextComponents(pnl_Display);
        list.forEach((component) -> {
            if (!component.equals(txt_NgayDienRa)
                    && !component.equals(txt_GioBatDau)
                    && !component.equals(txt_GioKetThuc)) {
                component.setEditable(edit);
            }
        });
        CustComp.getTextComponents(this).forEach((t) -> {
            t.addFocusListener(this);
        });
        if (edit) {
            pnl_NgayDienRa.remove(txt_NgayDienRa);
            pnl_NgayDienRa.remove(spr_Show6);
            pnl_NgayDienRa.add(dPicker_Start, 1);
            pnl_GioBatDau.remove(1);
            pnl_GioBatDau.remove(spr_Show7);
            pnl_GioBatDau.add(spn_Begin, 1);
            pnl_GioKetThuc.remove(1);
            pnl_GioKetThuc.remove(spr_Show8);
            pnl_GioKetThuc.add(spn_End, 1);

        } else {
            pnl_NgayDienRa.remove(1);
            pnl_NgayDienRa.add(txt_NgayDienRa, 1);
            pnl_NgayDienRa.add(spr_Show6);
            pnl_GioBatDau.remove(1);
            pnl_GioBatDau.add(txt_GioBatDau, 1);
            pnl_GioBatDau.add(spr_Show7);
            pnl_GioKetThuc.remove(1);
            pnl_GioKetThuc.add(txt_GioKetThuc, 1);
            pnl_GioKetThuc.add(spr_Show8);
        }
        pnl_Display.add(btn_OK);
        btn_OK.setText(type);
        btn_OK.setVisible(edit);
        if (type.equals(DataString.INSERTED)) {
            txt_MSK.setText("Mã sự kiện");
            txt_Ten.setText("Tên sự kiện");
        }
        list.forEach((txt) -> {
            txt.setForeground(Color.decode("#2e3233"));
        });
        if (type.equals(DataString.UPDATED)) {
            txt_MSK.setText(suKien_Model.getMaSK());
            txt_Ten.setText(suKien_Model.getTenSK());
            dPicker_Start.setDate(suKien_Model.getNgayDienRa());
            spn_Begin.setValue(suKien_Model.getGioVao());
            spn_End.setValue(suKien_Model.getGioRa());
        }
    }

    public static String getMask() {
        return mask;
    }

    public static void setMask(String mask) {
        SuKien_DetailSK.mask = mask;
    }

    public SuKien_Model getSuKien_Model() {
        return suKien_Model;
    }

    public void setSuKien_Model(SuKien_Model suKien_Model) {
        this.suKien_Model = suKien_Model;
        loadData();
    }

    public JPanel getPnl_Table() {
        return pnl_Table;
    }

    public void setPnl_Table(JPanel pnl_Table) {
        this.pnl_Table = pnl_Table;
    }

    public JButton getBtn_OK() {
        return btn_OK;
    }

    public void setBtn_OK(JButton btn_OK) {
        this.btn_OK = btn_OK;
    }

    public SuKien_DSThamGia getdSThamGia() {
        return dSThamGia;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dPicker_Start = new org.jdesktop.swingx.JXDatePicker();
        spn_Begin = new javax.swing.JSpinner();
        spn_End = new javax.swing.JSpinner();
        btn_OK = new javax.swing.JButton();
        pnl_Display = new javax.swing.JPanel();
        pnl_MaSK = new javax.swing.JPanel();
        lbl_Macb = new javax.swing.JLabel();
        txt_MSK = new javax.swing.JTextField();
        spr_Hide = new javax.swing.JSeparator();
        spr_Show = new javax.swing.JSeparator();
        pnl_TenSK = new javax.swing.JPanel();
        lbl_Macb1 = new javax.swing.JLabel();
        txt_Ten = new javax.swing.JTextField();
        spr_Hide5 = new javax.swing.JSeparator();
        spr_Show5 = new javax.swing.JSeparator();
        pnl_NgayDienRa = new javax.swing.JPanel();
        lbl_Macb2 = new javax.swing.JLabel();
        txt_NgayDienRa = new javax.swing.JTextField();
        spr_Hide6 = new javax.swing.JSeparator();
        spr_Show6 = new javax.swing.JSeparator();
        pnl_GioBatDau = new javax.swing.JPanel();
        lbl_Macb3 = new javax.swing.JLabel();
        txt_GioBatDau = new javax.swing.JTextField();
        spr_Hide7 = new javax.swing.JSeparator();
        spr_Show7 = new javax.swing.JSeparator();
        pnl_GioKetThuc = new javax.swing.JPanel();
        lbl_Macb4 = new javax.swing.JLabel();
        txt_GioKetThuc = new javax.swing.JTextField();
        spr_Hide8 = new javax.swing.JSeparator();
        spr_Show8 = new javax.swing.JSeparator();
        pnl_GhiChu = new javax.swing.JPanel();
        pnl_Table = new javax.swing.JPanel();

        dPicker_Start.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        dPicker_Start.setPreferredSize(new java.awt.Dimension(200, 32));

        spn_Begin.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        spn_Begin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        spn_Begin.setMinimumSize(new java.awt.Dimension(120, 32));
        spn_Begin.setPreferredSize(new java.awt.Dimension(120, 32));

        spn_End.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        spn_End.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        spn_End.setMinimumSize(new java.awt.Dimension(120, 32));
        spn_End.setPreferredSize(new java.awt.Dimension(120, 32));

        btn_OK.setBackground(new java.awt.Color(102, 153, 255));
        btn_OK.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_OK.setForeground(new java.awt.Color(255, 255, 255));
        btn_OK.setText("Lưu lại");
        btn_OK.setBorder(null);
        btn_OK.setContentAreaFilled(false);
        btn_OK.setOpaque(true);
        btn_OK.setPreferredSize(new java.awt.Dimension(80, 36));
        btn_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OKActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(900, 0));

        pnl_Display.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Display.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 20);
        flowLayout1.setAlignOnBaseline(true);
        pnl_Display.setLayout(flowLayout1);

        pnl_MaSK.setMinimumSize(new java.awt.Dimension(100, 36));
        pnl_MaSK.setOpaque(false);
        pnl_MaSK.setPreferredSize(new java.awt.Dimension(400, 36));
        pnl_MaSK.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setText("Mã SK:");
        lbl_Macb.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setPreferredSize(new java.awt.Dimension(100, 32));
        pnl_MaSK.add(lbl_Macb);

        txt_MSK.setEditable(false);
        txt_MSK.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_MSK.setForeground(new java.awt.Color(153, 153, 153));
        txt_MSK.setText("Mã sự kiện");
        txt_MSK.setBorder(null);
        txt_MSK.setPreferredSize(new java.awt.Dimension(300, 32));
        txt_MSK.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_MSKFocusGained(evt);
            }
        });
        txt_MSK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MSKActionPerformed(evt);
            }
        });
        pnl_MaSK.add(txt_MSK);

        spr_Hide.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide.setPreferredSize(new java.awt.Dimension(100, 0));
        pnl_MaSK.add(spr_Hide);

        spr_Show.setName("txt_MSCB"); // NOI18N
        spr_Show.setPreferredSize(new java.awt.Dimension(300, 1));
        pnl_MaSK.add(spr_Show);

        pnl_Display.add(pnl_MaSK);

        pnl_TenSK.setMinimumSize(new java.awt.Dimension(100, 36));
        pnl_TenSK.setOpaque(false);
        pnl_TenSK.setPreferredSize(new java.awt.Dimension(400, 36));
        pnl_TenSK.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb1.setText("Tên SK:");
        lbl_Macb1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb1.setPreferredSize(new java.awt.Dimension(100, 32));
        pnl_TenSK.add(lbl_Macb1);

        txt_Ten.setEditable(false);
        txt_Ten.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Ten.setForeground(new java.awt.Color(153, 153, 153));
        txt_Ten.setText("Tên sự kiện");
        txt_Ten.setBorder(null);
        txt_Ten.setPreferredSize(new java.awt.Dimension(300, 32));
        pnl_TenSK.add(txt_Ten);

        spr_Hide5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide5.setPreferredSize(new java.awt.Dimension(100, 0));
        pnl_TenSK.add(spr_Hide5);

        spr_Show5.setName("txt_HoTen"); // NOI18N
        spr_Show5.setPreferredSize(new java.awt.Dimension(300, 1));
        pnl_TenSK.add(spr_Show5);

        pnl_Display.add(pnl_TenSK);

        pnl_NgayDienRa.setMinimumSize(new java.awt.Dimension(100, 36));
        pnl_NgayDienRa.setOpaque(false);
        pnl_NgayDienRa.setPreferredSize(new java.awt.Dimension(400, 36));
        pnl_NgayDienRa.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb2.setText("Ngày diễn ra:");
        lbl_Macb2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb2.setPreferredSize(new java.awt.Dimension(100, 32));
        pnl_NgayDienRa.add(lbl_Macb2);

        txt_NgayDienRa.setEditable(false);
        txt_NgayDienRa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_NgayDienRa.setBorder(null);
        txt_NgayDienRa.setPreferredSize(new java.awt.Dimension(300, 32));
        pnl_NgayDienRa.add(txt_NgayDienRa);

        spr_Hide6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide6.setPreferredSize(new java.awt.Dimension(100, 0));
        pnl_NgayDienRa.add(spr_Hide6);

        spr_Show6.setName("txt_Email"); // NOI18N
        spr_Show6.setPreferredSize(new java.awt.Dimension(300, 1));
        pnl_NgayDienRa.add(spr_Show6);

        pnl_Display.add(pnl_NgayDienRa);

        pnl_GioBatDau.setMinimumSize(new java.awt.Dimension(100, 36));
        pnl_GioBatDau.setOpaque(false);
        pnl_GioBatDau.setPreferredSize(new java.awt.Dimension(400, 36));
        pnl_GioBatDau.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setText("Giờ bắt đầu");
        lbl_Macb3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setPreferredSize(new java.awt.Dimension(100, 32));
        pnl_GioBatDau.add(lbl_Macb3);

        txt_GioBatDau.setEditable(false);
        txt_GioBatDau.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_GioBatDau.setBorder(null);
        txt_GioBatDau.setPreferredSize(new java.awt.Dimension(300, 32));
        pnl_GioBatDau.add(txt_GioBatDau);

        spr_Hide7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide7.setPreferredSize(new java.awt.Dimension(100, 0));
        pnl_GioBatDau.add(spr_Hide7);

        spr_Show7.setName("txt_BoMon"); // NOI18N
        spr_Show7.setPreferredSize(new java.awt.Dimension(300, 1));
        pnl_GioBatDau.add(spr_Show7);

        pnl_Display.add(pnl_GioBatDau);

        pnl_GioKetThuc.setMinimumSize(new java.awt.Dimension(100, 36));
        pnl_GioKetThuc.setOpaque(false);
        pnl_GioKetThuc.setPreferredSize(new java.awt.Dimension(400, 36));
        pnl_GioKetThuc.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setText("Giờ kết thúc:");
        lbl_Macb4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setPreferredSize(new java.awt.Dimension(100, 32));
        pnl_GioKetThuc.add(lbl_Macb4);

        txt_GioKetThuc.setEditable(false);
        txt_GioKetThuc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_GioKetThuc.setBorder(null);
        txt_GioKetThuc.setPreferredSize(new java.awt.Dimension(300, 32));
        pnl_GioKetThuc.add(txt_GioKetThuc);

        spr_Hide8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 100, 0, 0));
        spr_Hide8.setPreferredSize(new java.awt.Dimension(100, 0));
        pnl_GioKetThuc.add(spr_Hide8);

        spr_Show8.setName("txt_Khoa"); // NOI18N
        spr_Show8.setPreferredSize(new java.awt.Dimension(300, 1));
        pnl_GioKetThuc.add(spr_Show8);

        pnl_Display.add(pnl_GioKetThuc);

        pnl_GhiChu.setMinimumSize(new java.awt.Dimension(100, 36));
        pnl_GhiChu.setOpaque(false);
        pnl_GhiChu.setPreferredSize(new java.awt.Dimension(400, 36));
        pnl_GhiChu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        pnl_Display.add(pnl_GhiChu);

        pnl_Table.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Table.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        pnl_Table.setOpaque(false);
        pnl_Table.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .addComponent(pnl_Display, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_Display, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_Table, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OKActionPerformed
        TransitionPane.setAlwaysOnTop(false);
        if (btn_OK.getText().equals(DataString.NO_CHANGED)) {
            TransitionPane.closeDialogBox();
        }
        if (btn_OK.getText().equals(DataString.INSERTED)) {
            checkInput();
            if (checkSave()) {
                DefaultTableModel tableModel = (DefaultTableModel) VIEW.getSuKienList().getTableModel_SuKien();
                SuKien_Model sk_Model = getData();
                tableModel.addRow(sk_Model.getObject());
                SuKien_ListSK.getListAdd_temp().add(sk_Model);
                Login_View.suKien_View.setSave(true);
                TransitionPane.closeDialogBox();
                Login_View.suKien_View.getCollapse().setCollapsed(false);
            }
        }
        if (btn_OK.getText().equals(DataString.UPDATED)) {
            checkInput();
            if (checkSave()) {
                row = VIEW.getSuKienList().getRow();
                SuKien_Model suKien = getData();
                SuKien_ListSK.getListEdit_temp().add(suKien);
                VIEW.getSuKienList().getTableModel_SuKien().setValueAt(suKien.getMaSK(), row, 1);
                VIEW.getSuKienList().getTableModel_SuKien().setValueAt(suKien.getTenSK(), row, 2);
                VIEW.getSuKienList().getTableModel_SuKien().setValueAt(suKien.getNgayDienRa(), row, 3);
                VIEW.getSuKienList().getTableModel_SuKien().setValueAt(suKien.getGioVao(), row, 4);
                VIEW.getSuKienList().getTableModel_SuKien().setValueAt(suKien.getGioRa(), row, 5);
                Login_View.suKien_View.setSave(true);
                TransitionPane.closeDialogBox();
                Login_View.suKien_View.getCollapse().setCollapsed(false);
            }
        }
    }//GEN-LAST:event_btn_OKActionPerformed

    private void txt_MSKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MSKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MSKActionPerformed

    private void txt_MSKFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MSKFocusGained

    }//GEN-LAST:event_txt_MSKFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_OK;
    private org.jdesktop.swingx.JXDatePicker dPicker_Start;
    private javax.swing.JLabel lbl_Macb;
    private javax.swing.JLabel lbl_Macb1;
    private javax.swing.JLabel lbl_Macb2;
    private javax.swing.JLabel lbl_Macb3;
    private javax.swing.JLabel lbl_Macb4;
    private javax.swing.JPanel pnl_Display;
    private javax.swing.JPanel pnl_GhiChu;
    private javax.swing.JPanel pnl_GioBatDau;
    private javax.swing.JPanel pnl_GioKetThuc;
    private javax.swing.JPanel pnl_MaSK;
    private javax.swing.JPanel pnl_NgayDienRa;
    private javax.swing.JPanel pnl_Table;
    private javax.swing.JPanel pnl_TenSK;
    private javax.swing.JSpinner spn_Begin;
    private javax.swing.JSpinner spn_End;
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
    private javax.swing.JTextField txt_GioBatDau;
    private javax.swing.JTextField txt_GioKetThuc;
    private javax.swing.JTextField txt_MSK;
    private javax.swing.JTextField txt_NgayDienRa;
    private javax.swing.JTextField txt_Ten;
    // End of variables declaration//GEN-END:variables
@Override
    public void focusGained(FocusEvent e) {
        Object object = e.getSource();
        if (object.equals(txt_MSK)) {
            spr_Show.setForeground(Config.focusGained);
            tipWarning[0].hideNotify();
        }
        if (object.equals(txt_Ten)) {
            spr_Show5.setForeground(Config.focusGained);
            tipWarning[1].hideNotify();
        }
        if (object.equals(dPicker_Start)) {
            tipWarning[2].hideNotify();
        }
        if (object.equals(spn_Begin)) {
            tipWarning[3].hideNotify();
        }
        if (object.equals(spn_End)) {
            tipWarning[4].hideNotify();
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        String gioVao, gioRa;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        gioVao = String.valueOf(format.format(spn_Begin.getValue()));
        gioRa = String.valueOf(format.format(spn_End.getValue()));
        Object object = e.getSource();
        if (object.equals(txt_MSK)) {
            spr_Show.setForeground(Config.focusLost);
            if (txt_MSK.getText().equals("") || txt_MSK.getText().trim().equals("Mã sự kiện")) {
                tipWarning[0].showNotify("Không được để trống trường này", edit);
            } else if (!Check.unAccent(txt_MSK.getText())) {
                tipWarning[0].showNotify("Mã sự kiện không chứa ký tự có dấu", edit);
            } else if (txt_MSK.getText().trim().length() > 14) {
                tipWarning[0].showNotify("Tối đa 15 ký tự", edit);
            } else {
                tipWarning[0].hideNotify();
            }
        }

        if (object.equals(txt_Ten)) {
            spr_Show5.setForeground(Config.focusLost);
            if (txt_Ten.getText().equals("") || txt_Ten.getText().trim().equals("Tên sự kiện")) {
                tipWarning[1].showNotify("Không được để trống trường này", edit);
            } else {
                tipWarning[1].hideNotify();
            }
        }
        if (object.equals(dPicker_Start)) {
            if (Util.compare(new Date(dPicker_Start.getDate().getTime()), new Date(System.currentTimeMillis())) < 0) {
                tipWarning[2].showNotify("Không thể chọn ngày đã diễn ra", edit, 3);
            } else {
                tipWarning[2].hideNotify();
            }
        }
        if (object.equals(spn_Begin)) {
            if (spn_Begin.getValue().equals("")) {
                tipWarning[3].showNotify("Không được để trống trường này", edit);
            } else if (Util.compare(Time.valueOf(gioVao), new Time(System.currentTimeMillis())) < 0) {
                tipWarning[3].showNotify("Không thể chọn thời gian đã qua", edit, 3);
            } else {
                tipWarning[3].hideNotify();
            }
        }
        if (object.equals(spn_End)) {
            if (spn_End.getValue().equals("")) {
                tipWarning[4].showNotify("Không được để trống trường này", edit, 3);
            } else if (Util.compare(Time.valueOf(gioRa), Time.valueOf(gioVao)) < 0) {
                tipWarning[4].showNotify("Không thể chọn thời gian ra sớm hơn thời gian vào", edit, 3);
            } else {
                tipWarning[4].hideNotify();
            }
        }

    }

}
