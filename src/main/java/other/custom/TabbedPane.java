/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.other.custom;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author chuna
 */
public class TabbedPane {

    public static void setTitleTab(int index, String name, JTabbedPane tabbed) {
        Font font = new Font("Arial", 1, 14);
        JLabel tab1 = new JLabel(name);
        tab1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tab1.setFont(font);
        tabbed.setTabComponentAt(index, tab1);
    }
}
