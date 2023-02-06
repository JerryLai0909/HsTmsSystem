/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

/**
 * Created by jerry on 2017/7/24.
 */

public class TransEntity {

    private Long to_point_uuid;
    private String point_name;
    private String transfer_date;
    private Double transfer_fee;
    private Long point_uuid;
    private String operation_point_name;
    private Long operator_id;
    private String user_name;
    private Integer transfer_type;
    private String transit_destination;
    private Long billing_uuid;
    private String remark;

    public Long getTo_point_uuid() {
        return to_point_uuid;
    }

    public void setTo_point_uuid(Long to_point_uuid) {
        this.to_point_uuid = to_point_uuid;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    public Double getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(Double transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    public Long getPoint_uuid() {
        return point_uuid;
    }

    public void setPoint_uuid(Long point_uuid) {
        this.point_uuid = point_uuid;
    }

    public String getOperation_point_name() {
        return operation_point_name;
    }

    public void setOperation_point_name(String operation_point_name) {
        this.operation_point_name = operation_point_name;
    }

    public Long getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(Long operator_id) {
        this.operator_id = operator_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(Integer transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getTransit_destination() {
        return transit_destination;
    }

    public void setTransit_destination(String transit_destination) {
        this.transit_destination = transit_destination;
    }

    public Long getBilling_uuid() {
        return billing_uuid;
    }

    public void setBilling_uuid(Long billing_uuid) {
        this.billing_uuid = billing_uuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TransEntity{" +
                "to_point_uuid=" + to_point_uuid +
                ", point_name='" + point_name + '\'' +
                ", transfer_date='" + transfer_date + '\'' +
                ", transfer_fee=" + transfer_fee +
                ", point_uuid=" + point_uuid +
                ", operation_point_name='" + operation_point_name + '\'' +
                ", operator_id=" + operator_id +
                ", user_name='" + user_name + '\'' +
                ", transfer_type=" + transfer_type +
                ", transit_destination='" + transit_destination + '\'' +
                ", billing_uuid=" + billing_uuid +
                ", remark='" + remark + '\'' +
                '}';
    }
}
