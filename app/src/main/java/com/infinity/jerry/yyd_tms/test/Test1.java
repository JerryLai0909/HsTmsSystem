/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.test;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017/7/5.
 */

public class Test1 {

    private List<List<Goods.Property>> proList = new ArrayList<>();

    private List<Goods> goodsList = new ArrayList<>();

    private int totalCount = 0;

    int a = 0;
    int b = 0;
    int c = 0;

    private void shit() {

        List<Goods.Property> list = new ArrayList<>();

        Goods.Property xx1 = new Goods.Property();
        xx1.setKey("颜色");
        xx1.setVlaue("蓝");
        Goods.Property xx2 = new Goods.Property();
        xx2.setKey("颜色");
        xx2.setVlaue("绿");
        list.add(xx1);
        list.add(xx2);

        List<Goods.Property> list2 = new ArrayList<>();

        Goods.Property xx3 = new Goods.Property();
        xx3.setKey("尺码");
        xx3.setVlaue("17");
        Goods.Property xx4 = new Goods.Property();
        xx4.setKey("尺码");
        xx4.setVlaue("18");
        Goods.Property xx5 = new Goods.Property();
        xx5.setKey("尺码");
        xx5.setVlaue("19");
        list2.add(xx3);
        list2.add(xx4);
        list2.add(xx5);

        List<Goods.Property> list3 = new ArrayList<>();
        Goods.Property xx6 = new Goods.Property();
        xx6.setKey("尺码");
        xx6.setVlaue("17");
        Goods.Property xx7 = new Goods.Property();
        xx7.setKey("尺码");
        xx7.setVlaue("17");
        Goods.Property xx8 = new Goods.Property();
        xx8.setKey("尺码");
        xx8.setVlaue("17");
        Goods.Property xx9 = new Goods.Property();
        xx9.setKey("尺码");
        xx9.setVlaue("17");
        list3.add(xx6);
        list3.add(xx7);
        list3.add(xx8);
        list3.add(xx9);
        proList.add(list);
        proList.add(list2);
        proList.add(list3);
    }

    private void boom() {
        for (int m = 0; m < proList.size(); m++) {
            totalCount *= proList.get(m).size();
        }
        for (int k = 0; k < proList.size(); k++) {
            for (int i = 0; i < totalCount; i++) {
                goodsList.get(i).getLists().add(proList.get(k).get(totalCount % proList.get(k).size()));
            }
        }

        Logger.i(goodsList.toString());

    }
}
