/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

import java.io.Serializable;

/**
 * Created by jerry on 2017/6/22.
 */

public class MyBillRequest implements Serializable {

    private String pageSize = "20";
    private String page = "1";
    private String payment_method;
    private String invoice_status;
    private String feeType;
    private String article_number;
    private String waybill_number;
    private String consigner;
    private String consigner_phone;
    private String consignee;
    private String consignee_phone;
    private String startTime;
    private String endTime;
    private String initial_station_id;
    private String terminal_station_id;

    private String delivery_method;
    private String batch_number;
    private String ownArticleNumber;

    private String invoice_date;

    @Override
    public String toString() {
        return "MyBillRequest{" +
                "pageSize='" + pageSize + '\'' +
                ", page='" + page + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", invoice_status='" + invoice_status + '\'' +
                ", feeType='" + feeType + '\'' +
                ", article_number='" + article_number + '\'' +
                ", waybill_number='" + waybill_number + '\'' +
                ", consigner='" + consigner + '\'' +
                ", consigner_phone='" + consigner_phone + '\'' +
                ", consignee='" + consignee + '\'' +
                ", consignee_phone='" + consignee_phone + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", initial_station_id='" + initial_station_id + '\'' +
                ", terminal_station_id='" + terminal_station_id + '\'' +
                ", delivery_method='" + delivery_method + '\'' +
                ", batch_number='" + batch_number + '\'' +
                ", ownArticleNumber='" + ownArticleNumber + '\'' +
                ", invoice_date='" + invoice_date + '\'' +
                '}';
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getOwnArticleNumber() {
        return ownArticleNumber;
    }

    public void setOwnArticleNumber(String ownArticleNumber) {
        this.ownArticleNumber = ownArticleNumber;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public String getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(String batch_number) {
        this.batch_number = batch_number;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
    }


    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(String invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getArticle_number() {
        return article_number;
    }

    public void setArticle_number(String article_number) {
        this.article_number = article_number;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getConsigner() {
        return consigner;
    }

    public void setConsigner(String consigner) {
        this.consigner = consigner;
    }

    public String getConsigner_phone() {
        return consigner_phone;
    }

    public void setConsigner_phone(String consigner_phone) {
        this.consigner_phone = consigner_phone;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInitial_station_id() {
        return initial_station_id;
    }

    public void setInitial_station_id(String initial_station_id) {
        this.initial_station_id = initial_station_id;
    }

    public String getTerminal_station_id() {
        return terminal_station_id;
    }

    public void setTerminal_station_id(String terminal_station_id) {
        this.terminal_station_id = terminal_station_id;
    }
}
