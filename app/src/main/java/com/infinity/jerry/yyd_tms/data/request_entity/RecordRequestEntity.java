/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

/**
 * Created by jerry on 2018/7/31.
 * You are good enough to do everthing
 */
public class RecordRequestEntity {

    private String amount;
    private String collectOperation;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCollectOperation() {
        return collectOperation;
    }

    public void setCollectOperation(String collectOperation) {
        this.collectOperation = collectOperation;
    }
}
