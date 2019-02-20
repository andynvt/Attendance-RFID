/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextField;

/**
 *
 * @author chuna
 */
public class CountTimer {

    private int h = 0, m = 0, s = 60;
    private boolean stop = false;
    private String text;
    private JTextField label;
    private java.util.Timer timer = new java.util.Timer(); //new timer

    public CountTimer(JTextField label) {
        this.label = label;
    }

    public CountTimer(JTextField label, int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
        this.label = label;
        label.setText(text);
    }
    private String hh = "", mm = "", ss = "";

    public void start() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (s > 0 && s <= 59) {
                    s--;
                } else if (s == 0 && m > 0) {
                    s = 59;
                    m--;
                } else if (s == 0 && m == 0 && h > 0) {
                    s = 59;
                    m = 59;
                    h--;
                } else if (s == 0 && m == 0 && h == 0) {
                    stop = true;
                    timer.cancel();
                }
                hh = h < 10 ? "0" + h : h + "";
                mm = m < 10 ? "0" + m : m + "";
                ss = s < 10 ? "0" + s : s + "";
                text = hh + ":" + mm + ":" + ss;
                label.setText(text);
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void stop() {
        timer.cancel();
        stop = true;
    }

    public void setLabel(JTextField label) {
        this.label = label;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void setTime(int h, int m, int s) {
        setH(h);
        setM(m);
        setS(s);
    }

    public void setTime(Time time) {
        h = time.getHours();
        m = time.getMinutes();
        s = time.getSeconds();
        setH(h);
        setM(m);
        setS(s);
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

}
