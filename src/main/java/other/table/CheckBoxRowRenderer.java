/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author chuna
 */
public class CheckBoxRowRenderer extends JCheckBox implements TableCellRenderer {

    private final Color row_ColorLight = Color.decode("#ffffff");
    private final Color row_ColorDark = Color.decode("#CFD8DC");
    private final Color font_Color = Color.decode("#2e3233");
    private final Color bg_selection = Color.decode("#BBDEFB");

    CheckBoxRowRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(bg_selection);
            setForeground(font_Color);
        } else {
            setBackground(row % 2 == 0 ? row_ColorDark : row_ColorLight);
        }
        if (Boolean.valueOf(table.getValueAt(row, 0).toString())) {
            setBackground(bg_selection);
            setForeground(font_Color);
        }
        setSelected((value != null && ((Boolean) value)));
        return this;
    }
}
