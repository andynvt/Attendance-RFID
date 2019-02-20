/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.view;

import sukien.model.SuKien_Model;
import administrator.settings.Config;
import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import static administrator.settings.Config.fg_Color1;
import static administrator.settings.Config.fg_Color2;
import dscanbo.view.DSCanBo_View;
import dssinhvien.view.DSSinhVien_View;
import other.custom.DataString;
import static other.custom.DataString.ADD;
import static other.custom.DataString.UPDATE;
import other.custom.FormatPattern;
import other.custom.GradientButton;
import other.custom.Util;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import javax.swing.text.JTextComponent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import other.custom.CustComp;
import other.custom.TransitionPane;
import static resources.Resources.them_Icon;

/**
 *
 * @author chuna
 */
public class DiemDanh_Details extends javax.swing.JPanel implements ActionListener, FocusListener {

    private static DiemDanh_DSThamGia dSThamGia;
    private SuKien_Model suKien_Model;
    private int type;
    boolean diemDanh;
    private GradientButton btn_ThemDanhSach;
    private boolean screen;
    private String mask;

    public DiemDanh_Details() {
        dSThamGia = new DiemDanh_DSThamGia(mask);
        suKien_Model = new SuKien_Model();
        initComponents();
        createUI();
    }

    public DiemDanh_Details(SuKien_Model event_Model) {
        this.suKien_Model = event_Model;
        initComponents();
        createUI();
    }

    private void createUI() {
        setEditor(type);
        styleSpinner();
        btn_ThemDanhSach = new GradientButton(bg_Color1, bg_Color2, fg_Color1, fg_Color2, "Thêm danh sách tham dự", them_Icon);
        btn_ThemDanhSach.addActionListener((e) -> {
//            Login_View.suKien_View.them_DanhSach();
        });
        pnl_ThemDanhSach.add(btn_ThemDanhSach);
        TransitionPane.replacePane(pnl_Table, dSThamGia);
    }
    boolean diemDanhVao, diemDanhRa;

    private void loadData() {
        setData();
        dSThamGia.setMask(suKien_Model.getMaSK());
        dSThamGia.getdSCanBo_View().setCbxFilter(DataString.cboxDiemDanh);
        dSThamGia.getdSCanBo_View().setMasukien(suKien_Model.getMaSK());
        dSThamGia.getdSCanBo_View().setDiemDanh(true);
        dSThamGia.getdSSinhVien_View().setCbxFilter(DataString.cboxDiemDanh);
        dSThamGia.getdSSinhVien_View().setMasukien(suKien_Model.getMaSK());
        dSThamGia.getdSSinhVien_View().setDiemDanh(true);
        emptyCB = DSCanBo_View.getController().countTatCaTrongDS(suKien_Model.getMaSK());
        emptySV = DSSinhVien_View.getController().countTatCaTrongDS(suKien_Model.getMaSK());
    }

    private void styleSpinner() {
        spn_Begin.setBorder(new LineBorder(Color.decode("#e1e1e1")));
        spn_End.setBorder(new LineBorder(Color.decode("#e1e1e1")));

        dPicker_Start.setFormats(new String[]{"EEEE, dd/MM/yyyy"});

        FormatPattern.formatSpinner(spn_Begin, "HH:mm");
        FormatPattern.formatSpinner(spn_End, "HH:mm");
        dPicker_Start.setDate(new java.util.Date());
        spn_Begin.setValue(new Time(7, 00, 0));
        spn_End.setValue(new Time(11, 00, 0));
    }

    private void setData() {
        txt_MSK.setText(suKien_Model.getMaSK());
        txt_Ten.setText(suKien_Model.getTenSK());
        txt_NgayDienRa.setText(Util.formatDate(suKien_Model.getNgayDienRa()));
        txt_GioBatDau.setText(suKien_Model.getGioVao().toString());
        txt_GioKetThuc.setText(suKien_Model.getGioRa().toString());
    }

    public void setDataDefault() {
        TransitionPane.replacePane(pnl_Table, dSThamGia);
        setEditor(type);
        txt_MSK.setText("");
        txt_Ten.setText("");
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

    public void setEditor(int type) {
        boolean edit = type == ADD || type == UPDATE;
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
        spr_Show6.setVisible(!edit);
        spr_Show7.setVisible(!edit);
        spr_Show8.setVisible(!edit);
        changeTextField(pnl_NgayDienRa, txt_NgayDienRa, dPicker_Start, edit);
        changeTextField(pnl_GioBatDau, txt_GioBatDau, spn_Begin, edit);
        changeTextField(pnl_GioKetThuc, txt_GioKetThuc, spn_End, edit);
    }
    private int emptyCB, emptySV;

    private void changeTextField(JPanel panel, Component txt, Component newComponent, boolean edit) {
        panel.remove(txt);
        panel.add(edit ? newComponent : txt, 1);
        panel.validate();
        panel.repaint();
    }

    public void changeTable() {
        if (DiemDanh_DSThamGia.getTypeTable() == 1) {
            dSThamGia.showDSSinhVien();
        } else if (DiemDanh_DSThamGia.getTypeTable() == 2) {
            dSThamGia.showDSCanBo();
        } else if (DiemDanh_DSThamGia.getTypeTable() == 0) {

        }
    }

    public boolean isScreen() {
        return screen;
    }

    public void setScreen(boolean screen) {
        this.screen = screen;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        setEditor(type);
        this.type = type;
    }

    public SuKien_Model getSuKien_Model() {
        return suKien_Model;
    }

    public void setSuKien_Model(SuKien_Model suKien_Model) {
        this.suKien_Model = suKien_Model;
        loadData();
    }

    public boolean isDiemDanh() {
        return diemDanh;
    }

    public void setDiemDanh(boolean diemDanh) {
        this.diemDanh = diemDanh;
    }

    public boolean isEmptyCB() {
        return emptyCB == 0;
    }

    public boolean isEmptySV() {
        return emptySV == 0;
    }

    public JTextField getTxt_GioBatDau() {
        return txt_GioBatDau;
    }

    public void setTxt_GioBatDau(JTextField txt_GioBatDau) {
        this.txt_GioBatDau = txt_GioBatDau;
    }

    public JTextField getTxt_GioKetThuc() {
        return txt_GioKetThuc;
    }

    public void setTxt_GioKetThuc(JTextField txt_GioKetThuc) {
        this.txt_GioKetThuc = txt_GioKetThuc;
    }

    public DiemDanh_DSThamGia getdSThamGia() {
        return dSThamGia;
    }

    public void setdSThamGia(DiemDanh_DSThamGia dSThamGia) {
        this.dSThamGia = dSThamGia;
    }

    public JTextField getTxt_Ten() {
        return txt_Ten;
    }

    public void setTxt_Ten(JTextField txt_Ten) {
        this.txt_Ten = txt_Ten;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dPicker_Start = new org.jdesktop.swingx.JXDatePicker();
        spn_Begin = new javax.swing.JSpinner();
        spn_End = new javax.swing.JSpinner();
        pnl_Empty = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnl_ThemDanhSach = new javax.swing.JPanel();
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

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chưa có danh sách tham dự. Xin mời thêm vào.");

        pnl_ThemDanhSach.setPreferredSize(new java.awt.Dimension(0, 55));

        javax.swing.GroupLayout pnl_EmptyLayout = new javax.swing.GroupLayout(pnl_Empty);
        pnl_Empty.setLayout(pnl_EmptyLayout);
        pnl_EmptyLayout.setHorizontalGroup(
            pnl_EmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_EmptyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_EmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_ThemDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_EmptyLayout.setVerticalGroup(
            pnl_EmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_EmptyLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_ThemDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        setMinimumSize(new java.awt.Dimension(900, 0));

        pnl_Display.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        pnl_Display.setOpaque(false);
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
        txt_MSK.setBorder(null);
        txt_MSK.setPreferredSize(new java.awt.Dimension(300, 32));
        txt_MSK.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_MSKFocusGained(evt);
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

    private void txt_MSKFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MSKFocusGained
     }//GEN-LAST:event_txt_MSKFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker dPicker_Start;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_Macb;
    private javax.swing.JLabel lbl_Macb1;
    private javax.swing.JLabel lbl_Macb2;
    private javax.swing.JLabel lbl_Macb3;
    private javax.swing.JLabel lbl_Macb4;
    private javax.swing.JPanel pnl_Display;
    private javax.swing.JPanel pnl_Empty;
    private javax.swing.JPanel pnl_GhiChu;
    private javax.swing.JPanel pnl_GioBatDau;
    private javax.swing.JPanel pnl_GioKetThuc;
    private javax.swing.JPanel pnl_MaSK;
    private javax.swing.JPanel pnl_NgayDienRa;
    private javax.swing.JPanel pnl_Table;
    private javax.swing.JPanel pnl_TenSK;
    private javax.swing.JPanel pnl_ThemDanhSach;
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
        }
        if (object.equals(txt_Ten)) {
            spr_Show5.setForeground(Config.focusGained);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        Object object = e.getSource();
        if (object.equals(txt_MSK)) {
            spr_Show.setForeground(Config.focusLost);
        }
        if (object.equals(txt_Ten)) {
            spr_Show5.setForeground(Config.focusLost);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
