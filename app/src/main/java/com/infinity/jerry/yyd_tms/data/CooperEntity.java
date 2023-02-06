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

public class CooperEntity {

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
        private int company_uuid;
        private int partner_company_id;
        private int partner_point_id;
        private int partner_status;
        private String company_name;
        private String point_name;

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getPoint_name() {
            return point_name;
        }

        public void setPoint_name(String point_name) {
            this.point_name = point_name;
        }

        public int getUuid() {
            return uuid;
        }

        public void setUuid(int uuid) {
            this.uuid = uuid;
        }

        public int getCompany_uuid() {
            return company_uuid;
        }

        public void setCompany_uuid(int company_uuid) {
            this.company_uuid = company_uuid;
        }

        public int getPartner_company_id() {
            return partner_company_id;
        }

        public void setPartner_company_id(int partner_company_id) {
            this.partner_company_id = partner_company_id;
        }

        public int getPartner_point_id() {
            return partner_point_id;
        }

        public void setPartner_point_id(int partner_point_id) {
            this.partner_point_id = partner_point_id;
        }

        public int getPartner_status() {
            return partner_status;
        }

        @Override
        public String toString() {
            return "PageListBean{" +
                    "uuid=" + uuid +
                    ", company_uuid=" + company_uuid +
                    ", partner_company_id=" + partner_company_id +
                    ", partner_point_id=" + partner_point_id +
                    ", partner_status=" + partner_status +
                    ", company_name='" + company_name + '\'' +
                    ", point_name='" + point_name + '\'' +
                    '}';
        }

        public void setPartner_status(int partner_status) {
            this.partner_status = partner_status;
        }
    }

    @Override
    public String toString() {
        return "CooperEntity{" +
                "pagebean=" + pagebean +
                ", pageList=" + pageList +
                '}';
    }
}
