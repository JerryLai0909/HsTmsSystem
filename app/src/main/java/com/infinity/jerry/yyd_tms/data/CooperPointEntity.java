/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.util.List;

/**
 * Created by jerry on 2017/6/21.
 */

public class CooperPointEntity {


    private PageCountEntity pagebean;
    private List<PageListBean> pageList;

    public PageCountEntity getPagebean() {
        return pagebean;
    }

    public void setPagebean(PageCountEntity pagebean) {
        this.pagebean = pagebean;
    }

    public List<PageListBean> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageListBean> pageList) {
        this.pageList = pageList;
    }

    public static class PageListBean {

        private int uuid;
        private int logistics_uuid;
        private String point_name;
        private String point_addr;

        public int getUuid() {
            return uuid;
        }

        public void setUuid(int uuid) {
            this.uuid = uuid;
        }

        public int getLogistics_uuid() {
            return logistics_uuid;
        }

        public void setLogistics_uuid(int logistics_uuid) {
            this.logistics_uuid = logistics_uuid;
        }

        public String getPoint_name() {
            return point_name;
        }

        public void setPoint_name(String point_name) {
            this.point_name = point_name;
        }

        public String getPoint_addr() {
            return point_addr;
        }

        public void setPoint_addr(String point_addr) {
            this.point_addr = point_addr;
        }
    }
}
