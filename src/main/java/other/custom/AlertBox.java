/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.other.custom;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JFrame;

/**
 *
 * @author chuna
 */
public class AlertBox extends JFrame {

    public static int OK_YES = 1;
    public static int NO_CANCEL = 0;

    public static int showMessage(Component pa, String content, String title, Icon icon) {
        return OK_YES;
    }
}
