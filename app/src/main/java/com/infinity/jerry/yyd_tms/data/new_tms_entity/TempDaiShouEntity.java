/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.new_tms_entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry on 2018/11/9.
 * You are good enough to do everthing
 */
public class TempDaiShouEntity implements Serializable {

    private List<NewBillEntity> dataList;

    @Override
    public String toString() {
        return "TempDaiShouEntity{" +
                "dataList=" + dataList +
                '}';
    }

    public List<NewBillEntity> getDataList() {
        return dataList;
    }

    public void setDataList(List<NewBillEntity> dataList) {
        this.dataList = dataList;
    }
}
