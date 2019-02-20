/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;

/**
 *
 * @author chuna
 */
public class CustomBalloonTip extends BalloonTip {

    private int h = 5;
    private Timer timer;
    private boolean showNotify = false;

    public CustomBalloonTip(JComponent attachedComponent, String contents) {
        super(attachedComponent, new JLabel(contents), new RoundedBalloonStyle(5, 5, Color.WHITE, Color.blue.brighter()),
                BalloonTip.Orientation.RIGHT_BELOW,
                BalloonTip.AttachLocation.CENTER,
                12, 10, false);
        setFont(new Font("Arial", 0, 20));
    }

    public CustomBalloonTip(JComponent attachedComponent, JLabel contents) {
        super(attachedComponent, contents, new RoundedBalloonStyle(5, 5, Color.WHITE, Color.blue.brighter()),
                BalloonTip.Orientation.RIGHT_BELOW,
                BalloonTip.AttachLocation.ALIGNED,
                12, 10, false);
    }

    public void showNotify(String str) {
        showNotify(str, true);
    }

    public void showNotify(String str, int time) {
        showNotify(str, true);
    }

    public void showNotify(String str, boolean visible) {
        this.setTextContents(str);
        this.setVisible(visible);
        h = 5;
        timer = new Timer(1000, (ActionEvent e) -> {
            h--;
            if (h == 0) {
                this.setVisible(false);
                timer.stop();
            }
        });
        timer.start();
        showNotify = true;
    }

    public void showNotify(String str, boolean visible, int seconds) {
        this.setTextContents(str);
        this.setVisible(visible);
        h = seconds;
        timer = new Timer(1000, (ActionEvent e) -> {
            h--;
            if (h == 0) {
                this.setVisible(false);
                timer.stop();
            }
        });
        timer.start();
        showNotify = true;
    }

    public void hideNotify() {
        this.setVisible(false);
        showNotify = false;
    }

    public boolean isShowNotify() {
        return showNotify;
    }
}
