/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sukien.view;

import static administrator.settings.Config.bg_Color1;
import static administrator.settings.Config.bg_Color2;
import app.view.App_View;
import canbo.view.CanBo_List;
import other.custom.GradientButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import login.Login_View;
import other.custom.Alert;
import resources.Resources;
import sinhvien.view.SinhVien_List;

/**
 *
 * @author chuna
 */
public class SuKien_ChonKieuNhap extends JDialog implements ActionListener {

    private GradientButton btn_ThemCanBo;
    private GradientButton btn_ThemSinhVien;
    private int kieuNhap;
    static int HETHONG = 1, EXCEL = 2;

    public SuKien_ChonKieuNhap(int kieuNhap) {
        super();
        this.kieuNhap = kieuNhap;
        createUI();
    }

    private void createUI() {
        btn_ThemCanBo = new GradientButton(bg_Color1, bg_Color2, Color.BLACK, Color.decode("#242424"), "Thêm Cán bộ", Resources.canBo_Icon);
        btn_ThemSinhVien = new GradientButton(bg_Color1, bg_Color2, Color.BLACK, Color.decode("#242424"), "Thêm Sinh viên", Resources.sinhVien_Icon);
        Arrays.asList(btn_ThemCanBo, btn_ThemSinhVien).forEach((t) -> {
            t.setPreferredSize(new Dimension(150, 100));
            t.addActionListener(this);
            t.setVerticalTextPosition(SwingConstants.BOTTOM);
            t.setHorizontalTextPosition(SwingConstants.CENTER);
        });
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        getContentPane().add(btn_ThemCanBo);
        getContentPane().add(btn_ThemSinhVien);
        setResizable(false);
        setBackground(Color.white);
        setAlwaysOnTop(true);
        pack();
    }

    public int getKieuNhap() {
        return kieuNhap;
    }

    public void setKieuNhap(int kieuNhap) {
        this.kieuNhap = kieuNhap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(btn_ThemCanBo)) {
            if (kieuNhap == HETHONG) {
                SuKien_ThemDS.getThemDS().setChoose(SuKien_ThemDS.CANBO);
                dispose();
                SuKien_ThemDS.getThemDS().setLocationRelativeTo(App_View.getContainer());
                SuKien_ThemDS.getThemDS().setVisible(true);
            } else if (kieuNhap == EXCEL) {
                dispose();
                Login_View.suKien_View.getImportDS().setChoose(SuKien_ImportDS.CANBO);
                Object[][] ob = CanBo_List.getController().import_CanBo();
                if (ob != null && ob.length > 0) {
                    SuKien_ImportDS.getImportDS().setData(ob);

                    dispose();
                    SuKien_ImportDS.getImportDS().setLocationRelativeTo(App_View.getContainer());
                    SuKien_ImportDS.getImportDS().setVisible(true);
                    JLabel lbl_Path_ = new JLabel(CanBo_List.getController().getPath());
                    SuKien_ImportDS.getImportDS().setLabel(lbl_Path_);
                    SuKien_ImportDS.getImportDS().getPnl_Path().add(lbl_Path_);
                    SuKien_ImportDS.getImportDS().getPnl_Path().validate();
                    SuKien_ImportDS.getImportDS().getPnl_Path().repaint();

                } else if (ob != null) {
                    Alert.showMessageDialog(this, "Tập tin không có dữ liệu.", "Import thất bại");
                } else if (ob == null) {
                    Alert.showMessageDialog(this, "Bạn chưa chọn file nào.", "Import thất bại");
                }

            }
        } else if (obj.equals(btn_ThemSinhVien)) {
            if (kieuNhap == HETHONG) {
                SuKien_ThemDS.getThemDS().setChoose(SuKien_ThemDS.SINHVIEN);
                dispose();
                SuKien_ThemDS.getThemDS().setLocationRelativeTo(App_View.getContainer());
                SuKien_ThemDS.getThemDS().setVisible(true);
            } else if (kieuNhap == EXCEL) {
                dispose();
                Login_View.suKien_View.getImportDS().setChoose(SuKien_ImportDS.SINHVIEN);
                Object[][] obSV = SinhVien_List.getController().import_SinhVien();
                if (obSV != null && obSV.length > 0) {
                    SuKien_ImportDS.getImportDS().setData(obSV);
                    dispose();
                    SuKien_ImportDS.getImportDS().setLocationRelativeTo(App_View.getContainer());
                    SuKien_ImportDS.getImportDS().setVisible(true);
                    JLabel lbl_Path_ = new JLabel(CanBo_List.getController().getPath());
                    SuKien_ImportDS.getImportDS().setLabel(lbl_Path_);
                    SuKien_ImportDS.getImportDS().getPnl_Path().add(lbl_Path_);
                    SuKien_ImportDS.getImportDS().getPnl_Path().validate();
                    SuKien_ImportDS.getImportDS().getPnl_Path().repaint();
                } else if (obSV != null) {
                    Alert.showMessageDialog(this, "Tập tin không có dữ liệu.", "Import thất bại");
                } else if (obSV == null) {
                    Alert.showMessageDialog(this, "Bạn chưa chọn file nào.", "Import thất bại");
                }
            }
        }
    }
}
