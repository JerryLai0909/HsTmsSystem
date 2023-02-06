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
 * Created by jerry on 2018/8/23.
 * You are good enough to do everthing
 */
public class FirstInfo {
    private boolean last;
    private int totalPages;
    private int totalElements;
    private Object sort;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
    private List<ContentBeanX> content;

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

    public List<ContentBeanX> getContent() {
        return content;
    }

    public void setContent(List<ContentBeanX> content) {
        this.content = content;
    }

    public static class ContentBeanX {
        /**
         * id : 4
         * createdTs : null
         * lastModifiedTs : null
         * pointId : 2505
         * content : {"area":"华富路","consigner":"美现","consignee":"浩锐","consigner_phone":null}
         */

        private int id;
        private Object createdTs;
        private Object lastModifiedTs;
        private int pointId;
        private ContentBean content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreatedTs() {
            return createdTs;
        }

        public void setCreatedTs(Object createdTs) {
            this.createdTs = createdTs;
        }

        public Object getLastModifiedTs() {
            return lastModifiedTs;
        }

        public void setLastModifiedTs(Object lastModifiedTs) {
            this.lastModifiedTs = lastModifiedTs;
        }

        public int getPointId() {
            return pointId;
        }

        public void setPointId(int pointId) {
            this.pointId = pointId;
        }

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * area : 华富路
             * consigner : 美现
             * consignee : 浩锐
             * consigner_phone : null
             */

            private String area;
            private String consigner;
            private String consignee_phone;

            @Override
            public String toString() {
                return "ContentBean{" +
                        "area='" + area + '\'' +
                        ", consigner='" + consigner + '\'' +
                        ", consignee_phone='" + consignee_phone + '\'' +
                        ", consignee='" + consignee + '\'' +
                        ", consigner_phone='" + consigner_phone + '\'' +
                        '}';
            }

            public String getConsignee_phone() {
                return consignee_phone;
            }

            public void setConsignee_phone(String consignee_phone) {
                this.consignee_phone = consignee_phone;
            }

            private String consignee;
            private String consigner_phone;

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getConsigner() {
                return consigner;
            }

            public void setConsigner(String consigner) {
                this.consigner = consigner;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getConsigner_phone() {
                return consigner_phone;
            }

            public void setConsigner_phone(String consigner_phone) {
                this.consigner_phone = consigner_phone;
            }
        }
    }
}
