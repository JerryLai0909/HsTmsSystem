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
 * Created by jerry on 2017/6/21.
 */

public class CooperCompanyEntity {

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

    public static class PageListBean implements Serializable {

        private int uuid;
        private String company_name;
        private Object company_addr;

        @Override
        public String toString() {
            return "PageListBean{" +
                    "uuid=" + uuid +
                    ", company_name='" + company_name + '\'' +
                    ", company_addr=" + company_addr +
                    '}';
        }

        public int getUuid() {
            return uuid;
        }

        public void setUuid(int uuid) {
            this.uuid = uuid;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public Object getCompany_addr() {
            return company_addr;
        }

        public void setCompany_addr(Object company_addr) {
            this.company_addr = company_addr;
        }
    }

    @Override
    public String toString() {
        return "CooperCompanyEntity{" +
                "pagebean=" + pagebean +
                ", pageList=" + pageList +
                '}';
    }
}
