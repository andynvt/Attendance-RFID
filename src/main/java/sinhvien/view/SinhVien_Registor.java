/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinhvien.view;

import canbo.view.*;
import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import other.custom.Check;
import other.custom.GradientButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.border.DropShadowBorder;
import other.custom.Alert;
import other.table.CustomTable;
import resources.Resources;
import sinhvien.controller.SinhVien_Controller;
import sinhvien.model.SinhVien_Model;

/**
 *
 * @author chuna
 */
public class SinhVien_Registor extends javax.swing.JPanel {

    /**
     * Creates new form DKTheCanBo
     */
    private SinhVien_Model sinhVien_Model;
    private SinhVien_Details sinhVien_Detail = new SinhVien_Details();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private final String txt;
    private GradientButton btn_DangKy, btn_Huy, btn_TroLai;
    private static CanBo_View VIEW = CanBo_View.getView();
    private JDialog dialog = new JDialog();

    public SinhVien_Registor() {
        initComponents();
        createUI();
        initSearch();
        txt = txt_TimKiem.getText();
    }

    private void createUI() {
        btn_DangKy = new GradientButton(bg_Color1, bg_Color2, Resources.daDK_Icon);
        btn_DangKy.setText("Đăng ký thẻ");
        btn_DangKy.setEnabled(false);
        btn_TroLai = new GradientButton(bg_Color1, bg_Color2, Resources.troLai_Icon);
        btn_TroLai.setText("Trở lại");

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
            boolean perfect;

            private void updateMaThe() {
                if (txt_temp.getText().length() == 10) {
                    txtMaRFID.setForeground(Color.decode("#2e3233"));
                    txtMaRFID.setText(txt_temp.getText());
                    txt_temp.setSelectionStart(0);
                    txt_temp.setSelectionEnd(9);
                }

            }
        });
        txt_temp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!Check.isRFIDValid(txt_temp.getText())) {
                    txt_temp.setText("");
                    txtMaRFID.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!Check.isRFIDValid(txt_temp.getText())) {
                    txt_temp.setText("");
                    txtMaRFID.setText("");
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (!Check.isRFIDValid(txt_temp.getText())) {
                    txt_temp.setText("");
                    txtMaRFID.setText("");
                }
            }

        });
        pnl_Display.setVisible(false);
        dialog.setLayout(new GridLayout());
        dialog.add(pnl_QuetMa);
        dialog.setIconImage(Resources.daDK_Icon.getImage());
        dialog.pack();
        dialog.setTitle("Đăng ký mã RFID");
        btn_DangKy.addActionListener((e) -> {
            if (txt_MaRFID.getText().equals("Chưa có") || txt_MaRFID.getText().equals("null")) {
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            } else {
                Alert.showMessageDialog(this, "Sinh viên đã đăng ký mã RFID rồi.", "Thông báo");
            }
        });
        btn_Huy = new GradientButton(bg_Color1, bg_Color2, Resources.huy_Icon);
        btn_Huy.setText("Huỷ bỏ");
        btn_Huy.addActionListener((e) -> {
            VIEW.getToggle().doClick();
        });
        btn_TroLai.addActionListener((e) -> {
            pnl_Info.removeAll();
            pnl_Info.add(pnl_KetQua);
            pnl_Info.validate();
            pnl_Info.repaint();
            btn_TroLai.setVisible(false);
            btn_DangKy.setEnabled(false);
        });
        pnl_Task.add(btn_TroLai);
        pnl_Task.add(btn_DangKy);
        pnl_Task.add(btn_Huy);
        btn_TroLai.setVisible(false);
        btnOk.addActionListener((e) -> {
            if (btnOk.getText().equals("Quét lại")) {
                txt_temp.setText("");
                txt_temp.requestFocus();
            } else {
                if (!txtMaRFID.getText().equals("")) {
                    boolean existRFID = CanBo_List.getController().existRFIDCanBo(txtMaRFID.getText().trim())
                            || SinhVien_List.getController().existRFIDSinhVien(txtMaRFID.getText().trim());
                    if (existRFID) {
                        Alert.showMessageDialog(this, "Mã thẻ RFID đã được sử dụng.", "Thông báo");
                    } else {
                        boolean update_maRFID = SinhVien_List.getController().update_MaThe(sinhVien_Model.getMaSV(), txtMaRFID.getText());
                        if (update_maRFID) {
                            Alert.showMessageDialog(this, "Đăng ký thẻ thành công.", "Thông báo");
                        } else {
                            Alert.showMessageDialog(this, "Đăng ký thẻ không thành công.", "Thông báo");
                        }
                        setValue(sinhVien_Model = SinhVien_List.getController().load_SinhVien(sinhVien_Model.getMaSV()));
                        dialog.dispose();
                    }
                } else {
                    Alert.showMessageDialog(this, "Không thể nhận dạng mã thẻ. Vui lòng quét lại!", "Lỗi quét thẻ");
                    txt_temp.requestFocus();
                }
            }
        });

    }

    private void initSearch() {
        jScrollPane1.setVisible(false);
        listModel.removeAllElements();
        txt_TimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                resultSearch(txt_TimKiem.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                resultSearch(txt_TimKiem.getText());

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                resultSearch(txt_TimKiem.getText());

            }
        });

        txt_TimKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jPanel1.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new DropShadowBorder(Color.black, 12, (float) 0.5, 4, false, false, true, false)));
                if (txt_TimKiem.getText().equals("Nhập mã sinh viên, tên, ngành") || txt_TimKiem.getText().isEmpty()) {
                    txt_TimKiem.setForeground(new Color(204, 204, 204));
                    txt_TimKiem.setCaretPosition(SwingConstants.CENTER);
                } else {
                    txt_TimKiem.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                jPanel1.setBorder(null);
                if (txt_TimKiem.getText().equals("Nhập mã sinh viên, tên, ngành") || txt_TimKiem.getText().isEmpty()) {
                    txt_TimKiem.setForeground(new Color(204, 204, 204));
                    txt_TimKiem.setCaretPosition(SwingConstants.CENTER);
                    txt_TimKiem.setText("Nhập mã sinh viên, tên, ngành");
                } else {
                    txt_TimKiem.setForeground(new Color(0, 0, 0));
                }
            }

        });
        txt_TimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean right = e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_UP;

                if (right && txt_TimKiem.getText().equals("Nhập mã sinh viên, tên, ngành") || txt_TimKiem.getText().isEmpty()) {
                    txt_TimKiem.setText("");
                } else {
                    txt_TimKiem.setForeground(new Color(0, 0, 0));
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (jScrollPane1.isShowing() && !listModel.isEmpty()) {
                        lst_Result.requestFocus();
//                        lst_Result.setSelectedIndex(0);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
//                if (txt_TimKiem.getText().equals("Nhập mã sinh viên, tên, ngành") || txt_TimKiem.getText().isEmpty()) {
//                    txt_TimKiem.setText("");
//                } else {
//                    txt_TimKiem.setForeground(new Color(0, 0, 0));
//                }
            }

        });
        txt_TimKiem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txt_TimKiem.getText().equals("Nhập mã sinh viên, tên, ngành") || txt_TimKiem.getText().isEmpty()) {
                    txt_TimKiem.setForeground(new Color(204, 204, 204));
                    txt_TimKiem.setCaretPosition(SwingConstants.CENTER);
                } else {
                    txt_TimKiem.setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (txt_TimKiem.getText().equals("Nhập mã sinh viên, tên, ngành") || txt_TimKiem.getText().isEmpty()) {
                    txt_TimKiem.setCaretPosition(SwingConstants.CENTER);
                }
            }

        });
        lst_Result.addListSelectionListener((e) -> {
            if (lst_Result.getSelectedIndex() != -1) {
                String[] result = lst_Result.getSelectedValue().split(" --- ");
                txt_TimKiem.setText(lst_Result.getSelectedValue());
                jScrollPane1.setVisible(false);
                sinhVien_Model = SinhVien_List.getController().load_SinhVien(result[0]);
                setValue(sinhVien_Model);
                pnl_Info.removeAll();
                pnl_Info.add(pnl_Display);
                pnl_Info.validate();
                pnl_Info.repaint();
            }
        });
    }

    private void setValue(SinhVien_Model sinhVien_Model) {
        txt_MSSV.setText(sinhVien_Model.getMaSV());
        txt_HoTen.setText(sinhVien_Model.getTen());
        txt_Email.setText(sinhVien_Model.getEmail());
        txt_Lop.setText(sinhVien_Model.getLop());
        txt_Nganh.setText(sinhVien_Model.getNganh());
        txt_Khoa.setText(sinhVien_Model.getKhoa());
        txt_NienKhoa.setText(sinhVien_Model.getNienKhoa());
        txt_MaRFID.setText(sinhVien_Model.getMaRFID());
        pnl_Display.setVisible(true);
        btn_DangKy.setEnabled(pnl_Display.isVisible());
    }

    private void resultSearch(String search) {
        ArrayList<SinhVien_Model> load_CanBo = SinhVien_List.getController().search_SinhVien(search);
        listModel.removeAllElements();
        load_CanBo.forEach((t) -> {
            listModel.addElement(t.getMaSV() + " --- " + t.getTen() + " --- " + t.getNganh());
        });
        lst_Result.setModel(listModel);
        if (search.isEmpty() || search.equalsIgnoreCase(txt) || listModel.isEmpty()) {
            jScrollPane1.setVisible(false);
        } else {
            jScrollPane1.setVisible(true);
            validate();
            repaint();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_QuetMa = new javax.swing.JPanel();
        txt_temp = new javax.swing.JTextField();
        txtMaRFID = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        pnl_KetQua = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnl_Table = new javax.swing.JPanel();
        pnl_Display = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_Macb = new javax.swing.JLabel();
        txt_MSSV = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lbl_Macb1 = new javax.swing.JLabel();
        txt_HoTen = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lbl_Macb2 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lbl_Macb3 = new javax.swing.JLabel();
        txt_Lop = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lbl_Macb4 = new javax.swing.JLabel();
        txt_Khoa = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        lbl_Macb5 = new javax.swing.JLabel();
        txt_Nganh = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        lbl_Macb7 = new javax.swing.JLabel();
        txt_NienKhoa = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        lbl_Macb6 = new javax.swing.JLabel();
        txt_MaRFID = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        txt_TimKiem = new javax.swing.JTextField();
        btn_TimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lst_Result = new javax.swing.JList<>();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        pnl_Info = new javax.swing.JPanel();
        pnl_Task = new javax.swing.JPanel();

        pnl_QuetMa.setBackground(new java.awt.Color(255, 255, 255));

        txtMaRFID.setEditable(false);
        txtMaRFID.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        txtMaRFID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaRFIDActionPerformed(evt);
            }
        });

        btnOk.setText("Đăng ký");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnHuy.setText("Huỷ bỏ");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTitle.setText("Xin mời đưa thẻ vào máy quét");
        lblTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 15));

        javax.swing.GroupLayout pnl_QuetMaLayout = new javax.swing.GroupLayout(pnl_QuetMa);
        pnl_QuetMa.setLayout(pnl_QuetMaLayout);
        pnl_QuetMaLayout.setHorizontalGroup(
            pnl_QuetMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_QuetMaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_QuetMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_QuetMaLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(btnOk)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuy))
                    .addComponent(txtMaRFID, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl_QuetMaLayout.createSequentialGroup()
                .addComponent(txt_temp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_QuetMaLayout.setVerticalGroup(
            pnl_QuetMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_QuetMaLayout.createSequentialGroup()
                .addComponent(txt_temp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMaRFID, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 23, Short.MAX_VALUE)
                .addGroup(pnl_QuetMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnl_KetQua.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Kết quả tìm kiếm:");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 25, 10, 25));
        pnl_KetQua.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        pnl_Table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 25));
        pnl_Table.setLayout(new java.awt.GridLayout(1, 0));
        pnl_KetQua.add(pnl_Table, java.awt.BorderLayout.CENTER);

        pnl_Display.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 50, 1, 1));
        pnl_Display.setOpaque(false);
        pnl_Display.setPreferredSize(new java.awt.Dimension(870, 900));
        pnl_Display.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 28));

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setText("MSSV:");
        lbl_Macb.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel2.add(lbl_Macb);

        txt_MSSV.setEditable(false);
        txt_MSSV.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_MSSV.setForeground(new java.awt.Color(153, 153, 153));
        txt_MSSV.setText("Mã số sinh viên");
        txt_MSSV.setBorder(null);
        txt_MSSV.setPreferredSize(new java.awt.Dimension(320, 32));
        txt_MSSV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_MSSVFocusGained(evt);
            }
        });
        jPanel2.add(txt_MSSV);

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
        txt_HoTen.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel3.add(txt_HoTen);

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
        txt_Email.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel4.add(txt_Email);

        pnl_Display.add(jPanel4);

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setText("Lớp");
        lbl_Macb3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb3.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel5.add(lbl_Macb3);

        txt_Lop.setEditable(false);
        txt_Lop.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Lop.setForeground(new java.awt.Color(153, 153, 153));
        txt_Lop.setText("Tên Lớp");
        txt_Lop.setBorder(null);
        txt_Lop.setPreferredSize(new java.awt.Dimension(320, 32));
        txt_Lop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_LopActionPerformed(evt);
            }
        });
        jPanel5.add(txt_Lop);

        pnl_Display.add(jPanel5);

        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setText("Khoa");
        lbl_Macb4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb4.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel6.add(lbl_Macb4);

        txt_Khoa.setEditable(false);
        txt_Khoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Khoa.setForeground(new java.awt.Color(153, 153, 153));
        txt_Khoa.setText("Tên khoa");
        txt_Khoa.setBorder(null);
        txt_Khoa.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel6.add(txt_Khoa);

        pnl_Display.add(jPanel6);

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb5.setText("Ngành:");
        lbl_Macb5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb5.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel7.add(lbl_Macb5);

        txt_Nganh.setEditable(false);
        txt_Nganh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_Nganh.setForeground(new java.awt.Color(153, 153, 153));
        txt_Nganh.setText("Tên ngành");
        txt_Nganh.setBorder(null);
        txt_Nganh.setPreferredSize(new java.awt.Dimension(320, 32));
        txt_Nganh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NganhActionPerformed(evt);
            }
        });
        jPanel7.add(txt_Nganh);

        pnl_Display.add(jPanel7);

        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb7.setText("Niên Khoa:");
        lbl_Macb7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb7.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel10.add(lbl_Macb7);

        txt_NienKhoa.setEditable(false);
        txt_NienKhoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_NienKhoa.setForeground(new java.awt.Color(153, 153, 153));
        txt_NienKhoa.setText("Niên Khoá");
        txt_NienKhoa.setBorder(null);
        txt_NienKhoa.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel10.add(txt_NienKhoa);

        pnl_Display.add(jPanel10);

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(400, 36));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lbl_Macb6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_Macb6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Macb6.setText("Mã RFID:");
        lbl_Macb6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Macb6.setPreferredSize(new java.awt.Dimension(80, 32));
        jPanel8.add(lbl_Macb6);

        txt_MaRFID.setEditable(false);
        txt_MaRFID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_MaRFID.setForeground(new java.awt.Color(153, 153, 153));
        txt_MaRFID.setText("Mã RFID");
        txt_MaRFID.setBorder(null);
        txt_MaRFID.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel8.add(txt_MaRFID);

        pnl_Display.add(jPanel8);

        setBackground(new java.awt.Color(249, 249, 249));

        jLayeredPane1.setBackground(new java.awt.Color(249, 249, 249));
        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 0, 5, 0));
        jLayeredPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLayeredPane1.setInheritsPopupMenu(true);
        jLayeredPane1.setLayout(new java.awt.FlowLayout());

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(650, 50));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        txt_TimKiem.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        txt_TimKiem.setForeground(new java.awt.Color(204, 204, 204));
        txt_TimKiem.setText("Nhập mã sinh viên, tên, ngành");
        txt_TimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        txt_TimKiem.setMargin(new java.awt.Insets(2, 5, 2, 2));
        txt_TimKiem.setPreferredSize(new java.awt.Dimension(600, 48));
        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });
        jPanel1.add(txt_TimKiem);

        btn_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_TimKiem.setText("Tìm");
        btn_TimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 4, 0));
        btn_TimKiem.setContentAreaFilled(false);
        btn_TimKiem.setOpaque(true);
        btn_TimKiem.setPreferredSize(new java.awt.Dimension(50, 46));
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });
        jPanel1.add(btn_TimKiem);

        jLayeredPane1.add(jPanel1);

        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder1.setCornerSize(5);
        dropShadowBorder1.setShadowSize(3);
        dropShadowBorder1.setShowLeftShadow(true);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 50), dropShadowBorder1));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(650, 155));

        lst_Result.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        lst_Result.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        lst_Result.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lst_Result.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lst_Result.setAlignmentX(0.0F);
        lst_Result.setAlignmentY(0.0F);
        lst_Result.setFixedCellHeight(30);
        lst_Result.setFixedCellWidth(380);
        lst_Result.setPreferredSize(new java.awt.Dimension(380, 150));
        jScrollPane1.setViewportView(lst_Result);

        jLayeredPane1.add(jScrollPane1);

        jLayeredPane2.setInheritsPopupMenu(true);

        pnl_Info.setBackground(new java.awt.Color(245, 245, 245));
        pnl_Info.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 50, 0, 50));
        pnl_Info.setLayout(new java.awt.GridLayout(1, 0));

        jLayeredPane2.setLayer(pnl_Info, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_Info, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnl_Info, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
            .addComponent(pnl_Task, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addComponent(pnl_Task, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(116, 116, 116)
                    .addComponent(jLayeredPane2)
                    .addGap(53, 53, 53)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        String txt = txt_TimKiem.getText().equals("Nhập mã sinh viên, tên, ngành") ? "" : txt_TimKiem.getText();
        ArrayList<SinhVien_Model> load_SinhVien = SinhVien_List.getController().search_SinhVien(txt);
        if (load_SinhVien.size() > 0) {
            Object[][] data = SinhVien_Controller.array2Object(load_SinhVien);
            String[] columnNames = {"Tất cả", "MSSV", "Tên", "Email", "Lớp", "Khoa", "Ngành", "Niên khoá", "Mã RFID"};
            CustomTable table = new CustomTable(data, columnNames);
            table.setModel(new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

            });
            table.removeColumn(table.getColumnModel().getColumn(0));
            jScrollPane1.setVisible(false);
            pnl_Table.removeAll();
            pnl_Table.add(table.getScrollPane());
            pnl_Table.validate();
            pnl_Table.repaint();
            pnl_Info.removeAll();
            pnl_Info.add(pnl_KetQua);
            pnl_Info.validate();
            pnl_Info.repaint();
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    pnl_Info.removeAll();
                    pnl_Info.add(pnl_Display);
                    pnl_Info.validate();
                    pnl_Info.repaint();
                    SinhVien_Model sinhVien_Model = new SinhVien_Model(
                            (String) table.getValueAt(table.getSelectedRow(), 0),
                            (String) table.getValueAt(table.getSelectedRow(), 1),
                            (String) table.getValueAt(table.getSelectedRow(), 2),
                            (String) table.getValueAt(table.getSelectedRow(), 3),
                            (String) table.getValueAt(table.getSelectedRow(), 4),
                            (String) table.getValueAt(table.getSelectedRow(), 5),
                            (String) table.getValueAt(table.getSelectedRow(), 6),
                            (String) table.getValueAt(table.getSelectedRow(), 7)
                    );
                    setValue(sinhVien_Model);
                    btn_TroLai.setVisible(true);
                }
            });
        } else {
            Alert.showMessageDialog(VIEW, "Không có kết quả tìm kiếm.", "Thông báo");
            pnl_Info.removeAll();
        }
    }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void txtMaRFIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaRFIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaRFIDActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed

    }//GEN-LAST:event_btnOkActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        dialog.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void txt_MSSVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MSSVFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MSSVFocusGained

    private void txt_LopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_LopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_LopActionPerformed

    private void txt_NganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NganhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NganhActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lbl_Macb;
    private javax.swing.JLabel lbl_Macb1;
    private javax.swing.JLabel lbl_Macb2;
    private javax.swing.JLabel lbl_Macb3;
    private javax.swing.JLabel lbl_Macb4;
    private javax.swing.JLabel lbl_Macb5;
    private javax.swing.JLabel lbl_Macb6;
    private javax.swing.JLabel lbl_Macb7;
    private javax.swing.JList<String> lst_Result;
    private javax.swing.JPanel pnl_Display;
    private javax.swing.JPanel pnl_Info;
    private javax.swing.JPanel pnl_KetQua;
    private javax.swing.JPanel pnl_QuetMa;
    private javax.swing.JPanel pnl_Table;
    private javax.swing.JPanel pnl_Task;
    private javax.swing.JTextField txtMaRFID;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_HoTen;
    private javax.swing.JTextField txt_Khoa;
    private javax.swing.JTextField txt_Lop;
    private javax.swing.JTextField txt_MSSV;
    private javax.swing.JTextField txt_MaRFID;
    private javax.swing.JTextField txt_Nganh;
    private javax.swing.JTextField txt_NienKhoa;
    private javax.swing.JTextField txt_TimKiem;
    private javax.swing.JTextField txt_temp;
    // End of variables declaration//GEN-END:variables
}
