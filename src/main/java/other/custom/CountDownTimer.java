/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class CountDownTimer {

    private static final int TIMER_PERIOD = 1000;
    protected static final int MAX_COUNT = 10;
    private int h, m, s;
    private boolean stop = false;
    private Timer timer;
    private String text;

    public CountDownTimer(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public void start() {
        timer = new Timer(TIMER_PERIOD, (ActionEvent e) -> {
            System.out.println("ssss");
            if (s >= 0) {
                s--;
            } else {
                s = 59;
                if (m >= 0) {
                    m--;
                } else {
                    m = 59;
                    if (h > 0) {
                        h--;
                    } else {
                        if (m == 0 && s == 0) {
                            stop = true;
                        }
                    }
                }
            }
            String hh, mm, ss;
            hh = h < 10 ? "0" + h : h + "";
            mm = m < 10 ? "0" + m : m + "";
            ss = s < 10 ? "0" + s : s + "";
            text = hh + ": " + mm + ": " + ss;
            System.out.println(text);
        });
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public static void main(String[] args) {
        CountDownTimer timer = new CountDownTimer(0, 1, 30);
        timer.start();
        timer.getTimer().start();
    }
}
