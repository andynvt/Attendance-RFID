package other.table;

import java.awt.event.ItemEvent;
import javax.swing.AbstractButton;
import javax.swing.JTable;

public class ItemListener implements java.awt.event.ItemListener {

    JTable jTable;

    public ItemListener(JTable jTable) {
        this.jTable = jTable;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        Object source = ie.getSource();
        if (source instanceof AbstractButton == false) {
            return;
        }
        boolean checked = ie.getStateChange() == ItemEvent.SELECTED;
        for (int x = 0, y = jTable.getRowCount(); x < y; x++) {
            jTable.setValueAt(checked, x, ((CheckBoxHeaderRenderer) source).column);
        }
    }

}
