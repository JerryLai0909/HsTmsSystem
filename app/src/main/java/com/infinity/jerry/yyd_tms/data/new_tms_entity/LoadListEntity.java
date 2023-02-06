/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.new_tms_entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2018/9/26.
 * You are good enough to do everthing
 */
public class LoadListEntity {

    private int id;
    private String endPoint;
    private int count;
    private int quantity;

    private boolean isChoose = false;
    private boolean isOpen;

    private List<NewBillEntity> billEntityList = new ArrayList<>();

    @Override
    public String toString() {
        return "LoadListEntity{" +
                "id=" + id +
                ", endPoint='" + endPoint + '\'' +
                ", count=" + count +
                ", quantity=" + quantity +
                ", isChoose=" + isChoose +
                ", isOpen=" + isOpen +
                ", billEntityList=" + billEntityList +
                '}';
    }

    public List<NewBillEntity> getBillEntityList() {
        return billEntityList;
    }

    public void setBillEntityList(List<NewBillEntity> billEntityList) {
        this.billEntityList = billEntityList;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
