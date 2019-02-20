/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import other.table.CustomTable;

/**
 *
 * @author chuna
 */
public class FormatTextField {

    public static void formatSearchField(JTextComponent txt_Search, JTable table) {
        String s = txt_Search.getText();
        txt_Search.setForeground(Color.decode("#95a5a6"));
        txt_Search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiem();
            }

            private void timKiem() {
                if (!txt_Search.getText().equals(s)) {
                    table.setRowSorter(CustomTable.Filter(txt_Search.getText(), table.getModel()));
                }
            }
        });
        txt_Search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txt_Search.getText().isEmpty() || txt_Search.getText().equals(s)) {
                    txt_Search.setCaretPosition(0);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (txt_Search.getText().isEmpty() || txt_Search.getText().equals(s)) {
                    txt_Search.setCaretPosition(0);
                }
            }
        });
        txt_Search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_Search.getText().isEmpty() || txt_Search.getText().equals(s)) {
                    txt_Search.setCaretPosition(0);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_Search.getText().isEmpty() || txt_Search.getText().equals(s)) {
                    txt_Search.setText(s);
                    txt_Search.setForeground(Color.decode("#95a5a6"));
                }
            }
        });
        txt_Search.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (txt_Search.getText().equals(s)) {
                    txt_Search.setText("");
                }
                txt_Search.setForeground(Color.black);
            }
        });
    }
}
