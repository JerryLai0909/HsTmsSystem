/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

/**
 * Created by jerry on 2018/11/9.
 * You are good enough to do everthing
 */
public class BarCodeEntity {

    private String barCode;

    public BarCodeEntity(String barCode) {
        this.barCode = barCode;
    }

    @Override
    public String toString() {
        return "BarCodeEntity{" +
                "barCode='" + barCode + '\'' +
                '}';
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
