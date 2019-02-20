package diemdanh.other.table;

import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class TableValue {

    public Object[] getValue(JTable table, DefaultTableModel tableModel) {
        int col = tableModel.getColumnCount();
        int row = table.getSelectedRow();
        Object[] o = new Object[col];
        for (int i = 0; i < col; i++) {
            o[i] = new Object();
            o[i] = tableModel.getValueAt(row, i);
        }
        return o;
    }

    public Object[] getValueList(String row, DefaultTableModel tableModel, int col) {
        int gRow = tableModel.getRowCount();
        int cols = tableModel.getColumnCount();

        Object[] tb = new Object[gRow];
        Object[] o = new Object[cols];
        for (int i = 0; i < gRow; i++) {
            tb[i] = new Object();
            tb[i] = tableModel.getValueAt(i, col);
            if (row.equals(tb[i])) {
                for (int j = 0; j < cols; j++) {
                    o[j] = new Object();
                    o[j] = tableModel.getValueAt(i, j);
                }
            }
        }
        return o;
    }

    public void filterTool(String query, JTable tb) {
        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tb.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(query));
    }

    public void deleteRow(DefaultTableModel tableModels, ArrayList rows) {
        for (int i = 0; i < rows.size(); i++) {
            tableModels.removeRow((int) rows.get(i));
            System.err.println(rows.get(i));
        }

    }

    public DefaultTableModel listModel(DefaultTableModel tableModel, int col) {
        DefaultTableModel listModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int row = tableModel.getRowCount();
        String[] colsName = {""};
        listModel.setColumnIdentifiers(colsName);
        Object[] os = {"Tất cả"};
        listModel.addRow(os);
        for (int r = 0; r < row; r++) {
            Object[] o = new Object[row];
            o[0] = tableModel.getValueAt(r, col);
            listModel.addRow(o);
        }
        return listModel;
    }

    public static void addRow(JTable table, Object[] strings) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(strings);
    }

    public void deleteRows(JTable table, int col, ArrayList list) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean checked = (Boolean) model.getValueAt(i, col);
            if (checked != null && checked) {
                Object id = table.getValueAt(i, col + 1).toString();
                list.add(id);
                model.removeRow(i);
                i--;
            }
        }
    }

    public boolean isSelectedRows(JTable table, int col) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean checked = (Boolean) model.getValueAt(i, col);
            if (checked != null && checked) {
                return true;
            }
        }
        return false;
    }

    public static Object[] getUserAt(JTable table, int row) throws URISyntaxException {
        int col = 9;
        Object[] os = new Object[col - 1];
        for (int i = 0; i < col - 1; i++) {
            os[i] = table.getValueAt(row, i + 1);
        }
        return os;
    }

    public static Object[] getBorrowAt(JTable table, int row) throws URISyntaxException {
        int col = 9;
        Object[] os = new Object[col - 1];
        for (int i = 0; i < col - 1; i++) {
            os[i] = table.getValueAt(row, i + 1);
        }
        return os;
    }
}
