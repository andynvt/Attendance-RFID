/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

/**
 *
 * @author chuna
 */
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    public static Time divideTime(Time T1, int d) {
        long t1 = T1.getHours() * 3600 + T1.getMinutes() * 60 + T1.getSeconds();
        long absSeconds = Math.abs(t1 / 2);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return Time.valueOf(positive);
    }

    public static Time betweenTime(Time T1, Time T2) {
        long t1 = T1.getHours() * 3600 + T1.getMinutes() * 60 + T1.getSeconds();
        long t2 = T2.getHours() * 3600 + T2.getMinutes() * 60 + T2.getSeconds();
        long absSeconds = Math.abs(t1 - t2);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return Time.valueOf(positive);
    }

    public static Time minusTime(Time T1, Time add) {
        long t1 = T1.getHours() * 3600 + T1.getMinutes() * 60 + T1.getSeconds();
        long t2 = add.getHours() * 3600 + add.getMinutes() * 60 + add.getSeconds();
        long absSeconds = t1 - t2;
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return Time.valueOf(positive);
    }

    public static Time plusTime(Time T1, Time add) {
        long t1 = T1.getHours() * 3600 + T1.getMinutes() * 60 + T1.getSeconds();
        long t2 = add.getHours() * 3600 + add.getMinutes() * 60 + add.getSeconds();
        long absSeconds = t1 + t2;
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return Time.valueOf(positive);
    }

    public static int compare(java.sql.Date t1, java.sql.Date t2) {
        java.sql.Date d1 = new java.sql.Date(t1.getYear(), t1.getMonth(), t1.getDate());
        java.sql.Date d2 = new java.sql.Date(t2.getYear(), t2.getMonth(), t2.getDate());
        return d1.compareTo(d2);
    }

    public static int compare(Time t1, Time t2) {
        java.sql.Time d1, d2;
        d1 = new Time(t1.getHours(), t1.getMinutes(), 0);
        d2 = new Time(t2.getHours(), t2.getMinutes(), 0);
        return d1.compareTo(d2);
    }

    public static Time between(Time T1, Time T2) {
        String s1, s2;
        s1 = String.valueOf(T1);
        s2 = String.valueOf(T2);
        LocalTime t1 = LocalTime.parse(s1);
        LocalTime t2 = LocalTime.parse(s2);

        Duration d = Duration.between(t2, t1);
        System.out.println(d);
        long seconds = d.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return Time.valueOf(positive);
    }

    public static Timestamp convertStringToTimestamp(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            Date date = (Date) formatter.parse(str_date);
            Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    public static String formatTimestamp(Timestamp ts) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, dd/MM/YYYY - HH:mm:ss");
            Date fechaNueva;
            fechaNueva = format.parse(ts.toString());
            return newFormat.format(fechaNueva);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static String formatTime(Time ts) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm:ss");
            Date fechaNueva;
            fechaNueva = format.parse(ts.toString());
            return newFormat.format(fechaNueva);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    static Date fechaNueva;

    public static String formatDate(java.sql.Date ts) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, dd/MM/YYYY");
            fechaNueva = format.parse(ts.toString());
            return newFormat.format(fechaNueva);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        Time t1 = new Time(12, 15, 0);
        Time t2 = new Time(System.currentTimeMillis());
        Time timeLimit = Util.minusTime(t1, t2);
        System.out.println(t1 + "\n" + t2 + "");
        System.out.println(timeLimit);
    }
}
