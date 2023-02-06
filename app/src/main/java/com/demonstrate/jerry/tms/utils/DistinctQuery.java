/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils;

import com.infinity.jerry.yyd_tms.data.database.BillingDrawDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2018/5/23.
 * You are good enough to do everthing
 */

public class DistinctQuery {

    public static List<BillingDrawDataBase> distincetReciveName(List<BillingDrawDataBase> list) {
        String temp = "";
        List<BillingDrawDataBase> dataList = new ArrayList<>();
        if (list == null) return dataList;
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).getReciveName();
            if (str != null) {
                if (!temp.contains(str)) {
                    temp += str + ",";
                    dataList.add(list.get(i));
                }
            }
        }
        return dataList;
    }

    public static List<BillingDrawDataBase> distinctSendName(List<BillingDrawDataBase> list) {
        String temp = "";
        List<BillingDrawDataBase> dataList = new ArrayList<>();
        if (list == null) return new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).getSendName();
            if (str != null) {
                if (!temp.contains(str)) {
                    temp += str + ",";
                    dataList.add(list.get(i));
                }
            }
        }
        return dataList;
    }

    public static List<BillingDrawDataBase> distinctRecivePhone(List<BillingDrawDataBase> list) {
        String temp = "";
        List<BillingDrawDataBase> dataList = new ArrayList<>();
        if (list == null) return dataList;
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).getRecivePhone();
            if (str != null) {
                if (!temp.contains(str)) {
                    temp += str + ",";
                    dataList.add(list.get(i));
                }
            }
        }
        return dataList;
    }

    public static List<BillingDrawDataBase> distinctSendPhone(List<BillingDrawDataBase> list) {
        String temp = "";
        List<BillingDrawDataBase> dataList = new ArrayList<>();
        if (list == null) return dataList;
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).getSendPhone();
            if (str != null) {
                if (!temp.contains(str)) {
                    temp += str + ",";
                    dataList.add(list.get(i));
                }
            }
        }
        return dataList;
    }

    public static List<BillingDrawDataBase> distincetRemark(List<BillingDrawDataBase> list) {
        String temp = "";
        List<BillingDrawDataBase> dataList = new ArrayList<>();
        if (list == null) return dataList;
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).getRemark();
            if (str != null) {
                if (!temp.contains(str)) {
                    temp += str + ",";
                    dataList.add(list.get(i));
                }
            }
        }
        return dataList;
    }
}
