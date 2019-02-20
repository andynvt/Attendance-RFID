/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.other.custom;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JLabel;

/**
 *
 * @author chuna
 */
public class BlinkLabel extends JLabel {

    private static final long serialVersionUID = 1L;

    private static final int BLINKING_RATE = 1000; // in ms

    private boolean blinkingOn = true;

    public BlinkLabel(String text) {
        super(text);
        Timer timer = new Timer(BLINKING_RATE, new TimerListener(this));
        timer.setInitialDelay(0);
        timer.start();
    }

    public void setBlinking(boolean flag) {
        this.blinkingOn = flag;
    }

    public boolean getBlinking(boolean flag) {
        return this.blinkingOn;
    }

    private class TimerListener implements ActionListener {

        private final BlinkLabel bl;
        private final Color bg;
        private final Color fg;
        private boolean isForeground = true;

        public TimerListener(BlinkLabel bl) {
            this.bl = bl;
            fg = bl.getForeground();
            bg = bl.getBackground();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            if (bl.blinkingOn) {
//                if (isForeground) {
//                    bl.setForeground(fg);
//                } else {
//                    bl.setForeground(bg);
//                }
//                isForeground = !isForeground;
//            } else {
//                if (isForeground) {
//                    bl.setForeground(fg);
//                    isForeground = false;
//                }
//            }
        }

    }

    // --- for testing 
    private static void createAndShowUI() {
        JFrame frame = new JFrame("BlinkLabel");
        final BlinkLabel bl = new BlinkLabel("I'm blinking!");

        frame.getContentPane().setLayout(new java.awt.FlowLayout());
        frame.getContentPane().add(bl);

        JButton b = new JButton("toogle blink");
        b.addActionListener((ActionEvent ae) -> {
            bl.blinkingOn = !bl.blinkingOn;
        });
        frame.getContentPane().add(b);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            createAndShowUI();
        });
    }
    // ---
}
