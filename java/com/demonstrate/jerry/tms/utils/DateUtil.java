/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 2/15/17.
 */

public class DateUtil {

    private static SimpleDateFormat sf = null;

    public static String getTimeFormatMD(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":" + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes());
    }

    public static String getCurrentDataYMD() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static String getCUrrentDateYMDHM() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    public static String getCurrentDataYMDHMS() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    public static String getDataWithLongYMD(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static String getdateWithStringHS(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sf = new SimpleDateFormat("HH:mm");
        return sf.format(date);
    }

    public static String getDataWithLongYMDHMS(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    public static String getDataWithLongMDHms(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM-dd HH:mm:ss");
        return sf.format(d);
    }

    public static String getDataWithLongYMDHM(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    public static String getTimetrampWithStringMDHM(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sf = new SimpleDateFormat("MM-dd HH:mm");
        return sf.format(date);
    }

    public static String getTimetrampWithStringYYMDHM(String time) {
        sf = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sf = new SimpleDateFormat("yy-MM-dd HH:mm");
        return sf.format(date);
    }


    public static long getTimetrampWithStringYMD(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getTimetrampWithStringYMDHMS(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getStringDataWithDataYMd(Date date) {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }

    public static String getStringDataWithDataYMdHMS(Date date) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(date);
    }


}
