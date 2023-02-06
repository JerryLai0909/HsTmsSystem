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
 * Created by jerry on 2017/6/20.
 */

public class BoPointAndManEntity {

    private List<PageListBean> pageList;

    public List<PageListBean> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageListBean> pageList) {
        this.pageList = pageList;
    }

    public static class PageListBean {

        private Long uuid;
        private String point_owner;
        private String point_owner_phone_o;
        private String point_name;
        private int num;

        public Long getUuid() {
            return uuid;
        }

        public void setUuid(Long uuid) {
            this.uuid = uuid;
        }

        public String getPoint_owner() {
            return point_owner;
        }

        public void setPoint_owner(String point_owner) {
            this.point_owner = point_owner;
        }

        public String getPoint_owner_phone_o() {
            return point_owner_phone_o;
        }

        public void setPoint_owner_phone_o(String point_owner_phone_o) {
            this.point_owner_phone_o = point_owner_phone_o;
        }

        public String getPoint_name() {
            return point_name;
        }

        public void setPoint_name(String point_name) {
            this.point_name = point_name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "PageListBean{" +
                    "uuid=" + uuid +
                    ", point_owner='" + point_owner + '\'' +
                    ", point_owner_phone_o='" + point_owner_phone_o + '\'' +
                    ", point_name='" + point_name + '\'' +
                    ", num=" + num +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BoPointAndManEntity{" +
                "pageList=" + pageList +
                '}';
    }
}
