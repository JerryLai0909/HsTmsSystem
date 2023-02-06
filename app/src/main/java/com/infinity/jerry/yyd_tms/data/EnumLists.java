/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017/6/21.
 */

public class EnumLists {

    private static List<ValueKeyPair> billEnumLists;
    private static List<ValueKeyPair> feeEnumLists;
    private static List<ValueKeyPair> payMethodEnmuLists;
//
//        "value": "已开票",
//        "key": 1
//        "value": "已装车",
//        "key": 2
//        "value": "运输中",
//        "key": 3
//        "value": "已到货",
//        "key": 4
//        "value": "确认收货",
//        "key": 5
//        "value": "中转",
//        "key": 6
//        "value": "运单作废",
//        "key": 9

    public static List<ValueKeyPair> getBillEnmu() {
        if (billEnumLists == null) {
            billEnumLists = new ArrayList<>();
            billEnumLists.add(new ValueKeyPair("已开票", "1"));
            billEnumLists.add(new ValueKeyPair("已装车", "2"));
            billEnumLists.add(new ValueKeyPair("运输中", "3"));
            billEnumLists.add(new ValueKeyPair("已到货", "4"));
            billEnumLists.add(new ValueKeyPair("确认收货", "5"));
            billEnumLists.add(new ValueKeyPair("中转", "6"));
            billEnumLists.add(new ValueKeyPair("运单作废", "9"));
        }

        return billEnumLists;
    }

//        "value": "运费",
//        "key": "freight"
//        "value": "代收费",
//        "key": "collection_fee"
//        "value": "保价费",
//        "key": "valuation_fee"
//        "value": "送货费",
//        "key": "delivery_fee"
//        "value": "垫付款",
//        "key": "advance"
//        "value": "接货费",
//        "key": "receiving_fee"
//        "value": "装卸费",
//        "key": "handling_fee"
//        "value": "包装费",
//        "key": "packing_fee"
//        "value": "上楼费",
//        "key": "upstair_fee"
//        "value": "叉吊费",
//        "key": "forklift_fee"
//        "value": "现返费",
//        "key": "return_fee"
//        "value": "欠返费",
//        "key": "under_charge_fee"
//        "value": "仓储费",
//        "key": "warehousing_fee"

    public static List<ValueKeyPair> getFeeEnum() {
        if (feeEnumLists == null) {
            feeEnumLists = new ArrayList<>();
            feeEnumLists.add(new ValueKeyPair("运费", "freight"));
            feeEnumLists.add(new ValueKeyPair("代收费", "collection_fee"));
            feeEnumLists.add(new ValueKeyPair("", ""));
            feeEnumLists.add(new ValueKeyPair("", ""));
            feeEnumLists.add(new ValueKeyPair("", ""));
            feeEnumLists.add(new ValueKeyPair("", ""));
            feeEnumLists.add(new ValueKeyPair("", ""));
            feeEnumLists.add(new ValueKeyPair("", ""));
            feeEnumLists.add(new ValueKeyPair("", ""));
        }
        return feeEnumLists;
    }
//        "value": "现付",
//            "key": 1
//        "value": "到付",
//            "key": 2
//        "value": "欠付",
//            "key": 3
//        "value": "回付",
//            "key": 4
//        "value": "月结",
//            "key": 5
//        "value": "货款扣",
//            "key": 6

    public static List<ValueKeyPair> getPayEnum() {
        if (payMethodEnmuLists == null) {
            payMethodEnmuLists = new ArrayList<>();
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
            payMethodEnmuLists.add(new ValueKeyPair("", ""));
        }
        return payMethodEnmuLists;
    }

    private static class ValueKeyPair {
        private String value;
        private String key;

        private ValueKeyPair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "ValueKeyPair{" +
                    "value='" + value + '\'' +
                    ", key='" + key + '\'' +
                    '}';
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
