/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author chuna
 */
public class HeaderRenderer extends JCheckBox implements TableCellRenderer {

    public HeaderRenderer(JTableHeader header, final int targetColumnIndex) {
        super((String) null);
        setOpaque(false);
        setFont(header.getFont());
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // compiled code
                JTableHeader header = (JTableHeader) e.getSource();
                JTable table = header.getTable();
                TableColumnModel columnModel = table.getColumnModel();
                int vci = columnModel.getColumnIndexAtX(e.getX());
                int mci = table.convertColumnIndexToModel(vci);
                if (mci == targetColumnIndex) {
                    TableColumn column = columnModel.getColumn(vci);
                    Object v = column.getHeaderValue();
                    boolean b = Status.DESELECTED.equals(v);
                    TableModel m = table.getModel();
                    for (int i = 0; i < m.getRowCount(); i++) {
                        m.setValueAt(b, i, mci);
                    }
                    column.setHeaderValue(b ? Status.SELECTED : Status.DESELECTED);
                }
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable tbl, Object val, boolean isS, boolean hasF, int row, int col) {
        if (val instanceof Status) {
            switch ((Status) val) {
                case SELECTED:
                    setSelected(true);
                    setEnabled(true);
                    break;
                case DESELECTED:
                    setSelected(false);
                    setEnabled(true);
                    break;
                case INDETERMINATE:
                    setSelected(true);
                    setEnabled(false);
                    break;
            }
        } else {
            setSelected(false);
            setEnabled(true);
        }
        TableCellRenderer r = tbl.getTableHeader().getDefaultRenderer();
        JLabel l = (JLabel) r.getTableCellRendererComponent(tbl, null, isS, hasF, row, col);

        l.setIcon(new CheckBoxIcon(this));
        l.setText(null);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
}
//<ins>

