/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.table;

import app.view.App_View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import other.table.CustomTable.MyCustomTableModel;
import static resources.Resources.back_Icon;
import static resources.Resources.doubleLeft_Icon;
import static resources.Resources.doubleRight_Icon;
import static resources.Resources.forward_Icon;

/**
 *
 * @author chuna
 */
public class PanelTable extends JPanel {

    private Object[][] data;
    private CustomTable table;

    private transient TableRowSorter<? extends TableModel> sorter;
    private final Font font = new Font("Arial", 0, 14);
    private static JComboBox<String> comboBoxItem;
    private final JButton first = new JButton();
    private final JButton prev = new JButton();
    private final JButton next = new JButton();
    private final JButton last = new JButton();
    private Action enterAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int v = Integer.parseInt(txt_NumPage.getText());
                if (v > 0 && v <= maxPageIndex) {
                    currentPageIndex = v;
                }
            } catch (NumberFormatException ex) {
            }
            initFilterAndButtons();
        }
    };
    private TableUpdateTask tableUpdateTask;
    private final JPanel box;
    private final JPanel po;
    private JLabel lblName;
    private final JLabel lblNumRow;
    private JTextField txt_NumPage;
    private final JLabel label = new JLabel("/ 1");
    private JPanel panel;
    private int itemsPerPage = 25;
    private int maxPageIndex;
    private int currentPageIndex = 1;
    private final String[] columnNames;
    private JScrollPane jScrollPane;

    public PanelTable(Object[][] data, String[] columnNames) {
        super(new BorderLayout());
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        this.po = new JPanel();
        PanelTable.comboBoxItem = new JComboBox<>(new String[]{"25", "50", "100", "500", "Tất cả"});
        this.panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.box = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.txt_NumPage = new JTextField(2);
        this.lblNumRow = new JLabel("Số dòng: ");
        this.data = data;
        this.columnNames = columnNames;
        table = new CustomTable(data, columnNames);
        sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        createUI();
    }

    public PanelTable(Object[][] data, String[] columnNames, String nameTable) {
        super(new BorderLayout());
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App_View.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        this.po = new JPanel();
        PanelTable.comboBoxItem = new JComboBox<>(new String[]{"25", "50", "100", "500", "Tất cả"});
        this.panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.box = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.txt_NumPage = new JTextField(2);
        this.lblName = new JLabel(nameTable + " | ");
        this.lblNumRow = new JLabel("Số dòng: ");
        this.data = data;
        this.columnNames = columnNames;
        table = new CustomTable(data, columnNames);
        sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        createUI();
    }

    public void createUI() {
        panel.setOpaque(false);
        first.setIcon(doubleLeft_Icon);
        last.setIcon(doubleRight_Icon);
        prev.setIcon(back_Icon);
        next.setIcon(forward_Icon);
        Arrays.asList(first, last, prev, next).forEach((t) -> {
            t.setPreferredSize(new Dimension(25, t.getPreferredSize().height));
            t.setContentAreaFilled(false);
            t.setBorderPainted(false);
            t.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    t.setOpaque(true);
                    t.setBackground(Color.decode("#BDBDBD"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    t.setOpaque(false);
                    t.setBackground(null);
                }

            });
        });
        table.setFillsViewportHeight(true);
        table.setRowSorter(sorter);
        po.setOpaque(false);
        po.add(txt_NumPage);
        po.add(label);
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        if (lblName != null) {
            box.add(lblName);
            lblName.setFont(new Font("Arial", 0, 18));
        }
        Arrays.asList(lblNumRow, comboBoxItem, first, prev, po, next, last, panel).forEach((c) -> {
            box.add(c);
        });
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        txt_NumPage.getInputMap(JComponent.WHEN_FOCUSED).put(enter, "Enter");
        txt_NumPage.getActionMap().put("Enter", enterAction);

        ActionListener jumpActionListener = e -> {
            Object c = e.getSource();
            if (first.equals(c)) {
                currentPageIndex = 1;
            } else if (prev.equals(c)) {
                currentPageIndex -= 1;
            } else if (next.equals(c)) {
                currentPageIndex += 1;
            } else if (last.equals(c)) {
                currentPageIndex = maxPageIndex;
            }
            initFilterAndButtons();
        };
        Arrays.asList(first, prev, next, last).forEach((b) -> {
            b.addActionListener(jumpActionListener);
        });
        comboBoxItem.addActionListener((e) -> {
            itemsPerPage = !comboBoxItem.getSelectedItem().toString().equals("Tất cả")
                    ? Integer.parseInt(comboBoxItem.getSelectedItem().toString()) : data.length;
            initFilterAndButtons();
        });
        add(box, BorderLayout.NORTH);
        tableUpdateTask = new TableUpdateTask(data.length, itemsPerPage);
        lblNumRow.setFont(font);
        comboBoxItem.setFont(font);
        label.setFont(font);
        txt_NumPage.setFont(font);
        po.setFont(font);
        setOpaque(false);
        jScrollPane = new JScrollPane();
        jScrollPane.setBorder(new LineBorder(Color.decode("#e1e1e1"), 1, true));
    }

    public void executeTable() {
        tableUpdateTask.execute();
        jScrollPane.remove(table);
        jScrollPane.setViewportView(table);
        add(jScrollPane);
        validate();
        repaint();
    }

    public String getNameTable() {
        return lblName.getText().split("|")[0];
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public CustomTable getTable() {
        return table;
    }

    public MyCustomTableModel getCustomTableModel() {
        return table.getTableModel();
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public JComboBox<String> getComboBoxItem() {
        return comboBoxItem;
    }

    public static void setComboBoxItem(JComboBox<String> comboBoxItem) {
        PanelTable.comboBoxItem = comboBoxItem;
    }

    public JTextField getTxt_NumPage() {
        return txt_NumPage;
    }

    public void setTxt_NumPage(JTextField txt_NumPage) {
        this.txt_NumPage = txt_NumPage;
    }

    public static void setSelectAll() {
        comboBoxItem.setSelectedIndex(4);
    }

    class TableUpdateTask extends LoadTask {

        @Override
        public Object[][] getData() {
            return data;
        }

        protected TableUpdateTask(int max, int itemsPerPage) {
            super(max, itemsPerPage);
        }

        @Override
        protected void process(List<List<Object[]>> chunks) {
            if (isCancelled()) {
                return;
            }
            chunks.forEach((list) -> {
                list.forEach((o) -> {
                    ((DefaultTableModel) table.getModel()).addRow(o);
                });
            });
            initFilterAndButtons();
        }

        @Override
        public void done() {
            table.setEnabled(true);
        }
    }

    public void initFilterAndButtons() {
        int rowCount = ((DefaultTableModel) table.getModel()).getRowCount();
        maxPageIndex = rowCount / itemsPerPage + (rowCount % itemsPerPage == 0 ? 0 : 1);
        sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                int ti = currentPageIndex - 1;
                int ei = entry.getIdentifier();
                return ti * itemsPerPage <= ei && ei < ti * itemsPerPage + itemsPerPage;
            }
        });
        first.setEnabled(currentPageIndex > 1);
        prev.setEnabled(currentPageIndex > 1);
        next.setEnabled(currentPageIndex < maxPageIndex);
        last.setEnabled(currentPageIndex < maxPageIndex);
        txt_NumPage.setText(Integer.toString(currentPageIndex));
        label.setText(String.format("/ %d", maxPageIndex));
    }
}
