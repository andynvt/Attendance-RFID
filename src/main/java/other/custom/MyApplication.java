/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import static java.awt.AWTEventMulticaster.add;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author chuna
 */
public class MyApplication extends JFrame {

    JTextField numberField = new JTextField(15);
    JTextField resultField = new JTextField(20);

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.setVisible(true);
    }

    private MyApplication() {
        super("Number doubler");
        resultField.setEditable(false);
        add(new JLabel("Number to double (n):"), BorderLayout.WEST);
        add(numberField, BorderLayout.CENTER);
        add(resultField, BorderLayout.SOUTH);
        JButton butt = new JButton("Calculate");
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numberStr = numberField.getText();
                numberStr = numberStr.trim();
                double n = Double.parseDouble(numberStr);
                n *= 2;
                resultField.setText("n * 2 = " + String.format("%.2f", n));
            }
        });
        add(butt, BorderLayout.EAST);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
}
