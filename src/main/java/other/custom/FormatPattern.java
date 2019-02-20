/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author chuna
 */
public class FormatPattern {

    public static CompoundBorder getRoundBorder(int radius, Color color) {
        RoundedBorder round = new RoundedBorder(color);
        EmptyBorder emptyBorder = new EmptyBorder(0, 5, 5, 0);
        return new CompoundBorder(round, emptyBorder);
    }

    public static DateEditor getDateEditor(JSpinner timeSpinner, String format) {
        SpinnerDateModel smb = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        timeSpinner.setModel(smb);
        if (format != null) {
            return new JSpinner.DateEditor(timeSpinner, format);
        }
        return null;
    }

    public static void formatSpinner(JSpinner timeSpinner, String format) {
        SpinnerDateModel smb = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        timeSpinner.setModel(smb);
        DateEditor dateEditor;
        if (format != null) {
            dateEditor = new JSpinner.DateEditor(timeSpinner, format);
        } else {
            dateEditor = new JSpinner.DateEditor(timeSpinner, "dd/MM/yy HH:mm");
        }
        timeSpinner.setEditor(dateEditor);
    }
}
