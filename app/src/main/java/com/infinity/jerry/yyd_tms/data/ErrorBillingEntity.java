/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry on 2017/7/17.
 */

public class ErrorBillingEntity implements Serializable{

    private PagebeanBean pagebean;
    private List<ErrorEntity> pageList;

    public PagebeanBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PagebeanBean pagebean) {
        this.pagebean = pagebean;
    }

    public List<ErrorEntity> getPageList() {
        return pageList;
    }

    public void setPageList(List<ErrorEntity> pageList) {
        this.pageList = pageList;
    }

    public static class PagebeanBean {
        /**
         * curPage : 1
         * pageCount : 1
         * rowsCount : 9
         * pageSize : 10
         */

        private int curPage;
        private int pageCount;
        private int rowsCount;
        private int pageSize;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getRowsCount() {
            return rowsCount;
        }

        public void setRowsCount(int rowsCount) {
            this.rowsCount = rowsCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class ErrorEntity implements Serializable {

        private Long uuid;
        private Long billing_uuid;
        private String record_date;
        private Long record_point_uuid;
        private Long recorder_uuid;
        private String point_name;
        private String recorder_name;
        private String unusual_type;
        private String unusual_des;
        private Long processor;
        private String processor_name;
        private String processed_date;
        private String processed_results;
        private Integer status;
        private String statusStr;
        private String waybill_number;
        private String consigner;
        private String consigner_phone;
        private String consignee;
        private String consignee_phone;
        private String goods_name;
        private Integer quantity;



        public Long getUuid() {
            return uuid;
        }

        public void setUuid(Long uuid) {
            this.uuid = uuid;
        }

        public Long getBilling_uuid() {
            return billing_uuid;
        }

        public void setBilling_uuid(Long billing_uuid) {
            this.billing_uuid = billing_uuid;
        }

        public String getRecord_date() {
            return record_date;
        }

        public void setRecord_date(String record_date) {
            this.record_date = record_date;
        }

        public Long getRecord_point_uuid() {
            return record_point_uuid;
        }

        public void setRecord_point_uuid(Long record_point_uuid) {
            this.record_point_uuid = record_point_uuid;
        }

        public Long getRecorder_uuid() {
            return recorder_uuid;
        }

        public void setRecorder_uuid(Long recorder_uuid) {
            this.recorder_uuid = recorder_uuid;
        }

        public String getPoint_name() {
            return point_name;
        }

        public void setPoint_name(String point_name) {
            this.point_name = point_name;
        }

        public String getRecorder_name() {
            return recorder_name;
        }

        public void setRecorder_name(String recorder_name) {
            this.recorder_name = recorder_name;
        }

        public String getUnusual_type() {
            return unusual_type;
        }

        public void setUnusual_type(String unusual_type) {
            this.unusual_type = unusual_type;
        }

        public String getUnusual_des() {
            return unusual_des;
        }

        public void setUnusual_des(String unusual_des) {
            this.unusual_des = unusual_des;
        }

        public Long getProcessor() {
            return processor;
        }

        public void setProcessor(Long processor) {
            this.processor = processor;
        }

        public String getProcessor_name() {
            return processor_name;
        }

        public void setProcessor_name(String processor_name) {
            this.processor_name = processor_name;
        }

        public String getProcessed_date() {
            return processed_date;
        }

        public void setProcessed_date(String processed_date) {
            this.processed_date = processed_date;
        }

        public String getProcessed_results() {
            return processed_results;
        }

        public void setProcessed_results(String processed_results) {
            this.processed_results = processed_results;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatusStr() {
            return statusStr;
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
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

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
