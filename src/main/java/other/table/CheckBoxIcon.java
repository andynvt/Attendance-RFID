/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.table;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

/**
 *
 * @author chuna
 */
public class CheckBoxIcon implements Icon {

    private final JCheckBox check;

    public CheckBoxIcon(JCheckBox check) {
        this.check = check;
    }

    @Override
    public int getIconWidth() {
        return check.getPreferredSize().width;
    }

    @Override
    public int getIconHeight() {
        return check.getPreferredSize().height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        SwingUtilities.paintComponent(
                g, check, (Container) c, x, y, getIconWidth(), getIconHeight());
    }
}
