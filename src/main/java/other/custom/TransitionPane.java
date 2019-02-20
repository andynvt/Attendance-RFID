/*
 * To change pa license header, choose License Headers in Project Properties.
 * To change pa template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import other.table.PanelTable;

/**
 *
 * @author chuna
 */
public class TransitionPane {

    private static final JPanel container = new JPanel(new GridLayout());
    private static JDialog dialog = new JDialog();

    public static JDialog getDialog() {
        return dialog;
    }

    public static void replacePane(JPanel pa, JComponent replace) {
        pa.removeAll();
        pa.add(replace);
        pa.validate();
        pa.repaint();
    }

    public static void replacePane(JPanel pa, JComponent replace, PanelTable table) {
        pa.removeAll();
        pa.add(replace);
        table.executeTable();
        pa.validate();
        pa.repaint();
    }

    public static void replacePane(JPanel newPane, JPanel oldPane, GroupLayout layout) {
        layout.replace(oldPane, newPane);
    }

    public static int getComponentIndex(Component component) {
        if (component != null && component.getParent() != null) {
            Container c = component.getParent();
            for (int i = 0; i < c.getComponentCount(); i++) {
                if (c.getComponent(i) == component) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void setLocationRelativeTo(Component c) {
        dialog.setLocationRelativeTo(c);
    }

    public static void setLocation(Point p) {
        dialog.setLocation(p);
    }

    public static void setLocation(int x, int y) {
        dialog.setLocation(x, y);
    }

    public static Point getLocation() {
        return dialog.getLocation();
    }

    public static int getWidth() {
        return dialog.getWidth();
    }

    public static Dimension getSize() {
        return dialog.getSize();
    }

    public static void closeDialogBox() {
        if (dialog.isShowing()) {
            dialog.dispose();
        }

    }

    public static void setAlwaysOnTop(boolean b) {
        dialog.setAlwaysOnTop(b);
    }

    public static void showDialogBox(Container pa, JComponent content, String title, ImageIcon icon) {
        if (icon != null) {
            dialog.setIconImage(icon.getImage());
        }
        dialog.setLayout(new GridLayout());
        dialog.getContentPane().removeAll();
        dialog.getContentPane().add(content);
        dialog.getContentPane().validate();
        dialog.getContentPane().repaint();
        dialog.setTitle(title);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setAlwaysOnTop(true);
//        closeOutDialog(true);
        dialog.setLocationRelativeTo(pa);
        dialog.setVisible(true);
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dialog.dispose();
                }
            }
        });
    }

//    public static void showDialogBox(Container pa, JDialog dialog, JComponent content, String title, ImageIcon icon) {
//        if (icon != null) {
//            dialog.setIconImage(icon.getImage());
//        }
//        GroupLayout layout = new GroupLayout(dialog.getContentPane());
//        dialog.getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addComponent(container, GroupLayout.DEFAULT_SIZE,
//                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addComponent(container, GroupLayout.DEFAULT_SIZE,
//                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//        container.removeAll();
//        container.add(content);
//        container.validate();
//        container.repaint();
//
//        dialog.repaint();
//
//        dialog.setTitle(title);
//
//        dialog.setResizable(false);
//        dialog.pack();
//        dialog.setAlwaysOnTop(true);
//        dialog.setLocationRelativeTo(pa);
//        dialog.setVisible(true);
//    }
    public static void closeOutDialog(boolean close) {
        TransitionPane.getDialog().addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                if (close) {
                    TransitionPane.closeDialogBox();
                } else {
                    TransitionPane.getDialog().setVisible(true);
                }
            }
        });
    }

    public static void repaint(Container instance) {
        instance.revalidate();
        instance.repaint();
    }

    public static FocusListener closeClickOut(JFrame frame) {
        return new FocusListener() {
            private boolean gained = false;

            @Override
            public void focusGained(FocusEvent e) {
                gained = true;
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (gained) {
                    frame.dispose();
                }
            }
        };
    }

}
