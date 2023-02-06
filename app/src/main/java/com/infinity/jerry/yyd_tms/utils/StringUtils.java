/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by jerry on 2017/6/29.
 */

public class StringUtils {

    public static String getNullablString(String string) {
        return string == null ? "" : string;
    }

    public static String getZeroableStringForDouble(Double sth) {
        if (sth == null || sth == 0D) {
            return "0";
        }
        return ZDoubleFormat.zFormat(String.valueOf(sth));
    }

    public static String getNullStringForDouble(Double sth) {
        if (sth == null || sth == 0D) {
            return "";
        }
        return String.valueOf(sth);
    }

    public static String getZeroableStringForLong(Long sth) {
        if (sth == null || sth == 0L) {
            return "0";
        }
        return String.valueOf(sth);
    }

    public static double getDoubleNotNull(Double sth) {
        if (sth != null) {
            return sth;
        }
        return 0.0;
    }

    /**
     * 字符串转byte数组
     * */
    public static byte[] strTobytes(String str){
        byte[] b=null,data=null;
        try {
            b = str.getBytes("utf-8");
            data=new String(b,"utf-8").getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }
    /**
     * byte数组拼接
     * */
    public static   byte[] byteMerger(byte[] byte_1, byte[] byte_2){
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }
    public static byte[] strTobytes(String str ,String charset){
        byte[] b=null,data=null;
        try {
            b = str.getBytes("utf-8");
            data=new String(b,"utf-8").getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }


}
