/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author chuna
 */
public class Alert {

    private static JTextArea area;

    public static void showWarning(Component pa, String content) {
        customJOptionPane(content);
        JOptionPane opt = new JOptionPane(area, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
        final JDialog dlg = opt.createDialog(pa, "Thông báo");
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                dlg.dispose();

            } catch (InterruptedException th) {

            }
        }).start();
        dlg.setVisible(true);
    }

    public static void showSuccess(Component pa, String content) {
        showSuccess(pa, content, 3000);
    }

    public static void showSuccess(Component pa, String content, int miliseconds) {
        customJOptionPane(content);
        JOptionPane opt = new JOptionPane(area, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
        final JDialog dlg = opt.createDialog(pa, "Thành công");
        new Thread(() -> {
            try {
                Thread.sleep(miliseconds);
                dlg.dispose();

            } catch (InterruptedException th) {

            }
        }).start();
        dlg.setVisible(true);
    }

    public static void showFailure(Component pa, String content) {
        customJOptionPane(content);
        JOptionPane opt = new JOptionPane(area, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
        final JDialog dlg = opt.createDialog(pa, "Thất bại");
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                dlg.dispose();

            } catch (InterruptedException th) {

            }
        }).start();
        dlg.setVisible(true);
    }

    public static int showInformationDialog(String content, String title) {
        return showInformationDialog(null, content, title);
    }

    public static int showInformationDialog(Component parent, String content, String title) {
        customJOptionPane(content);
        return JOptionPane.showConfirmDialog(parent, area, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/main/resourcesimage/Info_32px_Color.png"));
    }

    public static int showQuestionDialog(String content, String title) {
        return showQuestionDialog(null, content, title);
    }

    public static int showQuestionDialog(Component parent, String content, String title) {
        customJOptionPane(content);
        return JOptionPane.showConfirmDialog(parent, area, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/main/resources/image/Help_Color_32px.png"));
    }

    public static int showErrorDialog(String content, String title) {
        return showErrorDialog(null, content, title);
    }

    public static int showErrorDialog(Component parent, String content, String title) {
        customJOptionPane(content);
        return JOptionPane.showConfirmDialog(parent, area, title, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, new ImageIcon("src/main/resources/image/Error_32px.png"));
    }

    public static void showMessageDialog(String content, String title) {
        showMessageDialog(null, content, title);
    }

    public static void showMessageDialog(Component parent, String content, String title) {
        customJOptionPane(content);
        JOptionPane.showMessageDialog(parent, area, title, JOptionPane.ERROR_MESSAGE, new ImageIcon("src/main/resources/image/High Priority_Color_32px"));
    }

    private static void customJOptionPane(String content) {
        UIManager.put("OptionPane.background", Color.decode("#ffffff"));
        UIManager.put("Panel.background", Color.decode("#ffffff"));
        UIManager.put("Button.background", Color.decode("#E0E0E0"));
        UIManager.put("Button.foreground", Color.decode("#2e3233"));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#e1e1e1"), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        area = new JTextArea(content);
        area.setOpaque(false);
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        area.setFont(new Font("Arial", 0, 14));
    }

    public static void showMessageDialog(Component pa, String text) {
        UIManager.put("OptionPane.background", Color.decode("#ffffff"));
        UIManager.put("Panel.background", Color.decode("#ffffff"));
        UIManager.put("Button.background", Color.decode("#E0E0E0"));
        UIManager.put("Button.foreground", Color.decode("#2e3233"));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#e1e1e1"), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        JTextArea jTextArea = new JTextArea(text);
        jTextArea.setBackground(Color.white);
        jTextArea.setRows(5);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setOpaque(false);
        jTextArea.setOpaque(false);
        jTextArea.setEditable(false);
        jTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jTextArea.setFont(new Font("Arial", 0, 14));
        //        JOptionPane.showMessageDialog(pa, scrollPane, "Thất bại", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/main/resources/image/Error_32px.png"));
//        Object[] name = new Object[]{new JButton("Đóng")};
//        JOptionPane opt = new JOptionPane(
//                scrollPane, JOptionPane.INFORMATION_MESSAGE,
//                JOptionPane.DEFAULT_OPTION, null, name
//        );
//        final JDialog dlg = opt.createDialog(pa, "Thất bại");
//        ((JButton) name[0]).addActionListener((ActionEvent e) -> {
//            dlg.setVisible(false);
//        });
//        ((JButton) name[0]).setFont(new Font("Arial", 0, 14));
//        dlg.setVisible(true);
        JOptionPane.showMessageDialog(pa, scrollPane, "Thất bại", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/main/resources/image/High Priority_Color_32px"));
    }
    public static int YES = JOptionPane.YES_OPTION;
    public static int YES_NO = JOptionPane.YES_NO_OPTION;
    public static int YES_NO_CANCEL = JOptionPane.YES_NO_CANCEL_OPTION;
    public static int NO = JOptionPane.NO_OPTION;
    public static int OK = JOptionPane.OK_OPTION;
    public static int OK_CANCEL = JOptionPane.OK_CANCEL_OPTION;
    public static int CANCEL = JOptionPane.CANCEL_OPTION;
    public static int CLOSE = JOptionPane.CLOSED_OPTION;
}
