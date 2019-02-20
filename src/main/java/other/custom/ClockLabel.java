/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author chuna
 */
public class ClockLabel extends JLabel {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  hh:mm");
    private final SimpleDateFormat sdf_static = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
    private int currentSecond;
    private Calendar calendar;
    Calendar cal = Calendar.getInstance();

    public ClockLabel() {
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        setFont(new Font("Arial", 0, 14));
    }

    public String timeBegin() {
        return sdf_static.format(cal.getTime());
    }

    private void reset() {
        calendar = Calendar.getInstance();
        currentSecond = calendar.get(Calendar.SECOND);
    }

    public void start() {
        reset();
        ScheduledExecutorService worker = Executors.newScheduledThreadPool(3);
        worker.scheduleAtFixedRate(() -> {
            if (currentSecond == 60) {
                reset();
            }
            setText(String.format("%s:%02d", sdf.format(calendar.getTime()), currentSecond));
            currentSecond++;
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }
}
