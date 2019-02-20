/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

/**
 *
 * @author chuna
 */
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;

public class GridView extends JPanel {

    RadioButtonUI ui = new RadioButtonUI();
    int pageSize = 5;

    Model model = new Model();
    TableRowSorter sorter = new TableRowSorter(model);
    Box box = Box.createHorizontalBox();

    public GridView() {
        super(new BorderLayout());
        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer tcr, int row,
                    int column) {
                Component c = super.prepareRenderer(tcr, row, column);
                if (isRowSelected(row)) {
                    c.setForeground(getSelectionForeground());
                    c.setBackground(getSelectionBackground());
                } else {
                    c.setForeground(getForeground());
                    c.setBackground((row % 2 == 0) ? Color.lightGray
                            : getBackground());
                }
                return c;
            }
        };
        table.setIntercellSpacing(new Dimension());
        table.setShowGrid(false);
        table.setRowSorter(sorter);
        showPages(10, 1);

        add(new JScrollPane(table));
        add(box, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(320, 240));
    }

    private void showPages(final int itemsPerPage, final int currentPageIndex) {
        sorter.setRowFilter(filter(itemsPerPage, currentPageIndex - 1));
        ArrayList l = new ArrayList();

        int startPageIndex = currentPageIndex - pageSize;
        if (startPageIndex <= 0) {
            startPageIndex = 1;
        }
        int maxPageIndex = (model.getRowCount() / itemsPerPage) + 1;
        int endPageIndex = currentPageIndex + pageSize - 1;
        if (endPageIndex > maxPageIndex) {
            endPageIndex = maxPageIndex;
        }

        if (currentPageIndex > 1) {
            l
                    .add(createRadioButtons(itemsPerPage, currentPageIndex - 1,
                            "Prev"));
        }
        for (int i = startPageIndex; i <= endPageIndex; i++) {
            l.add(createLinks(itemsPerPage, currentPageIndex, i - 1));
        }
        if (currentPageIndex < maxPageIndex) {
            l
                    .add(createRadioButtons(itemsPerPage, currentPageIndex + 1,
                            "Next"));
        }

        box.removeAll();
        ButtonGroup bg = new ButtonGroup();
        box.add(Box.createHorizontalGlue());
        for (Object r : l) {
            box.add((Component) r);
            bg.add((AbstractButton) r);
        }
        box.add(Box.createHorizontalGlue());
        box.revalidate();
        box.repaint();
        l.clear();
    }

    private JRadioButton createLinks(final int itemsPerPage, final int current,
            final int target) {
        JRadioButton radio = new JRadioButton("" + (target + 1)) {
            protected void fireStateChanged() {
                ButtonModel model = getModel();
                if (!model.isEnabled()) {
                    setForeground(Color.GRAY);
                } else if (model.isPressed() && model.isArmed()) {
                    setForeground(Color.GREEN);
                } else if (model.isSelected()) {
                    setForeground(Color.RED);
                }
                super.fireStateChanged();
            }
        };
        radio.setForeground(Color.BLUE);
        radio.setUI(ui);
        if (target + 1 == current) {
            radio.setSelected(true);
        }
        radio.addActionListener((ActionEvent e) -> {
            showPages(itemsPerPage, target + 1);
        });
        return radio;
    }

    private JRadioButton createRadioButtons(final int itemsPerPage,
            final int target, String title) {
        JRadioButton radio = new JRadioButton(title);
        radio.setForeground(Color.BLUE);
        radio.setUI(ui);
        radio.addActionListener((ActionEvent e) -> {
            showPages(itemsPerPage, target);
        });
        return radio;
    }

    private RowFilter filter(final int itemsPerPage,
            final int target) {
        return new RowFilter() {
            @Override
            public boolean include(
                    Entry entry) {
                int ei = (int) entry.getIdentifier();
                return (target * itemsPerPage <= ei && ei < target
                        * itemsPerPage + itemsPerPage);
            }
        };
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Table");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GridView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class RadioButtonUI extends BasicRadioButtonUI {

    public Icon getDefaultIcon() {
        return null;
    }

    private static Dimension size = new Dimension();
    private static Rectangle rec1 = new Rectangle();
    private static Rectangle rec2 = new Rectangle();
    private static Rectangle rec3 = new Rectangle();

    public synchronized void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        Font f = c.getFont();
        g.setFont(f);
        FontMetrics fm = c.getFontMetrics(f);

        Insets i = c.getInsets();
        size = b.getSize(size);
        rec1.x = i.left;
        rec1.y = i.top;
        rec1.width = size.width - (i.right + rec1.x);
        rec1.height = size.height - (i.bottom + rec1.y);
        rec2.x = rec2.y = rec2.width = rec2.height = 0;
        rec3.x = rec3.y = rec3.width = rec3.height = 0;

        String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(),
                null, b.getVerticalAlignment(), b.getHorizontalAlignment(), b
                .getVerticalTextPosition(), b
                        .getHorizontalTextPosition(), rec1, rec2, rec3, 0);

        if (c.isOpaque()) {
            g.setColor(b.getBackground());
            g.fillRect(0, 0, size.width, size.height);
        }
        if (text == null) {
            return;
        }
        g.setColor(b.getForeground());
        if (!model.isSelected() && !model.isPressed() && !model.isArmed()
                && b.isRolloverEnabled() && model.isRollover()) {
            g.drawLine(rec1.x, rec1.y + rec1.height, rec1.x + rec1.width,
                    rec1.y + rec1.height);
        }
        View v = (View) c.getClientProperty(BasicHTML.propertyKey);
        if (v != null) {
            v.paint(g, rec3);
        } else {
            paintText(g, b, rec3, text);
        }
    }
}

class Model extends DefaultTableModel {

    Model() {
        JTable table = new JTable(this);
        addColumn("Name");
        addColumn("Address");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/register", "root", "root");
            String query = "select * from data";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                addRow(new Object[]{name, address});
            }
        } catch (Exception e) {
        }
    }
}
