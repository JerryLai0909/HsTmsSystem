/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.util.List;

/**
 * Created by jerry on 2018/9/27.
 * You are good enough to do everthing
 */
public class NewRecordsEntity {

    @Override
    public String toString() {
        return "NewRecordsEntity{" +
                "date='" + date + '\'' +
                ", content=" + content +
                '}';
    }

    private String date;
    private List<ContentBean> content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", plateNumber='" + plateNumber + '\'' +
                    ", batchNumber='" + batchNumber + '\'' +
                    ", driverName='" + driverName + '\'' +
                    ", loadingTime='" + loadingTime + '\'' +
                    ", count=" + count +
                    ", quantity=" + quantity +
                    '}';
        }

        private int id;
        private String plateNumber;
        private String batchNumber;
        private String driverName;
        private String loadingTime;
        private int count;
        private int quantity;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getBatchNumber() {
            return batchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getLoadingTime() {
            return loadingTime;
        }

        public void setLoadingTime(String loadingTime) {
            this.loadingTime = loadingTime;
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
}
