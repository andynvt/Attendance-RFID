/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.table;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author chuna
 */
public class HeaderCheckBoxHandler implements TableModelListener {

    private final JTable table;

    public HeaderCheckBoxHandler(JTable table) {
        this.table = table;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 0) {
            int mci = 0;
            int vci = table.convertColumnIndexToView(mci);
            TableColumn column = table.getColumnModel().getColumn(vci);
            Object title = column.getHeaderValue();
            if (!Status.INDETERMINATE.equals(title)) {
                column.setHeaderValue(Status.INDETERMINATE);
            } else {
                int selected = 0, deselected = 0;
                TableModel m = table.getModel();
                for (int i = 0; i < m.getRowCount(); i++) {
                    if (Boolean.TRUE.equals(m.getValueAt(i, mci))) {
                        selected++;
                    } else {
                        deselected++;
                    }
                }
                if (selected == 0) {
                    column.setHeaderValue(Status.DESELECTED);
                } else if (deselected == 0) {
                    column.setHeaderValue(Status.SELECTED);
                } else {
                    return;
                }
            }
            table.getTableHeader().repaint();
        }
    }
}
