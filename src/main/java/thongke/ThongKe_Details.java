/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thongke;

import sukien.model.SuKien_Model;
import dscanbo.view.DSCanBo_View;
import dssinhvien.view.DSSinhVien_View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Action;
import other.custom.BarChart;
import other.custom.DataString;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXCollapsiblePane;
import other.custom.TransitionPane;

/**
 *
 * @author chuna
 */
public class ThongKe_Details extends javax.swing.JPanel {

    private SuKien_Model suKien_Model;
    boolean diemDanh;
    private String masukien;
    private DSCanBo_View dSCanBo_View = new DSCanBo_View();
    private DSSinhVien_View dSSinhVien_View = new DSSinhVien_View();
    private BarChart canBoChart, sinhVienChart;
    private int type = 0;
    private int typeTable;
    private Action toggleAction;

    public ThongKe_Details() {
        suKien_Model = new SuKien_Model();
        initComponents();
        createUI();
    }

    public ThongKe_Details(SuKien_Model event_Model) {
        this.suKien_Model = event_Model;
        this.masukien = event_Model.getMaSK();

        initComponents();
        createUI();
    }

    private void createUI() {
        dSCanBo_View = new DSCanBo_View(masukien, true);
        dSSinhVien_View = new DSSinhVien_View(masukien, true);
        collapseView.setLayout(new BorderLayout());
        collapseView.add(pnl_Empty, BorderLayout.CENTER);
        collapseView.setCollapsed(true);
        toggleAction = collapseView.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);
    }

    public JXCollapsiblePane getCollapseView() {
        return collapseView;
    }

    public Action getToggleAction() {
        return toggleAction;
    }

    public void showTable() {
        if (DSCanBo_View.getCount() > 0) {
            showDSCanBo();
        } else if (DSSinhVien_View.getCount() > 0) {
            showDSSinhVien();
        } else {
            showEmpty();
        }
    }

    public void showDSCanBo() {
        TransitionPane.replacePane(pnl_Table, dSCanBo_View);
        dSCanBo_View.execute();
        typeTable = 1;
    }

    public void showDSSinhVien() {
        TransitionPane.replacePane(pnl_Table, dSSinhVien_View);
        dSSinhVien_View.execute();
        typeTable = 2;
    }

    public void showEmpty() {
        removeAll();
        add(pnl_Empty);
        validate();
        repaint();
        typeTable = 0;
    }

    public void loadData() {
        dSCanBo_View.setCbxFilter(DataString.cboxThongKe);
        dSCanBo_View.setMasukien(suKien_Model.getMaSK());
        dSCanBo_View.setDiemDanh(true);
        dSSinhVien_View.setCbxFilter(DataString.cboxThongKe);
        dSSinhVien_View.setMasukien(suKien_Model.getMaSK());
        dSSinhVien_View.setDiemDanh(true);
    }

    public void excuteDanhSach() {
        String[] columnNames = {"Có mặt", "Vắng(Có ĐK)", "Đi trễ", "ĐD thủ công", "ĐD tự động"};
        String seriesCB = "Cán bộ", seriesSV = "Sinh Viên";

        int cBCoMat = DSCanBo_View.getController().countCanBoCoMat(suKien_Model.getMaSK());
        int cBVangMat = DSCanBo_View.getController().countCBVangCoDK(suKien_Model.getMaSK());
        int cBDiTre = DSCanBo_View.getController().countCanBoDiTre(suKien_Model.getMaSK());
        int cBDiemDanhTC = DSCanBo_View.getController().countCanBoDDThuCong(suKien_Model.getMaSK());
        int cBDiemDanhTuDOng = DSCanBo_View.getController().countCanBoDDMay(suKien_Model.getMaSK());

        int sVCoMat = DSSinhVien_View.getController().countSinhVienCoMat(suKien_Model.getMaSK());
        int sVVangMat = DSSinhVien_View.getController().countSV_VangCoDK(suKien_Model.getMaSK());
        int sVDiTre = DSSinhVien_View.getController().countSinhVienDiTre(suKien_Model.getMaSK());
        int sVDiemDanhTC = DSSinhVien_View.getController().countSinhVienDDThuCong(suKien_Model.getMaSK());
        int sVDiemDanhTuDOng = DSSinhVien_View.getController().countSinhVienDDMay(suKien_Model.getMaSK());

        int emptyCB = DSCanBo_View.getController().countTatCaTrongDS(suKien_Model.getMaSK());
        int emptySV = DSSinhVien_View.getController().countTatCaTrongDS(suKien_Model.getMaSK());
        Number[] numbers = {cBCoMat, cBVangMat, cBDiTre, cBDiemDanhTC, cBDiemDanhTuDOng};
        canBoChart = new BarChart("Biểu đồ kết quả điểm danh cán bộ", "Loại danh sách", "Số lượng", seriesCB, columnNames, numbers);
        Number[] numbers2 = {sVCoMat, sVVangMat, sVDiTre, sVDiemDanhTC, sVDiemDanhTuDOng};
        sinhVienChart = new BarChart("Biểu đồ kết quả điểm danh sinh viên", "Loại danh sách", "Số lượng", seriesSV, columnNames, numbers2);
        pnl_Empty.removeAll();
        pnl_Empty.add(canBoChart);
        pnl_Empty.add(sinhVienChart);
        pnl_Empty.validate();
        pnl_Empty.repaint();
    }

    public static void main(String[] args) {
        new ThongKe_Details().excuteDanhSach();
    }
    private int toggle = 0;

    public void toggleBarChart() {
        toggle++;
        pnl_Empty.setVisible(toggle % 2 == 0);
    }

    public String getMasukien() {
        return masukien;
    }

    public void setMasukien(String masukien) {
        this.masukien = masukien;
    }

    public SuKien_Model getSuKien_Model() {
        return suKien_Model;
    }

    public void setSuKien_Model(SuKien_Model suKien_Model) {
        this.suKien_Model = suKien_Model;
    }

    public JPanel getPnl_Table() {
        return pnl_Table;
    }

    public void setPnl_Table(JPanel pnl_Table) {
        this.pnl_Table = pnl_Table;
    }

    public DSCanBo_View getdSCanBo_View() {
        return dSCanBo_View;
    }

    public void setdSCanBo_View(DSCanBo_View dSCanBo_View) {
        this.dSCanBo_View = dSCanBo_View;
    }

    public DSSinhVien_View getdSSinhVien_View() {
        return dSSinhVien_View;
    }

    public void setdSSinhVien_View(DSSinhVien_View dSSinhVien_View) {
        this.dSSinhVien_View = dSSinhVien_View;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Empty1 = new javax.swing.JPanel();
        lbl_2 = new javax.swing.JLabel();
        lbl_TongDK1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_TongCM1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbl_TongVM1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_TongCB1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_CBCM1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbl_CBVM1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_TongSV1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_SVCM1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lbl_SVVM1 = new javax.swing.JLabel();
        pnl_Empty = new javax.swing.JPanel();
        collapseView = new org.jdesktop.swingx.JXCollapsiblePane();
        pnl_Table = new javax.swing.JPanel();

        pnl_Empty1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnl_Empty1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lbl_2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_2.setText("Số Lượng Đăng Ký Tham Dự:");
        lbl_2.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(lbl_2);

        lbl_TongDK1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_TongDK1.setText("0");
        lbl_TongDK1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_TongDK1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_TongDK1);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Số Lượng Vắng Mặt:");
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel14);

        lbl_TongCM1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_TongCM1.setText("0");
        lbl_TongCM1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_TongCM1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_TongCM1);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Số Lượng Có Mặt:");
        jLabel15.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel15);

        lbl_TongVM1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_TongVM1.setText("0");
        lbl_TongVM1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_TongVM1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_TongVM1);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Số lượng Cán Bộ tham dự:");
        jLabel9.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel9);

        lbl_TongCB1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_TongCB1.setText("0");
        lbl_TongCB1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_TongCB1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_TongCB1);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Số lượng Cán Bộ Có Mặt:");
        jLabel11.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel11);

        lbl_CBCM1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_CBCM1.setText("0");
        lbl_CBCM1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_CBCM1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_CBCM1);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Số lượng Cán Bộ Vắng Mặt:");
        jLabel16.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel16);

        lbl_CBVM1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_CBVM1.setText("0");
        lbl_CBVM1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_CBVM1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_CBVM1);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Số lượng Sinh Viên tham dự:");
        jLabel17.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel17);

        lbl_TongSV1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_TongSV1.setText("0");
        lbl_TongSV1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_TongSV1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_TongSV1);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Số lượng Sinh Viên Có Mặt:");
        jLabel18.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel18);

        lbl_SVCM1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_SVCM1.setText("0");
        lbl_SVCM1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_SVCM1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_SVCM1);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Số lượng Sinh Viên Vắng Mặt:");
        jLabel19.setPreferredSize(new java.awt.Dimension(200, 36));
        pnl_Empty1.add(jLabel19);

        lbl_SVVM1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_SVVM1.setText("0");
        lbl_SVVM1.setMinimumSize(new java.awt.Dimension(50, 36));
        lbl_SVVM1.setPreferredSize(new java.awt.Dimension(50, 36));
        pnl_Empty1.add(lbl_SVVM1);

        pnl_Empty.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Empty.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 15, 25, 15));
        pnl_Empty.setMaximumSize(new java.awt.Dimension(32767, 320));
        pnl_Empty.setMinimumSize(new java.awt.Dimension(10, 320));
        pnl_Empty.setPreferredSize(new java.awt.Dimension(10, 320));
        pnl_Empty.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 15));

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(900, 0));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
        add(collapseView, java.awt.BorderLayout.PAGE_START);

        pnl_Table.setBackground(new java.awt.Color(255, 255, 255));
        pnl_Table.setOpaque(false);
        pnl_Table.setLayout(new java.awt.GridLayout(1, 0));
        add(pnl_Table, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (collapseView.isCollapsed()) {
            pnl_Empty.setPreferredSize(pnl_Table.getSize());
        } else {
            int width = getWidth();
            int height = getHeight();
            pnl_Empty.setPreferredSize(new Dimension(width, height));
        }
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXCollapsiblePane collapseView;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbl_2;
    private javax.swing.JLabel lbl_CBCM1;
    private javax.swing.JLabel lbl_CBVM1;
    private javax.swing.JLabel lbl_SVCM1;
    private javax.swing.JLabel lbl_SVVM1;
    private javax.swing.JLabel lbl_TongCB1;
    private javax.swing.JLabel lbl_TongCM1;
    private javax.swing.JLabel lbl_TongDK1;
    private javax.swing.JLabel lbl_TongSV1;
    private javax.swing.JLabel lbl_TongVM1;
    private javax.swing.JPanel pnl_Empty;
    private javax.swing.JPanel pnl_Empty1;
    private javax.swing.JPanel pnl_Table;
    // End of variables declaration//GEN-END:variables
}
