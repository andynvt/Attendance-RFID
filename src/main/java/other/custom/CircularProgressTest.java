/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.other.custom;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 *
 * @author chuna
 */
public class CircularProgressTest {

    public JComponent makeUI() {
        JProgressBar progress = new JProgressBar();
        // use JProgressBar#setUI(...) method
        progress.setUI(new ProgressCircleUI());
        progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        progress.setStringPainted(true);
        progress.setFont(progress.getFont().deriveFont(24f));
        progress.setForeground(Color.ORANGE);
        progress.setValue(200);
        (new Timer(50, e -> {
            int iv = Math.max(0, progress.getValue() - 1);
            progress.setValue(iv);
        })).start();

        JPanel p = new JPanel();
        p.add(progress);
        return p;
    }

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.getContentPane().add(new CircularProgressTest().makeUI());
            f.setSize(320, 240);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
