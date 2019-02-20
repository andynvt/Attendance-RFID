package run;

import java.awt.HeadlessException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chuna
 */
public class Run extends JFrame {

    public Run(JPanel panel) throws HeadlessException {
        super();
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Run() {

    }

    public String getPathFileSave() {
        JFileChooser c = new JFileChooser();
        String path = "", dir = "";
        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            path = c.getSelectedFile().getName();
            dir = c.getCurrentDirectory().toString();
            return dir + "\\" + path;
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            return "";
        }
        return "";
    }

    public static void main(String[] args) {
        String s = "choHiep.xls";
        String sx = "choHiep";
        String[] tokens = s.split("\\.(?=[^\\.]+$)");
        System.out.println(tokens[1]);

    }
}
