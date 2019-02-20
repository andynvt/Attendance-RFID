package other.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class CustomTable extends JTable {

    private ListSelectionModel selectModel;
    private MyCustomTableModel tableModel;
    private Object[] columnNames;

    public CustomTable(Object[][] data, String[] columnNames) {
        row_ColorLight = Color.decode("#ffffff");
        row_ColorDark = Color.decode("#CFD8DC");
        header_Color = Color.decode("#76AFEB");
        header_Font = Color.decode("#ffffff");
        font_Color = Color.decode("#2e3233");
        bg_selection = Color.decode("#BBDEFB");
        this.columnNames = columnNames;
        init();

    }

    public CustomTable(DefaultTableModel tableModel) {
        super(tableModel);
        row_ColorLight = Color.decode("#ffffff");
        row_ColorDark = Color.decode("#CFD8DC");
        header_Color = Color.decode("#76AFEB");
        header_Font = Color.decode("#ffffff");
        font_Color = Color.decode("#2e3233");
        bg_selection = Color.decode("#BBDEFB");
        init();
    }

    public CustomTable() {
        row_ColorLight = Color.decode("#ffffff");
        row_ColorDark = Color.decode("#CFD8DC");
        header_Color = Color.decode("#76AFEB");
        header_Font = Color.decode("#ffffff");
        font_Color = Color.decode("#2e3233");
        bg_selection = Color.decode("#BBDEFB");
        init();
    }

    public Object[][] tableModel2Object() {
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        int row = model.getRowCount();
        int col = model.getColumnCount();
        Object[][] oses = new Object[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                oses[i][j] = model.getValueAt(i, j);
            }
        }
        System.out.println(oses.length);
        return oses;
    }

    public boolean isSelected() {
        return getSelectedRow() != -1;
    }

    public ListSelectionModel getSelectModel() {
        return selectModel;
    }

    public MyCustomTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(MyCustomTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void setSelectModel(ListSelectionModel selectModel) {
        this.selectModel = selectModel;
    }

    private void init() {
        tableModel = new MyCustomTableModel(this, columnNames);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JTableHeader th = getTableHeader();
        th.setPreferredSize(new Dimension(100, 40));
        th.setBackground(header_Color);
        th.setForeground(header_Font);
        th.setFont(new Font("Arial", 1, 15));
        th.setOpaque(false);
        setFillsViewportHeight(true);
        setShowGrid(false);
        setRowHeight(40);
        setFont(new Font("Arial", 0, 14));
        setAutoCreateRowSorter(true);
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(javax.swing.JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(bg_selection);
                    c.setForeground(font_Color);
                } else {
                    c.setBackground(row % 2 == 0 ? row_ColorDark : row_ColorLight);
                }
                if (Boolean.valueOf(getValueAt(row, 0).toString())) {
                    c.setBackground(bg_selection);
                    c.setForeground(font_Color);
                }
                return c;
            }
        });
        setEditingColumn(0);
        setModel(dataModel);
    }

    public void setWidth(int col, int width) {
        setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel = getColumnModel();
        colModel.getColumn(col).setPreferredWidth(width);
    }

    public void addRow(Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        int row = data.length;
        for (int i = 0; i < row; i++) {
            model.addRow(data[i]);
        }
    }

    public void addRow(Object[] data) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(data);
    }

    public Object[][] getTableData() {
        DefaultTableModel dtm = (DefaultTableModel) this.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                tableData[i][j] = dtm.getValueAt(i, j);
            }
        }
        return tableData;
    }

    private int count = 0;

    public int getCheckedRow(int col) {
        count = 0;
        for (int i = 0; i < getRowCount(); i++) {
            if (isSelectedRows(i, col)) {
                count++;
            }
        }
        return count;
    }

    public boolean isSelectedRows(int row, int col) {
        Boolean checked = (Boolean) this.getValueAt(row, col);
        return checked != null && checked;
    }

    public boolean isAllSelectedRow() {
        int b = this.getSelectedRowCount();
        return b == this.getRowCount();
    }

    public JScrollPane getScrollPane() {
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    public int[] getSizeColumn(int col) {
        int w, h;
        w = getColumnModel().getColumn(col).getPreferredWidth();
        h = getColumnModel().getColumn(col).getPreferredWidth();
        int[] size = {w, h};
        return size;
    }
    boolean flag = true;

    public void toggleColumn(int col) {
        if (flag) {
            showColumn(0);
            flag = false;
        } else {
            hideColumn(0);
            flag = true;
        }
    }

    public void hideColumn(int index) {
        TableColumn column = getColumnModel().getColumn(index);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setWidth(0);
        column.setPreferredWidth(0);
        doLayout();
    }

    public void showColumn(int index) {
        TableColumn column = getColumnModel().getColumn(index);
        final int width = 80;
        column.setMinWidth(15);
        column.setMaxWidth(width);
        column.setWidth(width);
        column.setPreferredWidth(width);
        doLayout();
    }

    TableColumn tc;
    CheckBoxHeaderRenderer header;
    CheckBoxRowRenderer checkBoxRenderer;

    public void setCheckBox(int col) {
        tc = getColumnModel().getColumn(col);
        header = new CheckBoxHeaderRenderer(new ItemListener(this) {
        }, tc.getHeaderValue().toString());
        checkBoxRenderer = new CheckBoxRowRenderer();
        tc.setWidth(50);
        tc.setCellEditor(getDefaultEditor(Boolean.class));
        tc.setCellRenderer(checkBoxRenderer);
        tc.setHeaderRenderer(header);
    }

    public void setCellCheckBox(int col) {
        TableColumn tc = getColumnModel().getColumn(col);
        CheckBoxRowRenderer checkBoxRow = new CheckBoxRowRenderer();
        tc.setWidth(50);
        tc.setCellEditor(getDefaultEditor(Boolean.class));
        tc.setCellRenderer(checkBoxRow);
    }

    public void addButtonCell(Object ob) {
        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        getColumn(ob).setCellRenderer(buttonRenderer);
    }

    public static TableRowSorter Filter(String query, TableModel model) {
        return Filter(query, model, -1);
    }

    public static TableRowSorter Filter(String query, TableModel model, int column) {
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        RowFilter<TableModel, Object> rf = null;
        try {
            if (column > -1) {
                rf = RowFilter.regexFilter(query, column);
            } else {
                rf = RowFilter.regexFilter(query);
            }
        } catch (Exception e) {
        }
        sorter.setRowFilter(rf);
        return sorter;
    }

    public Color getRow_ColorLight() {
        return row_ColorLight;
    }

    public void setRow_ColorLight(Color row_ColorLight) {
        this.row_ColorLight = row_ColorLight;
    }

    public Color getRow_ColorDark() {
        return row_ColorDark;
    }

    public void setRow_ColorDark(Color row_ColorDark) {
        this.row_ColorDark = row_ColorDark;
    }

    public Color getHeader_Color() {
        return header_Color;
    }

    public void setHeader_Color(Color header_Color) {
        this.header_Color = header_Color;
    }

    public Color getHeader_Font() {
        return header_Font;
    }

    public void setHeader_Font(Color header_Font) {
        this.header_Font = header_Font;
    }

    public Color getFont_Color() {
        return font_Color;
    }

    public void setFont_Color(Color font_Color) {
        this.font_Color = font_Color;
    }

    public Color getBg_selection() {
        return bg_selection;
    }

    public void setBg_selection(Color bg_selection) {
        this.bg_selection = bg_selection;

    }

    public CheckBoxHeaderRenderer getHeader() {
        return header;
    }

    public void setHeader(CheckBoxHeaderRenderer header) {
        this.header = header;
    }
    private Color row_ColorLight;
    private Color row_ColorDark;
    private Color header_Color;
    private Color header_Font;
    private Color font_Color;
    private Color bg_selection;

    public class MyCustomTableModel extends DefaultTableModel {

        private CustomTable table;
        private Object[] columnNames;

        public MyCustomTableModel(CustomTable table, Object[] columnNames) {
            this.table = table;
            this.columnNames = columnNames;
            setColumnIdentifiers(columnNames);
            table.setModel(this);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column <= 0;
        }

        public void setDataVector(Object[][] dataVector) {
            super.setDataVector(dataVector, columnNames);
        }
    }
}
