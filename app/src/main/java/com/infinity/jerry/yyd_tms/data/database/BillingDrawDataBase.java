/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.database;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/6/27.
 */

public class BillingDrawDataBase extends DataSupport {

    private int id;
    private String sendName;
    private String sendPhone;
    private String reciveName;
    private String recivePhone;
    private int billWeight;
    private String remark;


    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getReciveName() {
        return reciveName;
    }

    public void setReciveName(String reciveName) {
        this.reciveName = reciveName;
    }

    public String getRecivePhone() {
        return recivePhone;
    }

    public void setRecivePhone(String recivePhone) {
        this.recivePhone = recivePhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BillingDrawDataBase{" +
                "id=" + id +

                ", billWeight=" + billWeight +
                ", remark='" + remark + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillWeight() {
        return billWeight;
    }

    public void setBillWeight(int billWeight) {
        this.billWeight = billWeight;
    }

}
