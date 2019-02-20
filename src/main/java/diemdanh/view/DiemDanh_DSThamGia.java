/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.view;

import sukien.view.*;
import dscanbo.view.DSCanBo_View;
import dssinhvien.view.DSSinhVien_View;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author chuna
 */
public class DiemDanh_DSThamGia extends javax.swing.JPanel {

    private DSCanBo_View dSCanBo_View;
    private DSSinhVien_View dSSinhVien_View;
    private String mask;
    private static int typeTable;
    private static SuKien_View VIEW = SuKien_View.getView();

    public DiemDanh_DSThamGia() {
        initComponents();
    }

    public DiemDanh_DSThamGia(String mask) {
        initComponents();
        this.mask = mask;
        createUI();
    }

    private void createUI() {
        dSCanBo_View = new DSCanBo_View(mask, true);
        dSSinhVien_View = new DSSinhVien_View(mask, true);
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
        removeAll();
        add(dSCanBo_View = new DSCanBo_View(mask, true));
        dSCanBo_View.execute();
        validate();
        repaint();
        typeTable = 1;
        dSCanBo_View.getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!dSCanBo_View.getTable().getHeader().isClicked()) {
                    VIEW.getBtn_XoaDS().setEnabled(true);
                } else {
                    VIEW.getBtn_XoaDS().setEnabled(false);
                }
            }

        });
        dSCanBo_View.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dSCanBo_View.getTable().getSelectedRowCount() > 0) {
                    VIEW.getBtn_XoaDS().setEnabled(true);
                }
            }
        });
    }

    public void showDSSinhVien() {
        removeAll();
        add(dSSinhVien_View = new DSSinhVien_View(mask, true));
        dSSinhVien_View.execute();
        validate();
        repaint();
        typeTable = 2;
        dSSinhVien_View.getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!dSSinhVien_View.getTable().getHeader().isClicked()) {
                    VIEW.getBtn_XoaDS().setEnabled(true);
                } else {
                    VIEW.getBtn_XoaDS().setEnabled(false);
                }
            }

        });
        dSSinhVien_View.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dSSinhVien_View.getTable().getSelectedRowCount() > 0) {
                    VIEW.getBtn_XoaDS().setEnabled(true);
                }
            }

        });
    }

    public void showEmpty() {
        removeAll();
        add(pnl_Empty);
        validate();
        repaint();
        typeTable = 0;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public static int getTypeTable() {
        return typeTable;
    }

    public DSCanBo_View getdSCanBo_View() {
        return dSCanBo_View;
    }

    public DSSinhVien_View getdSSinhVien_View() {
        return dSSinhVien_View;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Empty = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        pnl_Empty.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chưa có danh sách tham dự. Xin mời thêm vào.");
        pnl_Empty.add(jLabel1, java.awt.BorderLayout.CENTER);

        setLayout(new java.awt.GridLayout(1, 0));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnl_Empty;
    // End of variables declaration//GEN-END:variables
}
