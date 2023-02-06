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
 * Created by jerry on 2018/9/30.
 * You are good enough to do everthing
 */
public class NewArrivalEntity {

    private boolean last;
    private int totalPages;
    private int totalElements;
    private Object sort;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
    private List<ContentBean> content;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
                    ", arrivalStatus='" + arrivalStatus + '\'' +
                    ", isChoose=" + isChoose +
                    ", driverPhone='" + driverPhone + '\'' +
                    ", startStation='" + startStation + '\'' +
                    ", count=" + count +
                    ", quantity=" + quantity +
                    '}';
        }

        private int id;
        private String plateNumber;
        private String batchNumber;
        private String driverName;
        private String loadingTime;
        private String arrivalStatus;
        private boolean isChoose;
        private String driverPhone;
        private String startStation;
        private int count;
        private int quantity;


        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choosed) {
            isChoose = choosed;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getStartStation() {
            return startStation;
        }

        public void setStartStation(String startStation) {
            this.startStation = startStation;
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

        public String getArrivalStatus() {
            return arrivalStatus;
        }

        public void setArrivalStatus(String arrivalStatus) {
            this.arrivalStatus = arrivalStatus;
        }
    }
}
